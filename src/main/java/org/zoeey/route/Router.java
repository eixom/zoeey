/*
 * MoXie (SysTem128@GMail.Com) 2009-2-1 15:59:59
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.route;

import java.util.ArrayList;
import org.zoeey.dispatch.exceptions.RouterConnectException;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.zoeey.util.ArrayHelper;
import org.zoeey.util.StringHelper;

/**
 * 路由参数标准分析器
 * 格式规则较为严谨，使用 / 分割不同的参数 。默认多值参数使用逗号分隔。
 * ex.
 *  Router router = new Router();
 *                router.add("/:action")//
 *                // 正则匹配已出现的变量
 *                .addRegexp("action", "(list)", "/:page/:label")//
 *                // 已出现变量在某集合内
 *                .addArray("action", new String[]{"view"}, "/:id/:title")//
 *                .addArray("action", new String[]{"edit", "delete"}, "/:id")//
 *                // 参数个数是某值
 *                .addParamCount(5, "/:id/:page/:label/:highlight")//
 *                // 全QueryString正则匹配
 *                .addAllRegexp("/track/([^/]+)/?$", "/:trackSn");
 * 
 * @author MoXie(SysTem128@GMail.Com)
 */
public class Router {

    /**
     * 默认分隔符
     */
    public static final char DEFAULT_SEP = '/';
    /**
     * 规则列表
     */
    private List<RouterRule> ruleList;
    /**
     * 间隔符
     */
    private char sep = DEFAULT_SEP;

    /**
     * 参数分析器
     */
    public Router() {
        ruleList = new ArrayList<RouterRule>();
    }

    /**
     * 新增规则
     * @param ruleItem  路由规则项
     * @return
     */
    public Router add(RouterRule ruleItem) {
        ruleList.add(ruleItem);
        return this;
    }

    /**
     * 普通规则
     * .add("/:action")
     * @param pattern  规则描述
     * @return
     */
    public Router add(String pattern) {
        ruleList.add(new RouterRule(pattern, sep));
        return this;
    }

    /**
     * 正则匹配已出现的变量
     * .addRegexp("action", "(list)", "/:page/:label")
     * @param varName 变量名称
     * @param regexp 正则表达式
     * @param pattern  规则描述
     * @return
     */
    public Router addRegexp(String varName, String regexp, String pattern) {
        ruleList.add(new RouterRule(varName, regexp, pattern, sep));
        return this;
    }

    /**
     * 已出现变量在某集合内
     * .addArray("action", new String[]{"view"}, "/:id/:title")//
     * @param varName   变量名称
     * @param strs  字符串集
     * @param pattern  规则描述
     * @return
     */
    public Router addArray(String varName, String[] strs, String pattern) {
        ruleList.add(new RouterRule(varName, strs, pattern, sep));
        return this;
    }

    /**
     * 参数个数是某值
     * .add(5, "/:id/:page/:label/:highlight")
     * @param paramCount  参数个数
     * @param pattern  规则描述
     * @return
     */
    public Router addParamCount(int paramCount, String pattern) {
        ruleList.add(new RouterRule(paramCount, pattern, sep));
        return this;
    }

    /**
     * 全QueryString正则匹配
     *  addAllRegexp("track/([^/]+)/?$", new String[]{"trackSn"});
     * 注意：queryString 的起始“/”会被剔除掉。
     * @param regexp 正则表达式 
     * @param keys  键列表
     * @return
     */
    public Router addAllRegexp(String regexp, String[] keys) {
        ruleList.add(new RouterRule(regexp, keys));
        return this;
    }

    /**
     * 复制上一规则，追加规则描述
     * 注意：全QueryString正则匹配不可追加
     * @param pattern  规则描述
     * @param sep  间隔符。此方法常用于追加不定参数，设定此参数方便切换间隔符，
     * @return
     */
    public Router append(String pattern, char sep) {
        int size = ruleList.size();
        if (size > 0) {
            RouterRule lastRule = null;
            RouterRule rule = null;
            lastRule = ruleList.get(size - 1);
            if (lastRule != null) {
                switch (lastRule.getType()) {
                    case TYPE_NORMAL:
                        rule = new RouterRule(pattern, sep);
                        break;
                    case TYPE_VARREGEXP:
                        rule = new RouterRule(lastRule.getVarName(), lastRule.getRegexp(), pattern, sep);
                        break;
                    case TYPE_VARINSET:
                        rule = new RouterRule(lastRule.getVarName(), lastRule.getStrs(), pattern, sep);
                        break;
                    case TYPE_VARCOUNT:
                        rule = new RouterRule(lastRule.getParamCount(), pattern, sep);
                        break;
                    /*
                    case TYPE_ALLREGEXP:
                    rule = new RouterRule(rule.getRegexp(), rule.getStrs());
                    break;
                     */
                }
                if (rule != null) {
                    ruleList.add(rule);
                }
            }
        }
        return this;
    }

    /**
     * 切换间隔符，对其后添加的规则有效
     * @param sep   间隔符
     * @return
     */
    public Router shiftSep(char sep) {
        this.sep = sep;
        return this;
    }

    /**
     * 匹配上一规则，则终止。
     * @return
     */
    public Router end() {
        int size = ruleList.size();
        if (size > 0) {
            RouterRule rule = ruleList.get(size - 1);
            rule.end();
        }
        return this;
    }

