/*
 * MoXie (SysTem128@GMail.Com) 2009-8-6 16:14:11
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.dispatch;

import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import org.zoeey.dispatch.DispatchConfig.Entry;
import org.zoeey.dispatch.annotations.Mapping;
import org.zoeey.resource.ResourceExceptionMsg;
import org.zoeey.dispatch.exceptions.PublisherClassNoFoundException;
import org.zoeey.dispatch.exceptions.DispatchParserException;

/**
 * 路由配置文件读取
 * @author MoXie
 */
class DispatchConfigLoader {

    /**
     * 锁定创建
     */
    private DispatchConfigLoader() {
    }

    /**
     * 读取路由配置文件
     * @param configFile
     * @throws RouterParserException
     */
    public static void load(File configFile) //
            throws DispatchParserException {
        try {
            DispatchConfigParser parser = new DispatchConfigParser();
            parser.parse(configFile);
            DispatchConfig context = parser.getContext();
            /**
             * init
             */
            for (String init : context.getInitList()) {
                Dispatcher.add(init);
            }
            /**
             * publish
             */
            for (Entry item : context.getPublishList()) {
                Dispatcher.add(item.getPattern(), item.getPublish());
            }
            loadAnnotation(context.getAnnotList());
            /**
             * 排序
             */
            Dispatcher.sort();
        } catch (DispatchParserException ex) {
            throw new DispatchParserException(ex.getMessage(), DispatchConfigLoader.class.getName());
        } catch (IOException ex) {
            throw new DispatchParserException(ex.getMessage(), DispatchConfigLoader.class.getName());
        } catch (ParserConfigurationException ex) {
            throw new DispatchParserException(ex.getMessage(), DispatchConfigLoader.class.getName());
        } catch (PublisherClassNoFoundException ex) {
            throw new DispatchParserException(ex.getMessage(), DispatchConfigLoader.class.getName());
        } catch (SAXException ex) {
            throw new DispatchParserException(ex.getMessage(), DispatchConfigLoader.class.getName());
        }
    }

    /**
     * 解析Annotation声明的路径
     * @param annotList
     * @throws RouterEntryPublisherClassNoFoundException
     */
    @SuppressWarnings("unchecked")
    private static void loadAnnotation(List<String> annotList) //
            throws PublisherClassNoFoundException {
        try {
            Class<PublishAble> clazz = null;
            Mapping mapping = null;
            for (String annot : annotList) {
                clazz = (Class<PublishAble>) Class.forName(annot);
                if (clazz != null //
                        && PublishAble.class.isAssignableFrom(clazz) //
                        && clazz.isAnnotationPresent(Mapping.class)) {
                    mapping = clazz.getAnnotation(Mapping.class);
                    if (mapping.pattern().length > 0) {
                        for (String pattern : mapping.pattern()) {
                            Dispatcher.add(pattern, clazz);
                        }
                    }
                } else {
                    throw new PublisherClassNoFoundException(ResourceExceptionMsg.ROUTER_PUBLISHER_CLASS_NOFOUND_EXCEPTION);
                }
            }
        } catch (ClassNotFoundException ex) {
            throw new PublisherClassNoFoundException(ResourceExceptionMsg.ROUTER_PUBLISHER_CLASS_NOFOUND_EXCEPTION);
        }
    }
}
