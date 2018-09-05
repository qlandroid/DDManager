package com.hayikeji.ddmanager.ui.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.hayikeji.ddmanager.R;
import com.hayikeji.ddmanager.bean.WarnBean;
import com.hayikeji.ddmanager.ui.adapter.bean.HomeTitle;
import com.hayikeji.ddmanager.ui.adapter.bean.IHomeError;
import com.hayikeji.ddmanager.ui.fragment.home.WarnMsgFragment;

import java.util.List;

/**
 * 描述：
 * 邮箱 email:strive_bug@yeah.net
 * 创建时间 2018/8/6
 *
 * @author ql
 */
public class HomeContentAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, QLViewHolder> {
    public static final int UN_BIND = 0;
    public static final int NO_MSG = 1;
    public static final int TITLE = 3;
    public static final int SMALL = 4;//低电量
    public static final int STOP = 5;//停电
    public static final int ERROR = 6;//异常

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     */
    public HomeContentAdapter() {
        super(null);
        addItemType(UN_BIND, R.layout.item_un_bind);
        addItemType(NO_MSG, R.layout.item_no_msg);
        addItemType(TITLE, R.layout.item_title);
        addItemType(SMALL, R.layout.item_small);
        addItemType(STOP, R.layout.item_stop);
        addItemType(ERROR, R.layout.item_error);
    }

    @Override
    protected void convert(QLViewHolder helper, MultiItemEntity item) {
        switch (item.getItemType()) {
            case UN_BIND:
                convertUnBind(helper, item);
                break;
            case NO_MSG:
                convertNoMsg(helper, item);
                break;
            case TITLE:
                convertTitle(helper, item);
                break;
            case SMALL:
                convertSmall(helper, item);
                break;
            case STOP:
                convertStop(helper, item);
                break;
            case ERROR:
                convertError(helper, item);
                break;
        }
    }

    /**
     * 未绑定电表
     *
     * @param helper
     * @param item
     */
    private void convertUnBind(QLViewHolder helper, MultiItemEntity item) {

    }

    /**
     * 当前没有消息
     *
     * @param helper
     * @param item
     */
    private void convertNoMsg(QLViewHolder helper, MultiItemEntity item) {

    }

    /**
     * 标题设置
     *
     * @param helper
     * @param item
     */
    private void convertTitle(QLViewHolder helper, MultiItemEntity item) {
        if (item instanceof HomeTitle) {
            helper.setText(R.id.item_title_tv, ((HomeTitle) item).getTitle());
        }
    }

    /**
     * 低电量通知
     *
     * @param helper
     * @param item
     */
    private void convertSmall(QLViewHolder helper, MultiItemEntity item) {

    }

    /**
     * 停电通知
     *
     * @param helper
     * @param item
     */
    private void convertStop(QLViewHolder helper, MultiItemEntity item) {

    }

    /**
     * 电量异常信息
     *
     * @param helper
     * @param item
     */
    private void convertError(QLViewHolder helper, MultiItemEntity item) {
        IHomeError error = (IHomeError) item;
        helper.setText(R.id.item_error_tv_title, error.getTitle())
                .setText(R.id.item_error_tv_dev_code, error.getDevCode())
                .setText(R.id.item_error_tv_error_content, error.getErrorContent())
                .setText(R.id.item_error_tv_date, error.getDate());
    }

}
