package com.hayikeji.ddmananger.ui.activity.bind;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.ql.bindview.BindView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.hayikeji.ddmananger.C;
import com.hayikeji.ddmananger.R;
import com.hayikeji.ddmananger.base.BaseActivity;
import com.hayikeji.ddmananger.base.BindLayout;
import com.qmuiteam.qmui.alpha.QMUIAlphaImageButton;

@BindLayout(layoutRes = R.layout.activity_bind_code, title = "电表绑定")
public class BindDevActivity extends BaseActivity {


    @BindView(R.id.activity_bind_code_tv_to_bind)
    View tvToBind;
    @BindView(R.id.activity_bind_code_et_code)
    EditText etCode;
    private final int REQUEST_BIND = 222;

    @Override
    public void initBar() {
        super.initBar();
        QMUIAlphaImageButton qmuiAlphaImageButton = mTopBar.addRightImageButton(R.drawable.home_menu_scanning, R.id.top_bar_right_btn);
        qmuiAlphaImageButton.setOnClickListener(this);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        tvToBind.setOnClickListener(this);
    }

    @Override
    public void forbidClick(View v) {
        super.forbidClick(v);
        switch (v.getId()) {
            case R.id.activity_bind_code_tv_to_bind:
                String s = etCode.getText().toString();
                if (TextUtils.isEmpty(s)) {
                    return;
                }
                Bundle b = new Bundle();
                b.putString(C.DEV_CODE, s);
                startActivity(BindDetailsActivity.class, b, REQUEST_BIND);
                break;
            case R.id.top_bar_right_btn:
                toScanningActivity();
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

    @Override
    public void onResultCode(String resultContent) {
        super.onResultCode(resultContent);
        etCode.setText(resultContent);
    }
}
