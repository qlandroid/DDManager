package com.hayikeji.ddmanager.ui.shopping;

import android.os.Bundle;
import android.ql.bindview.BindView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hayikeji.ddmanager.R;
import com.hayikeji.ddmanager.base.BaseActivity;
import com.hayikeji.ddmanager.base.BindLayout;
import com.hayikeji.ddmanager.bean.BaseResult;
import com.hayikeji.ddmanager.bean.base.Page;
import com.hayikeji.ddmanager.http.OkHttpHeader;
import com.hayikeji.ddmanager.http.ResultCallback2;
import com.hayikeji.ddmanager.info.UrlApi;
import com.hayikeji.ddmanager.ui.shopping.adapter.GoodsAdapter;
import com.hayikeji.ddmanager.utils.ViewUtils;
import com.hayikeji.ddmanager.utils.div.GridDivider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@BindLayout(layoutRes = R.layout.activity_shopping_home, title = "商城")
public class ShoppingHomeActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.RequestLoadMoreListener {


    @BindView(R.id.activity_shopping_home_rv)
    RecyclerView rv;

    private Page page = new Page();
    private GoodsAdapter goodsAdapter = new GoodsAdapter();
    private List list = new ArrayList();

    @Override
    public void initWidget() {
        super.initWidget();
        rv.setLayoutManager(new GridLayoutManager(this, 2));
        rv.addItemDecoration(new GridDivider(this, 10, R.color.gray));
        rv.setAdapter(goodsAdapter);

        goodsAdapter.setOnItemClickListener(this);

        goodsAdapter.setOnLoadMoreListener(this, rv);
        displayLoadingDialog("加载中");
        loadMore();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Bundle bundle = new Bundle();
        GoodsBean o = (GoodsBean) list.get(position);
        ShoppingGoodsActivity.put(o.getId(), bundle);
        startActivity(ShoppingGoodsActivity.class, bundle);
    }

    private void loadMore() {
        goodsAdapter.setEnableLoadMore(false);
        Map<String, Object> map = new HashMap<>();
        page.addPage(map);
        OkHttpHeader.post(UrlApi.shopping_goods_list, map, new ResultCallback2() {
            @Override
            protected void onFailed(String error, int code) {
                cancelLoadingDialog();
                displayMessageDialog(error);
                goodsAdapter.loadMoreFail();
            }

            @Override
            protected void onSuccess(BaseResult response, int id) {
                cancelLoadingDialog();
                if (!response.isSuccess()) {
                    displayMessageDialog(response.getMessage());
                    return;
                }
                if (page.getPageNum() == 1) {
                    list.clear();
                }
                List listO = response.getListO(GoodsBean.class);
                if (listO != null) {
                    list.addAll(listO);
                }
                goodsAdapter.setNewData(list);
                if (response.checkLoadEnd(page)) {
                    goodsAdapter.loadMoreEnd();
                    return;
                }
                page.addPageNum();
                goodsAdapter.notifyDataSetChanged();
                goodsAdapter.setEnableLoadMore(true);
            }
        });

    }

    @Override
    public void onLoadMoreRequested() {
        loadMore();
    }
}
