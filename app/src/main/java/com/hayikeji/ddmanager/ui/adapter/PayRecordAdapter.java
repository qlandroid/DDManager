package com.hayikeji.ddmanager.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hayikeji.ddmanager.R;
import com.hayikeji.ddmanager.ui.adapter.bean.IPayRecord;

/**
 * 描述：
 * 邮箱 email:strive_bug@yeah.net
 * 创建时间 2018/8/17
 *
 * @author ql
 */
public class PayRecordAdapter extends BaseQuickAdapter<IPayRecord, QLViewHolder> {
    public PayRecordAdapter() {
        super(R.layout.item_pay_record);
    }

    @Override
    protected void convert(QLViewHolder helper, IPayRecord item) {
        helper.setText(R.id.item_pay_record_tv_date, item.getPayDate())
                .setText(R.id.item_pay_record_tv_dev_code, item.getDevCode())
                .setText(R.id.item_pay_record_tv_pay_amount, item.getPayAmount())
                .setText(R.id.item_pay_record_tv_pay_status, item.getPayStatus());
    }
}
