package com.bishe.lzj.myhealth.UI.DieaseIntro;

public class DieaseSubCateFragment extends BaseDieaseIntroFragment {

    int pid = -1;

    public DieaseSubCateFragment(int pid){
        this.pid = pid;
    }

    @Override
    protected String getParamUrl() {
        return "/subcategory.do?pid="+ pid;
    }

    @Override
    protected BaseDieaseIntroFragment getSubFragment(int id) {
        return new DieaseSubSubCateFragment(id);
    }

}