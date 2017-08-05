package me.loveshare.note5.data.util;

import me.loveshare.note5.data.constant.SymbolConstants;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tony on 2017/8/5.
 * 资源辅助工具类
 */
public class ResourceUtils {

    /**
     * 文件类型：1.视频 2.音频 3.图片 4.压缩文件 5:办公文件
     */
    public static final Map<Byte, String> TYPE = new HashMap<Byte, String>(10);
    /**
     * 文件平台来源：1.家庭 2.学校 3.公司 4.户外
     */
    public static final Map<Byte, String> SOURCE = new HashMap<Byte, String>(10);

    public static final String DISK_PREFIX = "resources";

    //私有默认打开时长
    public static final int OWNED_URL_MINUTES = 5;

    /**
     * 1.视频
     */
    public static final byte VIDEO = 1;
    /**
     * 2.音频
     */
    public static final byte VOICE = 2;
    /**
     * 3.图片
     */
    public static final byte IMAGE = 3;
    /**
     * 4.压缩文件
     */
    public static final byte ZIP = 4;
    /**
     * 5:办公文件
     */
    public static final byte OFFICE = 5;
    /**
     * 5:默认
     */
    public static final byte DEFAULTT = 6;
    /**
     * 1.家庭
     */
    public static final byte FAMILY = 1;
    /**
     * 2.学校
     */
    public static final byte SCHOOL = 2;
    /**
     * 3.公司
     */
    public static final byte COMPANY = 3;
    /**
     * 4.户外
     */
    public static final byte OUTDOORS = 4;
    /**
     * 5.默认
     */
    public static final byte DEFAULTS = 4;

    static {
        TYPE.put(VIDEO, "wmv,wm,asf,asx,rm,rmvb,ra,ram,mpg,mpeg,mpe,vob,dat,mov,3gp,mp4,mp4v,m4v,mkv,avi,flv,f4v");
        TYPE.put(VOICE, "wav,aif,au,mp3,ram,wma,mmf,amr,aac,flac");
        TYPE.put(IMAGE, "bmp,jpg,dib,jpeg,png,jfif,jpe,tif,tiff,gif,tgp");
        TYPE.put(ZIP, "rar,zip,7z,gz,arj,z,xz,iso,exe,dmg");
        TYPE.put(OFFICE, "ppt,pptx,doc,docx,xls,xlsx,pdf,txt");
        TYPE.put(OFFICE, "ppt,pptx,doc,docx,xls,xlsx,pdf,txt");
        TYPE.put(DEFAULTT, "defaultt");
        SOURCE.put(FAMILY, "family");
        SOURCE.put(SCHOOL, "school");
        SOURCE.put(COMPANY, "company");
        SOURCE.put(OUTDOORS, "outdoors");
        SOURCE.put(DEFAULTS, "defaults");
    }

    /**
     * 获取文件夹路径: resources/family/jpeg/20170519/
     */
    public static final String disk(Byte source, String suffix) {
        StringBuilder su = new StringBuilder(39);
        su.append(DISK_PREFIX).append(SymbolConstants.SPRIT);
        su.append(platform(source)).append(SymbolConstants.SPRIT);
        su.append(suffix).append(SymbolConstants.SPRIT);
        su.append(date()).append(SymbolConstants.SPRIT);
        return su.toString();
    }

    /**
     * UUID随机生产文件名: 21792dd7bcae443d846a1ec19a115054.jpeg
     */
    public static final String file(String uuid, String suffix) {
        return new StringBuilder(37).append(uuid).append(SymbolConstants.DOT).append(suffix).toString();
    }

    /**
     * 获取文件名：kaka.jpeg -> kaka
     */
    public static final String name(String fileName) {
        if (StringUtils.isEmpty(fileName) || fileName.indexOf(SymbolConstants.DOT) < 0) return SymbolConstants.EMPTY;
        return fileName.substring(0, fileName.lastIndexOf(SymbolConstants.DOT)).toLowerCase();
    }

    /**
     * 获取文件的扩展名：kaka.jpeg -> jpeg
     */
    public static final String suffix(String name) {
        if (StringUtils.isEmpty(name) || name.indexOf(SymbolConstants.DOT) < 0) return SymbolConstants.EMPTY;
        return name.substring(name.lastIndexOf(SymbolConstants.DOT) + 1).toLowerCase();
    }

    /**
     * 资源云OSS存储地址url:
     */
    public static String url(String ossUrl, String diskName, String fileName) {
        return new StringBuilder(128).append(ossUrl).append(diskName).append(fileName).toString();
    }

    /**
     * 获取来源平台: family
     */
    public static final String platform(Byte source) {
        return SOURCE.get(source);
    }

    /**
     * 获取当天的日期: 20170519
     */
    public static final String date() {
        return LocalDate.now().toString().replace(SymbolConstants.HLINE, "");
    }

    /**
     * 获取当天的日期+时间（毫秒）: 20170522161106540
     */
    public static final String time() {
        String time = new StringBuilder(22).append(LocalDate.now()).append(LocalTime.now()).toString();
        time = time.replace(SymbolConstants.HLINE, "").replace(SymbolConstants.COLON, "").replace(SymbolConstants.DOT, "");
        return time;
    }

    /**
     * UUID随机数: 21792dd7bcae443d846a1ec19a115054
     */
    public static final String uuid() {
        return UUIDUtils.uuid();
    }

    /**
     * 判断文件扩展名是否合法
     */
    public static final boolean next(Byte type, String suffix) {
        if (type == null) {
            return TYPE.containsValue(suffix);
        } else {
            if (TYPE.get(type) == null) {
                return false;
            } else {
                return TYPE.get(type).contains(suffix);
            }
        }
    }

    /**
     * 通过文件的名获取type
     */
    public static final byte type(String name) {
        if (StringUtils.isEmpty(name) || name.indexOf(SymbolConstants.DOT) < 0) return 0;
        String suffix = name.substring(name.lastIndexOf(SymbolConstants.DOT) + 1).toLowerCase();
        for (Map.Entry<Byte, String> entry : TYPE.entrySet()) {
            if (entry.getValue().contains(suffix)) {
                return entry.getKey();
            }
        }
        return 0;
    }
}
