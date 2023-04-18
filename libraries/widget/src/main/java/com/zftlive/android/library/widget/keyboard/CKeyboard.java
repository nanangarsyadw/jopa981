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

package com.zftlive.android.library.widget.keyboard;

import java.util.List;

import com.zftlive.android.library.widget.R;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.Keyboard.Key;
import android.inputmethodservice.KeyboardView;
import android.util.AttributeSet;

public class CKeyboard extends KeyboardView {
	
	private Drawable mKeybgDrawable;
	private Drawable mOpKeybgDrawable;
	private Resources res;

	public CKeyboard(Context context, AttributeSet attrs) {
		super(context, attrs);
		initResources(context);
	}

	public CKeyboard(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initResources(context);
	}
	
	private void initResources(Context context){
		res = context.getResources();
		
		mKeybgDrawable =res.getDrawable(R.drawable.widget_selector_kb_key_btn);
		mOpKeybgDrawable = res.getDrawable(R.drawable.widget_selector_kb_opkey);
	}

	@Override
	public void onDraw(Canvas canvas) {
		List<Key> keys = getKeyboard().getKeys();
		for (Key key : keys) {
			canvas.save();

			int offsety = 0;
			if (key.y == 0) {
				offsety = 1;
			}
			
			
			int initdrawy = key.y + offsety;
			
			Rect rect =new Rect(key.x, initdrawy, key.x + key.width, key.y
					+ key.height);	
			
			canvas.clipRect(rect);

			int primaryCode = -1;
			if(null != key.codes && key.codes.length !=0){
				primaryCode = key.codes[0];
			}
			
			Drawable dr = null;
			if (primaryCode == -3 || key.codes[0] == -5) {
				dr = mOpKeybgDrawable;
			} else if(-1 != primaryCode){
				dr = mKeybgDrawable;
			}

			if(null != dr){
				int[] state = key.getCurrentDrawableState();
	
				dr.setState(state);
				dr.setBounds(rect);
				dr.draw(canvas);
			}

			Paint paint = new Paint();
			paint.setAntiAlias(true);
			paint.setTextAlign(Paint.Align.CENTER);
			paint.setTextSize(50);
			paint.setColor(res.getColor(android.R.color.black));

			if (key.label != null) {
				canvas.drawText(
						key.label.toString(),
						key.x + (key.width / 2),
						initdrawy + (key.height + paint.getTextSize() - paint.descent()) / 2,
						paint);
			} else if (key.icon != null) {
				int intriWidth = key.icon.getIntrinsicWidth();
				int intriHeight = key.icon.getIntrinsicHeight();
				
				final int drawableX = key.x + (key.width - intriWidth) / 2;
				final int drawableY = initdrawy +(key.height - intriHeight) / 2;
				
				key.icon.setBounds(
						drawableX,drawableY,drawableX + intriWidth,
						drawableY + intriHeight);
				
				key.icon.draw(canvas);
			}

			canvas.restore();
		}
	}

}
