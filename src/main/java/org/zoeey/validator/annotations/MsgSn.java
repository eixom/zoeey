/*
 * MoXie (SysTem128@GMail.Com) 2009-2-1 15:59:59
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.validator.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 通告消息
 * 
 * @author MoXie(SysTem128@GMail.Com)
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Vali
public @interface MsgSn {

    /**
     * <pre>
     * 语言序列
     * 可作为语言包元键
     * </pre>
     * @return
     */
    public String value();

    /**
     * <pre>
     * 本地语言
     * 可以作为默认语言使用
     * </pre>
     * @return
     */
    public String msgNative() default "";

    /**
     * 状态标志
     * @return
     */
    public int sign() default 0; 
}
