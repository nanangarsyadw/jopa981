package com.zftlive.android.sample.basic;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.zftlive.android.R;
import com.zftlive.android.library.base.BaseActivity;
import com.zftlive.android.library.common.ActionBarManager;
import com.zftlive.android.library.common.WebPageActivity;
import com.zftlive.android.library.tools.ToolToast;

/**
 * 网页加载示例
 * 
 * @author 曾繁添
 * @version 1.0
 *
 */
public class LoadHTMLActivity extends BaseActivity implements View.OnClickListener{

	private EditText et_web_url;
	private Button btn_go;
	
	@Override
	public int bindLayout() {
		return R.layout.activity_load_html;
	}

	@Override
	public View bindView() {
		return null;
	}

	@Override
	public void initParms(Bundle parms) {

	}

	@Override
	public void initView(View view) {
		et_web_url = (EditText) findViewById(R.id.et_loadurl);
		btn_go = (Button) findViewById(R.id.btn_go);
		btn_go.setOnClickListener(this);
	}

	@Override
	public void doBusiness(Context mContext) {

		//初始化值
		et_web_url.setText("http://git.oschina.net/zftlive/");
		
		//初始化带返回按钮的标题栏
		String strCenterTitle = getResources().getString(R.string.LoadHTMLActivity);
//      ActionBarManager.initBackTitle(getContext(), getActionBar(), strCenterTitle);
        initBackTitleBar(strCenterTitle);
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
		case R.id.btn_go:
			String strLinkURL = et_web_url.getText().toString();
			if(TextUtils.isEmpty(strLinkURL)){
				ToolToast.showShort(getContext(), "请输入要打开的网址");
			}
			if(!strLinkURL.startsWith("http://")){
				strLinkURL = "http://" + strLinkURL;
			}
			getOperation().addParameter(WebPageActivity.LINK_URL, strLinkURL);
			getOperation().addParameter(WebPageActivity.SHOW_SHARE, true);
			getOperation().forward(WebPageActivity.class.getName(), LEFT_RIGHT);
			break;
		default:
			break;
		}
	}
}
