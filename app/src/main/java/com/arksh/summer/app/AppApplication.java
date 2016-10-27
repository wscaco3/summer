package com.arksh.summer.app;

import com.arksh.common.BuildConfig;
import com.arksh.common.app.BaseApplication;
import com.arksh.common.utils.LogUtils;

/**
 * Created by Administrator on 2016/10/27 0027.
 */

public class AppApplication extends BaseApplication {

    @Override
    public void onCreate(){
        super.onCreate();
        LogUtils.logInit(BuildConfig.DEBUG);
    }

}
