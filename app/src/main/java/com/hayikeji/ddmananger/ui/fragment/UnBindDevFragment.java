package com.hayikeji.ddmananger.ui.fragment;

import android.ql.bindview.BindView;
import android.view.View;
import android.widget.TextView;

import com.hayikeji.ddmananger.R;
import com.hayikeji.ddmananger.base.BaseFragment;
import com.hayikeji.ddmananger.base.BindLayout;

/**
 * 描述：
 * 邮箱 email:strive_bug@yeah.net
 * 创建时间 2018/8/7
 *
 * @author ql
 */
@BindLayout(layoutRes = R.layout.item_bind_e, title = "未绑定设备",backRes = 0)
public class UnBindDevFragment extends BaseFragment {

    @BindView(R.id.item_bind_e_tv_to_bind)
    TextView tvToBind;

    @Override
    protected void initWidget(View view) {
        super.initWidget(view);
        tvToBind.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.item_bind_e_tv_to_bind:
                break;
        }
    }
}