    /**
     * 分析普通规则
     * @param paramList
     * @param varName
     * @param value
     * @throws RouterConnectException
     */
    private void parseNormalRule(List<ParamEntry> paramList, String varName, String value) {
        if (varName == null) {
            return;
        }
        int len = varName.length();
        int size = 0;
        if (len > 2) {
            char left = varName.charAt(len - 3);
            char right = varName.charAt(len - 1);
            char _sep = varName.charAt(len - 2);

            // name[,] --> name
            // name{-} --> name

            // name[,]
            if (len > 3 && left == '[' && right == ']') {

                varName = varName.substring(0, len - 3);
                paramList.add(new ParamEntry(varName, StringHelper.split(value, _sep)));
                return;
            } else if (left == '{' && right == '}') {

                List<ParamEntry> valList = new ArrayList<ParamEntry>();
                value = StringHelper.trim(value, new char[]{_sep});
                String[] vals = StringHelper.split(value, _sep);
                size = vals.length;
                int i = 0;
                for (; i < size; i++) {
                    if ((i + 1) % 2 == 0) {
                        valList.add(new ParamEntry(vals[i - 1], vals[i]));
                    }
                }
                if (size % 2 == 1) {
                    valList.add(new ParamEntry(vals[i - 1], null));
                }
                // name{-}
                if (len > 3) {
                    varName = varName.substring(0, len - 3);
                    paramList.add(new ParamEntry(varName, valList));
                } else {
                    paramList.addAll(valList);
                }
                return;
            }
        }
        paramList.add(new ParamEntry(varName, value));
    }

    /**
     * 获取值处理器
     * @param query   请求字符串
     * @return
     */
    public ParamHandler getHandler(Query query) {
        return new ParamHandler(parse(query.getQueryString()));
    }

    /**
     * 获取值处理器
     * @param queryString   请求字符串
     * @return
     */
    public ParamHandler getHandler(String queryString) {
        return new ParamHandler(parse(queryString));
    }

    /**
     * 解析参数
     * @param query 请求项
     * @return
     * @throws RouterConnectException 
     */
    public List<ParamEntry> parse(Query query) {
        return parse(query.getQueryString());
    }

    /**
     * 解析参数
     * @param queryString 请求字符串
     * @return
     * @throws RouterConnectException
     */
    public List<ParamEntry> parse(String queryString) {

        /**
         * 整理资源
         */
        List<ParamEntry> paramList = new ArrayList<ParamEntry>();
        if (queryString != null && queryString.length() > 0 //
                && ruleList != null && ruleList.size() > 0) {
            if (queryString.startsWith("/")) {
                queryString = queryString.substring(1);
            }
            StringTokenizer queryTokenizer = new StringTokenizer(queryString);
            StringTokenizer tempQueryTokenizer;
            StringTokenizer varNameTokenizer;
            boolean hasMoreValue;
            String refer_val = null;
            String sepStr;
            char _sep;
            char lastSep = 0;
            boolean isParse;
            char[] varPrefix = new char[]{':', '/', '/'};
            int matchCount = 0;
            ruleList_for:
            for (RouterRule ruleItem : ruleList) {
                _sep = ruleItem.getSep();
                sepStr = String.valueOf(_sep);
                varPrefix[1] = _sep;
                varPrefix[2] = lastSep;
                isParse = false;
                /**
                 * 装载规则
                 */
                switch (ruleItem.getType()) {
                    case TYPE_NORMAL:
                        isParse = true;
                        break;
                    case TYPE_VARREGEXP:
                        refer_val = getLastValue(paramList, ruleItem.getVarName());
                        if (refer_val != null && refer_val.matches(ruleItem.getRegexp())) {
                            isParse = true;
                        }
                        break;
                    case TYPE_VARINSET:
                        refer_val = getLastValue(paramList, ruleItem.getVarName());

                        if (refer_val != null && ArrayHelper.inArray(ruleItem.getStrs(), refer_val)) {
                            isParse = true;
                        }
                        break;
                    case TYPE_VARCOUNT:
                        tempQueryTokenizer = new StringTokenizer(queryString, sepStr);
                        int paramsSize = tempQueryTokenizer.countTokens();
                        tempQueryTokenizer = null;
                        if (paramsSize == ruleItem.getParamCount()) {
                            isParse = true;
                        }

                        break;
                    case TYPE_ALLREGEXP:
                        Pattern pattern = Pattern.compile(ruleItem.getRegexp());
                        Matcher matcher = pattern.matcher(queryString);
                        String keys[] = ruleItem.getStrs();
                        if (!matcher.matches() //
                                || (matchCount = matcher.groupCount()) != keys.length) {
                            break;
                        }
                        matcher.reset();
                        if (matcher.find()) {
                            for (int j = 0; j < matchCount; j++) {
                                paramList.add(new ParamEntry(keys[j], matcher.group(j + 1)));
                            }
                        }
                        break;
                }

                if (isParse) {
                    varNameTokenizer = new StringTokenizer(ruleItem.getPattern());
                    while (varNameTokenizer.hasMoreTokens() //
                            && (hasMoreValue = queryTokenizer.hasMoreTokens())) {
                        if (!hasMoreValue) {
                            break ruleList_for;
                        }
                        parseNormalRule(paramList //
                                , StringHelper.ltrim(varNameTokenizer.nextToken(sepStr), varPrefix, 1)//
                                , StringHelper.ltrim(queryTokenizer.nextToken(sepStr), varPrefix, 1)//
                                );
                    }

                    if (ruleItem.isIsEnd()) {
                        break ruleList_for;
                    }
                }
                lastSep = _sep;
            }
        }
        return paramList;
    }

    private String getLastValue(List<ParamEntry> paramList, String key) {
        Object value = null;
        for (ParamEntry param : paramList) {
            if (param.getKey().equals(key)) {
                value = param.getValue();
            }
        }
        if (value instanceof String) {
            return (String) value;
        }
        return null;
    }
}
