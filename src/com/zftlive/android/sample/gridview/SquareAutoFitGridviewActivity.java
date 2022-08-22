package com.zftlive.android.sample.gridview;

import java.util.LinkedHashMap;
import java.util.Map;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.zftlive.android.R;
import com.zftlive.android.base.BaseActivity;
import com.zftlive.android.base.BaseAdapter;
import com.zftlive.android.common.ActionBarManager;
import com.zftlive.android.tools.ToolAlert;
import com.zftlive.android.tools.ToolImage;

/**
 * 正方形Item的gridview样例
 * @author 曾繁添
 * @version 1.0
 *
 */
public class SquareAutoFitGridviewActivity extends BaseActivity {

	private GridView gv_square;
	private MyGridAdapter mAdapter;
	private String imageURLs[] = new String[]{
			"http://www.daqianduan.com/wp-content/uploads/2014/12/kanjian.jpg",
			"http://www.daqianduan.com/wp-content/uploads/2014/11/capinfo.jpg",
			"http://www.daqianduan.com/wp-content/uploads/2014/11/mi-2.jpg",
			"http://www.daqianduan.com/wp-content/uploads/2014/10/dxy.cn_.png",
			"http://www.daqianduan.com/wp-content/uploads/2014/10/xinhua.jpg",
			"http://www.daqianduan.com/wp-content/uploads/2014/09/job.jpg",
			"http://www.daqianduan.com/wp-content/uploads/2013/06/ctrip.png",
			"http://www.daqianduan.com/wp-content/uploads/2014/09/ideabinder.png",
			"http://www.daqianduan.com/wp-content/uploads/2014/05/ymatou.png",
			"http://www.daqianduan.com/wp-content/uploads/2014/03/west_house.jpg",
			"http://www.daqianduan.com/wp-content/uploads/2014/03/youanxianpin.jpg",
			"http://www.daqianduan.com/wp-content/uploads/2014/02/jd.jpg",
			"http://www.daqianduan.com/wp-content/uploads/2013/11/wealink.png",
			"http://www.daqianduan.com/wp-content/uploads/2013/09/exmail.jpg",
			"http://www.daqianduan.com/wp-content/uploads/2013/09/alipay.png",
			"http://www.daqianduan.com/wp-content/uploads/2013/08/huaqiangbei.png",
			"http://www.daqianduan.com/wp-content/uploads/2013/06/ctrip.png",
			"http://www.daqianduan.com/static/img/thumbnail.png",
			"http://www.daqianduan.com/wp-content/uploads/2013/06/bingdian.png",
			"http://www.daqianduan.com/wp-content/uploads/2013/04/ctrip-wireless.png"
	};
	private String titles[] = new String[]{
			"前端开发工程师",
			"Web前端工程师(北京-海淀)",
			"更关注用户前端体验(北京)",
			"丁香园求多枚Web",
			"新华网招中高级",
			"好声音母公司梦响强音文化",
			"携程网国际业务部",
			"ideabinder",
			"海外购物公司洋码头",
			"金山软件-西山居",
			"优安鲜品招Web前端",
			"京东招聘Web前端开",
			"若邻网(上海)急聘程师",
			"腾讯广州研发线邮箱部）",
			"支付宝招募资深交互设计师",
			"华强北商城招聘前端开发工程师",
			"携程(上海)框架研发部",
			"阿里巴巴中文站招聘前端开发",
			"多途网络科端开发工程师",
			"携程无线前端团队招聘 直接"
	};
	
	@Override
	public int bindLayout() {
		return R.layout.activity_block_gridview;
	}

	@Override
	public void initView(View view) {
		gv_square = (GridView) findViewById(R.id.gv_square);
		gv_square.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Map<String,Object> itemData = (Map<String,Object>)parent.getItemAtPosition(position);
				ToolAlert.toastShort(getContext(), itemData.get("title")+"");
			}
		});
		mAdapter = new MyGridAdapter();
		ToolImage.initImageLoader(getContext());
		
		//初始化带返回按钮的标题栏
		String strCenterTitle = getResources().getString(R.string.SquareAutoFitGridviewActivity);
		ActionBarManager.initBackTitle(getContext(), getActionBar(), strCenterTitle);
	}

	@Override
	public void doBusiness(Context mContext) {
		//构造数据
		for (int i = 0; i < 20; i++) {
			Map<String,Object> rowData = new LinkedHashMap<String,Object>();
			rowData.put("imageUrl", imageURLs[i]);
			rowData.put("title", titles[i]);
			mAdapter.addItem(rowData);
		}
		gv_square.setAdapter(mAdapter);
		mAdapter.notifyDataSetChanged();
	}

	@Override
	public void resume() {

	}

	@Override
	public void destroy() {
		ToolImage.clearCache();
	}
	
	/**
	 * 网格适配器
	 *
	 */
	protected class MyGridAdapter extends BaseAdapter{

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder mViewHolder;
			if(null == convertView){
				convertView = getLayoutInflater().inflate(R.layout.activity_block_gridview_item, null);
				mViewHolder = new ViewHolder();
				mViewHolder.iv_icon = (ImageView)convertView.findViewById(R.id.iv_icon);
				mViewHolder.tv_title = (TextView)convertView.findViewById(R.id.tv_title);
				convertView.setTag(mViewHolder);
			}else{
				mViewHolder = (ViewHolder)convertView.getTag();
			}
			//设置数据
			Map<String,Object> rowData =  (Map)getItem(position);
			ToolImage.getImageLoader().displayImage((String)rowData.get("imageUrl"), mViewHolder.iv_icon);
			mViewHolder.tv_title.setText((String)rowData.get("title"));
			return convertView;
		}
		
		class ViewHolder{
			ImageView iv_icon;
			TextView tv_title;
		}
	}
}
