package com.bishe.lzj.myhealth.Logic.DataCollecter.Impl;

import com.bishe.lzj.myhealth.Bean.Pulse;
import com.bishe.lzj.myhealth.Logic.DataCollecter.DataCollector;
import com.bishe.lzj.myhealth.Logic.DataCollecter.MyBluetoothDataCollector;
import com.bishe.lzj.myhealth.MyApplication;

import java.util.Date;

/**
 * Created by lzj on 2016/3/2.
 */
public class SimilatePluseCollector extends MyBluetoothDataCollector {

    private static final String TAG = "SimilateBSCollector";
    public static final String PULSE = "PULSE";

    @Override
    protected String getFlag() {
        return PULSE;
    }

    @Override
    protected void handleData(String data, FinishCallbackListener finishCallbackListener) {
        Integer value = Integer.valueOf(data);
        Pulse pulse = new Pulse(MyApplication.getUser().getId(),value,new Date());
        if(finishCallbackListener != null)
            finishCallbackListener.onFinish(pulse);
    }

    @Override
    protected boolean checkData(String data) {
        try {
            Integer.valueOf(data);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
