package com.hayikeji.ddmananger.ui.activity;

import android.ql.bindview.BindView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hayikeji.ddmananger.R;
import com.hayikeji.ddmananger.base.BaseActivity;
import com.hayikeji.ddmananger.base.BindLayout;
import com.hayikeji.ddmananger.ui.adapter.bean.IMonth;
import com.hayikeji.ddmananger.ui.adapter.MonthSelectAdapter;

import java.util.ArrayList;
import java.util.List;

@BindLayout(layoutRes = R.layout.activity_pay_vip, title = "VIP开通")
public class PayVipActivity extends BaseActivity {


    @BindView(R.id.activity_pay_vip_rv_month_select)
    RecyclerView rvMonthSelect;

    MonthSelectAdapter monthSelectAdapter = new MonthSelectAdapter();


    @Override
    public void initBar() {
        super.initBar();
        mTopBar.addRightTextButton("历史记录",R.id.top_bar_right_btn).setOnClickListener(this);
    }

    @Override
    public void initData() {
        super.initData();
        List<IMonth> l = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            final int finalI = i;
            l.add(new IMonth() {
                @Override
                public int getTag() {

                    return finalI;
                }

                @Override
                public CharSequence getMonthStr() {
                    return "item";
                }
            });
        }
        monthSelectAdapter.setNewData(l);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        rvMonthSelect.setLayoutManager(new GridLayoutManager(this, 4));
        rvMonthSelect.setAdapter(monthSelectAdapter);

        monthSelectAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                monthSelectAdapter.setSelecTag(position);
                monthSelectAdapter.notifyDataSetChanged();
            }
        });
    }
}
