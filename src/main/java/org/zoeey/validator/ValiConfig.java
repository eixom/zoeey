/*
 * MoXie (SysTem128@GMail.Com) 2009-8-5 17:04:22
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.validator;

import org.zoeey.util.ParamHelper;

/**
 *
 * @author MoXie
 */
public class ValiConfig {

    private ValiConfig() {
    }
    /**
     * 错误时保留传递进来的值
     */
    public static final int RETAIN = 1;
    /**
     * 当验证错误时使用默认值
     */
    public static final int USEDEFAULT = ParamHelper.genParam(1);
}
