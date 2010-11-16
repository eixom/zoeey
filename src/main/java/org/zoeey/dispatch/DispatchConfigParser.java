/*
 * MoXie (SysTem128@GMail.Com) 2009-8-6 10:30:52
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.dispatch;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.zoeey.common.ZObject;
import org.zoeey.constant.SchemaConstants;
import org.zoeey.resource.ResourceExceptionMsg;
import org.zoeey.dispatch.exceptions.DispatchParserException;
import org.zoeey.util.StringHelper;

/**
 * 路由配置文件解析
 * @author MoXie
 */
class DispatchConfigParser {

    /**
     *  路由配置文件
     */
    private DispatchConfig config;

    /**
     * Router配置文件解析
     */
    public DispatchConfigParser() {
        config = new DispatchConfig();
    }

    /**
     * 分析配置文件
     * @param configFile
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     * @throws RouterParserException
     */
    public void parse(File configFile)//
            throws ParserConfigurationException, SAXException, IOException, DispatchParserException {

        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        domFactory.setNamespaceAware(true);
        DocumentBuilder builder = domFactory.newDocumentBuilder();
        Document doc = builder.parse(configFile);
        /**
         * 解析顺序
         * init/ignoreCaseNode
         * entrys/packs/pack
         * entrys/entry
         */
        /**
         * init
         */
        parseInit(doc);
        /**
         * entrys
         */
        parseEntrys(doc);
    }

    /**
     * 分析 root/init/
     * @param doc
     * @return 根路径无法解析时返回 null
     */
    private void parseInit(Document doc) {
        NodeList nodeList = doc.getElementsByTagNameNS(SchemaConstants.SCHEMA_DISPATCHCONFIG, "init");
        Node cNode;
        if (nodeList.getLength() > 0) {
            cNode = nodeList.item(0);
            if (cNode != null) {
                nodeList = cNode.getChildNodes();
                int nodeLen = nodeList.getLength();
                for (int i = 0; i < nodeLen; i++) {
                    cNode = nodeList.item(i);
                    if ("ignoreCase".equals(cNode.getLocalName())) {
                        parseIgnoreCase(cNode);
                    }
                }
            }
        }
    }

    /**
     * 解析  root/init/ignoreCase
     * @param ignoreCaseNode
     */
    private void parseIgnoreCase(Node ignoreCaseNode) {
        Node cNode = ignoreCaseNode.getFirstChild();
        if (cNode != null) {
            config.setIgnoreCase(ZObject.conv(StringHelper.trim(cNode.getNodeValue())).toBoolean());
        }
    }

    /**
     * 分析 root/entrys/
     * @param doc
     * @return 根路径无法解析时返回 null
     */
    private void parseEntrys(Document doc) throws DispatchParserException {
        NodeList nodeList = doc.getElementsByTagNameNS(SchemaConstants.SCHEMA_DISPATCHCONFIG, "entrys");
        Node cNode;
        if (nodeList != null && nodeList.getLength() > 0) {
            cNode = nodeList.item(0);
            boolean isAnnotsParsed = false;
            if (cNode != null) {
                nodeList = cNode.getChildNodes();
                int nodeLen = nodeList.getLength();
                for (int i = 0; i < nodeLen; i++) {
                    cNode = nodeList.item(i);
                    if (!isAnnotsParsed) {
                        if ("annots".equals(cNode.getLocalName())) {
                            parseAnnots(cNode);
                            isAnnotsParsed = true;
                            continue;
                        }
                    }
                    if ("entry".equals(cNode.getLocalName())) {
                        parseEntry(cNode);
                        continue;
                    }
                }
            }
        }
    }

    /**
     * 解析  root/entrys/packs
     * @param ignoreCaseNode
     */
    private void parseAnnots(Node annotsNode) {
        NodeList nodeList = annotsNode.getChildNodes();
        Node cNode;
        if (nodeList != null && nodeList.getLength() > 0) {
            //
            int nodeLen = nodeList.getLength();
            for (int i = 0; i < nodeLen; i++) {
                cNode = nodeList.item(i);
                if ("annot".equals(cNode.getLocalName())) {
                    parseAnnot(cNode);
                }
            }
        }
    }

    /**
     * root/entrys/packs/pack
     * @param packNode
     */
    private void parseAnnot(Node annotNode) {
        do {
            /**
             * 判断节点，节点内容为空则直接跳出
             */
            Node fcNode; // first child node
            fcNode = annotNode.getFirstChild();
            if (fcNode == null) {
                break;
            }
            config.addAnnot(fcNode.getNodeValue());
            /**
             * package load
             */
            /**
             * 属性处理
             */
            /*
            PackItem packItem = new PackItem();
            packItem.setPack(fcNode.getNodeValue());
            
            NamedNodeMap attrMap = null;
            attrMap = annotNode.getAttributes();
            if (attrMap.getLength() == 1) {
            Node attr;
            // name
            attr = attrMap.getNamedItem("recursion");
            if (attr != null) {
            packItem.setRecursion(ZObject.conv(StringHelper.trim(attr.getNodeValue())).toBoolean());
            }
            }
            context.addPack(packItem);
             */
        } while (false);
    }

    /**
     * 解析  root/entrys/entry
     * @param ignoreCaseNode
     */
    private void parseEntry(Node entryNode) throws DispatchParserException {
        NodeList nodeList = entryNode.getChildNodes();
        Node cNode;
        List<String> patternList = new ArrayList<String>();
        String publish = null;
        boolean isPublished = false;
        if (nodeList != null && nodeList.getLength() > 0) {
            //
            int nodeLen = nodeList.getLength();
            for (int i = 0; i < nodeLen; i++) {
                cNode = nodeList.item(i);
                if ("pattern".equals(cNode.getLocalName())) {
                    cNode = cNode.getFirstChild();
                    if (cNode != null) {
                        patternList.add(cNode.getNodeValue());
                    }
                    continue;
                }
                if (!isPublished) {
                    if ("publish".equals(cNode.getLocalName())) {
                        cNode = cNode.getFirstChild();
                        if (cNode != null) {
                            publish = cNode.getNodeValue();
                        }
                        isPublished = true;
                    }
                }
            }
        }
        if (publish == null) {
            throw new DispatchParserException(ResourceExceptionMsg.ROUTERPARSER_ENTRYNOPUBLISHEXCEPTION);
        }
        if (patternList.isEmpty()) {
            config.addInit(publish);
            return;
        }
        for (String pattern : patternList) {
            config.addPublish(pattern, publish);
        }
    }

    /**
     * 
     * @return
     */
    public DispatchConfig getContext() {
        return config;
    }
}
