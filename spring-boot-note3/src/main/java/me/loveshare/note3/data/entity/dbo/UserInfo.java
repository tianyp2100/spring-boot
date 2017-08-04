package me.loveshare.note3.data.entity.dbo;

import java.util.Date;
import javax.persistence.*;

@Table(name = "user_info")
public class UserInfo {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 姓名
     */
    private String compellation;

    /**
     * 性别(1男2女)
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
    @Column(name = "blood_type")
    private String bloodType;

    /**
     * 出生地
     */
    private Integer birthplace;

    /**
     * 教育程度（1:小学 2:初中 3:高中 4:中专 5:大专 6:本科 7:硕士 8:博士）
     */
    @Column(name = "edu_degree")
    private Byte eduDegree;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 自我介绍
     */
    @Column(name = "about_me")
    private String aboutMe;

    /**
     * 创建时间
     */
    @Column(name = "gmt_create")
    private Date gmtCreate;

    /**
     * 更新时间
     */
    @Column(name = "gmt_modified")
    private Date gmtModified;

    /**
     * 是否已逻辑删除(0正常1删除)
     */
    @Column(name = "is_deleted")
    private Byte isDeleted;

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取姓名
     *
     * @return compellation - 姓名
     */
    public String getCompellation() {
        return compellation;
    }

    /**
     * 设置姓名
     *
     * @param compellation 姓名
     */
    public void setCompellation(String compellation) {
        this.compellation = compellation;
    }

    /**
     * 获取性别(1男2女)
     *
     * @return sex - 性别(1男2女)
     */
    public String getSex() {
        return sex;
    }

    /**
     * 设置性别(1男2女)
     *
     * @param sex 性别(1男2女)
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * 获取年龄
     *
     * @return age - 年龄
     */
    public Byte getAge() {
        return age;
    }

    /**
     * 设置年龄
     *
     * @param age 年龄
     */
    public void setAge(Byte age) {
        this.age = age;
    }

    /**
     * 获取生日
     *
     * @return birthday - 生日
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * 设置生日
     *
     * @param birthday 生日
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * 获取生肖
     *
     * @return zodiac - 生肖
     */
    public String getZodiac() {
        return zodiac;
    }

    /**
     * 设置生肖
     *
     * @param zodiac 生肖
     */
    public void setZodiac(String zodiac) {
        this.zodiac = zodiac;
    }

    /**
     * 获取星座
     *
     * @return constellation - 星座
     */
    public String getConstellation() {
        return constellation;
    }

    /**
     * 设置星座
     *
     * @param constellation 星座
     */
    public void setConstellation(String constellation) {
        this.constellation = constellation;
    }

    /**
     * 获取血型
     *
     * @return blood_type - 血型
     */
    public String getBloodType() {
        return bloodType;
    }

    /**
     * 设置血型
     *
     * @param bloodType 血型
     */
    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    /**
     * 获取出生地
     *
     * @return birthplace - 出生地
     */
    public Integer getBirthplace() {
        return birthplace;
    }

    /**
     * 设置出生地
     *
     * @param birthplace 出生地
     */
    public void setBirthplace(Integer birthplace) {
        this.birthplace = birthplace;
    }

    /**
     * 获取教育程度（1:小学 2:初中 3:高中 4:中专 5:大专 6:本科 7:硕士 8:博士）
     *
     * @return edu_degree - 教育程度（1:小学 2:初中 3:高中 4:中专 5:大专 6:本科 7:硕士 8:博士）
     */
    public Byte getEduDegree() {
        return eduDegree;
    }

    /**
     * 设置教育程度（1:小学 2:初中 3:高中 4:中专 5:大专 6:本科 7:硕士 8:博士）
     *
     * @param eduDegree 教育程度（1:小学 2:初中 3:高中 4:中专 5:大专 6:本科 7:硕士 8:博士）
     */
    public void setEduDegree(Byte eduDegree) {
        this.eduDegree = eduDegree;
    }

    /**
     * 获取头像
     *
     * @return avatar - 头像
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * 设置头像
     *
     * @param avatar 头像
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    /**
     * 获取自我介绍
     *
     * @return about_me - 自我介绍
     */
    public String getAboutMe() {
        return aboutMe;
    }

    /**
     * 设置自我介绍
     *
     * @param aboutMe 自我介绍
     */
    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    /**
     * 获取创建时间
     *
     * @return gmt_create - 创建时间
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * 设置创建时间
     *
     * @param gmtCreate 创建时间
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * 获取更新时间
     *
     * @return gmt_modified - 更新时间
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     * 设置更新时间
     *
     * @param gmtModified 更新时间
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * 获取是否已逻辑删除(0正常1删除)
     *
     * @return is_deleted - 是否已逻辑删除(0正常1删除)
     */
    public Byte getIsDeleted() {
        return isDeleted;
    }

    /**
     * 设置是否已逻辑删除(0正常1删除)
     *
     * @param isDeleted 是否已逻辑删除(0正常1删除)
     */
    public void setIsDeleted(Byte isDeleted) {
        this.isDeleted = isDeleted;
    }
}