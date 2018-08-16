package com.hayikeji.ddmananger.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hayikeji.ddmananger.R;

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

    private int selectTag = 0;

    @Override
    protected void convert(QLViewHolder helper, IMonth item) {
        boolean b = selectTag == helper.getAdapterPosition();
        helper.getView(R.id.item_text_tv).setSelected(b);
        helper.setText(R.id.item_text_tv, item.getMonthStr());
    }

    public int getSelectTag() {
        return selectTag;
    }

    public void setSelecTag(int selecTag) {
        this.selectTag = selecTag;
    }
}
