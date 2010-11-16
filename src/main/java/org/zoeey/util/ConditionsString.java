/*
 * MoXie (SysTem128@GMail.Com) 2009-4-17 11:38:53
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <pre>
 * 以条件激活拼装的字符串。
 * 注意：字符串拼装顺序以条件<b>载入顺序</b>为准。
 * </pre>
 * @author MoXie(SysTem128@GMail.Com)
 */
public class ConditionsString implements ConditionsStringAble {

    /**
     * 激活的条件
     */
    private ArrayList<String> activedList;
    /**
     * 条件与结果项列表
     */
    private List<ConditionStringItem> itemList;

    /**
     *
     */
    public ConditionsString() {
        activedList = new ArrayList<String>();
        itemList = new ArrayList<ConditionStringItem>();
    }

    /**
     * 激活条件
     * @param condition
     */
    public void active(String condition) {
        activedList.add(condition);
    }

    /**
     * 取消激活条件
     * @param condition
     */
    public void cancel(String condition) {
        activedList.remove(condition);
    }

    /**
     * 更新激活条件
     * 
     * @param oldCondition 
     * @param newCondition
     */
    public void update(String oldCondition, String newCondition) {
        activedList.remove(oldCondition);
        activedList.add(newCondition);
    }

    /**
     * 移除所有激活条件
     */
    public void clear() {
        activedList.clear();
    }

    /**
     * 增加条件与结果项
     * @param condition
     * @param str
     */
    public void put(String condition, String str) {
        itemList.add(new ConditionStringItem(condition, str));
    }

    /**
     * <pre>
     * 从文件分析条件和结果项
     * 文件语法参见 {@link KeyValueFile}
     * </pre>
     * @see #fromString(java.lang.String) 
     * @param file
     * @throws java.io.IOException
     */
    public void fromFile(File file) throws IOException {
        String conditionText = TextFileHelper.read(file);
        fromString(conditionText);
    }

    /**
     * <pre>
     * 从字符串中分析条件和对应字符串。
     * 字符串语法参见 {@link KeyValueFile}
     * </pre>
     * @param conditionText
     */
    public void fromString(String conditionText) {
        List<KeyValueFile.Item> list = KeyValueFile.toList(conditionText);
        Iterator<KeyValueFile.Item> iterator = list.iterator();
        KeyValueFile.Item item;
        while (iterator.hasNext()) {
            item = iterator.next();
            put(item.getKey(), item.getValue());
        }
    }

    /**
     * 将结果转换为字符串
     * @return
     */
    @Override
    public String toString() {
        return toString(null);
    }

    /**
     * 将结果转换为字符串，并使用 seq 作为间隔。
     * @param sep 间隔符号
     * @return
     */
    public String toString(String sep) {
        return toString(sep, 0);
    }
    /**
     * 将多个空格制表符等替换为单个空格
     */
    public static final int TOSTR_SPACES_TOONE = ParamHelper.genParam(0);
    /**
     * 剔除换行符
     */
    public static final int TOSTR_SKIP_LFCR = ParamHelper.genParam(1);

    /**
     * 将结果转换为字符串，可使用间隔符，并进行修饰
     * @see #TOSTR_SPACES_TOONE 
     * @see #TOSTR_SKIP_LFCR
     * @param sep   间隔符，可为null。
     * @param options   修饰设置
     * @return
     */
    public String toString(String sep, int options) {
        Iterator<ConditionStringItem> iterator = itemList.iterator();
        StringBuilder strBuilder = new StringBuilder(200);
        ConditionStringItem item;
        int i = 0;
        while (iterator.hasNext()) {
            item = iterator.next();
            if (activedList.contains(item.getContidion())) {
                if (i > 0 && sep != null) {
                    strBuilder.append(sep);
                }
                i++;
                strBuilder.append(item.getStr());
            }
        }
        String _result = strBuilder.toString();
        strBuilder = null;
        if (options > 0) {
            if (ParamHelper.contain(options, TOSTR_SPACES_TOONE)) {
                _result = _result.replaceAll("[ \t]+", " ");
            }
            if (ParamHelper.contain(options, TOSTR_SKIP_LFCR)) {
                _result = _result.replaceAll("[\\r|\n]", "");
            }
        }
        return _result;
    }

    /**
     * 条件项
     */
    private class ConditionStringItem {

        private String contidion;   // 条件
        private String str;      // 结果字符串

        public ConditionStringItem(String contidion, String str) {
            this.contidion = contidion;
            this.str = str;
        }

        public String getContidion() {
            return contidion;
        }

        public String getStr() {
            return str;
        }
    }
}
