package me.loveshare.web.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import me.loveshare.data.entity.bo.common.BizResult;
import me.loveshare.data.entity.bo.common.JsonResult;
import me.loveshare.data.entity.bo.common.JsonResultMethod;
import me.loveshare.data.entity.vo.SignInVO;
import me.loveshare.data.util.DateUtils;
import me.loveshare.data.util.ValidatorUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Created by Tony on 2017/8/4.
 */
@Api(tags = {}, description = "测试接入Api")
@Slf4j
@RestController
@RequestMapping("test")
public class TestDataApi extends BaseApi {

    /**
     *测试地址：http://192.168.1.104:5204/note4/swagger-ui.html
     */

    /**
     * 协议测试
     * http: http://192.168.1.119:5204/note4/test.json
     * https: https://192.168.1.119:7204/note4/test.json
     * <p>
     * User-Access-Args:{"protocol":"HTTP/1.1(http)","ip":"192.168.1.119","port":"49181","method":"GET","url":"/note4/test.json","user-agent":"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36","uAT":"null"}
     * User-Access-Args:{"protocol":"HTTP/1.1(https)","ip":"192.168.1.119","port":"65299","method":"GET","url":"/note4/test.json","user-agent":"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36","uAT":"null"}
     */
    @ApiOperation("协议测试")
    @RequestMapping(value = "test", produces = "application/json; charset=UTF-8", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonResult initDbDatasC() {
        return JsonResultMethod.code_200("The request completed successfully.", DateUtils.current(4));
    }


    /**
     * 数据验证：Validator数据验证 + i18n国际化的提示 （注解: @Valid ）.<br/>
     * 1. 在resources目录下加入自定义的语言配置文件: ValidationMessages_*.properties  (文件名固定).<br/>
     * 2. 在application.yml文件中加入：spring.messages.basename: ValidationMessages
     * 3. MessageSource注解和getI18nMessage()方法使用
     * 注: swagger + lombok 共用，则页面VO对象getter和setter方法请自己写入，不推荐使用@Data注解
     */
    @ApiOperation("Validator数据验证 + i18n国际化的提示 （注解）1")
    @PostMapping("test-vi-1")
    public JsonResult testValidatorOrI18n1(HttpServletRequest request, @Valid @ModelAttribute SignInVO vo) {
        if ("typa1@qq.com".equals(vo.getUsername()) && "123abcD".equals(vo.getPassword())) {
            return JsonResultMethod.code_200("");
        } else {
            //用户名或密码错误  --代码内部获取i18n国际化的提示
            return JsonResultMethod.code_800(getI18nMessage(request, "username.password.error"));
        }
    }

    /**
     * Validator数据验证（方法内部）.<br/>
     * 1. 在resources目录下加入自定义的语言配置文件: ValidationMessages_*.properties  (文件名固定).<br/>
     * 2. 在application.yml文件中加入：spring.messages.basename: ValidationMessages
     * 3. MessageSource注解和getI18nMessage()方法使用
     */
    @ApiOperation("Validator数据验证 + i18n国际化的提示 （方法内部）2")
    @PostMapping("test-vi-2")
    public JsonResult testValidatorOrI18n2(HttpServletRequest request, @ModelAttribute SignInVO vo) {
        //代码内部验证
        BizResult<JsonResult> validatorBizResult = ValidatorUtils.validator(vo);
        if (!validatorBizResult.isFlag()) {
            return validatorBizResult.getResult();
        }
        if ("typa1@qq.com".equals(vo.getUsername()) && "123abcD".equals(vo.getPassword())) {
            return JsonResultMethod.code_200("");
        } else {
            //用户名或密码错误  --代码内部获取i18n国际化的提示
            return JsonResultMethod.code_800(getI18nMessage(request, "username.password.error"));
        }
    }

    /**
     * 语言切换，i18n国际化
     * 获取国际化提示信息  ?lang=en_US / zh_CN   -- 未实现，代码切换完成
     */
    @ApiOperation("语言切换，i18n国际化")
    @GetMapping("test-i")
    public JsonResult testI18n(HttpServletRequest request) {
        //用户名或密码错误  --代码内部获取i18n国际化的提示
        return JsonResultMethod.code_800(getI18nMessage(request, "username.password.error"));
    }
}
