package com.arksh.summer.ui.news.presenter;

import com.arksh.common.rx.RxSubscriber;
import com.arksh.summer.app.AppConstant;
import com.arksh.summer.bean.NewsChannelTable;
import com.arksh.summer.ui.news.contract.NewsChannelContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/16 0016.
 */

public class NewsChannelPresenter extends NewsChannelContract.Presenter {
    @Override
    public void lodeChannelsRequest() {
        mRxManage.add(mModel.lodeMineNewsChannels().subscribe(new RxSubscriber<List<NewsChannelTable>>(mContext,false) {

            @Override
            protected void _onNext(List<NewsChannelTable> newsChannelTables) {
                mView.returnMineNewsChannels(newsChannelTables);
            }

            @Override
            protected void _onError(String message) {

            }
        }));
        mRxManage.add(mModel.lodeMoreNewsChannels().subscribe(new RxSubscriber<List<NewsChannelTable>>(mContext,false) {
            @Override
            protected void _onNext(List<NewsChannelTable> newsChannelTables) {
                mView.returnMoreNewsChannels(newsChannelTables);
            }

            @Override
            protected void _onError(String message) {

            }
        }));
    }

    @Override
    public void onItemSwap(ArrayList<NewsChannelTable> newsChannelTableList, int fromPosition, int toPosition) {

        mRxManage.add(mModel.swapDb(newsChannelTableList,fromPosition,toPosition).subscribe(new RxSubscriber<String>(mContext,false) {
            @Override
            protected void _onNext(String s) {
                mRxManage.post(AppConstant.NEWS_CHANNEL_CHANGED,newsChannelTableList);
            }

            @Override
            protected void _onError(String message) {

            }
        }));
    }

    @Override
    public void onItemAddOrRemove(ArrayList<NewsChannelTable> mineChannelTableList, ArrayList<NewsChannelTable> moreChannelTableList) {
        mRxManage.add(mModel.updateDb(mineChannelTableList,moreChannelTableList).subscribe(new RxSubscriber<String>(mContext,false) {
            @Override
            protected void _onNext(String s) {
                mRxManage.post(AppConstant.NEWS_CHANNEL_CHANGED,mineChannelTableList);
            }

            @Override
            protected void _onError(String message) {

            }
        }));

    }
}
