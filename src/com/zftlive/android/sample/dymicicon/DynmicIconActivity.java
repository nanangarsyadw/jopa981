package com.zftlive.android.sample.dymicicon;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zftlive.android.R;
import com.zftlive.android.library.base.BaseActivity;

/**
 * 动态创建桌面快捷方式/更新图标示例
 * 
 * @author 曾繁添
 * @version 1.0
 * 
 */
public class DynmicIconActivity extends BaseActivity implements
		View.OnClickListener {

	Button btn_create_shortcut, btn_update_shortcut;

	@Override
	public int bindLayout() {
		return R.layout.activity_dymic_icon;
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
		btn_create_shortcut = (Button) findViewById(R.id.btn_create_shortcut);
		btn_update_shortcut = (Button) findViewById(R.id.btn_update_shortcut);
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
		case R.id.btn_create_shortcut:

			break;
		case R.id.btn_update_shortcut:

			break;

		default:
			break;
		}
	}
	
	private void setIcon(String activity_alias) {
//        Context ctx = GlobalApplication.gainContext();
//        PackageManager pm = ctx.getPackageManager();
//        ActivityManager am = (ActivityManager) ctx.getSystemService(Activity.ACTIVITY_SERVICE);
// 
//        // Enable/disable activity-aliases
//        pm.setComponentEnabledSetting(
//                new ComponentName(ctx, Launcher.class.getName()),
//                Launcher.class.getName().equals(activity_alias) ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED
//                        : PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
//                PackageManager.DONT_KILL_APP);
//        pm.setComponentEnabledSetting(
//                new ComponentName(ctx, ACTIVITY_ALIAS_2),
//                ACTIVITY_ALIAS_2.equals(activity_alias) ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED
//                        : PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
//                PackageManager.DONT_KILL_APP);
// 
//        // Find launcher and kill it
//        Intent i = new Intent(Intent.ACTION_MAIN);
//        i.addCategory(Intent.CATEGORY_HOME);
//        i.addCategory(Intent.CATEGORY_DEFAULT);
//        List<ResolveInfo> resolves = pm.queryIntentActivities(i, 0);
//        for (ResolveInfo res : resolves) {
//            if (res.activityInfo != null) {
//                am.killBackgroundProcesses(res.activityInfo.packageName);
//            }
//        }
    }

}
