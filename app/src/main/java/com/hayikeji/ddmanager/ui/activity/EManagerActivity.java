package com.hayikeji.ddmanager.ui.activity;

import android.graphics.Color;
import android.media.midi.MidiManager;
import android.ql.bindview.BindView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hayikeji.ddmanager.R;
import com.hayikeji.ddmanager.base.BaseActivity;
import com.hayikeji.ddmanager.base.BindLayout;
import com.hayikeji.ddmanager.bean.BaseResult;
import com.hayikeji.ddmanager.bean.DeviceBean;
import com.hayikeji.ddmanager.bean.EItemDetails;
import com.hayikeji.ddmanager.http.OkHttpHeader;
import com.hayikeji.ddmanager.http.ResultCallback2;
import com.hayikeji.ddmanager.info.UrlApi;
import com.hayikeji.ddmanager.ui.adapter.ECloseManagerAdapter;
import com.hayikeji.ddmanager.ui.adapter.bean.ECloseManager;
import com.hayikeji.ddmanager.ui.adapter.bean.IECloseManager;
import com.hayikeji.ddmanager.ui.widget.dialog.EManagerDialog;
import com.hayikeji.ddmanager.utils.DataUtils;
import com.hayikeji.ddmanager.utils.DateUtils;
import com.hayikeji.ddmanager.utils.div.DividerItemDecoration;
import com.hayikeji.ddmanager.utils.preferences.UserDevPreferences;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@BindLayout(layoutRes = R.layout.activity_emanager, title = "远程断电")
public class EManagerActivity extends BaseActivity implements BaseQuickAdapter.OnItemChildClickListener, EManagerDialog.OnSwitchStatusListener, BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.activity_emanager_et_dev_code)
    EditText etDevCode;
    @BindView(R.id.activity_emanager_to_query)
    View vQuery;
    @BindView(R.id.activity_emanager_rv)
    RecyclerView rv;

    private ECloseManagerAdapter adapter;
    private EManagerDialog eManagerDialog;

    private DeviceBean selectDev;

    @Override
    public void initWidget() {
        super.initWidget();
        vQuery.setOnClickListener(this);

        adapter = new ECloseManagerAdapter();
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
        rv.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL, 20, Color.GRAY));

        adapter.setOnItemChildClickListener(this);
        adapter.setOnItemClickListener(this);
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
                checkUserVipByDev((ECloseManager) adapter.getData().get(position));
                break;
            case R.id.item_e_close_manager_tv_assign_power://权限分配
                IECloseManager ieCloseManager2 = (IECloseManager) (adapter.getData().get(position));
                if (ieCloseManager2.isHavePowerAssign()) {
                    startActivity(PowerManagerActivity.class);
                }
                break;
        }
    }

    private void checkUserVipByDev(ECloseManager eCloseManager) {
        final ECloseManager closeManager = eCloseManager;
        Map<String, Object> map = new HashMap<>();
        map.put("userId", UserDevPreferences.getUserId(this));
        map.put("devId", eCloseManager.getDeviceBean().getId());

        displayLoadingDialog("检测权限");
        OkHttpHeader.post(UrlApi.check_vip, map, new ResultCallback2() {
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
                if (closeManager.isCanSwitch()) {
                    if (eManagerDialog == null) {
                        eManagerDialog = new EManagerDialog(EManagerActivity.this);
                    }
                    List<EManagerDialog.IEDetails> l = new ArrayList<>();
                    DeviceBean deviceBean = closeManager.getDeviceBean();
                    selectDev = deviceBean;
                    replaceEItemDetail(deviceBean, l);
                    String stringDate4 = DateUtils.getStringDate4(deviceBean.getUpdatetime() * 1000L);
                    eManagerDialog.resetContent(stringDate4, closeManager, l);
                    eManagerDialog.setOnSwitchListener(EManagerActivity.this);
                    eManagerDialog.show();
                }

            }
        });
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
                    ECloseManager e = new ECloseManager(deviceBean);
                    e.setShowSwitch(true);
                    e.setCanSwitch(true);
                    l.add(e);
                }
                adapter.setNewData(l);
                adapter.notifyDataSetChanged();

            }
        });
    }

    @Override
    public void onSwitchStatus(boolean isOpen) {

        String hint = isOpen ? "开启中" : "关闭中";

        eManagerDialog.hide();
        displayLoadingDialog(hint);
        final int runStatus = isOpen ? 1 : 0;
        Map<String, Object> map = new HashMap<>();
        map.put("runStatus", runStatus);
        map.put("userId", UserDevPreferences.getUserId(this));
        map.put("devId", selectDev.getId());
        OkHttpHeader.post(UrlApi.dev_set_run_status, map, new ResultCallback2() {
            @Override
            protected void onFailed(String error, int code) {
                cancelLoadingDialog();
                displayMessageDialog(error);
            }

            @Override
            protected void onSuccess(BaseResult response, int id) {
                cancelLoadingDialog();
                if (response.isSuccess()) {
                    boolean isOpen = 1 == runStatus;
                    String s = isOpen ? "开启成功" : "关闭成功";
                    displayTipDialogSuccess(s);
                    refresh(null);
                    return;
                }

                displayMessageDialog(response.getMessage());
            }
        });

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
