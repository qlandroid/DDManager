package com.hayikeji.ddmananger.ui.activity;

import android.ql.bindview.BindView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hayikeji.ddmananger.R;
import com.hayikeji.ddmananger.base.BaseActivity;
import com.hayikeji.ddmananger.base.BindLayout;
import com.hayikeji.ddmananger.ui.adapter.IDevDetails;
import com.hayikeji.ddmananger.ui.adapter.IPower;
import com.hayikeji.ddmananger.ui.adapter.PowerManagerAdapter;
import com.hayikeji.ddmananger.ui.widget.dialog.BottomDevSelectDialog;

import java.util.List;

@BindLayout(layoutRes = R.layout.activity_power_manager, title = "权限管理")
public class PowerManagerActivity extends BaseActivity implements BaseQuickAdapter.OnItemChildClickListener, BottomDevSelectDialog.OnSelectDevListener {

    @BindView(R.id.activity_power_manager_tv_select_dev)
    View vSelectDev;
    @BindView(R.id.activity_power_manager_tv_dev_code)
    TextView tvDevCode;
    @BindView(R.id.activity_power_manager_tv_owner)
    TextView tvOwner;
    @BindView(R.id.activity_power_manager_tv_room)
    TextView tvRoom;
    @BindView(R.id.activity_power_manager_tv_power)
    TextView tvPower;//权限
    @BindView(R.id.activity_power_manager_et_name)
    EditText etName;
    @BindView(R.id.activity_power_manager_et_phone)
    EditText etPhone;
    @BindView(R.id.activity_power_manager_tv_query)
    View vQuery;
    @BindView(R.id.activity_power_manager_rv)
    RecyclerView rv;


    private PowerManagerAdapter adapter;

    @Override
    public void initWidget() {
        super.initWidget();
        vQuery.setOnClickListener(this);
        vSelectDev.setOnClickListener(this);

        adapter = new PowerManagerAdapter();
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);

        adapter.setOnItemChildClickListener(this);


    }

    @Override
    public void forbidClick(View v) {
        super.forbidClick(v);
        switch (v.getId()) {
            case R.id.activity_power_manager_tv_select_dev://选择设备
                BottomDevSelectDialog d = new BottomDevSelectDialog(this);
                d.setDevList(null);
                d.setOnSelectDevListener(this);
                d.show();
                break;
            case R.id.activity_power_manager_tv_query://查询
                break;
        }
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()) {
            case R.id.item_power_tv_setting:
                IPower iPower = (IPower) (adapter.getData().get(position));
                if (iPower.isHavePower()) {

                }
                break;
        }
    }

    @Override
    public void onSelectDev(List<IDevDetails> data, int position) {

    }
}
