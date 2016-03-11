package com.bishe.lzj.myhealth.UI.DataCollect;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bishe.lzj.myhealth.R;
import com.bishe.lzj.myhealth.Util.LogUtil;

/**
 * Created by lzj on 2016/3/2.
 */
public class OSFragment extends AbstractDataFragment{

    private static final String TAG = "OSFragment";
    private AbstarctCollectFragment collectFragment =  new OSCollectFragment();
    private AbstractHistoryFragment historyFragment = new OSHistoryFragment();

    @Override
    protected String getTAG() {
        return TAG;
    }

    @Override
    protected AbstarctCollectFragment getCollectFragment() {
        return collectFragment;
    }

    @Override
    protected AbstractHistoryFragment getHistoryFragment() {
        return historyFragment;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.layout_os;
    }



}
