package com.hayikeji.ddmananger.ui.activity;

import android.graphics.Color;
import android.ql.bindview.BindView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hayikeji.ddmananger.R;
import com.hayikeji.ddmananger.base.BaseActivity;
import com.hayikeji.ddmananger.base.BindLayout;
import com.hayikeji.ddmananger.bean.BaseResult;
import com.hayikeji.ddmananger.bean.DeviceBean;
import com.hayikeji.ddmananger.bean.EItemDetails;
import com.hayikeji.ddmananger.http.OkHttpHeader;
import com.hayikeji.ddmananger.http.ResultCallback2;
import com.hayikeji.ddmananger.info.UrlApi;
import com.hayikeji.ddmananger.ui.adapter.ECloseManagerAdapter;
import com.hayikeji.ddmananger.ui.adapter.bean.ECloseManager;
import com.hayikeji.ddmananger.ui.adapter.bean.IECloseManager;
import com.hayikeji.ddmananger.ui.widget.dialog.EManagerDialog;
import com.hayikeji.ddmananger.utils.DataUtils;
import com.hayikeji.ddmananger.utils.div.DividerItemDecoration;
import com.hayikeji.ddmananger.utils.preferences.UserDevPreferences;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@BindLayout(layoutRes = R.layout.activity_emanager, title = "远程断电")
public class EManagerActivity extends BaseActivity implements BaseQuickAdapter.OnItemChildClickListener, EManagerDialog.OnSwitchStatusListener {

    @BindView(R.id.activity_emanager_et_dev_code)
    EditText etDevCode;
    @BindView(R.id.activity_emanager_to_query)
    View vQuery;
    @BindView(R.id.activity_emanager_rv)
    RecyclerView rv;

    private ECloseManagerAdapter adapter;

    @Override
    public void initWidget() {
        super.initWidget();
        vQuery.setOnClickListener(this);

        adapter = new ECloseManagerAdapter();
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
        rv.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL, 20, Color.GRAY));

        adapter.setOnItemChildClickListener(this);
        refresh(null);
    }


    @Override
    public void forbidClick(View v) {
        super.forbidClick(v);
        switch (v.getId()) {
            case R.id.activity_emanager_to_query:
                Editable text = etDevCode.getText();

                displayLoadingDialog("加载数据");
                refresh(text.toString());
                break;
        }
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()) {
            case R.id.item_e_close_manager_tv_open://开关权限
                IECloseManager ieCloseManager1 = (IECloseManager) (adapter.getData().get(position));
                if (ieCloseManager1.isCanSwitch()) {
                    EManagerDialog eManagerDialog = new EManagerDialog(this);
                    List<EManagerDialog.IEDetails> l = new ArrayList<>();
                    ECloseManager e = (ECloseManager) ieCloseManager1;
                    DeviceBean deviceBean = e.getDeviceBean();
                    replaceEItemDetail(deviceBean, l);
                    eManagerDialog.resetContent(ieCloseManager1, l);
                    eManagerDialog.setOnSwitchListener(this);
                    eManagerDialog.show();

                }
                break;
            case R.id.item_e_close_manager_tv_assign_power://权限分配
                IECloseManager ieCloseManager2 = (IECloseManager) (adapter.getData().get(position));
                if (ieCloseManager2.isHavePowerAssign()) {
                    startActivity(PowerManagerActivity.class);
                }
                break;
        }
    }

    private void replaceEItemDetail(DeviceBean deviceBean, List<EManagerDialog.IEDetails> l) {
        l.add(new EItemDetails("电压", deviceBean.getVoltage() + "", "V"));
        l.add(new EItemDetails("电流", deviceBean.getElectricCurrent() + "", "A"));
        l.add(new EItemDetails("频率", deviceBean.getFrequency() + "", "Hz"));
        l.add(new EItemDetails("漏电流", deviceBean.getLeakageCurrent() + "", "mA"));
        l.add(new EItemDetails("功率", deviceBean.getPower() + "", "kW"));
        l.add(new EItemDetails("功率因数", deviceBean.getPowerFactor() + "", ""));
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
                    return;
                }
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
    public void onSwitchStatus(boolean isOpen) {

    }
}
