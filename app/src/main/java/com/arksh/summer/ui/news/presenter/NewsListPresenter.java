package com.arksh.summer.ui.news.presenter;

import com.arksh.common.rx.RxSubscriber;
import com.arksh.summer.R;
import com.arksh.summer.app.AppConstant;
import com.arksh.summer.bean.NewsSummary;
import com.arksh.summer.ui.news.contract.NewsListContract;

import java.util.List;

/**
 * Created by Administrator on 2016/10/29 0029.
 */

public class NewsListPresenter extends NewsListContract.Presenter {
    @Override
    public void onStart(){
        super.onStart();
        //监听返回顶部的动作
        mRxManage.on(AppConstant.NEWS_LIST_TO_TOP,o -> mView.scrolltoTop());
    }
    @Override
    public void getNewsListDataRequest(String type, String id, int startPage) {
        mRxManage.add(mModel.getNewsListData(type,id,startPage).subscribe(new RxSubscriber<List<NewsSummary>>(mContext,false) {
            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading(mContext.getString(R.string.loading));
            }

            @Override
            protected void _onNext(List<NewsSummary> newsSummaries) {
                mView.returnNewsListData(newsSummaries);
                mView.stopLoading();
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }
}
