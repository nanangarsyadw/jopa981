package com.zftlive.android.sample.basic;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.zftlive.android.R;
import com.zftlive.android.library.base.BaseActivity;
import com.zftlive.android.library.tools.ToolAlert;
import com.zftlive.android.library.tools.ToolDateTime;
import com.zftlive.android.library.tools.ToolLocation;
import com.zftlive.android.library.tools.ToolPhone;
import com.zftlive.android.library.tools.ToolString;
import com.zftlive.android.library.widget.AlignTextView;
import com.zftlive.android.library.widget.OperationDialog;
import com.zftlive.android.library.widget.OperationDialog.DialogBuilder;
import com.zftlive.android.library.widget.OperationDialog.ItemBean;

/**
 * 基本常用操作测试样例
 * @author 曾繁添
 * @version 1.0
 *
 */
public class BasicTestActivity extends BaseActivity implements View.OnClickListener{

	private Button btn_opengps, btn_call,btn_contact,btn_setting,btn_carema,btn_photo;
	private boolean flag = true;
	private Thread task = null;
	private TextView tv_hanziyingwen1,tv_hanziyingwen2;
	private AlignTextView tv_align;
	
	@Override
	public int bindLayout() {
		return R.layout.activity_basic_test;
	}
	
	@Override
	public View bindView() {
		return null;
	}

	@Override
	public void initParms(Bundle parms) {
		
	}
	
	@SuppressLint("NewApi")
	@Override
	public void initView(View view) {
		btn_opengps = (Button) findViewById(R.id.btn_opengps);
		btn_opengps.setOnClickListener(this);
		btn_call = (Button) findViewById(R.id.btn_call);
		btn_call.setOnClickListener(this);
		btn_contact = (Button) findViewById(R.id.btn_contact);
		btn_contact.setOnClickListener(this);
		btn_setting = (Button) findViewById(R.id.btn_setting);
		btn_setting.setOnClickListener(this);
		btn_carema = (Button) findViewById(R.id.btn_carema);
		btn_carema.setOnClickListener(this);
		btn_photo = (Button) findViewById(R.id.btn_photo);
		btn_photo.setOnClickListener(this);
		
		tv_hanziyingwen1 = (TextView) findViewById(R.id.tv_hanziyingwen1);
		tv_hanziyingwen2 = (TextView) findViewById(R.id.tv_hanziyingwen2);
		tv_align  = (AlignTextView) findViewById(R.id.tv_align);
		
		//初始化带返回按钮的标题栏
		String strCenterTitle = getResources().getString(R.string.BasicTestActivity);
//      ActionBarManager.initBackTitle(getContext(), getActionBar(), strCenterTitle);
        initBackTitleBar(strCenterTitle);	

	}

	@Override
	public void doBusiness(Context mContext) {
//		task = new Thread(new ThreadTask());
//		task.start();

		//中英文混排对其问题
		String strText = "今天忽然发现Android项目中的文字排版参差不齐的情况非常严重，不得不想办法解决一下。1234568经过研究之后，abceHHHCCC终于找到了textview自动换行导致混乱的原因了----半角字符与全角字符混乱所致！一般情况下，我们输入的数字、字母以及英文标点都是半角，所以占位无法确定。";
		
		tv_hanziyingwen1.setText(strText);
		tv_hanziyingwen2.setText(ToolString.ToSBC(strText));
		tv_align.setText(strText);
		
	}

	@Override
	public void resume() {

	}

	@Override
	public void destroy() {

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_opengps:
			ToolLocation.forceOpenGPS(this);
			break;
		case R.id.btn_call:
			ToolPhone.callPhone(this, "10086");
			break;
		case R.id.btn_contact:
			ToolPhone.toChooseContactsList(getContext(), 99);
			break;
		case R.id.btn_setting:
			ToolPhone.toSettingActivity(getContext());
			break;
		case R.id.btn_carema:
			ToolPhone.toCameraActivity(getContext(), 88);
			break;
		case R.id.btn_photo:
//			ToolPhone.toImagePickerActivity(getContext(), 77);
//			flag = false;
//			task.interrupt();
			
			//初始化Item数据
            List<ItemBean> mItemData  = new ArrayList<ItemBean>();
            ItemBean item1 = new ItemBean();
            item1.isShowGo = false;
            item1.isShowOkay = true;
            item1.mLeftMainTitle = "定期理财";
            item1.mLeftSubTitle = "货币基金";
            item1.mRightMainTitle = "";
            mItemData.add(item1);
            
            ItemBean item2 = new ItemBean();
            item2.isShowGo = true;
            item2.isShowOkay = false;
            item2.mLeftMainTitle = "货币基金";
            item2.mLeftSubTitle = "定期理财";
            item2.mRightMainTitle = "年收益率6.5%";
            mItemData.add(item2);
            
            //创建一个Dialog
            new DialogBuilder(this)
            .setGravity(Gravity.BOTTOM)
            .setCanceledOnTouchOutside(false)
            .showButtomFooter(false)
            .setMainTitle(Html.fromHtml("为您搜索到了<font color='#359df5'>"+6+"</font>条相关内容"))
            .setItemClickListener(new OperationDialog.ItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                  ItemBean rowData = (ItemBean) parent.getItemAtPosition(position);
                  ToolAlert.toastShort(getContext(), rowData.mLeftMainTitle + "-->"+position);
                }
            })
            .setItemData(mItemData)
            .build()
            .show();
			
			break;
		default:
			break;
		}
	}

	
	public class ThreadTask implements Runnable{

		@Override
		public void run() {
			while(flag){
				Log.e(TAG, "Thread ID "+Thread.currentThread().getId() + "-->"+ToolDateTime.gainCurrentDate("yyyy-MM-dd HH:mm:ss"));
				try {
					Thread.sleep(3 * 1000);
				} catch (InterruptedException e) {
					Log.e(TAG, "线程被打断!");
				}
			}
		}
	}
}
