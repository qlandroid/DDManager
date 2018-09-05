package com.hayikeji.ddmanager.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hayikeji.ddmanager.R;
import com.hayikeji.ddmanager.ui.adapter.bean.IDevDetails;

/**
 * 描述：
 * 邮箱 email:strive_bug@yeah.net
 * 创建时间 2018/8/7
 *
 * @author ql
 */
public class DevSelectAdapter extends BaseQuickAdapter<IDevDetails, QLViewHolder> {


    public DevSelectAdapter() {
        super(R.layout.item_dev_select);
    }

    @Override
    protected void convert(QLViewHolder helper, IDevDetails item) {
        helper.setText(R.id.item_dev_select_tv_no, item.getDevNo())
                .setText(R.id.item_dev_select_tv_name, item.getDevName())
                .setText(R.id.item_dev_select_tv_door, item.getDevRoom())
                .setText(R.id.item_dev_select_tv_owner, item.getDevOwner());
    }
}
