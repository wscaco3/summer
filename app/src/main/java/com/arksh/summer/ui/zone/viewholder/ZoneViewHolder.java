package com.arksh.summer.ui.zone.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.arksh.summer.R;
import com.arksh.summer.ui.zone.bean.CircleItem;
import com.arksh.summer.ui.zone.presenter.CircleZonePresenter;

/**
 * Created by Administrator on 2016/11/21 0021.
 */

public class ZoneViewHolder extends RecyclerView.ViewHolder {

    private Context mContext;
    private int type;
    private View itemView;

    public ZoneViewHolder(View itemView, final Context context, int type) {
        super(itemView);
        this.itemView=itemView;
        this.type=type;
        this.mContext = context;
        //initView();
    }

    public static ZoneViewHolder create(Context context, int type) {
        ZoneViewHolder imageViewHolder = new ZoneViewHolder(LayoutInflater.from(context).inflate(R.layout.item_circle_list, null), context,type );
        return imageViewHolder;
    }
    /**
     * 设置数据
     * @param circleItem2
     * @param position2
     */
    public void setData(CircleZonePresenter mPresenter2, CircleItem circleItem2, final int position2){

    }
}
