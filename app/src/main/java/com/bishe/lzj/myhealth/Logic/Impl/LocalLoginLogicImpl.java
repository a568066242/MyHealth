package com.bishe.lzj.myhealth.Logic.Impl;

import android.content.Context;
import android.content.SharedPreferences;

import com.bishe.lzj.myhealth.Bean.LoginInfo;
import com.bishe.lzj.myhealth.Logic.LocalLoginLogic;
import com.bishe.lzj.myhealth.MyApplication;

/**
 * Created by lzj on 2016/2/24.
 */
public class LocalLoginLogicImpl implements LocalLoginLogic {

    private static final String FILE_NAME = "LAST_LOGIN";
    public static final String IS_REMEMBER_PWD = "IS_REMEMBER_PWD";
    public static final String IS_AUTO_LOGIN = "IS_AUTO_LOGIN";
    public static final String USERNAME = "USERNAME";
    public static final String PWD = "PWD";

    @Override
    public LoginInfo getLoginInfo() {
        SharedPreferences sharedPreferences = MyApplication.getContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return new LoginInfo(sharedPreferences.getBoolean(IS_AUTO_LOGIN,false)
                                ,sharedPreferences.getBoolean(IS_REMEMBER_PWD,false)
                                ,sharedPreferences.getString(USERNAME,null)
                                ,sharedPreferences.getString(PWD,null));
    }


    @Override
    public void saveLocalLoginInfo(LoginInfo loginInfo) {
        SharedPreferences sharedPreferences = MyApplication.getContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putBoolean(IS_REMEMBER_PWD,loginInfo.isRememberPWD());
        edit.putBoolean(IS_AUTO_LOGIN,loginInfo.isAutoLogin());
        edit.putString(USERNAME, loginInfo.getLastLoginUserName());
        edit.putString(PWD, loginInfo.getLastLoginPWD());
        edit.commit();

    }
}
