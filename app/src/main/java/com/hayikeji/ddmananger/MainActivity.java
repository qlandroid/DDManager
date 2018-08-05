package com.hayikeji.ddmananger;

import android.os.Bundle;

import com.hayikeji.ddmananger.base.BaseActivity;
import com.hayikeji.ddmananger.ui.activity.HomeActivity;


public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(HomeActivity.class);
        finish();
    }
}
