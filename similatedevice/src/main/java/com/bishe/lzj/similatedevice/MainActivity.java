package com.bishe.lzj.similatedevice;

import android.app.Activity;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class MainActivity extends Activity {

    public static final String BLOODPRESSURE = "BLOODPRESSURE";
    public static final int BLOODPRESSURE_ID = 0x11;
    public static final String BLOODSUGAR = "BLOODSUGAR";
    public static final int BLOODSUGAR_ID = 0x12;
    public static final String PULSE = "PULSE";
    public static final int PULSE_ID = 0x13;
    public static final String OxygenSaturation = "OxygenSaturation";
    public static final int OxygenSaturation_ID = 0x14;
    public static final String START_TAG = "START";
    public static final String END_TAG = "END";
    public static final String SUCCESS_TAG = "SUCCESS";
    public static final String SERVER_NAME = "server";
    public static final UUID UUID = java.util.UUID.fromString("a60f35f0-b93a-11de-8a39-08002009c666");
    private static final String TAG = "MainActivity";
    private static final String  RESTART_TAG = "RESTART";
    private BluetoothAdapter defaultAdapter;
    private BluetoothServerSocket serverSocket;
    private BluetoothSocket socket;
    private Bundle data;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case BLOODPRESSURE_ID:
                    new CollectBPDialog(MainActivity.this, new CollectDialog.OKCallbackListener() {
                        @Override
                        public void onSend(Bundle b) {
                            synchronized (MainActivity.this){
                                Log.w(TAG,"onSend");
                                data = b;
                                MainActivity.this.notify();
                            }
                        }
                    });
                    break;
                case BLOODSUGAR_ID:
                    new CollectBSDialog(MainActivity.this, new CollectDialog.OKCallbackListener() {
                        @Override
                        public void onSend(Bundle b) {
                            synchronized (MainActivity.this){
                                Log.w(TAG,"onSend");
                                data = b;
                                MainActivity.this.notify();
                            }
                        }
                    });
                    break;
                case OxygenSaturation_ID:
                    new CollectOSDialog(MainActivity.this, new CollectDialog.OKCallbackListener() {
                        @Override
                        public void onSend(Bundle b) {
                            synchronized (MainActivity.this){
                                Log.w(TAG,"onSend");
                                data = b;
                                MainActivity.this.notify();
                            }
                        }
                    });
                    break;
                case PULSE_ID:
                    new CollectPulseDialog(MainActivity.this, new CollectDialog.OKCallbackListener() {
                        @Override
                        public void onSend(Bundle b) {
                            synchronized (MainActivity.this){
                                Log.w(TAG,"onSend");
                                data = b;
                                MainActivity.this.notify();
                            }
                        }
                    });
                    break;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "get bluetooth adapter");
        defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        openBlueTooth();
        Log.i(TAG, "new thread");
        findViewById(R.id.btn_open_bluetooth_server).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new AcceptRunnable()).start();
            }
        });
    }

    private void openBlueTooth() {
        if(defaultAdapter != null && !defaultAdapter.isEnabled()){
            startActivity(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE));
        }
    }

    class AcceptRunnable implements Runnable {

        private static final String TAG = "AcceptRunnable";

        public AcceptRunnable() {
            Log.i(TAG,"constrocutor AcceptRunnable");
            BluetoothServerSocket tmp = null;
            try {
//               defaultAdapter.listenUsingRfcommWithServiceRecord(SERVER_NAME,UUID);
                tmp = defaultAdapter.listenUsingInsecureRfcommWithServiceRecord(SERVER_NAME,UUID);
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG,e.getMessage());

            }
            serverSocket = tmp;
        }

        @Override
        public void run() {
            Log.i(TAG, "in run");
            while (true){
                try {
                    socket = serverSocket.accept();//阻塞等待连接
                    Log.w(TAG,"accept "+socket.getRemoteDevice().getName());
                    if(socket!=null)
                        Log.i(TAG, "accept:" + socket.getRemoteDevice().getName());
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, e.getMessage());
                    break;
                }
                //有设备连接
                if (socket != null
                        //&& socket.isConnected() ??有些设备连接了却返回false
                        ){
                    try {

                        Log.i(TAG, "accept data from client");
                        InputStream inputStream = socket.getInputStream();
                        byte[] buffer = new byte[256];
                        String s = read(inputStream, buffer);
                        while (s == null){
                            s = read(inputStream,buffer);
                        }
                        Log.i(TAG,s);
                        switch (s){//判断是模拟什么设备
                            case BLOODPRESSURE:
                                sendSuccesss();//发送连接成功消息
                                while (canStart(inputStream)
                                     //   && socket.isConnected()
                                        ) {//只要连接并接受到开始或重新开始标志
                                    handler.sendMessage(Message.obtain(handler,BLOODPRESSURE_ID));

                                    synchronized (MainActivity.this){
                                        Log.w(TAG,"before wait");
                                        MainActivity.this.wait();//等待dialog唤醒
                                        Log.w(TAG,"after wait");
                                        int dbp = data.getInt("dbp");
                                        int sbp = data.getInt("sbp");
                                        boolean success = sendBloodPressure(socket.getOutputStream(),dbp,sbp);
                                        Log.i(TAG, "success：" + success);
                                        if (success) {
                                            Log.w(TAG, "send success");
                                            continue;
                                        }
                                        else {
                                            Log.e(TAG, "send failure and close this socket");
                                            closeSocket();
                                        }
                                    }
                                }
                                break;
                            case BLOODSUGAR:
                                sendSuccesss();//发送连接成功消息
                                while (canStart(inputStream)
                                    //   && socket.isConnected()
                                        ) {//只要连接并接受到开始或重新开始标志
                                    handler.sendMessage(Message.obtain(handler,BLOODSUGAR_ID));

                                    synchronized (MainActivity.this){
                                        Log.w(TAG,"before wait");
                                        MainActivity.this.wait();//等待dialog唤醒
                                        Log.w(TAG,"after wait");
                                        double bs = data.getDouble("bs");
                                        boolean success = sendBloodSugar(socket.getOutputStream(), bs);
                                        Log.i(TAG, "success：" + success);
                                        if (success) {
                                            Log.w(TAG, "send success");
                                            continue;
                                        }
                                        else {
                                            Log.e(TAG, "send failure and close this socket");
                                            closeSocket();
                                        }
                                    }
                                }
                                break;
                            case OxygenSaturation:
                                sendSuccesss();//发送连接成功消息
                                while (canStart(inputStream)
                                    //   && socket.isConnected()
                                        ) {//只要连接并接受到开始或重新开始标志
                                    handler.sendMessage(Message.obtain(handler,OxygenSaturation_ID));

                                    synchronized (MainActivity.this){
                                        Log.w(TAG,"before wait");
                                        MainActivity.this.wait();//等待dialog唤醒
                                        Log.w(TAG,"after wait");
                                        int os = data.getInt("os");
                                        boolean success = sendOxygenSaturation(socket.getOutputStream(), os);
                                        Log.i(TAG, "success：" + success);
                                        if (success) {
                                            Log.w(TAG, "send success");
                                            continue;
                                        }
                                        else {
                                            Log.e(TAG, "send failure and close this socket");
                                            closeSocket();
                                        }
                                    }
                                }
                                break;
                            case PULSE:
                                sendSuccesss();//发送连接成功消息
                                while (canStart(inputStream)
                                    //   && socket.isConnected()
                                        ) {//只要连接并接受到开始或重新开始标志
                                    handler.sendMessage(Message.obtain(handler,PULSE_ID));

                                    synchronized (MainActivity.this){
                                        Log.w(TAG,"before wait");
                                        MainActivity.this.wait();//等待dialog唤醒
                                        Log.w(TAG,"after wait");
                                        int pulse = data.getInt("pulse");
                                        boolean success = sendPulse(socket.getOutputStream(), pulse);
                                        Log.i(TAG, "success：" + success);
                                        if (success) {
                                            Log.w(TAG, "send success");
                                            continue;
                                        }
                                        else {
                                            Log.e(TAG, "send failure and close this socket");
                                            closeSocket();
                                        }
                                    }
                                }
                                break;

                            default:
                                Log.e(TAG,"not correct client,close socket");
                                closeSocket();
                                break;
                        }

                    } catch (IOException e) {
                        Log.e(TAG,e.getMessage());
                        e.printStackTrace();
                        Log.e(TAG, "read info failure,close this socket");
                        try {
                            closeSocket();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else{
                    Log.e(TAG,"socket not connect?");
                }
            }


        }

        private void closeSocket() throws IOException {
            if(socket != null)
                socket.close();
        }
    }




    private void sendSuccesss() throws IOException {
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(SUCCESS_TAG.getBytes());
    }

    @NonNull
    private String read(InputStream inputStream, byte[] buffer) throws IOException {
        int num = inputStream.read(buffer);
        if(num != -1)
            return new String(buffer,0,num);
        return null;
    }

    /**
     * 是否收到开始收集数据并发送的消息
     * @param inputStream
     * @return
     */
    private boolean canStart(InputStream inputStream){
        String s = null;
        byte[] buffer = new byte[256];
        try {
            s = read(inputStream,buffer);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        if(START_TAG.equals(s) || RESTART_TAG.equals(s)) {
            return true;
        }else {
            return false;
        }
    }

    private boolean sendPulse(OutputStream outputStream, int pulse) {
        Log.w(TAG,"into sendBloodPressure method");
        //send data
        try {
            outputStream.write(String.format("%d", pulse).getBytes());
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //send结束消息
        try {
            outputStream.write(END_TAG.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private boolean sendOxygenSaturation(OutputStream outputStream, int os) {
        Log.w(TAG,"into sendBloodPressure method");
        //send data
        try {
            outputStream.write(String.format("%d",os).getBytes());
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //send结束消息
        try {
            outputStream.write(END_TAG.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private boolean sendBloodSugar(OutputStream outputStream, double bs) {
        Log.w(TAG,"into sendBloodPressure method");
        //send data
        try {
            outputStream.write(String.format("%f", bs).getBytes());
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //send结束消息
        try {
            outputStream.write(END_TAG.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private boolean sendBloodPressure(OutputStream outputStream,int DBP,int SBP) {
        Log.w(TAG,"into sendBloodPressure method");
        //send data
        try {
            outputStream.write(String.format("%d:%d", DBP, SBP).getBytes());
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //send结束消息
        try {
            outputStream.write(END_TAG.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }

}
