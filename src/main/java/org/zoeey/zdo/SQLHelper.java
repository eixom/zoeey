/*
 * MoXie (SysTem128@GMail.Com) 2009-2-1 15:59:59
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.zdo;

/**
 * sql 语句辅助工具
 * @author MoXie(SysTem128@GMail.Com)
 */
public class SQLHelper {

    /**
     * 引号
     * 默认为反引号
     */
    private char quoter = '`';
    /**
     * 是否使用引号
     */
    private boolean isQuoter = true;
    /**
     * 前缀,用于表别称
     */
    private String prefix = null;

    /**
     * 设定引号
     * @param quoter
     */
    public void setQuoter(char quoter) {
        this.quoter = quoter;
    }

    /**
     * 去除引号
     */
    public void removeQuoter() {
        isQuoter = false;
    }

    /**
     * 设定前缀
     * @param prefix
     */
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    /**
     * 去除前缀
     */
    public void removePrefix() {
        prefix = null;
    }

    /**
     * 剔除不需要的键
     *
     * @param fields 原有键
     * @param flipFields 需要剔除的键列表
     * @return
     */
    private FieldItem[] flipFields(FieldItem[] fields, String[] flipFields) {
        if (fields == null || fields.length < 1) {
            return fields;
        }
        FieldItem _field = null;
        /**
         * 剔除不允许的类
         */
        if (flipFields != null && flipFields.length > 0) {
            String _name = null;
            for (int i = 0; i < fields.length; i++) {
                _field = fields[i];
                if (_field == null || !_field.isIsActive()) {
                    continue;
                }
                for (int j = 0; j < flipFields.length; j++) {
                    _name = flipFields[j];
                    if (_name == null || _name.length() < 1) {
                        continue;
                    }
                    if (_name.equals(_field.getName())) {
                        fields[i] = null;
                    }
                }
            }
        }
        return fields;
    }

    /**
     * 整理前缀
     * @param prefix
     * @return
     */
    private String getPrefix() {
        if (prefix == null || prefix.length() < 1) {
            return "";
        } else {
            return prefix + '.';
        }
    }

    /**
     * 用 逗号 连接数据库字段 有前缀
     * article.`Article_Id`,article.`Article_Title`
     * @param fields
     * @return
     * @
     */
    public String joinFields_Comma(FieldItem[] fields) {
        /**
         * 整理前缀
         */
        String _prefix = getPrefix();
        //
        StringBuilder fieldSql = new StringBuilder();
        FieldItem _field = null;
        String _argName = null;
        //
        int fieldSize = fields.length;
        for (int i = 0; i < fieldSize; i++) {
            _field = fields[i];
            if (_field == null || !_field.isIsActive()) {
                continue;
            }
            _argName = _field.getArgName();
            if (isQuoter) {
                fieldSql.append(_prefix).append(quoter).append(_argName).append(quoter);
            } else {
                fieldSql.append(_prefix).append(_argName);
            }
            if (i != fieldSize - 1) {
                fieldSql.append(','); //  使用逗号间隔
            } else {
                fieldSql.append(' '); //  空格结尾
            }
        }
        return fieldSql.toString();
    }

    /**
     * 用 逗号 连接数据库字段 无前缀
     * 无前缀 `Article_Id`,`Article_Title`
     * 有前缀 article.`Article_Id`,article.`Article_Title`
     * @param fields
     * @param flipFields
     * @return
     * @
     */
    public String joinFields_Comma(FieldItem[] fields, String[] flipFields) {
        /**
         * 弹出不必要的字段
         */
        fields = flipFields(fields, flipFields);
        return joinFields_Comma(fields);
    }

    /**
     * 用 逗号和冒号 连接数据库字段
     * :Coupon_Id,:Coupon_Title
     * @param fields
     * @return unknown
     */
    public String joinFields_ColonComma(FieldItem[] fields) {
        StringBuilder fieldSql = new StringBuilder(":");
        FieldItem _field = null;
        String _argName = null;
        //
        int fieldSize = fields.length;
        for (int i = 0; i < fieldSize; i++) {
            _field = fields[i];
            if (_field == null || !_field.isIsActive()) {
                continue;
            }
            _argName = _field.getArgName();
            fieldSql.append(_argName);
            if (i != fieldSize - 1) {
                fieldSql.append(",:"); //  使用逗号和冒号间隔
            } else {
                fieldSql.append(' '); //  空格结尾
            }
        }
        return fieldSql.toString();
    }

    /**
     * 用 逗号和冒号 连接数据库字段 剔除部分字段
     * :Coupon_Id,:Coupon_Title
     * @param fields
     * @param flipFields
     * @return
     * @
     */
    public String joinFields_ColonComma(FieldItem[] fields, String[] flipFields) {
        fields = flipFields(fields, flipFields);
        return joinFields_ColonComma(fields);
    }

