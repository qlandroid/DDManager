package com.hayikeji.ddmananger.ui.activity;

import android.ql.bindview.BindView;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hayikeji.ddmananger.C;
import com.hayikeji.ddmananger.MyApp;
import com.hayikeji.ddmananger.R;
import com.hayikeji.ddmananger.base.BaseActivity;
import com.hayikeji.ddmananger.base.BindLayout;
import com.hayikeji.ddmananger.bean.BaseResult;
import com.hayikeji.ddmananger.http.OkHttpHeader;
import com.hayikeji.ddmananger.http.ResultCallback2;
import com.hayikeji.ddmananger.info.UrlApi;
import com.hayikeji.ddmananger.utils.eventbus.ChangeDetailsEventBus;
import com.hayikeji.ddmananger.utils.preferences.UserDevPreferences;

import java.util.HashMap;
import java.util.Map;

@BindLayout(layoutRes = R.layout.activity_user_details, title = "用户详情")
public class UserDetailsActivity extends BaseActivity {
    @BindView(R.id.activity_user_details_tv_indate)
    TextView tvIndate;
    @BindView(R.id.activity_user_Details_tv_save)
    View vSave;
    @BindView(R.id.activity_user_details_et_nick_name)
    EditText etNickName;


    @Override
    public void initWidget() {
        super.initWidget();
        vSave.setOnClickListener(this);
        tvIndate.setText(UserDevPreferences.getInDate(this));
        etNickName.setText(UserDevPreferences.getUserNickName(this));
        if (etNickName.length() != 0) {

            etNickName.setSelection(0, etNickName.length() - 1);
        }
    }


    @Override
    public void forbidClick(View v) {
        super.forbidClick(v);
        switch (v.getId()) {

            case R.id.activity_user_Details_tv_save:
                Editable text = etNickName.getText();
                if (TextUtils.isEmpty(text)) {
                    return;
                }

                Map<String, Object> map = new HashMap<>();

                map.put("userId", UserDevPreferences.getUserId(this));
                map.put("vNickName", text.toString());

                OkHttpHeader.post(UrlApi.user_set_details, map, new ResultCallback2() {
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
                        C.sHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                MyApp.refreshUserDetails();
                                finish();
                            }
                        }, 1_000);
                    }
                });

                break;
        }
    }
}
