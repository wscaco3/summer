package com.arksh.summer.ui.main.fragment;

import com.arksh.common.base.BaseFragment;
import com.arksh.summer.R;
import com.arksh.summer.bean.NewsChannelTable;
import com.arksh.summer.ui.main.contract.NewsMainContract;
import com.arksh.summer.ui.main.model.NewsMainModel;
import com.arksh.summer.ui.main.presenter.NewsMainPresenter;

import java.util.List;

/**
 * Created by Administrator on 2016/10/28 0028.
 */

public class NewsMainFragment extends BaseFragment<NewsMainPresenter,NewsMainModel> implements NewsMainContract.View{
    @Override
    protected int getLayoutResource() {
        return R.layout.app_bar_news;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {

    }

    @Override
    public void returnMineNewsChannels(List<NewsChannelTable> newsChannelsMine) {

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
}
