/*
 * MoXie (SysTem128@GMail.Com) 2009-8-13 17:10:12
 * 
 * Copyright &copy; 2008-2010 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.zdo;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.zoeey.resource.ResourceExceptionMsg;
import org.zoeey.util.KeyValueFile;
import org.zoeey.util.MapHelper;
import org.zoeey.util.ReflectCacheHelper;
import org.zoeey.util.StringHelper;
import org.zoeey.zdo.exceptions.DAOBindBeanException;
import org.zoeey.zdo.zdbcontrol.ZDBControlMixed;

/**
 * 数据库访问辅助工具<br />
 * 注意：本类为了减少操作复杂度去除了对存储过程的支持。
 * @author MoXie
 */
public class DAOHelper {

    /**
     * ZDO Control
     */
    private ZDBControlMixed dbControl;
    /**
     * 是否自动关闭连接
     */
    private boolean autoClose = true;
    /**
     * 字段前缀
     */
    private String prefix = null;

    /**
     * 自定义操作组件的操作类
     * @param zdbControlMixed ZDB 混合操作组件。
     */
    public DAOHelper(ZDBControlMixed zdbControlMixed) {
        dbControl = zdbControlMixed;
    }

    /**
     * 不需要进行缓存的操作类
     * @param conn
     */
    public DAOHelper(Connection conn) {
        dbControl = new ZDBControlMixed(conn);
    }

    /**
     * 需要进行缓存的操作类
     * @param conn
     * @param cacheKey
     */
    public DAOHelper(Connection conn, String cacheKey) {
        dbControl = new ZDBControlMixed(conn, cacheKey);
    }

    /**
     * 是否自动关闭连接<br />
     * 注意：关闭时请注意手动执行 {@link #close() close()}
     * @param autoClose 是否自动关闭连接
     */
    public void setAutoClose(boolean autoClose) {
        this.autoClose = autoClose;
    }

    /**
     * 设置字段前缀
     * @param prefix
     * @return
     */
    public DAOHelper prefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    /**
     * 移除字段前缀
     * @return
     */
    public DAOHelper removePrefix() {
        this.prefix = null;
        return this;
    }

    /**
     * 发出SQL请求
     * @param sql
     * @return
     * @throws SQLException
     */
    public DAOHelper query(String sql) throws SQLException {
        dbControl.prepareStatement(sql);
        return this;
    }

    /**
     * 发出SQL请求<br />
     * SQL语句将从{@link KeyValueFile}中提取
     * @param keyValuefile  key file 格式文件
     * @param name  键名
     * @return
     * @see KeyValueFile
     * @throws SQLException
     * @throws IOException 
     */
    public DAOHelper queryFromText(File keyValuefile, String name) //
            throws SQLException, IOException {
        dbControl.prepareStatement(KeyValueFile.toMap(keyValuefile).get(name));
        return this;
    }

