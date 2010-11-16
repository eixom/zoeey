/*
 * MoXie (SysTem128@GMail.Com) 2009-3-10 17:58:06
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.zoeey.constant.EnvConstants;

/**
 * Web页面 访问工具
 * @author MoXie(SysTem128@GMail.Com)
 */
public class Snoopy {

    /**
     * 链接
     */
    private URL url;
    /**
     * 请求头信息
     */
    private RequestHeaders headers = null;
    /**
     * 连接信息
     */
    private HttpURLConnection conn = null;
    /**
     * 请求方式：POST
     */
    public static final String METHOD_POST = "POST";
    /**
     * 请求方式：GET
     */
    public static final String METHOD_GET = "GET";
    /**
     * 内容默认字符集
     */
    private String charset = EnvConstants.CHARSET;
    /**
     * POST请求数据
     */
    private String queryString = null;
    /**
     * 请求超时,默认30秒（30000）。
     * 单位：毫秒
     */
    private int timeout = 30000;

    /**
     *  Web页面 访问工具
     * @param url 链接。辅助工具：{@link UrlBuilder#build(java.util.Map) }
     */
    public Snoopy(URL url) {
        this.url = url;
        headers = new RequestHeaders();
        headers.setUserAgent("Zoeey/0.2");
        headers.setReferer("http://zoeey.org/");
    }

    /**
     * <pre>
     * 获取内容
     * headers:
     * referer  http://example.com
     * user-agent   Mozilla/5.0
     * {@link RequestHeaders}
     * </pre>
     * @param params Headers .<a href="http://www.faqs.org/rfcs/rfc2616">rfc2616</a>
     * @param method "GET", "POST", "HEAD", "OPTIONS", "PUT", "DELETE", "TRACE"
     * @return
     * @throws java.io.IOException
     */
    private String getContent(String method, String charset) throws IOException {
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);
            conn.setReadTimeout(timeout);
            if (headers != null) {
                for (Entry<String, String> entry : headers.getHeaders().entrySet()) {
                    conn.addRequestProperty(entry.getKey(), entry.getValue());
                }
            }
            if (METHOD_POST.equals(method) && queryString != null) {
                conn.setDoOutput(true);
                conn.getOutputStream().write(queryString.getBytes());
            }
            conn.connect();
            return TextFileHelper.read(conn.getInputStream(), charset);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    /**
     * 获取内容
     * GET 方式
     * @return
     * @throws java.io.IOException
     */
    public String doGet() throws IOException {
        return getContent(METHOD_GET, charset);
    }

    /**
     * 获取内容
     * POST 方式
     * @return
     * @throws java.io.IOException
     */
    public String doPost() throws IOException {
        return getContent(METHOD_POST, charset);
    }

    /**
     * 获取头信息
     * @return
     */
    public RequestHeaders getHeaders() {
        return headers;
    }

    /**
     * 设置请求头信息
     * @param headers
     */
    public void setHeaders(RequestHeaders headers) {
        this.headers = headers;
    }

    /**
     * 请求字符集
     * @return
     */
    public String getCharset() {
        return charset;
    }

    /**
     * 设置请求字符集
     * @param charset
     */
    public void setCharset(String charset) {
        this.charset = charset;
    }

    /**
     * 请求链接
     * @return
     */
    public URL getUrl() {
        return url;
    }

    /**
     * 设置请求链接
     * @param url
     */
    public void setUrl(URL url) {
        this.url = url;
    }

    /**
     * 获取连接信息
     * @return
     */
    public HttpURLConnection getConn() {
        return conn;
    }

    /**
     * 请求数据
     * @return
     */
    public String getQueryString() {
        return queryString;
    }

    /**
     * 设置请求数据
     * @param queryString
     */
    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    /**
     * 请求超时,默认30秒（30000）。
     * 单位：毫秒
     * @return
     */
    public int getTimeout() {
        return timeout;
    }

