package com.hayikeji.ddmanager.ui.widget.dialog;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hayikeji.ddmanager.R;
import com.hayikeji.ddmanager.ui.adapter.DevSelectAdapter;
import com.hayikeji.ddmanager.ui.adapter.bean.IDevDetails;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;

import java.util.List;

/**
 * 描述：
 * 邮箱 email:strive_bug@yeah.net
 * 创建时间 2018/8/7
 *
 * @author ql
 */
public class BottomDevSelectDialog implements BaseQuickAdapter.OnItemClickListener {
    private DevSelectAdapter mDevSelectAdapter = new DevSelectAdapter();
    private QMUIBottomSheet sheet;

    private OnSelectDevListener onSelectDevListener;
    public BottomDevSelectDialog(Context context){
        init(context);
    }

    private void init(Context context) {
        View selectViewGroup = LayoutInflater.from(context).inflate(R.layout.dialog_dev_select, null);
        selectViewGroup.findViewById(R.id.dialog_dev_select_iv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sheet.cancel();
            }
        });
        RecyclerView rv = (RecyclerView) selectViewGroup.findViewById(R.id.dialog_dev_select_rv);
        rv.setAdapter(mDevSelectAdapter);
        sheet = new QMUIBottomSheet(context);
        sheet.setContentView(selectViewGroup);
        sheet.setCancelable(true);

        mDevSelectAdapter.setOnItemClickListener(this);
    }

    public void setDevList(List<IDevDetails> datas) {
        mDevSelectAdapter.setNewData(datas);
        mDevSelectAdapter.notifyDataSetChanged();
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        onSelectDevListener.onSelectDev(adapter.getData(),position);
    }

    public void cancel(){
        sheet.cancel();
    }

    public void show(){
        sheet.show();
    }

    public void setOnSelectDevListener(OnSelectDevListener onSelectDevListener) {
        this.onSelectDevListener = onSelectDevListener;
    }

    public interface OnSelectDevListener {
        void onSelectDev(List<IDevDetails> data, int position);
    }
}
