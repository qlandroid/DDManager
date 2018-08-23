package com.hayikeji.ddmananger.ui.fragment.home;

import android.graphics.Color;
import android.os.Bundle;
import android.ql.bindview.BindView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.hayikeji.ddmananger.R;
import com.hayikeji.ddmananger.base.BaseFragment;
import com.hayikeji.ddmananger.base.BindLayout;
import com.hayikeji.ddmananger.bean.BaseResult;
import com.hayikeji.ddmananger.bean.DevDetails;
import com.hayikeji.ddmananger.bean.WarnBean;
import com.hayikeji.ddmananger.http.OkHttpHelper;
import com.hayikeji.ddmananger.http.ResultCallback;
import com.hayikeji.ddmananger.info.UrlApi;
import com.hayikeji.ddmananger.ui.HomeNav;
import com.hayikeji.ddmananger.ui.IGrid;
import com.hayikeji.ddmananger.ui.activity.DevListSelectActivity;
import com.hayikeji.ddmananger.ui.adapter.GridAdapter;
import com.hayikeji.ddmananger.ui.adapter.HomeContentAdapter;
import com.hayikeji.ddmananger.utils.DataUtils;
import com.hayikeji.ddmananger.utils.JsonUtils;
import com.hayikeji.ddmananger.utils.PreferencesUtils;
import com.hayikeji.ddmananger.utils.preferences.UserDevPreferences;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述：
 * 邮箱 email:strive_bug@yeah.net
 * 创建时间 2018/8/6
 *
 * @author ql
 */
@BindLayout(layoutRes = R.layout.frag_home, title = "首页", backRes = 0)
public class HomeFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.OnItemClickListener {
    private static final int NAV_TAG_VIP = 0;
    private static final int NAV_TAG_BIND = 1;
    private static final int NAV_TAG_ZS = 2;
    private static final int NAV_TAG_HZ = 3;


    @BindView(R.id.srl)
    SwipeRefreshLayout srl;
    @BindView(R.id.frag_home_banner)
    Banner banner;
    @BindView(R.id.frag_home_rv_nav)
    RecyclerView rvNav;
    @BindView(R.id.frag_home_rv_content)
    RecyclerView rvContent;

    private GridAdapter navAdapter = new GridAdapter();
    private HomeContentAdapter homeContentAdapter = new HomeContentAdapter();

    public static HomeFragment newInstance() {

        Bundle args = new Bundle();

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
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

    @Override
    protected void initWidget(View view) {
        super.initWidget(view);
        initTopbar();
        srl.setOnRefreshListener(this);
        rvNav.setLayoutManager(new GridLayoutManager(getContext(), 4));
        initNav();
        rvNav.setAdapter(navAdapter);

        navAdapter.setOnItemClickListener(this);

        rvContent.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        rvContent.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        rvContent.setAdapter(homeContentAdapter);

        displayLoadingDialog("加载数据中");

        refresh();
    }


    private void refresh() {

        Map<String, Object> params = new HashMap<>();
        params.put("userId", UserDevPreferences.getUserId(getContext()));
        OkHttpHelper.post(UrlApi.index_url, params, new ResultCallback() {
            @Override
            protected void onFailed(String error, int code) {
                cancelLoadingDialog();
                displayMessageDialog(error);
                srl.setRefreshing(false);
            }

            @Override
            protected void onSuccess(String response, int id) {
                cancelLoadingDialog();
                srl.setRefreshing(false);
                BaseResult o = JsonUtils.fromJson(response, BaseResult.class);
                DevDetails resultObj = DataUtils.getResultObj(o.getData(), DevDetails.class);
                List<WarnBean> warnList = resultObj.getWarnList();
                List<MultiItemEntity> data = homeContentAdapter.getData();
                if (data != null) {
                    data.clear();
                }
                if (!resultObj.getIsHasDev()) {
                    UserDevPreferences.saveIsHasDev(getContext(), false);
                    //没有设备
                    homeContentAdapter.addData(new MultiItemEntity() {
                        @Override
                        public int getItemType() {
                            return HomeContentAdapter.UN_BIND;
                        }
                    });
                } else if (warnList == null || warnList.size() == 0) {
                    homeContentAdapter.addData(new MultiItemEntity() {
                        @Override
                        public int getItemType() {
                            return HomeContentAdapter.NO_MSG;
                        }
                    });

                } else {
                    homeContentAdapter.setNewData((List) warnList);
                }

                homeContentAdapter.notifyDataSetChanged();
            }
        });
    }


    private void initNav() {
        List<IGrid> navs = new ArrayList<>();
        navs.add(new HomeNav(NAV_TAG_VIP, R.drawable.hp_icon_vip, "成为会员"));
        navs.add(new HomeNav(NAV_TAG_BIND, R.drawable.hp_icon_electric, "绑定电表"));
        navs.add(new HomeNav(NAV_TAG_ZS, R.drawable.hp_icon_join, "招商加盟"));
        navs.add(new HomeNav(NAV_TAG_HZ, R.drawable.hp_icon_cooperation, "合作伙伴"));
        navAdapter.setNewData(navs);
    }

    @Override
    public void onRefresh() {
        rvNav.postDelayed(new Runnable() {
            @Override
            public void run() {
                srl.setRefreshing(false);
            }
        }, 2_000);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        List<IGrid> data = adapter.getData();
        IGrid iGrid = data.get(position);
        switch (iGrid.getTag()) {
            case NAV_TAG_VIP:
                toast("成为会员");
                break;
            case NAV_TAG_BIND:
                toast("绑定");
                break;
            case NAV_TAG_HZ:
                toast("合作伙伴");
                break;
            case NAV_TAG_ZS:
                toast("招商");
                break;
        }
    }

}
