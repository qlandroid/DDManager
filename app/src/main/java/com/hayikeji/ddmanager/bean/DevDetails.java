package com.hayikeji.ddmanager.bean;

import java.util.List;

/**
 * 描述：
 * 邮箱 email:strive_bug@yeah.net
 * 创建时间 2018/8/15
 *
 * @author ql
 */
public class DevDetails {
    private int isHasDev;//0-无  1-有
    private List<WarnBean> warnList;

    public boolean getIsHasDev() {
        return 1 == isHasDev;
    }

    public void setIsHasDev(int isHasDev) {
        this.isHasDev = isHasDev;
    }

    public List<WarnBean> getWarnList() {
        return warnList;
    }

    public void setWarnList(List<WarnBean> warnList) {
        this.warnList = warnList;
    }
}
