package com.bishe.lzj.myhealth;

import android.app.Application;
import android.content.Context;

import com.bishe.lzj.myhealth.Bean.User;
import com.bishe.lzj.myhealth.Logic.DataSender.Impl.BasicDataSender;

/**
 * Created by lzj on 2016/2/18.
 */
public class MyApplication extends Application {
    private static Context context;
    private static User user;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }



    /**
     * get context anywhere
     * @return context
     */
    public  static Context getContext(){
        return context;
    }


    public static User getUser() {
        if(user == null)
            user = new User(0,"aaa","aaa");
        return user;
    }

    public static void setUser(User user) {
        MyApplication.user = user;
    }
}
