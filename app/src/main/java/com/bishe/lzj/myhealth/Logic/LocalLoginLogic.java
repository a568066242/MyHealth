package com.bishe.lzj.myhealth.Logic;

import com.bishe.lzj.myhealth.Bean.LoginInfo;

/**
 * Created by lzj on 2016/2/19.
 */
public interface LocalLoginLogic {
    /**
     * get local login info
     */
    public  LoginInfo getLoginInfo();

    /**
     * set local login info
     * @param loginInfo
     */
    public void saveLocalLoginInfo(LoginInfo loginInfo);
    




}
