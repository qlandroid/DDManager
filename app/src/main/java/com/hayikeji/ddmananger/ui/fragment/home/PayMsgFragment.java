package com.hayikeji.ddmananger.ui.fragment.home;

import android.graphics.Color;
import android.os.Bundle;
import android.ql.bindview.BindView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hayikeji.ddmananger.R;
import com.hayikeji.ddmananger.base.BaseFragment;
import com.hayikeji.ddmananger.base.BindLayout;
import com.hayikeji.ddmananger.bean.BaseResult;
import com.hayikeji.ddmananger.bean.TransationDocBean;
import com.hayikeji.ddmananger.bean.base.Page;
import com.hayikeji.ddmananger.http.OkHttpHeader;
import com.hayikeji.ddmananger.http.ResultCallback2;
import com.hayikeji.ddmananger.info.UrlApi;
import com.hayikeji.ddmananger.ui.adapter.PayMsgAdapter;
import com.hayikeji.ddmananger.utils.DataUtils;
import com.hayikeji.ddmananger.utils.ViewUtils;
import com.hayikeji.ddmananger.utils.div.DividerItemDecoration;
import com.hayikeji.ddmananger.utils.preferences.UserDevPreferences;

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
public class PayMsgFragment extends BaseFragment implements BaseQuickAdapter.RequestLoadMoreListener {
    @BindView(R.id.content_rv)
    RecyclerView rv;

    PayMsgAdapter payMsgAdapter;

    Page page = new Page();
    private List warnList = new ArrayList();
    View loadEndView;

    public static PayMsgFragment newInstance() {

        Bundle args = new Bundle();

        PayMsgFragment fragment = new PayMsgFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected void initWidget(View view) {
        super.initWidget(view);
        loadEndView = ViewUtils.getLoadEndView(getContext());
        payMsgAdapter = new PayMsgAdapter();
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL, 10, Color.GRAY));
        rv.setAdapter(payMsgAdapter);

        payMsgAdapter.setOnLoadMoreListener(this, rv);
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
        payMsgAdapter.setEnableLoadMore(false);
        OkHttpHeader.post(UrlApi.getTransaction, map, new ResultCallback2() {
            @Override
            protected void onFailed(String error, int code) {
                displayMessageDialog(error);
                payMsgAdapter.setEnableLoadMore(true);

            }

            @Override
            protected void onSuccess(BaseResult response, int id) {
                payMsgAdapter.setEnableLoadMore(true);

                if (!response.isSuccess()) {
                    return;
                }

                ArrayList<TransationDocBean> arrayResult = DataUtils.getArrayResult(response.getList(), TransationDocBean.class);
                if (page.getPageNum() == 1) {
                    warnList.clear();
                }
                if (arrayResult == null) {

                } else {
                    warnList.addAll(arrayResult);
                }

                payMsgAdapter.setNewData(warnList);
                payMsgAdapter.notifyDataSetChanged();
                if (response.getPageSizes() <= page.getPageNum()) {
                    payMsgAdapter.setEnableLoadMore(false);

                    payMsgAdapter.addFooterView(loadEndView);
                    payMsgAdapter.loadMoreEnd();
                }
            }
        });
    }
}
