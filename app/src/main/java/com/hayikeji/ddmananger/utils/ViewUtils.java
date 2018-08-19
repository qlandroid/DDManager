package com.hayikeji.ddmananger.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.hayikeji.ddmananger.R;

/**
 * 描述：
 * 邮箱 email:strive_bug@yeah.net
 * 创建时间 2018/8/19
 *
 * @author ql
 */
public class ViewUtils {

    public static View getLoadEndView(Context context){
        LayoutInflater from = LayoutInflater.from(context);
        View inflate = from.inflate(R.layout.item_foot, null);
        return inflate;
    }
}
