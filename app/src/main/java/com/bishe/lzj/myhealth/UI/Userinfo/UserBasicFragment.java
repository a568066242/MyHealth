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
import com.bishe.lzj.myhealth.Bean.UserBasic;
import com.bishe.lzj.myhealth.Logic.DataSender.VolleyDataSender;
import com.bishe.lzj.myhealth.MyApplication;
import com.bishe.lzj.myhealth.R;
import com.bishe.lzj.myhealth.UI.RegisterActivity;
import com.bishe.lzj.myhealth.Util.LogUtil;
import com.bishe.lzj.myhealth.Util.ToastUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by lzj on 2016/5/10.
 */
public class UserBasicFragment extends Fragment {

    private static final String TAG = "UserBasicFragment";
    @InjectView(R.id.et_name)
    protected EditText et_name;

    @InjectView(R.id.et_phone)
    protected EditText et_phone;

    @InjectView(R.id.et_age)
    protected EditText et_age;

    @InjectView(R.id.rg_sex)
    protected RadioGroup rg_sex;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_basicinfo, null);
        ButterKnife.inject(this, view);
        UserBasic userBasic = MyApplication.getUser().getUserBasic();
        et_name.setText(userBasic.getName());
        et_phone.setText(userBasic.getPhone());
        et_age.setText(userBasic.getAge()+"");
        if(userBasic.getSex().equals("男"))
            rg_sex.check(R.id.rb_male);
        else
            rg_sex.check(R.id.rb_female);
        return view;
    }

    public void save() {
        final int id = MyApplication.getUser().getId();
        final String phone = et_phone.getText().toString();
        final String name = et_name.getText().toString();
        final String age = et_age.getText().toString();
        final String sex = rg_sex.getCheckedRadioButtonId()==R.id.rb_male?"男":"女";
        LogUtil.i(TAG,phone+name+age+sex);
        if(!RegisterActivity.check(name,age,phone)){
            return;
        }
        int age_n = -1;
        try {
            age_n = Integer.valueOf(age);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            ToastUtil.show("年龄格式错误", ToastUtil.LENGH_SHORT);
            return;
        }
        if(age_n <  0){
            ToastUtil.show("年龄格式错误",ToastUtil.LENGH_SHORT);
            return;
        }

        final int finalAge_n = age_n;
        String url = VolleyDataSender.getBaseURL() + "/account" + "/updatebasic.do";
        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if(s.equals("true")) {
                    ToastUtil.showShort("上传更新成功");
                    MyApplication.getUser().setUserBasic(new UserBasic(id,name,sex, finalAge_n,phone));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ToastUtil.showShort("上传失败");
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("id",id+"");
                map.put("age",age);
                map.put("name",name);
                map.put("phone",phone);
                map.put("sex",sex);
                return map;
            }
        };
        LogUtil.d(TAG,url);
        VolleyDataSender.getmQueue().add(sr);
    }
}
