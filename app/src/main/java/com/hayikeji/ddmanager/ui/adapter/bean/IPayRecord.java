package com.hayikeji.ddmanager.ui.adapter.bean;

/**
 * 描述：
 * 邮箱 email:strive_bug@yeah.net
 * 创建时间 2018/8/17
 *
 * @author ql
 */
public interface IPayRecord {
     CharSequence getPayDate();

    CharSequence getDevCode();

    /**
     * 充值金额
     *
     * @return
     */
    CharSequence getPayAmount();

    /**
     * 获得充值状态
     *
     * @return
     */
    CharSequence getPayStatus();

}
