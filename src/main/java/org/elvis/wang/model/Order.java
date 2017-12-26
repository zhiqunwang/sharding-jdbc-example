package org.elvis.wang.model;

import java.io.Serializable;
import java.util.Date;

/**
 * create by elvis.wang  2017/11/15
 */
public class Order implements Serializable{
    private static final long serialVersionUID = 8537982127595524162L;

    private int orderId;

    private int userId;

    private Date createTime;

    private Date updateTime;

    private Date startTime;

    private Date endTime;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Order() {
    }

    public Order(int orderId, int userId, Date createTime, Date updateTime) {
        this.orderId = orderId;
        this.userId = userId;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }
}
