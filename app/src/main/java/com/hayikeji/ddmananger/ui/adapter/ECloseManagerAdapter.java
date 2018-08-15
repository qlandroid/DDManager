package com.hayikeji.ddmananger.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hayikeji.ddmananger.R;

/**
 * 描述：
 * 邮箱 email:strive_bug@yeah.net
 * 创建时间 2018/8/13
 *
 * @author ql
 */
public class ECloseManagerAdapter extends BaseQuickAdapter<IECloseManager, QLViewHolder> {
    public ECloseManagerAdapter() {
        super(R.layout.item_e_close_manager);
    }

    @Override
    protected void convert(QLViewHolder helper, IECloseManager item) {
        helper.setText(R.id.item_e_close_manager_tv_code, item.getDevNo())
                .setText(R.id.item_e_close_manager_tv_dev_name, item.getDevName())
                .setText(R.id.item_e_close_manager_tv_owner, item.getDevOwner())
                .setText(R.id.item_e_close_manager_tv_room, item.getDevRoom())
                .setText(R.id.item_e_close_manager_tv_status, item.isRun() ? "运行中" : "关闭中")
                .setText(R.id.item_e_close_manager_tv_power, item.getPower());
        if (item.isHavePowerAssign()) {
            helper.setBackgroundRes(R.id.item_e_close_manager_tv_assign_power, R.drawable.s_btn_del);
            helper.addOnClickListener(R.id.item_e_close_manager_tv_assign_power);
        } else {
            helper.setBackgroundRes(R.id.item_e_close_manager_tv_assign_power, R.drawable.bg_radius_gray);
        }

        if (item.isCanSwitch()) {
            helper.setBackgroundRes(R.id.item_e_close_manager_tv_open, R.drawable.s_btn_radius_blue)
                    .addOnClickListener(R.id.item_e_close_manager_tv_open);
        } else {
            helper.setBackgroundRes(R.id.item_e_close_manager_tv_open, R.drawable.bg_radius_gray);
        }

    }
}
