/*
 * MoXie (SysTem128@GMail.Com) 2009-3-20 6:26:52
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.util.combiner.js;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.zoeey.constant.SchemaConstants;
import org.zoeey.util.StringHelper;

/**
 * Js容器配置解释器
 * @author MoXie(SysTem128@GMail.Com)
 */
public class JsConfigParser {

    private JsContext context;

    /**
     * 初始化
     */
    public JsConfigParser() {
        context = new JsContext();
    }

    /**
     * 获取Js资源
     * @return
     */
    public JsContext getContext() {
        return context;
    }

    /**
     * 解析文件
     * @param configFile 
     * @throws org.xml.sax.SAXException
     * @throws javax.xml.parsers.ParserConfigurationException
     * @throws java.io.IOException
     */
    public void parse(File configFile) //
            throws SAXException, ParserConfigurationException, IOException {

        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        domFactory.setNamespaceAware(true);
        DocumentBuilder builder = domFactory.newDocumentBuilder();
        Document doc = builder.parse(configFile);
        /**
         * 解析顺序
         * init
         * fileRoot
         * safeRoot
         * forceFile
         * redirect
         * single
         * groups
         */
        /**
         * init
         */
        parseInit(doc);
        /**
         * fileRoot
         */
        parseFileRoot(doc);
        /**
         * safeRoot
         */
        parseSafeRoot(doc);
        /**
         * forceFile
         */
        parseForceFile(doc);
        /**
         * redirect
         */
        parseRedirect(doc);
        /**
         * single
         */
        parseSingle(doc);
        /**
         * groups
         */
        parseGroups(doc);
    }

    /**
     * 分析 root/init/
     * @param doc
     * @return 根路径无法解析时返回 null
     */
    private void parseInit(Document doc) {
        NodeList nodeList = doc.getElementsByTagNameNS(SchemaConstants.SCHEMA_JSCONTAINERCONFIG, "init");
        Node cNode;
        if (nodeList.getLength() > 0) {
            cNode = nodeList.item(0);
            if (cNode != null) {
                nodeList = cNode.getChildNodes();
                int nodeLen = nodeList.getLength();
                for (int i = 0; i < nodeLen; i++) {
                    cNode = nodeList.item(i);
                    if ("charset".equals(cNode.getLocalName())) {
                        parseCharset(cNode);
                    }
                }
            }
        }
    }

    /**
     * 解析  root/init/charset
     * @param sortNode
     */
    private void parseCharset(Node sortNode) {
        Node cNode = sortNode.getFirstChild();
        if (cNode != null) {
            context.setCharset(StringHelper.trim(cNode.getNodeValue()));
        }
    }

    /**
     * 分析出根路径
     * @param doc 
     * @return 根路径无法解析时返回 null
     */
    private void parseFileRoot(Document doc) {
        NodeList nodeList = doc.getElementsByTagNameNS(SchemaConstants.SCHEMA_JSCONTAINERCONFIG, "fileRoot");
        Node cNode;
        if (nodeList.getLength() > 0) {
            cNode = nodeList.item(0).getFirstChild();
            if (cNode != null) {
                context.setFileRoot(cNode.getNodeValue());
            }
        }
    }

    /**
     * 安全路径 解析
     * @param doc
     * @return 无安全路径时返回 {@code new ArrayList<JsFile>()}
     */
    private void parseSafeRoot(Document doc) {
        context.setSafeRootList(parseListChilds(doc.getElementsByTagNameNS(SchemaConstants.SCHEMA_JSCONTAINERCONFIG, "safeRoot")));
    }

    /**
     * 强制加载文件 解析
     * @param doc
     * @return 无强制加载文件时返回 {@code new ArrayList<JsFile>()}
     */
    private void parseForceFile(Document doc) {
        context.setForceFileList(parseListChilds(doc.getElementsByTagNameNS(SchemaConstants.SCHEMA_JSCONTAINERCONFIG, "forceFile")));
    }

    /**
     * 自动转向文件 解析
     * @param doc
     * @return 无文件时返回 {@code new ArrayList<JsFile>()}
     */
    private void parseRedirect(Document doc) {
        context.setRedirectList(parseListChilds(doc.getElementsByTagNameNS(SchemaConstants.SCHEMA_JSCONTAINERCONFIG, "redirect")));
    }

