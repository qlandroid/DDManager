package com.hayikeji.ddmananger.ui.activity;

import android.graphics.Color;
import android.ql.bindview.BindView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hayikeji.ddmananger.R;
import com.hayikeji.ddmananger.base.BaseActivity;
import com.hayikeji.ddmananger.base.BindLayout;
import com.hayikeji.ddmananger.ui.adapter.DevSelectAdapter;
import com.hayikeji.ddmananger.utils.div.DividerItemDecoration;

@BindLayout(layoutRes = R.layout.content_rv, title = "设备列表")
public class DevListSelectActivity extends BaseActivity {

    @BindView(R.id.content_rv)
    RecyclerView rv;

    DevSelectAdapter adapter = new DevSelectAdapter();

    @Override
    public void initWidget() {
        super.initWidget();
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL, 10, Color.GRAY));
        rv.setAdapter(adapter);
    }
}
