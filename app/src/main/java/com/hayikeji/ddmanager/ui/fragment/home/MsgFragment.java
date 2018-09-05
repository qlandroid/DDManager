package com.hayikeji.ddmanager.ui.fragment.home;

import android.os.Bundle;
import android.ql.bindview.BindView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hayikeji.ddmanager.R;
import com.hayikeji.ddmanager.base.BaseFragment;
import com.hayikeji.ddmanager.base.BindLayout;
import com.hayikeji.ddmanager.bean.BaseResult;
import com.hayikeji.ddmanager.bean.WarnBean;
import com.hayikeji.ddmanager.bean.base.Page;
import com.hayikeji.ddmanager.http.OkHttpHeader;
import com.hayikeji.ddmanager.http.ResultCallback2;
import com.hayikeji.ddmanager.info.UrlApi;
import com.hayikeji.ddmanager.ui.IGrid;
import com.hayikeji.ddmanager.ui.adapter.GridAdapter;
import com.hayikeji.ddmanager.ui.adapter.HomeContentAdapter;
import com.hayikeji.ddmanager.utils.DataUtils;
import com.hayikeji.ddmanager.utils.ViewUtils;
import com.hayikeji.ddmanager.utils.preferences.UserDevPreferences;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述：
 * 邮箱 email:strive_bug@yeah.net
 * 创建时间 2018/8/15
 *
 * @author ql
 */
@BindLayout(layoutRes = R.layout.content_rv, bindTopBar = false)
public class MsgFragment extends BaseFragment {
    @BindView(R.id.content_rv)
    RecyclerView rv;

    HomeContentAdapter homeContentAdapter;

    private List warnList = new ArrayList();
    View loadEndView;

    public static MsgFragment newInstance(String type) {

        Bundle args = new Bundle();
        args.putString("type", type);
        MsgFragment fragment = new MsgFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private String type;


    @Override
    protected void initWidget(View view) {
        super.initWidget(view);
        loadEndView = ViewUtils.getLoadEndView(getContext());
        homeContentAdapter = new HomeContentAdapter();
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        rv.setAdapter(homeContentAdapter);
        type = getArguments().getString("type");
        loadMore();
    }


    private void loadMore() {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", UserDevPreferences.getUserId(getContext()));
        map.put("type", type);
        homeContentAdapter.setEnableLoadMore(false);
        OkHttpHeader.post(UrlApi.error_warn, map, new ResultCallback2() {
            @Override
            protected void onFailed(String error, int code) {
                displayMessageDialog(error);
                homeContentAdapter.setEnableLoadMore(true);

            }

            @Override
            protected void onSuccess(BaseResult response, int id) {
                homeContentAdapter.setEnableLoadMore(true);

                if (!response.isSuccess()) {
                    return;
                }

                ArrayList<WarnBean> arrayResult = DataUtils.getArrayResult(response.getList(), WarnBean.class);
                if (arrayResult== null) {
                    homeContentAdapter.setEnableLoadMore(false);

                    homeContentAdapter.addFooterView(loadEndView);
                    homeContentAdapter.loadMoreEnd();
                    return;
                }
                warnList.clear();
                warnList.addAll(arrayResult);
                homeContentAdapter.setNewData(warnList);
                homeContentAdapter.notifyDataSetChanged();
                homeContentAdapter.setEnableLoadMore(false);

                homeContentAdapter.addFooterView(loadEndView);
                homeContentAdapter.loadMoreEnd();
            }
        });
    }

}


