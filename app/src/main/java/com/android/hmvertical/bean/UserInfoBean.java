package com.android.hmvertical.bean;

/**
 * Created by Administrator on 2018/7/4.
 */

public class UserInfoBean {

    private int id;
    private String userName;
    private String fullName;
    private String checkStationCode;
    private String checkStationName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCheckStationCode() {
        return checkStationCode;
    }

    public void setCheckStationCode(String checkStationCode) {
        this.checkStationCode = checkStationCode;
    }

    public String getCheckStationName() {
        return checkStationName;
    }

    public void setCheckStationName(String checkStationName) {
        this.checkStationName = checkStationName;
    }

    @Override
    public String toString() {
        return "UserInfoBean{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", fullName='" + fullName + '\'' +
                ", checkStationCode='" + checkStationCode + '\'' +
                ", checkStationName='" + checkStationName + '\'' +
                '}';
    }
}
