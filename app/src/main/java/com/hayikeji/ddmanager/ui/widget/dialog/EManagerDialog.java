package com.hayikeji.ddmanager.ui.widget.dialog;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hayikeji.ddmanager.R;
import com.hayikeji.ddmanager.ui.adapter.bean.IECloseManager;
import com.hayikeji.ddmanager.ui.adapter.QLViewHolder;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;

import java.util.List;

/**
 * 描述：
 * 邮箱 email:strive_bug@yeah.net
 * 创建时间 2018/8/12
 *
 * @author ql
 */
public class EManagerDialog implements CompoundButton.OnCheckedChangeListener {

    private QMUIBottomSheet sheet;
    private ItemDetailsAdapter adapter;
    private TextView tvCode, tvName, tvStatus, tvDate;
    private Switch aSwitch;
    private OnSwitchStatusListener onSwitchStatusListener;

    private boolean isFirst;

    public EManagerDialog(Context context) {
        init(context);
    }

    private void init(Context context) {
        adapter = new ItemDetailsAdapter();
        View viewGroup = LayoutInflater.from(context).inflate(R.layout.dialog_e_manager, null);

        tvCode = (TextView) viewGroup.findViewById(R.id.dialog_e_manager_tv_code);
        tvName = (TextView) viewGroup.findViewById(R.id.dialog_e_manager_tv_name);
        tvStatus = (TextView) viewGroup.findViewById(R.id.dialog_e_manager_tv_status);
        aSwitch = (Switch) viewGroup.findViewById(R.id.dialog_e_manager_switch);
        tvDate = (TextView) viewGroup.findViewById(R.id.dialog_e_manager_tv_date);
        viewGroup.findViewById(R.id.dialog_e_manager_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sheet.cancel();
            }
        });
        RecyclerView rv = (RecyclerView) viewGroup.findViewById(R.id.dialog_e_manager_rv);
        rv.setLayoutManager(new GridLayoutManager(context, 2));
        rv.setAdapter(adapter);
        sheet = new QMUIBottomSheet(context);
        sheet.setContentView(viewGroup);
        sheet.setCancelable(true);
        aSwitch.setOnCheckedChangeListener(this);

    }


    public void resetContent(String date, IECloseManager ieCloseManager, List<IEDetails> list) {
        tvCode.setText(ieCloseManager.getDevNo());
        tvName.setText(ieCloseManager.getDevName());
        int i = ieCloseManager.isRun() ? ContextCompat.getColor(tvName.getContext(), R.color.text_green_color) : Color.RED;
        tvStatus.setTextColor(i);
        String s = ieCloseManager.isRun() ? "运行中" : "关闭中";
        tvStatus.setText(s);
        aSwitch.setChecked(ieCloseManager.isRun());
        tvDate.setText(date);
        adapter.setNewData(list);
        adapter.notifyDataSetChanged();
        isFirst = false;

    }


    public void show() {
        sheet.show();
    }

    public void hide() {
        sheet.dismiss();
    }

    public void setOnSwitchListener(OnSwitchStatusListener l) {
        this.onSwitchStatusListener = l;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (onSwitchStatusListener == null) {
            return;
        }
        if (!isFirst) {
            isFirst = true;
        }
        onSwitchStatusListener.onSwitchStatus(isChecked);
    }

    public static class ItemDetailsAdapter extends BaseQuickAdapter<IEDetails, QLViewHolder> {

        public ItemDetailsAdapter() {
            super(R.layout.item_d_manager_status);
        }

        @Override
        protected void convert(QLViewHolder helper, IEDetails item) {
            helper.setText(R.id.item_d_manager_status_tv_label, item.getLabel())
                    .setText(R.id.item_d_manager_status_tv_value, item.getValue())
                    .setText(R.id.item_d_manager_status_tv_unit, item.getUnit());
        }
    }

    public static class EDetails implements IEDetails {
        private CharSequence label;
        private CharSequence value;
        private CharSequence unit;

        public EDetails(CharSequence label, CharSequence value, CharSequence unit) {
            this.label = label;
            this.value = value;
            this.unit = unit;
        }

        @Override
        public CharSequence getLabel() {
            return label;
        }

        @Override
        public CharSequence getValue() {
            return value;
        }

        @Override
        public CharSequence getUnit() {
            if (TextUtils.isEmpty(unit)) {
                return "";
            }
            return unit;
        }
    }

    public interface IEDetails {

        CharSequence getLabel();

        CharSequence getValue();

        CharSequence getUnit();
    }

    public interface OnSwitchStatusListener {
        void onSwitchStatus(boolean isOpen);
    }
}
