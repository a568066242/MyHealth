package com.bishe.lzj.myhealth.Logic;

/**
 * Created by lzj on 2016/2/22.
 */
public interface BaseLogic<T>  {

    public void insert(T object);

    public void update(T object);

    public void queryById(int id);


}
