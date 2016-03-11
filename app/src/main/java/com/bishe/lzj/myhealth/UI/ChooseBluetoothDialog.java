package com.bishe.lzj.myhealth.UI;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bishe.lzj.myhealth.MyApplication;
import com.bishe.lzj.myhealth.R;
import com.bishe.lzj.myhealth.UI.DataCollect.DataCollectActivity;
import com.bishe.lzj.myhealth.Util.BluetoothUtil;
import com.bishe.lzj.myhealth.Util.LogUtil;

/**
 * Created by lzj on 2016/2/25.
 */
public class ChooseBluetoothDialog extends AlertDialog implements View.OnClickListener {

    private static final String TAG = "ChooseBluetoothDialog";
    private RecyclerView recyclerView;
    private static final String TITLE = "请选择一个设备";
    private Context context;
    private static BluetoothAdapter adapter;
    private static TextView tv_search_show;
    private static ProgressBar progressBar;
    private static ImageView iv_search;
    private ChoosedCallbackListener choosedCallbackListener;
    public ChooseBluetoothDialog(Context context,ChoosedCallbackListener choosedCallbackListener) {
        super(context);
        this.context = context;
        this.choosedCallbackListener = choosedCallbackListener;
        init();
    }



    private void init() {
        LogUtil.i(TAG, "init");
        setTitle(TITLE);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_bluetoothchoose, null);
        recyclerView = (RecyclerView) view.findViewById(R.id.bluetooth_choose);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter = new BluetoothAdapter();
        recyclerView.setAdapter(adapter);

        setView(view);
        show();
        tv_search_show = (TextView) view.findViewById(R.id.tv_search_info);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        iv_search = (ImageView) findViewById(R.id.iv_search);
        iv_search.setOnClickListener(this);
    }

    public ChooseBluetoothDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public ChooseBluetoothDialog(Context context, int theme) {
        super(context, theme);
    }


    public static void update() {
        if(adapter  != null)
            adapter.notifyDataSetChanged();
    }

    public static void updateAfterDiscoveryFisish() {
        if(progressBar!=null) {
            progressBar.setVisibility(View.GONE);
            iv_search.setVisibility(View.VISIBLE);
        }
        if(tv_search_show !=null)
          tv_search_show.setText("搜索完毕,请选择设备");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_search://click search image
                tv_search_show.setText(R.string.is_searching);
                progressBar.setVisibility(View.VISIBLE);
                iv_search.setVisibility(View.GONE);
                BluetoothUtil.search();
                break;
        }
    }

    class BluetoothAdapter extends RecyclerView.Adapter<BluetoothAdapter.MyViewHolder>{

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new MyViewHolder(LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1
                                                                    ,null));
        }

        @Override
        public void onBindViewHolder(MyViewHolder myViewHolder, final int i) {
            myViewHolder.tv_bluetooth_name.setText(BluetoothUtil.getDevices_name().get(i));
            myViewHolder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LogUtil.i(TAG, BluetoothUtil.getBluetoothDevices().get(i).toString());
                    BluetoothUtil.cancelSearch();


                    if(choosedCallbackListener != null)
                        choosedCallbackListener.onChoosed(i);
                    ChooseBluetoothDialog.this.dismiss();

                }
            });
        }

        @Override
        public int getItemCount() {
            return BluetoothUtil.getDevices_name().size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder{
            View view;
            TextView tv_bluetooth_name;
            public MyViewHolder(View itemView) {
                super(itemView);
                view = itemView;
                tv_bluetooth_name = (TextView) itemView.findViewById(android.R.id.text1);
            }

        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtil.i(TAG, "stop");
        BluetoothUtil.cancelSearch();
    }

    public interface ChoosedCallbackListener{
        void onChoosed(int position);
    }

}
