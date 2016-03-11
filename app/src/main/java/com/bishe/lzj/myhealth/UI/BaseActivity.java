package com.bishe.lzj.myhealth.UI;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Window;

import com.bishe.lzj.myhealth.Util.ActivityController;
import com.bishe.lzj.myhealth.Util.LogUtil;

/**
 * Created by lzj on 2016/2/19.
 */
public class BaseActivity extends Activity {
    private static final String TAG = "BaseActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.i(TAG, getClass().getSimpleName());//print this activity name
        ActivityController.addActivity(this);
        //去标题栏
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityController.removeActivity(this);
    }
}
