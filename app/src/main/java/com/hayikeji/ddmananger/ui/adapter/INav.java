package com.hayikeji.ddmananger.ui.adapter;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * 描述：
 * 邮箱 email:strive_bug@yeah.net
 * 创建时间 2018/8/8
 *
 * @author ql
 */
public interface INav extends MultiItemEntity{
    String getName();

    int getIconRes();

    int getMenuType();
}
