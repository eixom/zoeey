/*
 * MoXie (SysTem128@GMail.Com) 2009-8-5 17:48:13
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
 *
 * @author MoXie
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Set {

    /**
     * <pre>
     * 枚举值
     * </pre>
     * @return
     */
    public String[] value() default {};

    /**
     * <pre>
     * 语言序列
     * 可作为语言包元键
     * 默认为长度为0的字符串。
     * 注意：如使用了 {@link MsgSn }则会将长度为0的{@link #msgSn() }替换掉
     * </pre>
     * @return
     */
    public String msgSn() default "";

    /**
     * 本地语言
     * 可以作为默认语言使用
     * @return
     */
    public String msgNative() default "";

    /**
     * 状态标志
     * @return
     */
    public int sign() default 0;

    /**
     * 验证失败后是否保留取得的数据
     * @return
     */
    public boolean retain() default true;
}
