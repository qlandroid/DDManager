package com.hayikeji.ddmanager.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hayikeji.ddmanager.R;

/**
 * 描述：
 * 邮箱 email:strive_bug@yeah.net
 * 创建时间 2018/9/10
 *
 * @author ql
 */
public class PayVipAdapter extends BaseQuickAdapter<IVip,QLViewHolder> {
    public PayVipAdapter() {
        super(R.layout.item_vip_record);
    }

    @Override
    protected void convert(QLViewHolder helper, IVip item) {
        helper.setText(R.id.item_vip_record_doc_tv_date,item.getStartDate())
                .setText(R.id.item_vip_record_doc_tv_dev_code,item.getDevCord())
                .setText(R.id.item_vip_record_doc_tv_doc,item.getDoc())
                .setText(R.id.item_vip_record_doc_tv_end_date,item.getPayEndDate())
                .setText(R.id.item_vip_record_doc_tv_pay_month,item.getBuyMonths())
                .setText(R.id.item_vip_record_doc_tv_price,item.getPrice());
    }
}
