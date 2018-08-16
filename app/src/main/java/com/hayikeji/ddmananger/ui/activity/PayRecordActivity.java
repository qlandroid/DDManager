package com.hayikeji.ddmananger.ui.activity;

import android.ql.bindview.BindView;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.hayikeji.ddmananger.R;
import com.hayikeji.ddmananger.base.BaseActivity;
import com.hayikeji.ddmananger.base.BindLayout;

@BindLayout(layoutRes = R.layout.activity_pay_record, title = "充值记录")
public class PayRecordActivity extends BaseActivity {

    @BindView(R.id.activity_pay_record_rv)
    RecyclerView rv;
    @BindView(R.id.activity_pay_record_tv_all)
    TextView tvAll;//选择全部
    @BindView(R.id.activity_pay_record_tv_dev_code)
    TextView tvDevCode;
    
}
