/*
 * MoXie (SysTem128@GMail.Com) 2009-6-20 17:10:02
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.plugins.zsk;

import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.servletunit.InvocationContext;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import static junit.framework.Assert.*;
import org.zoeey.util.JsonHelper;
import org.zoeey.util.QueryStringHelper;
import org.zoeey.util.UrlBuilder;
import org.zoeey.util.UrlHelper;

/**
 *
 * @author MoXie(SysTem128@GMail.Com)
 */
public class SimpleSSOSample {

    /**
     *
     * @param args
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws IOException {
        ServletRunner sr = new ServletRunner();
        sr.registerServlet("Login", "Login");
        ServletUnitClient sc = sr.newClient();
        /**
         * client
         */
        PostMethodWebRequest postRequest = new PostMethodWebRequest("http://zoeey.org/", true);
        postRequest.setParameter("name", "MoXie");
        postRequest.setParameter("password", "moxie's password");
        // ==============
        InvocationContext ic = sc.newInvocation(postRequest);
        /**
         * web server
         */
        HttpServletRequest request = ic.getRequest();
        SessionConveyer sessionConveyer = new SessionConveyer();
        // 会话编号不一定使用 Http Session id,仅是一个不错的选择。
        sessionConveyer.setSessionId(request.getSession().getId());
        Map<String, String> params = new HashMap<String, String>();
        params.put("name", request.getParameter("name"));
        params.put("password", request.getParameter("password"));
        sessionConveyer.setData(UrlBuilder.build(params)); // 也可以使用Json
        // 设定两个Web服务器和SSO服务器内部保留的密匙，用于上下传递时的内容加密
        String ticket = sessionConveyer.getTicket("webserver's key");
        assertEquals(ticket, "HkcLV1gEVgYPBlQHVwcGBFUFAF4FBVAAAAELCwYPAyJ1fT"
                + "NsYTFmJzEtBFQQBwVGDQ5DCAcEXFELGgF9F3x0aSslfyY9NTB/fg8=");
        /**
         * SSO server
         * ticket 的传输可以直接使用location跳转，P3P共享或者Js跨域hack。
         */
        sessionConveyer = new SessionConveyer();
        // 分解ticket内容，并进行验证
        sessionConveyer.parse(ticket, "webserver's key");
        if (sessionConveyer.vali()) {
            // 验证成功，并进行用户信息填充返回。
            params = new HashMap<String, String>();
            params = QueryStringHelper.toMap(sessionConveyer.getData());
            params.remove("password");
            params.put("email", "SysTem128@GMail.Com");
            //
            sessionConveyer.setData(JsonHelper.encode(params));
        } else {
            // 验证失败，通告web服务器失败原因。
            sessionConveyer.setData("false");
        }
        ticket = sessionConveyer.getTicket("ssoserver's key");
        assertEquals(ticket, "EEdZXlwKVFJSAlNXUlZWAAEFVA4HUlAPDgQAAFxRUSItc"
                + "DI0bnQ6bDx0CxMAAUFaAhZdcBcOCVRSXlBRQX12bXQWfGxlGy17Gl4oeiQ"
                + "8emRmA08xLlgTdVddHw4qansDeWdqOyU6PTlwBhUCIkALCh8DVhUPeQ==");
        /**
         * web server
         */
        sessionConveyer.parse(ticket, "ssoserver's key");
        if (sessionConveyer.vali(request.getSession().getId())) {
            String data = sessionConveyer.getData();
            if ("false".equals(data)) {
                fail("反馈失败");
            }
            params = (Map<String, String>) JsonHelper.decode(sessionConveyer.getData());
            assertEquals(params.get("email"), "SysTem128@GMail.Com");
        } else {
            fail("验证失败");
        }
    }
}
