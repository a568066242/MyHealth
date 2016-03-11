package com.bishe.lzj.similatedevice;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/**
 * Created by lzj on 2016/3/1.
 */
public class CollectOSDialog extends CollectDialog {

    private EditText et_os;

    protected CollectOSDialog(Context context, OKCallbackListener callbackListener) {
        super(context,callbackListener);
    }

    @Override
    protected void set() {
        setTitle("模拟收集血压");
        View view = getLayoutInflater().inflate(R.layout.dialog_collect_os,null);
        et_os = (EditText) view.findViewById(R.id.et_os);
        setView(view);

    }

    @Override
    protected Bundle getData() {
        Bundle bundle = new Bundle();
        int os = Integer.valueOf(et_os.getText().toString());
        bundle.putInt("os",os);
        return bundle;
    }
}
