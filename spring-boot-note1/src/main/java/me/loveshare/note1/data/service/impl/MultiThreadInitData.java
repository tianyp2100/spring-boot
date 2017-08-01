package me.loveshare.note1.data.service.impl;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import me.loveshare.note1.data.constant.DBdefaultConstants;
import me.loveshare.note1.data.entity.dbo.UserInfo;
import me.loveshare.note1.data.service.UserInfoService;
import me.loveshare.note1.data.utils.RandomDataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Tony on 2017/8/1.
 */
@Slf4j
@Component
public class MultiThreadInitData {

    @Autowired
    private UserInfoService userInfoService;

    public void initData() {
        MultiThread mt1 = new MultiThread(userInfoService);
        MultiThread mt2 = new MultiThread(userInfoService);
        MultiThread mt3 = new MultiThread(userInfoService);
        MultiThread mt4 = new MultiThread(userInfoService);
        MultiThread mt5 = new MultiThread(userInfoService);

        Thread t1 = new Thread(mt1);
        t1.setName("t1");
        Thread t2 = new Thread(mt2);
        t2.setName("t2");
        Thread t3 = new Thread(mt3);
        t3.setName("t3");
        Thread t4 = new Thread(mt4);
        t4.setName("t4");
        Thread t5 = new Thread(mt5);
        t5.setName("t5");

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
    }

}

@Slf4j
class MultiThread implements Runnable {

    private UserInfoService userInfoService;

    public MultiThread(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @Override
    public void run() {
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
            log.info("\n[" + Thread.currentThread().getName() + " | " + j + "]  " + JSON.toJSONString(ids));
        }
    }
}
