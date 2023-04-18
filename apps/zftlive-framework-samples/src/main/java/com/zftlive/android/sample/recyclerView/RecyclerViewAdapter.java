package com.zftlive.android.sample.recyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zftlive.android.R;
import com.zftlive.android.library.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView列表适配器
 *
 * @author 曾繁添
 * @version 1.0
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    /**
     * 上下文
     */
    protected Context mContext;

    /**
     * 数据源
     */
    private List dataSource = new ArrayList();

    /**
     * 日志输出标识
     */
    protected final static String TAG = "RecyclerViewAdapter";

    public RecyclerViewAdapter(Context mContext, List dataSource) {
        this.mContext = mContext;
        this.dataSource = dataSource;
    }

    @Override
    public int getItemCount() {
        Logger.d(TAG, "getItemCount");
        return dataSource.size();
    }

    @Override
    public long getItemId(int position) {
        Logger.d(TAG, "getItemId");
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        Logger.d(TAG, "getItemViewType");
        //多itemType类型区分
        return super.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Logger.d(TAG, "onCreateViewHolder");

        RecyclerView.ViewHolder holder;
        //根据item类别加载不同ViewHolder

        View view = LayoutInflater.from(mContext).inflate(R.layout.activity_image_listview_item, parent, false);
        holder = new RecyclerViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Logger.d(TAG, "onBindViewHolder");
        if(holder instanceof RecyclerViewHolder){
            //填充数据
            RowDataBean rowData = (RowDataBean)getItem(position);
            RecyclerViewHolder mViewHolder = (RecyclerViewHolder)holder;
            mViewHolder.tv_title.setText(rowData.title);
            Glide.with(mContext).load(rowData.imageURL).into(mViewHolder.iv_icon);
        }
    }

    /**
     * 获取当前位置对应的数据源
     *
     * @param position
     * @return
     */
    public Object getItem(int position) {
        if (position < dataSource.size())
            return dataSource.get(position);
        else
            return null;
    }

    /**
     * ViewHolder视图缓存
     */
    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_icon;
        TextView tv_title;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            iv_icon = (ImageView) itemView.findViewById(R.id.iv_icon);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
        }
    }
}
