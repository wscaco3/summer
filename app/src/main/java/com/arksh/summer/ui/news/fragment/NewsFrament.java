package com.arksh.summer.ui.news.fragment;


import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.arksh.common.base.BaseFragment;
import com.arksh.common.widget.LoadingTip;
import com.arksh.summer.R;
import com.arksh.summer.app.AppConstant;
import com.arksh.summer.bean.NewsSummary;
import com.arksh.summer.ui.news.adapter.NewListAdapter;
import com.arksh.summer.ui.news.contract.NewsListContract;
import com.arksh.summer.ui.news.model.NewsListModel;
import com.arksh.summer.ui.news.presenter.NewsListPresenter;
import com.aspsine.irecyclerview.IRecyclerView;
import com.aspsine.irecyclerview.OnLoadMoreListener;
import com.aspsine.irecyclerview.OnRefreshListener;
import com.aspsine.irecyclerview.animation.ScaleInAnimation;
import com.aspsine.irecyclerview.widget.LoadMoreFooterView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/10/29 0029.
 */

public class NewsFrament extends BaseFragment<NewsListPresenter,NewsListModel> implements NewsListContract.View,OnRefreshListener,OnLoadMoreListener{

    @BindView(R.id.irc)
    IRecyclerView irc;
    @BindView(R.id.loadTip)
    LoadingTip loadTip;

    private NewListAdapter newListAdapter;
    private List<NewsSummary> datas = new ArrayList<>();

    private String mNewsId;
    private String mNewsType;
    private int mStartPage=0;

    // 标志位，标志已经初始化完成。
    private boolean isPrepared;
    private boolean isVisible;

    @Override
    protected int getLayoutResource() {
        return R.layout.fra_news_list;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this,mModel);
    }

    @Override
    protected void initView() {
        if (getArguments() != null) {
            mNewsId = getArguments().getString(AppConstant.NEWS_ID);
            mNewsType = getArguments().getString(AppConstant.NEWS_TYPE);
        }
        irc.setLayoutManager(new LinearLayoutManager(getContext()));
        newListAdapter = new NewListAdapter(getContext(), datas);
        newListAdapter.openLoadAnimation(new ScaleInAnimation());
        irc.setAdapter(newListAdapter);
        irc.setOnRefreshListener(this);
        irc.setOnLoadMoreListener(this);
        //数据为空才重新发起请求
        if(newListAdapter.getSize()<=0) {
            mStartPage = 0;
            mPresenter.getNewsListDataRequest(mNewsType, mNewsId, mStartPage);
        }
    }

    @Override
    public void returnNewsListData(List<NewsSummary> newsSummaries) {
        if(newsSummaries!=null){
            mStartPage += 20;
            if (newListAdapter.getPageBean().isRefresh()) {
                irc.setRefreshing(false);
                newListAdapter.replaceAll(newsSummaries);
            }else {
                if(newsSummaries.size() > 0){
                    irc.setLoadMoreStatus(LoadMoreFooterView.Status.GONE);
                    newListAdapter.addAll(newsSummaries);
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
        if( newListAdapter.getPageBean().isRefresh()) {
            loadTip.setLoadingTip(LoadingTip.LoadStatus.loading);
        }
    }

    @Override
    public void stopLoading() {
        loadTip.setLoadingTip(LoadingTip.LoadStatus.finish);
    }

    @Override
    public void showErrorTip(String msg) {
        if( newListAdapter.getPageBean().isRefresh()) {
            loadTip.setLoadingTip(LoadingTip.LoadStatus.error);
            loadTip.setTips(msg);
            irc.setRefreshing(false);
        }else{
            irc.setLoadMoreStatus(LoadMoreFooterView.Status.ERROR);
        }
    }

    @Override
    public void onLoadMore(View loadMoreView) {
        newListAdapter.getPageBean().setRefresh(false);
        //发起请求
        irc.setLoadMoreStatus(LoadMoreFooterView.Status.LOADING);
        mPresenter.getNewsListDataRequest(mNewsType, mNewsId, mStartPage);
    }

    @Override
    public void onRefresh() {
        newListAdapter.getPageBean().setRefresh(true);
        mStartPage = 0;
        //发起请求
        irc.setRefreshing(true);
        mPresenter.getNewsListDataRequest(mNewsType, mNewsId, mStartPage);
    }

}
