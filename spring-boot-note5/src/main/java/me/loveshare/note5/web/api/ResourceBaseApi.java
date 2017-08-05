package me.loveshare.note5.web.api;

import lombok.extern.slf4j.Slf4j;
import me.loveshare.note5.data.constant.DBdefaultConstants;
import me.loveshare.note5.data.constant.FileSizeConstants;
import me.loveshare.note5.data.constant.SymbolConstants;
import me.loveshare.note5.data.entity.bo.common.BizResult;
import me.loveshare.note5.data.entity.bo.common.JsonResult;
import me.loveshare.note5.data.entity.bo.common.JsonResultEnum;
import me.loveshare.note5.data.entity.dbo.ResourceData;
import me.loveshare.note5.data.entity.vo.ResourceResult;
import me.loveshare.note5.data.exception.TSEDictionary;
import me.loveshare.note5.data.exception.TSException;
import me.loveshare.note5.data.service.RecordService;
import me.loveshare.note5.data.thirdparty.alibaba.aliyun.oss.AliyunOssTemplateI;
import me.loveshare.note5.data.util.DBUtils;
import me.loveshare.note5.data.util.RegularUtils;
import me.loveshare.note5.data.util.ResourceUtils;
import me.loveshare.note5.data.util.StringUtils;
import me.loveshare.note5.properties.AliyunOssProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * Created by Tony on 2017/5/20.<br/>
 * 资源文件:  上传(OOS对象存储) + 记录(MongoDB文档).<br/>
 * 注：上传资源文件的的API可直接继承使用.<br/>
 */
@Slf4j
public class ResourceBaseApi extends BaseApi {

    @Autowired
    protected AliyunOssProperties properties;
    @Autowired
    private RecordService recordService;
    @Autowired
    @Qualifier("openedOSSTemplate")
    protected AliyunOssTemplateI openedOSSTemplate;
    @Autowired
    @Qualifier("ownedOSSTemplate")
    protected AliyunOssTemplateI ownedOSSTemplate;

    /**
     * 上传资源文件到OOS对象存储 + MongoDB/MySQL记录(暂未加入，自定义)
     *
     * @param memberId 上传用户的id
     * @param type     文件类型：1.视频 2.音频 3.图片 4.压缩文件 5:办公文件
     * @param source   文件平台来源：1.家庭 2.学校 3.公司 4.户外
     * @param desc     文件描述
     * @param file     待上传的资源文件
     * @param isOwned  公开(false)或私有(true)
     *                 业务结果 封装对象:com.hxwisec.module.entity.common.BizResult<JsonResult>
     *                 pushBizResult.getResult() == false, 则返回封装错误信息对象:com.hxwisec.module.entity.common.JsonResult
     *                 pushBizResult.getResult() == true , 则返回封装成功信息对象:com.hxwisec.resource.ResourceResult
     */
    protected final BizResult<JsonResult> upload(Integer memberId, Byte type, Byte source, String desc, MultipartFile file, Boolean isOwned) {

        //默认值补齐
        source = DBUtils.numberBlank(source) ? ResourceUtils.DEFAULTS : source;

        AliyunOssTemplateI ossTemplate = isOwned ? ownedOSSTemplate : openedOSSTemplate;
        String ossUrl = isOwned ? properties.getOwnedURL1() : properties.getOpenedURL1();
        String bucketName = isOwned ? properties.getOwnedBucketName1() : properties.getOpenedBucketName1();

        //数据验证
        BizResult<JsonResult> verifyBizResult = verify(ossTemplate, recordService, ossUrl, bucketName, memberId, type, source, file);
        if (!verifyBizResult.isFlag()) {
            return verifyBizResult;
        }
        //资源，上传 + 记录
        BizResult<JsonResult> pushBizResult = push(ossTemplate, recordService, ossUrl, bucketName, memberId, type, source, desc, file, isOwned);
        if (!pushBizResult.isFlag()) {
            return pushBizResult;
        }
        //成功
        return pushBizResult;
    }


