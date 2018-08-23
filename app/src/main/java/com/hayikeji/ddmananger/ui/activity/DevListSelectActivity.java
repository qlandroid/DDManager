package com.hayikeji.ddmananger.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.ql.bindview.BindView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hayikeji.ddmananger.R;
import com.hayikeji.ddmananger.base.BaseActivity;
import com.hayikeji.ddmananger.base.BindLayout;
import com.hayikeji.ddmananger.bean.BaseResult;
import com.hayikeji.ddmananger.bean.DeviceBean;
import com.hayikeji.ddmananger.http.OkHttpHelper;
import com.hayikeji.ddmananger.http.ResultCallback;
import com.hayikeji.ddmananger.http.ResultCallback2;
import com.hayikeji.ddmananger.info.UrlApi;
import com.hayikeji.ddmananger.ui.adapter.DevSelectAdapter;
import com.hayikeji.ddmananger.ui.adapter.bean.IDevDetails;
import com.hayikeji.ddmananger.utils.DataUtils;
import com.hayikeji.ddmananger.utils.div.DividerItemDecoration;
import com.hayikeji.ddmananger.utils.preferences.UserDevPreferences;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@BindLayout(layoutRes = R.layout.content_rv, title = "设备列表")
public class DevListSelectActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.content_rv)
    RecyclerView rv;

    DevSelectAdapter adapter = new DevSelectAdapter();

    public static int getDevId(Intent data){
       return data.getIntExtra("devId",-1);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        List<IDevDetails> i = new ArrayList<>();
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL, 10, Color.GRAY));
        rv.setAdapter(adapter);


        adapter.setOnItemClickListener(this);
        refresh();
    }


    private void refresh() {
        displayLoadingDialog("加载数据中");
        Map<String, Object> map = new HashMap<>();
        map.put("userId", UserDevPreferences.getUserId(this));
        OkHttpHelper.post(UrlApi.dev_list, map, new ResultCallback2() {
            @Override
            protected void onFailed(String error, int code) {
                cancelLoadingDialog();
                displayMessageDialog(error);
            }

            @Override
            protected void onSuccess(BaseResult response, int id) {
                cancelLoadingDialog();
                if (!response.isSuccess()) {
                    displayMessageDialog(response.getCode()+response.getMessage());
                    return;
                }
                ArrayList<DeviceBean> arrayResult = DataUtils.getArrayResult(response.getList(), DeviceBean.class);
                adapter.setNewData((List) arrayResult);
                adapter.notifyDataSetChanged();

            }
        });
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        DeviceBean o = (DeviceBean) adapter.getData().get(position);
        int id = o.getId();
        Intent i = new Intent();
        i.putExtra("devId", id);
        setResult(Activity.RESULT_OK, i);
        finish();
    }
}
