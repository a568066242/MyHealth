package com.bishe.lzj.myhealth.Logic.DataSender.Impl;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bishe.lzj.myhealth.Bean.User;
import com.bishe.lzj.myhealth.Bean.UserBasic;
import com.bishe.lzj.myhealth.Bean.UserHealth;
import com.bishe.lzj.myhealth.Logic.DataSender.VolleyDataSender;
import com.bishe.lzj.myhealth.MyApplication;
import com.bishe.lzj.myhealth.UI.LoginActivity;
import com.bishe.lzj.myhealth.UI.RegisterActivity;
import com.bishe.lzj.myhealth.Util.LogUtil;
import com.bishe.lzj.myhealth.Util.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lzj on 2016/2/23.
 */
public class BasicDataSender extends VolleyDataSender {

    private static final String TAG = "BasicDataSender";
    public static final String USER_NAME = "userName";
    public static final String PWD = "pwd";
    public static final String NAME = "name";
    public static final String SEX = "sex";
    public static final String AGE = "age";
    public static final String PHONE = "phone";
    private static String ACCOUNT_URL = "/account";


    /**
     * send request to register
     * @param user
     * @param userBasic
     */
    public static void registerNewUser(final User user,final UserBasic userBasic,
                                       final FinishedCallbackListener finishedCallbackListener,
                                       final ErrorCallbackListener errorCallbackListener){
        LogUtil.i(TAG,"registerNewUser");
        RequestQueue requestQueue = getmQueue();
        String url = getBaseURL() + ACCOUNT_URL + "/register.do";
        LogUtil.i(TAG, url);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        LogUtil.i(TAG,"onResponse");
                        LogUtil.i(TAG,s);
                        if(finishedCallbackListener!=null){
                            Boolean isRegisterSuccess = null;
                            try {
                                isRegisterSuccess = Boolean.valueOf(s);
                            } catch (Exception e) {
                                e.printStackTrace();
                                isRegisterSuccess = false;
                            }
                            finishedCallbackListener.onFinished(RegisterActivity.afterRegisterBundle(isRegisterSuccess));
                        }
                    }
                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtil.i(TAG, "onErrorResponse");
                if(volleyError!=null) {
                    LogUtil.e(TAG, volleyError.getClass().getSimpleName());
                    if (errorCallbackListener != null)
                        errorCallbackListener.onError(volleyError.getClass().getSimpleName());
                }else{
                    if (errorCallbackListener != null)
                        errorCallbackListener.onError("unknown error");
                }
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put(USER_NAME,user.getUserName());
                map.put(PWD,user.getPassword());
                map.put(NAME,userBasic.getName());
                map.put(SEX,userBasic.getSex());
                map.put(AGE,String.valueOf(userBasic.getAge()));
                map.put(PHONE,userBasic.getPhone());
                return map;

            }
        };

        requestQueue.add(stringRequest);
    }


    /**
     * send login web request
     * @param username
     * @param pwd
     * @param finishedCallbackListener
     * @param errorCallbackListener
     */
    public static void login(final String username,final String pwd
            , final FinishedCallbackListener finishedCallbackListener
            , final ErrorCallbackListener errorCallbackListener){
        LogUtil.i(TAG,"login");
        String url = getBaseURL() + ACCOUNT_URL +"/loginByName.do";
        LogUtil.i(TAG,url);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtil.i(TAG,"onResponse");
                LogUtil.i(TAG, s);
                int id = -1;
                try {
                    id = Integer.valueOf(s);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    id = -1;
                }

                if(finishedCallbackListener != null)
                    finishedCallbackListener.onFinished(LoginActivity.afterLogionBundle(id));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtil.i(TAG, "onErrorResponse");
                LogUtil.e(TAG, volleyError.getClass().getSimpleName());
                if(errorCallbackListener != null)
                    errorCallbackListener.onError(volleyError.getClass().getSimpleName());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String ,String> map = new HashMap<>();
                map.put(USER_NAME,username);
                map.put(PWD,pwd);
                return map;
            }
        };
        getmQueue().add(stringRequest);
    }


    public static void getUserAllInfo(int id,final FinishedCallbackListener finishedCallbackListener){
        String url = getBaseURL() + ACCOUNT_URL +"/getbasic.do?id="+ MyApplication.getUser().getId();
        LogUtil.i(TAG, url);
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtil.d(TAG,s);
                Gson gson = new Gson();
                MyApplication.getUser().setUserBasic((UserBasic) gson.fromJson(s, new TypeToken<UserBasic>() {
                }.getType()));
                String url2 =getBaseURL() + ACCOUNT_URL +"/gethealth.do?id="+ MyApplication.getUser().getId() ;
                LogUtil.i(TAG, url2);
                StringRequest stringReques2 = new StringRequest(url2, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String s) {
                        LogUtil.d(TAG,s);
                        Gson gson = new Gson();
                        MyApplication.getUser().setUserHealth((UserHealth) gson.fromJson(s, new TypeToken<UserHealth>() {
                        }.getType()));
                        if(finishedCallbackListener != null){
                            finishedCallbackListener.onFinished(null);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        ToastUtil.showShort("请求数据出错！");
                    }
                });
                getmQueue().add(stringReques2);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ToastUtil.showShort("请求数据出错！");
            }
        });

        getmQueue().add(stringRequest);

    }



}
