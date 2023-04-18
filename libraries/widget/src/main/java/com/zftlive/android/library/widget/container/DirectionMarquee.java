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

package com.zftlive.android.library.widget.container;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 自动滚动Item的LinearLayout
 * @author 曾繁添
 * @version 1.0
 *
 */
public class DirectionMarquee extends LinearLayout {

	private long intelval = 800;
	private long duration = 1000;
	private List<View> childViewList = new ArrayList<View>();
	private int startIndex = 0;
	private int endIndex = 1;
	private boolean cancel = false;
	private Handler mHandler = new Handler();
	private Direction mDirection = null;

	/**
	 * 跑马灯方向枚举
	 * 垂直方向：BUTTOM_TOP、TOP_BUTTOM
	 * 水平方向：LEFT_RIGHT、RIGHT_LEFT
	 */
    public enum Direction {
        BUTTOM_TOP, TOP_BUTTOM, LEFT_RIGHT, RIGHT_LEFT;
    }
	
	public DirectionMarquee(Context context) {
		this(context,null);
	}

	public DirectionMarquee(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@TargetApi(11)
	public DirectionMarquee(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
	}
	
	/**
	 * 停止线程，停止动画
	 */
	public void stopMarquee(){
		this.cancel = true;
	}
	
	/**
	 * 设置跑马灯方向
	 * 垂直方向：BUTTOM_TOP、TOP_BUTTOM
	 * 水平方向：LEFT_RIGHT、RIGHT_LEFT
	 * @param mDirection
	 */
	public void setDirection(Direction mDirection){
		this.mDirection = mDirection;
	}
	
	/**
	 * 设置滚动频率
	 * @param intevalMS
	 */
	public void setInteval(long intevalMS){
		this.intelval = intevalMS;
	}
	
	public void startMarquee(){
		//初始化
		init();
		//发送消息
		mHandler.postDelayed(new Runnable() {
			long time = intelval;
			@Override
			public void run() {
				//清除动画，删除View
				int iCount = getChildCount();
				for (int i = 0; i < iCount; i++) {
					View mView = getChildAt(i);
					clearAnimation(mView);
				}
				removeAllViews();
				//重新添加动画
				View showView = childViewList.get(startIndex);
				View hiddenView = childViewList.get(endIndex);
				ViewGroup mShowViewParent = (ViewGroup) showView.getParent();
				if(null != mShowViewParent){
					mShowViewParent.removeView(showView);
				}
				addView(showView);
				ViewGroup mHidenViewParent = (ViewGroup) hiddenView.getParent();
				if(null != mHidenViewParent){
					mHidenViewParent.removeView(hiddenView);
				}
				addView(hiddenView);
				switch (showView.getVisibility()) {
				case View.VISIBLE:
					hiddenAnimation(showView);
					showAnimation(hiddenView);
					break;
				case View.GONE:
					showAnimation(showView);
					hiddenAnimation(hiddenView);
					break;
				default:
					break;
				}

				//计算好当前View索引
				startIndex++;
				if (startIndex == (childViewList.size()-1)){
					endIndex = 0;
					postMessage(this, time);
					return;
				}
				if(startIndex > (childViewList.size()-1)){
					startIndex = 0;
				}
				endIndex = startIndex + 1;
				postMessage(this, time);
			}
		}, intelval);
	}

	private void postMessage(Runnable run,long time){
		if(!cancel){
			mHandler.postDelayed(run, time);
		}
	}
	
	private void init(){
		
		//初始化跑马灯方向
		if(null == mDirection){
			switch (getOrientation()) {
			case HORIZONTAL:
				mDirection = Direction.RIGHT_LEFT;
				break;
			case VERTICAL:
				mDirection = Direction.BUTTOM_TOP;
				break;
			default:
				break;
			}
		}
		int iConut = getChildCount();
		for (int i = 0; i < iConut; i++) {
			childViewList.add(getChildAt(i));
		}
		//添加第一个View
		removeAllViews();
		addView(childViewList.get(0));
	}
	
	/**
	 * X轴方向无变化，Y轴方向变化显示(从底部向上显示)
	 * @param mView
	 */
	private void showAnimation(View mView) {
		TranslateAnimation mShowAction = null;
		//水平方向
		if(HORIZONTAL == getOrientation()){
			float formX = 1.0f;
			float toX = 0.0f;
			switch (mDirection) {
			//从左往右
			case LEFT_RIGHT:
				formX = 0.0f;
				toX = +1.0f;
				break;
			//从右往左
			case RIGHT_LEFT:
				formX = +1.0f;
				toX = 0.0f;
				break;
			default:
				break;
			}
			mShowAction = new TranslateAnimation(
					Animation.RELATIVE_TO_SELF, formX, //fromX 
					Animation.RELATIVE_TO_SELF, toX, //toX
					Animation.RELATIVE_TO_SELF, 0.0f,//fromY
					Animation.RELATIVE_TO_SELF, 0.0f  //toY
				);
		}else{
		//垂直方向，X不动
			float fromY = +1.0f;
			float toY = 0.0f;
			switch (mDirection) {
			//从下往上（默认）
			case BUTTOM_TOP:
				fromY = +1.0f;
				toY = 0.0f;
				break;
			//从上往下
			case TOP_BUTTOM:
				fromY = 0.0f;
				toY = +1.0f;
				break;
			default:
				break;
			}
			mShowAction = new TranslateAnimation(
					Animation.RELATIVE_TO_SELF, 0.0f, //fromX 
					Animation.RELATIVE_TO_SELF, 0.0f, //toX
					Animation.RELATIVE_TO_SELF, fromY,//fromY
					Animation.RELATIVE_TO_SELF, toY  //toY
				);
		}
		mShowAction.setDuration(duration);
		mView.startAnimation(mShowAction);
		mView.setVisibility(View.VISIBLE);
	}

	/**
	 * X轴方向无变化，Y轴方向变化隐藏（从屏幕向上退出）
	 * @param mView
	 */
	private void hiddenAnimation(View mView) {
		TranslateAnimation mHiddenAction = null;
		if(HORIZONTAL == getOrientation()){
			float formX = 0.0f;
			float toX = -1.0f;
			switch (mDirection) {
			//从左往右
			case LEFT_RIGHT:
				formX = -1.0f;
				toX = 0.0f;
				break;
			//从右往左
			case RIGHT_LEFT:
				formX = 0.0f;
				toX = -1.0f;
				break;
			default:
				break;
			}
			mHiddenAction = new TranslateAnimation(
					Animation.RELATIVE_TO_SELF, formX, //fromX 
					Animation.RELATIVE_TO_SELF, toX, //toX
					Animation.RELATIVE_TO_SELF, 0.0f, //fromY
					Animation.RELATIVE_TO_SELF, 0.0f //toY
				 );
		}else{
			//垂直方向，X不动
			float fromY = 0.0f;
			float toY = -1.0f;
			switch (mDirection) {
			//从下往上
			case BUTTOM_TOP:
				fromY = 0.0f;
				toY = -1.0f;
				break;
			//从上往下
			case TOP_BUTTOM:
				fromY = -1.0f;
				toY = 0.0f;
				break;
			default:
				break;
			}
			mHiddenAction = new TranslateAnimation(
					Animation.RELATIVE_TO_SELF, 0.0f, //fromX 
					Animation.RELATIVE_TO_SELF, 0.0f, //toX
					Animation.RELATIVE_TO_SELF, fromY, //fromY
					Animation.RELATIVE_TO_SELF, toY //toY
				 );
		}

		mHiddenAction.setDuration(duration);
		mView.startAnimation(mHiddenAction);
		mView.setVisibility(View.GONE);
	}
	
	/**
	 * 移除动画
	 * @param mView
	 */
	private void clearAnimation(View mView){
		mView.clearAnimation();
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}
}
