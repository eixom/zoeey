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
 * <pre>
 * 区间整数
 * 包含最大最小值
 * Range(min = 1,max = 3)
 * 1,2,3 均有效
 * 1.2 无效
 * 测试前会测试数据是否为整数
 * </pre>
 * @author MoXie(SysTem128@GMail.Com)
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Range {

    /**
     * 区间最小允许值，默认为 0 。
     * @return
     */
    public long min() default 0;

    /**
     * 区间最大允许值。
     * @return
     */
    public long max();

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
