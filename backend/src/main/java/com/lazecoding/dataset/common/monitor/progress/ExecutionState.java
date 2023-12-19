package com.lazecoding.dataset.common.monitor.progress;

import java.io.Serializable;
import java.util.Date;

/**
 * 执行状态
 *
 * @author lazecoding
 */
public class ExecutionState implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识
     */
    private String uid;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String desc;

    /**
     * 开始时间
     */
    private Date stareDate;

    /**
     * 更新时间
     */
    private Date updateDate;

    /**
     * 结束时间
     */
    private Date endDate;

    /**
     * 比例 XX % ， 0 - 100
     */
    private float ratio;

    /**
     * 运行状态(0 未运行；1 运行中；2 运行完毕；3 运行异常)
     */
    private int state;

    /**
     * 扩展信息
     */
    private Object extend;

    /**
     * 临时数据
     */
    private Object temporary;

    public ExecutionState() {
    }

    public ExecutionState(String uid, String name) {
        this.uid = uid;
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Date getStareDate() {
        return stareDate;
    }

    public void setStareDate(Date stareDate) {
        this.stareDate = stareDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public float getRatio() {
        return ratio;
    }

    public void setRatio(float ratio) {
        this.ratio = ratio;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Object getExtend() {
        return extend;
    }

    public void setExtend(Object extend) {
        this.extend = extend;
    }

    public Object getTemporary() {
        return temporary;
    }

    public void setTemporary(Object temporary) {
        this.temporary = temporary;
    }

    @Override
    public String toString() {
        return "ExecutionState{" +
                "uid='" + uid + '\'' +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", stareDate=" + stareDate +
                ", updateDate=" + updateDate +
                ", endDate=" + endDate +
                ", ratio=" + ratio +
                ", state=" + state +
                ", extend=" + extend +
                ", temporary=" + temporary +
                '}';
    }
}
