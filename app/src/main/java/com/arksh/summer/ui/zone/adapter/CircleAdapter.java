package com.arksh.summer.ui.zone.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.arksh.summer.ui.zone.bean.CircleItem;
import com.arksh.summer.ui.zone.presenter.CircleZonePresenter;
import com.arksh.summer.ui.zone.viewholder.ZoneViewHolder;
import com.aspsine.irecyclerview.baseadapter.BaseReclyerViewAdapter;

/**
 * Created by Administrator on 2016/11/21 0021.
 */

public class CircleAdapter extends BaseReclyerViewAdapter<CircleItem> {
    public static final int ITEM_VIEW_TYPE_DEFAULT = 0;
    public static final int ITEM_VIEW_TYPE_IMAGE = 1;
    public static final int ITEM_VIEW_TYPE_URL = 2;
    private Context mContext;
    private CircleZonePresenter mPresenter;

    public CircleAdapter(Context context, CircleZonePresenter mPresenter) {
        super(context);
        this.mContext=context;
        this.mPresenter=mPresenter;
    }

    @Override
    public int getItemViewType(int position) {
        return getData().get(position).getType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ZoneViewHolder.create(mContext,viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        if(holder instanceof ZoneViewHolder) {
            ((ZoneViewHolder) holder).setData(mPresenter, get(position), position);
        }
    }
}
