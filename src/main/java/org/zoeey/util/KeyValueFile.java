/*
 * MoXie (SysTem128@GMail.Com) 2009-4-17 18:09:28
 * 
 * Copyright &copy; 2008-2010 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.util;

import java.io.File;
import java.io.IOException;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.zoeey.loader.FieldMap;
import org.zoeey.loader.FieldMapper;

/**
 * <pre>
 * 键值对文件。
 * -------- *.kv 文件内容 --------
 * # 这里是注释
 * # 这里是注释
 * description="这是一段描述1，
 * 包含了换行" # 注释
 *  #这里是注释
 * description2="这是一段描述2，
 * 包含了换行"
 * description3="这是一段描述3，
 * 包含了换行"
 * description5 = "这是一段描述5，
 * 包含了换行"
 *
 * description6:"这是一段描述6，
 * 包含了换行"
 * /**
 *  * 多行换行
 *  * 测试
 *  * 下面是一个单反斜线，将会被忽略。
 *  * 其后不能包含非空字符(;同样会被忽略)
 *  *\/
 * / ;;;;;;;;;;;
 * // 双反斜斜线注释测试
 * consequent = "这是一句话的前半部分，"
 * consequent + "这是后半部分：）"
 * consequent + "这是最后一部分-_-"
 * # 注释以 # 开头以换行符结尾。
 * # 键与值之间使用'='或':'进行间隔（建议使用'='），前后允许使用空格、换行和制表符（不建议），
 * # 键值间隔符同一文件或文本内可交换使用。（不建议）
 * # 键允许使用 字母（a-zA-Z）、数字（0-9）、下划线（_）、减号（-）和点（.） 。
 * # 值使用 单引号（'）或双引号（"）括起来，所使用的引号须在内容中使用 '\' 进行转义。
 * # 使用双引号时内容中转义序列将会被替换为值。
 * # 使用单引号时内容中转义不会被值代替。
 * # 值内容内可换行。
 * # 值可为空（也可无引号），但键值分割符不可缺少。（不建议）
 * # 以下均为有效定义，且整体有效（可嵌套注释）。
 *
 * name = "MoXie_1"
 * name : "MoXie_2"
 * name = 'MoXie_3'
 * name : 'MoXie_4'
 * name = 'MoXie_5\'quote\''
 * name = 'MoXie_6\'quote\'\\\r\n' # 注意单引号和双引号的区别
 * name = "MoXie_7'quote'\\\r\n|"
 * name = "MoXie_8
 * 换行了的内容"
 * name
 * =
 * "键值间隔符前后可有空白字符"
 * 中文名 = "中文内容"
 * \u4e2d\u6587\u540d_2 = "\u4e2d\u6587\u5185\u5bb9_2"
 * \u4e2d\u6587\u540d_3 = '\u4e2d\u6587\u5185\u5bb9_3'
 * \u4e2d\u6587\u540d_4 = "\u4e2d\u6587\u5185\u5bb9_4_\ uG123" # \ uG123将不会被转义
 * # 与 FieldMapper 配合使用。
 * field[key_1][key_2_1] = 'FieldMapper_value_1\uff01' # 特别注意，当此处转换为 ！ 时不会被解析器解码为"！"。
 * field[key_1][key_2_2] = "FieldMapper_value_2！"
 * # 注意这个错误
 * name = "丢失了后半个引号
 * name = '这里将不同于期望值'
 * name = "这里将会被丢失"
 * --------/ *.kv 文件内容 --------
 * 注意：本类仅适用于处理小型键值对文件或描述字符串。
 *       可与 {@link FieldMapper} 配合使用。
 * 特别注意：分析仅会中断，不会抛出异常，请使用单元测试保证结果分析结果的正确性。
 * </pre>
 * @see FieldMap
 * @see FieldMapper
 * @author MoXie(SysTem128@GMail.Com)
 */
public class KeyValueFile {

    /**
     * 锁定创建
     */
    private KeyValueFile() {
    }
//    private static final char[] blankChars = {'\b', '\t', '\n', '\r', ' '};

    /**
     * 剔除空白元素
     */
    private static void skipBlank(CharacterIterator it) {
        char ch = 0;
        int i = 0;
        for (ch = it.current(); ch != CharacterIterator.DONE; ch = it.next()) {
            i++;
            if (Character.isWhitespace(ch) || ch == ';') {
                continue;
            } else {
                if (ch == '/' || ch == '#' || ch == '-') {
                    skipComment(it);
                    continue;

                }
                break;
            }

        }
    }

