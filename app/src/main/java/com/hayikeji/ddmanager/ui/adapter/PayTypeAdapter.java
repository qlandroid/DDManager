package com.hayikeji.ddmanager.ui.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hayikeji.ddmanager.R;
import com.hayikeji.ddmanager.ui.adapter.bean.IPayType;

/**
 * 描述：
 * 邮箱 email:strive_bug@yeah.net
 * 创建时间 2018/8/17
 *
 * @author ql
 */
public class PayTypeAdapter extends BaseQuickAdapter<IPayType, QLViewHolder> {
    public PayTypeAdapter() {
        super(R.layout.item_pay_type);
    }

    private int selectPosition = 0;

    @Override
    protected void convert(QLViewHolder helper, IPayType item) {
        helper.setText(R.id.item_pay_type_tv_name, item.getTypeName());
        helper.setImageResource(R.id.item_pay_type_iv, item.getTypeIconRes());
        helper.getView(R.id.item_pay_type_iv_select).setVisibility(helper.getAdapterPosition() == selectPosition ? View.VISIBLE : View.GONE);
    }

    public int getSelectPosition() {
        return selectPosition;
    }

    public void setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
        this.notifyDataSetChanged();
    }
}
