package com.hayikeji.ddmananger.ui.activity;


import android.os.Bundle;
import android.ql.bindview.BindView;
import android.view.View;
import android.view.ViewGroup;

import com.hayikeji.ddmananger.R;
import com.hayikeji.ddmananger.base.BaseActivity;
import com.hayikeji.ddmananger.base.BaseFragment;
import com.hayikeji.ddmananger.base.BindLayout;
import com.hayikeji.ddmananger.ui.fragment.EDetailsFragment;
import com.hayikeji.ddmananger.ui.fragment.HomeFragment;
import com.hayikeji.ddmananger.ui.fragment.IUnBindDev;
import com.hayikeji.ddmananger.ui.fragment.MyFragment;
import com.hayikeji.ddmananger.ui.fragment.UnBindDevFragment;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;

import java.util.ArrayList;
import java.util.List;

@BindLayout(layoutRes = R.layout.activity_home, bindTopBar = false)
public class HomeActivity extends BaseActivity implements IUnBindDev {


    private String POSITION = "position";
    private int framePosition;
    @BindView(R.id.activity_home_nav_index)
    ViewGroup navIndex;//首页
    @BindView(R.id.activity_home_nav_inform)
    ViewGroup navInform;//云通知
    @BindView(R.id.activity_home_nav_e_use)
    ViewGroup navEuse;//安全用电
    @BindView(R.id.activity_home_nav_my)
    ViewGroup navMy;
    @BindView(R.id.activity_home_nav_e_details)
    ViewGroup navEDetails;

    ViewGroup lastNav;

    private List<BaseFragment> fragments = new ArrayList<>();

    @Override
    public void initBar() {
        super.initBar();

    }

    @Override
    public void initData() {
        super.initData();
        fragments.add(HomeFragment.newInstance());
        fragments.add(EDetailsFragment.newInstance(this));
        fragments.add(EDetailsFragment.newInstance(this));
        fragments.add(EDetailsFragment.newInstance(this));
        fragments.add(MyFragment.newInstance());
    }

    @Override
    public void initStatusBar() {
        super.initStatusBar();
//        fullScreen(this);
        QMUIStatusBarHelper.translucent(this);
        QMUIStatusBarHelper.setStatusBarLightMode(this);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        navIndex.setTag(0);
        navIndex.setOnClickListener(this);
        navEDetails.setTag(1);
        navEDetails.setOnClickListener(this);
        navEuse.setTag(2);
        navEuse.setOnClickListener(this);
        navInform.setTag(3);
        navInform.setOnClickListener(this);
        navMy.setTag(4);
        navMy.setOnClickListener(this);

        myChangeFragment(R.id.activity_home_fl_content, fragments.get(0));
        setNavSelect(navIndex);
        lastNav = navIndex;
    }

    @Override
    public void forbidClick(View v) {
        super.forbidClick(v);
        switch (v.getId()) {
            case R.id.activity_home_nav_index:
            case R.id.activity_home_nav_inform:
            case R.id.activity_home_nav_e_use:
            case R.id.activity_home_nav_e_details:
            case R.id.activity_home_nav_my:
                if (v.equals(lastNav)) {
                    return;
                }
                setNavNormal(lastNav);
                int tag = (int) v.getTag();
                lastNav = (ViewGroup) v;
                setNavSelect(lastNav);
                myChangeFragment(R.id.activity_home_fl_content,fragments.get(tag));

                break;
        }
    }

    private void setNavNormal(ViewGroup vp) {
        for (int i = 0; i < vp.getChildCount(); i++) {
            vp.getChildAt(i).setSelected(false);
        }
    }

    private void setNavSelect(ViewGroup vp) {
        for (int i = 0; i < vp.getChildCount(); i++) {
            vp.getChildAt(i).setSelected(true);
        }
    }

    @Override
    public void changeUnbindFragment() {
        UnBindDevFragment unBindDevFragment = new UnBindDevFragment();
        myChangeFragment(R.id.activity_home_fl_content, unBindDevFragment);
    }

    /**
     * 解决屏幕旋转时：重复添加fragment。
     *
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //屏幕旋转时记录位置
        outState.putInt(POSITION, framePosition);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        //屏幕恢复时取出位置
        changeFragment(savedInstanceState.getInt(POSITION));
        super.onRestoreInstanceState(savedInstanceState);
    }

    private void changeFragment(int anInt) {

    }
}
