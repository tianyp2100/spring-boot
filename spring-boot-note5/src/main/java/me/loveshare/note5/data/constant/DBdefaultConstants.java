package me.loveshare.note5.data.constant;

import java.math.BigDecimal;

/**
 * Created by Tony on 2017/2/5.
 * 批量插入数据库默认值
 */
public class DBdefaultConstants {

    /**
     * 数据库VARCHAR/String的默认值为"nil"
     */
    public static final String VARCHAR_NIL = "nil";
    /**
     * 数据库VARCHAR/String的默认值为""
     */
    public static final String VARCHAR_DEFAULT = "";
    /**
     * 数据库TINYINT/byte的默认值为0
     */
    public static final byte BYTE_DEFAULT = 0;
    /**
     * 数据库SMALLINT/short的默认值为0
     */
    public static final short SHORT_DEFAULT = 0;
    /**
     * 数据库int/int的默认值为0
     */
    public static final int INT_DEFAULT = 0;
    /**
     * 数据库DECIMAL/BigDecimal的默认值为BigDecimal.ZERO
     */
    public static final BigDecimal BIGDECIMAL = BigDecimal.ZERO;

    /**
     * is_deleted是否已逻辑删除(N正常Y删除)
     */
    public static final String Y = "Y";
    /**
     * is_deleted是否已逻辑删除(N正常Y删除)
     */
    public static final String N = "N";
    /**
     * is_deleted是否已逻辑删除(0正常1删除)
     */
    public static final byte DELETED = 1;
    /**
     * is_deleted是否已逻辑删除(0正常1删除)
     */
    public static final byte USING = 0;
    /**
     * is_opened是否已逻辑删除(0公开1私有)
     */
    public static final byte OPENED = 0;
    /**
     * is_opened是否已逻辑删除(0公开1私有)
     */
    public static final byte OWNED = 1;
}
