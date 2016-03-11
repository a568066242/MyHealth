package com.bishe.lzj.similatedevice;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Message;

/**
 * Created by lzj on 2016/3/1.
 */
public abstract class CollectDialog extends AlertDialog {
    private OKCallbackListener callbackListener;

    protected CollectDialog(Context context, final OKCallbackListener callbackListener) {
        super(context);
        this.callbackListener = callbackListener;
        this.setCancelable(false);
        set();
        this.setButton(BUTTON_NEUTRAL, "确定", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(callbackListener != null) {
                    callbackListener.onSend(getData());
                }

                CollectDialog.this.dismiss();
            }
        });
        show();
    }

    public OKCallbackListener getCallbackListener() {
        return callbackListener;
    }

    /**
     * 设置标题与内容view界面
     */
    protected abstract void set();

    /**
     * 点击确定回调接口
     */
    interface OKCallbackListener{
        void onSend(Bundle b);
    };

    /**
     * 获取输入在模拟对话框上的数据
     * @return
     */
    protected abstract Bundle getData();

}
