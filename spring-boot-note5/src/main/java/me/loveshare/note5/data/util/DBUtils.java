package me.loveshare.note5.data.util;

import lombok.extern.slf4j.Slf4j;
import me.loveshare.note5.data.constant.DBdefaultConstants;

import java.math.BigDecimal;

/**
 * Created by Tony on 2017/2/11.
 * 数据库数据验证
 */
@Slf4j
public class DBUtils {
    /**
     * 数据库的varchar类型为空
     *
     * @return true 为空
     */
    public static boolean stringBlank(final CharSequence cs) {
        return cs == null || cs.length() == 0 || DBdefaultConstants.VARCHAR_DEFAULT.equals(cs);
    }

    /**
     * 数据库数字类型的默认值为0
     *
     * @return true 为空
     */
    public static boolean numberBlank(final Number num) {
        if (num == null) return true;
        if (num instanceof Integer) if (num.intValue() == 0) return true;
        if (num instanceof Long) if (num.longValue() == 0l) return true;
        if (num instanceof Byte) if (num.byteValue() == 0) return true;
        if (num instanceof Short) if (num.shortValue() == 0) return true;
        return false;
    }

    public static Number isNullNumberDefault(final Number num) {
        return numberBlank(num) ? 0 : num;
    }

    public static BigDecimal isNullBigDecimalDefault(BigDecimal decimal) {
        return decimal == null ? BigDecimal.ZERO : decimal;
    }
}
