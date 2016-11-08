package com.arksh.summer.ui.news.presenter;

import com.arksh.common.rx.RxSubscriber;
import com.arksh.common.utils.ToastUitl;
import com.arksh.summer.R;
import com.arksh.summer.bean.NewsDetail;
import com.arksh.summer.ui.news.contract.NewsDetailContract;

/**
 * Created by Administrator on 2016/11/8 0008.
 */

public class NewsDetailPresenter extends NewsDetailContract.Presenter {
    @Override
    public void getOneNewsDataRequest(String postId) {
        mRxManage.add(mModel.getOneNewsData(postId).subscribe(new RxSubscriber<NewsDetail>(mContext) {
            @Override
            protected void _onNext(NewsDetail newsDetail) {
                mView.returnOneNewsData(newsDetail);
            }

            @Override
            protected void _onError(String message) {
                ToastUitl.showToastWithImg(message, R.drawable.ic_wrong);
            }
        }));
    }
}
