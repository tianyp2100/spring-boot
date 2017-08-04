package me.loveshare.note3.data.entity.dbo;

import javax.persistence.*;

public class T1 {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 名称
     */
    private String names;

    /**
     * 电话
     */
    private String phone;

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
     * 获取名称
     *
     * @return names - 名称
     */
    public String getNames() {
        return names;
    }

    /**
     * 设置名称
     *
     * @param names 名称
     */
    public void setNames(String names) {
        this.names = names;
    }

    /**
     * 获取电话
     *
     * @return phone - 电话
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置电话
     *
     * @param phone 电话
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }
}