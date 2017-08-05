package me.loveshare.note5.web.api;

import lombok.extern.slf4j.Slf4j;
import me.loveshare.note5.data.entity.bo.common.JsonResult;
import me.loveshare.note5.data.entity.bo.common.JsonResultEnum;
import me.loveshare.note5.data.entity.bo.common.JsonResultMethod;
import me.loveshare.note5.data.util.StringUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * Created by Tony on 2017/6/8.
 * 资源上传到服务器本地磁盘.<br/>
 * 资源文件: 上传(OOS对象存储/服务器磁盘)  + 记录(MongoDB文档数据库/MySQL关系型数据库)，例：#{@link me.loveshare.note5.data.entity.dbo.ResourceData}.<br/>
 * 注：上传资源文件的的API可直接继承使用.<br/>
 */
@Slf4j
@RestController
public class UploadApi extends ResourceBaseApi {

    /**
     * 磁盘上传根目录
     */
    private static final String ROOT_DISK_NAME = "D:\\resources\\";

    /**
     * 上传资源文件到服务磁盘
     *
     * @param file   待上传的资源文件
     * @param type   文件类型: 1.视频 2.PPT 3.PDF 4.动画 5.WORD 6.音频 7.录屏 10.EXCEL 11.MP4+PPT关联 12.图片 13.压缩文件 14.文档
     * @param source 文件平台来源：1.项目培训 2.视频课 3.直播课 4.试题库 5.圈子 6.工作坊 7.备课 8.账户
     * @param desc   文件描述
     * @param own    是否私有化：公开(false)或私有(true)
     * @return JsonResult封装的对象
     */
    @PostMapping("disk-upload")
    public JsonResult upload(@RequestParam("file") MultipartFile file, byte type, byte source, String desc, boolean own) {
        String url = null;
        printUpload(file, type, source, desc, own);
        try {
            if (file != null && file.getSize() > 0) {
                String fileName = file.getOriginalFilename();
                FileUtils.copyInputStreamToFile(file.getInputStream(), new File(ROOT_DISK_NAME, fileName));
                url = ROOT_DISK_NAME + fileName;
            }
        } catch (Exception e) {
            log.error("资源分类上传到服务器本地磁盘异常" + e.getMessage(), e);
            return JsonResultMethod.code_500();
        }
        log.info("文件记录成功，上传成功");
        return new JsonResult(JsonResultEnum.SUCCESS.getCode(), "资源上传成功", url);  //url需要nginx做资源代理
    }

    private final void printUpload(MultipartFile file, int type, int source, String desc, boolean own) {
        StringBuilder su = new StringBuilder();
        su.append("\nUpload-Args:").append("{");
        su.append("\"fileSize\":\"").append((file.getSize() / 1048576f) + "MB.").append("\",");
        su.append("\"type\":\"").append(type).append("\",");
        su.append("\"source\":\"").append(source).append("\",");
        su.append("\"desc\":\"").append(desc).append("\",");
        su.append("\"own\":\"").append(own).append("\"}");
        log.info(su.toString());
    }

    /**
     * 上传资源文件到阿里云对象存储服务器服务器
     *
     * @param file   待上传的资源文件
     * @param uAT    用户访问令牌：user access token
     * @param type   文件类型: 1.视频 2.PPT 3.PDF 4.动画 5.WORD 6.音频 7.录屏 10.EXCEL 11.MP4+PPT关联 12.图片 13.压缩文件 14.文档
     * @param source 文件平台来源：1.项目培训 2.视频课 3.直播课 4.试题库 5.圈子 6.工作坊 7.备课 8.账户
     * @param desc   文件描述
     * @param own    是否私有化：公开("false")或私有("true")
     * @return JsonResult封装的对象
     */
    @PostMapping("oss-upload")
    public JsonResult uploadC(
            @RequestParam("file") MultipartFile file,
            @RequestHeader(value = "uAT", required = false) String uAT,
            @RequestParam(value = "type", required = false) byte type,
            @RequestParam(value = "source", required = false) byte source,
            @RequestParam(value = "desc", required = false) String desc,
            @RequestParam(value = "own", required = false) String own) {

        Integer memberId = getUserId(uAT);

        log.info("用户uAT/id：" + uAT + "/" + memberId);

        boolean isOwned = "true".equals(own);

        printUpload(file, type, source, desc, isOwned);

        desc = StringUtils.isEmpty(desc) ? "资源上传" : desc.trim();

        return upload(memberId, type, source, desc, file, isOwned).getResult();
    }

    /**
     * 通过url--获取阿里云私有链接签名地址  --有效时长5min
     */
    @ResponseBody
    @GetMapping("oss-sign-url")
    public JsonResult signUrl(@RequestParam("url") String url) {

        log.info("加密前地址：" + url);

        return sign(url);
    }
}
