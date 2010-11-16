/*
 * MoXie (SysTem128@GMail.Com) 2009-7-31 17:20:54
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
import org.zoeey.constant.EnvConstants;

/**
 *
 * @author MoXie
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Vali
public @interface Accessory {

    /**
     * 当上传多个文件时是否在文件验证失败时是否继续验证
     * @return
     */
    public boolean isSkip() default false;

    /**
     * 
     * @return
     */
    public String[] types() default {};

    /**
     *
     * @return
     */
    public int sizeMin() default 0;

    /**
     * 
     * @return
     */
    public int sizeMax() default EnvConstants.DEFAULT_UPLOAD_FILEMAXSIZE;

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
     *
     * @return
     */
    public String msgSn_type() default "";

    /**
     *
     * @return
     */
    public String msgSn_size() default "";

    /**
     * 本地语言
     * 可以作为默认语言使用
     * @return
     */
    public String msgNative() default "";

    /**
     *
     * @return
     */
    public String msgNative_type() default "";

    /**
     *
     * @return
     */
    public String msgNative_size() default "";

    /**
     * 状态标志
     * @return
     */
    public int sign() default 0;
//    /**
//     * 是否通知全局监督者提取提示信息
//     * @return
//     */
//    public boolean sendMsg() default true;
}
