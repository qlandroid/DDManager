package com.hayikeji.ddmanager.bean;

/**
 * 描述：
 * 邮箱 email:strive_bug@yeah.net
 * 创建时间 2018/9/6
 *
 * @author ql
 */
public class PriceBean {

    private Integer vipUnit;//会员单价
    private Integer eleUnit;//电费单价；

    public Integer getVipUnit() {
        return vipUnit;
    }

    public void setVipUnit(Integer vipUnit) {
        this.vipUnit = vipUnit;
    }

    public Integer getEleUnit() {
        return eleUnit;
    }

    public void setEleUnit(Integer eleUnit) {
        this.eleUnit = eleUnit;
    }
}
