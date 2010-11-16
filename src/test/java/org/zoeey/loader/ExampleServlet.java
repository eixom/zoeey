/*
 * MoXie (SysTem128@GMail.Com) 2009-7-17 9:50:10
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.loader;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * LoaderTest 使用了此类
 * @author MoXie
 */
public class ExampleServlet extends HttpServlet {

    private static final long serialVersionUID = 6505238315888036221L;

    /**
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    void setAccount(HttpServletRequest req, String account) throws ServletException {
        req.getSession().setAttribute("account", account);
    }
}
