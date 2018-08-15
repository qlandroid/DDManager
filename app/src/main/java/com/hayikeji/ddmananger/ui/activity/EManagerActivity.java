package com.hayikeji.ddmananger.ui.activity;

import android.graphics.Color;
import android.ql.bindview.BindView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hayikeji.ddmananger.R;
import com.hayikeji.ddmananger.base.BaseActivity;
import com.hayikeji.ddmananger.base.BindLayout;
import com.hayikeji.ddmananger.ui.adapter.ECloseManagerAdapter;
import com.hayikeji.ddmananger.ui.adapter.IECloseManager;
import com.hayikeji.ddmananger.ui.widget.dialog.EManagerDialog;
import com.hayikeji.ddmananger.utils.div.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

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
        rv.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL,20, Color.GRAY));

        adapter.setOnItemChildClickListener(this);
        List<IECloseManager> l = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            l.add(new EDate());
        }
        adapter.setNewData(l);
        adapter.notifyDataSetChanged();
    }

    public static class  EDate implements IECloseManager{

        @Override
        public CharSequence getPower() {
            return "";
        }

        @Override
        public boolean isHavePowerAssign() {
            return true;
        }

        @Override
        public boolean isCanSwitch() {
            return true;
        }

        @Override
        public boolean isRun() {
            return true;
        }

        @Override
        public CharSequence getDevNo() {
            return "123";
        }

        @Override
        public CharSequence getDevName() {
            return "123";
        }

        @Override
        public CharSequence getDevRoom() {
            return "123";
        }

        @Override
        public CharSequence getDevOwner() {
            return "222";
        }
    }

    @Override
    public void forbidClick(View v) {
        super.forbidClick(v);
        switch (v.getId()) {
            case R.id.activity_emanager_to_query:
                etDevCode.getText();
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
                    addTestDate(l);
                    eManagerDialog.resetContent(new EDate(), l);
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

    private void addTestDate(List<EManagerDialog.IEDetails> l) {
        l.add(new EManagerDialog.IEDetails() {
            @Override
            public CharSequence getLabel() {
                return "";
            }

            @Override
            public CharSequence getValue() {
                return "";
            }

            @Override
            public CharSequence getUnit() {
                return "";
            }
        });
        l.add(new EManagerDialog.IEDetails() {
            @Override
            public CharSequence getLabel() {
                return "";
            }

            @Override
            public CharSequence getValue() {
                return "";
            }

            @Override
            public CharSequence getUnit() {
                return "";
            }
        });
        l.add(new EManagerDialog.IEDetails() {
            @Override
            public CharSequence getLabel() {
                return "";
            }

            @Override
            public CharSequence getValue() {
                return "";
            }

            @Override
            public CharSequence getUnit() {
                return "";
            }
        });
        l.add(new EManagerDialog.IEDetails() {
            @Override
            public CharSequence getLabel() {
                return "";
            }

            @Override
            public CharSequence getValue() {
                return "";
            }

            @Override
            public CharSequence getUnit() {
                return "";
            }
        });
    }

    @Override
    public void onSwitchStatus(boolean isOpen) {

    }
}
