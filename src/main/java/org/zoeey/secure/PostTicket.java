/*
 * MoXie (SysTem128@GMail.Com) 2009-6-11 11:46:07
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.secure;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.zoeey.util.RandomHelper;

/**
 * <pre>
 * 会话签名，避免同一会话多次提交。
 * 支持多提交并行
 * </pre>
 * @author MoXie(SysTem128@GMail.Com)
 */
public class PostTicket {

    /**
     * 锁定创建
     */
    private PostTicket() {
    }

    /**
     * 建立新的会话标记
     * @param key   存储字段名
     * @param request   请求对象
     * @return
     */
    public static String create(String key, HttpServletRequest request) {
        return create(key, request.getSession(true));
    }

    /**
     * 建立新的会话标记
     * @param key   存储字段名
     * @param session   SESSION对象
     * @return
     */
    public static String create(String key, HttpSession session) {
        if (session == null) {
            return null;
        }
        @SuppressWarnings("unchecked")
        List<String> tokenList = (List<String>) session.getAttribute(key);
        if (tokenList == null) {
            tokenList = new ArrayList<String>();
        }
        String token = null;
        while (token == null || tokenList.contains(token)) {
            token = RandomHelper.toString(15, 25)//
                    .concat(RandomHelper.toTimeString(20));
        }
        tokenList.add(token);
        session.setAttribute(key, tokenList);
        return token;
    }

    /**
     * 验证会话标记
     * @param ticket    认证票据
     * @param key 存储字段名
     * @param request
     * @return
     */
    public static boolean vali(String ticket, String key, HttpServletRequest request) {
        return vali(ticket, key, request.getSession(false));
    }

    /**
     * 验证会话标记
     * @param ticket    认证票据
     * @param key   存储字段名
     * @param session   SESSION对象
     * @return
     */
    public static boolean vali(String ticket, String key, HttpSession session) {
        if (session == null) {
            return false;
        }
        @SuppressWarnings("unchecked")
        List<String> tokenList = (List<String>) session.getAttribute(key);

        if (tokenList == null) {
            return false;
        }
        if (!tokenList.contains(ticket)) {
            return false;
        }
        if (tokenList.contains(ticket)) {
            tokenList.remove(ticket);
            return true;
        }
        return false;
    }
}
