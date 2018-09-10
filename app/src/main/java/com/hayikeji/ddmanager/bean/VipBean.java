package com.hayikeji.ddmanager.bean;

import com.hayikeji.ddmanager.ui.adapter.IVip;
import com.hayikeji.ddmanager.utils.DateUtils;

/**
 * 描述：
 * 邮箱 email:strive_bug@yeah.net
 * 创建时间 2018/9/10
 *
 * @author ql
 */
public class VipBean implements IVip{

    /**
     * id : 57
     * createTime : 1535048887000
     * endTime : 1598198400000
     * comment :
     * buyMonths : 24
     * outTradeNo : abcde1231535048887586
     * status : 1
     * money : 0
     * userId : 180
     * code : 018200000104
     * deviceId : 48
     * vipPrice : 20
     * vipCharge : 1
     * startTime : 1535040000000
     */

    private int id;
    private long createTime;
    private long endTime;
    private String comment;
    private int buyMonths;
    private String outTradeNo;
    private int status;
    private int money;
    private int userId;
    private String code;
    private int deviceId;
    private int vipPrice;
    private int vipCharge;
    private long startTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public CharSequence getStartDate() {
        return DateUtils.getStringDate4(createTime);
    }

    @Override
    public CharSequence getDevCord() {
        if (code == null) {
            return "未知";
        }
        return code;
    }

    @Override
    public CharSequence getDoc() {
        if (outTradeNo == null) {
            return "未知";
        }
        return outTradeNo;
    }

    @Override
    public CharSequence getPayEndDate() {
        return DateUtils.getStringDate4(endTime);
    }

    @Override
    public CharSequence getBuyMonths() {
        return buyMonths+"";
    }

    @Override
    public CharSequence getPrice() {
        double f = money * 1.0f /100;
        return String.format("%.2f",f);
    }

    public void setBuyMonths(int buyMonths) {
        this.buyMonths = buyMonths;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public int getVipPrice() {
        return vipPrice;
    }

    public void setVipPrice(int vipPrice) {
        this.vipPrice = vipPrice;
    }

    public int getVipCharge() {
        return vipCharge;
    }

    public void setVipCharge(int vipCharge) {
        this.vipCharge = vipCharge;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }
}
