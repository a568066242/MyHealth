package com.bishe.lzj.myhealth.UI.DataCollect;

import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bishe.lzj.myhealth.Bean.BloodPressure;
import com.bishe.lzj.myhealth.Logic.DataCollecter.DataCollector;
import com.bishe.lzj.myhealth.Logic.DataSender.DataSender;
import com.bishe.lzj.myhealth.Logic.DataSender.VolleyHealthDataSender;
import com.bishe.lzj.myhealth.Logic.Factory.DataCollectorFactory;
import com.bishe.lzj.myhealth.MyApplication;
import com.bishe.lzj.myhealth.R;
import com.bishe.lzj.myhealth.Util.LogUtil;
import com.bishe.lzj.myhealth.Util.ToastUtil;

import java.io.IOException;
import java.util.Date;

/**
 * Created by lzj on 2016/3/2.
 */
public abstract class AbstarctCollectFragment extends Fragment implements View.OnClickListener, DataCollectFragment.ChangeDeviceNameCallbackListener {
    public static final int SUCCESS = 0x1;
    private TextView tv_caozuo;
    private TextView tv_device;
    private Button btn_connect;
    private Button btn_collect;
    private Button btn_save;
    private Object data = null;
//    private DataCollectActivity activity;
    private DataCollectFragment activity;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case SUCCESS:
                    handleAfterSuccess(msg.obj);
                    btn_collect.setText(MyApplication.getContext().getResources().getString(R.string.reaccept));
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LogUtil.w(getTAG(),"onCreateView");
        View view = inflater.inflate(getLauoutID(),null);
        init(view);
        return view;
    }


    protected  void init(View view){
//        activity = (DataCollectActivity) getActivity();
        activity = (DataCollectFragment) getFragmentManager().findFragmentById(R.id.container_main);
        tv_caozuo = (TextView) view.findViewById(R.id.tv_caozuo);
        tv_caozuo.setText(getCaoZuo());
        tv_device = (TextView) view.findViewById(R.id.tv_device);
        btn_collect = (Button) view.findViewById(R.id.btn_collect);
        btn_connect = (Button) view.findViewById(R.id.btn_connect);
        btn_save = (Button) view.findViewById(R.id.btn_save);
        view.findViewById(R.id.btn_choose).setOnClickListener(this);
        btn_save.setOnClickListener(this);
        btn_connect.setOnClickListener(this);
        btn_collect.setOnClickListener(this);
        initViews(view);
    }

    /**
     * init view in specified class
     * @param view
     */
    protected abstract void initViews(View view);


    /**
     * @return fragment layout id
     */
    protected abstract int getLauoutID();

    /**
     * get caozuoshuoming string
     * @return
     */
    protected abstract String getCaoZuo();

    /**
     * get datacollector
     * @return
     */
    protected abstract DataCollector getDataCollector();

    /**
     * get datasender
     * @return
     */
    protected abstract VolleyHealthDataSender getHealthDataSender();

    protected abstract String getTAG();


    protected abstract void handleAfterSuccess(Object obj);

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_connect://连接按钮
                if (getDataCollector() != null && activity.isNotConnect((getDataCollector()))) {
                    BluetoothSocket socket = null;
                    try {
                        socket = activity.initSocket(this);
                        if (socket == null) {
                            LogUtil.e(getTAG(), "init socket failure");
                            return;
                        }
                        activity.setSocket(socket, getDataCollector());
                        LogUtil.i(getTAG(), "send data");
                        ToastUtil.showShort("开始连接");
                        getDataCollector().ready();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else if (activity.isConnecting(getDataCollector())) {
                    ToastUtil.showShort("正在连接...");
                } else if (activity.isConnected(getDataCollector())) {
                    ToastUtil.showShort("已连接，可以进行测量了");
                }
                break;
            case R.id.btn_collect://测量按钮
                if (getDataCollector() != null) {
                    if (!activity.isConnected(getDataCollector())) {
                        ToastUtil.showShort("请先连接设备");
                        return;
                    }
                    if (activity.isCanAccept(getDataCollector())) {
                        getDataCollector().accept(new DataCollector.FinishCallbackListener() {
                            @Override
                            public void onFinish(Object o) {
                                Message m = handler.obtainMessage();
                                m.what = SUCCESS;
                                m.obj = o;
                                data = o;
                                handler.sendMessage(m);
                            }
                        });
                    } else {
                        ToastUtil.showShort("正在采集数据");
                    }
                }
                break;
            case R.id.btn_save://上传保存按钮
               // ToastUtil.showShort("此功能还未完成");
                if(data == null){
                    ToastUtil.showShort("请先采集数据");
                    //先模拟一下数据
                    data = getData();
                    return;
                }
                VolleyHealthDataSender healthDataSender = getHealthDataSender();
                healthDataSender.save(data, new DataSender.FinishedCallbackListener() {
                    @Override
                    public void onFinished(Bundle bundle) {
                        ToastUtil.showShort("上传成功");
                    }
                }, new DataSender.ErrorCallbackListener() {
                    @Override
                    public void onError(String error) {
                        ToastUtil.showShort("上传失败，请重新上传！");
                    }
                });
                break;
            case R.id.btn_choose://选择按钮
                activity.showChooseBluetoothDialog(this);
                break;
            default:
                break;
        }

    }

    /**
     * 模拟设置数据
     */
    protected abstract Object getData();




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtil.w(getTAG(),"onDestroyView");
        activity.disConnect(getDataCollector());
    }

    @Override
    public void onChange(String s) {
        tv_device.setText(s);
    }

}
