package com.bishe.lzj.myhealth.Logic.DataSender.Impl;

import com.bishe.lzj.myhealth.Bean.BloodPressure;
import com.bishe.lzj.myhealth.Bean.BloodSugar;
import com.bishe.lzj.myhealth.Logic.DataSender.VolleyHealthDataSender;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lzj on 2016/3/4.
 */
public class VolleyBSSender extends VolleyHealthDataSender<BloodSugar> {



    private static VolleyBSSender sender = new VolleyBSSender();


    private VolleyBSSender(){}


    @Override
    protected String getQueryUrl() {
        return "/queryBS.do";
    }

    @Override
    protected String getTAG() {
        return "VolleyBSSender";
    }

    @Override
    protected Map<String, String> getParamMap(BloodSugar data) {
        Map<String,String> params = new HashMap<>();
        params.put("date",data.getDate().toString());
        params.put("sugar",data.getBS()+"");
        params.put("id", String.valueOf(data.getUserId()));
        return params;
    }

    @Override
    public String getUrl() {
        return "/addOneBSS.do";
    }

    public static VolleyBSSender instance(){
        return sender;
    }

    public static List<BloodSugar> parseReturnList(String s){
        return getGson().fromJson(s,new TypeToken<List<BloodSugar>>(){}.getType());
    }

}
