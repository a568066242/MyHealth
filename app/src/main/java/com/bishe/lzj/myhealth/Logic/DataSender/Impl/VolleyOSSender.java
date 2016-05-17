package com.bishe.lzj.myhealth.Logic.DataSender.Impl;

import com.bishe.lzj.myhealth.Bean.BloodSugar;
import com.bishe.lzj.myhealth.Bean.OxygenSaturation;
import com.bishe.lzj.myhealth.Logic.DataSender.VolleyHealthDataSender;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lzj on 2016/5/9.
 */
public class VolleyOSSender extends VolleyHealthDataSender<OxygenSaturation> {

    private static VolleyOSSender sender = new VolleyOSSender();

    private VolleyOSSender(){}

    @Override
    protected String getQueryUrl() {
        return "/queryOS.do";
    }

    @Override
    protected String getTAG() {
        return "VolleyOSSender";
    }

    @Override
    protected Map<String, String> getParamMap(OxygenSaturation data) {
        Map<String,String> params = new HashMap<>();
        params.put("date",data.getDate().toString());
        params.put("os",data.getOs()+ "");
        params.put("id", String.valueOf(data.getUserID()));
        return params;
    }

    @Override
    public String getUrl() {
        return "/addOneOS.do";
    }

    public static VolleyOSSender instance(){
        return sender;
    }

    public static List<OxygenSaturation> parseReturnList(String s){
        return getGson().fromJson(s,new TypeToken<List<OxygenSaturation>>(){}.getType());
    }
}
