package com.hayikeji.ddmananger.ui.activity;

import android.ql.bindview.BindView;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.TextView;

import com.hayikeji.ddmananger.R;
import com.hayikeji.ddmananger.base.BaseActivity;
import com.hayikeji.ddmananger.base.BindLayout;

@BindLayout(layoutRes = R.layout.activity_pay_e)
public class PayEActivity extends BaseActivity {

    @BindView(R.id.activity_pay_e_tv_dev_code)
    TextView tvDevCode;//设备号码
    @BindView(R.id.activity_pay_e_tv_dev_name)
    TextView tvDevName;//设备名称
    @BindView(R.id.activity_pay_e_tv_owner_name)
    TextView tvOwnerName;//户主名称
    @BindView(R.id.activity_pay_e_tv_room)
    TextView tvRoom;
    @BindView(R.id.activity_pay_e_tv_e_amount)
    TextView tvEAmount;//剩余度数
    @BindView(R.id.activity_pay_e_tv_unit)
    TextView tvUnit;//电表单价
    @BindView(R.id.activity_pay_e_et_pay_price)
    EditText etPayPrice;//充值金额
    @BindView(R.id.activity_pay_e_tv_pay_amount)
    TextView tvPayAmount;//充值度数
    @BindView(R.id.activity_pay_e_rv)
    RecyclerView rv;
    @BindView(R.id.activity_pay_e_tv_to_pay)
    TextView tvPay;//去支付


}
