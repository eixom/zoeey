/*
 * MoXie (SysTem128@GMail.Com) 2009-4-20 23:03:13
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
/**
 * todo 字段多值同名尚未处理 09-05-13
 */
package org.zoeey.loader;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.zoeey.constant.EnvConstants;

/**
 * 字段表述类型装换
 * 将 name[key_1][key_2]=value 形式的值对，转换为Map。
 * @author MoXie(SysTem128@GMail.Com)
 */
public final class FieldMapper {

    /**
     * 锁定创建
     */
    private FieldMapper() {
    }

    /**
     * 将Map 转化为FieldMap。
     * 多重键name[key_1][key_2] 不会被保留。
     * @param map
     * @return
     */
    public static FieldMap<String, Object> toFieldMap(Set<Entry<String, Object>> entrySet) {
        FieldMap<String, Object> fieldMap = new FieldMap<String, Object>();
        String key;
        String[] keys;
        for (Entry<String, Object> entry : entrySet) {
            key = entry.getKey();
            keys = parseKeys(key);
            parseField(fieldMap, keys, entry.getValue());
        }
        return fieldMap;
    }

    /**
     * 将Map 转化为FieldMap。
     * @param map
     * @param skipMaped 多重键name[key_1][key_2] 是否保留。
     * @return
     */
    public static FieldMap<String, Object> toFieldMap(Map<String, Object> map) {
        return toFieldMap(map.entrySet());
    }

    /**
     * <pre>
     * 将 name[key_1][key_2]=value形式的值对，转化为{@link FieldMap}。
     * <pre>
     * @param map
     * @param fieldName
     * @param fieldValue
     */
    public static void fieldToMap(FieldMap<String, Object> map, String fieldName, Object fieldValue) {
        parseField(map, parseKeys(fieldName), fieldValue);
    }

    /**
     * 分析键列表
     * @param str
     * @return
     */
    private static String[] parseKeys(String str) {
        List<String> keyList = new ArrayList<String>();
        CharacterIterator it = new StringCharacterIterator(str);
        int i = 0;
        /**
         * 用于判断是否为 [ 开头。
         */
        char latestQ = 0;
        StringBuilder strBuilder = new StringBuilder();
        for (char ch = it.current(); ch != CharacterIterator.DONE; ch = it.next()) {
            i++;
            if (i == 1) {
                keyList.add(parseKey(strBuilder, it, latestQ));
                ch = it.current();
            }

            if (ch == '[') {
                strBuilder.setLength(0);
                keyList.add(parseKey(strBuilder, it, ch));
            }

        }
        return keyList.toArray(new String[keyList.size()]);
    }

    /**
     * 分析键
     * @param strBuilder
     * @param it
     * @param latestQ
     * @return
     */
    private static String parseKey(StringBuilder strBuilder, CharacterIterator it, char latestQ) {
        String str;
        for (char ch = it.current(); ch != CharacterIterator.DONE; ch = it.next()) {
            if (ch == '[') {
                if (latestQ == 0) {
                    break;
                } else {
                    continue;
                }
            }
            if (ch == ']') {
                break;
            }
            strBuilder.append(ch);
        }
        str = strBuilder.toString();
        return str;
    }

    /**
     * 转换新元素
     * @param map
     * @param keys
     * @param value
     */
    @SuppressWarnings("unchecked")
    private static void parseField(FieldMap<String, Object> map, String[] keys, Object value) {
        do {
            if (keys == null || keys.length == 0) {
                map.put("", value);
                break;
            }
            if (keys.length == 1) {
                map.put(keys[0], value);
                break;
            }
            String key;
            int keySize = keys.length;
            Object pObj;
            FieldMap<String, Object> pMap = map;
            FieldMap<String, Object> cMap;
            for (int i = 0; i < keySize; i++) {
                if (i > EnvConstants.FORCE_JUMPER) {
                    break;
                }
                key = keys[i];
                if (key == null || key.length() == 0) {
                    key = String.valueOf(getNumIndex(pMap));
                }
                pObj = pMap.get(key);
                if (i < keySize - 1) {
                    if (pObj instanceof FieldMap<?, ?>) {
                        pMap = (FieldMap<String, Object>) pObj;
                        continue;
                    } else {
                        cMap = new FieldMap<String, Object>();
                        if (pObj != null) {
                            cMap.put("0", pObj);
                        }
                        pMap.put(key, cMap);
                        pMap = cMap;
                    }
                } else {
                    pMap.put(key, value);
                }
            }
            cMap = null;
        } while (false);
    }

    /**
     * 获取数字键的索引。
     * @param map
     * @return
     */
    private static int getNumIndex(Map<String, Object> map) {
        Iterator<String> iterator = map.keySet().iterator();
        int key = 0;
        int nKey = 0;// new key
        String strKey;
        while (iterator.hasNext()) {
            strKey = iterator.next();
            if (isNum(strKey)) {
                nKey = Integer.valueOf(strKey);
                key = key > nKey ? key : nKey + 1;
            }
        }
        return key;
    }

    /**
     * 判断是否为纯数字字符串
     * @param str
     * @return
     */
    private static boolean isNum(String str) {
        boolean isPass = true;
        char[] chs = str.toCharArray();
        int chsLen = chs.length;
        if (chsLen == 0) {
            return false;
        }
        for (int i = chsLen - 1; i > 0; i--) {
            if (chs[i] < 48 || chs[i] > 57) {
                isPass = false;
                break;
            }
        }
        return isPass;
    }
}
