package com.arksh.summer.ui.news.model;


import com.arksh.common.rx.RxSchedulers;
import com.arksh.common.utils.TimeUtil;
import com.arksh.summer.api.Api;
import com.arksh.summer.api.HostType;
import com.arksh.summer.bean.VideoData;
import com.arksh.summer.ui.news.contract.VideosListContract;

import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.functions.Func1;

/**
 * des:视频列表model
 * Created by xsf
 * on 2016.09.14:54
 */
public class VideosListModel implements VideosListContract.Model {

    @Override
    public Observable<List<VideoData>> getVideosListData(final String type, int startPage) {
        return Api.getDefault(HostType.NETEASE_NEWS_VIDEO).getVideoList(Api.getCacheControl(),type,startPage)
                .flatMap(new Func1<Map<String, List<VideoData>>, Observable<VideoData>>() {
                    @Override
                    public Observable<VideoData> call(Map<String, List<VideoData>> map) {
                        return Observable.from(map.get(type));
                    }
                })
                //转化时间
                .map(videoData -> {
                    String ptime = TimeUtil.formatDate(videoData.getPtime());
                    videoData.setPtime(ptime);
                    return videoData;
                })
                .distinct()//去重
                .toSortedList((videoData, videoData2) -> videoData2.getPtime().compareTo(videoData.getPtime()))
                //声明线程调度
                .compose(RxSchedulers.io_main());
    }
}
