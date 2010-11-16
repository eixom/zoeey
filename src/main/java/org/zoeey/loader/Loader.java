/*
 * MoXie (SysTem128@GMail.Com) 2009-2-1 15:59:59
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.loader;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.zoeey.common.Supervisor;
import org.zoeey.common.WideCast;
import org.zoeey.common.ZObject;
import org.zoeey.dispatch.exceptions.RouterConnectException;
import org.zoeey.loader.annotations.Request;
import org.zoeey.loader.exceptions.LoaderException;
import org.zoeey.loader.fileupload.FileItem;
import org.zoeey.loader.fileupload.UploadConfig;
import org.zoeey.resource.ResourceExceptionMsg;
import org.zoeey.loader.fileupload.Uploader;
import org.zoeey.route.Query;
import org.zoeey.route.ParamHandler;
import org.zoeey.util.ArrayHelper;
import org.zoeey.util.FileHelper;
import org.zoeey.util.IpHelper;
import org.zoeey.util.QueryStringHelper;
import org.zoeey.util.ReflectCacheHelper;
import org.zoeey.util.StringHelper;
import org.zoeey.util.TextFileHelper;
import org.zoeey.validator.SwitchLabel;
import org.zoeey.validator.standards.FileItemVali;
import org.zoeey.validator.ValiAble;
import org.zoeey.validator.ValiFileAble;

/**
 * 数据读取器
 * @author MoXie
 */
public class Loader {

    /**
     * 全局监控者
     */
    private Supervisor svisor = null;
    /**
     * 验证器
     */
    private ValiAble[] valis = null;
    /**
     * 富化器
     */
    private Richer richer = null;

    /**
     * 数据读取
     * @param query     路由请求项
     * @param config    上传设置
     * @throws IOException
     */
    public Loader(Query query, UploadConfig config)
            throws IOException {
        this(query.getRequest(), config);
    }

    /**
     * 数据读取
     * @param query 路由请求项
     * @throws IOException
     */
    public Loader(Query query)
            throws IOException {
        this(query.getRequest());
    }

    /**
     * 数据读取
     * @param request   请求对象
     * @param config    上传设置
     * @throws IOException
     */
    public Loader(HttpServletRequest request, UploadConfig config) //
            throws IOException {
        this.request = request;
        getList = new ArrayList<Entry<String, String>>();
        postList = new ArrayList<Entry<String, String>>();
        if (config != null) {
            this.config = config;
        } else {
            this.config = new UploadConfig();
        }

        initGet();
        initMultipartPost();
        svisor = new Supervisor();
    }

    /**
     * 数据读取
     * @param request   请求对象
     * @throws IOException
     */
    public Loader(HttpServletRequest request) //
            throws IOException {
        this.request = request;
        this.isMultipart = false;
        getList = new ArrayList<Entry<String, String>>();
        postList = new ArrayList<Entry<String, String>>();

        initGet();
        initPost();
        svisor = new Supervisor();
    }

    /**
     * 设置全局监控者
     * @param svisor
     * @return
     */
    public Loader setSvisor(Supervisor svisor) {
        this.svisor = svisor;
        return this;
    }

    /**
     * 设置验证器
     * @param valis
     * @return
     */
    public Loader setValis(ValiAble[] valis) {
        if (valis != null) {
            this.valis = valis.clone();
        }
        return this;
    }

    /**
     * 设置富化者
     * @param richer
     * @return
     */
    public Loader setRicher(Richer richer) {
        this.richer = richer;
        return this;
    }

