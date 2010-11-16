/*
 * MoXie (SysTem128@GMail.Com) 2009-2-1 15:59:59
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.route;

import org.zoeey.dispatch.PublishEntry;
import org.zoeey.dispatch.PublishAble;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.zoeey.util.StringHelper;

/**
 * 路由请求项
 * @author MoXie(SysTem128@GMail.Com)
 */
public class Query {

    /**
     * 请求对象
     */
    private HttpServletRequest request;
    /**
     * 应答对象
     */
    private HttpServletResponse response;
    /**
     * 路由条目
     */
    private PublishEntry entry;
    /**
     * 请求连接
     */
    private String queryURI;

    /**
     * Router 请求项
     * @param entry
     * @param queryString
     */
    public Query(PublishEntry entry, String queryString) {
        this.entry = entry;
        this.queryURI = queryString;
    }

    /**
     * 载入请求对象
     * @param request   请求对象
     */
    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    /**
     * 载入应答对象
     * @param response  应答对象
     */
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    /**
     * 获取路由条目
     * @return
     */
    public PublishEntry getEntry() {
        return entry;
    }

    /**
     * 获取发布对象
     * @return
     */
    public PublishAble getPublish() {
        return entry.getPublish();
    }

    /**
     * 获取匹配模式
     * @return
     */
    public String getPattern() {
        return entry.getPattern();
    }

    /**
     * <pre>
     * 获取请求链接
     * 例如：
     *      定义：/{ContextPath}/{module}/{action}/{id}?{key}={value}
     *      返回部分为 /{module}/{action}/{id}
     * </pre>
     * @return
     */
    public String getURI() {
        return queryURI;
    }

    /**
     * <pre>
     * 获取查询字符串
     * 例如：
     *      定义：/{ContextPath}/{pattern}/{action}/{id}?{key}={value}
     *      匹配模式： /{pattern}
     *      返回部分为 /{action}/{id}
     * </pre>
     * @return
     */
    public String getQueryString() {
        return StringHelper.subString(queryURI, entry.getPattern().length(), -1);
    }

    /**
     * 请求对象
     * @return  请求对象
     */
    public HttpServletRequest getRequest() {
        return request;
    }

    /**
     * 应答对象
     * @return  应答对象
     */
    public HttpServletResponse getResponse() {
        return response;
    }
}
