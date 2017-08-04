package me.loveshare.note2.data.service;

import me.loveshare.note2.data.entity.common.MyPage;
import me.loveshare.note2.data.entity.dbo.UserInfo;
import me.loveshare.note2.data.entity.vo.common.ValueSortQuery;

/**
 * Created by Tony on 2017/8/4.
 */
public interface UserInfoService {


    /**
     * 获取列表
     */
    MyPage<UserInfo> list(ValueSortQuery query);
}
