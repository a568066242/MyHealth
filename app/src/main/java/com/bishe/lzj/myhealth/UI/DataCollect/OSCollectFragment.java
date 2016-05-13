package com.bishe.lzj.myhealth.UI.DataCollect;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bishe.lzj.myhealth.Bean.OxygenSaturation;
import com.bishe.lzj.myhealth.Logic.DataCollecter.DataCollector;
import com.bishe.lzj.myhealth.Logic.DataSender.Impl.VolleyOSSender;
import com.bishe.lzj.myhealth.Logic.DataSender.VolleyHealthDataSender;
import com.bishe.lzj.myhealth.Logic.Factory.DataCollectorFactory;
import com.bishe.lzj.myhealth.Logic.Factory.Impl.SimilateDataCollectorFactory;
import com.bishe.lzj.myhealth.MyApplication;
import com.bishe.lzj.myhealth.R;

import java.util.Date;

/**
 * Created by lzj on 2016/3/1.
 */
public class OSCollectFragment extends AbstarctCollectFragment{
    private static final String TAG = "OSCollectFragment";
    private DataCollector dataCollector = SimilateDataCollectorFactory
                    .getDataCollectorFactory().createOxygenSaturationCollector();

    private TextView tv_os_value;

    @Override
    protected void initViews(View view) {
        tv_os_value = (TextView) view.findViewById(R.id.tv_os_value);
    }

    @Override
    protected int getLauoutID() {
        return R.layout.layout_os_collect;
    }

    @Override
    protected String getCaoZuo() {
        return "";
    }

    @Override
    protected DataCollector getDataCollector() {
        return dataCollector;
    }

    @Override
    protected VolleyHealthDataSender getHealthDataSender() {
        return VolleyOSSender.instance();
    }

    @Override
    protected String getTAG() {
        return TAG;
    }

    @Override
    protected void handleAfterSuccess(Object obj) {
        OxygenSaturation os = (OxygenSaturation) obj;
        tv_os_value.setText(""+os.getOs());
    }

    @Override
    protected Object getData() {
        OxygenSaturation os = new OxygenSaturation();
        os.setDate(new Date());
        os.setOs(20);
        os.setUserID(MyApplication.getUser().getId());
        return os;
    }

}
