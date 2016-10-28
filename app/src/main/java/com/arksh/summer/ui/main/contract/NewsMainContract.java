package com.arksh.summer.ui.main.contract;

import com.arksh.common.base.BaseModel;
import com.arksh.common.base.BasePresenter;
import com.arksh.common.base.BaseView;
import com.arksh.summer.bean.NewsChannelTable;

import java.util.List;

import rx.Observable;


/**
 * Created by Administrator on 2016/10/28 0028.
 */

public interface NewsMainContract {
    interface Model extends BaseModel{
        Observable< List<NewsChannelTable> > lodeMineNewsChannels();
    }

    interface View extends BaseView{
        void returnMineNewsChannels(List<NewsChannelTable> newsChannelsMine);
    }

    abstract class Presenter extends BasePresenter<View, Model> {
        public abstract void lodeMineChannelsRequest();
    }
}
