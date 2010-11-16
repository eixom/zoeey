/*
 * MoXie (SysTem128@GMail.Com) 2009-2-1 15:59:59
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.dataBean;

/**
 * 用于缩放Person基类为Person
 * @author MoXie(SysTem128@GMail.Com)
 */
public interface SamplePersonBeanAble extends SampleUnUseAble {

    /**
     *
     * @return
     */
    public int getAge();

    /**
     *
     * @param age
     */
    public void setAge(int age);

    /**
     *
     * @return
     */
    public int getId();

    /**
     *
     * @param id
     */
    public void setId(int id);

    /**
     *
     * @return
     */
    public String getName();

    /**
     *
     * @param name
     */
    public void setName(String name);
}
