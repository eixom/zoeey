/*
 * MoXie (SysTem128@GMail.Com) 2009-8-18 16:30:32
 * 
 * Copyright &copy; 2008-2010 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.route;

/**
 * 规则类型
 */
public enum RuleType {

    /**
     * <pre>
     * 普通规则
     * add("/:action");
     * </pre>
     */
    TYPE_NORMAL,
    /**
     * <pre>
     * 正则匹配已出现的变量
     * .addRegexp("action", "(list)", "/:page/:label");
     * </pre>
     */
    TYPE_VARREGEXP, // 
    /**
     * <pre>
     * 已出现变量在某集合内
     * .addArray("action", new String[]{"view"}, "/:id/:title");
     * </pre>
     */
    TYPE_VARINSET,
    /**
     * <pre>
     * 参数个数是某值
     * .addParamCount(5, "/:id/:page/:label/:highlight");
     * </pre>
     */
    TYPE_VARCOUNT,
    /**
     * <pre>
     * 全QueryString正则匹配
     * .addAllRegexp("/track/([^/]+)/?$", "/:trackSn");
     * </pre>
     */
    TYPE_ALLREGEXP,
}
