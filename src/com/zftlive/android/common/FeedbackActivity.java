package com.zftlive.android.common;

import org.apache.http.Header;
import org.json.JSONObject;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.zftlive.android.R;
import com.zftlive.android.base.BaseActivity;
import com.zftlive.android.data.DTO;
import com.zftlive.android.tools.ToolAlert;
import com.zftlive.android.tools.ToolData;
import com.zftlive.android.tools.ToolHTTP;
import com.zftlive.android.tools.ToolString;

/**
 * 意见反馈Activity
 * @author Administrator
 * @version 1.0
 */
public class FeedbackActivity extends BaseActivity implements OnClickListener {

	private EditText et_message;
	private Button btn_sumbit;
	private ViewGroup root;
	private final static String FEED_BACK_URL = "";
	
	@Override
	public int bindLayout() {
		return R.layout.activity_feedback;
	}

	@Override
	public void initView(View view) {
		root = (ViewGroup)findViewById(R.id.ll_root);
		et_message = (EditText)findViewById(R.id.et_message);
		btn_sumbit = (Button)findViewById(R.id.btn_sumbit);
		//TODO 需真实数据接口
//		btn_sumbit.setOnClickListener(this);
	}

	@Override
	public void doBusiness(Context mContext) {
		
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
		case R.id.btn_sumbit:
			//提交表单
			if(validateForm()){
				ToolHTTP.post(FEED_BACK_URL, ToolData.gainForm(root, new DTO<String,Object>()), getFeedBackHandler());
			}
			break;
		default:
			break;
		}
	}
	
	/**
	 * 表单验证
	 * @return
	 */
	private boolean validateForm(){
		String strMessage = et_message.getText().toString();
		if(ToolString.isNoBlankAndNoNull(strMessage)){
			return true;
		}else{
			ToolAlert.showShort(getContext(), "请输入意见内容再提交，谢谢^_^");
			return false;
		}
	}
	
	/*
	 * 提交意见反馈Handler
	 */
	private JsonHttpResponseHandler getFeedBackHandler(){
		return new JsonHttpResponseHandler(){

			@Override
			public void onFailure(int statusCode, Header[] headers,String responseString, Throwable throwable) {
				ToolAlert.showShort(getContext(), "反馈意见失败，原因："+throwable.getMessage());
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,JSONObject response) {
				ToolAlert.showShort(getContext(), "感谢您的意见!");
				finish();
			}
		};
	}
}
