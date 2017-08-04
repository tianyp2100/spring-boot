package me.loveshare.note3.data.entity.dbo;

import javax.persistence.*;

public class T2 {
    /**
     * 主键id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 年龄
     */
    private Byte age;

    /**
     * 级别
     */
    private Byte star;

    /**
     * 获取主键id
     *
     * @return id - 主键id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置主键id
     *
     * @param id 主键id
     */
    public void setId(Integer id) {
        this.id = id;
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
     * 获取级别
     *
     * @return star - 级别
     */
    public Byte getStar() {
        return star;
    }

    /**
     * 设置级别
     *
     * @param star 级别
     */
    public void setStar(Byte star) {
        this.star = star;
    }
}