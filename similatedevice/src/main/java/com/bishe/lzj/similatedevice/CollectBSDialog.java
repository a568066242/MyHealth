package com.bishe.lzj.similatedevice;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/**
 * Created by lzj on 2016/3/2.
 */
public class CollectBSDialog extends  CollectDialog {
    private EditText et_bs;


    protected CollectBSDialog(Context context, OKCallbackListener callbackListener) {
        super(context, callbackListener);
    }

    @Override
    protected void set() {
        setTitle("模拟收集血糖");
        View view = getLayoutInflater().inflate(R.layout.dialog_collect_bs, null);
        et_bs = (EditText) view.findViewById(R.id.et_bs);
        setView(view);

    }

    @Override
    protected Bundle getData() {
        Bundle bundle = new Bundle();
        double bs = Double.valueOf(et_bs.getText().toString());
        bundle.putDouble("bs", bs);
        return bundle;
    }
}
