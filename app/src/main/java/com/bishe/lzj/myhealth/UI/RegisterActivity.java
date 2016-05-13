package com.bishe.lzj.myhealth.UI;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bishe.lzj.myhealth.Bean.User;
import com.bishe.lzj.myhealth.Bean.UserBasic;
import com.bishe.lzj.myhealth.Logic.DataSender.DataSender;
import com.bishe.lzj.myhealth.Logic.DataSender.Impl.BasicDataSender;
import com.bishe.lzj.myhealth.R;
import com.bishe.lzj.myhealth.Util.LogUtil;
import com.bishe.lzj.myhealth.Util.ToastUtil;

public class RegisterActivity extends BaseActivity {
    private static final String TAG = "RegisterActivity";
    EditText et_username;

    EditText et_password;

    EditText et_password_confirm;

    EditText et_name;

    EditText et_age;

    EditText et_phone;

    RadioGroup rg_sex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        intiViews();
    }

    private void intiViews() {
        et_age = (EditText) findViewById(R.id.et_age);
        et_name = (EditText) findViewById(R.id.et_name);
        et_password = (EditText) findViewById(R.id.et_password);
        et_password_confirm = (EditText) findViewById(R.id.et_password_confirm);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_username = (EditText) findViewById(R.id.et_username);
        rg_sex = (RadioGroup) findViewById(R.id.rg_sex);

        //back button
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //register button
        findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = et_username.getText().toString().trim();
                String password = et_password.getText().toString().trim();
                String password_confirm = et_password_confirm.getText().toString().trim();
                String name = et_name.getText().toString().trim();
                String phone = et_phone.getText().toString().trim();
                String age = et_age.getText().toString().trim();
                String sex = null;
                int checkedRadioButtonId = rg_sex.getCheckedRadioButtonId();
                switch (checkedRadioButtonId) {
                    case R.id.rb_female:
                        sex = "女";
                        break;
                    case R.id.rb_male:
                        sex = "男";
                        break;
                }

                register(username, password, password_confirm, name, phone, age, sex);

            }
        });

    }

    private void register(String username, String password, String password_confirm, String name, String phone, String age, String sex) {
        LogUtil.i(TAG,username);
        LogUtil.i(TAG,password);
        LogUtil.i(TAG,password_confirm);
        LogUtil.i(TAG,name);
        LogUtil.i(TAG,phone);
        LogUtil.i(TAG,sex);
        LogUtil.i(TAG, age);
        if(!check(username, password, password_confirm, name, phone, age))
            return;

        //send request
        int age_n = 0;

        try {
            age_n = Integer.valueOf(age);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            ToastUtil.show("年龄格式错误", ToastUtil.LENGH_SHORT);
            return;
        }
        LogUtil.i(TAG, "age_n:" + age_n);
        if(age_n <  0){
            ToastUtil.show("年龄格式错误",ToastUtil.LENGH_SHORT);
            return;
        }

        BasicDataSender.registerNewUser(new User(username, password), new UserBasic(name, sex, age_n, phone),
                new DataSender.FinishedCallbackListener() {
                    @Override
                    public void onFinished(Bundle bundle) {
                        if (bundle.getBoolean("result", false)) {//register success
                            ToastUtil.show("注册成功！", ToastUtil.LENGH_SHORT);
                            finish();
                        } else {
                            //failure
                            ToastUtil.show("此用户名已经注册过啦，请换个用户名", ToastUtil.LENGH_SHORT);
                        }

                    }
                }, new DataSender.ErrorCallbackListener() {
                    @Override
                    public void onError(String error) {
                        //error
                        ToastUtil.show("服务器出现了点问题，请稍后再试！", ToastUtil.LENGH_SHORT);
                    }
                });
    }





    public static boolean check(String username, String password, String password_confirm, String name, String phone, String age) {
        //check
        if(TextUtils.isEmpty(username)) {
            ToastUtil.show("请输入用户名", ToastUtil.LENGH_SHORT);
            return false;
        }

        if(TextUtils.isEmpty(password)){
            ToastUtil.show("请输入密码", ToastUtil.LENGH_SHORT);
            return false;
        }

        if(TextUtils.isEmpty(password_confirm) || !password.equals(password_confirm)){
            ToastUtil.show("请确保两次密码一致",ToastUtil.LENGH_SHORT);
            return false;
        }

        return check(name,age,phone);
    }


    public static boolean check(String name,String age,String phone){
        if(TextUtils.isEmpty(name)){
            ToastUtil.show("请输入姓名", ToastUtil.LENGH_SHORT);
            return false;
        }

        if(TextUtils.isEmpty(age)){
            ToastUtil.show("请输入年龄", ToastUtil.LENGH_SHORT);
            return false;
        }

        if(TextUtils.isEmpty(phone)){
            ToastUtil.show("请输入手机号",ToastUtil.LENGH_SHORT);
            return false;
        }
        return true;
    }

    /**
     * after send register web request can call this to get bundle
     * @param isRegisterSuccess
     * @return
     */
    public  static Bundle afterRegisterBundle(boolean isRegisterSuccess){
        Bundle bundle = new Bundle();
        bundle.putBoolean("result",isRegisterSuccess);
        return bundle;
    }


}
