/*
 * MoXie (SysTem128@GMail.Com) 2009-2-1 15:59:59
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.zdo.samples;

import java.sql.Types;
import java.util.List;
import org.zoeey.common.ZObject;
import org.zoeey.zdo.FieldItem;
import org.zoeey.zdo.TableEntryBase;
import org.zoeey.zdo.TableItemAble;

/**
 * 一个文章数据表的例子
 * @author MoXie(SysTem128@GMail.Com)
 */
public class SampleArticleTableEntry extends TableEntryBase implements TableItemAble {

//    /**
//     * 表字段数
//     */
//    private int fieldsSize = 4;
    /**
     * 编号
     */
    private FieldItem id = null;
    /**
     * 标题
     */
    private FieldItem title = null;
    /**
     * 内容
     */
    private FieldItem content = null;
    /**
     * 最后更新时间
     */
    private FieldItem editTime = null;
    /**
     * 表名称
     */
    private static String tableName = null;

    static {
        tableName = "T_Article";
    }

    private SampleArticleTableEntry() {
        super.setName(tableName);
        super.setNickName(tableName);
        List fieldsList = super.getFieldList();
        fieldsList.add(id = new FieldItem("id", Types.VARCHAR, new ZObject()));
        fieldsList.add(title = new FieldItem("title", Types.VARCHAR, new ZObject()));
        fieldsList.add(content = new FieldItem("content", Types.VARCHAR, new ZObject()));
        fieldsList.add(editTime = new FieldItem("editTime", Types.VARCHAR, new ZObject()));
//        fieldsList.add(id);
//        fieldsList.add(title);
//        fieldsList.add(content);
//        fieldsList.add(editTime);
    }

//    public int getFieldsSize() {
//        return fieldsSize;
//    }
    /**
     *
     * @param content
     */
    public void setContent(String content) {
        this.content.setValue(new ZObject(content));
    }

    /**
     *
     * @param editTime
     */
    public void setEditTime(long editTime) {
        this.editTime.setValue(new ZObject(editTime));
    }

    /**
     *
     * @param id
     */
    public void setId(int id) {
        this.id.setValue(new ZObject(id));
    }

    /**
     *
     * @param title
     */
    public void setTitle(String title) {
        this.title.setValue(new ZObject(title));
    }

    /**
     *
     * @return
     */
    public static SampleArticleTableEntry newInstance() {
        return new SampleArticleTableEntry();
    }

    /**
     *
     * @return
     */
    public FieldItem getContent() {
        return content;
    }

    /**
     *
     * @return
     */
    public FieldItem getEditTime() {
        return editTime;
    }

    /**
     *
     * @return
     */
    public FieldItem getId() {
        return id;
    }

    /**
     *
     * @return
     */
    public FieldItem getTitle() {
        return title;
    }

    /**
     * 接口内容
     * @return
     */
//    public FieldItem[] getFields() {
//        FieldItem[] fields = new FieldItem[super.getFieldsSize()];
//        fields[0] = id;
//        fields[2] = title;
//        fields[3] = content;
//        fields[4] = editTime;
//        return fields;
//    }
//    public String getName() {
//        return tableName;
//    }
//
//    public String getNickName() {
//        return nickName;
//    }
//
//    public void setNickName(String nickName) {
//        this.nickName = nickName;
//    }
    /**
     * 示例数据
     * @return
     */
    public static TableItemAble[] sampleData() {
        SampleArticleTableEntry tableEntry = null;
        TableItemAble[] tableItems = new SampleArticleTableEntry[5];
        for (int i = 0; i < 5; i++) {
            tableEntry = SampleArticleTableEntry.newInstance();
            tableItems[i] = tableEntry;
            tableEntry.setTitle("title_" + i);
            tableEntry.setContent("content_" + i);
            tableEntry.setEditTime(System.currentTimeMillis() / 1000);
        }
        return tableItems;
    }
}
