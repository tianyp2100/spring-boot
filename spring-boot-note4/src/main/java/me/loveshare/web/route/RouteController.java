package me.loveshare.web.route;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Tony on 2017/8/4.
 */
@Controller
public class RouteController {

    /**
     * Html页面测试
     * http://192.168.1.119:5204/note4/
     * https: https://192.168.1.119:7204/note4/test.json
     * http://192.168.1.119:5204/note4/gy.html
     * https://192.168.1.119:7204/note4/gy.girl
     * 注：1.此处后缀，不限制
     *     2.若https协议请求服务器，则保证页面上的js等资源也是https，则可能安全限制浏览器，不能正常加载页面
     */

    @RequestMapping("/")
    public String toIndexPage1() {
        return "index";
    }

    @RequestMapping("gy")
    public String toGYPage() {
        return "girl/test";
    }
}
