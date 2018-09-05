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

    @Override
    protected void convert(QLViewHolder helper, IPayType item) {
        helper.setText(R.id.item_pay_type_tv_name, item.getTypeName());
        helper.setImageResource(R.id.item_pay_type_iv, item.getTypeIconRes());
        helper.getView(R.id.item_pay_type_iv_select).setVisibility(item.isSelect() ? View.VISIBLE : View.GONE);
    }
}
