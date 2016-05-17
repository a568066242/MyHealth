package com.bishe.lzj.myhealth.UI.DieaseIntro;

/**
 * Created by lzj on 2016/5/10.
 */
public class DieaseSubSubCateFragment extends  BaseDieaseIntroFragment {


    private final int pid;

    public DieaseSubSubCateFragment(int pid){
        this.pid = pid;
    }

    @Override
    protected String getParamUrl() {
        return "/getdiease.do?pid="+pid;
    }

    @Override
    protected BaseDieaseIntroFragment getSubFragment(int id) {
        return new DieaseInfoFragment(id);
    }
}
