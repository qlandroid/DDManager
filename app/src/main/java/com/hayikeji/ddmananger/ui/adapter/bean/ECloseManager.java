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



    public ECloseManager(DeviceBean deviceBean) {
        this.deviceBean = deviceBean;
    }
    public DeviceBean getDeviceBean() {
        return deviceBean;
    }
    @Override
    public CharSequence getPower() {
        return "";
    }

    @Override
    public boolean isHavePowerAssign() {
        return false;
    }

    @Override
    public boolean isCanSwitch() {
        return false;
    }

    @Override
    public boolean isRun() {
        return 1 == deviceBean.getDStatus();
    }

    @Override
    public boolean isShowPower() {
        return false;
    }

    @Override
    public boolean isShowSwitch() {
        return false;
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
