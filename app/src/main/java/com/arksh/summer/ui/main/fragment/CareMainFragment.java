package com.arksh.summer.ui.main.fragment;

import android.widget.ImageView;

import com.arksh.common.base.BaseFragment;
import com.arksh.common.mode.ChangeModeController;
import com.arksh.common.widget.NormalTitleBar;
import com.arksh.summer.R;
import com.arksh.summer.ui.news.activity.AboutActivity;
import com.arksh.summer.ui.zone.activity.CircleZoneActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class CareMainFragment extends BaseFragment {
    @BindView(R.id.ntb)
    NormalTitleBar ntb;
    @BindView(R.id.iv_add)
    ImageView ivAdd;

    @Override
    protected int getLayoutResource() {
        return R.layout.fra_care_main;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        ntb.setTvLeftVisiable(false);
        ntb.setTitleText(getContext().getString(R.string.care_main_title));
    }


    @OnClick(R.id.ll_friend_zone)
    public void friendZone(){
        CircleZoneActivity.startAction(getContext());
    }
    @OnClick(R.id.ll_daynight_toggle)
    public void dayNightToggle(){
        ChangeModeController.toggleThemeSetting(getActivity());
    }
    @OnClick(R.id.ll_daynight_about)
    public void about(){
        AboutActivity.startAction(getContext());
    }
}
