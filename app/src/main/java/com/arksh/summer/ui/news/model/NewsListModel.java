package com.arksh.summer.ui.news.model;

import com.arksh.summer.bean.NewsSummary;
import com.arksh.summer.ui.news.contract.NewsListContract;

import java.util.List;

import rx.Observable;

/**
 * Created by Administrator on 2016/10/29 0029.
 */

public class NewsListModel implements NewsListContract.Model {
    @Override
    public Observable<List<NewsSummary>> getNewsListData(String type, String id, int startPage) {
        return null;
    }
}
