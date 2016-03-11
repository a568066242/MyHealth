package com.bishe.lzj.myhealth.Logic.DataCollecter.Impl;

import com.bishe.lzj.myhealth.Bean.OxygenSaturation;
import com.bishe.lzj.myhealth.Logic.DataCollecter.DataCollector;
import com.bishe.lzj.myhealth.Logic.DataCollecter.MyBluetoothDataCollector;
import com.bishe.lzj.myhealth.MyApplication;

import java.util.Date;

/**
 * Created by lzj on 2016/3/2.
 */
public class SimilateOSCollector extends MyBluetoothDataCollector {
    public static final String OxygenSaturation = "OxygenSaturation";
    private static final String TAG = "SimilateOSCollector";

    @Override
    protected String getFlag() {
        return OxygenSaturation;
    }

    @Override
    protected void handleData(String data, FinishCallbackListener finishCallbackListener) {
        Integer value = Integer.valueOf(data);
        OxygenSaturation os = new OxygenSaturation(MyApplication.getUser().getId(),new Date(),value);
        if(finishCallbackListener !=null)
            finishCallbackListener.onFinish(os);

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
