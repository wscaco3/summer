package com.arksh.summer.ui.news.contract;

import com.arksh.common.base.BaseModel;
import com.arksh.common.base.BasePresenter;
import com.arksh.common.base.BaseView;
import com.arksh.summer.bean.NewsSummary;

import java.util.List;

import rx.Observable;

/**
 * Created by Administrator on 2016/10/29 0029.
 */

public interface NewsListContract {
    interface Model extends BaseModel{
        //请求获取新闻
        Observable<List<NewsSummary>> getNewsListData(String type, final String id, int startPage);
    }

    interface View extends BaseView {
        //返回获取的新闻
        void returnNewsListData(List<NewsSummary> newsSummaries);
        //返回顶部
        void scrolltoTop();
    }

    abstract class Presenter extends BasePresenter<View, Model> {
        //发起获取新闻请求
        public abstract void getNewsListDataRequest(String type, final String id, int startPage);
    }
}
