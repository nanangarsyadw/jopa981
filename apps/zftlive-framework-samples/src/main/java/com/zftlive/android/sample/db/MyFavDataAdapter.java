/*
 *     Android基础开发个人积累、沉淀、封装、整理共通
 *     Copyright (c) 2016. 曾繁添 <zftlive@163.com>
 *     Github：https://github.com/zengfantian || http://git.oschina.net/zftlive
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */

package com.zftlive.android.sample.db;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zftlive.android.R;
import com.zftlive.android.library.base.adapter.BaseMAdapter;

/**
 * 我的收藏列表适配器
 * @author 曾繁添
 * @version 1.0
 *
 */
public class MyFavDataAdapter extends BaseMAdapter {

	private final static String TAG = "MyFavDataAdapter";
	
	private int mRightWidth = 0;
	
	public MyFavDataAdapter(Activity activity,int rightWidth) {
		super(activity);
		mRightWidth = rightWidth;
	}

	@SuppressLint("ResourceAsColor")
	@Override
	public View getView(final int position, View itemView, ViewGroup parent) {

		// 查找控件
		Holder holder = null;
		if (null == itemView) {
			itemView = LayoutInflater.from(getActivity()).inflate(R.layout.activity_db_fav_list_item, null);
			holder = new Holder();
			//左右两侧
            holder.item_left = (RelativeLayout)itemView.findViewById(R.id.item_left);
            holder.item_right = (RelativeLayout)itemView.findViewById(R.id.item_right);
			
			holder.tv_publish_date = (TextView) itemView.findViewById(R.id.tv_publish_date);
			holder.tv_category = (TextView) itemView.findViewById(R.id.tv_category);
			holder.tv_title = (TextView) itemView.findViewById(R.id.tv_title);
			holder.btn_go = (ImageView) itemView.findViewById(R.id.btn_go);
			
			//删除按钮
			holder.item_right_txt = (TextView)itemView.findViewById(R.id.item_right_txt);
			
			itemView.setTag(holder);
		} else {
			holder = (Holder) itemView.getTag();
		}

        LinearLayout.LayoutParams lp1 = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
        holder.item_left.setLayoutParams(lp1);
        LinearLayout.LayoutParams lp2 = new LayoutParams(mRightWidth, LayoutParams.MATCH_PARENT);
        holder.item_right.setLayoutParams(lp2);
		
		// 装填数据 TODO
//		Favorite data = (Favorite) getItem(position);
		Favorite data = null;
		try {
			
			//发布日期
			String favDate = data.getPublishDate();
			if(favDate.length() > 10){
				favDate = favDate.substring(0, 10);
			}
			holder.tv_publish_date.setText(favDate);
			holder.tv_category.setText(data.getCategory());
			holder.tv_title.setText(data.getTitle());
			holder.btn_go.setTag(position);
			holder.btn_go.setOnClickListener(new MyFavClickListener(data.getLinkURL()));
		} catch (Exception e) {
			Log.e(TAG, e.getMessage().toString());
		}
		
		//滑动删除按钮点击监听事件
		holder.item_right.setOnClickListener(new OnClickListener() {
	            @Override
	            public void onClick(View v) {
	                if (mListener != null) {
	                    mListener.onRightItemClick(v,position);
	                }
	            }
	        });
		
		return itemView;
	}

    /**
     * 单击事件监听器
     */
    private onRightItemClickListener mListener = null;
    
    public void setOnRightItemClickListener(onRightItemClickListener listener){
    	mListener = listener;
    }
	
	/***控件保持Holder**/
	public class Holder {
    	RelativeLayout item_left;
    	RelativeLayout item_right;
		TextView tv_publish_date;
		TextView tv_category;
		TextView tv_title;
		ImageView btn_go;
        TextView item_right_txt;
	}
	
	/**
	 * 我的收藏列表点击事件监听器
	 *
	 */
	public class MyFavClickListener implements View.OnClickListener{

		private String linkURL = "" ;
		
		public MyFavClickListener(String linkURL) {
			super();
			this.linkURL = linkURL;
		}

		@Override
		public void onClick(View v) {
//			Intent intent = new Intent(getActivity(),EssayDetailActivity.class);
//			intent.putExtra("url", linkURL);
//			getActivity().startActivity(intent);
		}
	}
	
    public interface onRightItemClickListener {
        void onRightItemClick(View v, int position);
    }

}
