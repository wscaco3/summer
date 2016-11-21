package com.arksh.summer.ui.zone.model;


import com.arksh.common.app.AppCache;
import com.arksh.common.rx.RxSchedulers;
import com.arksh.common.utils.LogUtils;
import com.arksh.summer.bean.Result;
import com.arksh.summer.ui.zone.DatasUtil;
import com.arksh.summer.ui.zone.bean.CommentItem;
import com.arksh.summer.ui.zone.contract.CircleZoneContract;

import rx.Observable;
import rx.Subscriber;

/**
 * des:
 * Created by xsf
 * on 2016.08.15:56
 */
public class ZoneModel implements CircleZoneContract.Model {
    /**
     * 获取未读条数
     * @return
     */
    @Override
    public Observable<String> getZoneNotReadNews() {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext(AppCache.getInstance().getIcon());
                subscriber.onCompleted();
                LogUtils.logd(AppCache.getInstance().getIcon());
            }
        }).compose(RxSchedulers.io_main());
    }

    /**
     * 获取列表
     * @param type
     * @param userId
     * @param page
     * @param rows
     * @return
     */
    @Override
    public Observable<Result> getListDatas(String type, String userId, int page, int rows) {
        return Observable.create(new Observable.OnSubscribe<Result>() {
            @Override
            public void call(Subscriber<? super Result> subscriber) {
                Result result = DatasUtil.getZoneListDatas();
                subscriber.onNext(result);
                subscriber.onCompleted();
                LogUtils.logd("result"+result.toString());
            }
        }).compose(RxSchedulers.io_main());
    }

    /**
     * 删除说说
     * @param circleId
     * @param position
     * @return
     */
    @Override
    public Observable<Result> deleteCircle(String circleId, int position) {
        return Observable.create(new Observable.OnSubscribe<Result>() {
            @Override
            public void call(Subscriber<? super Result> subscriber) {
                Result result = new Result();
                subscriber.onNext(result);
                subscriber.onCompleted();
                LogUtils.logd(result.toString());
            }
        }).compose(RxSchedulers.io_main());
    }

    /**
     * 增加点赞
     * @param publishId
     * @param publishUserId
     * @return
     */
    @Override
    public Observable<Result> addFavort(String publishId, String publishUserId) {
        return Observable.create(new Observable.OnSubscribe<Result>() {
            @Override
            public void call(Subscriber<? super Result> subscriber) {
                Result result = new Result();
                subscriber.onNext(result);
                subscriber.onCompleted();
                LogUtils.logd(result.toString());
            }
        }).compose(RxSchedulers.io_main());
    }

    /**
     * 取消点赞
     * @param publishId
     * @param publishUserId
     * @return
     */
    @Override
    public Observable<Result> deleteFavort(String publishId, String publishUserId) {
        return Observable.create(new Observable.OnSubscribe<Result>() {
            @Override
            public void call(Subscriber<? super Result> subscriber) {
                Result result = new Result();
                subscriber.onNext(result);
                subscriber.onCompleted();
                LogUtils.logd(result.toString());
            }
        }).compose(RxSchedulers.io_main());
    }

    /**
     * 增加评论
     * @param publishUserId
     * @param circleItem
     * @return
     */
    @Override
    public Observable<Result> addComment(String publishUserId, CommentItem circleItem) {
        return Observable.create(new Observable.OnSubscribe<Result>() {
            @Override
            public void call(Subscriber<? super Result> subscriber) {
                Result result = new Result();
                subscriber.onNext(result);
                subscriber.onCompleted();
                LogUtils.logd(result.toString());
            }
        }).compose(RxSchedulers.io_main());
    }

    /**
     * 删除评论
     * @param commentId
     * @return
     */
    @Override
    public Observable<Result> deleteComment(String commentId) {
        return Observable.create(new Observable.OnSubscribe<Result>() {
            @Override
            public void call(Subscriber<? super Result> subscriber) {
                Result result = new Result();
                subscriber.onNext(result);
                subscriber.onCompleted();
                LogUtils.logd(result.toString());
            }
        }).compose(RxSchedulers.io_main());
    }

}
