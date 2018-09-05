package com.hayikeji.ddmanager.ui.fragment.home;

import android.os.Bundle;
import android.ql.bindview.BindView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hayikeji.ddmanager.R;
import com.hayikeji.ddmanager.base.BaseFragment;
import com.hayikeji.ddmanager.base.BindLayout;
import com.hayikeji.ddmanager.bean.BaseResult;
import com.hayikeji.ddmanager.bean.WarnBean;
import com.hayikeji.ddmanager.bean.base.Page;
import com.hayikeji.ddmanager.http.OkHttpHeader;
import com.hayikeji.ddmanager.http.ResultCallback2;
import com.hayikeji.ddmanager.info.UrlApi;
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
 * 创建时间 2018/8/19
 *
 * @author ql
 */
@BindLayout(layoutRes = R.layout.content_rv, bindTopBar = false)
public class WarnMsgFragment extends BaseFragment implements BaseQuickAdapter.RequestLoadMoreListener {
    @BindView(R.id.content_rv)
    RecyclerView rv;

    HomeContentAdapter homeContentAdapter;

    Page page = new Page();
    private List warnList = new ArrayList();
    View loadEndView;

    public static WarnMsgFragment newInstance() {

        Bundle args = new Bundle();

        WarnMsgFragment fragment = new WarnMsgFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected void initWidget(View view) {
        super.initWidget(view);
        loadEndView = ViewUtils.getLoadEndView(getContext());
        homeContentAdapter = new HomeContentAdapter();
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        rv.setAdapter(homeContentAdapter);

        homeContentAdapter.setOnLoadMoreListener(this, rv);
        loadMore();
    }


    @Override
    public void onLoadMoreRequested() {
        page.addPageNum();
        loadMore();
    }

    private void loadMore() {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", UserDevPreferences.getUserId(getContext()));
        page.addPage(map);
        homeContentAdapter.setEnableLoadMore(false);
        OkHttpHeader.post(UrlApi.warn_msg, map, new ResultCallback2() {
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
                if (page.getPageNum() == 1) {
                    warnList.clear();
                }
                if (arrayResult != null) {

                    warnList.addAll(arrayResult);
                }
                homeContentAdapter.setNewData(warnList);
                homeContentAdapter.notifyDataSetChanged();
                if (response.getPageSizes() <= page.getPageNum()) {
                    homeContentAdapter.setEnableLoadMore(false);

                    homeContentAdapter.addFooterView(loadEndView);
                    homeContentAdapter.loadMoreEnd();
                }
            }
        });
    }
}
