package com.hayikeji.ddmananger.ui.activity.bind;

import android.ql.bindview.BindView;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hayikeji.ddmananger.R;
import com.hayikeji.ddmananger.base.BaseActivity;
import com.hayikeji.ddmananger.base.BindLayout;
import com.hayikeji.ddmananger.bean.BaseResult;
import com.hayikeji.ddmananger.bean.DeviceBean;
import com.hayikeji.ddmananger.http.OkHttpHeader;
import com.hayikeji.ddmananger.http.ResultCallback2;
import com.hayikeji.ddmananger.info.UrlApi;
import com.hayikeji.ddmananger.utils.DataUtils;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;

import java.util.HashMap;
import java.util.Map;

@BindLayout(layoutRes = R.layout.activity_bind_top_up,title = "绑定并充值")
public class BindTopUpActivity extends BaseActivity {

    @BindView(R.id.activity_bind_top_up_tv_dev_code)
    TextView tvDevCode;
    @BindView(R.id.activity_bind_top_up_tv_address)
    TextView tvAddress;
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
                break;
        }
    }
}
