package com.bishe.lzj.similatedevice;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * Created by lzj on 2016/3/1.
 */
public class CollectBPDialog extends CollectDialog {

    private EditText et_DBP;
    private EditText et_SBP;

    protected CollectBPDialog(Context context,OKCallbackListener callbackListener) {
        super(context,callbackListener);


    }

    @Override
    protected void set() {
        setTitle("模拟收集血压");
        View view = getLayoutInflater().inflate(R.layout.dialog_collect_bp,null);
        et_DBP = (EditText) view.findViewById(R.id.et_DBP);
        et_SBP = (EditText) view.findViewById(R.id.et_SBP);
        setView(view);

    }

    @Override
    protected Bundle getData() {
        Bundle bundle = new Bundle();
        int dbp = Integer.valueOf(et_DBP.getText().toString());
        int sbp = Integer.valueOf(et_SBP.getText().toString());
        bundle.putInt("dbp",dbp);
        bundle.putInt("sbp",sbp);
        return bundle;
    }
}
