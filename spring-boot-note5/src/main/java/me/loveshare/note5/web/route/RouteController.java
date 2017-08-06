package me.loveshare.note5.web.route;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Tony on 2017/8/4.
 * Html页面路由
 */
@Controller
public class RouteController {

    @RequestMapping("/")
    public String toIndexPage1() {
        return "index";
    }

    @RequestMapping("upload")
    public String toUploadPage() {
        return "html/upload";
    }

    @RequestMapping("ueditor")
    public String toUeditorPage() {
        return "html/ueditor";
    }

    @RequestMapping("image")
    public String toImagePage() {
        return "html/image";
    }
}
