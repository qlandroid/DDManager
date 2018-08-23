package com.hayikeji.ddmananger.ui.fragment;

import android.os.Bundle;
import android.ql.bindview.BindView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.hayikeji.ddmananger.R;
import com.hayikeji.ddmananger.base.BaseFragment;
import com.hayikeji.ddmananger.base.BindLayout;
import com.hayikeji.ddmananger.bean.BaseResult;
import com.hayikeji.ddmananger.bean.EUseBean;
import com.hayikeji.ddmananger.http.OkHttpHeader;
import com.hayikeji.ddmananger.http.ResultCallback2;
import com.hayikeji.ddmananger.info.UrlApi;
import com.hayikeji.ddmananger.ui.activity.EUseRecordActivity;
import com.hayikeji.ddmananger.ui.adapter.EUseMonthAdapter;
import com.hayikeji.ddmananger.utils.DataUtils;
import com.hayikeji.ddmananger.utils.preferences.UserDevPreferences;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 描述：
 * 邮箱 email:strive_bug@yeah.net
 * 创建时间 2018/8/17
 *
 * @author ql
 */
@BindLayout(layoutRes = R.layout.frag_e_use_record_month, bindTopBar = false)
public class EUseRecordMonthFragment extends BaseFragment implements EUseRecordActivity.IRefresh {

    @BindView(R.id.frag_e_use_record_month_tv)
    View vSelectDate;
    @BindView(R.id.frag_e_use_record_month_rv)
    RecyclerView rv;
    @BindView(R.id.frag_e_use_record_month_tv_date)
    TextView tvSelectDate;

    TimePickerView pvTime;

    private int year, month;

    EUseMonthAdapter eUseMonthAdapter;

    public static EUseRecordMonthFragment newInstance() {

        Bundle args = new Bundle();

        EUseRecordMonthFragment fragment = new EUseRecordMonthFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initWidget(View view) {
        super.initWidget(view);
        vSelectDate.setOnClickListener(this);
        //时间选择器
        pvTime = new TimePickerBuilder(getContext(), new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                Calendar c = Calendar.getInstance();
                c.setTime(date);
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH) + 1;
                loadMonth(UserDevPreferences.getSelectDev(getContext()), year, month);
                tvSelectDate.setText(year + "年" + month + "月");
            }
        }).setType(new boolean[]{true, true, false, false, false, false}).build();
        eUseMonthAdapter = new EUseMonthAdapter();
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));
        rv.setAdapter(eUseMonthAdapter);
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH) + 1;
        tvSelectDate.setText(year + "年" + month + "月");
        loadMonth(UserDevPreferences.getSelectDev(getContext()), year, month);
    }

    private void loadMonth(int devId, int year, int month) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", UserDevPreferences.getUserId(getContext()));
        map.put("year", year);
        map.put("month", month);
        map.put("type", "1");
        map.put("devId", devId);

        OkHttpHeader.post(UrlApi.user_e, map, new ResultCallback2() {
            @Override
            protected void onFailed(String error, int code) {
                displayMessageDialog(error);
            }

            @Override
            protected void onSuccess(BaseResult response, int id) {
                if (!response.isSuccess()) {
                    displayMessageDialog(response.getMessage());
                    return;
                }

                ArrayList arrayResult = DataUtils.getArrayResult(response.getList(), EUseBean.class);
                eUseMonthAdapter.setNewData(arrayResult);
                eUseMonthAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.frag_e_use_record_month_tv:
                pvTime.show();
                break;
        }
    }

    @Override
    public void refresh() {
        loadMonth(UserDevPreferences.getSelectDev(getContext()), year, month);
    }
}
