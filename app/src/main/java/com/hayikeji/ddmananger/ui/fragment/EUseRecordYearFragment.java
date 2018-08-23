package com.hayikeji.ddmananger.ui.fragment;

import android.os.Bundle;
import android.ql.bindview.BindView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hayikeji.ddmananger.R;
import com.hayikeji.ddmananger.base.BaseFragment;
import com.hayikeji.ddmananger.base.BindLayout;
import com.hayikeji.ddmananger.bean.BaseResult;
import com.hayikeji.ddmananger.bean.EUseBean;
import com.hayikeji.ddmananger.http.OkHttpHeader;
import com.hayikeji.ddmananger.http.ResultCallback2;
import com.hayikeji.ddmananger.info.UrlApi;
import com.hayikeji.ddmananger.ui.activity.EUseRecordActivity;
import com.hayikeji.ddmananger.ui.adapter.EUseRecordYearAdapter;
import com.hayikeji.ddmananger.utils.DataUtils;
import com.hayikeji.ddmananger.utils.preferences.UserDevPreferences;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 描述：
 * 邮箱 email:strive_bug@yeah.net
 * 创建时间 2018/8/20
 *
 * @author ql
 */
@BindLayout(layoutRes = R.layout.content_rv, bindTopBar = false)
public class EUseRecordYearFragment extends BaseFragment implements EUseRecordActivity.IRefresh {

    @BindView(R.id.content_rv)
    RecyclerView rv;

    EUseRecordYearAdapter eUseRecordYearAdapter;

    public static EUseRecordYearFragment newInstance() {
        
        Bundle args = new Bundle();
        
        EUseRecordYearFragment fragment = new EUseRecordYearFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initWidget(View view) {
        super.initWidget(view);
        eUseRecordYearAdapter = new EUseRecordYearAdapter();

        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        rv.setAdapter(eUseRecordYearAdapter);

        loadYear(UserDevPreferences.getSelectDev(getContext()));
    }

    private void loadYear(int devId) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", UserDevPreferences.getUserId(getContext()));
        map.put("type", "2");//1- month 2-year
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
                eUseRecordYearAdapter.setNewData(arrayResult);
                eUseRecordYearAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void refresh() {
        loadYear(UserDevPreferences.getSelectDev(getContext()));
    }
}
