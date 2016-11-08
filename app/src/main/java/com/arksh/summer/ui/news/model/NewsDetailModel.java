package com.arksh.summer.ui.news.model;

import com.arksh.common.rx.RxSchedulers;
import com.arksh.summer.api.Api;
import com.arksh.summer.api.HostType;
import com.arksh.summer.bean.NewsDetail;
import com.arksh.summer.ui.news.contract.NewsDetailContract;

import java.util.List;

import rx.Observable;

/**
 * Created by Administrator on 2016/11/8 0008.
 */

public class NewsDetailModel implements NewsDetailContract.Model {
    @Override
    public Observable<NewsDetail> getOneNewsData(String postId) {
        return Api.getDefault(HostType.NETEASE_NEWS_VIDEO).getNewDetail(Api.getCacheControl(), postId).map(
                map -> {
                    NewsDetail newsDetail = map.get(postId);
                    changeNewsDetail(newsDetail);
                    return newsDetail;
                }
        ).compose(RxSchedulers.<NewsDetail>io_main());
    }

    private void changeNewsDetail(NewsDetail newsDetail) {
        List<NewsDetail.ImgBean> imgSrcs = newsDetail.getImg();
        if (isChange(imgSrcs)) {
            String newsBody = newsDetail.getBody();
            newsBody = changeNewsBody(imgSrcs, newsBody);
            newsDetail.setBody(newsBody);
        }
    }

    private boolean isChange(List<NewsDetail.ImgBean> imgSrcs) {
        return imgSrcs != null && imgSrcs.size() >= 2;
    }

    private String changeNewsBody(List<NewsDetail.ImgBean> imgSrcs, String newsBody) {
        for (int i = 0; i < imgSrcs.size(); i++) {
            String oldChars = "<!--IMG#" + i + "-->";
            String newChars;
            if (i == 0) {
                newChars = "";
            } else {
                newChars = "<img src=\"" + imgSrcs.get(i).getSrc() + "\" />";
            }
            newsBody = newsBody.replace(oldChars, newChars);

        }
        return newsBody;
    }
}
