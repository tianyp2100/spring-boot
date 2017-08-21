package me.loveshare.note1.data.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.loveshare.note1.data.constant.ConstantDatas;
import me.loveshare.note1.data.dao.UserInfoMapper;
import me.loveshare.note1.data.entity.bo.common.JsonResult;
import me.loveshare.note1.data.entity.bo.common.JsonResultMethod;
import me.loveshare.note1.data.entity.bo.common.Page;
import me.loveshare.note1.data.entity.dbo.UserInfo;
import me.loveshare.note1.data.entity.dto.UserInfoDTO;
import me.loveshare.note1.data.entity.vo.UserInfoVO;
import me.loveshare.note1.data.entity.vo.common.Query;
import me.loveshare.note1.data.service.UserInfoService;
import me.loveshare.note1.data.utils.CollectionUtils;
import me.loveshare.note1.data.utils.DBUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tony on 2017/7/31.
 */
@Slf4j
@Service
@Transactional
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public void save(UserInfo record) {

    }

    @Override
    public List<Integer> saveBatch(List<UserInfo> records) {
        userInfoMapper.batchInsert(records);
        List<Integer> ids = new ArrayList<Integer>();
        for (UserInfo record : records) {
            ids.add(record.getId());
        }
        return ids;
    }

    @Override
    public void update() {

    }

    @Override
    public void get() {

    }

    @Override
    public JsonResult list(Query query) {

        //数据库分页参数处理
        query.initPageParams();

        //数据库查询数据
        Integer total = userInfoMapper.selectUserInfoCount();
        List<UserInfoDTO> list = null;
        if (!DBUtils.numberBlank(total)) {
            list = userInfoMapper.selectUserInfoPageJoinId(query);
//            list = userInfoMapper.selectUserInfoPageLimit(query);
        } else {
            return JsonResultMethod.code_804();
        }

        List<UserInfoVO> voList = changeListData(list);
        //封装页面数据
        Page<UserInfoVO> page = Page.page(total, voList, query.getPageIndex(), query.getPageSize());

        return JsonResultMethod.code_200("加载列表数据成功", page);
    }

    @Override
    public void remove() {

    }

    private List<UserInfoVO> changeListData(List<UserInfoDTO> list) {
        List<UserInfoVO> voList = new ArrayList<UserInfoVO>();
        if (CollectionUtils.isNotEmptyList(list)) {
            for (UserInfoDTO dto : list) {
                UserInfoVO vo = new UserInfoVO();
                vo.setId(dto.getId());
                vo.setCompellation(dto.getCompellation());
                vo.setSex(ConstantDatas.sexMap.get(dto.getSex()));
                vo.setAge(dto.getAge());
                vo.setBirthday(dto.getBirthday());
                vo.setZodiac(ConstantDatas.zodiacMap.get(dto.getZodiac()));
                vo.setConstellation(ConstantDatas.constellationMap.get(dto.getConstellation()));
                vo.setBloodType(dto.getBloodType());
                vo.setBirthplace(ConstantDatas.provinceMap.get(String.valueOf(dto.getBirthplace())));
                vo.setEduDegree(ConstantDatas.eduDegreeMap.get(String.valueOf(dto.getEduDegree())));
                vo.setAvatar(dto.getAvatar());
                vo.setAboutMe(dto.getAboutMe());
                voList.add(vo);
            }
        }
        return voList;
    }
}
