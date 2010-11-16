/*
 * MoXie (SysTem128@GMail.Com) 2009-2-1 15:59:59
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.zdo;

import org.zoeey.constant.EnvConstants;

/**
 * <pre>
 * 分页信息
 * 注意：本类的方法命名较为特殊
 * ex.
 *    MySQL:
 *    sql = "BETWEEN :startRow and :endRow "
 *      :startRow = {@link #getStartRow()}
 *      :endRow = {@link #getEndRow()}
 *
 *    [LIMIT {[offset,] row_count | row_count OFFSET offset}]
 *    sql = "LIMIT :offset,:pageSize "
 *      :offset = {@link #getOffset()}
 *      :pageSize = {@link #getPageSize()}
 * 
 *    H2 database:
 *      sql = "LIMIT :limit OFFSET :offset"
 *      :limit = {@link #getPageSize()}
 *      :offset = {@link #getOffset()}
 * </pre>
 * @see #first()
 * @see #prev()
 * @see #next()
 * @see #last()
 * @see #all()
 * @see #siblings(int) 
 * @author MoXie(SysTem128@GMail.Com)
 */
public class PageSet {

    /**
     * 单页条目
     */
    private int pageSize = 20;
    /**
     * 当前页
     */
    private int current = 1;
    /**
     * 默认起始页
     */
    private final int currentDefault = 1;
    /**
     * 信息数量
     */
    private int recordCount = 0;
    /**
     * 总页数
     */
    private int pageCount = 1;
    /**
     * 起始列
     */
    private int startRow = 0;
    /**
     * 结束列
     */
    private int endRow = 0;

    /**
     * 获取分页信息
     * 
     * @param recordCount 记录总数
     * @param pageSize 单页信息数
     * @param current 当前页
     */
    public PageSet(int recordCount, int pageSize, int current) {
        this.pageSize = pageSize;
        this.current = current;
        this.recordCount = recordCount;
        this.reckonAll();
    }

    /**
     * 获取分页信息<br />
     * 设置记录数前，分页信息将无法计算
     * @see #setRecordCount(int)
     * @param pageSize 单页信息数
     * @param current 当前页
     */
    public PageSet(int pageSize, int current) {
        this.pageSize = pageSize;
        this.current = current;
    }

