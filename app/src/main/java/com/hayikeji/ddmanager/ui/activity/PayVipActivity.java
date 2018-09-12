package com.hayikeji.ddmanager.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.ql.bindview.BindView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
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
import com.hayikeji.ddmanager.ui.adapter.PayTypeAdapter;
import com.hayikeji.ddmanager.ui.adapter.bean.IMonth;
import com.hayikeji.ddmanager.ui.adapter.MonthSelectAdapter;
import com.hayikeji.ddmanager.ui.adapter.bean.IPayType;
import com.hayikeji.ddmanager.utils.DataUtils;
import com.hayikeji.ddmanager.utils.preferences.UserDevPreferences;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@BindLayout(layoutRes = R.layout.activity_pay_vip, title = "VIP开通")
public class PayVipActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener {

    private static final int REQUEST_DEV_SELECT = 123;

    @BindView(R.id.activity_pay_vip_rv_month_select)
    RecyclerView rvMonthSelect;
    @BindView(R.id.activity_pay_vip_tv_dev_select)
    View vDevSelect;
    @BindView(R.id.activity_pay_vip_tv_to_pay)
    View vToPay;
    @BindView(R.id.activity_pay_vip_tv_a)
    View vA;//协议

    @BindView(R.id.activity_pay_vip_tv_dev_code)
    TextView tvDevCode;
    @BindView(R.id.activity_pay_vip_tv_owner_name)
    TextView tvOwnerName;
    @BindView(R.id.activity_pay_vip_tv_room)
    TextView tvRoom;
    @BindView(R.id.activity_pay_vip_tv_dev_name)
    TextView tvDevName;
    @BindView(R.id.activity_pay_e_tv_pay_price)
    TextView tvPayPrice;
    @BindView(R.id.activity_pay_vip_agreement_group)
    View vAgreementGroup;
    @BindView(R.id.activity_pay_vip_rb)
    ImageView ivSelected;
    @BindView(R.id.activity_pay_vip_tv_price_unit)
    TextView tvPriceUnit;
    @BindView(R.id.activity_pay_vip_rv)
    RecyclerView rvPay;


    PriceBean priceBean;


    MonthSelectAdapter monthSelectAdapter = new MonthSelectAdapter();
    private PayTypeAdapter payTypeAdapter = new PayTypeAdapter();

    @Override
    public void initBar() {
        super.initBar();
        mTopBar.addRightTextButton("历史记录", R.id.top_bar_right_btn).setOnClickListener(this);
    }

