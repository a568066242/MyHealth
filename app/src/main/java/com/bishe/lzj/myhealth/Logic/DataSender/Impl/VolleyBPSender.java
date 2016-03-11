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

import java.util.Map;

/**
 * Created by lzj on 2016/3/4.
 */
public class VolleyBPSender extends VolleyHealthDataSender<BloodPressure> {


    @Override
    protected String getTAG() {
        return null;
    }

    @Override
    protected Map<String, String> getParamMap(BloodPressure data) {
        return null;
    }
}
