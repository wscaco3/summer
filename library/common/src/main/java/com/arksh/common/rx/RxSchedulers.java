package com.arksh.common.rx;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * RxJava调度管理
 * Created by xsf
 * on 2016.08.14:50
 */
public class RxSchedulers {
    public static <T> Observable.Transformer<T, T> io_main() {
        return observable -> observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
