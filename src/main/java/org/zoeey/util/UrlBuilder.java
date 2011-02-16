/*
 * MoXie (SysTem128@GMail.Com) 2009-2-1 15:59:59
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.zoeey.common.ZObject;
import org.zoeey.constant.EnvConstants;

/**
 * <pre>
 * URL 请求字符串生成器
 * php: string http_build_query ( array $formdata [, string $numeric_prefix ] )
 * </pre>
 * @author MoXie(SysTem128@GMail.Com)
 */
public class UrlBuilder {

    /**
     * 锁定创建
     */
    private UrlBuilder() {
    }

    /**
     * 生成 URL-encode 之后的请求字符串
     *  
     * <pre>
     * from: {title:"title",author:"MoXie"}
     * to:  &title=title&author=MoXie
     * 注意：生成的请求字符串以&amp;开头
     *  </pre>
     * @param paramMap
     * @return
     */
    public static String build(Map<String, String> paramMap) {
        return build(paramMap.entrySet(), EnvConstants.CHARSET);
    }

    /**
     * 生成 URL-encode 之后的请求字符串
     *
     * <pre>
     * from: {title:"title",author:"MoXie"}
     * to:  &title=title&author=MoXie
     * 注意：生成的请求字符串以&amp;开头
     *  </pre>
     * @param entrySet 
     * @param charset
     * @return
     */
    public static String build(Collection<Entry<String, String>> entrySet, String charset) {
        StringBuilder urlBuilder = new StringBuilder();
        String key;
        String val;
        for (Entry<String, String> entry : entrySet) {
            key = ZObject.conv(entry.getKey()).toString();
            val = ZObject.conv(entry.getValue()).toString();
            urlBuilder.append('&');
            urlBuilder.append(UrlHelper.encode(key, charset));
            urlBuilder.append('=');
            urlBuilder.append(UrlHelper.encode(val, charset));
        }
        return urlBuilder.toString();
    }

    /**
     * 生成 URL-encode 之后的请求字符串
     * 并排除某些键
     * <pre>
     * {title:"title",author:"MoXie"}
     * except = {"title"}
     * &author=MoXie
     * </pre>
     * @param entrySet
     * @param excepts
     * @return
     */
    public static String build(Collection<Entry<String, String>> entrySet, String[] excepts) {
        return build(entrySet, excepts, EnvConstants.CHARSET);
    }

    /**
     * 生成 URL-encode 之后的请求字符串
     * 并排除某些键
     * <pre>
     * {title:"title",author:"MoXie"}
     * except = {"title"} 
     * &author=MoXie
     * </pre>
     * @param entrySet
     * @param excepts
     * @param charset
     * @return
     */
    public static String build(Collection<Entry<String, String>> entrySet, String[] excepts, String charset) {
        List<Entry<String, String>> _collection = new ArrayList<Entry<String, String>>(entrySet.size());
        for (Entry<String, String> entry : entrySet) {
            if (!ArrayHelper.inArray(excepts, entry.getKey())) {
                _collection.add(entry);
            }
        }
        return build(_collection, charset);
    }

    /**
     * <pre>
     * 生成 URL-encode 之后的请求字符串
     * 为了便于接收方解析需要有字符串前缀，请勿使用数字作为前缀。
     *
     * ["title","MoXie"]
     * numeric_prefix = params_
     * &params_0=title&params_1=MoXie
     * </pre>
     * @see #build(java.lang.String, java.util.List, java.lang.String)
     * @param params
     * @param numeric_prefix
     * @return
     */
    public static String buildList(Collection<String> params, String numeric_prefix) {
        return buildList(params, numeric_prefix, EnvConstants.CHARSET);
    }

    /**
     * <pre>
     * 生成 URL-encode 之后的请求字符串
     * 为了便于接收方解析需要有字符串前缀，请勿使用数字作为前缀。
     * 
     * ["title","MoXie"]
     * numeric_prefix = params_
     * &params_0=title&params_1=MoXie
     * </pre>
     * @see #build(java.lang.String, java.util.List, java.lang.String)
     * @param paramList
     * @param numeric_prefix
     * @param charset
     * @return
     */
    public static String buildList(Collection<String> paramList, String numeric_prefix, String charset) {
        StringBuilder urlBuilder = new StringBuilder();

        numeric_prefix = UrlHelper.encode(numeric_prefix, charset);
        String val = null;
        int i = 0;
        for (String obj : paramList) {
            val = ZObject.conv(obj).toString();
            urlBuilder.append('&');
            urlBuilder.append(numeric_prefix);
            urlBuilder.append(i++);
            urlBuilder.append('=');
            urlBuilder.append(UrlHelper.encode(val, charset));
        }
        return urlBuilder.toString();
    }

    /**
     * 自定义分隔符
     * 注意单元为空或长度为零时仍旧保留其位置
     * <pre>
     * build(["title","MoXie"],"/",false) ==  "title/MoXie"
     * build(["title","MoXie"],"/",true) ==  "/title/MoXie"
     * build({"title,"MoXie",null},"/",true) ==  "/title/MoXie/"
     * </pre>
     * @param params 值列表
     * @param separater
     * @param isSepAsPrefix  是否将分隔符作为前缀
     * @return
     */
    public static String buildList(Collection<String> params, String separater, boolean isSepAsPrefix) {
        return buildList(params, separater, isSepAsPrefix, EnvConstants.CHARSET);
    }

    /**
     * 自定义分隔符
     * 注意单元为空或长度为零时仍旧保留其位置
     * <pre>
     * build(["title","MoXie"],"/",false) ==  "title/MoXie"
     * build(["title","MoXie"],"/",true) ==  "/title/MoXie"
     * build({"title,"MoXie",null},"/",true) ==  "/title/MoXie/"
     * </pre>
     * @param params 值列表
     * @param separater
     * @param isSepAsPrefix  是否将分隔符作为前缀
     * @param charset
     * @return
     */
    public static String buildList(Collection<String> params, String separater, boolean isSepAsPrefix, String charset) {
        StringBuilder urlBuilder = new StringBuilder();
        String val = null;
        int i = 0;
        for (Object obj : params) {
            if (i++ == 0) {
                if (isSepAsPrefix) {
                    urlBuilder.append(separater);
                }
            } else {
                urlBuilder.append(separater);
            }
            val = ZObject.conv(obj).toString();
            urlBuilder.append(UrlHelper.encode(val, charset));
        }
        return urlBuilder.toString();
    }

    /**
     * 剔除为null的值
     * @param paramMap
     * @return
     */
    public static Map<String, String> clearParams(Map<String, String> paramMap) {
        Map<String, String> _params = new HashMap<String, String>(paramMap);
        for (Entry<String, String> entry : paramMap.entrySet()) {
            if (entry.getValue() == null) {
                _params.remove(entry.getKey());
            }
        }
        return _params;
    }

    /**
     * 剔除为null的值
     * @param paramList
     * @return
     */
    public static Collection<String> clearParams(Collection<String> paramList) {
        Collection<String> _params = new ArrayList<String>(paramList);
        for (String str : paramList) {
            if (str == null) {
                _params.remove(str);
            }
        }
        return _params;
    }
}
