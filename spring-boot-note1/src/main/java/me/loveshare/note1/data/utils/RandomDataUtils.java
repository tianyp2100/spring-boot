package me.loveshare.note1.data.utils;

import me.loveshare.note1.data.constant.ConstantDatas;

import java.util.*;

/**
 * Created by Tony on 2017/7/31.
 * 随机数据
 */
public class RandomDataUtils {

    private static Random random = new Random();

    /**
     * 随机姓名
     */
    public static String compellation() {
        return ConstantDatas.FIRST_NAME[random(ConstantDatas.FIRST_NAME.length)] + ConstantDatas.NAME_LIST[random(ConstantDatas.NAME_LIST.length)];
    }

    /**
     * 随机性别
     */
    public static String sex() {
        return random(2) == 1 ? "Boy" : "Girl";
    }

    /**
     * 随机血型
     */
    public static String bloodType() {
        return ConstantDatas.bloodArray[random(4)];
    }

    /**
     * 随机出生地
     */
    public static int birthplace() {
        return Integer.parseInt(new ArrayList<String>(ConstantDatas.provinceMap.keySet()).get(random(34)));
    }

    /**
     * 随机学历
     */
    public static byte eduDegree() {
        return (byte) (random(8) + 1);
    }

    /**
     * 随机头像
     */
    public static String avatar() {
        return "http://loveshare.oss-cn-shanghai.aliyuncs.com/universal/image/avatar/1/" + (random(20) + 1) + ".png";
    }

    /**
     * 随机自我介绍
     */
    public static String aboutMe() {
        Set<Integer> idxs = new HashSet<Integer>();
        int len = ConstantDatas.ABOUT_ME.length;
        while (idxs.size() < 4) {
            idxs.add(random(len));
        }
        StringBuilder su = new StringBuilder();
        for (Integer idx : idxs) {
            su.append(ConstantDatas.ABOUT_ME[idx]).append("，");
        }
        return su.substring(0, su.length() - 1).toString();
    }

    /**
     * 随机生日
     */
    public static Date birthday(int startY, int endY) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(startY, 01, 01);
        calendar.getTime().getTime();
        long min = calendar.getTime().getTime();
        calendar.set(endY, 11, 31);
        calendar.getTime().getTime();
        long max = calendar.getTime().getTime();
        double randomDate = Math.random() * (max - min) + min;
        calendar.setTimeInMillis(Math.round(randomDate));
        return calendar.getTime();
    }

    /**
     * 根据生日获得年龄
     */
    public static byte age(Date birthDay) {
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
     * 根据生日获取生肖  --中文
     */
    public static String zodica(Date date) {
        if (date == null) {
            return "";
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return ConstantDatas.zodiacList[cal.get(Calendar.YEAR) % 12];
    }

    /**
     * 根据生日获取生肖  --英文
     */
    public static String zodicaE(Date date) {
        return mapValue2Key(ConstantDatas.zodiacMap, zodica(date));
    }

    /**
     * 根据生日获取星座  --中文
     */
    public static String constellation(Date date) {
        if (date == null) {
            return "";
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        if (day < ConstantDatas.dayArray[month]) {
            month = month - 1;
        }
        if (month >= 0) {
            return ConstantDatas.constellationList[month];
        }
        return ConstantDatas.constellationList[11]; //摩羯座
    }

    /**
     * 根据生日获取星座  --英文
     */
    public static String constellationE(Date date) {
        return mapValue2Key(ConstantDatas.constellationMap, constellation(date));
    }

    /**
     * 获取随机数
     */
    public static int random(int maxNumber) {
        return random.nextInt(maxNumber);
    }

    /**
     * 通过value获取key
     */
    public static String mapValue2Key(Map<String, String> map, String value) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (value.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }
}
