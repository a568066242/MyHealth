package com.bishe.lzj.myhealth.UI;

import android.content.Intent;
import android.os.Bundle;

import com.bishe.lzj.myhealth.Bean.LoginInfo;
import com.bishe.lzj.myhealth.Logic.DataSender.DataSender;
import com.bishe.lzj.myhealth.Logic.DataSender.Impl.BasicDataSender;
import com.bishe.lzj.myhealth.Logic.Impl.LocalLoginLogicImpl;
import com.bishe.lzj.myhealth.Logic.LocalLoginLogic;
import com.bishe.lzj.myhealth.R;
import com.bishe.lzj.myhealth.Util.ToastUtil;

/**
 * 启动界面
 */
public class LaunchActivity extends BaseActivity {

    private static final String TAG = "LaunchActivity";
    private static final int WAITING_DURATION = 2*1000;
    LocalLoginLogic localLoginLogic = new LocalLoginLogicImpl();
    LoginInfo loginInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final long start = System.currentTimeMillis();
        setContentView(R.layout.activity_launch);
//        try {
//            Thread.sleep(500);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                //is auto login
                loginInfo = localLoginLogic.getLoginInfo();
                if(loginInfo.isAutoLogin()){//auto login
                    long end = System.currentTimeMillis();
                    if((end - start) < WAITING_DURATION){
                        try {
                            Thread.sleep(WAITING_DURATION - (end - start));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    String userName = loginInfo.getLastLoginUserName();
                    String pwd = loginInfo.getLastLoginPWD();
                    //send login web request
                    BasicDataSender.login(userName, pwd, new DataSender.FinishedCallbackListener() {
                        @Override
                        public void onFinished(Bundle bundle) {
                            int id = -1;
                            if (bundle != null) {
                                id = bundle.getInt("id");
                            }
                            if (id < 0) {//login failuer{
                               startLoginActivity(start);
                            } else {
                                //login success

                                ToastUtil.show("登陆成功", ToastUtil.LENGH_SHORT);
                                //start main activity
                                Intent intent = new Intent(LaunchActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    }, new DataSender.ErrorCallbackListener() {
                        @Override
                        public void onError(String error) {
                            ToastUtil.show("服务器出现了点问题，请稍后再试", ToastUtil.LENGH_SHORT);
                            startLoginActivity(start);
                        }
                    });
                }else{
                    //not auto login
                    startLoginActivity(start);
                }
            }
        }).start();

    }

    private void startLoginActivity(long start) {
        long end = System.currentTimeMillis();
        if((end - start) < WAITING_DURATION){
            try {
                Thread.sleep(WAITING_DURATION - (end - start));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        LoginActivity.actionStart(LaunchActivity.this, loginInfo.getLastLoginUserName(), loginInfo.getLastLoginPWD()
                , loginInfo.isRememberPWD(), loginInfo.isAutoLogin());
        finish();
    }


}
