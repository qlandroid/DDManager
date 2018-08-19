package com.hayikeji.ddmananger.utils.preferences;

import android.content.Context;

import com.hayikeji.ddmananger.utils.PreferencesUtils;

/**
 * 描述：
 * 邮箱 email:strive_bug@yeah.net
 * 创建时间 2018/8/19
 *
 * @author ql
 */
public class UserDevPreferences {

    private static final String FILE_NAME = "userDev";

    private static final String KEY_UN_BIND = "unBind";
    private static final String KEY_SELECT_DEV_ID = "selectDevId";
    private static final String KEY_USER_ID = "userId";

    public static void saveIsHasDev(Context context, boolean isHasDev) {
        PreferencesUtils.saveBoolean(context, FILE_NAME, KEY_UN_BIND, isHasDev);
    }
    public static void saveUserId(Context context,int userId){
        PreferencesUtils.saveInt(context,FILE_NAME,KEY_USER_ID,userId);
    }

    public static void saveSelectDev(Context context, int devId) {
        PreferencesUtils.saveInt(context, FILE_NAME, KEY_SELECT_DEV_ID, devId);
    }

    public static int getUserId(Context context){
        return PreferencesUtils.queryInt(context,FILE_NAME,KEY_USER_ID,-1);
    }

    public static int getSelectDev(Context context) {
        return PreferencesUtils.queryInt(context, FILE_NAME, KEY_SELECT_DEV_ID, -1);
    }

    public static boolean isHasDev(Context context) {
        return PreferencesUtils.queryBoolean(context, FILE_NAME, KEY_UN_BIND, false);
    }

}
