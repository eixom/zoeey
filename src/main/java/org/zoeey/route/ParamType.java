/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.zoeey.route;

/**
 *
 * @author MoXie
 */
public enum ParamType {
    /**
     *  ex. action = "list"
     */
    STRING,
    /**
     * ex. id = ["1","2","3"]
     */
    ARRAY,
    /**
     * ex. others = {"key1":"val1","key2":"val2"}
     */
    MAP
}
