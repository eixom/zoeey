/*
 * MoXie (SysTem128@GMail.Com) 2009-5-24 22:37:49
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.zdo.zdbcontrol;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author MoXie(SysTem128@GMail.Com)
 */
class ParamentNamedParser implements ParamentNamedParserAble {

    private List<String> paramList = null;
    private List<String> sqlList = null;
    private int index;
    private int len;
    private String sql = null;

    private String parseKey() {
        StringBuilder strBuilder = new StringBuilder();
        char ch = 0;
        while (true) {
            index++;
            if (index >= len) {
                break;
            }
            ch = sql.charAt(index);
            if (Character.isJavaIdentifierPart(ch)) {
                strBuilder.append(ch);
            } else {
                index--;
                break;
            }
        }
        String key = strBuilder.toString();
        return key.length() > 0 ? key : null;
    }

    public void convert(String sql) {
        index = 0;
        len = sql.length();
        this.sql = sql;
        char ch = 0;
        paramList = new ArrayList<String>();
        boolean isEscape = false;
        String key = null;
        sqlList = new ArrayList<String>();
        StringBuilder strBuilder = new StringBuilder();
        boolean isKey = false;
        while (true) {
            if (index >= len) {
                break;
            }
            isEscape = false;
            ch = sql.charAt(index);
            if (ch == '\\') {
                isEscape = true;
            }
            if (!isEscape) {
                if (ch == ':') {
                    key = parseKey();
                    if (key != null) {
                        paramList.add(key);
                        isKey = true;
                    }
                }
            }
            if (isKey) {
                sqlList.add(strBuilder.toString());
                sqlList.add("?");
                strBuilder = new StringBuilder();
                isKey = false;
            } else {
                strBuilder.append(ch);
                if (isEscape && index + 1 < len) {
                    index++;
                    strBuilder.append(sql.charAt(index));
                }
            }
            index++;
        }
        sqlList.add(strBuilder.toString());
    }

    public List<Integer> listIndexOf(String name) {
        List<Integer> indexList = new ArrayList<Integer>();
        if (paramList != null) {
            int i = 0;
            for (String key : paramList) {
                i++;
                if (key.equals(name)) {
                    indexList.add(i);
                }
            }
        }
        return indexList;
    }

    public List<String> listSql() {
        return sqlList;
    }

    /**
     * 获取参数列表
     * @return
     */
    public List<String> getParamList() {
        return paramList;
    }

    public String getNormalSql() {
        StringBuilder strBuilder = new StringBuilder();
        for (String str : sqlList) {
            strBuilder.append(str);
        }
        return strBuilder.toString();
    }

    /**
     * 绑定SQL语句
     * @param str
     * @param sql
     * @return
     */
    public boolean bindSQL(String str, String sql) {
        List<Integer> indexList = listIndexOf(str);
        boolean isBinded = false;
        int i = 0;
        for (int _index : indexList) {
            if ("?".equals(sqlList.get(_index))) {
                paramList.remove(i);
                sqlList.set(_index, sql);
                isBinded = true;
                i++;
            }
        }
        return isBinded;
    }
}
