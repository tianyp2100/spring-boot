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


    @RequestMapping(value = "test", produces = "application/json;charset=UTF-8", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonResult initDbDatasC() {
        return JsonResultMethod.code_200("The request completed successfully.", DateUtils.timestamp(new Date()));
    }
}