    /**
     * 注释<br />
     * 以 # 开头的行<br />
     * 以 -- 开头的行<br />
     * /* *\/ 包围的内容 <br />
     */
    private static void skipComment(CharacterIterator it) {
        char ch = 0;
        ch = it.current();
        // 注意： 添加或减少注释字符标识后需要对skipBlank进行查验。
        if (ch == '#') {
            for (ch = it.current(); ch != CharacterIterator.DONE; ch = it.next()) {
                if (ch == '\n' || ch == '\r') {
                    break;
                }

            }
        } else if (ch == '-') {
            ch = it.next();
            if (ch == '-') {
                for (ch = it.current(); ch != CharacterIterator.DONE; ch = it.next()) {
                    if (ch == '\n' || ch == '\r') {
                        break;
                    }
                }

            }
        } else if (ch == '/') {
            ch = it.next();
            if (ch == '/') {
                for (ch = it.current(); ch != CharacterIterator.DONE; ch = it.next()) {
                    if (ch == '\n' || ch == '\r') {
                        break;
                    }

                }
            } else if (ch == '*') {
                boolean isEndReady = false;
                for (ch = it.current(); ch != CharacterIterator.DONE; ch = it.next()) {
                    if (ch == '*') {
                        isEndReady = true;
                        continue;
                    }
                    if (isEndReady) {
                        isEndReady = false;
                        if (ch == '/') {
                            break;
                        }

                    }
                }
            } else {
                /**
                 * 如代码中出现单反斜杠则直接忽略
                 */
//                it.previous();
            }
        }
    }

    /**
     * 析出键表达式 , [0-9a-zA-Z_.-\[\]]
     * @param it
     * @return
     */
    private static String parseKey(CharacterIterator it) {
        char ch = 0;
        boolean isEscape = false;
        StringBuilder strBuilder = new StringBuilder(50);
        for (ch = it.current(); ch
                != CharacterIterator.DONE; ch =
                        it.next()) {
            if (!isEscape && Character.isLetterOrDigit(ch) //
                    //                    || (ch > 47 && ch < 58) // 0-9
                    //                    || (ch > 64 && ch < 91) // A-Z
                    //                    || (ch > 94 && ch < 123) // _ a-f
                    || (ch == 46) // .
                    || (ch == 45) // -
                    || (ch == 91) // [
                    || (ch == 93) // ]
                    || (ch == 95) // _
                    //                    || (ch == 123) // {
                    //                    || (ch == 125) // }
                    ) {
                strBuilder.append(ch);
                continue;

            } else {
                if (ch == '\\') {
                    isEscape = true;
                    continue;

                }


                if (isEscape) {
                    isEscape = false;
                    if (ch == 'u') {
                        ch = parseUnicode(it);
                        if (ch != 0) {
                            strBuilder.append(ch);
                        } else {
                            strBuilder.append("\\u");
                        }

                    } else {
                        strBuilder.append("\\u");
                        continue;

                    }


                } else {
                    skipBlank(it);
                    break;

                }



            }
        }
        return strBuilder.toString();
    }

    /**
     * 析出字符串
     * @param it
     * @return
     */
    private static String parseString(CharacterIterator it) {
        char ch = 0;
        boolean isEscape = false;
        int i = 0;
        int quoter = 0;
        StringBuilder strBuilder = new StringBuilder(200);
        for (ch = it.current(); ch
                != CharacterIterator.DONE; ch =
                        it.next()) {
            if (i == 0) {
                i = 1;
                if (ch != '"' && ch != '\'') {
                    break;
                } else {
                    quoter = ch;
                    continue;

                }


            }
            if (!isEscape && ch == '\\') {
                isEscape = true;
                if (quoter == '\'') {
                    strBuilder.append(ch);
                }

                continue;
            }

            if (isEscape) {
                // all character could be escaped
                isEscape = false;
                if (quoter == '\'') {
                    strBuilder.append(ch);
                } else {
                    if (ch == 'u') {
                        ch = parseUnicode(it);
                        if (ch != 0) {
                            strBuilder.append(ch);
                        } else {
                            strBuilder.append("\\u");
                        }

                    } else {
                        strBuilder.append(StringHelper.toEscapedVal(ch));
                    }

                }
                continue;
            } else {
                if (i != 0) {
                    if (ch == quoter) {
                        break;
                    }

                }
            }
            strBuilder.append(ch);
        }

        return strBuilder.toString();
    }

    /**
     * 请求unicode表达式
     * @param it
     * @return
     */
    private static char parseUnicode(CharacterIterator it) {
        char ch = 0;
        boolean isU = true;
        int i = 0;
        char[] chars = new char[4];
        int start = it.getIndex();
        while (i < 4) {
            ch = it.next();
            if (CharacterIterator.DONE == ch) {
                isU &= false;
                break;

            }


            if ((ch > 47 && ch < 58) // 0-9
                    || (ch > 64 && ch < 71) // A-F
                    || (ch > 96 && ch < 103) // a-f
                    ) {
                isU &= true;
            } else {
                isU &= false;
            }

            chars[i] = ch;
            i++;

        }


        if (!isU) {
            it.setIndex(start);
            ch = 0;
        } else {
            ch = (char) Integer.parseInt(new String(chars), 16);
        }

        chars = null;
        return ch;
    }

