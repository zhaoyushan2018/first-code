package com.xiaoshan.entity;

import java.io.Serializable;

/**
 * @author 
 */
public class Type implements Serializable {
    /**
     * ID
     */
    private Integer id;

    /**
     * 类型名称
     */
    private String typeName;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}