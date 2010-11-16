/*
 * MoXie (SysTem128@GMail.Com) 2009-7-14 17:45:12
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.dispatch;

import org.zoeey.dispatch.PublishAble;
import org.zoeey.route.Query;
import org.zoeey.dispatch.annotations.Mapping;

/**
 *
 * @author MoXie
 */
@Mapping(pattern = {"/article"})
public class ArticlePublish implements PublishAble {

    public void init() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void publish(Query query) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
