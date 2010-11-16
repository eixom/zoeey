/*
 * MoXie (SysTem128@GMail.Com) 2010-6-29 15:38:36
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 命令行参数选项辅助工具
 * @author MoXie
 */
public class OptionHelper {

    /**
     * 选项与参数列表
     */
    private Map<String, List<String>> argMap = null;
    /**
     * 参数列表
     */
    private List<String> argList = null;

    /**
     * 已整理的参数
     * @param args 参数
     */
    public OptionHelper(String[] args) {
        argList = Arrays.<String>asList(args);
        parseArgMap();
    }

    /**
     * 未整理的参数
     * @param arg 参数
     */
    public OptionHelper(String arg) {
        OptionParser op = new OptionParser(arg);
        argList = op.getArgList();
        parseArgMap();
    }

    /**
     * 分析参数，分配键值
     */
    private void parseArgMap() {
        argMap = new HashMap<String, List<String>>();
        List<String> valList = null;
        String arg = null;
        String key = null;
        int size = argList.size();

        for (int i = 0; i < size; i++) {
            arg = argList.get(i);
            if (arg.startsWith("--")) {
                key = arg.substring(2);
            } else if (arg.startsWith("-")) {
                key = arg.substring(1);
            } else {
                valList = new ArrayList<String>();
                valList.add(arg);
                i++;
                for_val:
                for (; i < size; i++) {
                    arg = argList.get(i);
                    if (arg.charAt(0) == '-') {
                        i--;
                        break for_val;
                    }
                    valList.add(arg);
                }
                if (key != null) {
                    argMap.put(key, valList);
                }
                key = null;
            }
            if (key != null) {
                argMap.put(key, null);
            }
        }
    }

    /**
     * 判断选项是否存在
     * @param opt   选项名
     * @return 
     */
    public boolean hasOpt(String opt) {
        return argMap.containsKey(opt);
    }

    /**
     * 判断选项是否存在
     * @param opt   选项名
     * @return
     */
    public boolean hasOpt(String[] opts) {
        for (String opt : opts) {
            if (argMap.containsKey(opt)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取单值
     * @param opt 选项名
     * @return 选项不存在或值为空时返回null
     */
    public String getOpt(String opt) {
        List<String> valList = argMap.get(opt);
        return valList == null || valList.isEmpty()
                ? null : valList.get(0);
    }

    /**
     * 获取单值
     * @param opt 选项名
     * @return  选项不存在或值为空时返回null
     */
    public String getOpt(String[] opts) {
        List<String> valList;
        for (String opt : opts) {
            valList = argMap.get(opt);
            if (valList != null && !valList.isEmpty()) {
                return valList.get(0);
            }
        }
        return null;

    }

    /**
     * 获取值列表
     * @param opt   选项名
     * @return  选项不存在时返回null
     */
    public List<String> getOptValList(String opt) {
        return argMap.get(opt);
    }

    /**
     * 获取值列表
     * @param opt   选项名
     * @return  选项不存在时返回null
     */
    public List<String> getOptValList(String[] opts) {
        for (String opt : opts) {
            if (argMap.containsKey(opt)) {
                return argMap.get(opt);
            }
        }
        return null;
    }

    /**
     * 根据参数位置获取值
     * @param optIndex 参数位置
     * @return
     */
    public String getArg(int optIndex) {
        return argList == null ? null : argList.get(optIndex);
    }

    /**
     * 根据参数位置获取值
     * @param optIndex 参数位置
     * @return
     */
    public int getArgCount() {
        return argList == null ? 0 : argList.size();
    }

    /**
     * 字符串参数解析
     */
    private static class OptionParser {

        /**
         * 处理位置
         */
        private int index = 0;
        /**
         * 参数表达式长度
         */
        private int length = 0;
        /**
         * 参数表达式
         */
        private String arguments = null;
        private List<String> argList = null;

        /**
         *  字符串参数解析
         * @param arguments 参数表达式
         */
        public OptionParser(String arguments) {
            this.arguments = StringHelper.trim(arguments);
            length = this.arguments.length();
            argList = new ArrayList<String>();
        }

        /**
         * 获取参数列表
         * @return
         */
        public List<String> getArgList() {
            char ch = Character.UNASSIGNED;

            String str = null;
            for (; index < length; index++) {
                ch = arguments.charAt(index);
                if (!Character.isWhitespace(ch)) {
                    str = parseString();
                    if (str != null) {
                        argList.add(str);
                    }
                }
            }
            return argList;
        }

        private String parseString() {

            char ch = Character.UNASSIGNED;
            boolean isEscape = false;
            StringBuilder strBuilder = new StringBuilder();
            char quote = Character.UNASSIGNED;
            for (int i = 0; index < length;
                    index++, i++) {
                ch = arguments.charAt(index);
                if (i == 0 && (ch == '\'' || ch == '"')) {
                    quote = ch;
                    continue;
                }
                if (ch == '\\') {
                    isEscape = true;
                    continue;
                }
                if (isEscape) {
                    strBuilder.append("\\");
                    strBuilder.append(ch);
                    isEscape = false;
                    continue;
                }
                if (i != 0 && ch == quote) {
                    quote = ch;
                    break;
                }
                if (quote == Character.UNASSIGNED && Character.isWhitespace(ch)) {
                    break;
                }
                strBuilder.append(ch);
            }
            if (strBuilder.length() == 0) {
                return null;
            }
            return strBuilder.toString();
        }
    }
}
