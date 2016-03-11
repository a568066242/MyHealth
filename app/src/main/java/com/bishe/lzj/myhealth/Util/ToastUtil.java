package com.bishe.lzj.myhealth.Util;

import android.widget.Toast;

import com.bishe.lzj.myhealth.MyApplication;

/**
 * Created by lzj on 2016/2/24.
 */
public class ToastUtil {

    public static int LENGH_SHORT = Toast.LENGTH_SHORT;
    public static int LENGH_LONG = Toast.LENGTH_LONG;

    public static void show(String message,int time){
        Toast.makeText(MyApplication.getContext(),message,time).show();
    }

    public static void showShort(String message){
        show(message,Toast.LENGTH_SHORT);
    }

}
