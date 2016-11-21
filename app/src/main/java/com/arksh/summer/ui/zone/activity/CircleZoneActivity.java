package com.arksh.summer.ui.zone.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.arksh.common.app.AppCache;
import com.arksh.common.base.BaseActivity;
import com.arksh.common.utils.ToastUitl;
import com.arksh.common.widget.LoadingTip;
import com.arksh.common.widget.NormalTitleBar;
import com.arksh.summer.R;
import com.arksh.summer.ui.zone.bean.CircleItem;
import com.arksh.summer.ui.zone.bean.CommentConfig;
import com.arksh.summer.ui.zone.bean.CommentItem;
import com.arksh.summer.ui.zone.bean.FavortItem;
import com.arksh.summer.ui.zone.contract.CircleZoneContract;
import com.arksh.summer.ui.zone.model.ZoneModel;
import com.arksh.summer.ui.zone.presenter.CircleZonePresenter;
import com.arksh.summer.ui.zone.widget.ZoneHeaderView;
import com.aspsine.irecyclerview.IRecyclerView;
import com.aspsine.irecyclerview.bean.PageBean;
import com.github.clans.fab.FloatingActionMenu;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/11/21 0021.
 */

public class CircleZoneActivity extends BaseActivity<CircleZonePresenter, ZoneModel> implements CircleZoneContract.View {

    @BindView(R.id.ntb)
    NormalTitleBar ntb;
    @BindView(R.id.irc)
    IRecyclerView irc;
    @BindView(R.id.loadedTip)
    LoadingTip loadedTip;
    @BindView(R.id.circleEt)
    EditText circleEt;
    @BindView(R.id.sendIv)
    ImageView sendIv;
    @BindView(R.id.editTextBodyLl)
    LinearLayout editTextBodyLl;
    @BindView(R.id.menu_red)
    FloatingActionMenu menuRed;
    //朋友圈头部
    ZoneHeaderView zoneHeaderView;

    private CommentConfig mCommentConfig;

    public static void startAction(Context context) {
        Intent intent = new Intent(context, CircleZoneActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.act_circle_list;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        menuRed.setClosedOnTouchOutside(true);
        //点赞效果初始化
        ntb.setTitleText(getString(R.string.circle_zone));
        //滑动列表关闭输入框
        irc.setOnTouchListener((view, motionEvent) -> {
            if (editTextBodyLl.getVisibility() == View.VISIBLE)
                updateEditTextBodyVisible(View.GONE, null);
            return false;
        });
        //初始化头部未读消息
        zoneHeaderView = new ZoneHeaderView(this);
        zoneHeaderView.setData(getString(R.string.nick_name), AppCache.getInstance().getIcon());
        irc.addHeaderView(zoneHeaderView);

    }
    /**
     * 点击事件
     *
     * @param view
     */
    @OnClick({R.id.tv_back, R.id.tv_right, R.id.sendIv, R.id.menu_red, R.id.fab1, R.id.fab2, R.id.fab3, R.id.fab4, R.id.fab5})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_right:
                break;
            //评论
            case R.id.sendIv:
                if (mPresenter != null) {
                    //发布评论
                    String content = circleEt.getText().toString().trim();
                    if (TextUtils.isEmpty(content)) {
                        ToastUitl.showToastWithImg("评论内容不能为空", R.drawable.ic_warm);
                        return;
                    }
                    mPresenter.addComment(content, mCommentConfig);
                }
                updateEditTextBodyVisible(View.GONE, null);
                break;
            case R.id.menu_red:
                break;
            case R.id.fab1:
                menuRed.close(true);
            case R.id.fab2:
                menuRed.close(true);
            case R.id.fab3:
                menuRed.close(true);
            case R.id.fab4:
                menuRed.close(true);
            case R.id.fab5:
                menuRed.close(true);
                //CirclePublishActivity.startAction(this);
                break;
        }
    }
    @Override
    public void updateNotReadNewsCount(int count, String icon) {

    }

    @Override
    public void setListData(List<CircleItem> circleItems, PageBean pageBean) {

    }

    @Override
    public void setOnePublishData(CircleItem circleItem) {

    }

    @Override
    public void update2DeleteCircle(String circleId, int position) {

    }

    @Override
    public void update2AddFavorite(int circlePosition, FavortItem addItem) {

    }

    @Override
    public void update2DeleteFavort(int circlePosition, String UserId) {

    }

    @Override
    public void update2AddComment(int circlePosition, CommentItem addItem) {

    }

    @Override
    public void update2DeleteComment(int circlePosition, String commentId, int commentPosition) {

    }

    @Override
    public void updateEditTextBodyVisible(int visibility, CommentConfig commentConfig) {

    }

    @Override
    public void showLoading(String title) {

    }

    @Override
    public void stopLoading() {

    }

    @Override
    public void showErrorTip(String msg) {

    }

}
