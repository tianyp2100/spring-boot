package me.loveshare.data.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期处理
 */
public class DateUtils {

    /**
     * 毫秒日期格式 :年-月-日 时:分:秒:毫秒  星期  时区: yyyy-MM-dd HH:mm:ss:S E zZ
     */
    public static final String TIMESTAMP_ZONE_STYLE = "yyyy-MM-dd HH:mm:ss:S E zZ";

    /**
     * 毫秒日期格式 :年-月-日 时:分:秒:毫秒: yyyy-MM-dd HH:mm:ss:S
     */
    public static final String TIMESTAMP_STYLE = "yyyy-MM-dd HH:mm:ss:S";

    public static final String TIMESTAMP_STYLE_NO = "yyyyMMddHHmmssS";

    /**
     * 毫秒日期格式 :年-月-日 时:分:秒: yyyy-MM-dd HH:mm:ss
     */
    public static final String TIME_0_STYLE = "yyyy-MM-dd HH:mm:ss";
    public static final String TIME_0_STYLE_CHINESE = "yyyy年MM月dd日HH时mm分ss秒";

    /**
     * 毫秒日期格式 :年-月-日 时:分: yyyy-MM-dd HH:mm
     */
    public static final String TIME_1_STYLE = "yyyy-MM-dd HH:mm";

    /**
     * 毫秒日期格式 :年-月-日: yyyy-MM-dd
     */
    public static final String DATE_STYLE = "yyyy-MM-dd";

    /**
     * 获取日期、星期、时区
     * yyyy-MM-dd HH:mm:ss:S E zZ
     */
    public static final String timestampzone(Date date) {
        return new SimpleDateFormat(TIMESTAMP_ZONE_STYLE).format(date);
    }

    /**
     * yyyy-MM-dd HH:mm:ss:S
     */
    public static final String timestamp(Date date) {
        return new SimpleDateFormat(TIMESTAMP_STYLE).format(date);
    }

    public static final String timestampx(long timestamp) {
        return new SimpleDateFormat(TIMESTAMP_STYLE_NO).format(timestamp);
    }

    /**
     * yyyy-MM-dd HH:mm:ss:S
     */
    public static final String timestamp(long date) {
        return new SimpleDateFormat(TIMESTAMP_STYLE).format(date);
    }

    public static final String timestampNow() {
        return new SimpleDateFormat(TIMESTAMP_STYLE).format(System.currentTimeMillis());
    }

    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static final String times(Date date) {
        return new SimpleDateFormat(TIME_0_STYLE).format(date);
    }

    /**
     * 2017年05月22日19时38分57秒
     */
    public static final String timesc(Date date) {
        return new SimpleDateFormat(TIME_0_STYLE_CHINESE).format(date);
    }

    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static final String times(long date) {
        return new SimpleDateFormat(TIME_0_STYLE).format(date);
    }

    /**
     * yyyy-MM-dd HH:mm
     */
    public static final String datehourminute(Date date) {
        return new SimpleDateFormat(TIME_1_STYLE).format(date);
    }

    /**
     * yyyy-MM-dd HH:mm
     */
    public static final String datehourminute(long date) {
        return new SimpleDateFormat(TIME_1_STYLE).format(date);
    }

    /**
     * yyyy-MM-dd
     */
    public static final String date(Date date) {
        return new SimpleDateFormat(DATE_STYLE).format(date);
    }

    /**
     * yyyy-MM-dd
     */
    public static final String date(long date) {
        return new SimpleDateFormat(DATE_STYLE).format(date);
    }

    /**
     * 字符串转换成Date
     */
    public static final Date getDateFromString(String dateTime) {
        Date date = null;

        SimpleDateFormat sdf = new SimpleDateFormat(TIME_0_STYLE);
        try {
            date = sdf.parse(dateTime);
        } catch (ParseException p) {

        }

        return date;
    }

    /**
     * 比较两个日期之间的大小
     *
     * @param d1
     * @param d2
     * @return 前者大于后者返回true 反之false
     */
    public static final boolean compare2Date(Date d1, Date d2) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(d1);
        c2.setTime(d2);

        int result = c1.compareTo(c2);
        if (result >= 0)
            return true;
        else
            return false;
    }

    /**
     * 根据出生日期获得年龄
     */
    public static byte getAge(Date birthDay) {
        if (birthDay == null) return 0;
        Calendar cal = Calendar.getInstance();

        if (cal.before(birthDay)) {
            return 0;
        }
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDay);

        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        byte age = (byte) (yearNow - yearBirth);

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;
            } else {
                age--;
            }
        }
        return age;
    }

    /**
     * 根据时间获取 ：例：2015-07-15 -> 两年前
     */
    public static String characterDate(Date date) {
        return RelativeDateFormatUtils.format(date);
    }

    /**
     * 获取当前时间下一天的日期
     *
     * @param time
     * @return
     */
    public static String getTomorrowStr(Date time) {
        Calendar c = Calendar.getInstance();
        c.setTime(time);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day + 1);
        return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
    }

    /**
     * 获取当前时间上一天的日期
     *
     * @param time
     * @return
     */
    public static String getYesterdayStr(Date time) {
        Calendar c = Calendar.getInstance();
        c.setTime(time);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - 1);
        return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
    }

    /**
     * 字符串转换日期
     */
    public static Date string2date(String dateStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_STYLE);
        return sdf.parse(dateStr);
    }

    /**
     * 字符串转换日期+日期
     */
    public static Date string2datetime(String datetimeStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(TIME_0_STYLE);
        return sdf.parse(datetimeStr);
    }

}
