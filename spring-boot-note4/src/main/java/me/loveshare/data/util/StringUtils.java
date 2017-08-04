package me.loveshare.data.util;


import me.loveshare.data.constant.DBdefaultConstants;

/**
 * Created by Tony on 2017/3/28.
 */
public class StringUtils {

    /**
     * 判断字符串是whitespace, empty ("") or null
     */
    public static boolean isBlank(final CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (Character.isWhitespace(cs.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断字符串不为empty ("") 或null
     */
    public static boolean isNotBlank(final CharSequence cs) {
        return !isBlank(cs);
    }

    /**
     * 判断字符串为empty ("") 或null
     */
    public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    /**
     * 判断字符串为empty ("") 或null
     *
     * @param cs 待判断字符串
     * @return 若为true，则返回默认值
     */
    public static String isNullDefault(String cs) {
        return cs == null ? DBdefaultConstants.VARCHAR_DEFAULT : cs;
    }

    /**
     * 判断字符串不为empty ("") 或null
     */
    public static boolean isNotEmpty(final CharSequence cs) {
        return !isEmpty(cs);
    }

    /**
     * 判断字符串仅仅是数字
     */
    public static boolean isNumeric(final CharSequence cs) {
        if (isEmpty(cs)) {
            return false;
        }
        final int sz = cs.length();
        for (int i = 0; i < sz; i++) {
            if (Character.isDigit(cs.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    private static final String ZERO_STR = "0";

    /**
     * 判断是有效的id
     *
     * @return true 则有效的id，非空非0是数字
     */
    public static boolean isOkId(String id) {
        if (isEmpty(id)) return false;
        if (!isNumeric(id)) return false;
        if (ZERO_STR.equals(id)) return false;
        return true;
    }
}
