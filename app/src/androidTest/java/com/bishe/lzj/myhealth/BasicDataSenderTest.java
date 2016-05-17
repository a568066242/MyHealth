package com.bishe.lzj.myhealth;

import android.os.Bundle;
import android.test.InstrumentationTestCase;

import com.bishe.lzj.myhealth.Bean.User;
import com.bishe.lzj.myhealth.Bean.UserBasic;
import com.bishe.lzj.myhealth.Logic.DataSender.DataSender;
import com.bishe.lzj.myhealth.Logic.DataSender.Impl.BasicDataSender;
/**
 * Created by lzj on 2016/2/23.
 */
public class BasicDataSenderTest extends InstrumentationTestCase {



    public void testRegisterNewUser() throws Exception{
        BasicDataSender.registerNewUser(new User("haha6", "123"), new UserBasic("吕中剑", "男", 22, "18352862364")
                , new DataSender.FinishedCallbackListener() {
            @Override
            public void onFinished(Bundle bundle) {
                assertEquals(false,bundle.getBoolean("result"));
            }
        }, null);
    }


    public void testLogin()throws Exception{
        BasicDataSender.login("lzj", "123123", new DataSender.FinishedCallbackListener() {
            @Override
            public void onFinished(Bundle bundle) {
                assertEquals(1, bundle.getInt("id"));
            }
        }, new DataSender.ErrorCallbackListener() {
            @Override
            public void onError(String error) {

            }
        });

        BasicDataSender.login("lzj", "1", new DataSender.FinishedCallbackListener() {
            @Override
            public void onFinished(Bundle bundle) {
                assertEquals(-1, bundle.getInt("id"));
            }
        }, new DataSender.ErrorCallbackListener() {
            @Override
            public void onError(String error) {

            }
        });
    }



}
