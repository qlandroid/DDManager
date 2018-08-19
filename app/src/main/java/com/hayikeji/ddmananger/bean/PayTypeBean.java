package com.hayikeji.ddmananger.bean;

import com.hayikeji.ddmananger.ui.adapter.bean.IPayType;

/**
 * 描述：
 * 邮箱 email:strive_bug@yeah.net
 * 创建时间 2018/8/17
 *
 * @author ql
 */
public class PayTypeBean implements IPayType {
    private boolean isSelect;
    private CharSequence typeName;
    private int iconRes;
    private int tag;


    public PayTypeBean(boolean isSelect, CharSequence typeName, int iconRes, int tag) {
        this.isSelect = isSelect;
        this.typeName = typeName;
        this.iconRes = iconRes;
        this.tag = tag;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    @Override
    public int getTag() {
        return tag;
    }

    @Override
    public boolean isSelect() {
        return isSelect;
    }

    @Override
    public CharSequence getTypeName() {
        return typeName;
    }

    @Override
    public int getTypeIconRes() {
        return iconRes;
    }
}