    /**
     * 验证上传的资源数据信息
     *
     * @param ossTemplate   阿里云OSS操作对象
     * @param recordService MongoDB记录服务对象
     * @param ossUrl        阿里云OSS外网域名
     * @param bucketName    阿里云OSS的Bucket名称
     * @param memberId      上传用户的id
     * @param type          文件类型：1.视频 2.音频 3.图片 4.压缩文件 5:办公文件
     * @param source        文件平台来源：1.家庭 2.学校 3.公司 4.户外
     * @param file          待上传的资源文件
     */
    protected final BizResult<JsonResult> verify(AliyunOssTemplateI ossTemplate, RecordService recordService, String ossUrl, String bucketName, Integer memberId, Byte type, Byte source, MultipartFile file) {

        BizResult<JsonResult> bizResult = BizResult.create(JsonResult.class);
        bizResult.getResult().setCode(JsonResultEnum.WARMTIPS.getCode());

        //-------------数据验证  --开始
        if (DBUtils.numberBlank(memberId) || file == null) {
            bizResult.getResult().setMessage("参数不能为空");
            return bizResult;
        }
        if (!ResourceUtils.SOURCE.containsKey(source)) {
            bizResult.getResult().setMessage("平台来源非法");
            return bizResult;
        }
        String orgFileName = file.getOriginalFilename();
        if (StringUtils.isEmpty(orgFileName) || orgFileName.indexOf(SymbolConstants.DOT) == -1) {
            bizResult.getResult().setMessage("文件名非法");
            return bizResult;
        }
        String suffix = ResourceUtils.suffix(orgFileName);
        if (!ResourceUtils.next(type, suffix)) {
            bizResult.getResult().setMessage("文件类型暂不支持");
            return bizResult;
        }
        if (DBUtils.numberBlank(type)) {
            type = ResourceUtils.type(orgFileName);
        }
        if (!ResourceUtils.TYPE.containsKey(type)) {
            bizResult.getResult().setMessage("文件的类型非法");
            return bizResult;
        }
        long fileSize = file.getSize();
        if (fileSize > FileSizeConstants.SIZE_1G) {
            bizResult.getResult().setMessage("文件最大可上传1GB");
            return bizResult;
        }
        if (!RegularUtils.isURL(ossUrl)) {
            bizResult.getResult().setMessage("域名非法");
            return bizResult;
        }

        //内部服务调用检查错误
        if (!RegularUtils.isURL(ossUrl)) {
            return bizResultError(bizResult, "域名");
        }
        if (StringUtils.isEmpty(bucketName)) {
            return bizResultError(bizResult, "桶名");
        }
        if (ossTemplate == null) {
            return bizResultError(bizResult, "存储服务");
        }
        if (recordService == null) {
            return bizResultError(bizResult, "记录服务");
        }

        //-------------数据验证  --通过
        bizResult.setFlag(true);
        return bizResult;
    }

