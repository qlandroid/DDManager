package com.hayikeji.ddmananger.ui.activity;


import com.hayikeji.ddmananger.R;
import com.hayikeji.ddmananger.base.BaseActivity;
import com.hayikeji.ddmananger.base.BindLayout;
import com.hayikeji.ddmananger.ui.fragment.EDetailsFragment;
import com.hayikeji.ddmananger.ui.fragment.HomeFragment;
import com.hayikeji.ddmananger.ui.fragment.IUnBindDev;
import com.hayikeji.ddmananger.ui.fragment.MyFragment;
import com.hayikeji.ddmananger.ui.fragment.UnBindDevFragment;

@BindLayout(layoutRes = R.layout.activity_home,bindTopBar = false)
public class HomeActivity extends BaseActivity implements IUnBindDev {


    @Override
    public void initWidget() {
        super.initWidget();
        HomeFragment homeFragment = new HomeFragment();
        EDetailsFragment f = new EDetailsFragment();
        MyFragment m = new MyFragment();
        myChangeFragment(R.id.activity_home_fl_content,m);
    }

    @Override
    public void changeUnbindFragment() {
        UnBindDevFragment unBindDevFragment = new UnBindDevFragment();
        myChangeFragment(R.id.activity_home_fl_content,unBindDevFragment);
    }
}
