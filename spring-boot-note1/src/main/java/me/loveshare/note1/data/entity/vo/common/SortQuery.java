package me.loveshare.note1.data.entity.vo.common;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Tony on 2017/3/22.
 * 分页排序查询参数.--其他类可继承扩展使用
 */
public class SortQuery extends Query implements Serializable {

    private static final long serialVersionUID = -2461779204354844541L;

    /**
     * 排序字段
     */
    private List<String> sortField;
    /**
     * 排序方式(DESC,ASC)
     */
    private List<String> sortOrder;

    public List<String> getSortField() {
        return sortField;
    }

    public void setSortField(List<String> sortField) {
        this.sortField = sortField;
    }

    public List<String> getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(List<String> sortOrder) {
        this.sortOrder = sortOrder;
    }
}
