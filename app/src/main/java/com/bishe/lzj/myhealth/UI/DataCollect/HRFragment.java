package com.bishe.lzj.myhealth.UI.DataCollect;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by lzj on 2016/3/2.
 */
@Deprecated
public class HRFragment extends AbstractDataFragment {

    @Override
    protected String getTAG() {
        return null;
    }

    @Override
    protected AbstarctCollectFragment getCollectFragment() {
        return null;
    }

    @Override
    protected AbstractHistoryFragment getHistoryFragment() {
        return null;
    }

    @Override
    protected int getLayoutID() {
        return 0;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
