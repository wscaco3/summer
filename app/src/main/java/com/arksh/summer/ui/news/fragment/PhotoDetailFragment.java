package com.arksh.summer.ui.news.fragment;

import android.view.View;
import android.widget.ProgressBar;

import com.arksh.common.base.BaseFragment;
import com.arksh.common.rx.RxSchedulers;
import com.arksh.common.utils.ImageLoaderUtils;
import com.arksh.summer.R;
import com.arksh.summer.app.AppConstant;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.Observable;
import rx.Subscriber;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * des:图文新闻详情
 * Created by xsf
 * on 2016.09.9:57
 */
public class PhotoDetailFragment extends BaseFragment{

    @BindView(R.id.photo_view)
    PhotoView photoView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    private String mImgSrc;

    @Override
    protected int getLayoutResource() {
        return R.layout.fra_news_photo_detail;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        if (getArguments() != null) {
            mImgSrc = getArguments().getString(AppConstant.PHOTO_DETAIL_IMGSRC);
        }
        initPhotoView();
    }

    private void initPhotoView() {
        mRxManager.add(Observable.timer(100, TimeUnit.MILLISECONDS) // 直接使用glide加载的话，activity切换动画时背景短暂为默认背景色
                .compose(RxSchedulers.io_main())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onNext(Long aLong) {
                        ImageLoaderUtils.displayBigPhoto(getContext(),photoView,mImgSrc);
                        photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                            @Override
                            public void onPhotoTap(View view, float x, float y) {
                                mRxManager.post(AppConstant.PHOTO_TAB_CLICK, "");
                            }

                            @Override
                            public void onOutsidePhotoTap() {

                            }
                        });
                    }
                }));
    }

}
