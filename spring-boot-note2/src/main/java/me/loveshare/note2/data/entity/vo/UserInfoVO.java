package me.loveshare.note2.data.entity.vo;

import lombok.Data;

import java.util.Date;

/**
 * Created by Tony on 2017/8/1.
 */
@Data
public class UserInfoVO {
    /**
     * 主键
     */
    private Integer id;
    /**
     * 姓名
     */
    private String compellation;
    /**
     * 性别
     */
    private String sex;
    /**
     * 年龄
     */
    private Byte age;
    /**
     * 生日
     */
    private Date birthday;
    /**
     * 生肖
     */
    private String zodiac;
    /**
     * 星座
     */
    private String constellation;
    /**
     * 血型
     */
    private String bloodType;
    /**
     * 出生地
     */
    private String birthplace;
    /**
     * 教育程度
     */
    private String eduDegree;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 自我介绍
     */
    private String aboutMe;
}
