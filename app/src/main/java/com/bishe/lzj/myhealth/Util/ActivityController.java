package com.bishe.lzj.myhealth.Util;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lzj on 2016/2/19.
 * controll activity class
 */
public class ActivityController {

    private static final List<Activity> activities = new ArrayList<>();

    public static void addActivity(Activity activity){
        if(activities.indexOf(activity) == -1)
            activities.add(activity);
    }

    public static void removeActivity(Activity activity){
        activities.remove(activity);
    }


    public static void removeAll(){
        for(Activity activity:activities){
            if(!activity.isFinishing())
                activity.finish();
        }
    }


}
