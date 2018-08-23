package com.hayikeji.ddmananger.ui.adapter.bean;

/**
 * 描述：
 * 邮箱 email:strive_bug@yeah.net
 * 创建时间 2018/8/13
 *
 * @author ql
 */
public interface IECloseManager extends IDevDetails {

    CharSequence getPower();

    /**
     * 是否有分配权限的 权利
     *
     * @return
     */
    boolean isHavePowerAssign();

    /**
     * 是否拥有开关的权利
     *
     * @return
     */
    boolean isCanSwitch();

    boolean isRun();

    boolean isShowPower();

    boolean isShowSwitch();
}
