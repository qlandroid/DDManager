package com.hayikeji.ddmananger.ui.activity;

import android.graphics.Color;
import android.ql.bindview.BindView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hayikeji.ddmananger.R;
import com.hayikeji.ddmananger.base.BaseActivity;
import com.hayikeji.ddmananger.base.BindLayout;
import com.hayikeji.ddmananger.ui.adapter.PayRecordAdapter;
import com.hayikeji.ddmananger.utils.div.DividerItemDecoration;

@BindLayout(layoutRes = R.layout.activity_pay_record, title = "充值记录")
public class PayRecordActivity extends BaseActivity {

    @BindView(R.id.activity_pay_record_rv)
    RecyclerView rv;
    @BindView(R.id.activity_pay_record_tv_all)
    TextView tvAll;//选择全部
    @BindView(R.id.activity_pay_record_tv_dev_code)
    TextView tvDevCode;

    PayRecordAdapter payRecordAdapter = new PayRecordAdapter();

    @Override
    public void initBar() {
        super.initBar();
        mTopBar.addRightTextButton("设备号", R.id.top_bar_right_btn).setOnClickListener(this);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL, 10, Color.GRAY));

        rv.setAdapter(payRecordAdapter);

    }

    @Override
    public void forbidClick(View v) {
        super.forbidClick(v);
        switch (v.getId()) {
            case R.id.top_bar_right_btn:
                startActivity(DevListSelectActivity.class);
                break;
        }
    }
}
