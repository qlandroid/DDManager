package com.hayikeji.ddmanager.ui.activity.bind;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.ql.bindview.BindView;
import android.view.View;
import android.widget.TextView;

import com.hayikeji.ddmanager.C;
import com.hayikeji.ddmanager.R;
import com.hayikeji.ddmanager.base.BaseActivity;
import com.hayikeji.ddmanager.base.BindLayout;
import com.hayikeji.ddmanager.bean.BaseResult;
import com.hayikeji.ddmanager.bean.DeviceBean;
import com.hayikeji.ddmanager.http.OkHttpHeader;
import com.hayikeji.ddmanager.http.ResultCallback2;
import com.hayikeji.ddmanager.info.UrlApi;
import com.hayikeji.ddmanager.utils.DataUtils;
import com.hayikeji.ddmanager.utils.JsonUtils;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

@BindLayout(layoutRes = R.layout.activity_bind_details, title = "电表绑定")
public class BindDetailsActivity extends BaseActivity {

    @BindView(R.id.activity_bind_details_tv_to_pay)
    View vToPay;
    @BindView(R.id.activity_bind_details_tv_cancel)
    View vCancel;
    @BindView(R.id.activity_bind_details_tv_dev_code)
    TextView tvDevCode;
    @BindView(R.id.activity_bind_details_tv_owner_name)
    TextView tvOwnerName;
    @BindView(R.id.activity_bind_details_tv_room)
    TextView tvRoom;
    private DeviceBean resultObj;
    private final int REQUEST_BIND = 22;


    @Override
    public void initWidget() {
        super.initWidget();
        vToPay.setOnClickListener(this);
        vCancel.setOnClickListener(this);

        String devCode = getBundle().getString(C.DEV_CODE);
        setTextView(devCode, "未知编码", tvDevCode);
        httpGetDevDetails(devCode);
    }

    private void httpGetDevDetails(String devCode) {
        displayLoadingDialog("查询中");
        Map<String, Object> map = new HashMap<>();
        map.put("devCode", devCode);
        OkHttpHeader.post(UrlApi.dev_details_by_code, map, new ResultCallback2() {
            @Override
            protected void onFailed(String error, int code) {
                cancelLoadingDialog();
                new QMUIDialog.MessageDialogBuilder(BindDetailsActivity.this)
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
                new QMUIDialog.MessageDialogBuilder(BindDetailsActivity.this)
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
            case R.id.activity_bind_details_tv_to_pay:
                String s = tvDevCode.getText().toString();
                Bundle b = new Bundle();
                b.putString(C.DEV_CODE, s);
                startActivity(BindTopUpActivity.class, b, REQUEST_BIND);
                break;
            case R.id.activity_bind_details_tv_cancel:
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_BIND:
                if (resultCode == Activity.RESULT_OK) {
                    finish();
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
