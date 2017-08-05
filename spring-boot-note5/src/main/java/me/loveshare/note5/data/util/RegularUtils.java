package me.loveshare.note5.data.util;

import me.loveshare.note5.data.constant.RegExpDefinition;
import me.loveshare.note5.data.exception.TSEDictionary;
import me.loveshare.note5.data.exception.TSException;

import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Created by Tony on 2017/3/28.
 * 数据正则方法.<br/>
 * 正则表达式定义. see class com.hxwisec.entity.defination.RegExpDefinition
 */
public class RegularUtils {

    /**
     * 是否包含汉字
     *
     * @param string 字符串
     * @return true 字符串包含汉字
     */
    public static final boolean isContainChineseString(String string) {
        return Optional.ofNullable(string).map(s -> string.matches(RegExpDefinition.CHINESE)).orElseGet(() -> false);
    }


    /**
     * 中国手机号校验
     *
     * @param mobile 中国手机号
     * @return true 中国手机号验证通过
     */
    public static final boolean isChineseMobile(String mobile) {
        return Optional.ofNullable(mobile).map(m -> m.matches(RegExpDefinition.MOBILE_PHONE)).orElseGet(() -> false);
    }

    /**
     * 固定电话校验
     *
     * @param fixed 固定电话号
     * @return true 固定电话号验证通过
     */
    public static final boolean isFixedPhone(String fixed) {
        return Optional.ofNullable(fixed).map(f -> f.matches(RegExpDefinition.FIXED_PHONE)).orElseGet(() -> false);
    }


    /**
     * 邮箱验证校验
     *
     * @param email 邮箱
     * @return true 数据验证通过
     */
    public static final boolean isEmail1(String email) {
        return Optional.ofNullable(email).map(e -> e.matches(RegExpDefinition.EMAIL)).orElseGet(() -> false);
    }

