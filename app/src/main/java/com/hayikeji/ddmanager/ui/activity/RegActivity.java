package com.hayikeji.ddmanager.ui.activity;

import android.ql.bindview.BindView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hayikeji.ddmanager.C;
import com.hayikeji.ddmanager.R;
import com.hayikeji.ddmanager.base.BaseActivity;
import com.hayikeji.ddmanager.base.BindLayout;
import com.hayikeji.ddmanager.bean.BaseResult;
import com.hayikeji.ddmanager.http.OkHttpHeader;
import com.hayikeji.ddmanager.http.ResultCallback2;
import com.hayikeji.ddmanager.info.UrlApi;
import com.hayikeji.ddmanager.utils.CheckUtils;

import java.util.HashMap;
import java.util.Map;

@BindLayout(layoutRes = R.layout.activity_reg, title = "注册")
public class RegActivity extends BaseActivity {

    @BindView(R.id.activity_reg_tv_reg)
    TextView tvReg;
    @BindView(R.id.activity_reg_et_account)
    EditText etAccount;
    @BindView(R.id.activity_reg_et_pw)
    EditText etPw;
    @BindView(R.id.activity_reg_et_phone)
    EditText etPhone;
    @BindView(R.id.activity_reg_et_msg_code)
    EditText etMsgCode;
    @BindView(R.id.activity_reg_tv_getMsgCode)
    TextView tvGetMsgCode;


    @Override
    public void initWidget() {
        super.initWidget();
        tvReg.setOnClickListener(this);
        tvGetMsgCode.setOnClickListener(this);


    }

    @Override
    public void forbidClick(View v) {
        super.forbidClick(v);
        switch (v.getId()) {
            case R.id.activity_reg_tv_getMsgCode:
                String s = etPhone.getText().toString();
                if (!CheckUtils.isMobileNO(s)) {
                    toFocusable(etPhone);
                    toast("请输入正确手机号码");
                    return;
                }
                break;
            case R.id.activity_reg_tv_reg:
                String ac = etAccount.getText().toString();
                String phone = etPhone.getText().toString();
                String pw = etPw.getText().toString();
                String msgCode = etMsgCode.getText().toString();
                if (TextUtils.isEmpty(ac)) {
                    toFocusable(etAccount);
                    etAccount.setSelection(0,etAccount.length()-1);
                    toast("请输入账号");
                    return;
                }
                if (!CheckUtils.isMobileNO(phone)) {
                    toFocusable(etPhone);
                    etPhone.setSelection(0,etPhone.length()-1);
                    toast("请输入正确的手机号码");
                    return;
                }
                if (TextUtils.isEmpty(pw)) {
                    toFocusable(etPw);
                    etPw.setSelection(0,etPw.length()-1);
                    toast("请输入密码");
                    return;
                }

                Map<String, String> map = new HashMap<>();
                map.put("vAccount", ac);
                map.put("vPhone", phone);
                map.put("vPassword", phone);
                displayLoadingDialog("注册中");
                OkHttpHeader.post(UrlApi.user_reg, map, new ResultCallback2() {
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

                        displayTipDialogSuccess("注册成功");
                        C.sHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        },1_000);
                    }
                });
                break;
        }
    }
}
