package com.hayikeji.ddmananger.ui.fragment;

import android.os.Bundle;
import android.ql.bindview.BindView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hayikeji.ddmananger.R;
import com.hayikeji.ddmananger.base.BaseFragment;
import com.hayikeji.ddmananger.base.BindLayout;
import com.hayikeji.ddmananger.ui.IGrid;
import com.hayikeji.ddmananger.ui.activity.EManagerActivity;
import com.hayikeji.ddmananger.ui.adapter.GridAdapter;
import com.hayikeji.ddmananger.ui.adapter.INav;
import com.hayikeji.ddmananger.ui.adapter.MyNavAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：
 * 邮箱 email:strive_bug@yeah.net
 * 创建时间 2018/8/8
 *
 * @author ql
 */
@BindLayout(layoutRes = R.layout.frag_my ,bindTopBar = false)
public class MyFragment extends BaseFragment {

    private static final int NAV_MENU_MY_DEV = 2;
    private static final int NAV_MENU_CONTROL_DEV = 3;
    private static final int NAV_MENU_USER_DETAILS = 4;

    private static final int NAV_MENU_VIP = 1;
    private static final int NAV_MENU_E_ADD = 5;
    private static final int NAV_MENU_E_PAY = 6;
    private static final int NAV_MENU_E_RECORD = 7;
    private static final int NAV_MENU_APP_VERSION = 8;


    @BindView(R.id.frag_my_tv_dev_count)
    TextView tvDevCount;//设备数量
    @BindView(R.id.frag_my_tv_in_date)
    TextView tvInDate;//入网时间
    @BindView(R.id.frag_my_tv_name)
    TextView tvName;//用户名称
    @BindView(R.id.frag_my_grid_nav)
    RecyclerView rvGridNav;
    @BindView(R.id.frag_my_iv_header)
    ImageView ivHeader;
    @BindView(R.id.frag_my_bottom_nav_rv)
    RecyclerView rvBottomNav;
    @BindView(R.id.frag_my_tv_logout)
    View tvLogout;

    private GridAdapter gridNavAdapter = new GridAdapter();
    private MyNavAdapter myNavAdapter;

    public static MyFragment newInstance() {
        
        Bundle args = new Bundle();
        
        MyFragment fragment = new MyFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected void initData() {
        super.initData();
        initBottomNavData();
        initGridNavDate();

    }

    private void initGridNavDate() {
        List<IGrid> l = new ArrayList<>();
        l.add(new GridNavBean(NAV_MENU_MY_DEV, "我的设备", R.drawable.icon_home_order_48));
        l.add(new GridNavBean(NAV_MENU_CONTROL_DEV, "远程断电", R.drawable.icon_home_order_48));
        l.add(new GridNavBean(NAV_MENU_USER_DETAILS, "个人信息", R.drawable.icon_home_order_48));
        gridNavAdapter.setNewData(l);
        gridNavAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                IGrid o = (IGrid) adapter.getData().get(position);
                switch (o.getTag()) {
                    case NAV_MENU_MY_DEV:
                        break;
                    case NAV_MENU_CONTROL_DEV:
                        startActivity(EManagerActivity.class);
                        break;
                    case NAV_MENU_USER_DETAILS:
                        break;
                }
            }
        });
    }

    @Override
    protected void initWidget(View view) {
        super.initWidget(view);
        rvBottomNav.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        rvBottomNav.setAdapter(myNavAdapter);

        rvGridNav.setLayoutManager(new GridLayoutManager(getContext(), 3) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

        rvGridNav.setAdapter(gridNavAdapter);
    }

    private void initBottomNavData() {
        List<INav> list = new ArrayList<>();
        list.add(new BottomNavBean("成为VIP", R.drawable.ic_vip, NAV_MENU_VIP, MyNavAdapter.SINGLE));
        list.add(new BottomNavBean());
        list.add(new BottomNavBean("新增电表", R.drawable.ic_vip, NAV_MENU_E_ADD, MyNavAdapter.S_DIV));
        list.add(new BottomNavBean("电力缴费", R.drawable.ic_vip, NAV_MENU_E_PAY, MyNavAdapter.S_DIV));
        list.add(new BottomNavBean("历史电量", R.drawable.ic_vip, NAV_MENU_E_RECORD, MyNavAdapter.SINGLE));
        list.add(new BottomNavBean());
        list.add(new BottomNavBean("合作伙伴", R.drawable.ic_vip, NAV_MENU_E_RECORD, MyNavAdapter.S_DIV));
        list.add(new BottomNavBean("招商加盟", R.drawable.ic_vip, NAV_MENU_E_RECORD, MyNavAdapter.S_DIV));
        list.add(new BottomNavBean("客户热线", R.drawable.ic_vip, NAV_MENU_E_RECORD, MyNavAdapter.SINGLE));
        list.add(new BottomNavBean());
        list.add(new BottomNavBean("版本号", R.drawable.ic_vip, NAV_MENU_E_RECORD, MyNavAdapter.SINGLE));

        myNavAdapter = new MyNavAdapter(list);
        myNavAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                INav o = (INav) adapter.getData().get(position);
                if (o.getItemType() == MyNavAdapter.S_NULL) {
                    return;
                }
                switch (o.getMenuType()) {
                    case NAV_MENU_APP_VERSION:
                        break;
                    case NAV_MENU_E_ADD:
                        break;
                    case NAV_MENU_E_PAY:
                        break;
                    case NAV_MENU_E_RECORD:
                        break;
                    case NAV_MENU_VIP:
                        break;
                }
            }
        });
    }

    public static class BottomNavBean implements INav {
        private String name;
        private int iconRes;
        private int menuType;
        private int itemType;

        public BottomNavBean(String name, int iconRes, int menuType, int itemType) {
            this.name = name;
            this.iconRes = iconRes;
            this.menuType = menuType;
            this.itemType = itemType;
        }

        public BottomNavBean() {
            itemType = MyNavAdapter.S_NULL;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public int getIconRes() {
            return iconRes;
        }

        @Override
        public int getMenuType() {
            return menuType;
        }

        @Override
        public int getItemType() {
            return itemType;
        }
    }

    public static class GridNavBean implements IGrid {
        private int tag;
        private String label;
        private int iconRes;

        public GridNavBean(int tag, String label, int iconRes) {
            this.tag = tag;
            this.label = label;
            this.iconRes = iconRes;
        }

        @Override
        public int getTag() {
            return tag;
        }

        @Override
        public int getIconRes() {
            return iconRes;
        }

        @Override
        public String getLabel() {
            return label;
        }
    }
}
