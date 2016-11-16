package com.arksh.summer.ui.news.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;

import com.arksh.common.widget.StatusBarCompat;
import com.arksh.summer.R;
import com.arksh.summer.app.AppConstant;
import com.arksh.summer.utils.SystemUiVisibilityUtil;
import com.arksh.summer.widget.PullBackLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class PhotosDetailActivity extends AppCompatActivity implements PullBackLayout.Callback {

    @BindView(R.id.photo_touch_iv)
    PhotoView photoTouchIv;
    @BindView(R.id.pull_back_layout)
    PullBackLayout pullBackLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.background)
    RelativeLayout background;

    private boolean mIsToolBarHidden;
    private boolean mIsStatusBarHidden;
    private Unbinder unbinder;

    public static void startAction(Context context, String url){
        Intent intent = new Intent(context, PhotosDetailActivity.class);
        intent.putExtra(AppConstant.PHOTO_DETAIL,url);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.translucentStatusBar(this);
        setContentView(R.layout.act_photos_detail);
        unbinder =  ButterKnife.bind(this);
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    public void initView() {
        StatusBarCompat.translucentStatusBar(this);
        pullBackLayout.setCallback(this);
        toolBarFadeIn();
        initToolbar();
        initImageView();
        setPhotoViewClickEvent();
    }

    private void toolBarFadeIn() {
        mIsToolBarHidden = true;
        hideOrShowToolbar();
    }

    private void toolBarFadeOut() {
        mIsToolBarHidden = false;
        hideOrShowToolbar();
    }

    protected void hideOrShowToolbar() {
        toolbar.animate()
                .alpha(mIsToolBarHidden ? 1.0f : 0.0f)
                .setInterpolator(new DecelerateInterpolator(2))
                .start();
        mIsToolBarHidden = !mIsToolBarHidden;
    }

    private void hideOrShowStatusBar() {
        if (mIsStatusBarHidden) {
            SystemUiVisibilityUtil.enter(PhotosDetailActivity.this);
        } else {
            SystemUiVisibilityUtil.exit(PhotosDetailActivity.this);
        }
        mIsStatusBarHidden = !mIsStatusBarHidden;
    }

    private void initToolbar() {
        toolbar.setTitle(getString(R.string.girl));
        toolbar.setNavigationOnClickListener(view -> finish());
    }

    private void initImageView() {
        String url = getIntent().getStringExtra(AppConstant.PHOTO_DETAIL);
        Glide.with(this).load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.ic_empty_picture)
                .crossFade().into(photoTouchIv);
    }

    private void setPhotoViewClickEvent() {
        photoTouchIv.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float v, float v1) {
                hideOrShowToolbar();
                hideOrShowStatusBar();
            }

            @Override
            public void onOutsidePhotoTap() {

            }
        });
    }

    @Override
    public void onPullStart() {
        toolBarFadeOut();
        mIsStatusBarHidden = true;
        hideOrShowStatusBar();
    }

    @Override
    public void onPull(float progress) {
        progress = Math.min(1f, progress * 3f);
        background.getBackground().setAlpha((int) (0xff/*255*/ * (1f - progress)));
    }

    @Override
    public void onPullCancel() {
        toolBarFadeIn();
    }

    @Override
    public void onPullComplete() {
        supportFinishAfterTransition();
    }

    @Override
    public void supportFinishAfterTransition() {
        super.supportFinishAfterTransition();
    }
}
