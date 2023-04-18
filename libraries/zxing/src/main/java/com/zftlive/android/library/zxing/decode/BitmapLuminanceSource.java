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

package com.zftlive.android.library.zxing.decode;

import android.graphics.Bitmap;

import com.google.zxing.LuminanceSource;

public class BitmapLuminanceSource extends LuminanceSource {

	private byte bitmapPixels[];

	protected BitmapLuminanceSource(Bitmap bitmap) {
		super(bitmap.getWidth(), bitmap.getHeight());

		// 首先，要取得该图片的像素数组内容
		int[] data = new int[bitmap.getWidth() * bitmap.getHeight()];
		this.bitmapPixels = new byte[bitmap.getWidth() * bitmap.getHeight()];
		bitmap.getPixels(data, 0, getWidth(), 0, 0, getWidth(), getHeight());

		// 将int数组转换为byte数组，也就是取像素值中蓝色值部分作为辨析内容
		for (int i = 0; i < data.length; i++) {
			this.bitmapPixels[i] = (byte) data[i];
		}
	}

	@Override
	public byte[] getMatrix() {
		// 返回我们生成好的像素数据
		return bitmapPixels;
	}

	@Override
	public byte[] getRow(int y, byte[] row) {
		// 这里要得到指定行的像素数据
		System.arraycopy(bitmapPixels, y * getWidth(), row, 0, getWidth());
		return row;
	}
}