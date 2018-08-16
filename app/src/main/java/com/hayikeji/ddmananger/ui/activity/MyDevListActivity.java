package com.hayikeji.ddmananger.ui.activity;

import android.graphics.Color;
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
import com.hayikeji.ddmananger.ui.adapter.ECloseManagerAdapter;
import com.hayikeji.ddmananger.ui.adapter.IECloseManager;
import com.hayikeji.ddmananger.ui.widget.dialog.DevDetailsDialog;
import com.hayikeji.ddmananger.ui.widget.dialog.EManagerDialog;
import com.hayikeji.ddmananger.utils.div.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

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
        List<IECloseManager> l = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            l.add(new EDate());
        }
        adapter.setNewData(l);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        DevDetailsDialog dialog = new DevDetailsDialog(this);
        dialog.show();
    }

    public static class EDate implements IECloseManager {

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
        public boolean isShowPower() {
            return false;
        }

        @Override
        public boolean isShowSwitch() {
            return false;
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


}
