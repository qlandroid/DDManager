package com.hayikeji.ddmananger.bean;

import com.hayikeji.ddmananger.ui.adapter.IPayMsgDoc;
import com.hayikeji.ddmananger.utils.DateUtils;

/**
 * 描述：
 * 邮箱 email:strive_bug@yeah.net
 * 创建时间 2018/8/19
 *
 * @author ql
 */
public class TransationDocBean implements IPayMsgDoc {
    private Integer id;
    private Integer devicesId;
    private Integer buyElectric;//购电量
    private Integer money;//购电金额
    private Long createTime;//创建时间
    private Integer userId;//用户id
    private String outTradeNo;//订单号
    private String status;//订单状态
    private String code;//设备编号
    private Integer elecPrice;//电价格
    private Integer elecCharge;//电价服务费

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDevicesId() {
        return devicesId;
    }

    public void setDevicesId(Integer devicesId) {
        this.devicesId = devicesId;
    }

    public Integer getBuyElectric() {
        return buyElectric;
    }

    public void setBuyElectric(Integer buyElectric) {
        this.buyElectric = buyElectric;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getElecPrice() {
        return elecPrice;
    }

    public void setElecPrice(Integer elecPrice) {
        this.elecPrice = elecPrice;
    }

    public Integer getElecCharge() {
        return elecCharge;
    }

    public void setElecCharge(Integer elecCharge) {
        this.elecCharge = elecCharge;
    }

    @Override
    public CharSequence getPayEAmount() {
        if (buyElectric == null) {
            return "0";
        }
        return buyElectric+"";
    }

    @Override
    public CharSequence getPayDate() {
        if (createTime == null) {
            return "";
        }

        return DateUtils.getStringDate4(createTime * 1000);
    }

    @Override
    public CharSequence getDevCode() {
        if (code == null) {
            return "";
        }
        return code;
    }

    @Override
    public CharSequence getDoc() {
        if (outTradeNo == null) {
            return "";
        }
        return outTradeNo;
    }

    @Override
    public CharSequence getPayPrice() {
        if (money == null) {
            money = 0;
        }
        return money + "";
    }
}
