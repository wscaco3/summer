package com.arksh.summer.ui.zone.presenter;

import android.view.View;

import com.arksh.common.app.AppCache;
import com.arksh.common.utils.JsonUtils;
import com.arksh.common.utils.LogUtils;
import com.arksh.common.utils.ToastUitl;
import com.arksh.summer.R;
import com.arksh.summer.app.AppConstant;
import com.arksh.summer.bean.Result;
import com.arksh.summer.ui.zone.DatasUtil;
import com.arksh.summer.ui.zone.bean.CircleItem;
import com.arksh.summer.ui.zone.bean.CommentConfig;
import com.arksh.summer.ui.zone.bean.CommentItem;
import com.arksh.summer.ui.zone.bean.FavortItem;
import com.arksh.summer.ui.zone.contract.CircleZoneContract;
import com.arksh.summer.ui.zone.widget.GoodView;
import com.aspsine.irecyclerview.bean.PageBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wevey.selector.dialog.DialogOnClickListener;
import com.wevey.selector.dialog.MDAlertDialog;

import java.util.List;
import java.util.Random;

import rx.Subscriber;
import rx.functions.Action1;

/**
 * Created by Administrator on 2016/11/21 0021.
 */

public class CircleZonePresenter extends CircleZoneContract.Presenter {

    //点赞效果
    private GoodView mGoodView;
    //删除朋友圈
    private MDAlertDialog mdAlertDialog;

    /**
     * 监听
     */
    @Override
    public void onStart() {
        super.onStart();
        LogUtils.logd("CircleZonePresenter onStart");
        //新增说说监听
        mRxManage.on(AppConstant.ZONE_PUBLISH_ADD, new Action1<CircleItem>() {
            @Override
            public void call(CircleItem circleItem) {
                if (circleItem != null) {
                    mView.setOnePublishData(circleItem);
                }
            }
        });
    }
    @Override
    public void getNotReadNewsCount() {
        mRxManage.add(mModel.getZoneNotReadNews().subscribe(icon -> mView.updateNotReadNewsCount(10, icon)));
    }

