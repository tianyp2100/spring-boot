package me.loveshare.note1.data.entity.vo.common;

import io.swagger.annotations.ApiModelProperty;
import me.loveshare.note1.data.entity.bo.common.Page;

import java.io.Serializable;

/**
 * Created by Tony on 2017/3/22.
 * 分页查询参数. --其他类可继承扩展使用
 */
public class Query implements Serializable {

    private static final long serialVersionUID = 1429886755790072545L;
    /**
     * 页码，默认第1页
     */
    @ApiModelProperty("页码，默认第1页")
    private Integer pageIndex = Page.DEFAULT_PAGE_INDEX;
    /**
     * 页面大小，默认每页10条
     */
    @ApiModelProperty("页面大小，默认每页10条")
    private Integer pageSize = Page.DEFAULT_PAGE_SIZE;
    /**
     * 数据库开始字段
     */
    @ApiModelProperty(value = "数据库分页开始位置", hidden = true)
    private int begin;
    /**
     * 数据库结束字段
     */
    @ApiModelProperty(value = "数据库分页结束位置", hidden = true)
    private int end;

    /**
     * 数据库分页参数处理
     */
    public void initPageParams() {
        this.pageIndex = Page.isEmpty2DefaultValue(this.pageIndex, Page.DEFAULT_PAGE_INDEX);
        this.pageSize = Page.isEmpty2DefaultValue(this.pageSize, Page.DEFAULT_PAGE_SIZE);
        this.begin = (pageIndex - 1) * pageSize;
        this.end = pageSize;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public int getBegin() {
        return begin;
    }

    public void setBegin(int begin) {
        this.begin = begin;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }
}
