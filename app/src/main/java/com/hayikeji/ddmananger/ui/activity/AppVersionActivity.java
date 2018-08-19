package com.hayikeji.ddmananger.ui.activity;

import android.ql.bindview.BindView;
import android.widget.TextView;

import com.hayikeji.ddmananger.R;
import com.hayikeji.ddmananger.base.BaseActivity;
import com.hayikeji.ddmananger.base.BindLayout;
import com.qmuiteam.qmui.util.QMUIDeviceHelper;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.util.QMUIPackageHelper;

@BindLayout(layoutRes = R.layout.activity_app_version,title = "版本号")
public class AppVersionActivity extends BaseActivity {

    @BindView(R.id.activity_app_version_tv_content)
    TextView tvContent;
    @BindView(R.id.activity_app_version_tv_update_date)
    TextView tvUpdateDate;
    @BindView(R.id.activity_app_version_tv_version)
    TextView tvVersion;


    @Override
    public void initWidget() {
        super.initWidget();
        String appVersion = QMUIPackageHelper.getAppVersion(this);
        tvVersion.setText(appVersion);
    }
}
