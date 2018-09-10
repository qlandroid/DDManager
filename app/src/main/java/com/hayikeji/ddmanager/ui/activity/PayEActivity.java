package com.hayikeji.ddmanager.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.ql.bindview.BindView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hayikeji.ddmanager.C;
import com.hayikeji.ddmanager.R;
import com.hayikeji.ddmanager.base.BaseActivity;
import com.hayikeji.ddmanager.base.BindLayout;
import com.hayikeji.ddmanager.bean.BaseResult;
import com.hayikeji.ddmanager.bean.DeviceBean;
import com.hayikeji.ddmanager.bean.PayTypeBean;
import com.hayikeji.ddmanager.bean.PriceBean;
import com.hayikeji.ddmanager.http.OkHttpHeader;
import com.hayikeji.ddmanager.http.OkHttpHelper;
import com.hayikeji.ddmanager.http.ResultCallback2;
import com.hayikeji.ddmanager.info.UrlApi;
import com.hayikeji.ddmanager.ui.adapter.bean.IMonth;
import com.hayikeji.ddmanager.ui.adapter.bean.IPayType;
import com.hayikeji.ddmanager.ui.adapter.PayTypeAdapter;
import com.hayikeji.ddmanager.utils.DataUtils;
import com.hayikeji.ddmanager.utils.eventbus.ChangeDetailsEventBus;
import com.hayikeji.ddmanager.utils.preferences.UserDevPreferences;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CancellationException;

@BindLayout(layoutRes = R.layout.activity_pay_e, title = "电量充值")
public class PayEActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener {

    private static final int REQUEST_DEV = 1233;
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
    TextView  tvUnit;//电表单价
    @BindView(R.id.activity_pay_e_et_pay_price)
    EditText etPayPrice;//充值金额
    @BindView(R.id.activity_pay_e_tv_pay_amount)
    TextView tvPayAmount;//充值度数
    @BindView(R.id.activity_pay_e_rv)
    RecyclerView rv;
    @BindView(R.id.activity_pay_e_tv_to_pay)
    TextView tvPay;//去支付
    @BindView(R.id.activity_pay_e_tv_select_dev)
    View vSelectDev;
    @BindView(R.id.activity_pay_e_tv_agreement)
    View vAgreement;
    @BindView(R.id.activity_pay_e_agreement_group)
    View vAgreementGroup;
    @BindView(R.id.activity_pay_e_iv)
    ImageView ivAgreement;

    private PayTypeAdapter payTypeAdapter = new PayTypeAdapter();
    private PriceBean priceBean = new PriceBean();

    @Override
    public void initBar() {
        super.initBar();
        mTopBar.addRightTextButton("历史记录", R.id.top_bar_right_btn).setOnClickListener(this);
    }

    @Override
    public void initData() {
        super.initData();
        priceBean.setEleUnit(10);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        tvPay.setOnClickListener(this);
        vSelectDev.setOnClickListener(this);
        vAgreement.setOnClickListener(this);
        vAgreementGroup.setOnClickListener(this);

        etPayPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    double v = Double.parseDouble(s.toString());
                    double priceUnit = priceBean.getEleUnit() * 1.0 / 100;
                    double v1 = v * priceUnit;
                    tvPayAmount.setText(String.format("%.2f", v1));
                } catch (Exception e) {
                    tvPayAmount.setText("0.00");
                }
            }
        });

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        List<IPayType> list = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            list.add(new PayTypeBean(false, "item--" + i, R.drawable.ic_launcher_background, i));
        }
        payTypeAdapter.setNewData(list);
        rv.setAdapter(payTypeAdapter);

        payTypeAdapter.setOnItemClickListener(this);
        httpLoadDevDetaila();
        httpPrice();
    }

    private void httpPrice() {
        OkHttpHelper.post(UrlApi.priceUnit, new HashMap<>(), new ResultCallback2() {
            @Override
            protected void onFailed(String error, int code) {

            }

            @Override
            protected void onSuccess(BaseResult response, int id) {
                if (!response.isSuccess()) {
                    return;
                }
                priceBean = response.getDataO(PriceBean.class);
                double priceUnit = priceBean.getEleUnit() * 1.0 / 100;
                tvUnit.setText(String.format("%.2f", priceUnit));
                String s = etPayPrice.getText().toString();

                int mount;
                try {
                    mount = Integer.parseInt(s);
                } catch (Exception e) {
                    mount = 0;
                }

                tvPayAmount.setText(String.format("%.2f", mount * priceUnit));
            }
        });
    }

    private void httpLoadDevDetaila() {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", UserDevPreferences.getUserId(this));
        map.put("devId", UserDevPreferences.getSelectDev(this));

        OkHttpHeader.post(UrlApi.dev, map, new ResultCallback2() {
            @Override
            protected void onFailed(String error, int code) {

            }

            @Override
            protected void onSuccess(BaseResult response, int id) {
                if (response.isSuccess()) {
                    DeviceBean resultObj = DataUtils.getResultObj(response.getData(), DeviceBean.class);
                    int l = resultObj.getBuyElectric() - resultObj.getElectric();
                    setTextView(resultObj.getDevOwner(), tvOwnerName)
                            .setTextView(resultObj.getCode(), tvDevCode)
                            .setTextView(resultObj.getDevRoom(), tvRoom)
                            .setTextView(resultObj.getNickname(), tvDevName)
                            .setTextView(l + "", tvEAmount);
                }
            }
        });
    }

    @Override
    public void forbidClick(View v) {
        super.forbidClick(v);
        switch (v.getId()) {
            case R.id.activity_pay_e_agreement_group:
                boolean selected = ivAgreement.isSelected();
                ivAgreement.setSelected(!selected);
                break;
            case R.id.activity_pay_e_tv_agreement:
                Bundle w = new Bundle();
                CommWebActivity.putUrl(UrlApi.agreement, "协议", w);
                startActivity(CommWebActivity.class, w);
                break;
            case R.id.top_bar_right_btn://历史记录
                startActivity(PayRecordActivity.class);
                break;
            case R.id.activity_pay_e_tv_select_dev://设备选择
                startActivity(DevListSelectActivity.class, REQUEST_DEV);
                break;
            case R.id.activity_pay_e_tv_to_pay://去支付
                if (!ivAgreement.isSelected()) {
                    toast("未同意协议");
                    return;
                }
                String s = etPayPrice.getText().toString();
                if (TextUtils.isEmpty(s)) {
                    return;
                }
                displayLoadingDialog("支付中");
                Map<String, Object> map = new HashMap<>();
                map.put("userId", UserDevPreferences.getUserId(getApplicationContext()));
                map.put("devId", UserDevPreferences.getSelectDev(getApplicationContext()));
                map.put("buyPrice", Integer.parseInt(s));
                OkHttpHeader.post(UrlApi.buyEle, map, new ResultCallback2() {
                    @Override
                    protected void onFailed(String error, int code) {
                        cancelLoadingDialog();
                        displayMessageDialog(error);
                    }

                    @Override
                    protected void onSuccess(BaseResult response, int id) {
                        cancelLoadingDialog();
                        if (!response.isSuccess()) {

                            displayMessageDialog(response.getMessage());
                            return;
                        }
                        displayTipDialogSuccess("充值成功");
                        C.sHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                ChangeDetailsEventBus.getInstance().postDetails(2);
                                finish();
                            }
                        }, 1_000);
                    }
                });
                break;
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        toast(position + "");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_DEV:
                if (resultCode == Activity.RESULT_OK) {
                    httpLoadDevDetaila();
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
