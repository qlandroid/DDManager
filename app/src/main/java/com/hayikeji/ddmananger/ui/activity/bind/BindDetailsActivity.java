package com.hayikeji.ddmananger.ui.activity.bind;

import android.os.Bundle;
import android.ql.bindview.BindView;
import android.view.View;
import android.widget.TextView;

import com.hayikeji.ddmananger.C;
import com.hayikeji.ddmananger.R;
import com.hayikeji.ddmananger.base.BaseActivity;
import com.hayikeji.ddmananger.base.BindLayout;
import com.hayikeji.ddmananger.bean.BaseResult;
import com.hayikeji.ddmananger.bean.DeviceBean;
import com.hayikeji.ddmananger.http.OkHttpHeader;
import com.hayikeji.ddmananger.http.ResultCallback2;
import com.hayikeji.ddmananger.info.UrlApi;
import com.hayikeji.ddmananger.utils.DataUtils;
import com.hayikeji.ddmananger.utils.JsonUtils;
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
                startActivity(BindTopUpActivity.class, b);
                break;
            case R.id.activity_bind_details_tv_cancel:
                finish();
                break;
        }
    }
}
