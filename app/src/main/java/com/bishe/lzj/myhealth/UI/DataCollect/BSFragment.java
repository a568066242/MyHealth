package com.bishe.lzj.myhealth.UI.DataCollect;

import com.bishe.lzj.myhealth.R;

/**
 * Created by lzj on 2016/3/2.
 */
public class BSFragment extends AbstractDataFragment {
    private static final String TAG = "BPFragment";

    private AbstarctCollectFragment collectFragment = new BSCollectFragment();
    private AbstractHistoryFragment historyFragment = new BSHistoryFragment();

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
        return R.layout.layout_bs;
    }

}