    /**
     * 填充数据模型
     * @see Request
     * @param <T>   
     * @param model 数据模型
     * @throws LoaderException
     */
    public <T> void load(T model) throws LoaderException {
        // <editor-fold defaultstate="collapsed" desc="从模型中提取字段名并载入数据。">
        if (model == null) {
            throw new LoaderException(ResourceExceptionMsg.LOADER_MODEL_NULL);
        }
        ReflectCacheHelper relectCache = ReflectCacheHelper.get(model.getClass());
        Map<Method, Request> methodMap = relectCache.<Request>getAnnotationPresentedMethodMap(Request.class);
        /**
         * request 标注
         */
        Request requestAnot;
        /**
         * Bean SET 方法的参数列表
         */
        Class<?>[] argTypes;
        /**
         * Bean SET 方法的第一个参数
         */
        Class<?> argType;
        /**
         * 参数是否为数组
         */
        boolean isArray;
        /**
         * 字段名
         */
        String fieldName;

        /**
         * 两种参数值
         */
        String value;
        String[] values;
        FileItem valueFile;
        FileItem[] valueFiles;
        /**
         * SET 方法标注列表
         */
        Class<? extends Annotation>[] annots;
        /**
         * 验证器验证结果
         */
        boolean isPass = true;
        boolean isRetain = true;
        Method method = null;
        RequestMethod valuedMethod = null;
        for (Entry<Method, Request> entry : methodMap.entrySet()) {
            method = entry.getKey();
            isRetain = true;
            isArray = false;
            argTypes = method.getParameterTypes();
            if (argTypes == null || argTypes.length != 1) {
                continue;
            } else {
                argType = argTypes[0];
                isArray = argType.isArray();
            }
            requestAnot = entry.getValue();
            /**
             * 初始化各种参数值
             */
            value = null;
            values = null;
            valueFile = null;
            valueFiles = null;
            valuedMethod = null;
            fieldName = requestAnot.name();
            /**
             * 取值
             */
            selectMethod:
            for (RequestMethod reqMethod : requestAnot.method()) {
                switch (reqMethod) {
                    case REQUEST:
                        if (isArray) {
                            values = getRequests(fieldName);
                        } else {
                            value = getRequest(fieldName);
                        }
                        break;
                    case GET:
                        if (isArray) {
                            values = getGets(fieldName);
                        } else {
                            value = getGet(fieldName);
                        }
                        break;
                    case POST:
                        if (isArray) {
                            values = getPosts(fieldName);
                        } else {
                            value = getPost(fieldName);
                        }
                        break;
                    case COOKIE:
                        if (isArray) {
                            values = getCookies(fieldName);
                        } else {
                            value = getCookie(fieldName);
                        }
                        break;
                    case SESSION:
                        if (isArray) {
                            /**
                             * session 不可传递数组
                             */
                            throw new LoaderException(ResourceExceptionMsg.LOADER_METHOD_ARGINVALID);
                        } else {
                            value = getSession(fieldName);
                        }
                        break;
                    case FILE:
                        if (isArray) {
                            valueFiles = getFiles(fieldName);
                        } else {
                            valueFile = getFile(fieldName);
                        }
                        break;
                    case HEADER:
                        if (isArray) {
                            values = getHeaders(fieldName);
                        } else {
                            value = getHeader(fieldName);
                        }
                        break;
                    case CLIENT_IP:
                        if (isArray) {
                            /**
                             * CLIENT_IP 不可传递数组
                             */
                            throw new LoaderException(ResourceExceptionMsg.LOADER_METHOD_ARGINVALID);
                        } else {
                            value = String.valueOf(getClientIp());
                        }
                        break;
                    default:
                        break;
                }
                if (RequestMethod.FILE != reqMethod) {
                    if (isArray) {
                        valuedMethod = reqMethod;
                        if (values != null && values.length > 0) {
                            break selectMethod;
                        }
                    } else {
                        valuedMethod = reqMethod;
                        if (value != null) {
                            break selectMethod;
                        }
                    }
                } else {
                    if (isArray) {
                        valuedMethod = reqMethod;
                        if (valueFiles != null && valueFiles.length > 0) {
                            break selectMethod;
                        }
                    } else {
                        valuedMethod = reqMethod;
                        if (valueFile != null) {
                            break selectMethod;
                        }
                    }
                }
            } // RequestMethod选择结束

            if (valuedMethod == null) {
                continue;
            }
            /**
             * 富化
             */
            if (richer != null) {
                richer.setLoader(this);
                richer.setSvisor(svisor);
                if (RequestMethod.FILE == valuedMethod) {
                    if (isArray) {
                        valueFiles = richer.rich(fieldName, valueFiles);
                    } else {
                        valueFile = richer.rich(fieldName, valueFile);
                    }
                } else {
                    if (isArray) {
                        values = richer.rich(fieldName, values);
                    } else {
                        value = richer.rich(fieldName, value);
                    }
                }
            }

            /**
             * 初始验证结果
             */
            isPass = true;
            /**
             * 
             */
            if (RequestMethod.FILE == valuedMethod) {
                /**
                 * 
                 */
                annots = relectCache.getMethodAnnotationTypes(method);
                ValiFileAble valiFile = new FileItemVali();
                if (valiFile.accept(annots)) {
                    if (isArray) {
                        isPass = valiFile.vali(svisor, relectCache.getMethodAnnotationMap(method), valueFiles);
                    } else {
                        isPass = valiFile.vali(svisor, relectCache.getMethodAnnotationMap(method), valueFile);
                    }
                    if (valiFile.swit() == SwitchLabel.ASSERT || valiFile.swit() == SwitchLabel.ALLOWNULL_ASSERT) {
                        break;
                    }
                }
                if (isPass) {
                    if (isArray) {
                        insFileValue(model, method, argType, valueFiles);
                    } else {
                        insFileValue(model, method, argType, valueFile);
                    }
                }
            } else {
                insValueLoop:
                do {
                    if (valis != null) {
                        annots = relectCache.getMethodAnnotationTypes(method);
                        ValiAble vali;
                        for (int i = 0; i < valis.length; i++) {
                            vali = valis[i];
                            if (vali != null) {
                                if (vali.accept(annots)) {
                                    if (isArray) {
                                        isPass &= vali.vali(svisor, relectCache.getMethodAnnotationMap(method), values);
                                    } else {
                                        isPass &= vali.vali(svisor, relectCache.getMethodAnnotationMap(method), value);
                                    }
                                    isRetain &= vali.isRetain();
                                    if (vali.swit() == SwitchLabel.ASSERT) {
                                        break;
                                    }
                                    if (vali.swit() == SwitchLabel.ALLOWNULL_ASSERT) {
                                        break insValueLoop;
                                    }
                                }

                            }
                        }
                    }
                    if (isPass || isRetain) {
                        if (isArray) {
                            insValue(model, method, argType, values);
                        } else {
                            insValue(model, method, argType, value);
                        }
                    }
                } while (false);
            }

        }
        // </editor-fold>
    }

