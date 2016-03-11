package com.bishe.lzj.myhealth.UI.DataCollect;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bishe.lzj.myhealth.R;

/**
 * Created by lzj on 2016/3/2.
 */
public class TitleLinerLayout extends LinearLayout {
    private TextView tv_history;
    private TextView tv_collect;

    public TitleLinerLayout(Context context) {
        super(context);
    }

    public TitleLinerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.title_layout, this);
        tv_collect = (TextView) findViewById(R.id.tv_collect);
        tv_history = (TextView) findViewById(R.id.tv_history);
        String collect_name = attrs.getAttributeValue(null,"collect_name");
        String hitory_name = attrs.getAttributeValue(null,"history_name");
        tv_collect.setText(collect_name);
        tv_history.setText(hitory_name);


    }

    public TitleLinerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



}
