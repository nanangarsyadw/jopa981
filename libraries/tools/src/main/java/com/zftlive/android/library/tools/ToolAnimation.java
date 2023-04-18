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

package com.zftlive.android.library.tools;

import android.graphics.ColorMatrixColorFilter;  
import android.view.MotionEvent;  
import android.view.View;  
import android.view.View.OnTouchListener;  
import android.widget.ImageView;  
  
public class ToolAnimation extends ToolBase {
      
    /** 
     * 给试图添加点击效果,让背景变深 
     * */  
    public static void addTouchDrak(View view , boolean isClick){  
        view.setOnTouchListener( VIEW_TOUCH_DARK ) ;   
          
        if(!isClick){  
            view.setOnClickListener(new View.OnClickListener() {  
                  
                @Override  
                public void onClick(View v) {  
                }  
            });  
        }  
    }  
      
    /** 
     * 给试图添加点击效果,让背景变暗 
     * */  
    public static void addTouchLight(View view , boolean isClick){  
        view.setOnTouchListener( VIEW_TOUCH_LIGHT ) ;   
          
        if(!isClick){  
            view.setOnClickListener(new View.OnClickListener() {  
                  
                @Override  
                public void onClick(View v) {  
                }  
            });  
        }  
    }  
      
      
    /** 
     * 让控件点击时，颜色变深 
     * */  
    public static final OnTouchListener VIEW_TOUCH_DARK = new OnTouchListener() {  
  
        public final float[] BT_SELECTED = new float[] { 1, 0, 0, 0, -50, 0, 1,  
                0, 0, -50, 0, 0, 1, 0, -50, 0, 0, 0, 1, 0 };  
        public final float[] BT_NOT_SELECTED = new float[] { 1, 0, 0, 0, 0, 0,  
                1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0 };  
  
        @Override  
        public boolean onTouch(View v, MotionEvent event) {  
            if (event.getAction() == MotionEvent.ACTION_DOWN) {  
                if(v instanceof ImageView){  
                    ImageView iv = (ImageView) v;  
                    iv.setColorFilter( new ColorMatrixColorFilter(BT_SELECTED) ) ;   
                }else{  
                    v.getBackground().setColorFilter( new ColorMatrixColorFilter(BT_SELECTED) );  
                    v.setBackgroundDrawable(v.getBackground());  
                }  
            } else if (event.getAction() == MotionEvent.ACTION_UP) {  
                if(v instanceof ImageView){  
                    ImageView iv = (ImageView) v;  
                    iv.setColorFilter( new ColorMatrixColorFilter(BT_NOT_SELECTED) ) ;   
                }else{  
                    v.getBackground().setColorFilter(  
                            new ColorMatrixColorFilter(BT_NOT_SELECTED));  
                    v.setBackgroundDrawable(v.getBackground());  
                }  
            }  
            return false;  
        }  
    };  
      
    /** 
     * 让控件点击时，颜色变暗 
     * */  
    public static final OnTouchListener VIEW_TOUCH_LIGHT = new OnTouchListener(){  
  
        public final float[] BT_SELECTED = new float[] { 1, 0, 0, 0, 50, 0, 1,  
                0, 0, 50, 0, 0, 1, 0, 50, 0, 0, 0, 1, 0 };  
        public final float[] BT_NOT_SELECTED = new float[] { 1, 0, 0, 0, 0, 0,  
                1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0 };  
          
        @Override  
        public boolean onTouch(View v, MotionEvent event) {  
            if (event.getAction() == MotionEvent.ACTION_DOWN) {  
                if(v instanceof ImageView){  
                    ImageView iv = (ImageView) v;  
                    iv.setDrawingCacheEnabled(true);   
                      
                    iv.setColorFilter( new ColorMatrixColorFilter(BT_SELECTED) ) ;   
                }else{  
                    v.getBackground().setColorFilter( new ColorMatrixColorFilter(BT_SELECTED) );  
                    v.setBackgroundDrawable(v.getBackground());  
                }  
            } else if (event.getAction() == MotionEvent.ACTION_UP) {  
                if(v instanceof ImageView){  
                    ImageView iv = (ImageView) v;   
                    iv.setColorFilter( new ColorMatrixColorFilter(BT_NOT_SELECTED) ) ;   
                    System.out.println( "变回来" );   
                }else{  
                    v.getBackground().setColorFilter(  
                            new ColorMatrixColorFilter(BT_NOT_SELECTED));  
                    v.setBackgroundDrawable(v.getBackground());  
                }  
            }  
            return false;  
        }  
    };  
}  