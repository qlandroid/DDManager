package com.hayikeji.ddmananger.ui.fragment.home;

import android.app.Activity;
import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hayikeji.ddmananger.R;
import com.hayikeji.ddmananger.base.BaseFragment;
import com.hayikeji.ddmananger.base.BindLayout;
import com.hayikeji.ddmananger.bean.BaseResult;
import com.hayikeji.ddmananger.bean.DeviceBean;
import com.hayikeji.ddmananger.bean.EDetails;
import com.hayikeji.ddmananger.http.OkHttpHeader;
import com.hayikeji.ddmananger.http.OkHttpHelper;
import com.hayikeji.ddmananger.http.ResultCallback2;
import com.hayikeji.ddmananger.info.UrlApi;
import com.hayikeji.ddmananger.ui.activity.DevListSelectActivity;
import com.hayikeji.ddmananger.ui.activity.EUseRecordActivity;
import com.hayikeji.ddmananger.ui.activity.HomeActivity;
import com.hayikeji.ddmananger.ui.activity.PowerManagerActivity;
import com.hayikeji.ddmananger.ui.adapter.bean.IDevDetails;
import com.hayikeji.ddmananger.ui.fragment.IUnBindDev;
import com.hayikeji.ddmananger.ui.widget.dialog.BottomDevSelectDialog;
import com.hayikeji.ddmananger.utils.DataUtils;
import com.hayikeji.ddmananger.utils.DateUtils;
import com.hayikeji.ddmananger.utils.preferences.UserDevPreferences;
import com.qmuiteam.qmui.util.QMUIKeyboardHelper;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUIEmptyView;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述：
 * 邮箱 email:strive_bug@yeah.net
 * 创建时间 2018/8/6
 *
 * @author ql
 */
@BindLayout(layoutRes = R.layout.frag_e_details, title = "电量信息", backRes = 0)
public class EDetailsFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, BottomDevSelectDialog.OnSelectDevListener {
    private final int REQUEST_SELECT_DEV = 1234;


    @BindView(R.id.e_details_tv_bind_date)
    TextView tvBindDate;//绑定日期
    @BindView(R.id.e_details_tv_door)
    TextView tvDoor;//门牌号
    @BindView(R.id.e_details_tv_e_num)
    TextView tvENum;//累计电量
    @BindView(R.id.e_details_tv_code)
    TextView tvCode;//设备号
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
    @BindView(R.id.e_details_tv_month_e_num)
    TextView tvMonthENum;//当前月总电量
    @BindView(R.id.e_details_tv_yeah_e_num)
    TextView tvYearENum;//当前年总电量
    @BindView(R.id.e_details_tv_year)
    TextView tvYear;//当前年份
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

        initTopbar();
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

        displayLoadingDialog("加载数据中");

