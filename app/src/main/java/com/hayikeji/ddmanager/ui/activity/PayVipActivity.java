package com.hayikeji.ddmanager.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.ql.bindview.BindView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hayikeji.ddmanager.R;
import com.hayikeji.ddmanager.base.BaseActivity;
import com.hayikeji.ddmanager.base.BindLayout;
import com.hayikeji.ddmanager.bean.BaseResult;
import com.hayikeji.ddmanager.bean.DeviceBean;
import com.hayikeji.ddmanager.http.OkHttpHeader;
import com.hayikeji.ddmanager.http.ResultCallback2;
import com.hayikeji.ddmanager.info.UrlApi;
import com.hayikeji.ddmanager.ui.adapter.bean.IMonth;
import com.hayikeji.ddmanager.ui.adapter.MonthSelectAdapter;
import com.hayikeji.ddmanager.utils.DataUtils;
import com.hayikeji.ddmanager.utils.preferences.UserDevPreferences;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@BindLayout(layoutRes = R.layout.activity_pay_vip, title = "VIP开通")
public class PayVipActivity extends BaseActivity {

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


    MonthSelectAdapter monthSelectAdapter = new MonthSelectAdapter();


    @Override
    public void initBar() {
        super.initBar();
        // mTopBar.addRightTextButton("历史记录", R.id.top_bar_right_btn).setOnClickListener(this);
    }

    @Override
    public void initData() {
        super.initData();
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

    private void httpLoadPrice() {

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
        tvPayPrice.setText((iMonth.getTag() * 2) + "");
        monthSelectAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Month o = (Month) adapter.getData().get(position);
                int i = o.getTag() * 2;
                tvPayPrice.setText(i + "");
                monthSelectAdapter.setSelectPostion(position);
                monthSelectAdapter.notifyDataSetChanged();
            }
        });
        httpLoadDevDetaila();

    }

    @Override
    public void forbidClick(View v) {
        super.forbidClick(v);
        switch (v.getId()) {
            case R.id.activity_pay_vip_agreement_group:
                boolean selected = ivSelected.isSelected();
                ivSelected.setSelected(!selected);
                break;
            case R.id.activity_pay_vip_tv_a:
                Bundle w = new Bundle();
                CommWebActivity.putUrl(UrlApi.agreement, "协议", w);
                startActivity(CommWebActivity.class,w);
                break;
            case R.id.activity_pay_vip_tv_dev_select:
                startActivity(DevListSelectActivity.class, REQUEST_DEV_SELECT);
                break;
            case R.id.activity_pay_vip_tv_to_pay:
                if (!ivSelected.isSelected()) {
                    toast("未同意协议");
                    return;
                }
                int selectTag = monthSelectAdapter.getSelectTag();
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
