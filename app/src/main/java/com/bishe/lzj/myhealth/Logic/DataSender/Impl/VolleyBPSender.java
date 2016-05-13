package com.bishe.lzj.myhealth.Logic.DataSender.Impl;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.bishe.lzj.myhealth.Bean.BloodPressure;
import com.bishe.lzj.myhealth.Logic.DataSender.VolleyHealthDataSender;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lzj on 2016/3/4.
 */
public class VolleyBPSender extends VolleyHealthDataSender<BloodPressure> {

    private static VolleyHealthDataSender sender = new VolleyBPSender();

    private VolleyBPSender(){}

    public static  VolleyHealthDataSender instance(){
        return sender;
    }

    @Override
    protected String getTAG() {
        return "VolleyBPSender";
    }

    @Override
    protected Map<String, String> getParamMap(BloodPressure data) {
        Map<String,String> params = new HashMap<>();

        params.put("date",data.getDate().toString());
        params.put("lowpressure", String.valueOf(data.getDBP()));
        params.put("highpressure", String.valueOf(data.getSBP()));
        params.put("id", String.valueOf(data.getUserID()));
        return params;
    }

    @Override
    public String getUrl() {
        return "/addOneBP.do";
    }
}
