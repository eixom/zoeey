/*
 * MoXie (SysTem128@GMail.Com) 2009-5-2 17:02:24
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.ztpl;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

/**
 *
 * @author MoXie(SysTem128@GMail.Com)
 */
public interface TemplateAble {

    /**
     *
     * @param writer
     * @param context
     * @param ztpl 
     * @throws IOException
     */
    public void publish(Writer writer, Map<String, Object> context, Ztpl ztpl) throws IOException;
}
