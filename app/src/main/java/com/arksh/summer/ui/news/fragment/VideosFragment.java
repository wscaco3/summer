package com.arksh.summer.ui.news.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.arksh.common.base.BaseFragment;
import com.arksh.common.widget.LoadingTip;
import com.arksh.summer.R;
import com.arksh.summer.app.AppConstant;
import com.arksh.summer.bean.VideoData;
import com.arksh.summer.ui.news.contract.VideosListContract;
import com.arksh.summer.ui.news.model.VideosListModel;
import com.arksh.summer.ui.news.presenter.VideoListPresenter;
import com.aspsine.irecyclerview.IRecyclerView;
import com.aspsine.irecyclerview.OnLoadMoreListener;
import com.aspsine.irecyclerview.OnRefreshListener;
import com.aspsine.irecyclerview.universaladapter.ViewHolderHelper;
import com.aspsine.irecyclerview.universaladapter.recyclerview.CommonRecycleViewAdapter;
import com.aspsine.irecyclerview.widget.LoadMoreFooterView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import butterknife.BindView;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerManager;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by Administrator on 2016/11/17 0017.
 */

public class VideosFragment extends BaseFragment<VideoListPresenter, VideosListModel> implements VideosListContract.View, OnRefreshListener, OnLoadMoreListener {
    @BindView(R.id.irc)
    IRecyclerView irc;
    @BindView(R.id.loadedTip)
    LoadingTip loadedTip;

    private CommonRecycleViewAdapter<VideoData> videoListAdapter;

    private String mVideoType;
    private int mStartPage = 0;

    @Override
    protected int getLayoutResource() {
        return R.layout.framents_news;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    protected void initView() {
        if (getArguments() != null) {
            mVideoType = getArguments().getString(AppConstant.VIDEO_TYPE);
        }
        irc.setLayoutManager(new LinearLayoutManager(getContext()));
        videoListAdapter = new CommonRecycleViewAdapter<VideoData>(getContext(),R.layout.item_video_list) {
            @Override
            public void convert(ViewHolderHelper helper, VideoData videoData) {
                helper.setImageRoundUrl(R.id.iv_logo,videoData.getTopicImg());
                helper.setText(R.id.tv_from,videoData.getTopicName());
                helper.setText(R.id.tv_play_time,String.format(getResources().getString(R.string.video_play_times), String.valueOf(videoData.getPlayCount())));
                JCVideoPlayerStandard jcVideoPlayerStandard = helper.getView(R.id.videoplayer);
                boolean setUp = jcVideoPlayerStandard.setUp(videoData.getMp4_url(), JCVideoPlayer.SCREEN_LAYOUT_LIST,
                        TextUtils.isEmpty(videoData.getDescription())?videoData.getTitle()+"":videoData.getDescription());

                if (setUp) {
                    Glide.with(mContext).load(videoData.getCover())
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .centerCrop()
                            .error(com.arksh.common.R.drawable.ic_empty_picture)
                            .crossFade().into(jcVideoPlayerStandard.thumbImageView);
                }

            }
        };
        irc.setAdapter(videoListAdapter);
        irc.setOnRefreshListener(this);
        irc.setOnLoadMoreListener(this);

        //视频监听
        irc.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {

            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
                if (JCVideoPlayerManager.popListener()!= null) {
                    JCVideoPlayer videoPlayer = (JCVideoPlayer) JCVideoPlayerManager.popListener();
                    if (((ViewGroup) view).indexOfChild(videoPlayer) != -1 && videoPlayer.currentState == JCVideoPlayer.CURRENT_STATE_PLAYING) {
                        JCVideoPlayer.releaseAllVideos();
                    }
                }
            }
        });
        //数据为空才重新发起请求
        if(videoListAdapter.getSize()<=0) {
            //发起请求
            mStartPage=0;
            mPresenter.getVideosListDataRequest(mVideoType, mStartPage);
        }
    }

    @Override
    public void returnVideosListData(List<VideoData> videoDatas) {
        if (videoDatas != null) {
            mStartPage += 1;
            if (videoListAdapter.getPageBean().isRefresh()) {
                irc.setRefreshing(false);
                videoListAdapter.replaceAll(videoDatas);
            } else {
                if (videoDatas.size() > 0) {
                    irc.setLoadMoreStatus(LoadMoreFooterView.Status.GONE);
                    videoListAdapter.addAll(videoDatas);
                } else {
                    irc.setLoadMoreStatus(LoadMoreFooterView.Status.THE_END);
                }
            }
        }

    }

    @Override
    public void scrolltoTop() {
        irc.smoothScrollToPosition(0);
    }

    @Override
    public void showLoading(String title) {
        if(videoListAdapter.getPageBean().isRefresh()){
            loadedTip.setLoadingTip(LoadingTip.LoadStatus.loading);
        }
    }

    @Override
    public void stopLoading() {
        loadedTip.setLoadingTip(LoadingTip.LoadStatus.finish);
    }

    @Override
    public void showErrorTip(String msg) {
        if( videoListAdapter.getPageBean().isRefresh()) {
            loadedTip.setLoadingTip(LoadingTip.LoadStatus.error);
            loadedTip.setTips(msg);
            irc.setRefreshing(false);
        }else{
            irc.setLoadMoreStatus(LoadMoreFooterView.Status.ERROR);
        }
    }

    @Override
    public void onLoadMore(View loadMoreView) {
        videoListAdapter.getPageBean().setRefresh(false);
        //发起请求
        irc.setLoadMoreStatus(LoadMoreFooterView.Status.LOADING);
        mPresenter.getVideosListDataRequest(mVideoType,mStartPage);
    }

    @Override
    public void onRefresh() {
        videoListAdapter.getPageBean().setRefresh(true);
        mStartPage = 0;
        //发起请求
        irc.setRefreshing(true);
        mPresenter.getVideosListDataRequest(mVideoType, mStartPage);
    }

    @Override
    public void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }
}
