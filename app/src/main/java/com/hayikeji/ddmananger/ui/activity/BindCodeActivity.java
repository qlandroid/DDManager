package com.hayikeji.ddmananger.ui.activity;

import android.ql.bindview.BindView;
import android.view.View;
import android.widget.EditText;

import com.hayikeji.ddmananger.R;
import com.hayikeji.ddmananger.base.BaseActivity;
import com.hayikeji.ddmananger.base.BindLayout;
import com.qmuiteam.qmui.alpha.QMUIAlphaImageButton;

@BindLayout(layoutRes = R.layout.activity_bind_code, title = "电表绑定")
public class BindCodeActivity extends BaseActivity {


    @BindView(R.id.activity_bind_code_tv_to_bind)
    View tvToBind;
    @BindView(R.id.activity_bind_code_et_code)
    EditText etCode;

    @Override
    public void initBar() {
        super.initBar();
        QMUIAlphaImageButton qmuiAlphaImageButton = mTopBar.addRightImageButton(R.drawable.home_menu_scanning, R.id.top_bar_right_btn);
        qmuiAlphaImageButton.setOnClickListener(this);
    }
}
