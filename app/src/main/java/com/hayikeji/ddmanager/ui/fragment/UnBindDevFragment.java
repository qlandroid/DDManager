package com.hayikeji.ddmanager.ui.fragment;

import android.graphics.Color;
import android.ql.bindview.BindView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hayikeji.ddmanager.R;
import com.hayikeji.ddmanager.base.BaseFragment;
import com.hayikeji.ddmanager.base.BindLayout;
import com.hayikeji.ddmanager.ui.activity.bind.BindDevActivity;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;

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
        initTopbar();
    }

    private void initTopbar() {
        ViewParent parent = mTopbar.getParent();
        ViewGroup parentView = (ViewGroup) parent;
        View v = new View(getContext());
        LinearLayout.LayoutParams l = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, QMUIStatusBarHelper.getStatusbarHeight(getContext()));

        v.setLayoutParams(l);
        v.setBackgroundColor(Color.WHITE);
        parentView.addView(v, 0);
    }


    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.item_bind_e_tv_to_bind:
                startActivity(BindDevActivity.class);
                break;
        }
    }
}
