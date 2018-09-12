package com.hayikeji.ddmanager.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hayikeji.ddmanager.R;
import com.hayikeji.ddmanager.ui.adapter.bean.IMonth;

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

    private int selectPosition = 0;

    @Override
    protected void convert(QLViewHolder helper, IMonth item) {
        boolean b = selectPosition == helper.getAdapterPosition();
        helper.getView(R.id.item_text_tv).setSelected(b);
        helper.setText(R.id.item_text_tv, item.getMonthStr());
    }

    public int getSelectPosition() {
        return selectPosition;
    }

    public void setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
    }

    public int getTag() {
        IMonth iMonth = this.getData().get(selectPosition);
        return iMonth.getTag();
    }
}
