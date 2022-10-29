package com.zftlive.android.library.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

/**
 * Listview下拉放大效果 ,初始化时需调用方法setHeadView
 * @author 曾繁添
 * @version 1.0
 *
 */
public class PullZoomListView extends ListView {

	private final static int BACK_SCALE = 0;
	private float scaleY = 0;
	private boolean isBacking = false;// 是否处在回弹状态
	/** 用于记录拖拉图片移动的坐标位置 */
	private Matrix matrix = new Matrix();
	/** 用于记录图片要进行拖拉时候的坐标位置 */
	private Matrix currentMatrix = new Matrix();
	private Matrix defaultMatrix = new Matrix();
	/** 图片宽高 **/
	private float imgHeight, imgWidth;
	/** 记录是拖拉照片模式还是放大缩小照片模式 0:拖拉模式，1：放大 */
	private int MODE_NORMAL = 0;
	/** 拖拉照片模式 */
	private final int MODE_DRAG = 1;
	/** 用于记录开始时候的坐标位置 */
	private PointF startPoint = new PointF();
	
	private ImageView mHeadImage;
	private Bitmap mHeadImageBmp;

	public PullZoomListView(Context context) {
		this(context, null);
	}

	public PullZoomListView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public PullZoomListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	/**
	 * 设置头部View，并指定缩放目标Imageview
	 * @param mHeadView Listview头部view
	 * @param zoomImageView 需要缩放的Imageview
	 */
	public void setHeadView(View mHeadView,ImageView zoomImageView){
		super.addHeaderView(mHeadView);
		
		//头部背景图片
		this.mHeadImage = zoomImageView;
		mHeadImageBmp = drawableToBitmap(mHeadImage.getDrawable());
		float scale = (float) getScreenWidth() / (float) mHeadImageBmp.getWidth();
		matrix.postScale(scale, scale, 0, 0);
		mHeadImage.setImageMatrix(matrix);
		defaultMatrix.set(matrix);
		
		imgHeight = scale * mHeadImageBmp.getHeight();
		imgWidth = scale * mHeadImageBmp.getWidth();
		
		RelativeLayout.LayoutParams relativeLayout = new RelativeLayout.LayoutParams((int) imgWidth, (int) imgHeight);
		mHeadImage.setLayoutParams(relativeLayout);
	}
	
    /**
     * Drawable转成Bitmap 
     * @param drawable
     * @return
     */
    public Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else if (drawable instanceof NinePatchDrawable) {
            Bitmap bitmap = Bitmap
                    .createBitmap(
                            drawable.getIntrinsicWidth(),
                            drawable.getIntrinsicHeight(),
                            drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                                    : Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight());
            drawable.draw(canvas);
            return bitmap;
        } else {
            return null;
        }
    }

	/**
	 * 获取屏幕宽度
	 * 
	 * @return
	 */
	private int getScreenWidth() {
		DisplayMetrics displayMetrics = getContext().getResources()
				.getDisplayMetrics();
		return displayMetrics.widthPixels;
	}
	
	/**
	 * 慢慢回弹初始状态
	 */
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case BACK_SCALE:
				float scale = (scaleY / 2 + imgHeight) / (imgHeight);
				if (scaleY > 0) {
					isBacking = true;
					matrix.set(currentMatrix);
					RelativeLayout.LayoutParams relativeLayout = new RelativeLayout.LayoutParams(
							(int) (scale * imgWidth), (int) (scale * imgHeight));
					mHeadImage.setLayoutParams(relativeLayout);
					matrix.postScale(scale, scale, imgWidth / 2, 0);
					mHeadImage.setImageMatrix(matrix);
					scaleY = (float) (scaleY / 2 - 1);
					mHandler.sendEmptyMessageDelayed(BACK_SCALE, 20);
				} else {
					scaleY = 0;
					RelativeLayout.LayoutParams relativeLayout = new RelativeLayout.LayoutParams(
							(int) imgWidth, (int) imgHeight);
					mHeadImage.setLayoutParams(relativeLayout);
					matrix.set(defaultMatrix);
					mHeadImage.setImageMatrix(matrix);
					isBacking = false;
				}
				break;
			default:
				break;
			}
			super.handleMessage(msg);
		}

	};
	
	/**
	 * 向下滑动让图片变大
	 * 
	 * @param event
	 * @return
	 */
	@SuppressLint("ClickableViewAccessibility")
	public boolean onTouchEvent(MotionEvent event) {

		// 无Header
		if (getHeaderViewsCount() == 0) {
			return super.onTouchEvent(event);
		}

		/**
		 * 使用switch (event.getAction())可以处理ACTION_DOWN和ACTION_UP事件；
		 * 使用switch (event.getAction() & MotionEvent.ACTION_MASK)就可以处理处理多点触摸的ACTION_POINTER_DOWN和ACTION_POINTER_UP事件
		 */
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		// 手指压下屏幕
		case MotionEvent.ACTION_DOWN:
			if (isBacking) {
				return super.onTouchEvent(event);
			}
			int[] location = new int[2];
			mHeadImage.getLocationInWindow(location);
			if (location[1] >= 0) {
				MODE_NORMAL = MODE_DRAG;
				// 记录ImageView当前的移动位置
				currentMatrix.set(mHeadImage.getImageMatrix());
				startPoint.set(event.getX(), event.getY());
			}
			break;
		// 手指在屏幕上移动，改事件会被不断触发
		case MotionEvent.ACTION_MOVE:
			// 拖拉图片
			if (MODE_NORMAL == MODE_DRAG) {
				float dy = event.getY() - startPoint.y; // 得到y轴的移动距离
				// 在没有移动之前的位置上进行移动
				if (dy / 2 + imgHeight <= 1.5 * imgHeight) {
					matrix.set(currentMatrix);
					float scale = (dy / 2 + imgHeight) / (imgHeight);// 得到缩放倍数
					if (dy > 0) {
						scaleY = dy;
						RelativeLayout.LayoutParams relativeLayout = new RelativeLayout.LayoutParams(
								(int) (scale * imgWidth),
								(int) (scale * imgHeight));
						mHeadImage.setLayoutParams(relativeLayout);
						matrix.postScale(scale, scale, imgWidth / 2, 0);
						mHeadImage.setImageMatrix(matrix);
					}
				}
			}
			break;
		// 手指离开屏幕
		case MotionEvent.ACTION_UP:
			// 当触点离开屏幕，图片还原
			mHandler.sendEmptyMessage(BACK_SCALE);
		
		//一个非主要的手指抬起
		case MotionEvent.ACTION_POINTER_UP:
			MODE_NORMAL = 0;
			break;
		}

		return super.onTouchEvent(event);
	}
}
