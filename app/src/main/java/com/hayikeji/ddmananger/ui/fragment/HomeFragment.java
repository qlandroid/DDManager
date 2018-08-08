package com.hayikeji.ddmananger.ui.fragment;

import android.ql.bindview.BindView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.hayikeji.ddmananger.R;
import com.hayikeji.ddmananger.base.BaseFragment;
import com.hayikeji.ddmananger.base.BindLayout;
import com.hayikeji.ddmananger.ui.HomeNav;
import com.hayikeji.ddmananger.ui.IGrid;
import com.hayikeji.ddmananger.ui.adapter.GridAdapter;
import com.hayikeji.ddmananger.ui.adapter.HomeContentAdapter;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：
 * 邮箱 email:strive_bug@yeah.net
 * 创建时间 2018/8/6
 *
 * @author ql
 */
@BindLayout(layoutRes = R.layout.frag_home, bindTopBar = false)
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

    @Override
    protected void initWidget(View view) {
        super.initWidget(view);
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
        rvContent.addItemDecoration(new DividerItemDecoration(getContext(),LinearLayoutManager.VERTICAL));
        rvContent.setAdapter(homeContentAdapter);

        initContentData();
        homeContentAdapter.notifyDataSetChanged();
    }

    private void initContentData() {
        homeContentAdapter.addData(new MultiItemEntity() {
            @Override
            public int getItemType() {
                return HomeContentAdapter.UN_BIND;
            }
        });
        homeContentAdapter.addData(new MultiItemEntity() {
            @Override
            public int getItemType() {
                return HomeContentAdapter.NO_MSG;
            }
        });
        homeContentAdapter.addData(new MultiItemEntity() {
            @Override
            public int getItemType() {
                return HomeContentAdapter.TITLE;
            }
        });
        homeContentAdapter.addData(new MultiItemEntity() {
            @Override
            public int getItemType() {
                return HomeContentAdapter.STOP;
            }
        });
        homeContentAdapter.addData(new MultiItemEntity() {
            @Override
            public int getItemType() {
                return HomeContentAdapter.STOP;
            }
        });
        homeContentAdapter.addData(new MultiItemEntity() {
            @Override
            public int getItemType() {
                return HomeContentAdapter.TITLE;
            }
        });
        homeContentAdapter.addData(new MultiItemEntity() {
            @Override
            public int getItemType() {
                return HomeContentAdapter.SMALL;
            }
        });
        homeContentAdapter.addData(new MultiItemEntity() {
            @Override
            public int getItemType() {
                return HomeContentAdapter.SMALL;
            }
        });
        homeContentAdapter.addData(new MultiItemEntity() {
            @Override
            public int getItemType() {
                return HomeContentAdapter.TITLE;
            }
        });
        homeContentAdapter.addData(new MultiItemEntity() {
            @Override
            public int getItemType() {
                return HomeContentAdapter.ERROR;
            }
        });
        homeContentAdapter.addData(new MultiItemEntity() {
            @Override
            public int getItemType() {
                return HomeContentAdapter.ERROR;
            }
        });
    }

    private void initNav() {
        List<IGrid> navs = new ArrayList<>();
        navs.add(new HomeNav(NAV_TAG_VIP, R.mipmap.ic_launcher, "成为会员"));
        navs.add(new HomeNav(NAV_TAG_BIND, R.mipmap.ic_launcher, "绑定电表"));
        navs.add(new HomeNav(NAV_TAG_ZS, R.mipmap.ic_launcher, "招商加盟"));
        navs.add(new HomeNav(NAV_TAG_HZ, R.mipmap.ic_launcher, "合作伙伴"));
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
