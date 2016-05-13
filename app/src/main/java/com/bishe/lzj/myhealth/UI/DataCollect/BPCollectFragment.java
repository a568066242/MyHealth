package com.bishe.lzj.myhealth.UI.DataCollect;


import android.app.Fragment;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bishe.lzj.myhealth.Bean.BloodPressure;
import com.bishe.lzj.myhealth.Bean.BloodSugar;
import com.bishe.lzj.myhealth.Logic.DataCollecter.DataCollector;
import com.bishe.lzj.myhealth.Logic.DataSender.Impl.VolleyBPSender;
import com.bishe.lzj.myhealth.Logic.DataSender.VolleyHealthDataSender;
import com.bishe.lzj.myhealth.Logic.Factory.Impl.SimilateDataCollectorFactory;
import com.bishe.lzj.myhealth.MyApplication;
import com.bishe.lzj.myhealth.R;
import com.bishe.lzj.myhealth.Util.LogUtil;
import com.bishe.lzj.myhealth.Util.ToastUtil;

import java.io.IOException;
import java.util.Date;

/**
 * Created by lzj on 2016/3/1.
 */
public class BPCollectFragment extends AbstarctCollectFragment {
    private static final String TAG = "BPCollectFragment";
    private TextView tv_dbp_value;
    private TextView tv_sbp_value;
    private DataCollector bloodPressureCollector = SimilateDataCollectorFactory
                                    .getDataCollectorFactory().createBloodPressureCollector();

    private VolleyHealthDataSender sender = VolleyBPSender.instance();

    @Override
    protected void initViews(View view) {
        tv_sbp_value = (TextView) view.findViewById(R.id.tv_bsp_value);
        tv_dbp_value = (TextView) view.findViewById(R.id.tv_dsp_value);
    }

    @Override
    protected int getLauoutID() {
        return R.layout.layout_bp_collect;
    }

    @Override
    protected String getCaoZuo() {
        return "";
    }

    @Override
    protected DataCollector getDataCollector() {
        return bloodPressureCollector;
    }

    @Override
    protected VolleyHealthDataSender getHealthDataSender() {
        return sender;
    }

    @Override
    protected String getTAG() {
        return TAG;
    }

    @Override
    protected void handleAfterSuccess(Object obj) {
        BloodPressure bp = (BloodPressure) obj;
        tv_dbp_value.setText(""+bp.getDBP());
        tv_sbp_value.setText(""+bp.getSBP());

    }

    @Override
    protected Object getData() {
        BloodPressure bs = new BloodPressure();
        bs.setDate(new Date());
        bs.setSBP(99);
        bs.setDBP(200);
        bs.setUserID(MyApplication.getUser().getId());
        return bs;
    }



}
