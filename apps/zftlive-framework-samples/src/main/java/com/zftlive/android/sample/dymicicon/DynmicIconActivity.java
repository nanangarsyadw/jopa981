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

package com.zftlive.android.sample.dymicicon;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zftlive.android.R;
import com.zftlive.android.library.base.ui.CommonActivity;

/**
 * 动态创建桌面快捷方式/更新图标示例
 * 
 * @author 曾繁添
 * @version 1.0
 * 
 */
public class DynmicIconActivity extends CommonActivity implements
		View.OnClickListener {

	Button btn_create_shortcut, btn_update_shortcut;

	@Override
	public int bindLayout() {
		return R.layout.activity_dymic_icon;
	}

	@Override
	public void initParams(Bundle parms) {

	}

	@Override
	public void initView(View view) {
		btn_create_shortcut = (Button) findViewById(R.id.btn_create_shortcut);
		btn_update_shortcut = (Button) findViewById(R.id.btn_update_shortcut);
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
