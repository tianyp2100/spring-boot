package me.loveshare.note1.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import me.loveshare.note1.data.entity.bo.common.JsonResult;
import me.loveshare.note1.data.entity.bo.common.JsonResultMethod;
import me.loveshare.note1.data.entity.dbo.T1;
import me.loveshare.note1.data.entity.dbo.T2;
import me.loveshare.note1.data.entity.vo.UserInfoVO;
import me.loveshare.note1.data.entity.vo.common.Query;
import me.loveshare.note1.data.service.TestService;
import me.loveshare.note1.data.service.UserInfoService;
import me.loveshare.note1.data.service.impl.MultiThreadInitData;
import me.loveshare.note1.data.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Created by Tony on 2017/8/1.
 */
@Api(tags = {}, description = "用户简单信息接入API")
@Slf4j
@RestController
@RequestMapping("userinfo")
public class UserInfoApi {

    @Autowired
    private MultiThreadInitData multiThreadInitData;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private TestService testService;


    /**
     * 初始化DB的数据：http://192.168.1.119:5201/note1/userinfo/initdbdatas.json
     */
    @ApiOperation(value = "使用随机数据初始化DB的数据", notes = "http://192.168.1.119:5201/note1/userinfo/initdbdatas")
    @ResponseBody
    @RequestMapping(value = "initdbdatas", produces = "application/json; charset=UTF-8", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonResult initDbDatasC() {
        multiThreadInitData.initData();
        return JsonResultMethod.code_200(DateUtils.timestamp(new Date()) + ": Database table user_info init start...");
    }

    /**
     * 列表数据
     * jsonp: http://192.168.1.119:5201/note1/userinfo/list.json?callback=success
     * json:  http://192.168.1.119:5201/note1/userinfo/list.json
     */
    @ApiOperation(value = "用户简单信息列表", response = UserInfoVO.class)
    @ResponseBody
    @RequestMapping(value = "list", produces = "application/json; charset=UTF-8", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonResult listC(Query query) {
        return userInfoService.list(query);
    }

    @ApiOperation("事务测试")
    @ResponseBody
    @RequestMapping(value = "add", produces = "application/json; charset=UTF-8", method = {RequestMethod.GET, RequestMethod.POST})
    public String addC() {
        T2 t2 = new T2();
        t2.setAge((byte) 15);
        t2.setStar((byte) 5);
        T1 t1 = new T1();
        t1.setNames("卡卡");
        t1.setPhone("15212345678111111111");
        return "Execute resul:" + (testService.save(t1, t2) ? "successful." : "failure.");
    }
}
