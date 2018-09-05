package com.hayikeji.ddmanager.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hayikeji.ddmanager.R;

/**
 * 描述：
 * 邮箱 email:strive_bug@yeah.net
 * 创建时间 2018/8/19
 *
 * @author ql
 */
public class PayMsgAdapter  extends BaseQuickAdapter<IPayMsgDoc,QLViewHolder>{
    public PayMsgAdapter() {
        super(R.layout.item_pay_msg_doc);
    }

    @Override
    protected void convert(QLViewHolder helper, IPayMsgDoc item) {
        helper.setText(R.id.item_pay_msg_doc_tv_amount,item.getPayEAmount())
                .setText(R.id.item_pay_msg_doc_tv_date,item.getPayDate())
                .setText(R.id.item_pay_msg_doc_tv_dev_code,item.getDevCode())
                .setText(R.id.item_pay_msg_doc_tv_doc,item.getDoc())
                .setText(R.id.item_pay_msg_doc_tv_price,item.getPayPrice());
    }
}
