package com.bishe.lzj.similatedevice;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/**
 * Created by lzj on 2016/3/1.
 */
public class CollectPulseDialog extends CollectDialog {

    private EditText et_pulse;

    protected CollectPulseDialog(Context context, OKCallbackListener callbackListener) {
        super(context,callbackListener);


    }

    @Override
    protected void set() {
        setTitle("模拟收集血压");
        View view = getLayoutInflater().inflate(R.layout.dialog_collect_pulse,null);
        et_pulse = (EditText) view.findViewById(R.id.et_pulse);
        setView(view);

    }

    @Override
    protected Bundle getData() {
        Bundle bundle = new Bundle();
        int pulse = Integer.valueOf(et_pulse.getText().toString());
        bundle.putInt("pulse",pulse);
        return bundle;
    }
}
