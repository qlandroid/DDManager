package com.hayikeji.ddmananger.ui.activity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.ql.bindview.BindView;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.hayikeji.ddmananger.R;
import com.hayikeji.ddmananger.base.BaseActivity;
import com.hayikeji.ddmananger.base.BaseFragment;
import com.hayikeji.ddmananger.base.BindLayout;
import com.hayikeji.ddmananger.bean.BaseResult;
import com.hayikeji.ddmananger.bean.DeviceBean;
import com.hayikeji.ddmananger.http.OkHttpHeader;
import com.hayikeji.ddmananger.http.ResultCallback2;
import com.hayikeji.ddmananger.info.UrlApi;
import com.hayikeji.ddmananger.ui.fragment.EUseRecordMonthFragment;
import com.hayikeji.ddmananger.utils.DataUtils;
import com.hayikeji.ddmananger.utils.PreferencesUtils;
import com.hayikeji.ddmananger.utils.preferences.UserDevPreferences;
import com.qmuiteam.qmui.util.QMUIResHelper;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogMenuItemView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@BindLayout(layoutRes = R.layout.activity_euse_record,title = "电量统计")
public class EUseRecordActivity extends BaseActivity {
    private final String P_MONTH = "模式:月";
    private final String P_YEAR = "模式:年";

    @BindView(R.id.activity_euse_record_tv_dev_code)
    TextView tvDevCode;
    @BindView(R.id.activity_euse_record_tv_owner)
    TextView tvOwner;
    @BindView(R.id.activity_euse_record_tv_run_status)
    TextView tvRunStatus;
    @BindView(R.id.activity_euse_record_tv_room)
    TextView tvRoom;
    @BindView(R.id.activity_euse_record_tv_select_dev)
    View vSelectDev;

    private Button topbarRightTextBtn;
    private int userId;
    private List<BaseFragment> list = new ArrayList<>();

    @Override
    public void initData() {
        super.initData();
        userId = UserDevPreferences.getUserId(this);

        list.add(EUseRecordMonthFragment.newInstance());
    }

    @Override
    public void initBar() {
        super.initBar();

        topbarRightTextBtn = mTopBar.addRightTextButton(P_MONTH, R.id.top_bar_right_btn);
        topbarRightTextBtn.setOnClickListener(this);

    }

    @Override
    public void initWidget() {
        super.initWidget();
        vSelectDev.setOnClickListener(this);
        loadDev(UserDevPreferences.getSelectDev(this));
        myChangeFragment(R.id.activity_e_User_record_fl_content, list.get(0));
    }

    private void loadDev(int devId) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("devId", devId);

        OkHttpHeader.post(UrlApi.dev, map, new ResultCallback2() {
            @Override
            protected void onFailed(String error, int code) {
                cancelLoadingDialog();
                displayMessageDialog(error);
            }

            @Override
            protected void onSuccess(BaseResult response, int id) {
                cancelLoadingDialog();
                if (!response.isSuccess()) {
                    displayMessageDialog(response.getMessage());
                    return;
                }
                DeviceBean resultObj = DataUtils.getResultObj(response.getData(), DeviceBean.class);
                tvDevCode.setText(resultObj.getCode());
                tvOwner.setText(resultObj.getDevOwner());
                tvRoom.setText(resultObj.getDevRoom());
                boolean equals = "1".equals(resultObj.getDStatus());
                String s = equals ? "正常运行" : "停止运行中";
                int i = equals ? ContextCompat.getColor(getApplicationContext(), R.color.text_green_color) : Color.RED;
                tvRunStatus.setTextColor(i);
                tvRunStatus.setText(s);
            }
        });
    }


    @Override
    public void forbidClick(View v) {
        super.forbidClick(v);
        switch (v.getId()) {
            case R.id.activity_euse_record_tv_select_dev:
                startActivity(DevListSelectActivity.class);
                break;
            case R.id.top_bar_right_btn:
                QMUIDialog d = new QMUIDialog.CheckableDialogBuilder(this)
                        .addItems(new CharSequence[]{"月", "年"}, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0:
                                        topbarRightTextBtn.setText(P_MONTH);
                                        myChangeFragment(R.id.activity_e_User_record_fl_content, list.get(0));
                                        break;
                                    case 1:
                                        topbarRightTextBtn.setText(P_YEAR);
                                        myChangeFragment(R.id.activity_e_User_record_fl_content, list.get(1));
                                        break;
                                }
                                dialog.cancel();
                            }
                        }).show();
                d.setCancelable(true);
                break;
        }
    }
}
