package com.hayikeji.ddmanager.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.ql.bindview.BindView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hayikeji.ddmanager.R;
import com.hayikeji.ddmanager.base.BaseActivity;
import com.hayikeji.ddmanager.base.BindLayout;
import com.hayikeji.ddmanager.bean.BaseResult;
import com.hayikeji.ddmanager.bean.DeviceBean;
import com.hayikeji.ddmanager.bean.TransationDocBean;
import com.hayikeji.ddmanager.bean.base.Page;
import com.hayikeji.ddmanager.http.OkHttpHeader;
import com.hayikeji.ddmanager.http.ResultCallback2;
import com.hayikeji.ddmanager.info.UrlApi;
import com.hayikeji.ddmanager.ui.adapter.PayMsgAdapter;
import com.hayikeji.ddmanager.ui.adapter.PayRecordAdapter;
import com.hayikeji.ddmanager.utils.DataUtils;
import com.hayikeji.ddmanager.utils.ViewUtils;
import com.hayikeji.ddmanager.utils.div.DividerItemDecoration;
import com.hayikeji.ddmanager.utils.preferences.UserDevPreferences;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@BindLayout(layoutRes = R.layout.activity_pay_record, title = "充值记录")
public class PayRecordActivity extends BaseActivity implements BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.activity_pay_record_rv)
    RecyclerView rv;
    @BindView(R.id.activity_pay_record_tv_all)
    TextView tvAll;//选择全部
    @BindView(R.id.activity_pay_record_tv_dev_code)
    TextView tvDevCode;

    PayMsgAdapter payMsgAdapter = new PayMsgAdapter();
    Page page = new Page();
    private List warnList = new ArrayList();
    private View loadEndView;
    private final int REQUEST_SELECT_DEV = 22;

    @Override
    public void initBar() {
        super.initBar();
        mTopBar.addRightTextButton("设备号", R.id.top_bar_right_btn).setOnClickListener(this);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        tvAll.setOnClickListener(this);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL, 10, Color.GRAY));
        rv.setAdapter(payMsgAdapter);

        loadEndView = ViewUtils.getLoadEndView(this);

        payMsgAdapter.setOnLoadMoreListener(this, rv);
        loadMore();
        httpLoadDevDetaila();
    }

    private void httpLoadDevDetaila() {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", UserDevPreferences.getUserId(this));
        map.put("devId", UserDevPreferences.getSelectDev(this));

        OkHttpHeader.post(UrlApi.dev, map, new ResultCallback2() {
            @Override
            protected void onFailed(String error, int code) {

            }

            @Override
            protected void onSuccess(BaseResult response, int id) {
                if (response.isSuccess()) {
                    DeviceBean resultObj = DataUtils.getResultObj(response.getData(), DeviceBean.class);
                    setTextView(resultObj.getCode(), tvDevCode);
                }
            }
        });
    }

    @Override
    public void forbidClick(View v) {
        super.forbidClick(v);
        switch (v.getId()) {
            case R.id.activity_pay_record_tv_all:
                tvDevCode.setText("全部");
                break;
            case R.id.top_bar_right_btn:
                startActivity(DevListSelectActivity.class, REQUEST_SELECT_DEV);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_SELECT_DEV:
                httpLoadDevDetaila();
                refresh();
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void refresh() {
        page.setPageNum(1);
        loadMore();
    }

    private void loadMore() {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", UserDevPreferences.getUserId(this));
        map.put("devId", UserDevPreferences.getSelectDev(this));
        page.addPage(map);
        payMsgAdapter.setEnableLoadMore(false);
        OkHttpHeader.post(UrlApi.getTransaction, map, new ResultCallback2() {
            @Override
            protected void onFailed(String error, int code) {
                displayMessageDialog(error);
                payMsgAdapter.setEnableLoadMore(true);

            }

            @Override
            protected void onSuccess(BaseResult response, int id) {
                payMsgAdapter.setEnableLoadMore(true);

                if (!response.isSuccess()) {
                    return;
                }

                ArrayList<TransationDocBean> arrayResult = DataUtils.getArrayResult(response.getList(), TransationDocBean.class);
                if (page.getPageNum() == 1) {
                    warnList.clear();
                }
                if (arrayResult == null) {

                } else {
                    warnList.addAll(arrayResult);
                    page.addPageNum();
                }

                payMsgAdapter.setNewData(warnList);
                payMsgAdapter.notifyDataSetChanged();
                if (response.getPageSizes() <= page.getPageNum()) {
                    payMsgAdapter.setEnableLoadMore(false);

                    payMsgAdapter.addFooterView(loadEndView);
                    payMsgAdapter.loadMoreEnd();
                }
            }
        });
    }

    @Override
    public void onLoadMoreRequested() {
        loadMore();
    }
}
