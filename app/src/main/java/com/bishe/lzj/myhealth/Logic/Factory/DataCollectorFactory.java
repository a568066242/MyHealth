package com.bishe.lzj.myhealth.Logic.Factory;

import com.bishe.lzj.myhealth.Logic.DataCollecter.DataCollector;

/**
 * Created by lzj on 2016/2/22.
 */
public interface DataCollectorFactory {
    public  DataCollector createBloodPressureCollector();
    public  DataCollector createBloodSugarSCollector();
    public  DataCollector createOxygenSaturationCollector();
    public  DataCollector createPulseCollector();


}
