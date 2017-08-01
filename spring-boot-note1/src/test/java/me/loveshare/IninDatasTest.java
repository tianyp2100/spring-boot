package me.loveshare;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import me.loveshare.note1.data.constant.DBdefaultConstants;
import me.loveshare.note1.data.entity.dbo.UserInfo;
import me.loveshare.note1.data.service.UserInfoService;
import me.loveshare.note1.data.utils.RandomDataUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Tony on 2017/7/31.
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringBootNote1Application.class)
public class IninDatasTest {

    @Autowired
    private UserInfoService userInfoService;

    @Test
    public void test() {
        for (int j = 0; j < 40000; j++) {
            List<UserInfo> records = new ArrayList<UserInfo>();
            for (int i = 0; i < 1000; i++) {
                UserInfo info = new UserInfo();
                info.setCompellation(RandomDataUtils.compellation());
                info.setSex(RandomDataUtils.sex());
                Date birthday = RandomDataUtils.birthday(1920, 2010);
                info.setBirthday(birthday);
                info.setAge(RandomDataUtils.age(birthday));
                info.setZodiac(RandomDataUtils.zodicaE(birthday));
                info.setConstellation(RandomDataUtils.constellationE(birthday));
                info.setBloodType(RandomDataUtils.bloodType());
                info.setBirthplace(RandomDataUtils.birthplace());
                info.setEduDegree(RandomDataUtils.eduDegree());
                info.setAvatar(RandomDataUtils.avatar());
                info.setAboutMe(RandomDataUtils.aboutMe());
                Date time = new Date();
                info.setGmtCreate(time);
                info.setGmtModified(time);
                info.setIsDeleted(DBdefaultConstants.USING);
                records.add(info);
            }
            List<Integer> ids = userInfoService.saveBatch(records);
            log.info("\n[" + j + "]  " + JSON.toJSONString(ids));
        }

    }
}