package com.zftlive.android.library.base;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import com.zftlive.android.library.MApplication;
import com.zftlive.android.library.widget.SwipeBackLayout;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * android 系统中的四大组件之一Activity基类
 * 
 * @author 曾繁添
 * @version 1.0
 * 
 */
public abstract class BaseActivity extends FragmentActivity implements IBaseActivity {

  /*** 整个应用Applicaiton **/
  private MApplication mApplication = null;
  /** 当前Activity的弱引用，防止内存泄露 **/
  private WeakReference<Activity> mContextWR = null;
  /** 当前Activity渲染的视图View **/
  private ViewGroup mContextView = null;
  /** 动画类型 **/
  private int mAnimationType = NONE;
  /** 是否运行截屏 **/
  private boolean isCanScreenshot = true;
  /** 右滑关闭当前Activity顶层容器 **/
  protected SwipeBackLayout rootView;
  /** 共通操作 **/
  protected Operation mOperation = null;
  /** 日志输出标志 **/
  protected final String TAG = this.getClass().getSimpleName();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Log.d(TAG, "BaseActivity-->onCreate()");

    // 获取应用Application
    mApplication = (MApplication) getApplicationContext();

    // 设置渲染视图View
    int baseLayout = BaseView.gainResId(mApplication, BaseView.LAYOUT, "activity_base_container");
    mContextView = (ViewGroup) LayoutInflater.from(this).inflate(baseLayout, null);
    setContentView(mContextView);

    // 将当前Activity压入栈
    mContextWR = new WeakReference<Activity>(this);
    mApplication.pushTask(mContextWR);

    // 实例化共通操作
    mOperation = new Operation(this);

    // 初始化参数
    Bundle bundle = getIntent().getExtras();
    if (bundle != null) {
      mAnimationType = bundle.getInt(ANIMATION_TYPE, NONE);
    } else {
      bundle = new Bundle();
    }
    initParms(bundle);

    View mView = bindView();
    if (null == mView) {
      mView = LayoutInflater.from(this).inflate(bindLayout(), null);
    }
    ViewGroup mContent =
        (ViewGroup) findViewById(BaseView.gainResId(mApplication, BaseView.ID, "fl_content"));
    mContent.addView(mView);

    // 初始化控件
    initView(mContextView);

    // 业务操作
    doBusiness(this);

    // 显示VoerFlowMenu
    displayOverflowMenu(getContext());

