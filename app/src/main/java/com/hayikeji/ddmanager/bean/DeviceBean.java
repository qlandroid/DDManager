package com.hayikeji.ddmanager.bean;

import com.hayikeji.ddmanager.ui.adapter.bean.IDevDetails;

/**
 * 描述：
 * 邮箱 email:strive_bug@yeah.net
 * 创建时间 2018/8/18
 *
 * @author ql
 */
public class DeviceBean implements IDevDetails {


    /**
     * id : 50
     * typeId : 182
     * code : 018200000106
     * buyElectric : 18072
     * electric : 0
     * status : 3
     * categoryId : 13
     * operatorId : 13
     * detailedId : 44
     * deviceAdmin : 213
     * deviceAddress : 213
     * usersId : 0
     * time : 1513481185
     * money : 0
     * balance : 0
     * dStatus : 1
     * voltage : 17582
     * electricCurrent : 0
     * power : 0
     * reactivePower : 0
     * apparentPower : 0
     * powerFactor : 1000
     * frequency : 4999
     * leakageCurrent : 0
     * temperature : 0
     * humidity : 0
     * detectionsign : 0
     * version : 207
     * updatetime : 1531897258
     * nickname : 花房
     * elecStatus : 1
     * replaceStatus : 0
     * replaceReason :
     * latitude : null
     * longitude : null
     */

    private int id;
    private String typeId;
    private String code;
    private int buyElectric;
    private int electric;
    private int status;
    private int categoryId;
    private int operatorId;
    private int detailedId;
    private String deviceAdmin;
    private String deviceAddress;
    private int usersId;
    private long time;
    private int money;
    private int balance;
    private int dStatus;
    private int voltage;
    private int electricCurrent;
    private int power;
    private int reactivePower;
    private int apparentPower;
    private int powerFactor;
    private int frequency;
    private int leakageCurrent;
    private int temperature;
    private int humidity;
    private int detectionsign;
    private int version;
    private long updatetime;
    private String nickname;
    private int elecStatus;
    private int replaceStatus;
    private String replaceReason;
    private Object latitude;
    private Object longitude;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getCode() {
        if (code == null) {
            return "";
        }
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getBuyElectric() {
        return buyElectric;
    }

    public void setBuyElectric(int buyElectric) {
        this.buyElectric = buyElectric;
    }

    public int getElectric() {
        return electric;
    }

    public void setElectric(int electric) {
        this.electric = electric;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(int operatorId) {
        this.operatorId = operatorId;
    }

    public int getDetailedId() {
        return detailedId;
    }

    public void setDetailedId(int detailedId) {
        this.detailedId = detailedId;
    }

    public String getDeviceAdmin() {
        return deviceAdmin;
    }

    public void setDeviceAdmin(String deviceAdmin) {
        this.deviceAdmin = deviceAdmin;
    }

    public String getDeviceAddress() {
        return deviceAddress;
    }

    public void setDeviceAddress(String deviceAddress) {
        this.deviceAddress = deviceAddress;
    }

    public int getUsersId() {
        return usersId;
    }

    public void setUsersId(int usersId) {
        this.usersId = usersId;
    }

    public long getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getDStatus() {
        return dStatus;
    }

    public void setDStatus(int dStatus) {
        this.dStatus = dStatus;
    }

    public int getVoltage() {
        return voltage;
    }

    public void setVoltage(int voltage) {
        this.voltage = voltage;
    }

    public int getElectricCurrent() {
        return electricCurrent;
    }

    public void setElectricCurrent(int electricCurrent) {
        this.electricCurrent = electricCurrent;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getReactivePower() {
        return reactivePower;
    }

    public void setReactivePower(int reactivePower) {
        this.reactivePower = reactivePower;
    }

    public int getApparentPower() {
        return apparentPower;
    }

    public void setApparentPower(int apparentPower) {
        this.apparentPower = apparentPower;
    }

    public int getPowerFactor() {
        return powerFactor;
    }

    public void setPowerFactor(int powerFactor) {
        this.powerFactor = powerFactor;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public int getLeakageCurrent() {
        return leakageCurrent;
    }

    public void setLeakageCurrent(int leakageCurrent) {
        this.leakageCurrent = leakageCurrent;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getDetectionsign() {
        return detectionsign;
    }

    public void setDetectionsign(int detectionsign) {
        this.detectionsign = detectionsign;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public long getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(long updatetime) {
        this.updatetime = updatetime;
    }

    public String getNickname() {
        if (nickname == null) {
            return "";
        }
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getElecStatus() {
        return elecStatus;
    }

    public void setElecStatus(int elecStatus) {
        this.elecStatus = elecStatus;
    }

    public int getReplaceStatus() {
        return replaceStatus;
    }

    public void setReplaceStatus(int replaceStatus) {
        this.replaceStatus = replaceStatus;
    }

    public String getReplaceReason() {
        return replaceReason;
    }

    public void setReplaceReason(String replaceReason) {
        this.replaceReason = replaceReason;
    }

    public Object getLatitude() {
        return latitude;
    }

    public void setLatitude(Object latitude) {
        this.latitude = latitude;
    }

    public Object getLongitude() {
        return longitude;
    }

    public void setLongitude(Object longitude) {
        this.longitude = longitude;
    }

    @Override
    public CharSequence getDevNo() {
        if (code == null) {
            return "";
        }
        return code;
    }

    @Override
    public CharSequence getDevName() {
        if (nickname == null) {
            return "";
        }
        return nickname;
    }

    @Override
    public CharSequence getDevRoom() {
        if (deviceAddress == null) {
            return "";
        }
        return deviceAddress;
    }

    @Override
    public CharSequence getDevOwner() {
        if (deviceAdmin == null) {
            return "";
        }
        return deviceAdmin;
    }
}
