package com.bishe.lzj.myhealth.UI.DataCollect;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bishe.lzj.myhealth.R;

/**
 * Created by lzj on 2016/3/1.
 */
public class OSHistoryFragment extends AbstractHistoryFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_os_history,null);
        return view;
    }
}
