package me.loveshare.note1.data.entity.vo.common;

import java.io.Serializable;

/**
 * Created by Tony on 2017/3/22.
 * 值排序查询对象--其他类可继承扩展使用
 */
public class ValueSortQuery extends SortQuery implements Serializable {

    private static final long serialVersionUID = -8870087755997048415L;

    /**
     * 模糊搜索字符串
     */
    private String serachValue;

    public String getSerachValue() {
        return serachValue;
    }

    public void setSerachValue(String serachValue) {
        this.serachValue = serachValue;
    }
}
