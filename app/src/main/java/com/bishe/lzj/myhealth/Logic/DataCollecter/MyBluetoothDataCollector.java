package com.bishe.lzj.myhealth.Logic.DataCollecter;

import android.bluetooth.BluetoothSocket;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.bishe.lzj.myhealth.Util.LogUtil;
import com.bishe.lzj.myhealth.Util.ToastUtil;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * Created by lzj on 2016/2/25.
 */
public abstract class MyBluetoothDataCollector implements DataCollector{

    private  final String TAG = getClass().getSimpleName();
    private final String SUCCESS_TAG = "SUCCESS";
    private final String START_TAG = "START";
    private final String END_TAG = "END";
    private  State state = new State();
    private BluetoothSocket socket;
    private InputStream in;
    private OutputStream out;

    public MyBluetoothDataCollector(){}

    public MyBluetoothDataCollector(BluetoothSocket socket) throws IOException {
        setSocket(socket);
    }

    public void setSocket(BluetoothSocket socket) throws IOException {
        this.socket = socket;
        in = socket.getInputStream();
        out = socket.getOutputStream();
    }

    public InputStream getIn() {
        return in;
    }

    public OutputStream getOut() {
        return out;
    }

    public BluetoothSocket getSocket() {
        return socket;
    }




    @Override
    public void ready() {
        if(!getState().isNotConnect()) {
            LogUtil.w(TAG,"state:  "+getState().getInfoByState(getState().current_state));
            return;
        }

        //连接时会阻塞进程，因此用异步任务类执行连接，且方便更新界面
        new AsyncTask<Void,Integer,Integer>(){

            public  static final int CONNECT_FAILURE = 0x11;
            public  static final int READ_FAILURE = 0x12;
            public  static final int WRITE_FAILURE = 0x13;
            public  static final int CONNECT_SUCCESS = 0x14;
            public  static final int UNKNOWN_DEVICE = 0x15;

            @Override
            protected void onPostExecute(Integer stateID) {
                super.onPostExecute(stateID);
                switch (stateID){
                    case CONNECT_FAILURE:
                        ToastUtil.showShort("连接失败");
                        getState().setStateNotConnect();
                        break;
                    case READ_FAILURE:
                        ToastUtil.showShort("无法从此设备接收数据，请重新连接");
                        getState().setStateNotConnect();
                        break;
                    case WRITE_FAILURE:
                        ToastUtil.showShort("无法向此设备传输数据，请重新连接");
                        getState().setStateNotConnect();
                        break;
                    case UNKNOWN_DEVICE:
                        getState().setStateNotConnect();
                        ToastUtil.showShort("此设备无法测量，请重新选择设备连接");
                        break;
                    case CONNECT_SUCCESS:
                        ToastUtil.showShort("连接成功！");
                        getState().setCurrent_state(State.STATE_CONNECTED);
                        LogUtil.w(TAG,"连接成功，状态自动从成功转入未开始接收数据");
//                        getState().setCurrent_state(State.STATE_NOT_ACCEPT);
                        break;

                }
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
            }

            @Override
            protected Integer doInBackground(Void... params) {
                LogUtil.w(TAG,"connect start");
                //connect
                try {
                    getState().setCurrent_state(State.STATE__CONNECTING);
                    LogUtil.w(TAG, "try connect");
                    socket.connect();
                } catch (IOException e) {
                    getState().setStateNotConnect();
                    e.printStackTrace();
                    LogUtil.e(TAG, e.getMessage());
    //                ToastUtil.show("连接失败", ToastUtil.LENGH_SHORT);
                    close();
                    return CONNECT_FAILURE;
                }
                //send tag
                OutputStream out = getOut();
                try {
                    LogUtil.i(TAG, "send " + getFlag());
                    out.write(getFlag().getBytes());
                    out.flush();
                } catch (IOException e) {
                    getState().setStateNotConnect();
                    LogUtil.e(TAG,e.getMessage());
                    e.printStackTrace();
//                    ToastUtil.show("无法传输,请重新连接",ToastUtil.LENGH_SHORT);

                    close();
                    return WRITE_FAILURE;
                }

                //access return tag
                InputStream in = getIn();
                byte[] buffer = new byte[256];
                String return_tag ;
                try {
                    return_tag = read(in, buffer);
                    LogUtil.w(TAG,return_tag);
                } catch (IOException e) {
                    getState().setStateNotConnect();
                    close();
                    e.printStackTrace();
 //                   ToastUtil.show("无法接收数据，请重新连接",ToastUtil.LENGH_SHORT);
                    return READ_FAILURE;
                }


                if(SUCCESS_TAG.equals(return_tag))
                {
                    LogUtil.w(TAG,"连接成功");
//                    ToastUtil.show("连接成功",ToastUtil.LENGH_SHORT);
                    getState().setCurrent_state(State.STATE_CONNECTED);
                    getState().setCurrent_state(State.STATE_NOT_ACCEPT);
                    return CONNECT_SUCCESS;
                }else{
                    LogUtil.w(TAG, "连接失败");
                    getState().setStateNotConnect();
                    close();
                    return UNKNOWN_DEVICE;
//                    ToastUtil.show("连接失败，请选择正确设备连接",ToastUtil.LENGH_SHORT);
                }

            }
        }.execute();

    }

