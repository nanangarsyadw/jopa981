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

package com.zftlive.android.library.zxing;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.util.Log;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.lang.reflect.Field;
import java.util.Hashtable;

/**
 * 二维码/条形码工具类
 *
 * @author 曾繁添
 * @version 1.0
 *
 */
public class QRCode {

  /**
   * 根据指定内容生成自定义宽高的二维码图片
   *
   * @param strContent 需要生成二维码的内容
   * @param width 二维码宽度
   * @param height 二维码高度
   * @throws WriterException 生成二维码异常
   */
  public static Bitmap makeQRImage(String strContent, int width, int height) throws WriterException {
    // 判断生成二维码数据合法性
    if (null == strContent || "".equals(strContent)) return null;

    // 二维码参数设置
    Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
    // 容错率L=7% M=15% Q=25% H=30% 容错率越高，越容易被快速扫描
    hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
    hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
    hints.put(EncodeHintType.MARGIN, 1);

    // 图像数据转换，使用了矩阵转换
    BitMatrix bitMatrix =
        new QRCodeWriter().encode(strContent, BarcodeFormat.QR_CODE, width, height, hints);
    int[] pixels = new int[width * height];
    // 按照二维码的算法，逐个生成二维码的图片，两个for循环是图片横列扫描的结果
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        if (bitMatrix.get(x, y))
          pixels[y * width + x] = 0xff000000;
        else
          pixels[y * width + x] = 0xffffffff;
      }
    }
    // 生成二维码图片的格式，使用ARGB_8888
    Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
    bitmap.setPixels(pixels, 0, width, 0, 0, width, height);

    return bitmap;
  }

  /**
   * 生成带Logo的二维码
   *
   * @param strContent 二维码内容
   * @param qrWH 二维码的宽高
   * @param mLogoBitmap Logo图片
   * @param logoWH LOGO的宽高(注意：LOGO的宽高最多只能是二维码图片宽高的0.2，否则生成的二维码无法扫描识别)
   * @return
   * @throws WriterException 生成二维码失败异常
   */
  public static Bitmap makeQRImageWithLogo(String strContent, int qrWH, Bitmap mLogoBitmap,
      int logoWH) throws WriterException {

    // 判断生成二维码数据合法性
    if (null == strContent || "".equals(strContent)) return null;

    // 缩放Logo图片
    Matrix logoMatrix = new Matrix();
    float sx = (float) 2 * logoWH / mLogoBitmap.getWidth();
    float sy = (float) 2 * logoWH / mLogoBitmap.getHeight();
    logoMatrix.setScale(sx, sy);
    mLogoBitmap =
        Bitmap.createBitmap(mLogoBitmap, 0, 0, mLogoBitmap.getWidth(), mLogoBitmap.getHeight(),
            logoMatrix, false);

    // 二维码参数设置
    Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
    // 容错率L=7% M=15% Q=25% H=30% 容错率越高，越容易被快速扫描
    hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
    hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
    hints.put(EncodeHintType.MARGIN, 1);

    QRCodeWriter write = new QRCodeWriter();
    try {
      Field menuKeyField = QRCodeWriter.class.getDeclaredField("QUIET_ZONE_SIZE");
      if (menuKeyField != null) {
        menuKeyField.setAccessible(true);
        menuKeyField.setInt(write, 1);// 控制的边框的宽度
      }
    } catch (Exception e) {
      Log.e("cretaeBitmap", "反射设置QRCodeWriter.QUIET_ZONE_SIZE失败，原因：" + e.getMessage());
    }
    BitMatrix matrix = write.encode(strContent, BarcodeFormat.QR_CODE, qrWH, qrWH, hints);
    // BitMatrix matrix = new MultiFormatWriter().encode(strContent,BarcodeFormat.QR_CODE, qrWH,
    // qrWH, hints);
    int width = matrix.getWidth();
    int height = matrix.getHeight();

    // 二维矩阵转为一维像素数组,也就是一直横着排了
    int halfW = width / 2;
    int halfH = height / 2;
    int[] pixels = new int[width * height];
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        if (x > halfW - logoWH && x < halfW + logoWH && y > halfH - logoWH && y < halfH + logoWH) {
          pixels[y * width + x] = mLogoBitmap.getPixel(x - halfW + logoWH, y - halfH + logoWH);
        } else {
          if (matrix.get(x, y)) {
            pixels[y * width + x] = 0xff000000;
          } else {
            // 无信息设置像素点为白色
            pixels[y * width + x] = 0xffffffff;
          }
        }
      }
    }
    Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
    // 通过像素数组生成bitmap
    bitmap.setPixels(pixels, 0, width, 0, 0, width, height);

    return bitmap;
  }

}
