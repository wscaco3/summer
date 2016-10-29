package com.arksh.summer.ui.news.fragment;


import android.widget.TextView;

import com.arksh.common.base.BaseFragment;
import com.arksh.summer.R;
import com.arksh.summer.app.AppConstant;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/10/29 0029.
 */

public class NewsFrament extends BaseFragment {

    @BindView(R.id.textView3)
    TextView textView3;
    private String mNewsId;
    private String mNewsType;

    @Override
    protected int getLayoutResource() {
        return R.layout.fra_news_list;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        if (getArguments() != null) {
            mNewsId = getArguments().getString(AppConstant.NEWS_ID);
            mNewsType = getArguments().getString(AppConstant.NEWS_TYPE);
            textView3.setText(mNewsId);
        }
    }

}
