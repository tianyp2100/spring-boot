package me.loveshare.test;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import me.loveshare.SpringBootNote2Application;
import me.loveshare.note2.data.entity.common.MyPage;
import me.loveshare.note2.data.entity.dbo.UserInfo;
import me.loveshare.note2.data.entity.vo.common.ValueSortQuery;
import me.loveshare.note2.data.service.UserInfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringBootNote2Application.class)
public class Application2Test {

    @Autowired
    private UserInfoService userInfoService;

    @Test
    public void testList() {
        long st = System.currentTimeMillis();
        ValueSortQuery query = new ValueSortQuery();
        query.setPageIndex(200);
        query.setPageSize(20);
        MyPage<UserInfo> page = userInfoService.list(query);
        log.info("\n***" + JSON.toJSONString(page));
        long et = System.currentTimeMillis();
        log.info("\nTotal time-consuming:" + (et - st) + "ms.");  //955ms
    }

}





























