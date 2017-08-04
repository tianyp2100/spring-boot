package me.loveshare.note2.data.service.impl;

import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import me.loveshare.note2.data.dao.UserInfoMapper;
import me.loveshare.note2.data.entity.common.MyPage;
import me.loveshare.note2.data.entity.common.Page;
import me.loveshare.note2.data.entity.dbo.UserInfo;
import me.loveshare.note2.data.entity.vo.common.ValueSortQuery;
import me.loveshare.note2.data.service.UserInfoService;
import me.loveshare.note2.data.util.EncodeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.io.UnsupportedEncodingException;

/**
 * Created by Tony on 2017/8/4.
 */
@Slf4j
@Service
@Transactional
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public MyPage<UserInfo> list(ValueSortQuery query) {
        Example example = new Example(UserInfo.class);
        Example.Criteria criteria = example.createCriteria();
        //处理分页相关数据
        Integer pageIndex = Page.isEmpty2DefaultValue(query.getPageIndex(), Page.DEFAULT_PAGE_INDEX);
        Integer pageSize = Page.isEmpty2DefaultValue(query.getPageSize(), Page.DEFAULT_PAGE_SIZE);
        query.setPageIndex(pageIndex);
        query.setPageSize(pageSize);
        //模糊查询值
        String serachValue = query.getSerachValue();
        if (!StringUtils.isEmpty(serachValue)) {
            try {
                serachValue = EncodeUtils.decodeStr(serachValue);
            } catch (UnsupportedEncodingException e) {
                log.error("Serach Value encoding failure." + e.getMessage(), e);
                serachValue = null;
            }
            criteria.andLike("compellation", "%" + serachValue + "%");
        }
        example.orderBy("gmt_create").desc();
        PageHelper.startPage(query.getPageIndex(), query.getPageSize());
        return new MyPage(userInfoMapper.selectByExample(example));
    }
}
