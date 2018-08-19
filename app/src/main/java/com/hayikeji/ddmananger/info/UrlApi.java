package com.hayikeji.ddmananger.info;

/**
 * 描述：
 * 邮箱 email:strive_bug@yeah.net
 * 创建时间 2018/8/18
 *
 * @author ql
 */
public class UrlApi {

    public static final String baseUrl = "http://192.168.0.101:8080";
    public static  String index_url = baseUrl +"/dev/homepage";
    public static  String dev_list = baseUrl +"/dev/getDevList";

    public static String e_details = baseUrl +"/dev/getDevDetails";

    public static String warn_msg = baseUrl +"/dev/getWarnMsg";

    public static String getTransaction = baseUrl + "/dev/getTransaction";

    public static String error_warn = baseUrl + "/dev/getStopDev";
    public static String dev = baseUrl + "/dev/getDevStatus";

    public static  String user_e = baseUrl + "/dev/useE";
}
