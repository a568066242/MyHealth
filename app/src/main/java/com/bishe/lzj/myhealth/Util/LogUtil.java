package com.bishe.lzj.myhealth.Util;

import android.util.Log;

/**
 * Created by lzj on 2016/2/18.
 * logutil
 */
public class LogUtil {
    private static final  int verbose = 1;
    private static final int debug = 2;
    private static final int info = 3;
    private static final int warn = 4;
    private static final int error = 5;
    private static final int nothing = 6;
    private static final int current = debug;

    public static void i(String TAG,String message){
        if(current <= info)
            Log.i(TAG,message);
    }
    public static void e(String TAG,String message){
        if(current <= error)
            Log.e(TAG,message);
    }
    public static void w(String TAG,String message){
        if(current <= warn)
            Log.w(TAG,message);
    }
    public static void v(String TAG,String message){
        if(current <= verbose)
            Log.v(TAG,message);
    }
    public static void d(String TAG,String message){
        if(current <= debug)
            Log.d(TAG,message);
    }

}
