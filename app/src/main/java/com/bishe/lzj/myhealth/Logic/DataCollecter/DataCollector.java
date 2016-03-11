package com.bishe.lzj.myhealth.Logic.DataCollecter;

import java.io.InputStream;

/**
 * Created by lzj on 2016/2/22.
 */
public interface DataCollector {


    /**
     * send message to call device ready to collect data
     */
    public void ready();

    /**
     * accept data
     */
    public void accept(FinishCallbackListener finishCallbackListener);


    public void close();

    interface FinishCallbackListener{
        void onFinish(Object o);
    }
}