    /**
     * 单独文件 解析
     * @param doc
     * @return 无文件时返回 {@code new ArrayList<JsFile>()}
     */
    private void parseSingle(Document doc) {
        context.setSingleFileMap(parseListChilds(doc.getElementsByTagNameNS(SchemaConstants.SCHEMA_JSCONTAINERCONFIG, "single")));
    }

    /**
     * 组文件 解析
     * @param doc
     * @return 无文件时返回 {@code new ArrayList<JsFile>()}
     */
    private void parseGroups(Document doc) {
        context.setGroupFileMap(parseGroupsChilds(doc.getElementsByTagNameNS(SchemaConstants.SCHEMA_JSCONTAINERCONFIG, "groups")));
    }

    /**
     * 解释列表节点的集合
     * {@code
     *  <zo:single> <<<--->>
     *      <zo:file name="articleCom">article/common.js</zo:file>
     *      <zo:file name="articleEdit" fileRoot="/webRoot/js/">article/edit.js</zo:file>
     *      <zo:file name="specialPage_nationalDay">@/webRoot/special/script/edit.js</zo:file>
     * </zo:single>
     * <zo:single fileRoot="/webRoot/Script/edition2.0/"> <<<--->>
     *      <zo:file name="articleCom_2_0">article/common.js</zo:file>
     *      <zo:file name="articleEdit_2_0">article/edit.js</zo:file>
     * </zo:single>
     * }
     * @param listNode 列表节点 集合 {@code [<single>,<single>] or [<redirect>,<redirect>]}
     * @param context Js资源
     * @return
     */
    private List<JsFile> parseListChilds(NodeList listNodeList) {
        List<JsFile> jsFileList = new ArrayList<JsFile>();
        do {
            /**
             * 没有设定安全目录
             */
            int nodeCount = listNodeList.getLength();
            if (nodeCount < 1) {
                break;
            }
            /**
             * <single> or <redirect> ...
             */
            Node parentNode;
            for (int i = 0; i < nodeCount; i++) {
                parentNode = listNodeList.item(i);
                /**
                 * 将父级节点内的 file 节点全部转换为JsFileObject并放入JsFileListObject
                 * <file>.parent() -> JsFileListObject
                 * <redirect> -> JsFileListObject
                 */
                parseListNode(jsFileList, parentNode);
            }
        } while (false);
        return jsFileList;
    }

    /**
     * 解释 分组 文件列表 的集合
     * {@code
     * <zo:groups>    <<<--->>
     *      <zo:group name="article.edit">
     *           <zo:file>article/common.js</zo:file>
     *           <zo:file>article/edit.js</zo:file>
     *      </zo:group>
     *      <zo:group name="customer.default.article.edit" fileRoot="{myDir}default/article/">
     *          <zo:file>common.js</zo:file>
     *          <zo:file>edit.js</zo:file>
     *      </zo:group>
     * </zo:groups>
     * <zo:groups fileRoot="/manage/"> <<<--->>
     *      <zo:group name="manage.article.edit">
     *           <zo:file>article/common.js</zo:file>
     *           <zo:file>article/edit.js</zo:file>
     *      </zo:group>
     * </zo:groups>
     * }
     * @param groupsNodeList 列表节点 集合 [<groups>,<groups>]
     * @param context Js资源
     * @return
     */
    private Map<String, List<JsFile>> parseGroupsChilds(NodeList groupsNodeList) {
        /**
         * bugfix:group中的文件有序。
         */
        Map<String, List<JsFile>> groupFileMap = new LinkedHashMap<String, List<JsFile>>();
        List<JsFile> jsFileList;
        do {
            /**
             * [<groups>,<groups>].length
             */
            int groupsNodeCount = groupsNodeList.getLength();
            int groupNodeCount = 0;
            if (groupsNodeCount < 1) {
                break;
            }
            Node groupsNode;
            NodeList groupNodeList;
            Node groupNode;
            JsGroupNode jsGroupNode;
            for (int i = 0; i < groupsNodeCount; i++) {
                /**
                 *  <groups>
                 */
                groupsNode = groupsNodeList.item(i);
                /**
                 * [<group> || <unknow>,<group> || <unknow>]
                 */
                groupNodeList = groupsNode.getChildNodes();
                groupNodeCount = groupNodeList.getLength();
                if (groupNodeCount < 1) {
                    continue;
                }
                for (int j = 0; j < groupNodeCount; j++) {
                    /**
                     * <group> || <unknow>
                     */
                    groupNode = groupNodeList.item(j);
                    if (!"group".equals(groupNode.getLocalName())) {
                        continue;
                    }
                    jsFileList = new ArrayList<JsFile>();
                    /**
                     * <group> -> JsGroupNodeObject
                     */
                    jsGroupNode = parseJsGroupNode(groupNode);
                    /**
                     * 将group下的<file> 转换为 JsFileObject 并填充如 JsFileListObject
                     * <group>:[<file>,<file>] -> List[JsFileObject,JsFileObject]
                     */
                    parseListNode(jsFileList, groupNode);
                    /**
                     * 以group.name属性为键将JsFileList存放起来
                     */
                    groupFileMap.put(jsGroupNode.getName(), jsFileList);
                }
            }
        } while (false);
        return groupFileMap;
    }

