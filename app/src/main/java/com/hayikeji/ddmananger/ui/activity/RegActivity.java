package com.hayikeji.ddmananger.ui.activity;

import android.ql.bindview.BindView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hayikeji.ddmananger.R;
import com.hayikeji.ddmananger.base.BaseActivity;
import com.hayikeji.ddmananger.base.BindLayout;
import com.hayikeji.ddmananger.utils.CheckUtils;

@BindLayout(layoutRes = R.layout.activity_reg,title = "注册")
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
                    return;
                }
                if (!CheckUtils.isMobileNO(phone)) {
                    return;
                }
                if (TextUtils.isEmpty(pw)) {
                    return;
                }
                if (!CheckUtils.isMsgCode(msgCode)) {
                    return;
                }
                finish();

                break;
        }
    }
}
