/*
 * MoXie (SysTem128@GMail.Com) 2009-6-19 10:21:19
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.plugins.zsk;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.zoeey.common.ZObject;
import org.zoeey.util.EncryptHelper;
import org.zoeey.util.QueryStringHelper;
import org.zoeey.util.UrlBuilder;

/**
 * <pre>
 * 会话传输工具
 * 注意：会话数据使用字符串类型，需要将不同类型的数据串行化后使用。
 * </pre>
 * @author MoXie(SysTem128@GMail.Com)
 */
public class SessionConveyer {

    private static String PR_KEY = "PRIVATE_KEY_9283701928";
    private static String KEY_SIGN = "sign";
    private static String KEY_SESSION_ID = "sid";
    private static String KEY_DATA = "data";
    /**
     * 会话编号
     * @var String
     */
    private String sessionId;
    /**
     * 数据
     * @var String
     */
    private String data;
    /**
     * 签名
     * @var String
     */
    private String sign;

    /**
     * 
     * @param sessionId
     */
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    /**
     *
     * @param data
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * 获取传输的数据
     * @return
     */
    public String getData() {
        return data;
    }

    /**
     * 会话编号 Session id
     * @return String
     */
    public String getSessionId() {
        return this.sessionId;
    }

    /**
     * 数据签名
     * @return String
     */
    private String sign(String sessionId) {
        if (sessionId == null) {
            sessionId = this.sessionId;
        }
        return EncryptHelper.md5(PR_KEY.concat(sessionId).concat(this.data));
    }

    /**
     * 制取可以传输的ticket
     * @param key
     * @return <type>
     * @throws IOException
     */
    public String getTicket(String key) throws IOException {
        Encryptor encryptor = new Encryptor(key);
        Map<String, String> params = new HashMap<String, String>();
        params.put(KEY_SIGN, sign(null));
        params.put(KEY_SESSION_ID, this.sessionId);
        params.put(KEY_DATA, this.data);
        return encryptor.encrypt(UrlBuilder.build(params));
    }

    /**
     * 分析ticket。
     * @param paramsString
     * @param key
     * @throws IOException
     */
    public void parse(String paramsString, String key) throws IOException {
        Map<String, String> params = QueryStringHelper.toMap(new Encryptor(key).decrypt(paramsString));
        this.sign = ZObject.conv(params.get(KEY_SIGN)).toString();
        this.sessionId = ZObject.conv(params.get(KEY_SESSION_ID)).toString();
        this.data = ZObject.conv(params.get(KEY_DATA)).toString();
    }

    /**
     * 数据验证
     * @param sessionId
     * @return <type>
     */
    public boolean vali(String sessionId) {
        if (sign(sessionId).equals(this.sign)) {
            return true;
        }
        return false;
    }

    /**
     * 数据验证
     * @return <type>
     */
    public boolean vali() {
        if (sign(null).equals(this.sign)) {
            return true;
        }
        return false;
    }
}
