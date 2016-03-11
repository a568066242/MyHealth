package com.bishe.lzj.myhealth.Util;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.Toast;

import com.bishe.lzj.myhealth.MyApplication;
import com.bishe.lzj.myhealth.UI.ChooseBluetoothDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Created by lzj on 2016/2/22.
 */
public class BluetoothUtil {
    public static final java.util.UUID UUID = java.util.UUID.fromString("a60f35f0-b93a-11de-8a39-08002009c666");
    private static final String TAG = "BluetoothUtil";
    private static BluetoothAdapter mAdapter = BluetoothAdapter.getDefaultAdapter();
    private static List<BluetoothDevice> bluetoothDevices = new ArrayList<>();
    private static List<String> devices_name = new ArrayList<>();
    private static BroadcastReceiver receiver = new BluetoothReceiver();
    /**
     * open bluetooth
     */
    public static  void open()  {
        LogUtil.i(TAG,"open");
        if(mAdapter == null){
            LogUtil.e(TAG,"no bluetooth");
            Toast.makeText(MyApplication.getContext(),"对不起，改设备没有蓝牙系统！",Toast.LENGTH_LONG).show();
            return;
        }

        if(!mAdapter.isEnabled()) {
            Intent enable = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            enable.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            MyApplication.getContext().startActivity(enable);
        }
    }

    /**
     * judge bluetooth can use
     * @return
     */
    public static boolean isEnabled(){
        return mAdapter.isEnabled();
    }

    public static void cancelSearch(){
        if ((mAdapter.isDiscovering())) {
            mAdapter.cancelDiscovery();
        }
    }


    /**
     * serach other bluetooths
     */
    public static void search(){
        bluetoothDevices.clear();
        devices_name.clear();
        Set<BluetoothDevice> bondedDevices = mAdapter.getBondedDevices();
        if(bondedDevices.size() > 0)
            for(BluetoothDevice device:bondedDevices)
            {
                bluetoothDevices.add(device);
                devices_name.add(device.getName());
            }
        LogUtil.i(TAG,"search");
        mAdapter.startDiscovery();

    }



    /**
     * 注册蓝牙广播接受者
     */
    public static void registerBroadcast(){
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        MyApplication.getContext().registerReceiver(receiver,filter);
    }

    /**
     * 注销蓝牙广播接受者
     */
    public static void unRegisterBroadcast(){
        MyApplication.getContext().unregisterReceiver(receiver);
    }

    public static class BluetoothReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                LogUtil.i(TAG, device.getName());
                if(devices_name.indexOf(device.getName()) == -1){//未加入时添加
                    bluetoothDevices.add(device);
                    devices_name.add(device.getName());
                    ChooseBluetoothDialog.update();
                }
//                showDevices();
            }else if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)){
                LogUtil.i(TAG,"discovery finished");
                ChooseBluetoothDialog.updateAfterDiscoveryFisish();
            }

        }

    }


    public static List<BluetoothDevice> getBluetoothDevices() {
        return bluetoothDevices;
    }

    public static List<String> getDevices_name() {
        return devices_name;
    }
}
