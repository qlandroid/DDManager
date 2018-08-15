package com.hayikeji.ddmananger.ui.widget.dialog;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hayikeji.ddmananger.R;
import com.hayikeji.ddmananger.bean.DevDetails;
import com.hayikeji.ddmananger.ui.adapter.QLViewHolder;
import com.hayikeji.ddmananger.utils.div.DividerItemDecoration;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;

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

    public void resetView(DevDetails d) {

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
}
