package com.bishe.lzj.myhealth.UI.Userinfo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bishe.lzj.myhealth.Bean.UserHealth;
import com.bishe.lzj.myhealth.Logic.DataSender.VolleyDataSender;
import com.bishe.lzj.myhealth.MyApplication;
import com.bishe.lzj.myhealth.R;
import com.bishe.lzj.myhealth.Util.LogUtil;
import com.bishe.lzj.myhealth.Util.ToastUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by lzj on 2016/5/10.
 */
public class UserHealthFragment extends Fragment{

    private static final String TAG = "UserHealthFragment";
    @InjectView(R.id.et_shengao)
    protected EditText et_shengao;

    @InjectView(R.id.et_tizhong)
    protected  EditText et_tizhong;

    @InjectView(R.id.rg_jiazu)
    protected RadioGroup rg_jiazu;

    @InjectView(R.id.rg_chuanran)
    protected RadioGroup rg_chuanran;

    @InjectView(R.id.et_qita)
    protected EditText et_qita;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_healthinfo,container,false);
        ButterKnife.inject(this,view);
        UserHealth userHealth = MyApplication.getUser().getUserHealth();
        if(userHealth.getHeight() != 0)
            et_shengao.setText(""+userHealth.getHeight());

        if(userHealth.getWeight() != 0 )
            et_tizhong.setText(""+userHealth.getWeight());

        if(userHealth.getJiazu() == 0)
            rg_jiazu.check(R.id.rb_wu);
        else
            rg_jiazu.check(R.id.rb_you);

        if(userHealth.getChuanran() == 0)
            rg_chuanran.check(R.id.rb_wu2);
        else
            rg_chuanran.check(R.id.rb_you2);

        if(userHealth.getQita() != null)
            et_qita.setText(userHealth.getQita());

        return view;
    }



    public void save() {
        final int id = MyApplication.getUser().getId();
        final double height;
        final double weight;
        try {
            height = Double.valueOf(et_shengao.getText().toString());
            weight = Double.valueOf(et_tizhong.getText().toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            ToastUtil.showShort("身高或体重错误");
            return ;
        }
        final int chuanran = rg_chuanran.getCheckedRadioButtonId()==R.id.rb_wu2?0:1;
        final int jiazu = rg_jiazu.getCheckedRadioButtonId()==R.id.rb_wu?0:1;
        final String qita = et_qita.getText().toString();

        String url = VolleyDataSender.getBaseURL()+"/account/updatehealth.do";
        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtil.w(TAG,s);
                if(s.equals("true")) {
                    ToastUtil.showShort("更新成功");
                    ToastUtil.showShort("weight: "+weight);
                    MyApplication.getUser().setUserHealth(new UserHealth(id, qita, jiazu, chuanran, weight, height));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ToastUtil.showShort("更新失败");
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("id",id+"");
                map.put("chuanran",chuanran+"");
                map.put("height",height+"");
                map.put("jiazu",jiazu+"");
                map.put("qita",qita);
                map.put("weight",weight+"");
                return map;
            }
        };

        VolleyDataSender.getmQueue().add(sr);

    }

}
