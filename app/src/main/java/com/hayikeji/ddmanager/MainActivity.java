package com.hayikeji.ddmanager;

import android.os.Bundle;

import com.hayikeji.ddmanager.base.BaseActivity;
import com.hayikeji.ddmanager.ui.activity.HomeActivity;
import com.hayikeji.ddmanager.ui.activity.LoginActivity;
import com.hayikeji.ddmanager.utils.PreferencesUtils;
import com.hayikeji.ddmanager.utils.preferences.UserDevPreferences;


public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(LoginActivity.class);
        finish();
    }
}
