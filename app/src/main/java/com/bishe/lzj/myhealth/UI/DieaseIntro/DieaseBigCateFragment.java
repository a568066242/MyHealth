package com.bishe.lzj.myhealth.UI.DieaseIntro;

import com.bishe.lzj.myhealth.Bean.DieaseCategory;
import com.bishe.lzj.myhealth.UI.DieaseIntro.BaseDieaseIntroFragment;

import java.util.List;

public class DieaseBigCateFragment extends BaseDieaseIntroFragment{


    @Override
    protected String getParamUrl() {
        return "/bigcategory.do";
    }

    @Override
    protected BaseDieaseIntroFragment getSubFragment(int id) {
        return new DieaseSubCateFragment(id);
    }
}