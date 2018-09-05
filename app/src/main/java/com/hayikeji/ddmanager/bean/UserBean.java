package com.hayikeji.ddmanager.bean;

/**
 * 描述：
 * 邮箱 email:strive_bug@yeah.net
 * 创建时间 2018/8/21
 *
 * @author ql
 */
public class UserBean {
    private int devCount;
    private Integer userId;
    private long regTime;
    private String nickName;

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public long getRegTime() {
        return regTime;
    }

    public int getDevCount() {
        return devCount;
    }

    public void setDevCount(int devCount) {
        this.devCount = devCount;
    }

    public void setRegTime(long regTime) {
        this.regTime = regTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }
}
