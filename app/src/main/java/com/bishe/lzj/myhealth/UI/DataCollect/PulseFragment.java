package com.bishe.lzj.myhealth.UI.DataCollect;
import com.bishe.lzj.myhealth.R;


/**
 * Created by lzj on 2016/3/2.
 */
public class PulseFragment extends AbstractDataFragment{
    private static final String TAG = "PulseFragment";
    private AbstarctCollectFragment collectFragment = new PulseCollectFragment();
    private AbstractHistoryFragment historyFragment = new PulseHistoryFragment();

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
        return R.layout.layout_pulse;
    }


}
