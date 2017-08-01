package me.loveshare.note1.data.dao;

import me.loveshare.note1.data.entity.dbo.T2;

public interface T2Mapper {
    int deleteByPrimaryKey(Integer id);

    int insert(T2 record);

    int insertSelective(T2 record);

    T2 selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(T2 record);

    int updateByPrimaryKey(T2 record);
}