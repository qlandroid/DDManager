package com.hayikeji.ddmananger.ui.activity;

import android.ql.bindview.BindView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.common.StringUtils;
import com.hayikeji.ddmananger.MyApp;
import com.hayikeji.ddmananger.R;
import com.hayikeji.ddmananger.base.BaseActivity;
import com.hayikeji.ddmananger.base.BindLayout;
import com.hayikeji.ddmananger.bean.BaseResult;
import com.hayikeji.ddmananger.bean.UserBean;
import com.hayikeji.ddmananger.http.OkHttpHeader;
import com.hayikeji.ddmananger.http.ResultCallback2;
import com.hayikeji.ddmananger.info.UrlApi;
import com.hayikeji.ddmananger.utils.CheckUtils;
import com.hayikeji.ddmananger.utils.DataUtils;
import com.hayikeji.ddmananger.utils.DateUtils;
import com.hayikeji.ddmananger.utils.preferences.UserDevPreferences;

import java.util.HashMap;
import java.util.Map;

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
        etAccount.setText("15601953393");
        etPw.setText("123456");
    }


    @Override
    public void forbidClick(View v) {
        super.forbidClick(v);
        switch (v.getId()) {
            case R.id.activity_login_tv_login:
                String ac = etAccount.getText().toString();
                String pw = etPw.getText().toString();
                displayLoadingDialog("登录中");
                Map<String, Object> map = new HashMap<>();
                map.put("vAccount", ac);
                map.put("vPassword", pw);

                OkHttpHeader.post(UrlApi.user_login, map, new ResultCallback2() {
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

                        UserBean resultObj = DataUtils.getResultObj(response.getData(), UserBean.class);
                        UserDevPreferences.saveUserId(getBaseContext(), resultObj.getUserId());
                        startActivity(HomeActivity.class);
                        MyApp.refreshUserDetails();
                    }
                });
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
