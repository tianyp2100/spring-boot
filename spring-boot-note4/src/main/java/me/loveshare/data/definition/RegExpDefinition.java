package me.loveshare.data.definition;

/**
 * @author Tony
 * @date 2017/7/4
 * 正则表达式定义
 */
public final class RegExpDefinition {
    /**
     * 长度6-20
     */
    public static final String LENGTH_6_20_BIT = "^.{6,20}$";
    /**
     * 11位数字正则
     */
    public static final String NUMBER_11_BIT = "^\\d{11}$";
    /**
     * 11位数字正则
     */
    public static final String BARCODE_13_BIT = "^\\d{13}$";
    /**
     * 大写字母
     */
    public static final String UPPER_LETTER = "^[A-Z]$";
    /**
     * 1个大写字母
     */
    public static final String UPPER_LETTER_1 = "^[A-Z]{1}$";
    /**
     * 小写字母
     */
    public static final String LOWER_LETTER = "^[a-z]$";
    /**
     * 数字
     */
    public static final String NUMBER_DIGIT = "^[\\d]$";
    /**
     * 中文
     */
    public static final String CHINESE = "^[\u4e00-\u9fa5]$";
    /**
     * 手机号
     */
    public static final String MOBILE_PHONE = "^((13[0-9])|(17[0|6|7|8])|(14[5|7])|(15[^4,\\D])|(18[0-9]))\\d{8}$";
    /**
     * 固定电话
     */
    public static final String FIXED_PHONE = "^(0\\d{2}-\\d{8}(-\\d{1,4})?)|(0\\d{3}-\\d{7,8}(-\\d{1,4})?)$";
    /**
     * 邮箱
     */
    public static final String EMAIL = "^([a-zA-Z0-9_.-])+@(([a-zA-Z0-9-])+\\.)+([a-zA-Z0-9]{2,4})+$";
    /**
     * 邮箱验证码 --数字字母混合6位
     */
    public static final String EMAIL_CODE = "^[A-Za-z0-9]{6}$";
    /**
     * 短信验证码 --6位数字正则
     */
    public static final String PHONE_CODE = "^\\d{6}$";
    /**
     * 算数验证码 --0到9的数学四计算结果
     */
    public static final String IMAGE_CODE = "^[-\\d]{1,3}$";
    /**
     * 用户名正则  --昵称  --中文、英文、数字组合
     */
    public static final String USERNAME1 = "^[a-zA-Z\\d\u4E00-\u9FA5]{2,35}$";
    /**
     * 名称
     */
    public static final String NAME = "^[a-zA-Z\u4E00-\u9FA5]{2,20}$";
    /***
     * 中文姓名
     */
    public static final String CHINESE_NAME = "^[·\u4E00-\u9FA5]{2,7}$";
    /**
     * 特殊字符  --英文
     */
    public static final String SPECIAL_SYMBOL = "`~!@#$%^&*()-_+={}[]|\\:;'\"/<>,.?";
    /**
     * 特殊字符 --中英文
     */
    public static final String SPECIAL_SYMBOL_ALL = "[`~!@#$%^&*()+=|{}:;',//[//].<>/?\\~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
    /**
     * 金额正则：①：非负整数输入，②：两位小数的非负浮点数输入，③：最大2位小数位和11位整数
     */
    public static final String MONEY = "^(([1-9]\\d{0,11})|0)(\\.\\d{1,2})?$";
    /**
     * 用户名
     */
    public static final String USERNAME = "^[@_.-A-Za-z0-9]{4,25}$";
    /**
     * 1.密码长度6~15位 2.大写字母，小写字母，数字，特殊符号必须四选三
     */
    public static final String PASSWORD = "^(?![a-zA-Z]+$)(?![A-Z0-9]+$)(?![A-Z\\W_]+$)(?![a-z0-9]+$)(?![a-z\\W_]+$)(?![0-9\\W_]+$)[a-zA-Z0-9\\W_]{6,15}$";
    /**
     * 中国大陆身份证，格式，非实名
     */
    public static final String CHINESE_IDENTITY_CARD = "^(^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$)|(^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])((\\d{4})|\\d{3}[Xx])$)$";
    /**
     * 中国大陆护照
     */
    public static final String CHINESE_PASSPORT = "^(([a-zA-Z]{5,17})|([a-zA-Z0-9]{5,17}))$";
    /**
     * 中国港澳通行证
     */
    public static final String CHINESE_HONGKONG_MACAO_PASS = "^[HMhm]{1}([0-9]{10}|[0-9]{8})$";
    /**
     * 中国台湾通行证
     */
    public static final String CHINESE_TAIWAN_PASS = "^(([0-9]{8})|([0-9]{10}))$";
    /**
     * 邮编
     */
    public static final String POST_CODE = "^\\d{6}$";
    /**
     * IP正则
     */
    public static final String IP = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";
    /**
     * 网站域名
     */
    public static final String URL = "(http://|ftp://|https://|www){0,1}[^\u4e00-\u9fa5\\s]*?\\.(com|net|cn|me|tw|fr)[^\u4e00-\u9fa5\\s]*";

    /**
     * 小时分钟
     */
    public static final String HOUR_MINUTE = "(([1-2][0-3])\\:([0-5]?[0-9]))?$";
    /**
     * 年
     */
    public static final String YEAR = "^\\d{4}$";
    /**
     * 对象json字符串
     */
    public static final String OBJECT_JSON = "^\\{.*\\}$";
    /**
     * 也可""空的对象json字符串
     */
    public static final String OBJECT__JSON = "^(\\s)*|\\{.*\\}$";
    /**
     * 数组json字符串
     */
    public static final String ARRAY_JSON = "^\\[.*\\]$";
    /**
     * 对象数组json字符串
     */
    public static final String OBJECT_ARRAY_JSON = "^\\[\\{([\\s\\S]*?)\\}\\]$";
    /**
     * 也可""空的对象数组json字符串
     */
    public static final String OBJECT_ARRAY__JSON = "^(\\s)*|\\[\\{([\\s\\S]*?)\\}\\]$";
    /**
     * mongo数据库id正则表达是
     */
    public static final String MONGODB_OBJECT_ID = "^[a-z0-9]{24}$";
    /**
     * uAk : user acess token  43位
     */
    public static final String UAK = "^[a-z0-9]{43,64}$";
    /**
     * 数字开头
     */
    public static final String START_DIGIT = "^[0-9]+.*$";

}
