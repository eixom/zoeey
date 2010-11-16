/*
 * MoXie (SysTem128@GMail.Com) 2009-2-1 15:59:59
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.common.container;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet资源
 * @author MoXie(SysTem128@GMail.Com)
 */
public class ServletResource {

    /**
     * 附带属性
     */
    private Map<String, Object> properties = null;
    /**
     * Servlet 对象
     */
    private HttpServlet servlet = null;
    /**
     * Servlet context
     */
    private ServletContext context = null;
    /**
     * Servlet config
     */
    private ServletConfig config = null;
    /**
     * Servlet  request 对象
     */
    private HttpServletRequest request = null;
    /**
     * Servlet response
     */
    private HttpServletResponse response = null;

    /**
     * Servlet资源
     */
    public ServletResource() {
        properties = new HashMap<String, Object>();
    }

    /**
     * 获取 Servlet 对象
     * @return
     */
    public HttpServlet getHttpServlet() {
        return servlet;
    }

    /**
     * 设定 Servlet 对象
     * @param servlet
     */
    public void setHttpServlet(HttpServlet servlet) {
        if (getConfig() == null) {
            setContext(servlet.getServletContext());

        }
        if (getConfig() == null) {
            setConfig(getConfig());
        }
        this.servlet = servlet;
    }

    /**
     * 获取附加属性
     * @param key   键
     * @return      值
     */
    public Object get(String key) {
        return properties.get(key);
    }

    /**
     * 设置附加属性
     * @param key   键
     * @param value 值
     */
    public void set(String key, Object value) {
        this.properties.put(key, value);
    }

    /**
     * 获取 Config 对象
     * @return Config 对象
     */
    public ServletConfig getConfig() {
        return config;
    }

    /**
     * 设置 Config 对象
     * @param config  Config 对象
     */
    public void setConfig(ServletConfig config) {
        this.config = config;
    }

    /**
     * 获取 Context 对象
     * @return Context 对象
     */
    public ServletContext getContext() {
        return context;
    }

    /**
     * 设置 Context 对象
     * @param context  Context 对象
     */
    public void setContext(ServletContext context) {
        this.context = context;
    }

    /**
     * 获取 Request 对象
     * @return   Request 对象
     */
    public HttpServletRequest getRequest() {
        return request;
    }

    /**
     * 设置 Request 对象
     * @param request  Request 对象
     */
    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    /**
     * 获取 Response 对象
     * @return Response 对象
     */
    public HttpServletResponse getResponse() {
        return response;
    }

    /**
     * 设置 Response 对象
     * @param response  Response 对象
     */
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }
}