    @Override
    public void initData() {
        super.initData();
        priceBean = new PriceBean();
        priceBean.setEleUnit(100);
        priceBean.setVipUnit(500);
        List<IMonth> l = new ArrayList<>();
        l.add(new Month("1个月", 1));
        l.add(new Month("2个月", 2));
        l.add(new Month("3个月", 3));
        l.add(new Month("半年", 6));
        l.add(new Month("一年", 12));
        l.add(new Month("两年", 24));
        monthSelectAdapter.setNewData(l);
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
                    setTextView(resultObj.getDevOwner(), tvOwnerName)
                            .setTextView(resultObj.getCode(), tvDevCode)
                            .setTextView(resultObj.getDevRoom(), tvRoom)
                            .setTextView(resultObj.getNickname(), tvDevName);
                }
            }
        });
    }

    @Override
    public void initWidget() {
        super.initWidget();
        vAgreementGroup.setOnClickListener(this);
        vA.setOnClickListener(this);
        vToPay.setOnClickListener(this);
        vDevSelect.setOnClickListener(this);
        rvMonthSelect.setLayoutManager(new GridLayoutManager(this, 3));
        rvMonthSelect.setAdapter(monthSelectAdapter);
        IMonth iMonth = monthSelectAdapter.getData().get(0);

        double priceUnit = priceBean.getVipUnit() * 1.0 / 100;
        tvPayPrice.setText(String.format("%.2f", priceUnit));
        double v = iMonth.getTag() * priceBean.getVipUnit() * 1.0 / 100;
        tvPayPrice.setText(String.format("%.2f", v));

        rvPay.setLayoutManager(new LinearLayoutManager(this));
        rvPay.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        List<IPayType> list = new ArrayList<>();
        list.add(new PayTypeBean(true, "支付宝支付", R.drawable.pay_zhifubao_logo, 0));
        list.add(new PayTypeBean(false, "微信支付", R.drawable.pay_weixin_logo, 1));
        payTypeAdapter.setNewData(list);
        rvPay.setAdapter(payTypeAdapter);

        payTypeAdapter.setOnItemClickListener(this);
        monthSelectAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Month o = (Month) adapter.getData().get(position);
                double i = o.getTag() * priceBean.getVipUnit() * 1.0 / 100;
                tvPayPrice.setText(String.format("%.2f", i));
                monthSelectAdapter.setSelectPosition(position);
                monthSelectAdapter.notifyDataSetChanged();
            }
        });
        httpLoadDevDetaila();
        httpPrice();
    }

    @Override
    public void forbidClick(View v) {
        super.forbidClick(v);
        switch (v.getId()) {
            case R.id.top_bar_right_btn:
                startActivity(PayVipRecordActivity.class);
                break;
            case R.id.activity_pay_vip_agreement_group:
                boolean selected = ivSelected.isSelected();
                ivSelected.setSelected(!selected);
                break;
            case R.id.activity_pay_vip_tv_a:
                Bundle w = new Bundle();
                CommWebActivity.putUrl(UrlApi.agreement, "协议", w);
                startActivity(CommWebActivity.class, w);
                break;
            case R.id.activity_pay_vip_tv_dev_select:
                startActivity(DevListSelectActivity.class, REQUEST_DEV_SELECT);
                break;
            case R.id.activity_pay_vip_tv_to_pay:
                if (!ivSelected.isSelected()) {
                    toast("未同意协议");
                    return;
                }
                int selectTag = monthSelectAdapter.getSelectPosition();
                Month m = (Month) monthSelectAdapter.getData().get(selectTag);
                displayLoadingDialog("提交中");
                Map<String, Object> map = new HashMap<>();
                map.put("userId", UserDevPreferences.getUserId(this));
                map.put("devId", UserDevPreferences.getSelectDev(this));
                map.put("buyCount", m.getTag());

                OkHttpHeader.post(UrlApi.devVIP, map, new ResultCallback2() {
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
                        displayTipDialogSuccess("成为VIP成功");
                    }
                });
                break;
        }
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
                IMonth iMonth = monthSelectAdapter.getData().get(monthSelectAdapter.getSelectPosition());
                double priceUnit = priceBean.getVipUnit() * 1.0 / 100;
                double v = iMonth.getTag() * priceUnit;
                tvPayPrice.setText(String.format("%.2f", v));
                tvPriceUnit.setText(String.format("%.2f", priceUnit));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_DEV_SELECT:
                if (resultCode != Activity.RESULT_OK) {
                    return;
                }
                int devId = DevListSelectActivity.getDevId(data);
                UserDevPreferences.saveSelectDev(this, devId);
                httpLoadDevDetaila();
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        PayTypeBean o = (PayTypeBean) adapter.getData().get(position);
        int selectTag = monthSelectAdapter.getTag();
        payTypeAdapter.setSelectPosition(position);
        switch (o.getTag()) {
            case 0://支付宝支付
                toast("支付宝支付 -- " + selectTag);
                break;
            case 1://微信支付
                toast("微信支付 -- " + selectTag);
                break;
        }
    }

    private class Month implements IMonth {
        private String monthStr;
        private int tag;

        public Month(String monthStr, int tag) {
            this.monthStr = monthStr;
            this.tag = tag;
        }

        @Override
        public int getTag() {
            return tag;
        }

        @Override
        public CharSequence getMonthStr() {
            return monthStr;
        }
    }
}