    /**
     * 绑定参数<br />
     * 注意：每次使用绑定参数都会从1开始
     * @param args
     * @return
     * @throws SQLException
     */
    public DAOHelper bind(Object... args) throws SQLException {
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                dbControl.setObject(i + 1, args[i]);
            }
        }
        return this;
    }

    /**
     * 绑定参数 <br />
     * 注意：如prefix不为null则会被使用。
     * @param bean
     * @return
     * @throws SQLException
     * @throws DAOBindBeanException
     */
    public DAOHelper bindBean(Object bean) throws SQLException, DAOBindBeanException {
        ReflectCacheHelper relectCache = ReflectCacheHelper.get(bean.getClass());
        Map<String, Method> methodMap = relectCache.getGetMap();
        if (methodMap != null) {
            try {

                List<String> paramList = dbControl.getParamList();
                Method method = null;
                if (prefix != null) {
                    int prefixLen = prefix.length();
                    for (String param : paramList) {
                        method = methodMap.get(StringHelper.subString(param, prefixLen, -1));
                        if (method != null) {
                            dbControl.setObject(param, method.invoke(bean));
                        }
                    }
                } else {
                    for (String param : paramList) {
                        method = methodMap.get(param);
                        if (method != null) {
                            dbControl.setObject(param, method.invoke(bean));
                        }
                    }
                }
            } catch (IllegalAccessException ex) {
                throw new DAOBindBeanException(ResourceExceptionMsg.DAO_INVOKE_NOACCESS);
            } catch (IllegalArgumentException ex) {
                throw new DAOBindBeanException(ResourceExceptionMsg.DAO_INVOKE_ARGINVALID);
            } catch (InvocationTargetException ex) {
                throw new DAOBindBeanException(ResourceExceptionMsg.DAO_INVOKE_EXUNCATCHED);
            }
        }
        return this;
    }

    /**
     * 绑定参数<br />
     * 注意：如prefix不为null则会被使用。
     * @param paramMap
     * @return
     * @throws SQLException
     */
    public DAOHelper bindMap(Map<String, Object> paramMap) throws SQLException {
        if (paramMap != null) {
            for (Entry<String, Object> entry : paramMap.entrySet()) {
                if (prefix != null) {
                    dbControl.setObject(prefix.concat(entry.getKey()), entry.getValue());
                } else {
                    dbControl.setObject(entry.getKey(), entry.getValue());
                }
            }
        }
        return this;
    }

    /**
     * 绑定参数<br />
     * 注意：每次使用绑定参数都会从1开始
     * @param paramList
     * @return
     * @throws SQLException
     */
    public DAOHelper bindList(List<Object> paramList) throws SQLException {
        if (paramList != null) {
            int i = 1;
            for (Object obj : paramList) {
                dbControl.setObject(i++, obj);
            }
        }
        return this;
    }

    /**
     * 新执行组
     * @return
     * @throws SQLException
     */
    public DAOHelper addBatch() throws SQLException {
        dbControl.addBatch();
        return this;
    }

    /**
     * 执行语句<br />
     * 注意：无结果时返回 0
     * @return
     * @throws SQLException
     */
    public int exec() throws SQLException {
        int result = 0;
        try {
            result = dbControl.executeUpdate();
        } finally {
            autoClose();
        }
        return result;
    }
    /**
     * 获取第一个字段
     * @see #getGeneratedKeys(org.zoeey.zdo.DAOHelper.ListHandler)
     */
    public static ListHandler<Integer> GENERATED_HANDLER_INTEGER = new ListHandler<Integer>() {

        public Integer handle(ResultSet rs, int index) throws SQLException {
            return rs.getInt(1);
        }
    };

    /**
     * 返回请求生成字段<br />
     * handler 为 null 时返回第一个字段的列表<br />
     * 注意：在新增数据前请注意取消自动关闭。 
     * @param <T>   返回值列表单元数据类型
     * @param handler  结果处理器
     * @return  无结果时返回 null
     * @throws SQLException
     * @see #setAutoClose(boolean) 
     * @see #GENERATED_HANDLER_INTEGER   
     */
    public <T> List<T> getGeneratedKeys(ListHandler<T> handler) throws SQLException {
        List<T> list = null;
        try {
            if (handler != null) {
                ResultSet rs = dbControl.getGeneratedKeys();
                int i = 0;
                list = new ArrayList<T>();
                while (rs.next()) {
                    list.add(handler.handle(rs, i++));
                }
            }
        } finally {
            autoClose();
        }
        return list;
    }

    /**
     * 返回单一数据(取第一个字段)<br />
     * 注意：无结果时返回null
     * @param <T>   返回值列表单元数据类型
     * @param toType    返回值类，不可为 null
     * @return  结果列表第一行第一个字段使用toType强制转换的结果
     * @throws SQLException
     * @see #setAutoClose(boolean) 
     */
    public <T> T exec(Class<T> toType) throws SQLException {
        ResultSet rs;
        T result = null;
        try {
            rs = dbControl.executeQuery();
            if (rs.next()) {
                result = toType.cast(rs.getObject(1));
            }
        } finally {
            autoClose();
        }
        return result;
    }

    /**
     * 取回数据列表
     * @param <T>   返回值列表单元数据类型
     * @param handler   结果处理器
     * @return  无结果时返回 null
     * @throws SQLException
     * @see #setAutoClose(boolean) 
     */
    public <T> List<T> exec(ListHandler<T> handler) throws SQLException {
        List<T> list = null;
        try {
            if (handler != null) {
                ResultSet rs = dbControl.executeQuery();
                int i = 0;
                list = new ArrayList<T>();
                while (rs.next()) {
                    list.add(handler.handle(rs, i++));
                }
            }
        } finally {
            autoClose();
        }
        return list;
    }

    /**
     * 取回数据<br />
     * 取出结果集中的第一条
     * @param <T>   返回值数据类型
     * @param handler   结果处理器
     * @return  无结果时返回 null
     * @throws SQLException
     */
    public <T> T exec(ObjectHandler<T> handler) throws SQLException {
        T result = null;
        try {
            if (handler != null) {
                ResultSet rs = dbControl.executeQuery();
                while (rs.next()) {
                    result = handler.handle(rs);
                    break;
                }
            }
        } finally {
            autoClose();
        }
        return result;
    }

    /**
     * 遍历数据
     * @param handler   结果处理器
     * @throws SQLException
     */
    public void exec(TraverseHandler handler) throws SQLException {

        try {
            if (handler != null) {
                ResultSet rs = dbControl.executeQuery();
                while (rs.next()) {
                    handler.handle(rs);
                }
            }
        } finally {
            autoClose();
        }
    }

    /**
     * 取回一个 Bean 的数据
     * 注意：如prefix不为null则会被使用。
     * @param <T>   返回值数据类型
     * @param beanClass 返回值类
     * @return 无结果时返回 null
     * @throws SQLException
     * @throws DAOBindBeanException
     * @see #setAutoClose(boolean) 
     */
    public <T> T getBean(Class<T> beanClass) throws SQLException, DAOBindBeanException {
        T bean = null;
        try {
            ReflectCacheHelper relectCache = ReflectCacheHelper.get(beanClass);
            Map<String, Method> methodMap = MapHelper.keyToUpperCase(relectCache.getSetMap());
            if (methodMap != null) {
                ResultSet rs = dbControl.executeQuery();
                String columnName = null;
                Method method = null;
                ResultSetMetaData rsmd;
                try {
                    bean = beanClass.newInstance();
                    while (rs.next()) {
                        rsmd = rs.getMetaData();
                        for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                            /**
                             * fixedbug
                             * getColumnName -> getColumnLabel
                             * Article_Title : ARTICLE_TITLE
                             * Article_Title as title : TITLE
                             */
                            columnName = rsmd.getColumnLabel(i);
                            if (prefix != null) {
                                columnName = StringHelper.replaceFirstIgnoreCase(columnName, prefix, "");
                            }
                            method = methodMap.get(columnName.toUpperCase());
                            if (method != null && rs.getObject(i) != null) {
                                method.invoke(bean, rs.getObject(i));
                            }
                        }
                        break;
                    }
                } catch (InstantiationException ex) {
                    throw new DAOBindBeanException(ResourceExceptionMsg.DAO_INVOKE_INSTANTIATION, ex);
                } catch (IllegalAccessException ex) {
                    throw new DAOBindBeanException(ResourceExceptionMsg.DAO_INVOKE_NOACCESS, ex);
                } catch (IllegalArgumentException ex) {
                    throw new DAOBindBeanException(ResourceExceptionMsg.DAO_INVOKE_ARGINVALID, ex);
                } catch (InvocationTargetException ex) {
                    throw new DAOBindBeanException(ResourceExceptionMsg.DAO_INVOKE_EXUNCATCHED, ex);
                }
            }
        } finally {
            autoClose();
        }
        return bean;
    }

    /**
     * 取回一个 Bean 列表数据<br />
     * 注意：如prefix不为null则会被使用。<br />
     *      ex.<br />
     *      prefix: user_<br />
     *      sql:  select account as user_name from users where id=1<br />
     *      result: setName(:name)<br />
     * @param <T>   返回值列表单元数据类型
     * @param beanClass 返回值列表单元类
     * @return  无结果时返回 null
     * @throws SQLException
     * @throws DAOBindBeanException
     * @see #setAutoClose(boolean) 
     */
    public <T> List<T> getBeanList(Class<T> beanClass) throws SQLException, DAOBindBeanException {
        List<T> list = null;
        T bean = null;
        try {
            ReflectCacheHelper relectCache = ReflectCacheHelper.get(beanClass);
            Map<String, Method> methodMap = MapHelper.keyToUpperCase(relectCache.getSetMap());
            if (methodMap != null) {
                ResultSet rs = dbControl.executeQuery();
                String[] columnNames = null;
                String columnName = null;
                Method method = null;
                ResultSetMetaData rsmd;
                list = new ArrayList<T>();
                try {
                    while (rs.next()) {
                        bean = beanClass.newInstance();
                        if (columnNames == null) {
                            rsmd = rs.getMetaData();
                            columnNames = new String[rsmd.getColumnCount()];
                            for (int i = 1; i <= columnNames.length; i++) {
                                /**
                                 * fixedbug
                                 * getColumnName -> getColumnLabel
                                 * Article_Title : ARTICLE_TITLE
                                 * Article_Title as title : TITLE
                                 */
                                columnName = rsmd.getColumnLabel(i).toUpperCase();
                                if (prefix != null) {
                                    columnName = StringHelper.replaceFirstIgnoreCase(columnName, prefix, "");
                                }
                                columnNames[i - 1] = columnName;
                            }
                        }
                        if (columnNames != null) {
                            for (int i = 1; i <= columnNames.length; i++) {
                                method = methodMap.get(columnNames[i - 1]);
                                /**
                                 * 
                                 */
                                if (method != null && rs.getObject(i) != null && method.getParameterTypes().length == 1) {
                                    method.invoke(bean, rs.getObject(i));
                                }
                            }
                            list.add(bean);
                        }
                    }
                } catch (InstantiationException ex) {
                    throw new DAOBindBeanException(ResourceExceptionMsg.DAO_INVOKE_INSTANTIATION, ex);
                } catch (IllegalAccessException ex) {
                    throw new DAOBindBeanException(ResourceExceptionMsg.DAO_INVOKE_NOACCESS, ex);
                } catch (IllegalArgumentException ex) {
                    throw new DAOBindBeanException(ResourceExceptionMsg.DAO_INVOKE_ARGINVALID, ex);
                } catch (InvocationTargetException ex) {
                    throw new DAOBindBeanException(ResourceExceptionMsg.DAO_INVOKE_EXUNCATCHED, ex);
                }
            }
        } finally {
            autoClose();
        }
        return list;
    }

    /**
     * 取回数据列表<br />
     * 注意：如prefix不为null则会被使用。<br />
     *      ex.<br />
     *      prefix: user_<br />
     *      sql:  select account as user_name from users where id=1<br />
     *      result: [{"name":"user's name"}]<br />
     * @return  无结果时返回 null
     * @throws SQLException
     * @throws DAOBindBeanException
     * @see #setAutoClose(boolean) 
     */
    public List<Map<String, Object>> getMapList() throws SQLException, DAOBindBeanException {
        List<Map<String, Object>> list = null;
        Map<String, Object> bean = null;


        ResultSet rs = dbControl.executeQuery();
        String[] columnNames = null;
        String columnName = null;
        ResultSetMetaData rsmd;
        list = new ArrayList<Map<String, Object>>();
        try {
            while (rs.next()) {
                if (columnNames == null) {
                    rsmd = rs.getMetaData();
                    columnNames = new String[rsmd.getColumnCount()];
                    for (int i = 1; i <= columnNames.length; i++) {
                        /**
                         * fixedbug
                         * getColumnName -> getColumnLabel
                         * Article_Title : ARTICLE_TITLE
                         * Article_Title as title : TITLE
                         */
                        columnName = rsmd.getColumnLabel(i);
                        if (prefix != null) {
                            columnName = StringHelper.replaceFirstIgnoreCase(columnName, prefix, "");
                        }
                        columnNames[i - 1] = columnName;
                    }
                }
                if (columnNames != null) {
                    bean = new HashMap<String, Object>();
                    for (int i = 1; i <= columnNames.length; i++) {
                        bean.put(columnNames[i - 1], rs.getObject(i));
                    }
                    list.add(bean);
                }
            }
        } finally {
            autoClose();
        }
        return list;
    }

    /**
     * 取回数据行<br />
     * 注意：如prefix不为null则会被使用。<br />
     *      ex.<br />
     *      prefix: user_<br />
     *      sql:  select account as user_name from users where id=1<br />
     *      result: {"name":"user's name"}<br />
     * @return  无结果时返回 null
     * @throws SQLException
     * @throws DAOBindBeanException
     * @see #setAutoClose(boolean)
     */
    public Map<String, Object> getMap() throws SQLException {
        Map<String, Object> row = new HashMap<String, Object>();

        ResultSet rs = dbControl.executeQuery();
        String[] columnNames = null;
        String columnName = null;
        ResultSetMetaData rsmd;

        try {
            while (rs.next()) {
                if (columnNames == null) {
                    rsmd = rs.getMetaData();
                    columnNames = new String[rsmd.getColumnCount()];
                    for (int i = 1; i <= columnNames.length; i++) {
                        /**
                         * fixedbug
                         * getColumnName -> getColumnLabel
                         * Article_Title : ARTICLE_TITLE
                         * Article_Title as title : TITLE
                         */
                        columnName = rsmd.getColumnLabel(i);
                        if (prefix != null) {
                            columnName = StringHelper.replaceFirstIgnoreCase(columnName, prefix, "");
                        }
                        columnNames[i - 1] = columnName;
                    }
                }
                if (columnNames != null) {
                    for (int i = 1; i <= columnNames.length; i++) {
                        row.put(columnNames[i - 1], rs.getObject(i));
                    }
                }
                break;
            }
        } finally {
            autoClose();
        }
        return row;
    }

    /**
     * 使用缓存的单值取回
     * @param <T>
     * @param handler
     * @param toType
     * @return
     * @throws SQLException
     */
    public <T> T cache(CacheHandler<T> handler, Class<T> toType) //
            throws SQLException {
        String cacheKey = dbControl.getUniqueKey();
        if (handler.isCached(cacheKey)) {
            autoClose();
            return handler.get(cacheKey);
        }
        T data = exec(toType);
        handler.set(cacheKey, data);
        return data;
    }

    /**
     * 使用缓存的数据列表取回
     * @param <T>   返回值数据类型
     * @param cacheHandler  缓存处理器
     * @param queryHandler  结果处理器
     * @return  无结果时返回 null
     * @throws SQLException
     * @see #exec(org.zoeey.zdo.DAOHelper.ListHandler) 
     */
    public <T> List<T> cache(CacheHandler<List<T>> cacheHandler, ListHandler<T> queryHandler) //
            throws SQLException {
        String cacheKey = dbControl.getUniqueKey();
        if (cacheHandler.isCached(cacheKey)) {
            autoClose();
            return cacheHandler.get(cacheKey);
        }
        List<T> data = exec(queryHandler);
        cacheHandler.set(cacheKey, data);
        return data;
    }

    /**
     * 使用缓存的数据取回
     * @param <T>   返回值数据类型
     * @param cacheHandler  缓存处理器
     * @param queryHandler  结果处理器
     * @return  无结果时返回 null
     * @throws SQLException
     * @see #exec(org.zoeey.zdo.DAOHelper.ObjectHandler) 
     */
    public <T> T cache(CacheHandler<T> cacheHandler, ObjectHandler<T> queryHandler) //
            throws SQLException {
        try {
            String cacheKey = dbControl.getUniqueKey();
            if (cacheHandler.isCached(cacheKey)) {
                return cacheHandler.get(cacheKey);
            }
            T data = exec(queryHandler);
            cacheHandler.set(cacheKey, data);
            return data;
        } finally {
            autoClose();
        }
    }

    /**
     * 使用缓存的数据取回Bean
     * @param <T>  返回值数据类型
     * @param cacheHandler  缓存处理器
     * @param beanClass 返回值类
     * @return
     * @throws SQLException
     * @throws DAOBindBeanException
     * @see #getBean(java.lang.Class) 
     */
    public <T> T cacheBean(CacheHandler<T> cacheHandler, Class<T> beanClass) //
            throws SQLException, DAOBindBeanException {
        try {
            String cacheKey = dbControl.getUniqueKey();
            if (cacheHandler.isCached(cacheKey)) {
                return cacheHandler.get(cacheKey);
            }
            T data = getBean(beanClass);
            cacheHandler.set(cacheKey, data);
            return data;
        } finally {
            autoClose();
        }
    }

    /**
     * 使用缓存的数据取回Bean列表
     * @param <T>   返回值列表单元数据类型
     * @param cacheHandler  缓存处理器
     * @param beanClass     返回值列表单元类
     * @return  无结果时返回 null
     * @throws SQLException
     * @throws DAOBindBeanException
     * @see #getBeanList(java.lang.Class) 
     */
    public <T> List<T> cacheBeanList(CacheHandler<List<T>> cacheHandler, Class<T> beanClass) //
            throws SQLException, DAOBindBeanException {
        try {
            String cacheKey = dbControl.getUniqueKey();
            if (cacheHandler.isCached(cacheKey)) {
                return cacheHandler.get(cacheKey);
            }
            List<T> data = getBeanList(beanClass);
            cacheHandler.set(cacheKey, data);
            return data;
        } finally {
            autoClose();
        }
    }

    /**
     * 使用缓存的数据取回信息列表
     * @param cacheHandler  缓存处理器
     * @return  无结果时返回 null
     * @throws SQLException
     * @throws DAOBindBeanException
     * @see #getMapList() 
     */
    public List<Map<String, Object>> cacheMapList(CacheHandler<List<Map<String, Object>>> cacheHandler) //
            throws SQLException, DAOBindBeanException {
        try {
            String cacheKey = dbControl.getUniqueKey();
            if (cacheHandler.isCached(cacheKey)) {
                return cacheHandler.get(cacheKey);
            }
            List<Map<String, Object>> data = getMapList();
            cacheHandler.set(cacheKey, data);
            return data;
        } finally {
            autoClose();
        }
    }

    /**
     * 使用缓存的数据取回信息列表
     * @param cacheHandler  缓存处理器
     * @return  无结果时返回 null
     * @throws SQLException
     * @throws DAOBindBeanException
     * @see #getMapList()
     */
    public Map<String, Object> cacheMap(CacheHandler<Map<String, Object>> cacheHandler) //
            throws SQLException, DAOBindBeanException {
        try {
            String cacheKey = dbControl.getUniqueKey();
            if (cacheHandler.isCached(cacheKey)) {
                return cacheHandler.get(cacheKey);
            }
            Map<String, Object> data = getMap();
            cacheHandler.set(cacheKey, data);
            return data;
        } finally {
            autoClose();
        }
    }

    /**
     * 取回单一 Long 数据(第一个字段)
     * @return 查询结果第一条第一个字段,并强制转换为 Long
     * @throws SQLException
     */
    public long getLong() throws SQLException {
        long result = 0L;
        try {
            ResultSet rs = dbControl.executeQuery();
            if (rs.next()) {
                result = rs.getLong(1);
            }
        } finally {
            autoClose();
        }
        return result;
    }

    /**
     * 取回单一 int 数据(第一个字段)
     * @return  查询结果第一条第一个字段 
     * @throws SQLException
     */
    public int getInt() throws SQLException {
        /**
         * getObject 会取回 count() 为 Long 无法直接转换为 Integer
         */
        int result = 0;
        try {
            ResultSet rs = dbControl.executeQuery();
            if (rs.next()) {
                result = rs.getInt(1);
            }
        } finally {
            autoClose();
        }
        return result;
    }

    /**
     * 取回单一 String 数据(第一个字段)
     * @return 查询结果第一条第一个字段,并强制转换为 String
     * @throws SQLException
     */
    public String getString() throws SQLException {
        return exec(String.class);
    }
    /**
     * 是否已关闭连接
     */
    private boolean isClosed = false;

    /**
     * 自动关闭连接
     * @throws SQLException
     */
    private void autoClose() throws SQLException {
        if (autoClose && !isClosed) {
            close();
            isClosed = true;
        }
    }

    /**
     * 释放 PreparedStatement 对象
     * @throws SQLException
     */
    public void closePreparedStatement() throws SQLException {
        if (!dbControl.isPreparedStatement()) {
            dbControl.closePreparedStatement();
        }
    }

    /**
     * 关闭连接
     * @throws SQLException
     */
    public void close() throws SQLException {
        if (!dbControl.isClosed()) {
            dbControl.close();
        }
    }

    /**
     * 请求数据操作接口
     * @param <T>   返回值列表单元数据类型
     */
    public static interface ListHandler<T> {

        /**
         * 结果集处理器
         * @param rs    结果集（单行）
         * @param index 结果索引
         * @return  数据列表
         * @throws SQLException
         */
        public T handle(ResultSet rs, int index) throws SQLException;
    }

    /**
     * 单行数据请求数据操作接口
     * @param <T>   返回值数据类型
     */
    public static interface ObjectHandler<T> {

        /**
         * 结果集处理器
         * @param rs 结果集（第一行）
         * @return  单行数据填充的数据对象
         * @throws SQLException
         */
        public T handle(ResultSet rs) throws SQLException;
    }

    /**
     * 遍历数据操作接口
     */
    public static interface TraverseHandler {

        /**
         * 结果集处理器
         * @param rs 结果集（单行）
         * @throws SQLException
         */
        public void handle(ResultSet rs) throws SQLException;
    }

    /**
     * 请求数据操作接口
     * @param <T> 返回值数据类型
     */
    public static interface CacheHandler<T> {

        /**
         * 检查是否已缓存
         * @param uniqueKey 查询/结果 标识
         * @return  是否缓存
         * @throws SQLException
         */
        public boolean isCached(String uniqueKey) throws SQLException;

        /**
         * 获取缓存数据
         * @param uniqueKey 查询/结果 标识
         * @return 缓存数据
         * @throws SQLException
         */
        public T get(String uniqueKey) throws SQLException;

        /**
         * 缓存数据
         * @param uniqueKey 查询/结果 标识
         * @param data 需要缓存的数据
         * @throws SQLException
         */
        public void set(String uniqueKey, T data) throws SQLException;
    }

    /**
     * 资源释放
     * @throws SQLException
     */
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        if (dbControl != null) {
            if (!dbControl.isClosed()) {
                dbControl.close();
            }
        }

    }
}
