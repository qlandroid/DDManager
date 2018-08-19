package com.hayikeji.ddmananger.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hayikeji.ddmananger.R;
import com.hayikeji.ddmananger.ui.adapter.bean.IPower;

/**
 * 描述：
 * 邮箱 email:strive_bug@yeah.net
 * 创建时间 2018/8/13
 *
 * @author ql
 */
public class PowerManagerAdapter extends BaseQuickAdapter<IPower, QLViewHolder> {
    public PowerManagerAdapter() {
        super(R.layout.item_power);
    }

    @Override
    protected void convert(QLViewHolder helper, IPower item) {
        helper.setText(R.id.item_power_tv_name, item.getName())
                .setText(R.id.item_power_tv_phone, item.getPhone())
                .setText(R.id.item_power_tv_power, item.getPower());
        if (item.isHavePower()) {
            helper.setBackgroundRes(R.id.item_power_tv_setting, R.drawable.s_btn_radius_blue);
            helper.addOnClickListener(R.id.item_power_tv_setting);
        } else {
            helper.setBackgroundRes(R.id.item_power_tv_setting, R.drawable.bg_radius_gray);
        }
    }
}
