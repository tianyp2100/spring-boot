package me.loveshare.note5.web.api;

import lombok.extern.slf4j.Slf4j;
import me.loveshare.note5.data.entity.bo.common.JsonResult;
import me.loveshare.note5.data.entity.bo.common.JsonResultMethod;
import me.loveshare.note5.data.util.DateUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Created by Tony on 2017/8/5.
 */
@Slf4j
@RestController
public class TestDataApi extends BaseApi {


    /**
     * 协议测试
     * http:  http://192.168.1.119:5205/note5/test.json
     * https: https://192.168.1.119:7205/note5/test.json
     * <p>
     * User-Access-Args:{"protocol":"HTTP/1.1(http)","ip":"192.168.1.119","port":"64894","method":"GET","url":"/note5/test.json","user-agent":"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36","uAT":"null"}
     * User-Access-Args:{"protocol":"HTTP/1.1(https)","ip":"192.168.1.119","port":"64910","method":"GET","url":"/note5/test.json","user-agent":"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36","uAT":"null"}
     */
    @RequestMapping(value = "test", produces = "application/json;charset=UTF-8", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonResult initDbDatasC() {
        return JsonResultMethod.code_200("The request completed successfully.", DateUtils.timestamp(new Date()));
    }
}
