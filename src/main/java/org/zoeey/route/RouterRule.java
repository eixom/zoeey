/*
 * MoXie (SysTem128@GMail.Com) 2009-8-18 16:30:32
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.route;

/**
 * 路由规则
 */
public class RouterRule {

    /**
     * 普通规则
     */
    private String pattern; // add("/:action")
    /**
     * 已出现变量
     */
    private String varName;
    /**
     * 正则表达式
     * .add("action", "(list)", "/:page/:label")
     */
    private String regexp;
    /**
     * 数据集合
     * .add("action", new String[]{"view"}, "/:id/:title")
     */
    private String[] strs;
    /**
     * 参数总个数
     * .add(5, "/:id/:page/:label/:highlight")
     */
    private int paramCount;
    /**
     * 规则类型
     */
    private RuleType type;
    /**
     * 间隔符
     */
    private char sep;
    /**
     * 是否终止
     */
    private boolean isEnd;

    /**
     * <pre>
     * 普通规则
     *  add("/:action");
     * </pre>
     * @param rule  规则描述
     * 
     */
    public RouterRule(String pattern, char sep) {
        this.pattern = pattern;
        type = RuleType.TYPE_NORMAL;
        this.sep = sep;
    }

    /**
     * <pre>
     * 正则匹配已出现的变量
     * .add("action", "(list)", "/:page/:label");
     * </pre>
     * 
     * @param varName 变量名称  
     * @param regexp 正则表达式
     * @param rule  规则描述
     * 
     */
    public RouterRule(String varName, String regexp, String pattern, char sep) {
        this.varName = varName;
        this.regexp = regexp;
        this.pattern = pattern;
        this.sep = sep;
        type = RuleType.TYPE_VARREGEXP;

    }

    /**
     * <pre>
     * 已出现变量在某集合内
     * .add("action", new String[]{"view"}, "/:id/:title");
     * </pre>
     * @param varName 变量名称
     * @param strs  字符串集
     * @param rule  规则描述
     * 
     */
    public RouterRule(String varName, String[] strs, String pattern, char sep) {
        this.pattern = pattern;
        this.varName = varName;
        this.strs = strs.clone();
        this.sep = sep;
        type = RuleType.TYPE_VARINSET;

    }

    /**
     * <pre>
     * 参数个数是某值
     * .add(5, "/:id/:page/:label/:highlight");
     * </pre>
     * @param rule  规则描述
     * @param paramCount 参数个数
     * 
     */
    public RouterRule(int paramCount, String pattern, char sep) {
        this.paramCount = paramCount;
        this.pattern = pattern;
        this.sep = sep;
        type = RuleType.TYPE_VARCOUNT;

    }

    /**
     * <pre>
     * 全QueryString正则匹配
     * add("/track/([^/]+)/?$", "/:trackSn");
     * </pre>
     * @param regexp 正则表达式
     * @param keys  键列表
     * 
     */
    public RouterRule(String regexp, String[] keys) {
        this.regexp = regexp;
        this.strs = keys;
        type = RuleType.TYPE_ALLREGEXP;

    }

    public void end() {
        this.isEnd = true;
    }

    /**
     * 参数数量
     * @return
     */
    public int getParamCount() {
        return paramCount;
    }

    /**
     * 正则
     * @return
     */
    public String getRegexp() {
        return regexp;
    }

    /**
     * 规则
     * @return
     */
    public String getPattern() {
        return pattern;
    }

    /**
     * 数据集
     * @return
     */
    public String[] getStrs() {
        return strs;
    }

    /**
     * 规则类型
     * @return
     */
    public RuleType getType() {
        return type;
    }

    /**
     * 变量名
     * @return
     */
    public String getVarName() {
        return varName;
    }

    /**
     * 是否终止
     * @return
     */
    public boolean isIsEnd() {
        return isEnd;
    }

    /**
     * 获取分隔符
     * @return
     */
    public char getSep() {
        return sep;
    }
}
