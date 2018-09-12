package com.hayikeji.ddmanager.ui.adapter.bean;

import android.support.annotation.DrawableRes;

/**
 * 描述：
 * 邮箱 email:strive_bug@yeah.net
 * 创建时间 2018/8/17
 *
 * @author ql
 */
public interface IPayType {

    int getTag();


    CharSequence getTypeName();

    @DrawableRes
    int getTypeIconRes();
}
