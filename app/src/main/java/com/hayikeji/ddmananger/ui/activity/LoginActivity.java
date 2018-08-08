package com.hayikeji.ddmananger.ui.activity;

import android.ql.bindview.BindView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.common.StringUtils;
import com.hayikeji.ddmananger.R;
import com.hayikeji.ddmananger.base.BaseActivity;
import com.hayikeji.ddmananger.base.BindLayout;
import com.hayikeji.ddmananger.utils.CheckUtils;

@BindLayout(layoutRes = R.layout.activity_login, bindTopBar = false)
public class LoginActivity extends BaseActivity {

    @BindView(R.id.activity_login_iv)
    ImageView iv;
    @BindView(R.id.activity_login_et_account)
    EditText etAccount;
    @BindView(R.id.activity_login_et_pw)
    EditText etPw;
    @BindView(R.id.activity_login_tv_reg)
    TextView tvReg;
    @BindView(R.id.activity_login_tv_forget)
    TextView tvForget;
    @BindView(R.id.activity_login_tv_login)
    TextView tvLogin;


    @Override
    public void initWidget() {
        super.initWidget();
        tvReg.setOnClickListener(this);
        tvForget.setOnClickListener(this);
        tvLogin.setOnClickListener(this);

    }


    @Override
    public void forbidClick(View v) {
        super.forbidClick(v);
        switch (v.getId()) {
            case R.id.activity_login_tv_login:
                String ac = etAccount.getText().toString();
                String pw = etPw.getText().toString();
                if (!CheckUtils.isMobileNO(ac)) {
                    toFocusable(etAccount);
                    return;
                }

                if (TextUtils.isEmpty(pw)) {
                    toFocusable(etPw);
                    return;
                }
                if (pw.length() < 6) {
                    return;
                }

                startActivity(HomeActivity.class);
                break;
            case R.id.activity_login_tv_forget:
                startActivity(ForgetActivity.class);
                break;
            case R.id.activity_login_tv_reg:
                startActivity(RegActivity.class);
                break;
        }
    }
}