    /**
     *  解析 flie 列表节点为为JsFile并填充入JsFileList
     * <pre>
     * {@code
     *  <zo:single>
     *      <zo:file name="articleCom">article/common.js</zo:file> <<<--->>
     *      <zo:file name="articleEdit" fileRoot="/webRoot/js/">article/edit.js</zo:file> <<<--->>
     *      <zo:file name="specialPage_nationalDay">@/webRoot/special/script/edit.js</zo:file> <<<--->>
     * </zo:single>
     * }
     * </pre>
     * @param jsFileList 列表资源 {@code new {[JsFileObject,JsFileObject]}    }
     * @param parentNode 列表节点  {@code <file>.parent() ex. <single> or <redirect> }
     * @param context Js资源
     */
    private void parseListNode(List<JsFile> jsFileList, Node parentNode) {
        do {
            /**
             * 子集 file
             * <file>.parent() -> [<file>,<file>]
             */
            NodeList fileNodeList = parentNode.getChildNodes();
            int fileNodeCount = fileNodeList.getLength();
            if (fileNodeCount < 1) {
                break;
            }
            Node fileNode; // <file>
            JsFile jsFile; // JsFileObject
            /**
             * <file>.parent() -> JsListObject
             * 如：<single> -> JsListObject
             * 用于将参数递入子集 即是 <file>
             */
            JsListNodeAble jsList = parseJsListNode(parentNode);
            for (int i = 0; i < fileNodeCount; i++) {
                fileNode = fileNodeList.item(i);
                if (!"file".equals(fileNode.getLocalName())) {
                    continue;
                }
                /**
                 * <file> -> JsFileObject
                 */
                jsFile = parseJsFile(fileNode, context, jsList);
                if (jsFile.getName() != null) {
                    context.addNamedFile(jsFile);
                }
                jsFileList.add(jsFile);
            }
        } while (false);
    }

    /**
     * 分析 JsList 数据类型的参数
     * @param jsListNode
     * @param attrMap
     */
    private void parserJsListNodeAttr(JsListNodeAble jsListNode, NamedNodeMap attrMap) {
        // fileRoot
        Node attr = attrMap.getNamedItem("fileRoot");
        if (attr != null) {
            jsListNode.setFileRoot(attr.getNodeValue());
        }
        // isDebug
        attr = attrMap.getNamedItem("isDebug");
        if (attr != null) {
            jsListNode.setIsDebug("true".equals(attr.getNodeValue()));
        }
        // charset
        attr = attrMap.getNamedItem("charset");
        if (attr != null) {
            jsListNode.setCharset(attr.getNodeValue());
        }
    }

