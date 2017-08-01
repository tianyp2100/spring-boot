package me.loveshare.note1.data.entity.vo.common;

import java.io.Serializable;

/**
 * Created by Tony on 2017/3/22.
 * 值查询对象--其他类可继承扩展使用
 */
public class ValueQuery extends Query implements Serializable {

    private static final long serialVersionUID = 749958517894569766L;
    /**
     * 模糊搜索字符串
     */
    private String searchValue;

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }
}
