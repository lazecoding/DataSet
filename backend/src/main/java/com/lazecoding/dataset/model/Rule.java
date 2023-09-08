package com.lazecoding.dataset.model;

import java.io.Serializable;
import java.util.UUID;

/**
 * 规则
 */
public class Rule implements Serializable {

    private static final long serialVersionUID = -1;

    /**
     * Id
     */
    private String id;

    /**
     * 正则
     */
    private String regex;

    /**
     * 描述
     */
    private String desc;

    public Rule() {
        this.id = UUID.randomUUID().toString().replace("-", "");
    }

    public Rule(String regex) {
        this.id = UUID.randomUUID().toString().replace("-", "");
        this.regex = regex;
    }

    public Rule(String regex, String desc) {
        this.id = UUID.randomUUID().toString().replace("-", "");
        this.regex = regex;
        this.desc = desc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
