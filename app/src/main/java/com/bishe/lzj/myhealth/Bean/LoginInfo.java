package com.bishe.lzj.myhealth.Bean;

/**
 * Created by lzj on 2016/2/19.
 */
public class LoginInfo {

    private boolean isAutoLogin;
    private boolean isRememberPWD;
    private String lastLoginUserName;
    private String lastLoginPWD;

    public LoginInfo() {
    }

    public LoginInfo(boolean isAutoLogin, boolean isRememberPWD, String lastLoginUserName, String lastLoginPWD) {
        this.isAutoLogin = isAutoLogin;
        this.isRememberPWD = isRememberPWD;
        this.lastLoginUserName = lastLoginUserName;
        this.lastLoginPWD = lastLoginPWD;
    }

    public boolean isAutoLogin() {
        return isAutoLogin;
    }

    public void setIsAutoLogin(boolean isAutoLogin) {
        this.isAutoLogin = isAutoLogin;
    }

    public boolean isRememberPWD() {
        return isRememberPWD;
    }

    public void setIsRememberPWD(boolean isRememberPWD) {
        this.isRememberPWD = isRememberPWD;
    }

    public String getLastLoginUserName() {
        return lastLoginUserName;
    }

    public void setLastLoginUserName(String lastLoginUserName) {
        this.lastLoginUserName = lastLoginUserName;
    }

    public String getLastLoginPWD() {
        return lastLoginPWD;
    }

    public void setLastLoginPWD(String lastLoginPWD) {
        this.lastLoginPWD = lastLoginPWD;
    }
}
