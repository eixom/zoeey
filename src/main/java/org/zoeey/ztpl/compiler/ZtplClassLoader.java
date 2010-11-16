/*
 * MoXie (SysTem128@GMail.Com) 2009-5-2 21:19:46
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.ztpl.compiler;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.zoeey.constant.EnvConstants;
import org.zoeey.ztpl.TemplateAble;
import org.zoeey.ztpl.ZtplConstant;

/**
 * 类装载工具
 * @author MoXie(SysTem128@GMail.Com)
 */
public class ZtplClassLoader extends ClassLoader {

    /**
     * 类装载工具
     */
    public ZtplClassLoader() {
        super(Thread.currentThread().getContextClassLoader());
    }

    /**
     * 
     * @param name
     * @param code
     * @return
     */
    @SuppressWarnings("unchecked")
    public Class<TemplateAble> defineClass(String name, byte[] code) {
        name = ZtplConstant.CLASS_PACKAGE + name;

        Class<TemplateAble> clazz = (Class<TemplateAble>) findLoadedClass(name);
        if (clazz == null) {
            clazz = (Class<TemplateAble>) defineClass(name, code, 0, code.length);
        }
        return clazz;
    }

    /**
     *
     * @param name
     * @param classFile
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @SuppressWarnings("unchecked")
    public Class<TemplateAble> defineClass(String name, File classFile)//
            throws IOException, ClassNotFoundException {

        name = ZtplConstant.CLASS_PACKAGE + name;
        Class<TemplateAble> clazz = (Class<TemplateAble>) findLoadedClass(ZtplConstant.CLASS_PACKAGE + name);

        if (clazz == null) {
            InputStream fis = null;
            DataInputStream dis = null;
            try {
                fis = new FileInputStream(classFile);
                dis = new DataInputStream(fis);
                byte[] buffer = new byte[EnvConstants.BUFFER_BYTE_SIZE];
                int read = 0;
                int i = 0;
                while ((read = dis.read(buffer)) != -1) {
                    defineClass(name, buffer, i * EnvConstants.BUFFER_BYTE_SIZE, read);
                    i++;
                }
                clazz = (Class<TemplateAble>) loadClass(name);
            } finally {
                if (fis != null) {
                    fis.close();
                    fis = null;
                }
                if (dis != null) {
                    dis.close();
                    dis = null;
                }
            }
        }
        return clazz;
    }
}
