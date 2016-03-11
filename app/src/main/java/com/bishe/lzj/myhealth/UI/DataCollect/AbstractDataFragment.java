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
public abstract  class AbstractDataFragment extends Fragment implements View.OnClickListener {

    private TextView tv_collect;
    private TextView tv_history;
    private int current = 0;//0为测量，1为历史
    public static final int COLLECT_ID = 0;
    public static final int HISTORY_ID = 1;
    private DataCollectActivity activity;
    private FragmentManager fragmentManager;


    protected abstract String getTAG();
    protected abstract AbstarctCollectFragment getCollectFragment();
    protected abstract AbstractHistoryFragment getHistoryFragment();
    protected abstract int getLayoutID();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LogUtil.w(getTAG(), "onCreateView");
        View view = inflater.inflate(getLayoutID(), null);
        initViews(view);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        LogUtil.w(getTAG(), "onAttach");
        if(activity !=null) {
            LogUtil.w(getTAG(), activity.getClass().getSimpleName());
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.w(getTAG(), "onCreate");
        init();
    }

    private void init() {
        activity = (DataCollectActivity) getActivity();
        fragmentManager = getFragmentManager();
        if(fragmentManager == null){
            LogUtil.e(getTAG(),"fragmentManager is null");
        }
    }

    private void initViews(View view) {
        tv_collect = (TextView) view.findViewById(R.id.tv_collect);
        tv_history = (TextView) view.findViewById(R.id.tv_history);
        tv_collect.setOnClickListener(this);
        tv_history.setOnClickListener(this);

        if(fragmentManager!=null){
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.container_inner, getCollectFragment());
            fragmentTransaction.add(R.id.container_inner, getHistoryFragment());
            if(current == COLLECT_ID){
                convertToCollect();
//                fragmentTransaction.hide(historyFragment);
            }else{
                convertToHistory();
//                fragmentTransaction.hide(collectFragment);
            }
            fragmentTransaction.commit();
        }
        else{
            LogUtil.e(getTAG(),"fragmentManager is null");
        }


    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_collect://点击测量按钮
                if(current == COLLECT_ID)
                    return;
                convertToCollect();
                break;
            case R.id.tv_history://点击历史按钮
                if(current == HISTORY_ID)
                    return;
                convertToHistory();
                break;
        }
    }

    private void convertToHistory() {
        current = HISTORY_ID;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.hide(getCollectFragment());
        fragmentTransaction.show(getHistoryFragment());
        fragmentTransaction.commit();
        activity.setClickStyle(tv_history);
        activity.setNotClickStyle(tv_collect);

    }

    private void convertToCollect() {
        current = COLLECT_ID;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.hide(getHistoryFragment());
        fragmentTransaction.show(getCollectFragment());
        fragmentTransaction.commit();
        activity.setClickStyle(tv_collect);
        activity.setNotClickStyle(tv_history);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtil.w(getTAG(), "onDestroyView");
        removeAll();
    }

    private void removeAll() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(getHistoryFragment());
        fragmentTransaction.remove(getCollectFragment());
        fragmentTransaction.commit();
    }


}
