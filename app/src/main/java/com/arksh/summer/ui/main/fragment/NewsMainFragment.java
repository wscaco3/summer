package com.arksh.summer.ui.main.fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.arksh.common.base.BaseFragment;
import com.arksh.common.base.BaseFragmentAdapter;
import com.arksh.summer.R;
import com.arksh.summer.app.AppConstant;
import com.arksh.summer.bean.NewsChannelTable;
import com.arksh.summer.ui.main.contract.NewsMainContract;
import com.arksh.summer.ui.main.model.NewsMainModel;
import com.arksh.summer.ui.main.presenter.NewsMainPresenter;
import com.arksh.summer.ui.news.activity.NewsChannelActivity;
import com.arksh.summer.ui.news.fragment.NewsFrament;
import com.arksh.summer.utils.MyUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/10/28 0028.
 */

public class NewsMainFragment extends BaseFragment<NewsMainPresenter, NewsMainModel> implements NewsMainContract.View {
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.add_channel_iv)
    ImageView addChannelIv;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    private BaseFragmentAdapter fragmentAdapter;

    @Override
    protected int getLayoutResource() {
        return R.layout.app_bar_news;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this,mModel);
    }

    @Override
    protected void initView() {
        mPresenter.lodeMineChannelsRequest();

    }

    @Override
    public void returnMineNewsChannels(List<NewsChannelTable> newsChannelsMine) {
        if(newsChannelsMine != null){
            List<String> channeNames = new ArrayList<>();
            List<Fragment> mNewsFragmentList = new ArrayList<>();
            for(NewsChannelTable nc:newsChannelsMine){
                channeNames.add(nc.getNewsChannelName());
                mNewsFragmentList.add(createListFragments(nc));
            }
            fragmentAdapter = new BaseFragmentAdapter(getChildFragmentManager(),mNewsFragmentList,channeNames);
            viewPager.setAdapter(fragmentAdapter);
            tabs.setupWithViewPager(viewPager);
            MyUtils.dynamicSetTabLayoutMode(tabs);
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }
    }


    private NewsFrament createListFragments(NewsChannelTable newsChannel) {
        NewsFrament fragment = new NewsFrament();
        Bundle bundle = new Bundle();
        bundle.putString(AppConstant.NEWS_ID, newsChannel.getNewsChannelId());
        bundle.putString(AppConstant.NEWS_TYPE, newsChannel.getNewsChannelType());
        bundle.putInt(AppConstant.CHANNEL_POSITION, newsChannel.getNewsChannelIndex());
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void showLoading(String title) {

    }

    @Override
    public void stopLoading() {

    }

    @Override
    public void showErrorTip(String msg) {

    }

    @OnClick(R.id.add_channel_iv)
    public void onClickAdd() {
        //ChangeModeController.toggleThemeSetting(getActivity());
        NewsChannelActivity.startAction(getContext());
    }

    @OnClick(R.id.fab)
    public void onClickToTop() {
        mRxManager.post(AppConstant.NEWS_LIST_TO_TOP, "");
    }
}