    @NonNull
    private String read(InputStream in, byte[] buffer) throws IOException {
        String return_tag;
        int num;
        num = in.read(buffer);
        return_tag = new String(buffer,0,num);
        return return_tag;
    }

    @Override
    public void accept(final FinishCallbackListener finishCallbackListener) {
        if(getState().isNotAccept() || getState().isAccepted()) {
            //若设备处于未测量数据或者已测量完一组数据，可以测量或重新测量
            new AsyncTask<Void,Integer,Integer>(){

                public static final int CONNECT_DISRUPT = 0x21;//连接中断
                public static final int ACCEPT_SUCCESS = 0x22;//成功接收数据
                public static final int DATA_EXCEPTION = 0x23;//接收到的数据异常


                @Override
                protected void onPostExecute(Integer integer) {
                    super.onPostExecute(integer);
                    switch (integer){
                        case CONNECT_DISRUPT:
                            ToastUtil.showShort("连接中断请重新连接");
                            getState().setStateNotConnect();
                            close();
                            break;
                        case ACCEPT_SUCCESS:
                            ToastUtil.showShort("数据成功采集");
                            getState().setCurrent_state(State.STATE_ACCEPTED);
                            break;
                        case DATA_EXCEPTION:
                            ToastUtil.showShort("接收到的数据不完整，请重测");
                            getState().setCurrent_state(State.STATE_NOT_ACCEPT);
                            break;
                    }
                }

                @Override
                protected Integer doInBackground(Void... params) {
                    if (getState().isConnected()) {//已经连接设备
                        LogUtil.w(TAG, "has connected");
                        LogUtil.w(TAG, "accept data");
                        getState().setCurrent_state(State.STATE_ACCEPTING);
                        OutputStream out = getOut();
                        try {
                            LogUtil.w(TAG, "send start tag");
                            out.write(START_TAG.getBytes());
                        } catch (IOException e) {
                            LogUtil.e(TAG, "连接中断，请重新连接");
                            //     ToastUtil.show("连接中断，请重新连接",ToastUtil.LENGH_SHORT);
                            getState().setStateNotConnect();
                            e.printStackTrace();
                            return CONNECT_DISRUPT;
                        }


                        InputStream in = getIn();
                        StringBuilder result = new StringBuilder();
                        //accept data
                        while (true) {
                            byte[] buffer = new byte[256];
                            String s = null;
                            try {
                                s = read(in, buffer);
                                LogUtil.w(TAG, s);
                            } catch (IOException e) {
                                e.printStackTrace();
                                getState().setStateNotConnect();
                                close();
                                LogUtil.e(TAG, "连接中断，请重新连接");
                                return CONNECT_DISRUPT;
                                //     ToastUtil.show("连接中断，请重新连接",ToastUtil.LENGH_SHORT);
                            }
                            if (END_TAG.equals(s)) {
                                getState().setCurrent_state(State.STATE_ACCEPTED);
                                //   ToastUtil.show("数据传输完毕",ToastUtil.LENGH_SHORT);
                                LogUtil.i(TAG, "数据传输完毕");
                                break;
                            }
                            result.append(s);
                        }
                        String data = result.toString();
                        boolean ok = checkData(data);
                        if(!ok) {
                            getState().setCurrent_state(State.STATE_NOT_ACCEPT);
                            return DATA_EXCEPTION;
                        }
                        handleData(data, finishCallbackListener);
                        return ACCEPT_SUCCESS;
                    } else {//not connect
//                        ToastUtil.show("请先连接设备",ToastUtil.LENGH_SHORT);
                        LogUtil.i(TAG, "请先连接设备");
                        return CONNECT_DISRUPT;
                    }
                }
            }.execute();
        }
        else  if(getState().isAccepting()) {
            ToastUtil.show("正在采集数据", ToastUtil.LENGH_SHORT);
            LogUtil.i(TAG, "正在采集数据,请稍等");
        }
        else{
            LogUtil.i(TAG, "请先连接设备");
            ToastUtil.show("请先连接设备",ToastUtil.LENGH_SHORT);
        }
    }