    /**
     * 设置记录数<br />
     * 分页信息将会重新计算
     * @param recordCount
     */
    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
        this.reckonAll();
    }

    /**
     * 重新计算 页面总数 和 当前页
     */
    private void reckonAll() {
        /**
         * 计算总页数
         */
        this.reckonPageCount();
        /**
         * 计算当前页
         */
        this.reckonCurrent();
        /**
         * 计算起始列
         */
        this.reckonStartRow();
        /**
         * 计算结束列
         */
        this.reckonEndRow();
    }

    /**
     * 计算当前页页码
     */
    private void reckonCurrent() {
        /**
         * 有效性处理
         */
        current = current < pageCount ? current : pageCount;
        /**
         * 默认处理
         */
        current = current > 0 ? current : currentDefault;
    }

    /**
     * 获取当前页页码
     * @return
     */
    public int getCurrent() {
        return current;
    }

    /**
     * 获取当前页页码
     * @return
     */
    public int current() {
        return current;
    }

    /**
     * 计算页面总数
     */
    private void reckonPageCount() {
        this.pageCount = this.recordCount / this.pageSize;
        if ((this.recordCount % this.pageSize) > 0) {
            pageCount = pageCount + 1;
        }
        pageCount = pageCount == 0 ? 1 : pageCount;
    }

    /**
     * 获取页面总数
     * @return
     */
    public int getPageCount() {
        return pageCount;
    }

    /**
     * 获取单页显示条数
     * <pre>
     * ex.
     *    MySQL:
     *    [LIMIT {[offset,] row_count | row_count OFFSET offset}]
     *    sql = "LIMIT :offset,:pageSize "
     *      :offset = {@link #getOffset()}
     *      :pageSize = {@link #getPageSize()}
     *
     *    sql = "BETWEEN :startRow and :endRow "
     *      :startRow = {@link #getStartRow()}
     *      :endRow = {@link #getEndRow()}
     *
     *
     *    H2 database:
     *      sql = "LIMIT :limit OFFSET :offset"
     *      :limit = {@link #getPageSize()}
     *      :offset = {@link #getOffset()}
     * </pre>
     * @return
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * 设置总记录数
     * @return
     */
    public int getRecordCount() {
        return recordCount;
    }

    /**
     * 计算当前页起始行行号
     */
    private void reckonStartRow() {
        int _startRow = this.pageSize * (this.current - 1);
        _startRow = _startRow > 0 ? _startRow : 0;
        this.startRow = _startRow;
    }

    /**
     * 获取起始行行行号
     * <pre>
     * ex.
     *    MySQL:
     *    [LIMIT {[offset,] row_count | row_count OFFSET offset}]
     *    sql = "LIMIT :offset,:pageSize "
     *      :offset = {@link #getOffset()}
     *      :pageSize = {@link #getPageSize()}
     *
     *    sql = "BETWEEN :startRow and :endRow "
     *      :startRow = {@link #getStartRow()}
     *      :endRow = {@link #getEndRow()}
     *
     *
     *    H2 database:
     *      sql = "LIMIT :limit OFFSET :offset"
     *      :limit = {@link #getPageSize()}
     *      :offset = {@link #getOffset()}
     * </pre>
     * @return
     */
    public int getStartRow() {
        return this.recordCount > 0 ? this.startRow + 1 : 0;
    }

    /**
     * 获取起始偏移量
     * <pre>
     * ex.
     *    MySQL:
     *    [LIMIT {[offset,] row_count | row_count OFFSET offset}]
     *    sql = "LIMIT :offset,:pageSize "
     *      :offset = {@link #getOffset()}
     *      :pageSize = {@link #getPageSize()}
     * 
     *    sql = "BETWEEN :startRow and :endRow "
     *      :startRow = {@link #getStartRow()}
     *      :endRow = {@link #getEndRow()}
     *   
     *
     *    H2 database:
     *      sql = "LIMIT :limit OFFSET :offset"
     *      :limit = {@link #getPageSize()}
     *      :offset = {@link #getOffset()}
     * </pre>
     * @return
     */
    public int getOffset() {
        return this.startRow;
    }

    /**
     * 计算当前页结束行行号
     */
    private void reckonEndRow() {
        endRow = startRow + this.pageSize;
        endRow = endRow < recordCount ? endRow : recordCount;
    }

    /**
     * 获取当前页结束行行数
     * <pre>
     * ex.
     *    MySQL:
     *    [LIMIT {[offset,] row_count | row_count OFFSET offset}]
     *    sql = "LIMIT :offset,:pageSize "
     *      :offset = {@link #getOffset()}
     *      :pageSize = {@link #getPageSize()}
     *
     *    sql = "BETWEEN :startRow and :endRow "
     *      :startRow = {@link #getStartRow()}
     *      :endRow = {@link #getEndRow()}
     *
     *
     *    H2 database:
     *      sql = "LIMIT :limit OFFSET :offset"
     *      :limit = {@link #getPageSize()}
     *      :offset = {@link #getOffset()}
     * </pre>
     * @return
     */
    public int getEndRow() {
        return endRow;
    }

    /**
     * 是否有前一页
     * @return
     */
    public boolean hasPrev() {
        return current() > 1;
    }

    /**
     * 是否有下一页页码
     * @return
     */
    public boolean hasNext() {
        return getPageCount() > current;
    }

    /**
     * 是否是最后一页页码
     * @return
     */
    public boolean isLast() {
        return !hasNext();
    }

    /**
     * 是否是第一页页码
     * @return
     */
    public boolean isFirst() {
        return !hasPrev();
    }

    /**
     * 是否有列表当记录数为0时则返回false
     * @return
     */
    public boolean hasList() {
        return getRecordCount() > 0;
    }

    /**
     * 获取第一页页码
     * @return
     */
    public int first() {
        return 1;
    }

    /**
     * 获取前一页页码
     * @return
     */
    public int prev() {
        int prePage = this.current - 1;
        prePage = (prePage >= 1) ? prePage : 1;
        return prePage;
    }

    /**
     * 获取下一页页码
     * @return
     */
    public int next() {
        int nextPage = this.current + 1;
        return (nextPage < this.pageCount) ? nextPage : this.pageCount;
    }

    /**
     * 获取最后页页码
     * @see #getPageCount()
     * @return  
     */
    public int last() {
        return getPageCount();
    }

    /**
     * 获取所有页面列表
     * @return  所有页码组成的数组
     */
    public int[] all() {
        int[] pages;
        if (this.pageCount > 0) {
            pages = new int[this.pageCount];
            int j = 0;
            for (int i = 1; i <= this.pageCount; i++, j++) {
                pages[j] = i;
            }
        } else {
            pages = new int[]{1};
        }
        return pages;
    }

    /**
     * 获取临近页面列表
     * @param count 列举数量，包含自身
     * @return  当页码数小于1时返回仅包含 1 的数组。
     */
    public int[] siblings(int count) {
        int[] pages;
        if (this.pageCount > 0 && count > 0) {
            /**
             * 去除当前页
             */
            count--;
            int startPage = this.current - ((((count % 2) > 0) ? count - 1 : count) / 2);
            int endPage = count + startPage;
            int jumper = 0;
            boolean isContinue;
            /**
             * 可缩小的滑块
             */
            do {
                jumper++;
                isContinue = false;
                /**
                 * 向后走 1 位
                 */
                if (startPage < 1) {
                    startPage++;
                    if (endPage < this.pageCount) {
                        endPage++;
                    }
                    isContinue = true;
                }
                /**
                 * 向前走 1 位
                 */
                if (endPage > this.pageCount) {
                    endPage--;
                    if (startPage > 1) {
                        startPage--;
                    }
                    isContinue = true;
                }
                /**
                 * 防循环锁
                 * 该方法已经严格测试。
                 * 请勿为此举感到担心，纯属极端癖好。
                 */
                if (jumper > EnvConstants.FORCE_JUMPER) {
                    isContinue = false;
                }
            } while (isContinue);
            /**
             *
             */
            pages = new int[endPage - startPage + 1];
            int j = 0;
            for (int i = startPage; i <= endPage; i++, j++) {
                pages[j] = i;
            }
        } else {
            pages = new int[]{1};
        }
        return pages;
    }

    /**
     * 对比页面索引和当前页并显示相应内容
     * @param page  需要对比的页码
     * @param show  页码与当前页<b>相同</b>显示的内容
     * @param elseShow 页码与当前页<b>不同</b>显示的内容
     * @return  对比结果字符串
     */
    public String compare(int page, String show, String elseShow) {
        if (current != page) {
            show = elseShow;
        }
        return show;
    }

    /**
     * 判断是否为当前页
     * @param page  需要对比的页码
     * @return  是否为当前页
     */
    public boolean isCurrent(int page) {
        if (current != page) {
            return false;
        }
        return true;
    }
}
