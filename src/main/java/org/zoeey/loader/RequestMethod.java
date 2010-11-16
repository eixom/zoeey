/*
 * MoXie (SysTem128@GMail.Com) 2009-2-1 15:59:59
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.loader;

/**
 * 取值方式
 * @author MoXie(SysTem128@GMail.Com)
 */
public enum RequestMethod {

    /**
     * String Integer Long Double Short 可为数组
     */
    REQUEST,
    /**
     * String Integer Long Double Short 可为数组
     */
    GET,
    /**
     * String Integer Long Double Short 可为数组
     */
    POST,
    /**
     * String Integer Long Double Short 可为数组
     */
    COOKIE,
    /**
     * String Integer Long Double Short 不可为数组
     */
    SESSION,
    /**
     * FileItem 可为数组
     */
    FILE,
    /**
     * String Integer Long Double Short 可为数组
     */
    HEADER,
    /**
     *   Long 客户IP  不可为数组
     */
    CLIENT_IP,
    /**
     * 
     */
    PUT
}
