package com.arksh.summer.ui.main.model;

import com.arksh.summer.bean.NewsChannelTable;
import com.arksh.summer.ui.main.contract.NewsMainContract;

import java.util.List;

import rx.Observable;

/**
 * Created by Administrator on 2016/10/28 0028.
 */

public class NewsMainModel implements NewsMainContract.Model {
    @Override
    public Observable<List<NewsChannelTable>> lodeMineNewsChannels() {
        return null;
    }
}
