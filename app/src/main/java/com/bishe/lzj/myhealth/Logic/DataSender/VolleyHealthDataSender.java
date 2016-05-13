package com.bishe.lzj.myhealth.Logic.DataSender;

import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bishe.lzj.myhealth.Bean.BloodPressure;
import com.bishe.lzj.myhealth.Util.LogUtil;

import java.util.Map;

/**
 * Created by lzj on 2016/3/4.
 * 基本健康数据上传服务器
 */
public abstract class VolleyHealthDataSender<T> extends VolleyDataSender {



    public  void save(final T data,
                      final FinishedCallbackListener finishedCallbackListener,
                      final ErrorCallbackListener errorCallbackListener){
            RequestQueue requestQueue = getmQueue();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, getBaseURL() + "/health" + getUrl(), new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    if(finishedCallbackListener!=null) {
                        if(s.equals("true"))
                            finishedCallbackListener.onFinished(new Bundle());
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    if(volleyError!=null) {
                        if (errorCallbackListener != null)
                            errorCallbackListener.onError(volleyError.getClass().getSimpleName());
                    }else{
                        if (errorCallbackListener != null)
                            errorCallbackListener.onError("unknown error");
                    }
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> paramMap = getParamMap(data);
                    return paramMap;
                }
            };
            requestQueue.add(stringRequest);
    }

    protected abstract String getTAG();

    /**
     * 获取请求行内容，由具体子类完成
     * @return
     */
    protected abstract Map<String,String> getParamMap(T data);

    public abstract String getUrl();
}

