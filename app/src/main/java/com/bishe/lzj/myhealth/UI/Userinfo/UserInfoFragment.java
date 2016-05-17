package com.bishe.lzj.myhealth.UI.Userinfo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bishe.lzj.myhealth.MyApplication;
import com.bishe.lzj.myhealth.R;
import com.bishe.lzj.myhealth.UI.DataCollect.AbstarctCollectFragment;
import com.bishe.lzj.myhealth.UI.DataCollect.AbstractHistoryFragment;
import com.bishe.lzj.myhealth.UI.DataCollect.DataCollectFragment;
import com.bishe.lzj.myhealth.UI.DataCollect.TitleLinerLayout;
import com.bishe.lzj.myhealth.UI.MainActivity;
import com.bishe.lzj.myhealth.Util.LogUtil;

/**
 * Created by lzj on 2016/5/10.
 */
public class UserInfoFragment extends MainActivity.PlaceholderFragment implements View.OnClickListener {


    private static final String TAG = "UserInfoFragment";
    private TextView tv_basicinfo;
    private TextView tv_healthinfo;
    private int current = 0;//0为基本，1为健康
    public static final int COLLECT_ID = 0;
    public static final int HISTORY_ID = 1;
    private FragmentManager fragmentManager;
    private UserBasicFragment userBasicFragment = new UserBasicFragment();
    private UserHealthFragment userHealthFragment = new UserHealthFragment();


    protected Fragment getCollectFragment(){
        return userBasicFragment;
    }
    protected  Fragment getHistoryFragment(){
        return userHealthFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LogUtil.w(getTAG(), "onCreateView");
        View view = inflater.inflate(R.layout.fragment_userinfo, container,false);
        init();
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
    }

    private void init() {
        fragmentManager = getFragmentManager();
    }

    private void initViews(View view) {
        tv_basicinfo = (TextView) view.findViewById(R.id.tv_collect);
        tv_healthinfo = (TextView) view.findViewById(R.id.tv_history);
        tv_basicinfo.setOnClickListener(this);
        tv_healthinfo.setOnClickListener(this);

        view.findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (current){
                    case COLLECT_ID://在基本信息下点击保存
                        LogUtil.d(TAG,"click save in basic");
                        userBasicFragment.save();
                        break;
                    case HISTORY_ID://在健康信息下点击
                        LogUtil.d(TAG,"click save in health");
                        userHealthFragment.save();
                        break;
                }
            }
        });

        if(fragmentManager!=null){
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.userinfo_container, getCollectFragment());
            fragmentTransaction.add(R.id.userinfo_container, getHistoryFragment());
            if(current == COLLECT_ID){
                convertToCollect();
            }else{
                convertToHistory();
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
        setClickStyle(tv_healthinfo);
        setNotClickStyle(tv_basicinfo);

    }

    private void convertToCollect() {
        current = COLLECT_ID;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.hide(getHistoryFragment());
        fragmentTransaction.show(getCollectFragment());
        fragmentTransaction.commit();
        setClickStyle(tv_basicinfo);
        setNotClickStyle(tv_healthinfo);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtil.w(getTAG(), "onDestroyView");
    }

    @Deprecated
    private void removeAll() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(getHistoryFragment());
        fragmentTransaction.remove(getCollectFragment());
        fragmentTransaction.commit();
    }

    /**
     * set fragment attached this activity textview clicked style
     * @param click tv
     */
    protected void setClickStyle(TextView click){
        click.setTextColor(MyApplication.getContext().getResources().getColor(R.color.black));
        click.setBackgroundResource(R.color.white);

    }

    /**
     * set fragment attached this activity textview not clicked style
     * @param notClick tv
     */
    protected void setNotClickStyle(TextView notClick){
        notClick.setTextColor(MyApplication.getContext().getResources().getColor(R.color.white));
        notClick.setBackgroundResource(R.color.gray);
    }



    public String getTAG() {
        return "UserInfoFragment";
    }
}