        int selectDev = UserDevPreferences.getSelectDev(getContext());
        if (selectDev == -1) {
            checkHasDev();
        } else {
            refresh();
        }
    }

    private void checkHasDev() {
        //加载设备
        Map<String, Object> map = new HashMap<>();
        map.put("userId", UserDevPreferences.getUserId(getContext()));
        OkHttpHelper.post(UrlApi.dev_list, map, new ResultCallback2() {
            @Override
            protected void onFailed(String error, int code) {
                cancelLoadingDialog();
                displayMessageDialog(error);
            }

            @Override
            protected void onSuccess(BaseResult response, int id) {
                cancelLoadingDialog();
                if (!response.isSuccess()) {
                    displayMessageDialog(response.getCode() + response.getMessage());
                    return;
                }
                ArrayList<DeviceBean> arrayResult = DataUtils.getArrayResult(response.getList(), DeviceBean.class);
                if (arrayResult == null || arrayResult.size() == 0) {
                    UserDevPreferences.saveIsHasDev(getContext(), false);
                    iUnBindDev.changeUnbindFragment();
                } else {
                    UserDevPreferences.saveIsHasDev(getContext(), true);
                    UserDevPreferences.saveSelectDev(getContext(), arrayResult.get(0).getId());
                    refresh();
                }

            }
        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            displayLoadingDialog("加载数据中");
            int selectDev = UserDevPreferences.getSelectDev(getContext());
            if (selectDev == -1) {
                checkHasDev();
            } else {
                refresh();
            }
        }
    }

    private void refresh() {
        if (!UserDevPreferences.isHasDev(getContext())) {
            checkHasDev();
            return;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("userId", UserDevPreferences.getUserId(getContext()));
        map.put("devId", UserDevPreferences.getSelectDev(getContext()));
        OkHttpHelper.post(UrlApi.e_details, map, new ResultCallback2() {
            @Override
            protected void onFailed(String error, int code) {
                srl.setRefreshing(false);
                cancelLoadingDialog();
                displayMessageDialog(error);
            }

            @Override
            protected void onSuccess(BaseResult response, int id) {
                srl.setRefreshing(false);
                cancelLoadingDialog();
                if (!response.isSuccess()) {
                    displayMessageDialog(response.getMessage());
                    return;
                }

                EDetails resultObj = DataUtils.getResultObj(response.getData(), EDetails.class);

                DeviceBean devO = resultObj.getDevO();
                setTextView(devO.getCode(), tvCode)
                        .setTextView(devO.getNickname(), tvName)
                        .setTextView(devO.getDevOwner(), tvOwner)
                        .setTextView(devO.getDevRoom(), tvDoor);

                EDetails.TotalE totalE = resultObj.getTotalE();
                EDetails.TotalE yearE = resultObj.getYearE();
                EDetails.TotalE monthE = resultObj.getMonthE();
                setTextView(totalE.getTotalE() + "", tvENum)
                        .setTextView(yearE.getTotalE() + "", tvYearENum)
                        .setTextView(yearE.getYear() + "  年", tvYear)
                        .setTextView(monthE.getTotalE() + "", tvMonthENum)
                        .setTextView(monthE.getMonth() + "  月", tvMonth);

                String stringDate4 = DateUtils.getStringDate4(resultObj.getBindDate() * 1000);
                setTextView(resultObj.getUnit() + "", tvUnit)
                        .setTextView(stringDate4, tvBindDate)
                        .setTextView(String.format("%.2f", resultObj.getBuyElectric()), tvPrice);
            }
        });
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
                startActivity(PowerManagerActivity.class);
                break;
            case R.id.e_details_tv_summary://查看历史统计
                startActivity(EUseRecordActivity.class);
                break;
            case R.id.e_details_tv_setting_name://设置别名
                displaySettingNameDialog();
                break;
            case R.id.top_bar_right_btn://点击选择设备
                //dialog.show();
                startActivity(DevListSelectActivity.class, REQUEST_SELECT_DEV);
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
                        if (QMUIKeyboardHelper.isKeyboardVisible(getActivity())) {
                            QMUIKeyboardHelper.hideKeyboard(editText);
                        }
                        dialog.cancel();
                        displayLoadingDialog("提交中");
                        httpSetDevNickName(text.toString());
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

    private void httpSetDevNickName(String nickName) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", UserDevPreferences.getUserId(getContext()));
        map.put("devId", UserDevPreferences.getSelectDev(getContext()));
        map.put("nickName", nickName);

        OkHttpHeader.post(UrlApi.setting_nice_name, map, new ResultCallback2() {
            @Override
            protected void onFailed(String error, int code) {
                cancelLoadingDialog();
                displayMessageDialog(error);
            }

            @Override
            protected void onSuccess(BaseResult response, int id) {
                cancelLoadingDialog();
                if (response.isSuccess()) {
                    displayTipDialogSuccess("修改成功");
                } else {
                    displayMessageDialog(response.getMessage());
                }
                refresh();
            }
        });
    }

    @Override
    public void onRefresh() {
        refresh();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_SELECT_DEV:
                if (resultCode != Activity.RESULT_OK) {
                    return;
                }
                int devId = DevListSelectActivity.getDevId(data);
                UserDevPreferences.saveSelectDev(getContext(), devId);
                displayLoadingDialog("加载数据中");
                refresh();

                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onSelectDev(List<IDevDetails> data, int position) {

    }
}
