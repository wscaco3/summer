package com.arksh.summer.ui.news.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;

import com.arksh.common.base.BaseActivity;
import com.arksh.summer.R;

import butterknife.BindView;

public class AboutActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    private String mShareLink;

    @Override
    public int getLayoutId() {
        return R.layout.act_about;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {

        SetTranslanteBar();
        toolbar.setNavigationOnClickListener(view -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                finishAfterTransition();
            } else {
                finish();
            }
        });
        toolbar.setTitle(getString(R.string.app_name));
        toolbarLayout.setTitle(getString(R.string.app_name));
        //分享
        fab.setOnClickListener(view -> {
            if (mShareLink == null) {
                mShareLink = "";
            }
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share));
            intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_contents, getString(R.string.app_name), mShareLink));
            startActivity(Intent.createChooser(intent, getTitle()));
        });
    }

    public static void startAction(Context mContext) {
        Intent intent = new Intent(mContext, AboutActivity.class);
        mContext.startActivity(intent);
    }
}
