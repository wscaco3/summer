package com.arksh.summer.ui.main.activity;


import android.app.Activity;
import android.content.Intent;

import com.arksh.common.base.BaseActivity;
import com.arksh.summer.R;


public class MainActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.act_main;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {

    }

    public static void startAction(Activity activity){
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
    }
}
