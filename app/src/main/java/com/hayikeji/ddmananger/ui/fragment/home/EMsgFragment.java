package com.hayikeji.ddmananger.ui.fragment.home;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.ql.bindview.BindView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.LinearLayout;

import com.hayikeji.ddmananger.R;
import com.hayikeji.ddmananger.base.BaseFragment;
import com.hayikeji.ddmananger.base.BindLayout;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUIPagerAdapter;
import com.qmuiteam.qmui.widget.QMUITabSegment;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：
 * 邮箱 email:strive_bug@yeah.net
 * 创建时间 2018/8/15
 *
 * @author ql
 */
@BindLayout(layoutRes = R.layout.frag_e_msg, title = "云通知", backRes = 0)
public class EMsgFragment extends BaseFragment {

    @BindView(R.id.frag_e_msg_segment)
    QMUITabSegment tabSegment;
    @BindView(R.id.frag_e_msg_vp)
    ViewPager vp;

    private TabMenuAdapter tabMenuAdapter;

    public static EMsgFragment newInstance() {

        Bundle args = new Bundle();

        EMsgFragment fragment = new EMsgFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initData() {
        super.initData();
        tabMenuAdapter = new TabMenuAdapter();
    }

    private class TabMenuAdapter extends QMUIPagerAdapter {
        private FragmentTransaction mCurrentTransaction;
        private Fragment mCurrentPrimaryItem = null;
        private List<BaseFragment> list = new ArrayList<>();

        public TabMenuAdapter() {
            /**
             *   case 0:
             return PayMsgFragment.newInstance();
             case 1:
             return WarnMsgFragment.newInstance();
             case 2:
             return MsgFragment.newInstance("0");
             case 3:
             return MsgFragment.newInstance("1");
             */
            list.add(PayMsgFragment.newInstance());
            list.add(WarnMsgFragment.newInstance());
            list.add(MsgFragment.newInstance("0"));
            list.add(MsgFragment.newInstance("1"));
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((Fragment) object).getView();
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            /**
             * tabSegment.addTab(new QMUITabSegment.Tab("充值通知"));
             tabSegment.addTab(new QMUITabSegment.Tab("异常警告"));
             tabSegment.addTab(new QMUITabSegment.Tab("停电通知"));
             tabSegment.addTab(new QMUITabSegment.Tab("低电通知"));
             */
            switch (position) {
                case 0:
                    return "充值通知";
                case 1:
                    return "异常警告";
                case 2:
                    return "停电通知";
                case 3:
                    return "低电通知";
                default:
                    return "About";
            }
        }

        @Override
        protected Object hydrate(ViewGroup container, int position) {
            return list.get(position);
        }

        @SuppressLint("CommitTransaction")
        @Override
        protected void populate(ViewGroup container, Object item, int position) {
            String name = makeFragmentName(container.getId(), position);
            if (mCurrentTransaction == null) {
                mCurrentTransaction = getChildFragmentManager()
                        .beginTransaction();
            }
            Fragment fragment = getChildFragmentManager().findFragmentByTag(name);
            if (fragment != null) {
                mCurrentTransaction.attach(fragment);
            } else {
                fragment = (Fragment) item;
                mCurrentTransaction.add(container.getId(), fragment, name);
            }
            if (fragment != mCurrentPrimaryItem) {
                fragment.setMenuVisibility(false);
                fragment.setUserVisibleHint(false);
            }
        }

        @SuppressLint("CommitTransaction")
        @Override
        protected void destroy(ViewGroup container, int position, Object object) {
            if (mCurrentTransaction == null) {
                mCurrentTransaction = getChildFragmentManager()
                        .beginTransaction();
            }
            mCurrentTransaction.detach((Fragment) object);
        }

        @Override
        public void startUpdate(ViewGroup container) {
            if (container.getId() == View.NO_ID) {
                throw new IllegalStateException("ViewPager with adapter " + this
                        + " requires a view id");
            }
        }

        @Override
        public void finishUpdate(ViewGroup container) {
            if (mCurrentTransaction != null) {
                mCurrentTransaction.commitNowAllowingStateLoss();
                mCurrentTransaction = null;
            }
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            Fragment fragment = (Fragment) object;
            if (fragment != mCurrentPrimaryItem) {
                if (mCurrentPrimaryItem != null) {
                    mCurrentPrimaryItem.setMenuVisibility(false);
                    mCurrentPrimaryItem.setUserVisibleHint(false);
                }
                if (fragment != null) {
                    fragment.setMenuVisibility(true);
                    fragment.setUserVisibleHint(true);
                }
                mCurrentPrimaryItem = fragment;
            }
        }

        private String makeFragmentName(int viewId, long id) {
            return "QDFitSystemWindowViewPagerFragment:" + viewId + ":" + id;
        }
    }

    ;

    @Override
    protected void initWidget(View view) {
        super.initWidget(view);
        initTopbar();

        initTabAndPager();


    }

    private void initTopbar() {
        ViewParent parent = mTopbar.getParent();
        ViewGroup parentView = (ViewGroup) parent;
        View v = new View(getContext());
        LinearLayout.LayoutParams l = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, QMUIStatusBarHelper.getStatusbarHeight(getContext()));

        v.setLayoutParams(l);
        v.setBackgroundColor(Color.WHITE);
        parentView.addView(v, 0);
    }

    private void initTabAndPager() {
        vp.setAdapter(tabMenuAdapter);
//        QMUIDrawableHelper
        Drawable drawable = getContext().getResources().getDrawable(R.drawable.shape_blue_line);
        tabSegment.setIndicatorDrawable(drawable);

        tabSegment.setDefaultNormalColor(ContextCompat.getColor(getContext(), R.color.color_number_9));
        tabSegment.setDefaultSelectedColor(Color.BLACK);


        tabSegment.setHasIndicator(true);
        tabSegment.setupWithViewPager(vp);
        tabSegment.setMode(QMUITabSegment.MODE_FIXED);

    }


}
