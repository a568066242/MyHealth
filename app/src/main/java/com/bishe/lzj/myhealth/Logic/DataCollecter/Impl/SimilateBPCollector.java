package com.bishe.lzj.myhealth.Logic.DataCollecter.Impl;

import android.bluetooth.BluetoothSocket;
import android.util.Log;

import com.bishe.lzj.myhealth.Bean.BloodPressure;
import com.bishe.lzj.myhealth.Logic.DataCollecter.MyBluetoothDataCollector;
import com.bishe.lzj.myhealth.MyApplication;
import com.bishe.lzj.myhealth.Util.LogUtil;
import com.bishe.lzj.myhealth.Util.ToastUtil;

import java.io.IOException;
import java.util.Date;

/**
 * Created by lzj on 2016/2/22.
 */
public class SimilateBPCollector extends MyBluetoothDataCollector {


    public static final String BLOODPRESSURE = "BLOODPRESSURE";
    private static final String TAG = "SimilateBPCollector";

    public SimilateBPCollector() {
    }

    public SimilateBPCollector(BluetoothSocket socket) throws IOException {
        super(socket);
    }

    @Override
    protected String getFlag() {
        return BLOODPRESSURE;
    }

    @Override
    protected void handleData(String data, FinishCallbackListener finishCallbackListener) {
        LogUtil.w(TAG,data);
        String[] split = data.split(":");//先舒张压，再收缩压
        BloodPressure bloodPressure = new BloodPressure();
        bloodPressure.setDBP(Integer.valueOf(split[0]));
        bloodPressure.setSBP(Integer.valueOf(split[1]));
        bloodPressure.setDate(new Date());
        bloodPressure.setUserID(MyApplication.getUser().getId());
        if(finishCallbackListener != null)
            finishCallbackListener.onFinish(bloodPressure);
    }

    @Override
    protected boolean checkData(String data) {
        String[] split = data.split(":");
        if(split.length !=2)
        {
            LogUtil.e(TAG,"接收到的数据不完整，请重测");
            LogUtil.e(TAG, "data error,data num:" + split.length);
//            ToastUtil.show("数据传输异常，请重测",ToastUtil.LENGH_SHORT);

            return false;
        }
        try {
            Integer.valueOf(split[0]);
            Integer.valueOf(split[1]);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }


}
