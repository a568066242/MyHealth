package com.bishe.lzj.myhealth.Logic.DataCollecter.Impl;

import android.bluetooth.BluetoothSocket;

import com.bishe.lzj.myhealth.Bean.BloodSugar;
import com.bishe.lzj.myhealth.Logic.DataCollecter.MyBluetoothDataCollector;
import com.bishe.lzj.myhealth.MyApplication;

import java.io.IOException;
import java.util.Date;

/**
 * Created by lzj on 2016/2/22.
 */
public class SimilateBSCollector extends MyBluetoothDataCollector {
    private static final String TAG = "SimilateBSCollector";
    public static final String BLOODSUGAR = "BLOODSUGAR";

    public  SimilateBSCollector(){}

    public SimilateBSCollector(BluetoothSocket socket) throws IOException {
        super(socket);
    }


    @Override
    protected String getFlag() {
        return BLOODSUGAR;
    }

    @Override
    protected void handleData(String data, FinishCallbackListener finishCallbackListener) {
        Double value = Double.valueOf(data);
        BloodSugar bloodSugar = new BloodSugar(MyApplication.getUser().getId(),value,new Date());
        if(finishCallbackListener != null)
            finishCallbackListener.onFinish(bloodSugar);

    }

    @Override
    protected boolean checkData(String data) {
        try {
            Double.valueOf(data);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return  false;
        }
        return true;
    }


}