    @Override
    public void close(){
        getState().setStateNotConnect();
        if(in!=null)
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        if(out !=null)
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        if(socket!= null)
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        in = null;
        out = null;
        socket = null;

    }


    protected abstract String getFlag();


    /**
     * 处理数据，由具体子类完成
     * @param data 数据
     * @param finishCallbackListener 完成数据采集回调函数
     */
    protected abstract void handleData(String data, FinishCallbackListener finishCallbackListener);

    /**
     * 检查数据是否完整
     * @param data 数据
     * @return
     */
    protected abstract boolean checkData(String data);

    public State getState() {
        return state;
    }

    /**
     * 设备状态类
     */
    public class State{

        public static final int STATE_NOT_CONNECT = 0;//未连接状态
        public static final int STATE__CONNECTING = 1;//正在连接状态
        public static final int STATE_CONNECTED = 2;//已连接状态
        public static final int STATE_NOT_ACCEPT = 3;//未测量数据状态
        public static final int STATE_ACCEPTING = 4;//正在测量数据状态
        public static final int STATE_ACCEPTED = 5;//已经测量完数据状态
        private int current_state = STATE_NOT_CONNECT;

        public int getCurrent_state() {
            return current_state;
        }

        synchronized public void setCurrent_state(int new_state) {
            //新状态与原状态一致时，不做任何操作
            if(current_state == new_state)
                return;
           switch (current_state)
           {
               case STATE_NOT_CONNECT:
                   if(new_state == STATE__CONNECTING) {//从未连接到正在连接
                       LogUtil.w(TAG,getMessage(current_state,new_state));
                       current_state = new_state;
                   }
                   else{
                       LogUtil.e(TAG,getErrorMessage(current_state,new_state));
                   }
                   break;
               case STATE__CONNECTING:
                   if(new_state == STATE_CONNECTED)//从正在连接到已经连接
                       current_state = new_state;
                   else{
                       LogUtil.e(TAG,getErrorMessage(current_state,new_state));
                   }
                   break;
               case STATE_CONNECTED:
                   if(new_state == STATE_NOT_ACCEPT)//从已连接到未测量数据
                       current_state = new_state;
                   else{
                       LogUtil.e(TAG,getErrorMessage(current_state,new_state));
                   }
                   break;
               case STATE_NOT_ACCEPT:
                   if(new_state == STATE_ACCEPTING)//从未测数据到正在测数据
                       current_state = new_state;
                   else{
                       LogUtil.e(TAG,getErrorMessage(current_state,new_state));
                   }
                   break;
               case STATE_ACCEPTING:
                   if(new_state == STATE_ACCEPTED)//从正在接受数据到已接受完数据
                       current_state = new_state;
                   else{
                       LogUtil.e(TAG,getErrorMessage(current_state,new_state));
                   }
                   break;
               case STATE_ACCEPTED:
                   if(new_state == STATE_ACCEPTING || new_state == STATE_NOT_ACCEPT)
                       current_state = new_state;
                   else{
                       LogUtil.e(TAG,getErrorMessage(current_state,new_state));
                   }
                   break;
           }
        }

        private String getMessage(int current_state, int new_state) {
            return "from state:" +
                    getInfoByState(current_state)+" to state:"+getInfoByState(new_state);
        }

        public boolean isConnecting(){return current_state == STATE__CONNECTING;}

        public boolean isConnected()
        {
//            if(socket != null)
//                if(!socket.isConnected())  //此方法是判断曾经是否连接过
//                    setStateNotConnect();
            return current_state >= STATE_CONNECTED;
        }

        public boolean isNotConnect()
        {
            return current_state == STATE_NOT_CONNECT;
        }


        public boolean isNotAccept(){
            return current_state == STATE_NOT_ACCEPT;
        }

        public boolean isAccepting()
        {
            return current_state == STATE_ACCEPTING;
        }

        public boolean isAccepted()
        {
            return current_state == STATE_ACCEPTED;
        }

        private void setStateNotConnect(){
            current_state = STATE_NOT_CONNECT;
        }

        private String getErrorMessage(int current_state,int new_state){
            return "can't from state:" +
                    getInfoByState(current_state)+" to state:"+getInfoByState(new_state);
        }

        private String getInfoByState(int state){
            switch (state)
            {
                case STATE__CONNECTING:
                    return "STATE_CONNECTING";
                case STATE_NOT_CONNECT:
                    return "STATE_NOT_CONNECT";
                case STATE_CONNECTED:
                    return "STATE_CONNECTED";
                case STATE_NOT_ACCEPT:
                    return "STATE_NOT_ACCEPT";
                case STATE_ACCEPTED:
                    return "STATE_ACCEPTED";
                case STATE_ACCEPTING:
                    return "STATE_ACCEPTING";
                default:
                    return "unknown state";
            }
        }
    }

}
