package com.hayikeji.ddmananger;

import android.os.Bundle;

import com.hayikeji.ddmananger.base.BaseActivity;
import com.hayikeji.ddmananger.ui.activity.HomeActivity;
import com.hayikeji.ddmananger.ui.activity.LoginActivity;
import com.hayikeji.ddmananger.utils.PreferencesUtils;
import com.hayikeji.ddmananger.utils.preferences.UserDevPreferences;


public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserDevPreferences.saveIsHasDev(this, true);
        UserDevPreferences.saveSelectDev(this, 50 );
        UserDevPreferences.saveUserId(this,17);
        startActivity(LoginActivity.class);
        finish();
    }
}
