package me.loveshare.note1.data.entity.bo.common;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 分页--页面数据封装类
 */
public class Page<T> implements Serializable {

    private static final long serialVersionUID = 7704708223318432628L;

    public static final int DEFAULT_PAGE_INDEX = 1;
    public static final int DEFAULT_PAGE_SIZE = 10;
    private static final int ZERO = 0;
    private static final int MAX = 2147483647;

    //页面数据
    @ApiModelProperty("页面数据")
    private List<T> datas;
    //总页数
    @ApiModelProperty("总页数")
    private int totalPage;
    //总记录数
    @ApiModelProperty("总记录数")
    private int totalCount;
    //页码 --若此值=1，则是第一页，若此值=totalPage，则是最后一页
    @ApiModelProperty("页码 --若此值=1，则是第一页，若此值=totalPage，则是最后一页")
    private int pageIndex = DEFAULT_PAGE_INDEX;
    //每页数据大小
    @ApiModelProperty("每页数据大小")
    private int pageSize = DEFAULT_PAGE_SIZE;
    //当前页数据条数
    @ApiModelProperty("当前页数据条数")
    private int currentPageDatasCount;

    //Constructor
    public Page() {
        super();
    }

    public Page(List<T> datas, int totalPage, int totalCount, int pageIndex, int pageSize, int currentPageDatasCount) {
        super();
        this.datas = datas;
        this.totalPage = totalPage;
        this.totalCount = totalCount;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.currentPageDatasCount = currentPageDatasCount;
    }

    /**
     * 通过分页数据创建分页对象
     *
     * @param total     数据总条数
     * @param list      当前页数据
     * @param pageIndex 页码
     * @param pageSize  页面大小
     */
    public static final <T> Page<T> page(Integer total, List<T> list, Integer pageIndex, Integer pageSize) {
        //数据有效
        List<T> listx = Optional.ofNullable(list).orElseGet(() -> new ArrayList<T>(ZERO));
        int totalx = Optional.ofNullable(total).orElseGet(() -> ZERO);
        int pageIndexx = pageIndex == null || pageIndex.intValue() == ZERO ? DEFAULT_PAGE_INDEX : pageIndex;
        int pageSizex = pageSize == null ? DEFAULT_PAGE_SIZE : pageSize;
        //创建分页对象
        return new Page<T>(list, countpaging((int) totalx, pageSizex), (int) totalx, pageIndexx, pageSizex, listx.size());
    }

    /**
     * --总页数
     *
     * @param count 总页数
     * @param size  页面大小
     */
    public static final int countpaging(int count, int size) {
        return size != ZERO ? (count % size > ZERO ? count / size + DEFAULT_PAGE_INDEX : count / size) : ZERO;
    }

    /**
     * 判断如果值为null或基本类型的默认值则返回默认值，否则返回自己
     */
    public static Integer isEmpty2DefaultValue(Integer arg, Integer defaultVal) {
        return (arg == null || arg < 0 || arg > MAX) ? defaultVal : arg;
    }

    /**
     * 判读当前页数据是否为空
     */
    public boolean currentPageEmpty() {
        return this.currentPageDatasCount == 0;
    }

    //getter and setter
    public List<T> getDatas() {
        return datas;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalCount, int pageSize) {
        if (pageSize != ZERO) {
            if (totalCount % pageSize > ZERO) {
                this.totalPage = totalCount / pageSize + DEFAULT_PAGE_INDEX;
            } else {
                this.totalPage = totalCount / pageSize;
            }
        } else {
            this.totalPage = ZERO;
        }
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurrentPageDatasCount() {
        return currentPageDatasCount;
    }

    public void setCurrentPageDatasCount(int currentPageDatasCount) {
        this.currentPageDatasCount = currentPageDatasCount;
    }
}
