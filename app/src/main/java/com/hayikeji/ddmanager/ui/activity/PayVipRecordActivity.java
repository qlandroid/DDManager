package com.hayikeji.ddmanager.ui.activity;

import android.graphics.Color;
import android.ql.bindview.BindView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hayikeji.ddmanager.R;
import com.hayikeji.ddmanager.base.BaseActivity;
import com.hayikeji.ddmanager.base.BindLayout;
import com.hayikeji.ddmanager.bean.BaseResult;
import com.hayikeji.ddmanager.bean.VipBean;
import com.hayikeji.ddmanager.bean.base.Page;
import com.hayikeji.ddmanager.http.OkHttpHeader;
import com.hayikeji.ddmanager.http.ResultCallback2;
import com.hayikeji.ddmanager.info.UrlApi;
import com.hayikeji.ddmanager.ui.adapter.PayVipAdapter;
import com.hayikeji.ddmanager.utils.div.DividerItemDecoration;
import com.hayikeji.ddmanager.utils.preferences.UserDevPreferences;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@BindLayout(layoutRes = R.layout.activity_pay_vip_record, title = "交易记录")
public class PayVipRecordActivity extends BaseActivity implements BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.activity_pay_vip_record_rv)
    RecyclerView rv;

    private PayVipAdapter payVipAdapter = new PayVipAdapter();
    private Page page = new Page();
    private List data = new ArrayList();

    @Override
    public void initWidget() {
        super.initWidget();
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL, 10, Color.GRAY));
        rv.setAdapter(payVipAdapter);

        payVipAdapter.setOnLoadMoreListener(this, rv);
        payVipAdapter.setEnableLoadMore(false);
        loadMore();
    }

    private void loadMore() {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", UserDevPreferences.getUserId(this));
        map.put("devId", UserDevPreferences.getSelectDev(this));
        page.addPage(map);
        OkHttpHeader.post(UrlApi.pay_vip_record, map, new ResultCallback2() {
            @Override
            protected void onFailed(String error, int code) {
                cancelLoadingDialog();
                displayMessageDialog(error);
            }

            @Override
            protected void onSuccess(BaseResult response, int id) {
                cancelLoadingDialog();
                if (!response.isSuccess()) {
                    displayMessageDialog(response.getMessage());
                    return;
                }
                List list = response.getListO(VipBean.class);
                if (page.getPageNum() == 1) {
                    data.clear();
                }
                if (list != null) {
                    data.addAll(list);
                }
                payVipAdapter.setNewData(data);
                if (response.checkLoadEnd(page)) {
                    payVipAdapter.loadMoreEnd();
                    return;
                }
                page.addPageNum();
                payVipAdapter.setEnableLoadMore(true);
                payVipAdapter.notifyDataSetChanged();
            }
        });
    }


    @Override
    public void onLoadMoreRequested() {
        payVipAdapter.setEnableLoadMore(false);
        loadMore();
    }
}