    /**
     * 上传资源文件到OOS对象存储 + MongoDB记录
     *
     * @param ossTemplate   阿里云OSS操作对象
     * @param recordService MongoDB记录服务对象
     * @param ossUrl        阿里云OSS外网域名
     * @param bucketName    阿里云OSS的Bucket名称
     * @param memberId      上传用户的id
     * @param type          文件类型：1.视频 2.音频 3.图片 4.压缩文件 5:办公文件
     * @param source        文件平台来源：1.家庭 2.学校 3.公司 4.户外
     * @param desc          文件描述
     * @param file          待上传的资源文件
     * @param isOwned       公开(false)或私有(true)
     */
    protected final BizResult<JsonResult> push(AliyunOssTemplateI ossTemplate, RecordService recordService, String ossUrl, String bucketName, Integer memberId, Byte type, Byte source, String desc, MultipartFile file, Boolean isOwned) {

        BizResult<JsonResult> bizResult = BizResult.create(JsonResult.class);
        bizResult.getResult().setCode(JsonResultEnum.WARMTIPS.getCode());

        Date time = new Date();
        String uuid = ResourceUtils.uuid();
        String orgFileName = file.getOriginalFilename();
        String suffix = ResourceUtils.suffix(orgFileName);
        long fileSize = file.getSize();
        //-------------资源OSS上传参数
        String diskName = ResourceUtils.disk(source, suffix);
        String fileName = ResourceUtils.file(uuid, suffix);
        //-------------资源mongodb记录对象封装
        String url = ResourceUtils.url(ossUrl, diskName, fileName);
        ResourceData record = new ResourceData();
        record.setCode(uuid);
        record.setName(orgFileName);
        record.setDesc(StringUtils.isEmpty(desc) ? DBdefaultConstants.VARCHAR_DEFAULT : desc);
        record.setSource(source);
        record.setType(DBUtils.numberBlank(type) ? ResourceUtils.type(orgFileName) : type);
        record.setUrl(ossUrl);
        record.setBucketName(bucketName);
        record.setDiskName(diskName);
        record.setFileName(fileName);
        record.setSize(fileSize);
        record.setIsOpened(isOwned ? DBdefaultConstants.OWNED : DBdefaultConstants.OPENED);
        record.setAuthor(memberId);
        record.setGmtCreate(time);
        record.setGmtModified(time);
        record.setIsDeleted(DBdefaultConstants.USING);

        //-------------资源分类上传到阿里云OSS存储(公有的Bucket上)
        String md5 = "";
        try {
            md5 = fileUpload(file, ossTemplate, type, bucketName, diskName, fileName);
        } catch (Exception e) {
            log.error("资源分类上传到阿里云OSS存储(公有的Bucket上)异常" + e.getMessage(), e);
            return bizResultError(bizResult, "OSS存储");
        }
        record.setMd5(md5);
        try {
            String code = recordData(recordService, record);
            if (StringUtils.isEmpty(code)) {
                throw new TSException(TSEDictionary.RECORD_DATA_FAIL.getCode(), "资源记录持久化失败");
            }
            bizResult.getResult().setCode(JsonResultEnum.SUCCESS.getCode());
            bizResult.getResult().setMessage("资源上传成功");
            String signUrl = isOwned ? ossTemplate.sign(url, ResourceUtils.OWNED_URL_MINUTES) : null;
            bizResult.getResult().setData(isOwned ? ResourceResult.create(uuid, url, signUrl) : ResourceResult.create(uuid, url));
            bizResult.setFlag(true);
            return bizResult;
        } catch (Exception e) {
            log.error("资源上传的记录存储到mongodb的resource_data集合中异常" + e.getMessage(), e);
            return bizResultError(bizResult, "资源记录");
        }
    }

    protected BizResult<JsonResult> bizResultError(BizResult<JsonResult> bizResult, String msg) {
        bizResult.getResult().setMessage(TSEDictionary.SYSTEM_ERROR.getMessage() + "->[" + msg + "]");
        bizResult.getResult().setCode(JsonResultEnum.ERROR.getCode());
        return bizResult;
    }

    protected String fileUpload(MultipartFile file, AliyunOssTemplateI ossTemplate, Byte type, String bucketName, String diskName, String fileName) throws Exception {
        return ossTemplate.upload(bucketName, diskName, fileName, file.getInputStream());
    }

    protected String recordData(RecordService recordService, ResourceData data) throws Exception {
        return recordService.record(data);
    }

    protected final JsonResult sign(String url) {
        String signUrl = null;
        try {
            signUrl = ownedOSSTemplate.sign(url, ResourceUtils.OWNED_URL_MINUTES);
        } catch (Exception e) {
            return new JsonResult(JsonResultEnum.WARMTIPS.getCode(), "原图加载失败");
        }
        return new JsonResult(JsonResultEnum.SUCCESS.getCode(), "获取加密地址成功", signUrl);
    }
}
