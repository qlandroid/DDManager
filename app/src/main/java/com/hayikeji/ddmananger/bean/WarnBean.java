package com.hayikeji.ddmananger.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.hayikeji.ddmananger.ui.adapter.HomeContentAdapter;
import com.hayikeji.ddmananger.ui.adapter.bean.IHomeError;
import com.hayikeji.ddmananger.utils.DateUtils;

/**
 * 描述：
 * 邮箱 email:strive_bug@yeah.net
 * 创建时间 2018/8/19
 *
 * @author ql
 */
public class WarnBean implements IHomeError ,MultiItemEntity {
    private Integer id;
    private String warn;
    private DeviceBean device;
    private Long wtime;
    private int deviceId;
    private String code;
    private String handleStatus;
    private String alarmType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWarn() {
        return warn;
    }

    public void setWarn(String warn) {
        this.warn = warn;
    }

    public DeviceBean getDevice() {
        return device;
    }

    public void setDevice(DeviceBean device) {
        this.device = device;
    }

    public Long getWtime() {
        return wtime;
    }

    public void setWtime(Long wtime) {
        this.wtime = wtime;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getHandleStatus() {
        return handleStatus;
    }

    public void setHandleStatus(String handleStatus) {
        this.handleStatus = handleStatus;
    }

    public String getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(String alarmType) {
        this.alarmType = alarmType;
    }

    @Override
    public CharSequence getTitle() {
        return "异常通知";
    }

    @Override
    public CharSequence getDevCode() {
        if (device == null) {
            return "";
        }

        return device.getCode();
    }

    @Override
    public CharSequence getErrorContent() {
        if (warn == null) {
            return "";
        }
        return warn;
    }

    @Override
    public CharSequence getDate() {
        if (wtime == null) {
            return "未知时间";
        }
        String stringDate4 = DateUtils.getStringDate4(wtime * 1000);
        return stringDate4;
    }

    @Override
    public int getItemType() {
        return HomeContentAdapter.ERROR;
    }
}
