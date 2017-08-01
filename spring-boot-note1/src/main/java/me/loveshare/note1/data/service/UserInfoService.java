package me.loveshare.note1.data.service;

import me.loveshare.note1.data.entity.bo.common.JsonResult;
import me.loveshare.note1.data.entity.dbo.UserInfo;
import me.loveshare.note1.data.entity.vo.common.Query;

import java.util.List;

/**
 * Created by Tony on 2017/7/31.
 * 用户信息服务类
 */
public interface UserInfoService {

    /**
     * 新建
     */
    void save(UserInfo record);

    /**
     * 批量新建
     *
     * @param records 批量新建数据
     * @return 按“records”参数数据的顺序返回插入数据库主键
     */
    List<Integer> saveBatch(List<UserInfo> records);

    /**
     * 编辑
     */
    void update();

    /**
     * 获取信息
     */
    void get();

    /**
     * 获取列表
     */

    JsonResult list(Query query);

    /**
     * 删除
     */
    void remove();
}
