package com.hayikeji.ddmananger;

import android.support.multidex.MultiDexApplication;
import android.util.DisplayMetrics;

import com.hayikeji.ddmananger.info.OkHttpInfo;

/**
 * 文件下载地址 HttpDownloadHelper ----Utils
 * http://www.wanandroid.com/blog/show/2080
 * Created by mrqiu on 2017/10/15.
 */
public class MyApp extends MultiDexApplication {
    private static MyApp instance;



    public static MyApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        OkHttpInfo.initOkHttpCard(this);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        C.SCREEN_WIDTH= displayMetrics.widthPixels;
        C.SCREEN_HEIGHT= displayMetrics.heightPixels;
    }



}
