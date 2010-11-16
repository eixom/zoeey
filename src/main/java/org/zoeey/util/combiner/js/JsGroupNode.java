/*
 * MoXie (SysTem128@GMail.Com) 2009-3-20 23:25:06
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.util.combiner.js;

/**
 *
 * @author MoXie(SysTem128@GMail.Com)
 */
public class JsGroupNode extends JsListNodeBase
        implements JsListNodeAble {

    /**
     * 昵称
     */
    private String name = null;

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }
}
