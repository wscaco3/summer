package com.arksh.summer.ui.news.contract;


import com.arksh.common.base.BaseModel;
import com.arksh.common.base.BasePresenter;
import com.arksh.common.base.BaseView;
import com.arksh.summer.bean.NewsChannelTable;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * des:
 * Created by xsf
 * on 2016.09.11:53
 */
public interface NewsChannelContract {

    interface Model extends BaseModel {
        Observable<List<NewsChannelTable>> lodeMineNewsChannels();
        Observable<List<NewsChannelTable>> lodeMoreNewsChannels();
        Observable<String> swapDb(ArrayList<NewsChannelTable> newsChannelTableList, int fromPosition, int toPosition);
        Observable<String> updateDb(ArrayList<NewsChannelTable> mineChannelTableList, ArrayList<NewsChannelTable> moreChannelTableList);
    }

    interface View extends BaseView {
        void returnMineNewsChannels(List<NewsChannelTable> newsChannelsMine);
        void returnMoreNewsChannels(List<NewsChannelTable> newsChannelsMore);
    }

    abstract class Presenter extends BasePresenter<View, Model> {
        public abstract void lodeChannelsRequest();
        public abstract void onItemSwap(ArrayList<NewsChannelTable> newsChannelTableList, int fromPosition, final int toPosition);
        public abstract void onItemAddOrRemove(ArrayList<NewsChannelTable> mineChannelTableList, ArrayList<NewsChannelTable> moreChannelTableList);
    }
}
