package com.hayikeji.ddmanager.ui.activity.bind;

import android.app.Activity;
import android.ql.bindview.BindView;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hayikeji.ddmanager.C;
import com.hayikeji.ddmanager.MyApp;
import com.hayikeji.ddmanager.R;
import com.hayikeji.ddmanager.base.BaseActivity;
import com.hayikeji.ddmanager.base.BindLayout;
import com.hayikeji.ddmanager.bean.BaseResult;
import com.hayikeji.ddmanager.bean.DeviceBean;
import com.hayikeji.ddmanager.http.OkHttpHeader;
import com.hayikeji.ddmanager.http.ResultCallback2;
import com.hayikeji.ddmanager.info.UrlApi;
import com.hayikeji.ddmanager.utils.DataUtils;
import com.hayikeji.ddmanager.utils.preferences.UserDevPreferences;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;

import java.util.HashMap;
import java.util.Map;

@BindLayout(layoutRes = R.layout.activity_bind_top_up, title = "绑定并充值")
public class BindTopUpActivity extends BaseActivity {

    @BindView(R.id.activity_bind_top_up_tv_dev_code)
    TextView tvDevCode;
    @BindView(R.id.activity_bind_top_up_tv_dev_name)
    TextView tvDevName;
    @BindView(R.id.activity_bind_top_up_tv_owner_name)
    TextView tvOwnerName;
    @BindView(R.id.activity_bind_top_up_tv_unit_price)
    TextView tvUnitPrice;//单价
    @BindView(R.id.activity_bind_top_up_tv_room)
    TextView tvRoom;
    @BindView(R.id.activity_bind_top_up_et_pay_price)
    EditText etPayPrice;
    @BindView(R.id.activity_bind_top_up_tv_pay_amount)
    TextView tvPayAmount;
    @BindView(R.id.activity_bind_top_up_rv_pay)
    RecyclerView rvPay;
    @BindView(R.id.activity_bind_top_up_tv_to_pay)
    View tvToPay;

    private String code;
    private DeviceBean resultObj;

    @Override
    public void initWidget() {
        super.initWidget();
        tvToPay.setOnClickListener(this);


        etPayPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                double v;
                try {
                    v = Double.parseDouble(s.toString());
                } catch (Exception e) {
                    return;
                }
                double unitPrice;
                String s1 = tvUnitPrice.getText().toString();
                try {
                    unitPrice = Double.parseDouble(s1);
                } catch (Exception e) {
                    return;
                }

                double v1 = v * unitPrice;

                tvPayAmount.setText(String.format("%.2f", v1));
            }
        });
        code = getBundle().getString("devCode");

        httpGetDevDetails(code);
    }


    private void httpGetDevDetails(String devCode) {
        displayLoadingDialog("查询中");
        Map<String, Object> map = new HashMap<>();
        map.put("devCode", devCode);
        OkHttpHeader.post(UrlApi.dev_details_by_code, map, new ResultCallback2() {
            @Override
            protected void onFailed(String error, int code) {
                cancelLoadingDialog();
                new QMUIDialog.MessageDialogBuilder(BindTopUpActivity.this)
                        .setTitle("提示")
                        .setMessage(error)
                        .addAction("确定", new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                finish();
                                dialog.cancel();
                            }
                        })
                        .show();
            }

            @Override
            protected void onSuccess(BaseResult response, int id) {
                cancelLoadingDialog();
                if (response.isSuccess()) {
                    resultObj = DataUtils.getResultObj(response.getData(), DeviceBean.class);
                    tvDevCode.setText(resultObj.getCode());
                    tvRoom.setText(resultObj.getDevRoom());
                    tvOwnerName.setText(resultObj.getDevOwner());
                    tvDevName.setText(resultObj.getDevName());
                    return;
                }
                new QMUIDialog.MessageDialogBuilder(BindTopUpActivity.this)
                        .setTitle("提示")
                        .setMessage(response.getMessage())
                        .addAction("确定", new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                finish();
                                dialog.cancel();
                            }
                        })
                        .show();
            }
        });

    }

    @Override
    public void forbidClick(View v) {
        super.forbidClick(v);
        switch (v.getId()) {
            case R.id.activity_bind_top_up_tv_to_pay:
                String s = etPayPrice.getText().toString();
                if (TextUtils.isEmpty(s)) {
                    return;
                }
                Map<String, Object> map = new HashMap<>();
                map.put("userId", UserDevPreferences.getUserId(this));
                map.put("devCode", code);
                map.put("buyPrice", Integer.parseInt(etPayPrice.getText().toString()));
                OkHttpHeader.post(UrlApi.bindDev, map, new ResultCallback2() {
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

                        displayTipDialogSuccess("绑定成功");
                        MyApp.refreshUserDetails();
                        MyApp.refreshUserDev();
                        C.sHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                setResult(Activity.RESULT_OK);
                                finish();
                            }
                        }, 1_000);
                    }
                });
                break;
        }
    }
}
