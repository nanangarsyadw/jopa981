package com.zftlive.android.sample.image.photoview;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.zftlive.android.R;
import com.zftlive.android.library.base.BaseActivity;

/**
 * 图片浏览器demo
 * 
 * @author 曾繁添
 * @version 1.0
 * 
 *
 */
public class PictureViewerDemoActivity extends BaseActivity {

  /** Item数据实体集合 */
  private ArrayList<ItemEntity> itemEntities = new ArrayList<ItemEntity>();
  /** ListView对象 */
  private ListView listview;
  private ListItemAdapter mListAdapter;
  
  @Override
  public int bindLayout() {
    return R.layout.activity_photoview_main;
  }

  @Override
  public void initParms(Bundle parms) {

  }

  @Override
  public void initView(View view) {
    listview = (ListView) findViewById(R.id.listview);
    mListAdapter = new ListItemAdapter(this, itemEntities);
    listview.setAdapter(mListAdapter);
  }

  @Override
  public void doBusiness(Context mContext) {
    initDemoData();

    //初始化带返回按钮的标题栏
    String strCenterTitle = getResources().getString(R.string.PictureViewerActivity);
//  ActionBarManager.initBackTitle(getContext(), getActionBar(), strCenterTitle);
    initBackTitleBar(strCenterTitle);
  }
  
  private void initDemoData(){
    // 1.无图片
    ItemEntity entity1 = new ItemEntity(//
            "http://img.my.csdn.net/uploads/201410/19/1413698871_3655.jpg", "张三", "今天天气不错...", null);
    itemEntities.add(entity1);
    // 2.1张图片
    ArrayList<String> urls_1 = new ArrayList<String>();
    urls_1.add("http://img.my.csdn.net/uploads/201410/19/1413698883_5877.jpg");
    ItemEntity entity2 = new ItemEntity(//
            "http://img.my.csdn.net/uploads/201410/19/1413698865_3560.jpg", "李四", "今天雾霾呢...", urls_1);
    itemEntities.add(entity2);
    // 3.3张图片
    ArrayList<String> urls_2 = new ArrayList<String>();
    urls_2.add("http://img.my.csdn.net/uploads/201410/19/1413698867_8323.jpg");
    urls_2.add("http://img.my.csdn.net/uploads/201410/19/1413698883_5877.jpg");
    urls_2.add("http://img.my.csdn.net/uploads/201410/19/1413698837_5654.jpg");
    ItemEntity entity3 = new ItemEntity(//
            "http://img.my.csdn.net/uploads/201410/19/1413698837_5654.jpg", "王五", "今天好大的太阳...", urls_2);
    itemEntities.add(entity3);
    // 4.6张图片
    ArrayList<String> urls_3 = new ArrayList<String>();
    urls_3.add("http://img.my.csdn.net/uploads/201410/19/1413698837_7507.jpg");
    urls_3.add("http://img.my.csdn.net/uploads/201410/19/1413698865_3560.jpg");
    urls_3.add("http://img.my.csdn.net/uploads/201410/19/1413698867_8323.jpg");
    urls_3.add("http://img.my.csdn.net/uploads/201410/19/1413698837_5654.jpg");
    urls_3.add("http://img.my.csdn.net/uploads/201410/19/1413698883_5877.jpg");
    urls_3.add("http://img.my.csdn.net/uploads/201410/19/1413698839_2302.jpg");
    ItemEntity entity4 = new ItemEntity(//
            "http://img.my.csdn.net/uploads/201410/19/1413698883_5877.jpg", "赵六", "今天下雨了...", urls_3);
    itemEntities.add(entity4);
    mListAdapter.notifyDataSetChanged();
  }
}
