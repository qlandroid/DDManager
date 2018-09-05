package com.hayikeji.ddmanager.ui.shopping.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hayikeji.ddmanager.R;
import com.hayikeji.ddmanager.ui.adapter.QLViewHolder;

import java.util.List;

/**
 * 描述：
 * 邮箱 email:strive_bug@yeah.net
 * 创建时间 2018/8/31
 *
 * @author ql
 */
public class GoodsAdapter  extends BaseQuickAdapter<IEGoods,QLViewHolder>{
    public GoodsAdapter() {
        super(R.layout.item_shopping_grid_goods);
    }

    @Override
    protected void convert(QLViewHolder helper, IEGoods item) {
        helper.setText(R.id.item_shopping_grid_goods_tv_price,item.getPrice())
                .setText(R.id.item_shopping_grid_goods_tv_title,item.getTitle());

        String img = item.getImg();
        if (TextUtils.isEmpty(img)) {
            helper.setImageResource(R.id.item_shopping_grid_goods_iv,R.drawable.d_test_1);
            return;
        }

        Glide.with(helper.itemView.getContext())
                .load(img)
                .asBitmap()
                .into((ImageView) helper.getView(R.id.item_shopping_grid_goods_iv));

    }
}
