package com.arksh.summer.ui.main.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.arksh.common.base.BaseActivity;
import com.arksh.summer.R;
import com.flyco.tablayout.CommonTabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends BaseActivity {

    @BindView(R.id.fl_body)
    FrameLayout flBody;
    @BindView(R.id.tab_layout)
    CommonTabLayout tabLayout;

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

    /*
    * 入口
    * */
    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
