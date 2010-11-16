/*
 * MoXie (SysTem128@GMail.Com) 2009-2-1 15:59:59
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.loader.annotations;

import org.zoeey.loader.RequestMethod;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 取值方式
 * @author MoXie(SysTem128@GMail.Com)
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Request {

    /**
     * 表单字段名
     * @return  字段名
     */
    public String name();

    /**
     * 取值方式
     * @see RequestMethod
     * @return  取值方式
     */
    public RequestMethod[] method();
}
