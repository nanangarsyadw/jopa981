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

package com.zftlive.android.library.widget.progressbar;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ProgressBar;

/**
 * 垂直进度条
 * @author 曾繁添
 *
 */
public class VerticalProgressBar extends ProgressBar
{

	public VerticalProgressBar(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}

	public VerticalProgressBar(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public VerticalProgressBar(Context context)
	{
		super(context);
	}

	@Override
	protected synchronized void onDraw(Canvas canvas)
	{
		//反转90度，将水平ProgressBar竖起来
		canvas.rotate(-90);
		
		//将经过旋转后得到的VerticalProgressBar移到正确的位置
		canvas.translate(-getHeight(), 0);
		
		super.onDraw(canvas);
	}

	@Override
	protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(heightMeasureSpec, widthMeasureSpec);
		//互换宽高值
		setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		//互换宽高值
		super.onSizeChanged(h, w, oldw, oldh);
	}
}
