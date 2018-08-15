package com.hayikeji.ddmananger.ui.fragment;

import android.os.Bundle;
import android.ql.bindview.BindView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hayikeji.ddmananger.R;
import com.hayikeji.ddmananger.base.BaseFragment;
import com.hayikeji.ddmananger.base.BindLayout;
import com.hayikeji.ddmananger.ui.IGrid;
import com.hayikeji.ddmananger.ui.adapter.GridAdapter;

import java.util.ArrayList;
import java.util.List;

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


    public static MsgFragment newInstance(int i) {

        Bundle args = new Bundle();

        MsgFragment fragment = new MsgFragment();
        args.putInt("dd",i);
        fragment.setArguments(args);

        return fragment;
    }


    @Override
    protected void initWidget(View view) {
        super.initWidget(view);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        GridAdapter adapter = new GridAdapter();
        List<IGrid> s = new ArrayList<>();
        int i = getArguments().getInt("dd");
        for (int i1 = 0; i1 < i; i1++) {
            s.add(new MyFragment.GridNavBean(2,"ddd"+i1,R.drawable.icon_back));
        }
        adapter.setNewData(s);
        rv.setAdapter(adapter);
    }

}


