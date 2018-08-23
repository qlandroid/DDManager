package com.hayikeji.ddmananger.ui.adapter.bean;

import com.hayikeji.ddmananger.bean.DeviceBean;

/**
 * 描述：
 * 邮箱 email:strive_bug@yeah.net
 * 创建时间 2018/8/19
 *
 * @author ql
 */
public class ECloseManager implements IECloseManager {
    private DeviceBean deviceBean;
    private boolean isShowPower;

    private boolean isCanSwitch;
    private boolean isShowSwitch;

    public ECloseManager(DeviceBean deviceBean) {
        this.deviceBean = deviceBean;
    }

    public void setCanSwitch(boolean canSwitch) {
        isCanSwitch = canSwitch;
    }

    public void setShowSwitch(boolean showSwitch) {
        isShowSwitch = showSwitch;
    }

    public DeviceBean getDeviceBean() {
        return deviceBean;
    }

    @Override
    public CharSequence getPower() {
        return "";
    }

    public void setShowPower(boolean showPower) {
        isShowPower = showPower;
    }

    @Override
    public boolean isHavePowerAssign() {
        return false;
    }

    @Override
    public boolean isCanSwitch() {
        return isCanSwitch;
    }

    @Override
    public boolean isRun() {
        return 1 == deviceBean.getDStatus();
    }

    @Override
    public boolean isShowPower() {
        return isShowPower;
    }

    @Override
    public boolean isShowSwitch() {
        return isShowSwitch;
    }

    @Override
    public CharSequence getDevNo() {

        return deviceBean.getCode();
    }

    @Override
    public CharSequence getDevName() {
        return deviceBean.getNickname();
    }

    @Override
    public CharSequence getDevRoom() {
        return deviceBean.getDevRoom();
    }

    @Override
    public CharSequence getDevOwner() {
        return deviceBean.getDevOwner();
    }
}
