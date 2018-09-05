package com.hayikeji.ddmanager.ui.activity;

import android.graphics.Color;
import android.ql.bindview.BindView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hayikeji.ddmanager.R;
import com.hayikeji.ddmanager.base.BaseActivity;
import com.hayikeji.ddmanager.base.BindLayout;
import com.hayikeji.ddmanager.bean.BaseResult;
import com.hayikeji.ddmanager.bean.DevDetails;
import com.hayikeji.ddmanager.bean.DeviceBean;
import com.hayikeji.ddmanager.http.OkHttpHeader;
import com.hayikeji.ddmanager.http.OkHttpHelper;
import com.hayikeji.ddmanager.http.ResultCallback2;
import com.hayikeji.ddmanager.info.UrlApi;
import com.hayikeji.ddmanager.ui.adapter.ECloseManagerAdapter;
import com.hayikeji.ddmanager.ui.adapter.bean.ECloseManager;
import com.hayikeji.ddmanager.ui.adapter.bean.IECloseManager;
import com.hayikeji.ddmanager.ui.widget.dialog.DevDetailsDialog;
import com.hayikeji.ddmanager.ui.widget.dialog.EManagerDialog;
import com.hayikeji.ddmanager.utils.DataUtils;
import com.hayikeji.ddmanager.utils.div.DividerItemDecoration;
import com.hayikeji.ddmanager.utils.preferences.UserDevPreferences;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@BindLayout(layoutRes = R.layout.activity_my_dev_list, title = "我的设备")
public class MyDevListActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener {
    @BindView(R.id.activity_my_dev_list_et_dev_code)
    EditText etDevCode;
    @BindView(R.id.activity_my_dev_list_to_query)
    View vQuery;
    @BindView(R.id.activity_my_dev_list_rv)
    RecyclerView rv;
    @BindView(R.id.activity_my_dev_list_tv_dev_count)
    TextView tvDevCount;

    private ECloseManagerAdapter adapter;

    @Override
    public void initWidget() {
        super.initWidget();
        vQuery.setOnClickListener(this);

        adapter = new ECloseManagerAdapter();
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
        rv.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL, 20, Color.GRAY));

        adapter.setOnItemClickListener(this);

        refresh(null);
    }

    private void refresh(String code) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", UserDevPreferences.getUserId(this));
        if (code != null) {
            map.put("code", code);
        }
        OkHttpHeader.post(UrlApi.dev_list, map, new ResultCallback2() {
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

                ArrayList<DeviceBean> arrayResult = DataUtils.getArrayResult(response.getList(), DeviceBean.class);
                if (arrayResult == null || arrayResult.size() == 0) {
                    adapter.setNewData(null);
                    adapter.notifyDataSetChanged();
                    tvDevCount.setText("0");
                    return;
                }
                tvDevCount.setText(arrayResult.size() + "");
                ArrayList<IECloseManager> l = new ArrayList<>();
                for (DeviceBean deviceBean : arrayResult) {
                    l.add(new ECloseManager(deviceBean));
                }
                adapter.setNewData(l);
                adapter.notifyDataSetChanged();

            }
        });
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        DevDetailsDialog dialog = new DevDetailsDialog(this);
        ECloseManager o = (ECloseManager) adapter.getData().get(position);
        DevDetails devDetails = new DevDetails();

        dialog.resetView(o.getDeviceBean());
        dialog.show();
    }


    @Override
    public void forbidClick(View v) {
        super.forbidClick(v);
        switch (v.getId()) {
            case R.id.activity_my_dev_list_to_query:

                String devCode = etDevCode.getText().toString();
                refresh(devCode);
                displayLoadingDialog("查询中");
                break;
        }
    }


}