    /**
     * 装载单字符串
     * @param method    单参数方法
     * @param argType   参数类型
     * @param value     值
     * @throws LoadMethodsException
     */
    private void insValue(Object model, Method method, Class<?> argType, String value)
            throws LoaderException {
        try {
            if (value == null) {
                return;
            }
            if (String.class.isAssignableFrom(argType)) {
                method.invoke(model, value);
            } else if (Integer.class.isAssignableFrom(argType) || int.class.isAssignableFrom(argType)) {
                method.invoke(model, new ZObject(value).toInteger());
            } else if (Long.class.isAssignableFrom(argType) || long.class.isAssignableFrom(argType)) {
                method.invoke(model, new ZObject(value).toLong());
            } else if (Double.class.isAssignableFrom(argType) || double.class.isAssignableFrom(argType)) {
                method.invoke(model, new ZObject(value).toDouble());
            } else if (Short.class.isAssignableFrom(argType) || short.class.isAssignableFrom(argType)) {
                method.invoke(model, new ZObject(value).toShort());
            } else {
                throw new LoaderException(ResourceExceptionMsg.LOADER_METHOD_ARGINVALID);
            }
        } catch (IllegalAccessException ex) {
            throw new LoaderException(ResourceExceptionMsg.LOADER_METHOD_NOACCESS);
        } catch (IllegalArgumentException ex) {
            throw new LoaderException(ResourceExceptionMsg.LOADER_METHOD_ARGINVALID);
        } catch (InvocationTargetException ex) {
            throw new LoaderException(ResourceExceptionMsg.LOADER_METHOD_EXUNCATCHED);
        }
    }

