/*
 * MoXie (SysTem128@GMail.Com) 2009-7-29 16:06:07
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.validator;

import java.lang.annotation.Annotation;
import java.util.Map;
import org.zoeey.common.Supervisor;

/**
 *
 * @author MoXie
 */
public interface ValiAble {

    /**
     *
     * @param clazzes
     * @return
     */
    public boolean accept(Class<? extends Annotation>[] clazzes);

    /**
     *
     * @return
     */
    public SwitchLabel swit();

    /**
     *
     * @return
     */
    public boolean isRetain();

    /**
     *
     * @param svisor
     * @param annts
     * @param value
     * @return
     */
    public boolean vali(Supervisor svisor, Map<Class<? extends Annotation>, Annotation> annts, String value);

    /**
     *
     * @param svisor
     * @param annts
     * @param values
     * @return
     */
    public boolean vali(Supervisor svisor, Map<Class<? extends Annotation>, Annotation> annts, String[] values);
}
