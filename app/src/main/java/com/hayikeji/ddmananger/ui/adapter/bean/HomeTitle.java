package com.hayikeji.ddmananger.ui.adapter.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.hayikeji.ddmananger.ui.adapter.HomeContentAdapter;

/**
 * 描述：
 * 邮箱 email:strive_bug@yeah.net
 * 创建时间 2018/8/19
 *
 * @author ql
 */
public class HomeTitle implements MultiItemEntity{


    private CharSequence title;

    public HomeTitle(CharSequence title) {
        this.title = title;
    }

    public CharSequence getTitle() {
        return title;
    }

    public void setTitle(CharSequence title) {
        this.title = title;
    }

    @Override
    public int getItemType() {
        return HomeContentAdapter.TITLE;
    }
}
