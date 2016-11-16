package com.arksh.summer.ui.news.model;


import com.arksh.common.rx.RxSchedulers;
import com.arksh.summer.api.Api;
import com.arksh.summer.api.HostType;
import com.arksh.summer.bean.PhotoGirl;
import com.arksh.summer.ui.news.contract.PhotoListContract;

import java.util.List;

import rx.Observable;

/**
 * des:图片
 * Created by xsf
 * on 2016.09.12:02
 */
public class PhotosListModel implements PhotoListContract.Model{
    @Override
    public Observable<List<PhotoGirl>> getPhotosListData(int size, int page) {
        return Api.getDefault(HostType.GANK_GIRL_PHOTO)
                .getPhotoList(Api.getCacheControl(),size, page)
                .map(girlData -> girlData.getResults())
                .compose(RxSchedulers.io_main());
    }
}
