package com.arksh.summer.ui.news.model;

import com.arksh.common.rx.RxSchedulers;
import com.arksh.common.utils.TimeUtil;
import com.arksh.summer.api.Api;
import com.arksh.summer.api.ApiConstants;
import com.arksh.summer.api.HostType;
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
        return Api.getDefault(HostType.NETEASE_NEWS_VIDEO).getNewsList(Api.getCacheControl(), type, id, startPage).flatMap(map -> {
            if (id.endsWith(ApiConstants.HOUSE_ID)) {
                return Observable.from(map.get("北京"));
            }
            return Observable.from(map.get(id));
        }).map(newsSummary -> {
            String ptime = TimeUtil.formatDate(newsSummary.getPtime());
            newsSummary.setPtime(ptime);
            return newsSummary;
        })
        .distinct()//去重
        .toSortedList((newsSummary, newsSummary2) -> newsSummary2.getPtime().compareTo(newsSummary.getPtime()))
        //声明线程调度
        .compose(RxSchedulers.<List<NewsSummary>>io_main());
    }
}
