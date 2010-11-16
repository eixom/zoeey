/*
 * MoXie (SysTem128@GMail.Com) 2009-1-18 15:52:52
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.plugins.zsk;

import java.io.Serializable;

/**
 *
 * @author MoXie(SysTem128@GMail.Com)
 */
public class UserInfo implements Serializable {

    private static final long serialVersionUID = -1799353278318883351L;
    private String account;
    private Integer age;

    /**
     *
     * @return
     */
    public String getAccount() {
        return account;
    }

    /**
     *
     * @param account
     */
    public void setAccount(String account) {
        this.account = account;
    }

    /**
     *
     * @return
     */
    public Integer getAge() {
        return age;
    }

    /**
     *
     * @param age
     */
    public void setAge(Integer age) {
        this.age = age;
    }
}
