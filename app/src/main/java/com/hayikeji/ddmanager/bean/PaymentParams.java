package com.hayikeji.ddmanager.bean;

/**
 * 描述：
 * 邮箱 email:strive_bug@yeah.net
 * 创建时间 2018/9/4
 *
 * @author ql
 */
public class PaymentParams {

    private String deviceName;

    private String deviceType;

    private Integer voltage;

    private String currentSpec;

    private Integer deviceNum;

    private String category;

    private Integer price;

    private Integer charge;

    private Integer vipPrice;

    private Integer vipCharge;

    private String devInsInfo;

    private String openBank;

    private String bankName;

    private String contacts;

    private String accNum;

    private String contactsNum;

    private String postalAddress;

    private String receAddress;

    private String consignee;

    private String phone;
    private int buyCount;//购买数量
    private int goodsId;

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public Integer getVoltage() {
        return voltage;
    }

    public void setVoltage(Integer voltage) {
        this.voltage = voltage;
    }

    public String getCurrentSpec() {
        return currentSpec;
    }

    public void setCurrentSpec(String currentSpec) {
        this.currentSpec = currentSpec;
    }

    public Integer getDeviceNum() {
        return deviceNum;
    }

    public void setDeviceNum(Integer deviceNum) {
        this.deviceNum = deviceNum;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getCharge() {
        return charge;
    }

    public void setCharge(Integer charge) {
        this.charge = charge;
    }

    public Integer getVipPrice() {
        return vipPrice;
    }

    public void setVipPrice(Integer vipPrice) {
        this.vipPrice = vipPrice;
    }

    public Integer getVipCharge() {
        return vipCharge;
    }

    public void setVipCharge(Integer vipCharge) {
        this.vipCharge = vipCharge;
    }

    public String getDevInsInfo() {
        return devInsInfo;
    }

    public void setDevInsInfo(String devInsInfo) {
        this.devInsInfo = devInsInfo;
    }

    public String getOpenBank() {
        return openBank;
    }

    public void setOpenBank(String openBank) {
        this.openBank = openBank;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getAccNum() {
        return accNum;
    }

    public void setAccNum(String accNum) {
        this.accNum = accNum;
    }

    public String getContactsNum() {
        return contactsNum;
    }

    public void setContactsNum(String contactsNum) {
        this.contactsNum = contactsNum;
    }

    public String getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }

    public String getReceAddress() {
        return receAddress;
    }

    public void setReceAddress(String receAddress) {
        this.receAddress = receAddress;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setBuyCount(int buyCount) {
        this.buyCount = buyCount;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }
}
