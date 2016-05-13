package com.bishe.lzj.myhealth.UI.DataCollect;

import android.app.Fragment;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bishe.lzj.myhealth.Bean.BloodPressure;
import com.bishe.lzj.myhealth.Bean.BloodSugar;
import com.bishe.lzj.myhealth.Logic.DataCollecter.DataCollector;
import com.bishe.lzj.myhealth.Logic.DataSender.Impl.VolleyBSSender;
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
public class BSCollectFragment extends AbstarctCollectFragment {
    private static final String TAG = "BSCollectFragment";
    private TextView tv_bs_value;
    private DataCollector bloodSugarCollector = SimilateDataCollectorFactory
            .getDataCollectorFactory().createBloodSugarSCollector();


    @Override
    protected void initViews(View view) {
        tv_bs_value = (TextView) view.findViewById(R.id.tv_bs_value);
    }

    @Override
    protected int getLauoutID() {
        return R.layout.layout_bs_collect;
    }

    @Override
    protected String getCaoZuo() {
        return "";
    }

    @Override
    protected DataCollector getDataCollector() {
        return bloodSugarCollector;
    }

    @Override
    protected VolleyHealthDataSender getHealthDataSender() {
        return VolleyBSSender.instance();
    }

    @Override
    protected String getTAG() {
        return TAG;
    }

    @Override
    protected void handleAfterSuccess(Object obj) {
        BloodSugar bs = (BloodSugar) obj;
        tv_bs_value.setText(""+bs.getBS());
    }

    @Override
    protected Object getData() {
        BloodSugar bs = new BloodSugar();
        bs.setDate(new Date());
        bs.setBS(23.4);
        bs.setUserId(MyApplication.getUser().getId());
        return bs;
    }

}
