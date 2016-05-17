package com.bishe.lzj.myhealth.Logic.DataSender.Impl;

import com.bishe.lzj.myhealth.Bean.Pulse;
import com.bishe.lzj.myhealth.Logic.DataSender.VolleyHealthDataSender;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lzj on 2016/5/9.
 */
public class VolleyPulseSender extends VolleyHealthDataSender<Pulse> {

    private static VolleyPulseSender sender = new VolleyPulseSender();

    private VolleyPulseSender(){}

    @Override
    protected String getQueryUrl() {
        return "/queryPluse.do";
    }

    @Override
    protected String getTAG() {
        return "VolleyPulseSender";
    }

    @Override
    protected Map<String, String> getParamMap(Pulse data) {
        Map<String,String> params = new HashMap<>();
        params.put("date",data.getDate().toString());
        params.put("pulse",data.getPulse()+ "");
        params.put("id", String.valueOf(data.getUserID()));
        return params;
    }

    @Override
    public String getUrl() {
        return "/addOnePulse.do";
    }

    public static VolleyPulseSender instance(){
        return sender;
    }

    public static List<Pulse> parseReturnList(String s){
        return getGson().fromJson(s,new TypeToken<List<Pulse>>(){}.getType());
    }
}
