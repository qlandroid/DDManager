package com.hayikeji.ddmananger.info;


/**
 * 描述：
 * 邮箱 email:strive_bug@yeah.net
 * 创建时间 2018/8/18
 *018200000104
 * @author ql
 */
public class UrlApi {

    public static final String baseUrl = "http://182.254.145.142:10881";
//    public static final String baseUrl = "http://192.168.0.101:10881";

    public static String user_login = baseUrl + "/vuser/login";
    public static String user_reg = baseUrl + "/vuser/reg";
    public static String user_forget_password = baseUrl + "/vuser/forgetPassword";
    public static String user_set_details = baseUrl + "/vuser/setUserDetails";

    public static String index_url = baseUrl + "/dev/homepage";
    public static String dev_list = baseUrl + "/dev/getDevList";

    public static String e_details = baseUrl + "/dev/getDevDetails";

    public static String warn_msg = baseUrl + "/dev/getWarnMsg";

    public static String getTransaction = baseUrl + "/dev/getTransaction";

    public static String error_warn = baseUrl + "/dev/getStopDev";

    public static String dev = baseUrl + "/dev/getDevStatus";

    public static String user_e = baseUrl + "/dev/useE";

    public static String setting_nice_name = baseUrl + "/dev/setDevName";

    /**
     * @param userId    用户ID
     * @param devId     设备唯一id
     * @param runStatus 设备运行状态 0-关闭，1-开启
     */
    public static String dev_set_run_status = baseUrl + "/dev/setDevRunStatus";

    public static String dev_details_by_code = baseUrl + "/dev/getDevDetailsByCode";

    public static String check_vip = baseUrl + "/dev/checkVip";

    public static String user_details = baseUrl + "/vuser/getUserDetails";



    public static final String bindDev = baseUrl +"/bind/bindDev";
    public static final String buyEle = baseUrl +"/bind/buyEle";
    public static final String devVIP = baseUrl +"/bind/devVIP";

}
