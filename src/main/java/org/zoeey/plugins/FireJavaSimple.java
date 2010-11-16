/*
 * MoXie (SysTem128@GMail.Com) 2009-4-21 0:39:54
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.plugins;

import javax.servlet.http.HttpServletResponse;
import org.zoeey.common.Version;
import org.zoeey.common.container.ObjectHolder;
import org.zoeey.util.JsonHelper;

/**
 * <pre>
 * 将obj发送至FireBug控制台。同<a href="http://www.getfirebug.com/">FirePHP</a>的 FB::log 。
 * <a href="http://www.getfirebug.com/">FirePHP</a>
 * <a href="http://www.firephp.org/">FireBug</a>
 *</pre>
 * @author MoXie(SysTem128@GMail.Com)
 */
public class FireJavaSimple {

    private static final String version = "0.2.1";

    private FireJavaSimple() {
    }

    /**
     * LOG 信息
     * @param response
     * @param obj
     */
    public static final void log(HttpServletResponse response, Object obj) {
        log(response, obj, "LOG");
    }

    /**
     * WARN 信息
     * @param response
     * @param obj
     */
    public static final void warn(HttpServletResponse response, Object obj) {
        log(response, obj, "WARN");
    }

    /**
     * ERROR 信息
     * @param response
     * @param obj
     */
    public static final void error(HttpServletResponse response, Object obj) {
        log(response, obj, "ERROR");
    }

    /**
     * INFO 信息
     * @param response
     * @param obj
     */
    public static final void info(HttpServletResponse response, Object obj) {
        log(response, obj, "INFO");
    }

    /**
     *
     * <pre>
     * 将obj发送至FireBug控制台。同<a href="http://www.getfirebug.com/">FirePHP</a>的 FB::log 。
     * <a href="http://www.getfirebug.com/">FirePHP</a>
     * <a href="http://www.firephp.org/">FireBug</a>
     *</pre>
     * @param response
     * @param obj
     */
    private static final void log(HttpServletResponse response, Object obj, String type) {
        int i = ObjectHolder.<Integer>get("FireJavaSimple_index", 0);
        i++;
        ObjectHolder.put("FireJavaSimple_index", i);
        if (i == 1) {
            response.setHeader("X-Powered-By", "Zoeey/" + Version.VERSION);
            response.setHeader("X-Wf-Protocol-1", "http://meta.wildfirehq.org/Protocol/JsonStream/0.2");
            response.setHeader("X-Wf-1-Plugin-1", "http://meta.firephp.org/Wildfire/Plugin/FirePHP/Library-FirePHPCore/" + version);
            response.setHeader("X-Wf-1-Structure-1", "http://meta.firephp.org/Wildfire/Structure/FirePHP/FirebugConsole/0.1");
        }
        StringBuilder msgBuilder = new StringBuilder();
        msgBuilder.append("[{\"Type\":\"");
        msgBuilder.append(type);
        msgBuilder.append("\"},");
        msgBuilder.append(JsonHelper.encode(obj));
        msgBuilder.append(']');
        response.setHeader("X-Wf-1-1-1-" + i, String.format("%d|%s|", msgBuilder.length(),msgBuilder.toString()));
        response.setIntHeader("X-Wf-1-Index", i);
    }
}