    /**
     * 解析 包含 isDebug fileRoot 的file节点父级
     * <pre>
     *  {@code
     *      <zo:single></zo:single> -> JsListNodeObject
     *      <zo:redirect></zo:redirect> -> JsListNodeObject
     * }
     * </pre>
     * @param listNode 列表节点
     * @param context  Js资源
     * @return  
     */
    private JsListNodeAble parseJsListNode(Node listNode) {

        JsListNodeAble jsListNode = null;
        do {
            /**
             * 判断节点，节点内容为空则直接跳出
             */
            Node fcNode; // first child node
            fcNode = listNode.getFirstChild();
            if (fcNode == null) {
                break;
            }
            jsListNode = new JsListNode();
            jsListNode.setFileRoot(context.getFileRoot());
            /**
             * 属性处理
             */
            NamedNodeMap attrMap = null;
            attrMap = listNode.getAttributes();
            if (attrMap.getLength() < 1) {
                break;
            }
            parserJsListNodeAttr(jsListNode, attrMap);
        } while (false);
        return jsListNode;
    }

    /**
     * 解析 包含 isDebug fileRoot 的file节点父级
     * <pre>
     * {@code
     *    <zo:group></zo:group> -> JsGroupNodeObject // 比JsListNode多了name属性
     * }
     * </pre>
     * @param listNode 列表节点
     * @param context  Js资源
     * @return
     */
    private JsGroupNode parseJsGroupNode(Node groupEl) {

        JsGroupNode jsGroupNode = null;
        do {
            /**
             * 判断节点，节点内容为空则直接跳出
             */
            Node fcNode; // first child node
            fcNode = groupEl.getFirstChild();
            if (fcNode == null) {
                break;
            }
            jsGroupNode = new JsGroupNode();
            jsGroupNode.setFileRoot(context.getFileRoot());
            /**
             * 属性处理
             */
            NamedNodeMap attrMap = null;
            attrMap = groupEl.getAttributes();
            if (attrMap.getLength() < 1) {
                break;
            }
            Node attr;
            // name
            attr = attrMap.getNamedItem("name");
            if (attr != null) {
                jsGroupNode.setName(attr.getNodeValue());
            }
            // fileRoot
            parserJsListNodeAttr(jsGroupNode, attrMap);
        } while (false);
        return jsGroupNode;
    }

    /**
     * 解析 file 元素
     * <pre>
     *  {@code
     *      <file> -> JsFileObject
     *   }
     * </pre>
     * @param fileNode 文件节点
     * @param context   Js资源
     * @param parentNode 父级节点   {@code <single>,<redirect>,<group> 等}
     * @return JsFileObject 节点为空则返回null
     */
    private JsFile parseJsFile(Node fileNode//
            , JsContext context, JsListNodeAble parentNode) {
        JsFile jsFile = null;
        do {
            /**
             * 判断节点，节点内容为空则直接跳出
             */
            Node fcNode; // first child node
            fcNode = fileNode.getFirstChild();
            if (fcNode == null) {
                break;
            }
            jsFile = new JsFile(fcNode.getNodeValue());
            jsFile.setFileRoot(context.getFileRoot());
            if (parentNode != null) {
                /**
                 * 调试，根目录，字符集
                 * 继承与父级元素
                 */
                jsFile.setIsDebug(parentNode.isIsDebug());
                jsFile.setFileRoot(parentNode.getFileRoot());
                jsFile.setCharset(parentNode.getCharset());
            }
            /**
             * 属性处理
             */
            NamedNodeMap attrMap = null;
            attrMap = fileNode.getAttributes();
            if (attrMap.getLength() < 1) {
                break;
            }
            Node attr;
            // name
            attr = attrMap.getNamedItem("name");
            if (attr != null) {
                jsFile.setName(attr.getNodeValue());
            }
            // fileRoot
            attr = attrMap.getNamedItem("fileRoot");
            if (attr != null) {
                jsFile.setFileRoot(attr.getNodeValue());
            }
            // isStatic
            attr = attrMap.getNamedItem("isStatic");
            if (attr != null) {
                jsFile.setIsStatic("true".equals(attr.getNodeValue()));
            }
            // isDebug
            attr = attrMap.getNamedItem("isDebug");
            if (attr != null) {
                jsFile.setIsDebug("true".equals(attr.getNodeValue()));
            }
            // charset
            attr = attrMap.getNamedItem("charset");
            if (attr != null) {
                jsFile.setCharset(attr.getNodeValue());
            }
        } while (false);
        return jsFile;
    }
}
