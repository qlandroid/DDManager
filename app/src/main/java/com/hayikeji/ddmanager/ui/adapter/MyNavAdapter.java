package com.hayikeji.ddmanager.ui.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.hayikeji.ddmanager.R;
import com.hayikeji.ddmanager.ui.adapter.bean.INav;

import java.util.List;

/**
 * 描述：
 * 邮箱 email:strive_bug@yeah.net
 * 创建时间 2018/8/8
 *
 * @author ql
 */
public class MyNavAdapter extends BaseMultiItemQuickAdapter<INav, QLViewHolder> {
    public static final int SINGLE = 0;//不带下划线
    public static final int S_DIV = 1;//带下划线
    public static final int S_NULL = 3;//空白框

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public MyNavAdapter(List<INav> data) {
        super(data);
        addItemType(SINGLE, R.layout.item_nav_single);
        addItemType(S_DIV, R.layout.item_nav_div);
        addItemType(S_NULL, R.layout.item_nav_null);
    }

    @Override
    protected void convert(QLViewHolder helper, INav item) {
        if (item.getItemType() == S_NULL) {
            return;
        }
        helper.setText(R.id.item_my_tv_name, item.getName())
                .setImageResource(R.id.item_my_iv, item.getIconRes());
    }
}
