package com.bishe.lzj.myhealth.UI.DataCollect;
import com.bishe.lzj.myhealth.R;

/**
 * Created by lzj on 2016/3/2.
 */
public class BPFragment extends AbstractDataFragment {

    private static final String TAG = "BPFragment";
    private AbstarctCollectFragment collectFragment = new BPCollectFragment();
    private AbstractHistoryFragment historyFragment = new BPHistoryFragment();

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
        return R.layout.layout_bp;
    }


}
