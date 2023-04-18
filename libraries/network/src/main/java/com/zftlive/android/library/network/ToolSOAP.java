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

package com.zftlive.android.library.network;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.os.Handler;
import android.os.Message;

/**
 * 访问WebService的工具类,
 * 
 * @author 曾繁添
 * @version 1.0
 * 
 */
public class ToolSOAP {
	
	// 含有3个线程的线程池
	private static final ExecutorService executorService = Executors.newFixedThreadPool(3);

	/**
	 * 
	 * @param url WebService服务器地址
	 * @param namespace 命名空间
	 * @param methodName WebService的调用方法名
	 * @param properties WebService的参数
	 * @param webServiceCallBack 返回结果回调接口
	 */
	public static void callService(String url,final String namespace,final String methodName,HashMap<String,String> properties,final WebServiceCallBack webServiceCallBack) {
		// 创建HttpTransportSE对象，传递WebService服务器地址
		final HttpTransportSE httpTransportSE = new HttpTransportSE(url);
		// 创建SoapObject对象
		SoapObject soapObject = new SoapObject(namespace, methodName);
		
		// SoapObject添加参数
		if (properties != null) {
			for (Iterator<Map.Entry<String, String>> it = properties.entrySet().iterator(); it.hasNext();) {
				Map.Entry<String, String> entry = it.next();
				soapObject.addProperty(entry.getKey(), entry.getValue());
			}
		}

		// 实例化SoapSerializationEnvelope，传入WebService的SOAP协议的版本号
		final SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		// 设置是否调用的是.Net开发的WebService
		soapEnvelope.setOutputSoapObject(soapObject);
		soapEnvelope.dotNet = true;
		httpTransportSE.debug = true;

		// 用于子线程与主线程通信的Handler
		final Handler mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				if(msg.what == 0){
					webServiceCallBack.onSucced((SoapObject) msg.obj);
				}else{
					webServiceCallBack.onFailure((String)msg.obj);
				}
			}
		};

		// 开启线程去访问WebService
		executorService.submit(new Runnable() {

			@Override
			public void run() {
				SoapObject resultSoapObject = null;
				Message mgs = mHandler.obtainMessage();
				try {
					
					httpTransportSE.call(namespace + methodName, soapEnvelope);
					if (soapEnvelope.getResponse() != null) {
						// 获取服务器响应返回的SoapObject
						resultSoapObject = (SoapObject) soapEnvelope.bodyIn;
					}
					mgs.what = 0;
					mgs.obj = resultSoapObject;
					
				} catch (Exception e) {
					mgs.what = 1;
					mgs.obj = e.getMessage();
				} 
				
				// 将获取的消息利用Handler发送到主线程
				mHandler.sendMessage(mgs);
			}
		});
	}

	/**
	 * WebService回调接口
	 *
	 */
	public interface WebServiceCallBack {
		
		public void onSucced(SoapObject result);
		
		public void onFailure(String result);
	}

}