    // 是否可以截屏
    if (!isCanScreenshot) {
      getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
    }
  }

  @Override
  public View bindView() {
    return null;
  }

  @Override
  protected void onRestart() {
    super.onRestart();
    Log.d(TAG, "BaseActivity-->onRestart()");
  }

  @Override
  protected void onStart() {
    super.onStart();
    Log.d(TAG, "BaseActivity-->onStart()");
  }

  @Override
  protected void onResume() {
    super.onResume();
    Log.d(TAG, "BaseActivity-->onResume()");
    resume();
  }

  @Override
  public void resume() {

  }

  @Override
  protected void onPause() {
    super.onPause();
    Log.d(TAG, "BaseActivity-->onPause()");
  }

  @Override
  protected void onStop() {
    super.onStop();
    Log.d(TAG, "BaseActivity-->onStop()");
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    Log.d(TAG, "BaseActivity-->onDestroy()");

    destroy();
    mApplication.removeTask(mContextWR);
  }

  @Override
  public void destroy() {

  }

  /**
   * 显示Actionbar菜单图标
   */
  @Override
  public boolean onMenuOpened(int featureId, Menu menu) {
    if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
      if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
        try {
          Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
          m.setAccessible(true);
          m.invoke(menu, true);// 显示
        } catch (Exception e) {
          Log.e(TAG, "onMenuOpened-->" + e.getMessage());
        }
      }
    }
    return super.onMenuOpened(featureId, menu);
  }

  /**
   * 显示OverFlowMenu按钮
   * 
   * @param mContext 上下文Context
   */
  public void displayOverflowMenu(Context mContext) {
    try {
      ViewConfiguration config = ViewConfiguration.get(mContext);
      Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
      if (menuKeyField != null) {
        menuKeyField.setAccessible(true);
        menuKeyField.setBoolean(config, false);// 显示
      }
    } catch (Exception e) {
      Log.e("ActionBar", e.getMessage());
    }
  }

  /**
   * 获取当前Activity
   * 
   * @return
   */
  protected Activity getContext() {
    if (null != mContextWR)
      return mContextWR.get();
    else
      return null;
  }

  /**
   * 获取共通操作机能
   */
  public Operation getOperation() {
    return this.mOperation;
  }

  /**
   * 设置是否可截屏
   * 
   * @param isShortCut
   */
  public void setCanScreenshot(boolean isCanScreenshot) {
    this.isCanScreenshot = isCanScreenshot;
  }

  /**
   * 隐藏标题栏
   */
  public void hiddeTitleBar() {
    // 标题栏容器
    View mTitleBarContainer =
        findViewById(BaseView.gainResId(mApplication, BaseView.ID, "ll_title"));
    if (null == mTitleBarContainer) {
      return;
    }
    mTitleBarContainer.setVisibility(View.GONE);
  }

  /**
   * 设置标题栏背景颜色
   * 
   * @param strColor 背景颜色，如：#FFFFFF
   */
  public void setTitleBarBgColor(String strColor) {
    if (TextUtils.isEmpty(strColor)) return;
    setTitleBarBgColor(Color.parseColor(strColor));
  }

  /**
   * 设置标题栏背景颜色
   * 
   * @param mResId 背景资源文件-->mContext.getResources().getColor(R.color.actionbar_bg)
   */
  public void setTitleBarBgColor(int mResId) {
    View mTitleBarContainer =
        findViewById(BaseView.gainResId(mApplication, BaseView.ID, "ll_title"));
    mTitleBarContainer.setBackgroundColor(mResId);
  }

  /**
   * 设置标题栏背景图片
   * 
   * @param strResName 图片资源文件名称，如：actionbar_bg
   */
  public void setTitleBarBg(String strResName) {
    if (TextUtils.isEmpty(strResName)) return;
    setTitleBarBg(BaseView.gainResId(mApplication, BaseView.DRAWABLE, strResName));
  }

  /**
   * 设置标题栏背景图片
   * 
   * @param mResId 图片资源id，如：R.drawable.actionbar_bg
   */
  public void setTitleBarBg(int mResId) {
    View mTitleBarContainer =
        findViewById(BaseView.gainResId(mApplication, BaseView.ID, "ll_title"));
    mTitleBarContainer.setBackgroundResource(mResId);
  }

  /**
   * 初始化返回按钮+标题左对齐
   * 
   * @param strTitle 标题名称
   * @param mBtnClickListener Home/Menu按钮点击监听事件
   */
  public void initHomeMenuTitleBar(String strTitle, View.OnClickListener mBtnClickListener) {
    View mMenuBtn = findViewById(BaseView.gainResId(mApplication, BaseView.ID, "iv_menu"));
    mMenuBtn.setVisibility(View.VISIBLE);
    if (null != mBtnClickListener) {
      mMenuBtn.setOnClickListener(mBtnClickListener);
    }
    View mBackBtn = findViewById(BaseView.gainResId(mApplication, BaseView.ID, "iv_back"));
    mBackBtn.setVisibility(View.GONE);

    // 标题
    TextView mTitleText =
        (TextView) findViewById(BaseView.gainResId(mApplication, BaseView.ID, "tv_title"));
    mTitleText.setText(strTitle);
  }

  /**
   * 初始化返回按钮+标题左对齐
   * 
   * @param strTitle 标题名称
   */
  public void initBackTitleBar(String strTitle) {
    initBackTitleBar(strTitle, Gravity.LEFT | Gravity.CENTER_VERTICAL);
  }

  /**
   * 初始化返回按钮+指定标题文本对齐方式
   * 
   * @param strTitle 标题名称
   * @param mGravity 标题文本对其方式 Gravity.LEFT|Gravity.CENTER_VERTICAL
   */
  public void initBackTitleBar(String strTitle, int mGravity) {
    // 设置标题
    TextView mTitleText =
        (TextView) findViewById(BaseView.gainResId(mApplication, BaseView.ID, "tv_title"));
    mTitleText.setText(strTitle);
    mTitleText.setGravity(mGravity);

    // 设置点击事件
    View mBackBtn = findViewById(BaseView.gainResId(mApplication, BaseView.ID, "iv_back"));
    mBackBtn.setVisibility(View.VISIBLE);
    mBackBtn.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View v) {
        finish();
      }
    });
  }

  /**
   * 获取标题栏容器，可以自行控制左右按钮和布局
   * 
   * @return
   */
  public ViewGroup gainTitleBarVG() {
    return (ViewGroup) findViewById(BaseView.gainResId(mApplication, BaseView.ID, "ll_title"));
  }

  /**
   * 初始化标题栏右侧[完成/提交]按钮
   * 
   * @param strBtnText 按钮显示文本
   * @param mClickListener 点击监听事件
   */
  public void initRightDoneBtn(String strBtnText, View.OnClickListener mClickListener) {
    Button mDoneBtn =
        (Button) findViewById(BaseView.gainResId(mApplication, BaseView.ID, "btn_done"));
    mDoneBtn.setVisibility(View.VISIBLE);
    mDoneBtn.setText(strBtnText);
    if (null != mClickListener) {
      mDoneBtn.setOnClickListener(mClickListener);
    }
  }

  /**
   * 隐藏标题栏右侧[完成/提交]按钮
   */
  public void hiddenRightDoneBtn() {
    Button mDoneBtn =
        (Button) findViewById(BaseView.gainResId(mApplication, BaseView.ID, "btn_done"));
    mDoneBtn.setVisibility(View.GONE);
  }

  /**
   * Actionbar点击返回键关闭事件
   */
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        finish();
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
  }

  public void finish() {
    super.finish();
    switch (mAnimationType) {
      case IBaseActivity.LEFT_RIGHT:
        overridePendingTransition(0,
            BaseView.gainResId(mApplication, BaseView.ANIM, "base_slide_right_out"));
        break;
      case IBaseActivity.TOP_BOTTOM:
        overridePendingTransition(0,
            BaseView.gainResId(mApplication, BaseView.ANIM, "base_push_up_out"));
        break;
      case IBaseActivity.FADE_IN_OUT:
        overridePendingTransition(0,
            BaseView.gainResId(mApplication, BaseView.ANIM, "base_fade_out"));
        break;
      default:
        break;
    }
    mAnimationType = NONE;
  }
}
