/*
 * MoXie (SysTem128@GMail.Com) 2009-7-28 17:26:05
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.util;

/**
 *
 * @author MoXie
 */
public class ObjForArrayHelper {

    private String name;
    private int age;

    /**
     *
     * @param name
     * @param age
     */
    public ObjForArrayHelper(String name, int age) {
        this.name = name;
        this.age = age;
    }

    /**
     *
     * @return
     */
    public int getAge() {
        return age;
    }

    /**
     *
     * @param age
     */
    public void setAge(int age) {
        this.age = age;
    }

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

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof ObjForArrayHelper)) {
            return false;
        }

        return this.hashCode() == obj.hashCode();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 37 * hash + this.age;
        return hash;
    }

    @Override
    public String toString() {
        return " name:" + this.name + " age:" + this.age;
    }
}
