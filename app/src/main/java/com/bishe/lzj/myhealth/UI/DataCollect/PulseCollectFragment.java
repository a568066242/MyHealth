package com.bishe.lzj.myhealth.UI.DataCollect;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bishe.lzj.myhealth.Bean.Pulse;
import com.bishe.lzj.myhealth.Logic.DataCollecter.DataCollector;
import com.bishe.lzj.myhealth.Logic.Factory.Impl.SimilateDataCollectorFactory;
import com.bishe.lzj.myhealth.R;

/**
 * Created by lzj on 2016/3/1.
 */
public class PulseCollectFragment extends AbstarctCollectFragment {
    private static final String TAG = "PulseCollectFragment";
    private DataCollector dataCollector = SimilateDataCollectorFactory
                                .getDataCollectorFactory().createPulseCollector();
    private TextView tv_pulse_value;
    @Override
    protected void initViews(View view) {
        tv_pulse_value = (TextView) view.findViewById(R.id.tv_pulse_value);
    }

    @Override
    protected int getLauoutID() {
        return R.layout.layout_pulse_collect;
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
    protected String getTAG() {
        return TAG;
    }

    @Override
    protected void handleAfterSuccess(Object obj) {
        Pulse pulse = (Pulse) obj;
        tv_pulse_value.setText(""+pulse.getPulse());
    }
}
