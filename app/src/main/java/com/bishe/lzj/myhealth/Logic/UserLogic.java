package com.bishe.lzj.myhealth.Logic;

import com.bishe.lzj.myhealth.Bean.User;

/**
 * Created by lzj on 2016/2/19.
 */
public interface UserLogic {

    public void register(User user);

    public void update(User user);

    public void queryById(int id);


}