    /**
     * 装载多字符串值
     * @param method    单参数方法
     * @param argType   参数类型
     * @param values    值
     * @throws LoadMethodsException
     */
    private void insValue(Object model, Method method, Class<?> argType, String[] values)
            throws LoaderException {
        try {
            if (values == null) {
                return;
            }
            argType = argType.getComponentType();
            if (String.class.isAssignableFrom(argType)) {
                method.invoke(model, new Object[]{values});
            } else if (Integer.class.isAssignableFrom(argType) || int.class.isAssignableFrom(argType)) {
                method.invoke(model, new Object[]{WideCast.castToInteger(values)});
            } else if (Long.class.isAssignableFrom(argType) || long.class.isAssignableFrom(argType)) {
                method.invoke(model, new Object[]{WideCast.castToLong(values)});
            } else if (Double.class.isAssignableFrom(argType) || double.class.isAssignableFrom(argType)) {
                method.invoke(model, new Object[]{WideCast.castToDouble(values)});
            } else if (Short.class.isAssignableFrom(argType) || short.class.isAssignableFrom(argType)) {
                method.invoke(model, new Object[]{WideCast.castToShort(values)});
            } else {
                throw new LoaderException(ResourceExceptionMsg.LOADER_METHOD_ARGINVALID);
            }
        } catch (IllegalAccessException ex) {
            throw new LoaderException(ResourceExceptionMsg.LOADER_METHOD_NOACCESS);
        } catch (IllegalArgumentException ex) {
            throw new LoaderException(ResourceExceptionMsg.LOADER_METHOD_ARGINVALID);
        } catch (InvocationTargetException ex) {
            throw new LoaderException(ResourceExceptionMsg.LOADER_METHOD_EXUNCATCHED);
        }
    }

    /**
     * 装载单文件值
     * @param method 单参数方法
     * @param argType   参数类型
     * @param value     值
     * @throws LoadMethodsException
     */
    private void insFileValue(Object model, Method method, Class<?> argType, FileItem value) //
            throws LoaderException {
        try {
            if (value == null) {
                return;
            }
            if (FileItem.class.isAssignableFrom(argType)) {
                method.invoke(model, value);
            } else {
                /**
                 * 仅允许使用 FileItem 类型
                 */
                throw new LoaderException(ResourceExceptionMsg.LOADER_METHOD_ARGINVALID);
            }
        } catch (IllegalAccessException ex) {
            throw new LoaderException(ResourceExceptionMsg.LOADER_METHOD_NOACCESS);
        } catch (IllegalArgumentException ex) {
            throw new LoaderException(ResourceExceptionMsg.LOADER_METHOD_ARGINVALID);
        } catch (InvocationTargetException ex) {
            throw new LoaderException(ResourceExceptionMsg.LOADER_METHOD_EXUNCATCHED);
        }
    }

    /**
     * 装载多文件值
     * @param method
     * @param argType
     * @param values
     * @throws LoadMethodsException
     */
    private void insFileValue(Object model, Method method, Class<?> argType, FileItem[] values) //
            throws LoaderException {
        try {
            if (values == null) {
                return;
            }
            argType = argType.getComponentType();
            if (FileItem.class.isAssignableFrom(argType)) {
                method.invoke(model, new Object[]{values});
            } else {
                /**
                 * 仅允许使用 FileItem 类型
                 */
                throw new LoaderException(ResourceExceptionMsg.LOADER_METHOD_ARGINVALID);
            }
        } catch (IllegalAccessException ex) {
            throw new LoaderException(ResourceExceptionMsg.LOADER_METHOD_NOACCESS);
        } catch (IllegalArgumentException ex) {
            throw new LoaderException(ResourceExceptionMsg.LOADER_METHOD_ARGINVALID);
        } catch (InvocationTargetException ex) {
            throw new LoaderException(ResourceExceptionMsg.LOADER_METHOD_EXUNCATCHED);
        }
    }
    /**
     * loaderHelper Adapter
     */
    /**
     * 请求对象
     */
    private HttpServletRequest request;
    /**
     * GET 方式传递的参数
     */
    private List<Entry<String, String>> getList = null;
    /**
     * Router 中以 GET 方式传递的参数
     */
    private ParamHandler paramHandler = null;
    /**
     * POST 方式传递的参数
     */
    private List<Entry<String, String>> postList = null;
    /**
     * 是否为
     */
    private boolean isMultipart = false;
    /**
     * 上传组件
     */
    private Uploader uploader = null;
    /**
     * 配置信息
     */
    UploadConfig config = null;

    /**
     * 设置路由信息，参数可从GET中取出
     * @param fieldList
     * @throws RouterConnectException
     */
    public void setParamHandler(ParamHandler paramHandler) {
        this.paramHandler = paramHandler;
    }

    /**
     * 初始化 GET 方式传递的字段
     */
    private void initGet() {
        String queryString = request.getQueryString();
        if (queryString == null) {
            return;
        }
        getList.addAll(QueryStringHelper.getEntryList(queryString, request.getCharacterEncoding()));
    }