    /**
     * 请求超时,默认30秒（30000）。
     * 单位：毫秒
     * @param timeout
     */
    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    /**
     * Headers
     * http://www.ietf.org/rfc/rfc2068.txt
     */
    public static class RequestHeaders {

        private Map<String, String> headers;

        /**
         * Headers
         * <a href="http://www.ietf.org/rfc/rfc2068.txt">rfc2068</a>
         */
        public RequestHeaders() {
            headers = new HashMap<String, String>();
        }

        /**
         * 获取所有头信息
         * @return
         */
        public Map<String, String> getHeaders() {
            return headers;
        }

        /**
         * 接收 Accept
         * @param accept
         */
        public void setAccept(String accept) {
            headers.put("Accept", accept);
        }

        /**
         * 接收字符集 Accept-Charset
         * @param acceptCharset
         */
        public void setAcceptCharset(String acceptCharset) {
            headers.put("Accept-Charset", acceptCharset);
        }

        /**
         * 接收编码 Accept-Encoding
         * @param acceptEncoding
         */
        public void setAcceptEncoding(String acceptEncoding) {
            headers.put("Accept-Encoding", acceptEncoding);
        }

        /**
         * 接收语言 Accept-Language
         * @param acceptLanguage
         */
        public void setAcceptLanguage(String acceptLanguage) {
            headers.put("Accept-Language", acceptLanguage);
        }

        /**
         * 认证 Authorization
         * @param authorization
         */
        public void setAuthorization(String authorization) {
            headers.put("Authorization", authorization);
        }

        /**
         * 源 From
         * @param from
         */
        public void setFrom(String from) {
            headers.put("From", from);
        }

        /**
         * 主机 Host
         * @param host
         */
        public void setHost(String host) {
            headers.put("Host", host);
        }

        /**
         * 假如匹配 If-Match
         * @param ifMatch
         */
        public void setIfMatch(String ifMatch) {
            headers.put("If-Match", ifMatch);
        }

        /**
         * 假如修改 If-Modified-Since
         * @param ifModifiedSince
         */
        public void setIfModifiedSince(String ifModifiedSince) {
            headers.put("If-Modified-Since", ifModifiedSince);
        }

        /**
         * 假如不匹 If-None-Match
         * @param ifNoneMatch
         */
        public void setIfNoneMatch(String ifNoneMatch) {
            headers.put("If-None-Match", ifNoneMatch);
        }

        /**
         * 假如归类 If-Range
         * @param ifRange
         */
        public void setIfRange(String ifRange) {
            headers.put("If-Range", ifRange);
        }

        /**
         * 假如不修改 If-Unmodified-Since
         * @param ifUnmodifiedSince
         */
        public void setIfUnmodifiedSince(String ifUnmodifiedSince) {
            headers.put("If-Unmodified-Since", ifUnmodifiedSince);
        }

        /**
         * 最大转发量 Max-Forwards
         * @param maxForwards
         */
        public void setMaxForwards(String maxForwards) {
            headers.put("Max-Forwards", maxForwards);
        }

        /**
         * 代理认证 Proxy-Authorization
         * @param proxyAuthorization
         */
        public void setProxyAuthorization(String proxyAuthorization) {
            headers.put("Proxy-Authorization", proxyAuthorization);
        }

        /**
         * 范围 Range
         * @param range
         */
        public void setRange(String range) {
            headers.put("Range", range);
        }

        /**
         * 提交者 Referer
         * @param referer
         */
        public void setReferer(String referer) {
            headers.put("Referer", referer);
        }

        /**
         * 用户代理 User-Agent
         * @param userAgent
         */
        public void setUserAgent(String userAgent) {
            headers.put("User-Agent", userAgent);
        }

        /**
         * 自定义头信息
         * @param key
         * @param value
         */
        public void setHeader(String key, String value) {
            headers.put(key, value);
        }
    }
}
