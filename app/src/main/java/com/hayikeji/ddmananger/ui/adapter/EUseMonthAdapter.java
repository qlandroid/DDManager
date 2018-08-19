package com.hayikeji.ddmananger.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hayikeji.ddmananger.R;
import com.hayikeji.ddmananger.bean.EUseBean;

import java.util.List;

/**
 * 描述：
 * 邮箱 email:strive_bug@yeah.net
 * 创建时间 2018/8/20
 *
 * @author ql
 */
public class EUseMonthAdapter extends BaseQuickAdapter<EUseBean, QLViewHolder> {
    public EUseMonthAdapter() {
        super(R.layout.item_e_use_month);
    }

    @Override
    protected void convert(QLViewHolder helper, EUseBean item) {
        helper.setText(R.id.item_e_use_month_tv_day, item.getDay() + "")
                .setText(R.id.item_e_use_month_tv_amount, item.getTotalE() + "");
    }
}
