/*
 * MoXie (SysTem128@GMail.Com) 2009-5-2 21:05:00
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.ztpl.compiler;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.zoeey.common.ZObject;
import org.zoeey.constant.EnvConstants;
import org.zoeey.util.BinaryFileHelper;
import org.zoeey.util.FileHelper;
import org.zoeey.util.TextFileHelper;
import org.zoeey.ztpl.TemplateAble;
import org.zoeey.ztpl.Ztpl;
import org.zoeey.ztpl.ZtplConfig;

/**
 *
 * @author MoXie(SysTem128@GMail.Com)
 */
public class CompileHandler {

    /**
     * 已编译文件列表
     */
    private Ztpl ztpl = null;

    /**
     *
     * @param ztpl
     */
    public CompileHandler(Ztpl ztpl) {
        this.ztpl = ztpl;
    }

    /**
     * 检查模版文件是否需要编译
     * 通过文件绝对路径和最后更新时间进行判断
     * @param compileFile
     * @param config
     * @return
     * @throws java.io.IOException
     */
    private boolean isNeedCompile(File tplFile, String classFilePath, ZtplConfig config)
            throws IOException {
        boolean isNeedCompile = true;
        synchronized (tplFile) {
            do {
                /**
                 * 文件不存在
                 */
                if (!tplFile.exists()) {
                    break;
                }
                /**
                 * 检查编译情况
                 */
                if (config.isIsCheckModify()) {
                    File log = new File(classFilePath.concat(".log"));
                    long lastModified = tplFile.lastModified();
                    long logTime = 0L;
                    if (log.exists()) {
                        ZObject.conv(TextFileHelper.read(log)).toLong();
                    }
                    if (lastModified == logTime) {
                        isNeedCompile = false;
                        break;
                    }
                    TextFileHelper.write(log, new ZObject(lastModified).toString());
                }
                isNeedCompile = false;
            } while (false);
        }
        if (EnvConstants.IS_DEBUGING) {
            isNeedCompile = true;
        }
        return isNeedCompile;

    }

    /**
     * 获取编译文件路径
     * @param tplFileName
     * @param compileDir
     * @return
     */
    private String getClassFilePath(File tplFile, String className, File compileDir) {
        StringBuilder strBuilder = new StringBuilder(50);
        strBuilder.append(compileDir.getAbsolutePath());
        strBuilder.append('/');
        strBuilder.append(CompileHelper.getPackName("pack_", tplFile));
        strBuilder.append('/');
        strBuilder.append(className);
        strBuilder.append(".class");
        return FileHelper.backToslash(strBuilder.toString());
    }

    /**
     * 获取模版类
     * @param tplFile
     * @param config
     * @return 编译出错时返回null。
     */
    public TemplateAble getCompiledClass(File tplFile, ZtplConfig config) {
        try {
            ZtplClassLoader classLoader = new ZtplClassLoader();
            String className = CompileHelper.getClassName("Zoeey_tpl_", tplFile);
            String classFilePath = getClassFilePath(tplFile, className, config.getCompileDir());
            File classFile = new File(classFilePath);
            Class<TemplateAble> clazz;
            if (isNeedCompile(tplFile, classFilePath, config)) {
                /**
                 * 编译
                 */
                Compiler compiler = new Compiler(config);
                /**
                 * 输出字节码
                 */
                BinaryFileHelper.write(classFile, compiler.compile(tplFile));
            }

            clazz = classLoader.defineClass(className, classFile);

            /**
             * 加载到ClassLoader
             */
            if (TemplateAble.class.isAssignableFrom(clazz)) {
                // TemplateAble 构造函数没有参数
                try {
                    return clazz.newInstance();
                } catch (InstantiationException ex) {
                    Logger.getLogger(CompileHandler.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(CompileHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(CompileHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }
}
