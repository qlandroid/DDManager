package com.hayikeji.ddmananger.ui.adapter;

import java.nio.charset.Charset;

/**
 * 描述：设备详细信息
 * 邮箱 email:strive_bug@yeah.net
 * 创建时间 2018/8/7
 *
 * @author ql
 */
public interface IDevDetails {
    /**
     * 设备编号
     *
     * @return
     */
    CharSequence getDevNo();

    /**
     * 设备名称
     *
     * @return
     */
    CharSequence getDevName();

    /**
     * 设备门牌号
     *
     * @return
     */
    CharSequence getDevDoor();

    /**
     * 设备所有人
     *
     * @return
     */
    CharSequence getDevOwner();
}
