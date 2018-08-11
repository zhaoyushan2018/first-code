package com.xiaoshan.erp.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
public class Customer implements Serializable {
    /**
     * 用户ID
     */
    private Integer id;

    /**
     * 客户名称
     */
    private String userName;

    /**
     * 身份证
     */
    private String idCard;

    /**
     * 手机号
     */
    private String tel;

    /**
     * 创建时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", idCard='" + idCard + '\'' +
                ", tel='" + tel + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}