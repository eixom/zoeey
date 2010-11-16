/*
 * MoXie (SysTem128@GMail.Com) 2009-2-1 15:59:59
 * 
 * Copyright &copy; 2008-2010 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.common;

/**
 * 版本信息
 * @author MoXie(SysTem128@GMail.Com)
 */
public final class Version {

    /**
     * 锁定创建
     */
    private Version() {
    }
    /**
     * 当前版本
     */
    public static final String VERSION = "Zoeey 0.2";

    /**
     * 比较版本
     * @param version   需要进行对比的版本号
     * @return 1 较新 0 相同 －1 较旧
     */
    public static int compare(String version) {
        if (version == null) {
            return -1;
        }

        if (VERSION.equals(version)) {
            return 0; // 同一版本
        }
        int currentVersionLen = VERSION.length();
        char[] currentVersinChars = VERSION.toCharArray();
        char[] versionChars = version.toCharArray();
        char ch = 0;
        char chv = 0;
        int versLen = versionChars.length;
        for (int i = 0; i < versLen; i++) {
            ch = versionChars[i];
            if (ch == '.') {
                continue;
            }
            if (i < currentVersionLen) {
                chv = currentVersinChars[i];
                chv = chv == '.' ? 0 : chv;
            } else {
                chv = 0;
            }

            if (ch == chv) { // 相同
                continue;
            }
            if (ch > chv) {
                return 1; // 较新
            } else {
                return -1; // 较旧
            }

        }
        return 0;
    }

    /**
     * 是否为当前版本或较新版本
     * @param version   需要进行对比的版本号
     * @return <b>true</b> 为当前版本或较新 ，<b>false</b> 早于当前版本，较旧。
     */
    public static boolean equalOrlater(String version) {
        return compare(version) > -1;
    }
}
