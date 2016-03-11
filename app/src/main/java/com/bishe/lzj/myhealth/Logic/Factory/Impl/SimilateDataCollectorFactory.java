package com.bishe.lzj.myhealth.Logic.Factory.Impl;

import android.bluetooth.BluetoothSocket;

import com.bishe.lzj.myhealth.Logic.DataCollecter.DataCollector;
import com.bishe.lzj.myhealth.Logic.DataCollecter.Impl.SimilateBPCollector;
import com.bishe.lzj.myhealth.Logic.DataCollecter.Impl.SimilateBSCollector;
import com.bishe.lzj.myhealth.Logic.DataCollecter.Impl.SimilateOSCollector;
import com.bishe.lzj.myhealth.Logic.DataCollecter.Impl.SimilatePluseCollector;
import com.bishe.lzj.myhealth.Logic.Factory.DataCollectorFactory;

/**
 * Created by lzj on 2016/2/22.
 */
public class SimilateDataCollectorFactory implements DataCollectorFactory {
    private static SimilateDataCollectorFactory dataCollectorFactory = new SimilateDataCollectorFactory();

    private SimilateDataCollectorFactory() {
    }

    @Override
    public DataCollector createBloodPressureCollector() {
        return new SimilateBPCollector();
    }

    @Override
    public DataCollector createBloodSugarSCollector() {
        return new SimilateBSCollector();
    }

    @Override
    public DataCollector createOxygenSaturationCollector() {
        return new SimilateOSCollector();
    }

    @Override
    public DataCollector createPulseCollector() {
        return new SimilatePluseCollector();
    }

    public static SimilateDataCollectorFactory getDataCollectorFactory() {
        return dataCollectorFactory;
    }
}
