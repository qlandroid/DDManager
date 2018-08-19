package com.hayikeji.ddmananger.ui.widget.dialog;

import android.content.Context;
import android.content.pm.LabeledIntent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hayikeji.ddmananger.R;
import com.hayikeji.ddmananger.bean.DevDetails;
import com.hayikeji.ddmananger.bean.DeviceBean;
import com.hayikeji.ddmananger.ui.adapter.QLViewHolder;
import com.hayikeji.ddmananger.utils.DateUtils;
import com.hayikeji.ddmananger.utils.div.DividerItemDecoration;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：
 * 邮箱 email:strive_bug@yeah.net
 * 创建时间 2018/8/15
 *
 * @author ql
 */
public class DevDetailsDialog {

    private QMUIBottomSheet sheet;
    private DevDetailsAdapter devDetailsAdapter;

    public DevDetailsDialog(Context context) {
        init(context);
    }

    private void init(Context context) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View inflate = layoutInflater.inflate(R.layout.dialog_dev_details, null);
        inflate.findViewById(R.id.dialog_dev_details_close).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sheet.dismiss();
                    }
                }
        );
        RecyclerView rv = (RecyclerView) inflate.findViewById(R.id.content_rv);
        rv.setLayoutManager(new LinearLayoutManager(context));
        rv.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL, 1, Color.GRAY));
        devDetailsAdapter = new DevDetailsAdapter();
        rv.setAdapter(devDetailsAdapter);
        sheet = new QMUIBottomSheet(context);
        sheet.setCancelable(true);
        sheet.setContentView(inflate);
    }

    public void resetView(DeviceBean d) {
        List<ILabelContent> l = new ArrayList<>();

        l.add(new LabelContent("设备编号", d.getCode()));
        l.add(new LabelContent("设备名称", d.getNickname()));
        l.add(new LabelContent("户主", d.getDevOwner().toString()));
        l.add(new LabelContent("总购电量", d.getBuyElectric() + ""));
        l.add(new LabelContent("总用电量", d.getElectric() + ""));
        l.add(new LabelContent("当前状态", "1".equals(d.getDStatus()) ? "在线" : "停电"));
        l.add(new LabelContent("创建时间", DateUtils.getStringDate4(d.getTime() * 1000)));


        devDetailsAdapter.setNewData(l);
        devDetailsAdapter.notifyDataSetChanged();
    }

    public void show() {
        sheet.show();
    }

    public void dismiss() {
        sheet.dismiss();
    }


    private static class DevDetailsAdapter extends BaseQuickAdapter<ILabelContent, QLViewHolder> {
        public DevDetailsAdapter() {
            super(R.layout.item_t_left_t_right);
        }

        @Override
        protected void convert(QLViewHolder helper, ILabelContent item) {
            helper.setText(R.id.item_t_1, item.getLabel())
                    .setText(R.id.item_t_2, item.getContent());
        }
    }

    private static class LabelContent implements ILabelContent {
        String label;
        String content;

        public LabelContent(String label, String content) {
            this.label = label;
            this.content = content;
        }

        @Override
        public CharSequence getLabel() {
            return label;
        }

        @Override
        public CharSequence getContent() {
            return content;
        }
    }
}