    @Override
    public void getListData(String type, String userId, int page, int rows) {
        //加载更多不显示加载条
        if (page <= 1)
            mView.showLoading("加载中...");
        mRxManage.add(mModel.getListDatas(type,userId,page,rows).subscribe(new Subscriber<Result>() {
            @Override
            public void onCompleted() {
                mView.stopLoading();
            }

            @Override
            public void onError(Throwable e) {
                mView.showErrorTip("加载出错了！");
            }

            @Override
            public void onNext(Result result) {

                if (result != null) {
                    try {
                        Gson gson = new Gson();
                        List<CircleItem> circleItems = gson.fromJson(JsonUtils.getValue(result.getMsg(), "list"), new TypeToken<List<CircleItem>>(){}.getType());
                        for (int i = 0; i < circleItems.size(); i++) {
                            circleItems.get(i).setPictures(DatasUtil.getRandomPhotoUrlString(new Random().nextInt(9)));
                        }
                        PageBean pageBean = gson.fromJson(JsonUtils.getValue(result.getMsg(), "page"), PageBean.class);
                        mView.setListData(circleItems, pageBean);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }));
    }

    @Override
    public void deleteCircle(String circleId, int position) {
        mdAlertDialog = new MDAlertDialog.Builder(mContext)
                .setHeight(0.25f)  //屏幕高度*0.3
                .setWidth(0.7f)  //屏幕宽度*0.7
                .setTitleVisible(true)
                .setTitleText("温馨提示")
                .setTitleTextColor(R.color.black_light)
                .setContentText("确定删除该条说说吗？")
                .setContentTextColor(R.color.black_light)
                .setLeftButtonText("不删除")
                .setLeftButtonTextColor(R.color.black_light)
                .setRightButtonText("删除")
                .setRightButtonTextColor(R.color.gray)
                .setTitleTextSize(16)
                .setContentTextSize(14)
                .setButtonTextSize(14).setOnclickListener(new DialogOnClickListener() {
                    @Override
                    public void clickLeftButton(View view) {
                        mdAlertDialog.dismiss();
                    }

                    @Override
                    public void clickRightButton(View view) {
                        mdAlertDialog.dismiss();
                        mView.startProgressDialog();
                        mRxManage.add(mModel.deleteCircle(circleId, position).subscribe(new Subscriber<Result>() {
                            @Override
                            public void onCompleted() {
                                mView.stopProgressDialog();
                            }

                            @Override
                            public void onError(Throwable e) {
                                mView.startProgressDialog();
                                ToastUitl.showToastWithImg(mContext.getString(R.string.net_error), R.drawable.ic_wrong);
                            }

                            @Override
                            public void onNext(Result result) {
                                mView.update2DeleteCircle(circleId, position);
                            }
                        }));
                    }
                })
                .build();
        mdAlertDialog.show();
    }

    @Override
    public void addFavort(String publishId, String publishUserId, int circlePosition, View view) {
        mView.startProgressDialog();
        mRxManage.add(mModel.addFavort(publishId,publishUserId).subscribe(new Subscriber<Result>() {
            @Override
            public void onCompleted() {
                mView.stopProgressDialog();
            }

            @Override
            public void onError(Throwable e) {
                ToastUitl.showToastWithImg(mContext.getString(R.string.net_error), R.drawable.ic_wrong);
            }

            @Override
            public void onNext(Result result) {
                if (result != null) {
                    if (mGoodView == null) {
                        mGoodView = new GoodView(mContext);
                    }
                    //mGoodView.setTextInfo("点赞成功", ContextCompat.getColor(mContext, R.color.main_color), 12);
                    mGoodView.setImage(R.drawable.dianzan);
                    mGoodView.show(view);
                    FavortItem item = new FavortItem(publishId, AppCache.getInstance().getUserId(), "jayden");
                    mView.update2AddFavorite(circlePosition, item);
                }
            }
        }));
    }

    @Override
    public void deleteFavort(String publishId, String publishUserId, int circlePosition) {
        mView.startProgressDialog();
        mRxManage.add(mModel.deleteFavort(publishId,publishUserId).subscribe(new Subscriber<Result>() {
            @Override
            public void onCompleted() {
                mView.stopProgressDialog();
            }

            @Override
            public void onError(Throwable e) {
                ToastUitl.showToastWithImg(mContext.getString(R.string.net_error), R.drawable.ic_wrong);
            }

            @Override
            public void onNext(Result result) {
                if (result != null) {
                    mView.update2DeleteFavort(circlePosition, AppCache.getInstance().getUserId());
                }
            }
        }));
    }

    @Override
    public void addComment(String content, CommentConfig config) {
        if (config == null) {
            return;
        }
        mView.startProgressDialog();
        mRxManage.add(mModel.addComment(config.getPublishUserId(), new CommentItem(config.getName(), config.getId(), content, config.getPublishId(), AppCache.getInstance().getUserId(), "jayden")).subscribe(new Subscriber<Result>() {

            @Override
            public void onCompleted() {
                mView.stopProgressDialog();
            }

            @Override
            public void onError(Throwable e) {
                ToastUitl.showToastWithImg(mContext.getString(R.string.net_error), R.drawable.ic_wrong);
            }

            @Override
            public void onNext(Result result) {
                if (result != null) {
                    mView.update2AddComment(config.circlePosition, new CommentItem(config.getName(), config.getId(), content, config.getPublishId(), AppCache.getInstance().getUserId(), "锋"));
                }
            }
        }));
    }

    @Override
    public void deleteComment(int circlePosition, String commentId, int commentPosition) {
        mView.startProgressDialog();
        mRxManage.add(mModel.deleteComment(commentId).subscribe(new Subscriber<Result>() {
            @Override
            public void onCompleted() {
                mView.stopProgressDialog();
            }

            @Override
            public void onError(Throwable e) {
                mView.stopProgressDialog();
                ToastUitl.showToastWithImg(mContext.getString(R.string.net_error), R.drawable.ic_wrong);
            }

            @Override
            public void onNext(Result result) {
                mView.update2DeleteComment(circlePosition, commentId, commentPosition);
            }
        }));
    }

    @Override
    public void showEditTextBody(CommentConfig commentConfig) {
        mView.updateEditTextBodyVisible(View.VISIBLE, commentConfig);
    }
}
