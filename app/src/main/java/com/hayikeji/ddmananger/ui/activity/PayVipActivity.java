package com.hayikeji.ddmananger.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.ql.bindview.BindView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hayikeji.ddmananger.R;
import com.hayikeji.ddmananger.base.BaseActivity;
import com.hayikeji.ddmananger.base.BindLayout;
import com.hayikeji.ddmananger.ui.adapter.bean.IMonth;
import com.hayikeji.ddmananger.ui.adapter.MonthSelectAdapter;
import com.hayikeji.ddmananger.utils.preferences.UserDevPreferences;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@BindLayout(layoutRes = R.layout.activity_pay_vip, title = "VIP开通")
public class PayVipActivity extends BaseActivity {

    private static final int REQUEST_DEV_SELECT = 123;

    @BindView(R.id.activity_pay_vip_rv_month_select)
    RecyclerView rvMonthSelect;
    @BindView(R.id.activity_pay_vip_tv_dev_select)
    View vDevSelect;
    @BindView(R.id.activity_pay_e_tv_to_pay)
    View vToPay;

    MonthSelectAdapter monthSelectAdapter = new MonthSelectAdapter();


    @Override
    public void initBar() {
        super.initBar();
        mTopBar.addRightTextButton("历史记录", R.id.top_bar_right_btn).setOnClickListener(this);
    }

    @Override
    public void initData() {
        super.initData();
        List<IMonth> l = new ArrayList<>();
        l.add(new Month("1个月", 1));
        l.add(new Month("2个月", 2));
        l.add(new Month("3个月", 3));
        l.add(new Month("半年", 4));
        l.add(new Month("一年", 5));
        l.add(new Month("二难念", 6));
        monthSelectAdapter.setNewData(l);
    }


    private void httpLoadDevDetaila() {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", UserDevPreferences.getUserId(this));
        map.put("devId", UserDevPreferences.getSelectDev(this));

    }

    private void httpLoadPrice(){

    }

    @Override
    public void initWidget() {
        super.initWidget();
        vToPay.setOnClickListener(this);
        vDevSelect.setOnClickListener(this);
        rvMonthSelect.setLayoutManager(new GridLayoutManager(this, 4));
        rvMonthSelect.setAdapter(monthSelectAdapter);

        monthSelectAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                monthSelectAdapter.setSelecTag(position);
                monthSelectAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void forbidClick(View v) {
        super.forbidClick(v);
        switch (v.getId()) {
            case R.id.activity_pay_vip_tv_dev_select:
                startActivity(DevListSelectActivity.class, REQUEST_DEV_SELECT);
                break;
            case R.id.activity_pay_e_tv_to_pay:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_DEV_SELECT:
                if (resultCode != Activity.RESULT_OK) {
                    return;
                }
                int devId = DevListSelectActivity.getDevId(data);
                UserDevPreferences.saveSelectDev(this,devId);
                httpLoadDevDetaila();
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private class Month implements IMonth {
        private String monthStr;
        private int tag;

        public Month(String monthStr, int tag) {
            this.monthStr = monthStr;
            this.tag = tag;
        }

        @Override
        public int getTag() {
            return 0;
        }

        @Override
        public CharSequence getMonthStr() {
            return null;
        }
    }
}
