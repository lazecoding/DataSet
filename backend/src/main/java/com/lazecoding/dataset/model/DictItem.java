package com.lazecoding.dataset.model;

import java.io.Serializable;
import java.util.UUID;

/**
 * 词典
 */
public class DictItem implements Serializable {

    private static final long serialVersionUID = -1;

    /**
     * Id
     */
    private String id;

    /**
     * 值
     */
    private String value;

    /**
     * 描述
     */
    private String desc;


    public DictItem() {
        this.id = UUID.randomUUID().toString().replace("-", "");
    }

    public DictItem(String value, String desc) {
        this.id = UUID.randomUUID().toString().replace("-", "");
        this.value = value;
        this.desc = desc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