    /**
     * 初始化 POST 方式传递的字段
     */
    private void initPost() throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        if (inputStream == null) {
            return;
        }
        postList = QueryStringHelper.getEntryList(TextFileHelper.read(inputStream//
                , request.getCharacterEncoding()), request.getCharacterEncoding());
    }

    /**
     * 初始化 POST 方式传递的字段 包含文件上传
     * @throws IOException
     */
    private void initMultipartPost() throws IOException {
        uploader = new Uploader(request, config);
        if (!uploader.isMultipart()) {
            isMultipart = false;
            initPost();
            return;
        }
        isMultipart = true;
    }

    /**
     * 获取上传组件
     * @return
     */
    public Uploader getUploader() {
        return uploader;
    }

    /**
     * 是否为多段型数据（文件上传）
     * @return
     */
    public boolean isMultipart() {
        return this.isMultipart;
    }

    /**
     * 获取GET方式传递的参数，包括由Router传入的参数。
     * 注意：当有多个值时只取最后一个，GET方式权重高于Router。
     * @param name 字段名
     * @return  没有找到该字段时返回 null
     */
    public String getGet(String name) {
        String val = null;
        if (paramHandler != null) {
            val = paramHandler.getValue(name);
        }
        for (Entry<String, String> field : getList) {
            if (name.equals(field.getKey())) {
                val = field.getValue();
            }
        }
        return val;
    }

    /**
     * 获取POST方式传递的参数，包括文件上传时的文本字段(type=text)
     * @param name
     * @return 没有找到该字段时返回 null
     */
    public String getPost(String name) {
        if (isMultipart) {
            return uploader.getParamenter(name);
        } else {
            if (name == null) {
                return null;
            }
            for (Entry<String, String> field : postList) {
                if (name.equals(field.getKey())) {
                    return field.getValue();
                }
            }
            return null;
        }
    }

    /**
     * 获取 COOKIE 方式传递的字段
     * @param name 字段名称
     * @return 没有找到该字段时返回 null
     */
    public String getCookie(String name) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (name.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    /**
     * 获取session内存储的字段
     * @param name  字段名称
     * @return  没有找到该字段时返回 null
     */
    public String getSession(String name) {
        HttpSession session = request.getSession();
        if (session != null) {
            return ZObject.conv(session.getAttribute(name)).toString();
        }
        return null;
    }

    /**
     * 获取文件字段
     * @param name 字段名称
     * @return 没有找到该字段时返回 null
     */
    public FileItem getFile(String name) {
        if (isMultipart == false) {
            return null;
        }
        return uploader.getFileItem(name);
    }

    /**
     * 分别获取 GET 或 POST 或 COOKIE 方式传递的字段
     *
     * @param name  字段名称
     * @return  没有找到该字段时返回 null
     */
    public String getRequest(String name) {
        String str = null;
        str = getGet(name);
        if (str != null) {
            return str;
        }
        str = getPost(name);
        if (str != null) {
            return str;
        }
        str = getCookie(name);
        if (str != null) {
            return str;
        }
        return null;
    }

    /**
     * 获取 <b>GET</b> 方式传递的同键多值参数
     *
     * @param name  字段名称
     * @return  没有找到该字段时返回 null
     */
    public String[] getGets(String name) {
        List<String> valList = null;
        if (paramHandler != null) {
            valList = paramHandler.getValueList(name);
        }
        if (valList == null) {
            valList = new ArrayList<String>();
        }
        for (Entry<String, String> field : getList) {
            if (name.equals(field.getKey())) {
                valList.add(field.getValue());
            }
        }
        return valList.toArray(new String[valList.size()]);
    }

    /**
     * 获取 <b>POST</b> 方式传递的同键多值参数
     * @param name  字段名称
     * @return  没有找到该字段时返回 null
     */
    public String[] getPosts(String name) {
        if (!isMultipart) {
            String[] values = new String[postList.size()];
            int i = 0;
            for (Entry<String, String> field : postList) {
                if (name.equals(field.getKey())) {
                    values[i] = field.getValue();
                    i++;
                }
            }
            if (i == 0) {
                return null;
            }
            return ArrayHelper.copyOf(values, i);
        } else {
            List<String> list = uploader.getParamenterValueList(name);
            /**
             * fixedbug: list is null
             */
            if (list == null) {
                return null;
            }
            String[] values = new String[list.size()];
            int i = 0;
            for (String str : list) {
                values[i] = str;
                i++;
            }
            if (i == 0) {
                return null;
            }
            return ArrayHelper.copyOf(values, i);
        }
    }

    /**
     * 获取 <b>COOKIE</b> 方式传递的同键多值参数
     * @param name  字段名称
     * @return  没有找到该字段时返回 null
     */
    public String[] getCookies(String name) {

        Cookie[] cookies = request.getCookies();
        String[] values = new String[0];
        if (cookies != null) {
            int i = 0;
            values = new String[cookies.length];
            for (Cookie cookie : cookies) {
                if (name.equals(cookie.getName())) {
                    values[i] = cookie.getValue();
                    i++;
                }
            }
            if (i == 0) {
                return null;
            }
            return ArrayHelper.copyOf(values, i);
        }
        return null;

    }

    /**
     * 获取 <b>GET 或 POST 或 COOKIE</b> 方式传递的同键多值参数
     * @param name  字段名称
     * @return  没有找到该字段时返回 空数组(new String[0])
     */
    public String[] getRequests(String name) {
        String[] strs = getGets(name);
        if (strs != null) {
            return strs;
        }
        strs = getPosts(name);
        if (strs != null) {
            return strs;
        }
        strs = getCookies(name);
        if (strs != null) {
            return strs;
        }
        return null;
    }

    /**
     * 获取单键多值的文件字段
     * @param name  字段名称
     * @return  没有找到该字段时返回 null
     */
    public FileItem[] getFiles(String name) {
        if (isMultipart == false) {
            return null;
        }
        List<FileItem> list = uploader.getFileItemValueList(name);
        if (list == null) {
            return null;
        }
        return list.toArray(new FileItem[list.size()]);
    }

    /**
     * 获取请求头字段信息
     * @param name 字段名称
     * @return  没有找到该字段时返回 null
     */
    public String getHeader(String name) {
        return request.getHeader(name);
    }

    /**
     * 获取请求头部信息
     * @param name 字段名
     * @return  没有找到该字段时返回 null
     */
    public String[] getHeaders(String name) {
        List<String> headers = new ArrayList<String>();
        @SuppressWarnings("unchecked")
        Enumeration<String> enumeration = request.getHeaders(name);
        if (enumeration != null) {
            while (enumeration.hasMoreElements()) {
                headers.add(enumeration.nextElement());
            }
        }
        return headers.toArray(new String[headers.size()]);
    }

    /**
     * 获取用户IP地址
     * 注意：并不保证绝对有效可信。
     *      仅支持IPv4
     * @return  未能获取时返回 0L
     */
    public long getClientIp() {
        long clientIp = 0L;
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            /**
             * 多重反向代理
             */
            if (ip != null && ip.indexOf(',') > -1) {
                String[] ips = StringHelper.split(ip, ',');
                if (ips.length > 0) {
                    ip = ips[0];
                }
            }
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // bugfix:127.0.0.1 separator:"."
        if (StringHelper.split(ip, '.').length == 4) {
            clientIp = IpHelper.toLong(ip);
        }
        return clientIp;
    }

    /**
     * 获取载入的Request对象
     * @return
     */
    public HttpServletRequest getRequest() {
        return request;
    }

    /**
     * 查验提交方式是否为<b>POST</b>
     * @return
     */
    public boolean isPosted() {
        return isMethod("POST");
    }

    /**
     * 查验提交方式，大小写不敏感
     * @param method 提交方式
     * @return
     */
    public boolean isMethod(String method) {
        return request.getMethod().equalsIgnoreCase(method);
    }

    /**
     * 清理临时文件
     */
    public void clearTempFile() {
        if (!isMultipart()) {
            return;
        }
        Map<String, List<FileItem>> map = getUploader().getFileMap();
        if (map == null) {
            return;
        }
        Collection<List<FileItem>> fileItemListCol = map.values();
        for (List<FileItem> fileItemList : fileItemListCol) {
            if (fileItemList != null) {
                for (FileItem fileItem : fileItemList) {
                    if (fileItem != null) {
                        FileHelper.tryDelete(fileItem.getTempFile());
                    }
                }
            }
        }
        return;
    }
}
