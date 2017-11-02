package com.zhtx.goodsentity.domain;

/**
 * Created by maqian on 16/4/18.
 */
public class AdminLoginInfo {
    int roleId;
    int userId;
    String userName;

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
