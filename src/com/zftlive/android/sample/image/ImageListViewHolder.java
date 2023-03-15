package com.zftlive.android.sample.image;

import java.util.Map;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zftlive.android.R;
import com.zftlive.android.library.base.BaseViewHolder;
import com.zftlive.android.library.third.universalimageloader.core.ImageLoader;
import com.zftlive.android.library.tools.ToolImage;

/**
 * ViewHolder绑定控件、数据
 *
 */
public class ImageListViewHolder extends BaseViewHolder<Map<String,Object>>{

	ImageView iv_icon;
	TextView tv_title;
	ImageLoader universalimageloader;
	Context mContext;
	
	public ImageListViewHolder(Context mContext) {
		super(mContext);
		this.mContext = mContext;
		universalimageloader = ToolImage.getImageLoader();
	}
	
	@Override
	public BaseViewHolder createViewHolder(){
		return new ImageListViewHolder(mContext);
	}
	
	@Override
	public int bindLayout() {
		return R.layout.activity_image_listview_item;
	}

	@Override
	public void initView(View view) {
		iv_icon = (ImageView)findViewById(R.id.iv_icon);
		tv_title = (TextView)findViewById(R.id.tv_title);
	}

	@Override
	public void fillData(Map<String,Object> rowData, int position) {
		//异步加载图片防止错位方法二：com.nostra13.universalimageloader.core.ImageLoader
		universalimageloader.displayImage((String)rowData.get("imageUrl"), iv_icon, ToolImage.getFadeOptions(R.drawable.default_icon,R.drawable.ic_launcher));
		tv_title.setText((String)rowData.get("title"));
	}
}
