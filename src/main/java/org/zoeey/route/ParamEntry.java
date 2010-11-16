package org.zoeey.route;
/**
 * Router参数项
 * @author MoXie
 */
public class ParamEntry {

    private String key;
    private Object value;

    public ParamEntry(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public Object getValue() {
        return value;
    }
}
