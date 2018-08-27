package com.hayikeji.ddmananger;

import android.content.Context;
import android.support.multidex.MultiDexApplication;
import android.util.DisplayMetrics;

import com.hayikeji.ddmananger.bean.BaseResult;
import com.hayikeji.ddmananger.bean.DeviceBean;
import com.hayikeji.ddmananger.bean.UserBean;
import com.hayikeji.ddmananger.http.OkHttpHeader;
import com.hayikeji.ddmananger.http.OkHttpHelper;
import com.hayikeji.ddmananger.http.ResultCallback2;
import com.hayikeji.ddmananger.info.UrlApi;
import com.hayikeji.ddmananger.utils.DataUtils;
import com.hayikeji.ddmananger.utils.eventbus.ChangeDetailsEventBus;
import com.hayikeji.ddmananger.utils.preferences.UserDevPreferences;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

        //OkHttpInfo.initOkHttpCard(this);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        C.SCREEN_WIDTH = displayMetrics.widthPixels;
        C.SCREEN_HEIGHT = displayMetrics.heightPixels;
        C.SCREEN_HEIGHT_3 = (int)(C.SCREEN_WIDTH * 3.0f / 4);
        C.SCREEN_HEIGHT_9 = (int)(C.SCREEN_WIDTH * 9.0f / 16);
    }


    public static void refreshUserDetails() {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", UserDevPreferences.getUserId(getInstance().getApplicationContext()));


        OkHttpHeader.post(UrlApi.user_details, map, new ResultCallback2() {
            @Override
            protected void onFailed(String error, int code) {

            }

            @Override
            protected void onSuccess(BaseResult response, int id) {
                if (!response.isSuccess()) {
                    return;
                }
                UserBean resultObj = DataUtils.getResultObj(response.getData(), UserBean.class);
                UserDevPreferences.saveDevCount(getInstance().getApplicationContext(), resultObj.getDevCount());
                UserDevPreferences.saveInDate(getInstance().getApplicationContext(), resultObj.getRegTime());
                UserDevPreferences.saveNickName(getInstance().getApplicationContext(),resultObj.getNickName());
                ChangeDetailsEventBus.getInstance().postDetails(2);
            }
        });
    }

    public static void refreshUserDev() {
        Map<String, Object> map = new HashMap<>();
        Context applicationContext = getInstance().getApplicationContext();

        map.put("userId", UserDevPreferences.getUserId(applicationContext));
        OkHttpHelper.post(UrlApi.dev_list, map, new ResultCallback2() {
            @Override
            protected void onFailed(String error, int code) {
            }

            @Override
            protected void onSuccess(BaseResult response, int id) {
                if (!response.isSuccess()) {
                    return;
                }
                Context applicationContext = getInstance().getApplicationContext();

                ArrayList<DeviceBean> arrayResult = DataUtils.getArrayResult(response.getList(), DeviceBean.class);
                if (arrayResult != null && arrayResult.size() != 0) {
                    UserDevPreferences.saveDevCount(applicationContext, arrayResult.size());
                    UserDevPreferences.saveIsHasDev(applicationContext, true);
                    UserDevPreferences.saveSelectDev(applicationContext, arrayResult.get(0).getId());
                } else {
                    UserDevPreferences.saveDevCount(applicationContext, 0);
                    UserDevPreferences.saveIsHasDev(applicationContext, false);
                }

                ChangeDetailsEventBus.getInstance().postDetails(1);
            }
        });
    }

}
