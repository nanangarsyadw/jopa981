package com.zftlive.android.sample.menu;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.zftlive.android.R;
import com.zftlive.android.base.BaseActivity;

/**
 * 抽屉菜单
 * 
 * @author 曾繁添
 * @version 1.0
 * 
 */
public class DrawerLayoutActivity extends BaseActivity {
	private String[] mPlanetTitles;
	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private ListView mDrawerList;

	@Override
	public int bindLayout() {
		return R.layout.activity_drawerlayout;
	}

	@Override
	public void initView(View view) {
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		// 绑定Listview
		mPlanetTitles = getResources().getStringArray(R.array.anim_type);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		mDrawerList.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, mPlanetTitles));
		mDrawerList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				setTitle(mPlanetTitles[position]);
				mDrawerList.setItemChecked(position, true);
				mDrawerLayout.closeDrawer(mDrawerList);
			}
		});

		// 抽屉菜单
		mDrawerLayout.setDrawerShadow(R.drawable.view_fixheadtable_shadow_left,
				GravityCompat.START);
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_menu_black_36dp, R.drawable.ic_menu_black_36dp,
				R.drawable.ic_expand_more_grey600_36dp) {
			public void onDrawerClosed(View view) {
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);
	}

	@Override
	public void doBusiness(Context mContext) {

		// ActionBar配置 Note: getActionBar() Added in API level 11
		getActionBar().setDisplayShowHomeEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayUseLogoEnabled(true);
		getActionBar().setLogo(R.drawable.ic_menu_black_36dp);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public void resume() {

	}

	@Override
	public void destroy() {

	}

}