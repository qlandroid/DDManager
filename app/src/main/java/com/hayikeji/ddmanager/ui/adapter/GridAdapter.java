package com.hayikeji.ddmanager.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hayikeji.ddmanager.R;
import com.hayikeji.ddmanager.ui.IGrid;

/**
 * 描述：
 * 邮箱 email:strive_bug@yeah.net
 * 创建时间 2018/8/6
 *
 * @author ql
 */
public class GridAdapter extends BaseQuickAdapter<IGrid,QLViewHolder>{
    public GridAdapter() {
        super(R.layout.item_grid);
    }

    @Override
    protected void convert(QLViewHolder helper, IGrid item) {
        helper.setImageResource(R.id.item_grid_iv,item.getIconRes())
                .setText(R.id.item_grid_tv,item.getLabel());
    }
}
