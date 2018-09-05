package com.hayikeji.ddmanager.ui.activity;

import android.ql.bindview.BindView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.zxing.common.StringUtils;
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

@BindLayout(layoutRes = R.layout.activity_forget, title = "忘记密码")
public class ForgetActivity extends BaseActivity {

    @BindView(R.id.activity_forget_et_phone)
    EditText etPhone;
    @BindView(R.id.activity_forget_et_pw)
    EditText etPw;
    @BindView(R.id.activity_forget_et_pw_2)
    EditText etPw2;
    @BindView(R.id.activity_forget_et_msg_code)
    EditText etCode;
    @BindView(R.id.activity_forget_tv_forget)
    TextView tvForget;
    @BindView(R.id.activity_forget_tv_getMsgCode)
    TextView tvGetMsgCode;

    @Override
    public void initWidget() {
        super.initWidget();
        tvForget.setOnClickListener(this);
        tvGetMsgCode.setOnClickListener(this);
    }

    @Override
    public void forbidClick(View v) {
        super.forbidClick(v);
        switch (v.getId()) {
            case R.id.activity_forget_tv_getMsgCode:
                boolean selected = !v.isSelected();
                v.setSelected(selected);

                String phone = etPhone.getText().toString();
                if (!CheckUtils.isMobileNO(phone)) {
                    return;
                }
                break;
            case R.id.activity_forget_tv_forget:
                String rp = etPhone.getText().toString();
                if (!CheckUtils.isMobileNO(rp)) {
                    toFocusable(etPhone);
                    return;
                }
                String pw1 = etPw.getText().toString();
                String pw2 = etPw2.getText().toString();
                if (TextUtils.isEmpty(pw1)) {
                    toFocusable(etPw);
                    return;
                }
                if (TextUtils.isEmpty(pw2)) {
                    toFocusable(etPw2);
                    return;
                }
                if (!pw1.equals(pw2)) {
                    toFocusable(etPw2);
                    return;
                }
                Map<String, String> map = new HashMap<>();
                map.put("vPhone", rp);
                map.put("vPassword", pw1);

                displayLoadingDialog("提交中");
                OkHttpHeader.post(UrlApi.user_forget_password, map, new ResultCallback2() {
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
                        displayTipDialogSuccess("修改成功");
                        finish();
                    }
                });

                break;
        }
    }
}