    /**
     * 含有@_-的符合只有邮箱，验证
     *
     * @param email 邮箱
     * @return true 数据验证通过
     */
    public static final boolean isEmail2(String email) {
        if (email == null || email.length() == 0) return false;
        if (email.indexOf("@") != -1 || email.indexOf(".") != -1 || email.indexOf("_") != -1 || email.indexOf("-") != -1) {
            if (!isEmail1(email)) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * 邮箱验证码验证
     */
    public static final boolean isEmailCode(String emailCode) {
        return Optional.ofNullable(emailCode).map(ec -> ec.matches(RegExpDefinition.EMAIL_CODE)).orElseGet(() -> false);
    }

    /**
     * 算数验证码
     *
     * @param imageCode 图片验证码的值
     * @return true 数据验证通过
     */
    public static final boolean isImageCode(String imageCode) {
        return Optional.ofNullable(imageCode).map(ic -> ic.matches(RegExpDefinition.IMAGE_CODE)).orElseGet(() -> false);
    }

    /**
     * 手机验证码
     */
    public static final boolean isPhoneVCode(String vCode) {
        return Optional.ofNullable(vCode).map(vc -> vc.matches(RegExpDefinition.PHONE_CODE)).orElseGet(() -> false);
    }

    /**
     * ip格式验证
     */
    public static final boolean isIP(String ip) {
        return Optional.ofNullable(ip).map(i -> i.matches(RegExpDefinition.IP)).orElseGet(() -> false);
    }

    /**
     * 密码安全验证 -- 1.密码长度6~15位 2.大写字母，小写字母，数字，特殊符号必须四选三
     */
    public static final boolean isSafePassword(String password) {
        return Optional.ofNullable(password).map(p -> p.matches(RegExpDefinition.PASSWORD)).orElseGet(() -> false);
    }

    /**
     * 校验输入金额的格式
     *
     * @param money 金额
     * @return true 金额验证通过
     */
    public static final boolean isMoney(String money) {
        return Optional.ofNullable(money).map(m -> m.matches(RegExpDefinition.MONEY)).orElseGet(() -> false);
    }

    /**
     * 验证域名
     */
    public static final boolean isURL(String url) {
        return Optional.ofNullable(url).map(u -> u.matches(RegExpDefinition.URL)).orElseGet(() -> false);
    }

    /**
     * 验证用户名校验
     *
     * @param username 用户名
     * @return true 数据验证通过
     */
    public static final boolean isUserName(String username) {
        return Optional.ofNullable(username).map(un -> un.matches(RegExpDefinition.USERNAME1)).orElseGet(() -> false);
    }

    public static final boolean isChineseName(String username) {
        return Optional.ofNullable(username).map(un -> un.matches(RegExpDefinition.CHINESE_NAME)).orElseGet(() -> false);
    }

    /**
     * 11位数字校验
     *
     * @param num11 11位数字输入
     * @return true 数据验证通过
     */
    public static final boolean is11BitNumber(String num11) {
        return Optional.ofNullable(num11).map(n11 -> n11.matches(RegExpDefinition.NUMBER_11_BIT)).orElseGet(() -> false);
    }

    /**
     * 用户为了确保安全性，密码必须为(大写字母、小写字母、数字、特殊字符中三种组合以上,长度6-15)
     *
     * @param password 密码
     * @return true 数据验证通过  密码验证通过
     */
    public static final boolean isPasswordDifficultGt3(String password) {
        int difficultyrRound = 0;
        int min = 6;
        int max = 15;
        //含有一次记录难度
        boolean isAZ = true;
        boolean isaz = true;
        boolean isNumber = true;
        boolean isSpecialSymbol = true;
        if (password != null && password.length() >= min && password.length() <= max) {
            char[] uAPass = password.toCharArray();
            for (int i = 0, len = uAPass.length; i < len; i++) {
                if (isAZ && Character.isUpperCase(uAPass[i])) {
                    difficultyrRound++;
                    isAZ = false;
                }
                if (isaz && Character.isLowerCase(uAPass[i])) {
                    difficultyrRound++;
                    isaz = false;
                }
                if (isNumber && Character.isDigit(uAPass[i])) {
                    difficultyrRound++;
                    isNumber = false;
                }
                if (isSpecialSymbol && RegExpDefinition.SPECIAL_SYMBOL.indexOf(uAPass[i]) != -1) {
                    difficultyrRound++;
                    isSpecialSymbol = false;
                }
                if (difficultyrRound >= 3) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * China身份证有效性验证(非认证)
     *
     * @param identityCard 身份证号码
     * @return boolean 校验结果,true为有效.
     */
    public static boolean isChineseIdentityCard(String identityCard) throws TSException {
        try {
            if (null == identityCard || "NULL".equalsIgnoreCase(identityCard) || "".equals(identityCard)) {
                return false;
            }
            if (Pattern.matches(RegExpDefinition.CHINESE_IDENTITY_CARD, identityCard) && identityCard.length() == 18) {
                //将前17位加权因子保存在数组里
                int[] idCardWi = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
                //这是除以11后，可能产生的11位余数、验证码，也保存成数组
                int[] idCardY = {1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2};
                //用来保存前17位各自乖以加权因子后的总和
                int idCardWiSum = 0;
                for (int i = 0; i < 17; i++) {
                    idCardWiSum += Integer.parseInt(identityCard.substring(i, i + 1)) * idCardWi[i];
                }
                //计算出校验码所在数组的位置
                int idCardMod = idCardWiSum % 11;
                //得到最后一位身份证号码
                String idCardLast = identityCard.substring(17);
                //如果等于2，则说明校验码是10，身份证号码最后一位应该是X
                if (idCardMod == 2) {
                    if ("x".equalsIgnoreCase(idCardLast)) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    //用计算出的验证码与最后一位身份证号码匹配，如果一致，说明通过，否则是无效的身份证号码
                    if (Integer.parseInt(idCardLast) == idCardY[idCardMod]) {
                        return true;
                    } else {
                        return false;
                    }
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            throw new TSException(TSEDictionary.ARGS_FAIL.getCode(), "身份证号码:" + identityCard + "有效性校验异常." + e.getMessage());
        }
    }

}