    /**
     * 转化为Map
     * @param file
     * @return
     * @throws java.io.IOException
     */
    public static Map<String, String> toMap(File file) throws IOException {
        return toMap(TextFileHelper.read(file));
    }

    /**
     * 转化为Map
     * @param text
     * @return
     */
    public static Map<String, String> toMap(String text) {
        List<Item> list = toList(text);
        Iterator<Item> iterator = list.iterator();
        Item item;

        Map<String, String> map = new HashMap<String, String>();
        while (iterator.hasNext()) {
            item = iterator.next();
            map.put(item.getKey(), item.getValue());
        }

        return map;
    }

    /**
     * 转化为List
     * @param file
     * @return
     * @throws java.io.IOException
     */
    public static List<Item> toList(File file) throws IOException {
        return toList(TextFileHelper.read(file));
    }

    /**
     * 转化为List
     * @param text
     * @return
     */
    public static List<Item> toList(String text) {
        List<Item> list = new ArrayList<Item>();
        CharacterIterator it = new StringCharacterIterator(text);
        char ch = 0;
        String key = null;
        String val = null;
        for (ch = it.current(); ch != CharacterIterator.DONE; ch = it.next()) {
            skipBlank(it); // [ ]key="value"
            key = parseKey(it);// [key]="value"
            skipBlank(it);// key[ ]="value"
            ch = it.current();// key[=]"value"
            if (ch != '=' && ch != ':' && ch != '+') {
                break;
            }
            if (it.getEndIndex() == it.getIndex()) {
                break;
            }
            it.next();
            skipBlank(it);
            val = parseString(it);
            if (ch == '+') {
                if (key.length() > 0) {
                    appendLastVal(list, key, val);
                } else {
                    appendLastVal(list, null, val);
                }
            } else {
                list.add(new Item(key, val));
            }

        }

        return list;
    }

    /**
     * 获取某键最后出现的值，作为字段链接的源数据
     * @param list
     * @param key
     * @return
     */
    private static void appendLastVal(List<Item> list, String key, String val) {
        Item item = null;
        int index = 0;
        if (list.isEmpty()) {
            return;
        }
        if (key != null) {
            int size = list.size();
            for (int i = size - 1; i >= 0; i--) {
                item = list.get(i);
                if (key.equals(item.getKey())) {
                    index = i;
                    break;
                }
                item = null;
            }
        } else {
            index = list.size() - 1;
            item = list.get(index);
        }
        if (item != null) {
            list.set(index, new Item(item.getKey()//
                    , new StringBuilder()//
                    .append(item.getValue())//
                    .append(val).toString()));
        }
    }

    /**
     * 存储字符串表为键值对文件<br />
     * 注意：中文字符会被转译
     * @param file  键值对文件
     * @param context   字符串表
     * @throws IOException
     */
    public static void store(File file, Map<String, String> context) throws IOException {
        StringBuilder strBuilder = new StringBuilder();
        for (Entry<String, String> entry : context.entrySet()) {
            strBuilder.append(entry.getKey());
            strBuilder.append('=');
            strBuilder.append('\"');
            strBuilder.append(StringHelper.utf8_literal(entry.getValue()));
            strBuilder.append('\"');
            strBuilder.append(EnvInfo.getLineSeparator());
        }
        TextFileHelper.write(file, strBuilder.toString());
    }

    /**
     * 存储键值项列表为键值对文件<br />
     * 注意：中文字符会被转译
     * @param file  键值对文件
     * @param itemList   键值项列表
     * @throws IOException
     */
    public static void store(File file, List<Item> itemList) throws IOException {
        StringBuilder strBuilder = new StringBuilder();
        for (Item item : itemList) {
            strBuilder.append(item.getKey());
            strBuilder.append('=');
            strBuilder.append('\"');
            strBuilder.append(StringHelper.utf8_literal(item.getValue()));
            strBuilder.append('\"');
            strBuilder.append(EnvInfo.getLineSeparator());
        }
        TextFileHelper.write(file, strBuilder.toString());
    }

    /**
     * 键值项
     */
    public static class Item {

        private String key;
        private String val;

        /**
         * 键值项
         * @param key   键
         * @param val   值
         */
        public Item(String key, String val) {
            this.key = key;
            this.val = val;
        }

        /**
         * 获取键
         * @return
         */
        public String getKey() {
            return key;
        }

        /**
         * 获取值
         * @return
         */
        public String getValue() {
            return val;
        }
    }
}
