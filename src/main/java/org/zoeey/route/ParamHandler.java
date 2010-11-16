/*
 * MoXie (SysTem128@GMail.Com) 2010-10-8 14:35:29
 * 
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.route;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author MoXie
 */
public class ParamHandler {

    private List<ParamEntry> entryList;

    public ParamHandler(List<ParamEntry> entryList) {
        this.entryList = entryList;
    }

    /**
     * 获取最后出现的值
     * @param name  参数名
     * @return
     */
    public String getValue(String name) {
        Object obj = null;
        for (ParamEntry field : entryList) {
            if (name.equals(field.getKey())) {
                obj = field.getValue();
            }
        }
        if (obj != null) {
            if (obj instanceof String) {
                return (String) obj;
            } else if (obj.getClass().isArray()) {
                String[] strs = (String[]) obj;
                return strs[strs.length - 1];
            }
        }
        return null;
    }

    /**
     * 获取值列表
     * @param name  参数名
     * @return
     */
    public List<String> getValueList(String name) {
        List<String> valList = new ArrayList<String>();
        Object obj = null;
        for (ParamEntry field : entryList) {
            if (name.equals(field.getKey())) {
                obj = field.getValue();
                if (obj == null) {
                    continue;
                }
                if (obj instanceof String) {
                    valList.add((String) obj);
                    continue;
                }
                if (obj.getClass().isArray()) {
                    valList.addAll(Arrays.<String>asList((String[]) obj));
                }
            }
        }
        return valList;
    }

    /**
     * 获取不定参数
     * ex. 
     *  不定参数项 .add("/:test/:others{-}")
     * @param name  参数名
     * @return
     */
    public List<ParamEntry> getList(String name) {
        Object obj = null;
        for (ParamEntry field : entryList) {
            if (name.equals(field.getKey())) {
                obj = field.getValue();
                if (obj == null) {
                    continue;
                }
                if (obj instanceof List) {
                    return (List<ParamEntry>) obj;
                }

            }
        }
        return null;
    }

    /**
     * 获取不定参数，各键取最后出现的值。
     * ex.
     *  不定参数项 .add("/:test/:others{-}")
     * @param name  参数名
     * @return
     */
    public Map<String, String> getMap(String name) {
        Map<String, String> map = new HashMap<String, String>();
        List<ParamEntry> list = getList(name);
        if (list != null) {
            for (ParamEntry entry : list) {
                map.put(entry.getKey(), (String) entry.getValue());
            }
        }
        return map;
    }
}
