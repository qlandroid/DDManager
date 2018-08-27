package com.hayikeji.ddmananger.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hayikeji.ddmananger.R;
import com.hayikeji.ddmananger.ui.adapter.bean.IMonth;

/**
 * 描述：
 * 邮箱 email:strive_bug@yeah.net
 * 创建时间 2018/8/17
 *
 * @author ql
 */
public class MonthSelectAdapter extends BaseQuickAdapter<IMonth, QLViewHolder> {
    public MonthSelectAdapter() {
        super(R.layout.item_text);
    }

    private int selectPostion = 0;

    @Override
    protected void convert(QLViewHolder helper, IMonth item) {
        boolean b = selectPostion == helper.getAdapterPosition();
        helper.getView(R.id.item_text_tv).setSelected(b);
        helper.setText(R.id.item_text_tv, item.getMonthStr());
    }

    public int getSelectTag() {
        return selectPostion;
    }

    public void setSelectPostion(int selectPostion) {
        this.selectPostion = selectPostion;
    }
}
