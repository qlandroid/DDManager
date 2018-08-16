package com.hayikeji.ddmananger.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.ql.bindview.BindView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hayikeji.ddmananger.R;
import com.hayikeji.ddmananger.base.BaseFragment;
import com.hayikeji.ddmananger.base.BindLayout;
import com.hayikeji.ddmananger.ui.activity.DevListSelectActivity;
import com.hayikeji.ddmananger.ui.activity.HomeActivity;
import com.hayikeji.ddmananger.ui.adapter.IDevDetails;
import com.hayikeji.ddmananger.ui.widget.dialog.BottomDevSelectDialog;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.util.QMUIKeyboardHelper;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUIEmptyView;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;

import java.util.List;

/**
 * 描述：
 * 邮箱 email:strive_bug@yeah.net
 * 创建时间 2018/8/6
 *
 * @author ql
 */
@BindLayout(layoutRes = R.layout.frag_e_details, title = "电量信息", backRes = 0)
public class EDetailsFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, BottomDevSelectDialog.OnSelectDevListener {

    @BindView(R.id.e_details_tv_bind_date)
    TextView tvBindDate;//绑定日期
    @BindView(R.id.e_details_tv_door)
    TextView tvDoor;//门牌号
    @BindView(R.id.e_details_tv_e_num)
    TextView tvENum;//累计电量
    @BindView(R.id.e_details_tv_no)
    TextView tvNo;//设备号
    @BindView(R.id.e_details_tv_setting_name)
    TextView tvSettingName;
    @BindView(R.id.e_details_tv_name)
    TextView tvName;//设备别名
    @BindView(R.id.e_details_tv_owner)
    TextView tvOwner;//户主
    @BindView(R.id.e_details_tv_power)
    TextView tvPower;//权限
    @BindView(R.id.e_details_tv_p_manager)
    TextView tvPManager;//设备人员管理
    @BindView(R.id.e_details_tv_summary)
    TextView tvSummary;//查看电量统计
    @BindView(R.id.e_details_tv_e_price)
    TextView tvEPrice;//总价格
    @BindView(R.id.e_details_tv_month_e_num)
    TextView tvMonthENum;//当前月总电量
    @BindView(R.id.e_details_tv_e_month_price)
    TextView tvMonthEPrice;//当前月总金额
    @BindView(R.id.e_details_tv_yeah_e_num)
    TextView tvYeahENum;//当前年总电量
    @BindView(R.id.e_details_tv_e_yeah_price)
    TextView tvYeahEPrice;//当前年总金额
    @BindView(R.id.e_details_tv_yeah)
    TextView tvYeah;//当前年份
    @BindView(R.id.e_details_tv_month)
    TextView tvMonth;//当前月份
    @BindView(R.id.e_details_tv_unit)
    TextView tvUnit;//当前电量单价
    @BindView(R.id.e_details_tv_price)
    TextView tvPrice;//当前剩余金额
    @BindView(R.id.emptyView)
    QMUIEmptyView emptyView;
    @BindView(R.id.frag_e_details_srl)
    SwipeRefreshLayout srl;

    private BottomDevSelectDialog dialog;
    private IUnBindDev iUnBindDev;



    public static EDetailsFragment newInstance(IUnBindDev iUnBindDev) {

        Bundle args = new Bundle();

        EDetailsFragment fragment = new EDetailsFragment();
        fragment.setiUnBindDev(iUnBindDev);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initData() {
        super.initData();
        dialog = new BottomDevSelectDialog(getContext());
        dialog.setOnSelectDevListener(this);
    }

    @Override
    protected void initWidget(View view) {
        super.initWidget(view);
        tvPManager.setOnClickListener(this);
        Button devSelect = mTopbar.addRightTextButton("设备选择", R.id.top_bar_right_btn);
        devSelect.setOnClickListener(this);
        srl.setOnRefreshListener(this);
        tvSettingName.setOnClickListener(this);
        tvSummary.setOnClickListener(this);
        hideEmptyView();
        /*LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mTopbar.getLayoutParams();
        layoutParams.topMargin = QMUIStatusBarHelper.getStatusbarHeight(getContext());
        mTopbar.setLayoutParams(layoutParams);*/
        ViewParent parent = mTopbar.getParent();
        ViewGroup parentView = (ViewGroup) parent;
        View v = new View(getContext());
        LinearLayout.LayoutParams l = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,QMUIStatusBarHelper.getStatusbarHeight(getContext()));

        v.setLayoutParams(l);
        v.setBackgroundColor(Color.RED);
        parentView.addView(v,0);
    }


    public void setiUnBindDev(IUnBindDev iUnBindDev) {
        this.iUnBindDev = iUnBindDev;
    }

    private void emptyViewLoading() {
        emptyView.show(true, null, "加载中...", null, null);
    }

    private void emptyViewError(String error) {
        emptyView.show(false, "提示", error, "重新加载", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emptyViewLoading();
                onRefresh();
            }
        });
    }

    private void hideEmptyView() {
        emptyView.hide();
    }

    private void toUnBindDevContent() {
        if (iUnBindDev == null) {
            return;
        }
        iUnBindDev.changeUnbindFragment();
    }


    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.e_details_tv_p_manager:
                break;
            case R.id.e_details_tv_summary://查看历史统计
                break;
            case R.id.e_details_tv_setting_name://设置别名
                displaySettingNameDialog();
                break;
            case R.id.top_bar_right_btn://点击选择设备
               //dialog.show();
                startActivity(DevListSelectActivity.class);
                break;
        }
    }

    private void displaySettingNameDialog() {
        QMUIDialog.EditTextDialogBuilder editTextDialogBuilder = new QMUIDialog.EditTextDialogBuilder(getContext());
        final EditText editText = editTextDialogBuilder
                .getEditText();

        editTextDialogBuilder.setTitle("设备名称设置")
                .setPlaceholder("请输入设备名称")
                .addAction("确定", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        Editable text = editText.getText();
                        tvName.setText(text);

                        if (QMUIKeyboardHelper.isKeyboardVisible(getActivity())) {
                            QMUIKeyboardHelper.hideKeyboard(editText);
                        }
                        dialog.cancel();
                    }
                }).addAction("取消", new QMUIDialogAction.ActionListener() {
            @Override
            public void onClick(QMUIDialog dialog, int index) {
                FragmentActivity activity = getActivity();
                if (activity instanceof IUnBindDev) {
                    ((HomeActivity) activity).changeUnbindFragment();
                }
                if (QMUIKeyboardHelper.isKeyboardVisible(getActivity())) {
                    QMUIKeyboardHelper.hideKeyboard(editText);
                }
                dialog.cancel();
            }
        });
        editTextDialogBuilder.show();
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onSelectDev(List<IDevDetails> data, int position) {

    }
}
