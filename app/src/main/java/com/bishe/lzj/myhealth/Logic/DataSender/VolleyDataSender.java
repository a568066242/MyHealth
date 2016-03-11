package com.bishe.lzj.myhealth.Logic.DataSender;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.bishe.lzj.myhealth.Logic.DataSender.DataSender;
import com.bishe.lzj.myhealth.MyApplication;

/**
 * Created by lzj on 2016/2/23.
 */
public abstract class VolleyDataSender implements DataSender {
    private static RequestQueue mQueue = Volley.newRequestQueue(MyApplication.getContext());
    private static String baseURL = "http://10.0.2.2:8080/HealthDataService";


//    private static FinishedCallbackListener finishedCallbackListener;
//    private static ErrorCallbackListener errorCallbackListener;

    public static RequestQueue getmQueue() {
        return mQueue;
    }

    public static String getBaseURL() {
        return baseURL;
    }

//    public static ErrorCallbackListener getErrorCallbackListener() {
//        return errorCallbackListener;
//    }
//
//    public static void setErrorCallbackListener(ErrorCallbackListener errorCallbackListener) {
//        VolleyDataSender.errorCallbackListener = errorCallbackListener;
//    }
//
//    public static FinishedCallbackListener getFinishedCallbackListener() {
//        return finishedCallbackListener;
//    }
//
//    public static void setFinishedCallbackListener(FinishedCallbackListener finishedCallbackListener) {
//        VolleyDataSender.finishedCallbackListener = finishedCallbackListener;
//    }
}
