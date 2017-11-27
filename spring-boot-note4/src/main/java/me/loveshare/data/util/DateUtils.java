package me.loveshare.data.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Tony on 2017/9/12.
 */
public class DateUtils {

    /**
     * 时间格式1 : 2017-09-12
     */
    public static final String TIME_STYLE_1 = "yyyy-MM-dd";
    /**
     * 时间格式2 : 2017-09-12 10:20
     */
    public static final String TIME_STYLE_2 = "yyyy-MM-dd HH:mm";
    /**
     * 时间格式3 : 2017-09-12 10:20:32
     */
    public static final String TIME_STYLE_3 = "yyyy-MM-dd HH:mm:ss";
    /**
     * 时间格式4 : 2017-09-12 10:20:32:930
     */
    public static final String TIME_STYLE_4 = "yyyy-MM-dd HH:mm:ss:S";
    /**
     * 时间格式5 : 2017-09-12 10:20:32:930 星期二 CST+0800
     */
    public static final String TIME_STYLE_5 = "yyyy-MM-dd HH:mm:ss:S E zZ";
    /**
     * 时间格式6 : 2017年09月12日10时22分34秒
     */
    public static final String TIME_STYLE_6 = "yyyy年MM月dd日HH时mm分ss秒";

    /**
     * 时间格式7 : 20170912102332642
     */
    public static final String TIME_STYLE_7 = "yyyyMMddHHmmssS";

    /**
     * 按格式返回当前时间字符串
     */
    public static final String current(int style) {
        return time(null, style);
    }

    /**
     * 按格式返回时间字符串
     *
     * @param time  日期对象，若time=null，则返回当前时间
     * @param style style 日期模板编号，例：TIME_STYLE_5则为5，默认：TIME_STYLE_3
     * @return 格式化日期字符串
     */
    public static final String time(Date time, int style) {
        if (time == null) {
            time = new Date();
        }
        String timeStyle = null;
        switch (style) {
            case 1: {
                timeStyle = TIME_STYLE_1;
                break;
            }
            case 2: {
                timeStyle = TIME_STYLE_2;
                break;
            }
            case 4: {
                timeStyle = TIME_STYLE_4;
                break;
            }
            case 5: {
                timeStyle = TIME_STYLE_5;
                break;
            }
            case 6: {
                timeStyle = TIME_STYLE_6;
                break;
            }
            case 7: {
                timeStyle = TIME_STYLE_7;
                break;
            }
            default: {
                timeStyle = TIME_STYLE_3;
                break;
            }
        }
        return new SimpleDateFormat(timeStyle).format(time);
    }

    /**
     * 日期字符串转换成Date
     *
     * @param dateString 日期字符串
     * @return 日期对象
     * @throws ParseException
     */
    public static final Date string2date(String dateString) throws ParseException {
        return new SimpleDateFormat(TIME_STYLE_3).parse(dateString);
    }

    /**
     * 比较两个日期之间的大小
     *
     * @param date1 日期对象1
     * @param date2 日期对象2
     * @return date1 > date2，返回true，反之false
     */
    public static final boolean compareDate(Date date1, Date date2) {
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.setTime(date1);
        calendar2.setTime(date2);
        return calendar1.compareTo(calendar2) >= 0;
    }

    /**
     * 根据出生日期获得年龄
     *
     * @param birthday 出生日期
     * @return 年龄
     */
    public static final byte getAge(Date birthday) {
        if (birthday == null) {
            return 0;
        }
        Calendar cal = Calendar.getInstance();

        if (cal.before(birthday)) {
            return 0;
        }
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthday);

        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        byte age = (byte) (yearNow - yearBirth);

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                }
            } else {
                age--;
            }
        }
        return age;
    }

    /**
     * 获取时间的距离描述，两天前 ：例：2015-07-15 -> 两年前
     */
    public static final String relative(Date date) {
        return RelativeDateFormat.format(date);
    }
}

class RelativeDateFormat {
    private static final long ONE_MINUTE = 60000L;
    private static final long ONE_HOUR = 3600000L;
    private static final long ONE_DAY = 86400000L;
    private static final long ONE_WEEK = 604800000L;

    private static final String ONE_SECOND_AGO = "秒前";
    private static final String ONE_MINUTE_AGO = "分钟前";
    private static final String ONE_HOUR_AGO = "小时前";
    private static final String ONE_DAY_AGO = "天前";
    private static final String ONE_MONTH_AGO = "月前";
    private static final String ONE_YEAR_AGO = "年前";

    public static String format(Date date) {
        long delta = System.currentTimeMillis() - date.getTime();
        if (delta < 1L * ONE_MINUTE) {
            long seconds = toSeconds(delta);
            return (seconds <= 0 ? 1 : seconds) + ONE_SECOND_AGO;
        }
        if (delta < 45L * ONE_MINUTE) {
            long minutes = toMinutes(delta);
            return (minutes <= 0 ? 1 : minutes) + ONE_MINUTE_AGO;
        }
        if (delta < 24L * ONE_HOUR) {
            long hours = toHours(delta);
            return (hours <= 0 ? 1 : hours) + ONE_HOUR_AGO;
        }
        if (delta < 48L * ONE_HOUR) {
            return "昨天";
        }
        if (delta < 30L * ONE_DAY) {
            long days = toDays(delta);
            return (days <= 0 ? 1 : days) + ONE_DAY_AGO;
        }
        if (delta < 12L * 4L * ONE_WEEK) {
            long months = toMonths(delta);
            return (months <= 0 ? 1 : months) + ONE_MONTH_AGO;
        } else {
            long years = toYears(delta);
            return (years <= 0 ? 1 : years) + ONE_YEAR_AGO;
        }
    }

    private static long toSeconds(long date) {
        return date / 1000L;
    }

    private static long toMinutes(long date) {
        return toSeconds(date) / 60L;
    }

    private static long toHours(long date) {
        return toMinutes(date) / 60L;
    }

    private static long toDays(long date) {
        return toHours(date) / 24L;
    }

    private static long toMonths(long date) {
        return toDays(date) / 30L;
    }

    private static long toYears(long date) {
        return toMonths(date) / 365L;
    }
}

















