package com.bishe.lzj.myhealth.Logic.DataSender;

import android.os.Bundle;

/**
 * Created by lzj on 2016/2/23.
 */
public interface DataSender {

    interface FinishedCallbackListener{
        void onFinished(Bundle bundle);

    }

    interface ErrorCallbackListener{
        void onError(String error);
    }
}
