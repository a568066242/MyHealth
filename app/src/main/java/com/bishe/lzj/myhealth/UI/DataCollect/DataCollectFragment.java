package com.bishe.lzj.myhealth.UI.DataCollect;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bishe.lzj.myhealth.Logic.DataCollecter.DataCollector;
import com.bishe.lzj.myhealth.Logic.DataCollecter.MyBluetoothDataCollector;
import com.bishe.lzj.myhealth.MyApplication;
import com.bishe.lzj.myhealth.R;
import com.bishe.lzj.myhealth.UI.ChooseBluetoothDialog;
import com.bishe.lzj.myhealth.UI.MainActivity;
import com.bishe.lzj.myhealth.Util.BluetoothUtil;
import com.bishe.lzj.myhealth.Util.LogUtil;
import com.bishe.lzj.myhealth.Util.ToastUtil;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by lzj on 2016/5/8.
 */
public class DataCollectFragment extends MainActivity.PlaceholderFragment implements View.OnClickListener{

    private static final String TAG = "DataCollectFragment";
    private FragmentManager fragmentManager = null;
    private BPFragment bpFragment;
    private BSFragment bsFragment;
    private OSFragment osFragment;
    private HRFragment hrFragment;
    private PulseFragment pulseFragment;
    private BluetoothDevice current_choose_device = null;
    public final  int ID_FRAGMENT_BP = 0;
    public final  int ID_FRAGMENT_BS = 2;
    public final  int ID_FRAGMENT_OS = 1;
    public final  int ID_FRAGMENT_HR = 4;
    public final  int ID_FRAGMENT_PULSE = 3;
    private int current_fragment = ID_FRAGMENT_BP;
    private View self;
    private int[] tv_RID = new int[]{R.id.tv_bp,R.id.tv_os,R.id.tv_bs,R.id.tv_pulse};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_datacollect,container,false);
        fragmentManager = getFragmentManager();
        if(!BluetoothUtil.isEnabled())
            BluetoothUtil.open();
        init();
        initViews(view);
        return view;
    }

    private void init() {
        //register broadcast
        BluetoothUtil.registerBroadcast();
        bsFragment = new BSFragment();
        bpFragment = new BPFragment();
        osFragment = new OSFragment();
        hrFragment = new HRFragment();
        pulseFragment = new PulseFragment();
    }

    private void initViews(View view) {
        self = view;
//        bpFragment = (BPFragment) fragmentManager.findFragmentById(R.id.fragment);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container,bpFragment);
        fragmentTransaction.attach(bpFragment);
        fragmentTransaction.commit();
        view.findViewById(R.id.click_bp).setOnClickListener(this);
        view.findViewById(R.id.click_bs).setOnClickListener(this);
//        findViewById(R.id.click_hr).setOnClickListener(this);
        view.findViewById(R.id.click_os).setOnClickListener(this);
        view.findViewById(R.id.click_pulse).setOnClickListener(this);
    }

    public BluetoothDevice getCurrent_choose_device() {
        return current_choose_device;
    }

    public void setCurrent_choose_device(BluetoothDevice current_choose_device) {
        this.current_choose_device = current_choose_device;
    }

    protected boolean isConnecting(DataCollector dataCollector) {
        MyBluetoothDataCollector blue_bp_collector = null;
        if (dataCollector instanceof MyBluetoothDataCollector)
            blue_bp_collector = (MyBluetoothDataCollector) dataCollector;
        if(blue_bp_collector !=null)
            return blue_bp_collector.getState().isConnecting();
        return false;
    }

    protected boolean isConnected(DataCollector dataCollector) {
        MyBluetoothDataCollector blue_bp_collector = null;
        if (dataCollector instanceof MyBluetoothDataCollector)
            blue_bp_collector = (MyBluetoothDataCollector) dataCollector;
        if(blue_bp_collector !=null)
            return blue_bp_collector.getState().isConnected();
        return false;
    }

    protected boolean isNotConnect(DataCollector dataCollector){
        MyBluetoothDataCollector blue_bp_collector = null;
        if (dataCollector instanceof MyBluetoothDataCollector)
            blue_bp_collector = (MyBluetoothDataCollector) dataCollector;
        if(blue_bp_collector != null)
            return blue_bp_collector.getState().isNotConnect();
        return false;
    }

    protected boolean isCanAccept(DataCollector dataCollector){
        MyBluetoothDataCollector blue_bp_collector = null;
        if (dataCollector instanceof MyBluetoothDataCollector)
            blue_bp_collector = (MyBluetoothDataCollector) dataCollector;
        if(blue_bp_collector != null)
            return blue_bp_collector.getState().isNotAccept() ||
                    blue_bp_collector.getState().isAccepted();
        return false;
    }


    protected void setSocket(BluetoothSocket socket, DataCollector dataCollector) throws IOException {
        MyBluetoothDataCollector blue_bp_collector = null;
        if (dataCollector instanceof MyBluetoothDataCollector)
            blue_bp_collector = (MyBluetoothDataCollector) dataCollector;
        if(blue_bp_collector != null)
            blue_bp_collector.setSocket(socket);
        else
            LogUtil.e(TAG, "setSocket failure");
    }

    protected BluetoothSocket initSocket(ChangeDeviceNameCallbackListener listener) throws IOException {
        LogUtil.i(TAG, "create socket");
        if(current_choose_device == null){
            ToastUtil.show("请先选择设备，然后连接", ToastUtil.LENGH_LONG);
            showChooseBluetoothDialog(listener);
            return null;
        }
        BluetoothSocket socket = current_choose_device.createRfcommSocketToServiceRecord(BluetoothUtil.UUID);
        return socket;
    }

    protected BluetoothSocket initSocketByReflect(ChangeDeviceNameCallbackListener listener) throws IOException {
        LogUtil.i(TAG, "create socket");
        if(current_choose_device == null){
            ToastUtil.show("请先选择设备，然后连接", ToastUtil.LENGH_LONG);
            showChooseBluetoothDialog(listener);
            return null;
        }
        BluetoothSocket socket = null;
        BluetoothSocket temp = null;
        try {
            Method m = current_choose_device.getClass().getMethod(
                    "createRfcommSocket", new Class[] { int.class });
            temp = (BluetoothSocket) m.invoke(current_choose_device, 29);//这里端口为1
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        socket = temp;

        return socket;
    }

    protected void showChooseBluetoothDialog(final ChangeDeviceNameCallbackListener listener) {
        BluetoothUtil.open();
        LogUtil.i(TAG, "btn_show_choose_bluetooth clicked");
        LogUtil.i(TAG, "before show dialog");
        new ChooseBluetoothDialog(getActivity(), new ChooseBluetoothDialog.ChoosedCallbackListener() {
            @Override
            public void onChoosed(int position) {
                current_choose_device = BluetoothUtil.getBluetoothDevices().get(position);
                if(listener != null)
                    listener.onChange(BluetoothUtil.getDevices_name().get(position));
            }
        });
        LogUtil.i(TAG, "after show dialog");
        if(!BluetoothUtil.isEnabled())
            BluetoothUtil.open();
        BluetoothUtil.search();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.click_bp:
                if(current_fragment == ID_FRAGMENT_BP)
                    return;
                convertToBPFragment();
                break;
            case R.id.click_os:
                if(current_fragment == ID_FRAGMENT_OS)
                    return;
                convertToOSFragment();
                break;
            case R.id.click_bs:
                if(current_fragment == ID_FRAGMENT_BS)
                    return;
                convertToBSFragment();
                break;
            case R.id.click_pulse:
                if(current_fragment == ID_FRAGMENT_PULSE)
                    return;
                convertToPULSEFragment();
                break;
//            case R.id.click_hr:
//                if(current_fragment == ID_FRAGMENT_HR)
//                    return;
//                convertToHRFragment();
//                break;
            default:
                break;
        }
    }

    private void convertToHRFragment() {
        setNotClickStyle();
        current_fragment = ID_FRAGMENT_HR;
        setClickStyle();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, hrFragment);
        fragmentTransaction.commit();
    }

    private void convertToPULSEFragment() {
        setNotClickStyle();
        current_fragment = ID_FRAGMENT_PULSE;
        setClickStyle();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, pulseFragment);
        fragmentTransaction.commit();
    }

    private void convertToBSFragment() {
        setNotClickStyle();
        current_fragment = ID_FRAGMENT_BS;
        setClickStyle();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, bsFragment);
        fragmentTransaction.commit();



    }

    private void convertToOSFragment() {
        setNotClickStyle();
        current_fragment = ID_FRAGMENT_OS;
        setClickStyle();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, osFragment);
        fragmentTransaction.commit();
    }

    private void convertToBPFragment() {
        setNotClickStyle();
        current_fragment = ID_FRAGMENT_BP;
        setClickStyle();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, bpFragment);
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




    public  interface ChangeDeviceNameCallbackListener{
        void onChange(String s);
    }

    /**
     * this method called after current_fragment modified
     */
    private void setClickStyle(){
        TextView tv = (TextView) self.findViewById(tv_RID[current_fragment]);
        tv.setTextColor(MyApplication.getContext().getResources().getColor(R.color.darkturquoise));
    }

    /**
     * this method called before current_fragment modified
     */
    private void setNotClickStyle(){
        TextView tv = (TextView) self.findViewById(tv_RID[current_fragment]);
        tv.setTextColor(MyApplication.getContext().getResources().getColor(R.color.gray));
    }

    protected void disConnect(DataCollector dataCollector){
        if(isConnected(dataCollector)) {
            dataCollector.close();
            current_choose_device = null;
            ToastUtil.showShort("连接已断开");
        }
    }
}
