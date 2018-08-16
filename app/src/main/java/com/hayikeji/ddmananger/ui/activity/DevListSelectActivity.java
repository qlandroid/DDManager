package com.hayikeji.ddmananger.ui.activity;

import android.graphics.Color;
import android.ql.bindview.BindView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hayikeji.ddmananger.R;
import com.hayikeji.ddmananger.base.BaseActivity;
import com.hayikeji.ddmananger.base.BindLayout;
import com.hayikeji.ddmananger.ui.adapter.DevSelectAdapter;
import com.hayikeji.ddmananger.ui.adapter.IDevDetails;
import com.hayikeji.ddmananger.utils.div.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

@BindLayout(layoutRes = R.layout.content_rv, title = "设备列表")
public class DevListSelectActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.content_rv)
    RecyclerView rv;

    DevSelectAdapter adapter = new DevSelectAdapter();

    @Override
    public void initWidget() {
        super.initWidget();
        List<IDevDetails> i = new ArrayList<>();
        for (int i1 = 0; i1 < 20; i1++) {
            i.add(new EManagerActivity.EDate());
        }
        adapter.setNewData(i);
        
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL, 10, Color.GRAY));
        rv.setAdapter(adapter);
        

        adapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        
    }
}
