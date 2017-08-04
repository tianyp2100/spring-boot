package me.loveshare.note2.api;

import lombok.extern.slf4j.Slf4j;
import me.loveshare.note2.data.entity.common.JsonResult;
import me.loveshare.note2.data.entity.common.JsonResultMethod;
import me.loveshare.note2.data.entity.common.MyPage;
import me.loveshare.note2.data.entity.dbo.UserInfo;
import me.loveshare.note2.data.entity.vo.common.ValueSortQuery;
import me.loveshare.note2.data.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Tony on 2017/8/1.
 */
@Slf4j
@RestController
@RequestMapping("userinfo")
public class UserInfoApi {

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 列表数据
     * json:  http://192.168.1.119:5202/note2/userinfo/list.json
     */
    @ResponseBody
    @RequestMapping(value = "list", produces = "application/json; charset=UTF-8", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonResult listC(ValueSortQuery query) {
        try {
            MyPage<UserInfo> page = userInfoService.list(query);
            return page == null || page.currentPageEmpty() ? JsonResultMethod.code_804() : JsonResultMethod.code_200("列表数据加载成功", page);
        } catch (Exception e) {
            log.error("列表数据加载异常:" + e.getMessage(), e);
            return JsonResultMethod.code_500();
        }
    }

}
