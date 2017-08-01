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
}
