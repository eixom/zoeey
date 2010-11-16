/*
 * MoXie (SysTem128@GMail.Com) 2010-3-16 13:36:23
 * 
 * Copyright &copy; 2008-2010 Zoeey.Org . All rights are reserved.
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.ztpl.compiler;

import java.io.File;
import org.zoeey.util.ArrayHelper;
import org.zoeey.util.EncryptHelper;
import org.zoeey.util.FileHelper;
import org.zoeey.util.StringHelper;

/**
 * 编译辅助工具
 * @author MoXie
 */
public class CompileHelper {

    /**
     * 通过文件路径制取 包名
     * @param tplFile   模板文件
     * @return  包名
     */
    public static String getPackName(String prefix, File tplFile) {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(prefix);
        String dir = FileHelper.backToslash(tplFile.getParent());
        if (dir.indexOf("/") > -1) {
            String[] strs = StringHelper.split(dir, '/');
            dir = ArrayHelper.join(ArrayHelper.reverse(strs), "/");
            strs = null;
        }
        char ch = Character.UNASSIGNED;
        int len = dir.length();
        for (int i = 0; i < len; i++) {
            if (i > 200) {
                break;
            }
            ch = dir.charAt(i);
            if (Character.isJavaIdentifierPart(ch)) {
                strBuilder.append(ch);
            } else {
                strBuilder.append('_');
            }
        }
        return strBuilder.toString();
    }

    /**
     * 通过文件路径制取 类名
     * @param tplFile 模板文件
     * @return 类名
     */
    public static String getClassName(String prefix, File tplFile) {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(prefix);
        String str = tplFile.getName();
        char ch = Character.UNASSIGNED;
        int len = str.length();
        for (int i = 0; i < len; i++) {
            if (i > 50) {
                break;
            }
            ch = str.charAt(i);
            if (Character.isJavaIdentifierPart(ch)) {
                strBuilder.append(ch);
            } else {
                strBuilder.append('_');
            }
        }
        strBuilder.append(EncryptHelper.md5(tplFile.getAbsolutePath()));
        return strBuilder.toString();
    }
}
