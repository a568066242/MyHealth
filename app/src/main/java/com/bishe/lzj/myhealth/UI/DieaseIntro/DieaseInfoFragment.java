package com.bishe.lzj.myhealth.UI.DieaseIntro;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bishe.lzj.myhealth.Bean.DieaseInfo;
import com.bishe.lzj.myhealth.Logic.DataSender.VolleyDataSender;
import com.bishe.lzj.myhealth.R;
import com.bishe.lzj.myhealth.Util.LogUtil;
import com.bishe.lzj.myhealth.Util.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Created by lzj on 2016/5/10.
 */
public class DieaseInfoFragment extends BaseDieaseIntroFragment {

    private static final String TAG = "DieaseInfoFragment";
    private final int id;
    private TextView tv_name;
    private TextView tv_position;
    private TextView tv_department;
    private TextView tv_reason;
    private TextView tv_bodysym;
    private TextView tv_symptoms;
    private TextView tv_prevent;
    private DieaseInfo dieaseInfo;

    public DieaseInfoFragment(int id){
        LogUtil.i(TAG,"id is "+id);
        this.id = id;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_diease_info,container,false);
        tv_bodysym = (TextView) view.findViewById(R.id.tv_bodysym);
        tv_department = (TextView) view.findViewById(R.id.tv_department);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_position = (TextView) view.findViewById(R.id.tv_position);
        tv_prevent = (TextView) view.findViewById(R.id.tv_prevent);
        tv_reason = (TextView) view.findViewById(R.id.tv_symptoms);
        tv_symptoms = (TextView) view.findViewById(R.id.tv_reason);
        get();
        return view;
    }


    @Override
    protected void get() {
        StringRequest sr = new StringRequest(VolleyDataSender.getBaseURL() + "/diease" + getParamUrl(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        LogUtil.i(TAG,"thread is :"+Thread.currentThread().getName());
                        Gson gson = new Gson();
                        dieaseInfo = gson.fromJson(s, new TypeToken<DieaseInfo>(){}.getType());
                        tv_name.setText(dieaseInfo.getName());
                        tv_symptoms.setText(dieaseInfo.getSymptoms());
                        tv_reason.setText(dieaseInfo.getReaseon());
                        tv_prevent.setText(dieaseInfo.getPrevent());
                        tv_bodysym.setText(dieaseInfo.getBodysym());
                        tv_department.setText(dieaseInfo.getDepartment());
                        tv_position.setText(dieaseInfo.getPosition());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ToastUtil.showShort("请求数据失败");
            }
        });
        Volley.newRequestQueue(getActivity()).add(sr);
    }

    @Override
    protected String getParamUrl() {
        return "/dieaseinfo.do?id=" + id;
    }

    @Override
    protected BaseDieaseIntroFragment getSubFragment(int id) {
        return null;
    }
}
