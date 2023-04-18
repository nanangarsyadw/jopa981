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

package com.zftlive.android.library.tools.pay.alipay;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;

/**
 * 移动支付宝钱包支付工具类
 * 
 * @author 曾繁添
 * @version 1.0
 * 
 */
public class AlipayHelper {

  private static final String ALGORITHM = "RSA";
  private static final String SIGN_ALGORITHMS = "SHA1WithRSA";
  private static final String DEFAULT_CHARSET = "UTF-8";

  /**
   * RAS算法加密订单数据
   * 
   * @param data 需加密的数据
   * @param privateKey 私钥字符（商户私钥,pkcs8格式)
   * @return
   */
  public static String sign(String data, String privateKey) {
    try {
      PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decode(privateKey));
      KeyFactory keyf = KeyFactory.getInstance(ALGORITHM);
      PrivateKey priKey = keyf.generatePrivate(priPKCS8);

      Signature signature = Signature.getInstance(SIGN_ALGORITHMS);

      signature.initSign(priKey);
      signature.update(data.getBytes(DEFAULT_CHARSET));

      byte[] signed = signature.sign();

      return Base64.encode(signed);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }

}
