/*
 * MoXie (SysTem128@GMail.Com) 2009-2-1 15:59:59
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.resource;

/**
 * 异常信息描述资源
 *
 * @author MoXie(SysTem128@GMail.Com)
 */
public class ResourceExceptionMsg {

    /**
     * 没有找到需要发布的类
     */
    public static final String ROUTER_PUBLISHER_CLASS_NOFOUND_EXCEPTION = "${router.publisherClassNoFound}";
    /**
     *  
     */
    public static final String ROUTER_CONNECT_ERROR = "${router.connection.error}";
    /**
     *
     * 
     */
    public static final String ROUTERPARSER_ENTRYNOPUBLISHEXCEPTION = "${router.parser.entryNoPublish}";
    /**
     * 文件命名错误
     */
    public static final String JSCONTAINER_INCORRECTFILENAME_EXCEPTION = "${jsContainer.incorrectFileName}";
    /**
     * ZDO
     */
    /**
     * 参数不存在或格式错误
     */
    public static final String ZDBCONTROL_INCORRECTPARAMETER_EXCEPTION = "${zdbcontrol.incorrectParameter}";
    /**
     * ZDO
     */
    /**
     * 尚未定义表名称
     */
    public static final String ZDO_TABLEUNDEFINDED_EXCEPTION = "${zdo.tableUndefinded}";
    /**
     * Loader
     */
    public static final String LOADER_MODEL_NULL = "${loader.model.null}";
    /**
     *
     */
    public static final String LOADER_METHOD_NOACCESS = "${loader.method.noAccess}";
    /**
     *
     */
    public static final String LOADER_METHOD_ARGINVALID = "${loader.method.argInvalid}";
    /**
     *
     */
    public static final String LOADER_METHOD_EXUNCATCHED = "${loader.method.exUncatched}";
    /**
     * DAO
     */
    public static final String DAO_MODEL_NULL = "${dao.model.null}";
    /**
     *
     */
    public static final String DAO_INVOKE_INSTANTIATION = "${dao.invoke.instantiation}";
    /**
     *
     */
    public static final String DAO_INVOKE_NOACCESS = "${dao.invoke.noAccess}";
    /**
     *
     */
    public static final String DAO_INVOKE_ARGINVALID = "${dao.invoke.argInvalid}";
    /**
     *
     */
    public static final String DAO_INVOKE_EXUNCATCHED = "${dao.invoke.exUncatched}";
    /**
     * ZTPL Analyzer
     */
    /**
     * 映射表键中有特殊字符
     */
    public static final String ZTPL_MAP_KEY_SYMBOLERROR = "${ztpl.map.key.symbolError}";
    /**
     * 映射表没有解析到键
     */
    public static final String ZTPL_MAP_KEY_NOTEXISTS = "${ztpl.map.key.noExists}";
    /**
     * 映射表表达式缺少值
     */
    public static final String ZTPL_MAP_KEY_END = "${ztpl.map.key.end}";
    /**
     * 映射表表达式结尾错误
     */
    public static final String ZTPL_MAP_END = "${ztpl.map.end}";
    /**
     * 列表表达式结尾错误
     */
    public static final String ZTPL_LIST_END = "${ztpl.list.end}";
    /**
     * 由双引号起始的字符串未结尾
     */
    public static final String ZTPL_STRING_DQUOTE_UNCLOSED = "${ztpl.string.dQuote.unClosed}";
    /**
     * 由单引号起始的字符串未结尾
     */
    public static final String ZTPL_STRING_SQUOTE_UNCLOSED = "${ztpl.string.sQuote.unClosed}";
    /**
     * 字符串内包含不需要转义却被转义的字符
     */
    public static final String ZTPL_STRING_ESCAPE = "${ztpl.string.escape}";
    /**
     * 数字中包含多个小数点
     */
    public static final String ZTPL_NUMBER_MULTI_DOT = "${ztpl.number.multi.dot}";
    /**
     * 数字中标识十六进制的字母x，位置有误
     */
    public static final String ZTPL_NUMBER_X_POSITION = "${ztpl.number.x.position}";
    /**
     * 数字以不允许的字符结尾
     */
    public static final String ZTPL_NUMBER_END_ERROR = "${ztpl.number.end.error}";
    /**
     * 函数不可占用关键词
     */
    public static final String ZTPL_FUNCTION_KEYWORD = "${ztpl.function.keyword}";
    /**
     * 函数内包含非法字符
     */
    public static final String ZTPL_FUNCTION_SYMBOLERROR = "${ztpl.function.symbolError}";
    /**
     * 函数名不可为空
     */
    public static final String ZTPL_FUNCTION_LENGTH = "${ztpl.function.length}";
    /**
     * 变量名不可为空
     */
    public static final String ZTPL_VAR_LENGTH = "${ztpl.var.length}";
    /**
     * 无法识别的等号
     */
    public static final String ZTPL_EQUAL_NUM = "${ztpl.equal.num}";
    /**
     * 无法识别的与字符
     */
    public static final String ZTPL_AND_NUM = "${ztpl.and.num}";
    /**
     * 无法识别的与字符
     */
    public static final String ZTPL_UNKNOW_SYMBOL = "${ztpl.unknow.symbol}";
}
