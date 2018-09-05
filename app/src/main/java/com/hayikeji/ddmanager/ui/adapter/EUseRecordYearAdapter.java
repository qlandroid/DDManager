package com.hayikeji.ddmanager.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hayikeji.ddmanager.R;
import com.hayikeji.ddmanager.bean.EUseBean;

/**
 * 描述：
 * 邮箱 email:strive_bug@yeah.net
 * 创建时间 2018/8/20
 *
 * @author ql
 */
public class EUseRecordYearAdapter extends BaseQuickAdapter<EUseBean, QLViewHolder> {
    public EUseRecordYearAdapter() {
        super(R.layout.item_e_use_year);
    }

    @Override
    protected void convert(QLViewHolder helper, EUseBean item) {
        helper.setText(R.id.item_e_use_year_tv_year, item.getYear() + "")
                .setText(R.id.item_e_use_year_tv_amount, item.getTotalE() + "");
    }
}
