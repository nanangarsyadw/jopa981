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

package com.zftlive.android.sample.basic;

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
import com.zftlive.android.library.Alert;
import com.zftlive.android.library.base.ui.CommonActivity;
import com.zftlive.android.library.map.ToolLocation;
import com.zftlive.android.library.tools.ToolDateTime;
import com.zftlive.android.library.tools.ToolPhone;
import com.zftlive.android.library.tools.ToolString;
import com.zftlive.android.library.widget.dialog.OperationDialog;
import com.zftlive.android.library.widget.dialog.OperationDialog.OpButtonBean;
import com.zftlive.android.library.widget.dialog.OperationDialog.DialogBuilder;
import com.zftlive.android.library.widget.dialog.OperationDialog.OpItemBean;
import com.zftlive.android.library.widget.textview.AlignTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * 基本常用操作测试样例
 * @author 曾繁添
 * @version 1.0
 *
 */
public class BasicTestActivity extends CommonActivity implements View.OnClickListener{

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
	public void initParams(Bundle parms) {
		
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
		mWindowTitle.initBackTitleBar(strCenterTitle);

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
            List<OpItemBean> mItemData  = new ArrayList<OpItemBean>();
            OpItemBean item1 = new OpItemBean();
            item1.isShowGo = false;
            item1.isShowOkay = true;
            item1.leftMainTitle = "定期理财";
            item1.leftSubTitle = "货币基金";
            item1.rightMainTitle = "";
            mItemData.add(item1);
            
            OpItemBean item2 = new OpItemBean();
            item2.isShowGo = true;
            item2.isShowOkay = false;
            item2.leftMainTitle = "货币基金";
            item2.leftSubTitle = "定期理财";
            item2.rightMainTitle = "年收益率6.5%";
            mItemData.add(item2);
            
            //创建一个Dialog
            new DialogBuilder(this)
            .setGravity(Gravity.CENTER)
            .setCanceledOnTouchOutside(false)
            .showTopHeader(false)
            .showButtomFooter(false)
            .setFillScreenWith(false)
            .hasAnimation(false)
            .setMainTitle(Html.fromHtml("为您搜索到了<font color='#359df5'>"+6+"</font>条相关内容"))
            .addItemData(new OpItemBean("货币基金", "定期理财","年收益率6.5%", true))
            .addItemData(new OpItemBean("货币基金", "", R.drawable.recoment, "年收益率6.5%", true))
            .setItemClickListener(new OperationDialog.ItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                  OpItemBean rowData = (OpItemBean) parent.getItemAtPosition(position);
                  Alert.showCenterText(getContext(), "点击了第"+rowData.leftMainTitle+"个Item");
                }
            })
            .showItemListLastLine(true)
            
            .setBodyTitle("中间对话框标题")
            .setBodyMsg("zftlive，顾名思义“曾繁添的生活”，我是一个追求技术的代码狂，我的生活很单纯，占据我大部分生活的是代码，在工作之余整理/沉淀自己所学、所能，一方面可以记录一下，方便日后使用、开发；另一方面开源共享可以帮助一些需要这方面资源的同学，两全其美的事情何乐而不为。")
            .setOperationBtnDirection(OperationDialog.VERTICAL)
            .addOperationBtn(new OpButtonBean(R.id.action_fav, "取消"))
            .addOperationBtn(new OpButtonBean(R.id.action_settings, "确定","#FF0000"))
            .addOperationBtn(new OpButtonBean(R.id.action_about, "我知道了","#359df5"))
            .setOperationClickListener(new OperationDialog.OpClickListener() {
              
              @Override
              public void onClick(View v) {
                switch (v.getId()) {
                  case R.id.action_about:
					  Alert.showCenterText(getContext(), "点击了取消按钮");
                    break;
                  case R.id.action_settings:
					  Alert.showCenterText(getContext(), "点击了确定按钮");
                    break;
                  default:
                    break;
                }
              }
            })
            .setCancelListener(new OperationDialog.OpCancelListener() {
              
              @Override
              public void onCancel() {
                //Dialog关闭的时候，这里可以做收起软键盘
				  Alert.showCenterText(getContext(), "Dialog关闭了");
              }
            })
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
