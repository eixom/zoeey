/*
 * MoXie (SysTem128@GMail.Com) 2009-1-18 15:20:13
 * 
 *
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.plugins.zsk;

/**
 *
 * @param <T>
 * @author MoXie(SysTem128@GMail.Com)
 */
public interface StoreAble<T> {

    /**
     *
     * @param key
     * @param value
     * @throws Exception
     */
    public void put(String key, T value) throws Exception;

    /**
     *
     * @param key
     * @return
     * @throws Exception
     */
    public T get(String key) throws Exception;
}
