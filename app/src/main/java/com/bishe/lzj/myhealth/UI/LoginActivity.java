package com.bishe.lzj.myhealth.UI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.bishe.lzj.myhealth.Bean.LoginInfo;
import com.bishe.lzj.myhealth.Logic.DataSender.DataSender;
import com.bishe.lzj.myhealth.Logic.DataSender.Impl.BasicDataSender;
import com.bishe.lzj.myhealth.Logic.Impl.LocalLoginLogicImpl;
import com.bishe.lzj.myhealth.Logic.LocalLoginLogic;
import com.bishe.lzj.myhealth.R;
import com.bishe.lzj.myhealth.Util.ToastUtil;

/**
 * Created by lzj on 2016/2/18.
 */
public class LoginActivity extends BaseActivity {
    private static final String USERNAME = "USERNAME";
    private static final String PWD = "PWD";
    private static final String ISREMEMBER = "ISREMEMBER";
    private static final String ISAUTO = "ISAUTO";
    private Button btn_login;
    private EditText et_username;
    private EditText et_password;
    private CheckBox cb_remember;
    private CheckBox cb_auto_login;
    private LocalLoginLogic localLoginLogic = new LocalLoginLogicImpl();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        initViews();

    }

    /**
     * init views in this activity
     */
    private void initViews() {

        btn_login = (Button) findViewById(R.id.btn_login);
        et_password = (EditText) findViewById(R.id.et_password);
        et_username = (EditText) findViewById(R.id.et_username);
        cb_auto_login = (CheckBox) findViewById(R.id.cb_auto_login);
        cb_remember = (CheckBox) findViewById(R.id.cb_remember_password);

        cb_auto_login.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true)//if set auto_login true,set remember check box true
                    cb_remember.setChecked(true);
            }
        });

        cb_remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == false)//if click remember cb false,set auto_login  check box false
                    cb_auto_login.setChecked(false);
            }
        });

        //setvalue
        String username = getIntent().getStringExtra(USERNAME);
        String pwd = getIntent().getStringExtra(PWD);
        boolean is_auto = getIntent().getBooleanExtra(ISAUTO,false);
        boolean is_remember = getIntent().getBooleanExtra(ISREMEMBER,false);

        if(username != null)
            et_username.setText(username);

        cb_auto_login.setChecked(is_auto);
        cb_remember.setChecked(is_remember);

        if(is_remember)
            if(pwd != null)
                 et_password.setText(pwd);

        //login button
        btn_login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {//check username and password
                final String userName = et_username.getText().toString().trim();
                if(TextUtils.isEmpty(userName)){
                    ToastUtil.show("请输入用户名",ToastUtil.LENGH_SHORT);
                    return;
                }
                final String pwd = et_password.getText().toString().trim();
                if(TextUtils.isEmpty(pwd)){
                    ToastUtil.show("请输入密码",ToastUtil.LENGH_SHORT);
                    return;
                }
                final boolean is_auto = cb_auto_login.isChecked();
                final boolean is_remember = cb_remember.isChecked();
                //send login web request
                BasicDataSender.login(userName, pwd, new DataSender.FinishedCallbackListener() {
                    @Override
                    public void onFinished(Bundle bundle) {


                        int id = -1;
                        if (bundle != null) {
                            id = bundle.getInt("id");
                        }
                        if (id < 0) {//login failuer{
                            ToastUtil.show("用户名或密码错误", ToastUtil.LENGH_SHORT);
                        } else {
                            //login success
                            localLoginLogic.saveLocalLoginInfo(new LoginInfo(is_auto,is_remember,userName,pwd));
                            ToastUtil.show("登陆成功", ToastUtil.LENGH_SHORT);
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                }, new DataSender.ErrorCallbackListener() {
                    @Override
                    public void onError(String error) {
                        ToastUtil.show("服务器出现了点问题，请稍后再试", ToastUtil.LENGH_SHORT);
                    }
                });


            }
        });

        findViewById(R.id.tv_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//open register ui
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

    }

    /**
     * other activities can by this method start this activity
     * @param context
     * @param username
     * @param pwd
     * @param isRemember
     */
    public static void actionStart(Context context,String username,String pwd,boolean isRemember,boolean isAuto){
        Intent i = new Intent(context,LoginActivity.class);
        i.putExtra(USERNAME,username);
        i.putExtra(PWD,pwd);
        i.putExtra(ISREMEMBER,isRemember);
        i.putExtra(ISAUTO, isAuto);
        context.startActivity(i);
    }


    /**
     * after send login web request can call this to get bundle
     * @param id
     */
    public static Bundle afterLogionBundle(int id){
        Bundle bundle = new Bundle();
        bundle.putInt("id",id);
        return bundle;
    }
}