    /**
     * 用 And 连接数据库字段
     * AND `Coupon_Id` = :Coupon_Id
     * 在使用WHERE的时候注意加上 WHERE 1=1 后再使用本函数
     * @param fields
     * @return unknown
     */
    public String joinFields_And(FieldItem[] fields) {
        StringBuilder fieldSql = new StringBuilder();
        FieldItem _field = null;
        String _argName = null;
        String _prefix = getPrefix();
        //
        int fieldSize = fields.length;
        for (int i = 0; i < fieldSize; i++) {
            _field = fields[i];
            if (_field == null || !_field.isIsActive()) {
                continue;
            }
            _argName = _field.getArgName();
            fieldSql.append("AND ");
            if (isQuoter) {
                fieldSql.append(_prefix).append(quoter).append(_argName).append(quoter);
            } else {
                fieldSql.append(_prefix).append(_argName);
            }
            fieldSql.append(" = :");
            fieldSql.append(_argName);
            fieldSql.append(' '); //  空格结尾
        }
        return fieldSql.toString();
    }

    /**
     * 
     * @param fields
     * @param flipFields
     * @return
     * @
     */
    public String joinFields_And(FieldItem[] fields, String[] flipFields) {
        fields = flipFields(fields, flipFields);
        return joinFields_And(fields);
    }

    /**
     * <pre>
     * 用 Or 连接数据库字段
     * Or `Coupon_Id` = :Coupon_Id
     * 在使用WHERE的时候注意加上 WHERE 1=1 后再使用本函数
     * </pre>
     * @param fields 
     * @return unknown
     */
    public String joinFields_Or(FieldItem[] fields) {
        StringBuilder fieldSql = new StringBuilder();
        FieldItem _field = null;
        String _argName = null;
        String _prefix = getPrefix();
        //
        int fieldSize = fields.length;
        for (int i = 0; i < fieldSize; i++) {
            _field = fields[i];
            if (_field == null || !_field.isIsActive()) {
                continue;
            }
            _argName = _field.getArgName();
            fieldSql.append("OR ");
            if (isQuoter) {
                fieldSql.append(_prefix).append(quoter).append(_argName).append(quoter);
            } else {
                fieldSql.append(_prefix).append(_argName);
            }
            fieldSql.append(" = :");
            fieldSql.append(_argName);
            fieldSql.append(' '); //  空格结尾
        }
        return fieldSql.toString();
    }

    /**
     * <pre>
     * 用 Or 连接数据库字段
     * Or `Coupon_Id` = :Coupon_Id
     * 在使用WHERE的时候注意加上 WHERE 1=1 后再使用本函数
     * </pre>
     * @param fields 字段
     * @param flipFields 需要剔除的字段
     * @return
     * @
     */
    public String joinFields_Or(FieldItem[] fields, String[] flipFields) {
        fields = flipFields(fields, flipFields);
        return joinFields_Or(fields);
    }

    /**
     *  <pre>
     * 用  = : 链接数据库字段,使用逗号间隔
     *  `Coupon_Id` = :Coupon_Id,`Coupon_Title` = :Coupon_Title
     * 在使用WHERE的时候注意加上 WHERE 1=1 后再使用本函数
     * </pre>
     * @param fields
     * @return unknown
     */
    public String joinFields_EquColon(FieldItem[] fields) {
        StringBuilder fieldSql = new StringBuilder();
        FieldItem _field = null;
        String _argName = null;
        String _prefix = getPrefix();
        //
        int fieldSize = fields.length;
        for (int i = 0; i < fieldSize; i++) {
            _field = fields[i];
            if (_field == null || !_field.isIsActive()) {
                continue;
            }
            _argName = _field.getArgName();
            if (isQuoter) {
                fieldSql.append(_prefix).append(quoter).append(_argName).append(quoter);
            } else {
                fieldSql.append(_prefix).append(_argName);
            }
            fieldSql.append(" = :");
            fieldSql.append(_argName);
            if (i != fieldSize - 1) {
                fieldSql.append(','); //  使用逗号和冒号间隔
            } else {
                fieldSql.append(' '); //  空格结尾
            }
        }
        return fieldSql.toString();
    }

    /**
     * 用  = : 链接数据库字段,使用逗号间隔
     *
     * @param fields    字段
     * @param flipFields   需要屏蔽的字段
     * @return
     * @
     */
    public String joinFields_EquColon(FieldItem[] fields, String[] flipFields) {
        fields = flipFields(fields, flipFields);
        return joinFields_EquColon(fields);
    }
}
