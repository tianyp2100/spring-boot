package me.loveshare.note1.data.dao;

import me.loveshare.note1.data.entity.dbo.T1;

public interface T1Mapper {
    int deleteByPrimaryKey(Integer id);

    int insert(T1 record);

    int insertSelective(T1 record);

    T1 selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(T1 record);

    int updateByPrimaryKey(T1 record);
}