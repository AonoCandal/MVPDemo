package com.aono.networklib;

import android.content.Context;
import android.text.TextUtils;

import com.aono.networklib.interceptor.DynamicParamsInterceptor;
import com.aono.networklib.interceptor.HeadInterceptor;
import com.aono.networklib.interceptor.LogInterceptor;
import com.aono.networklib.interceptor.ParamsInterceptor;
import com.aono.networklib.utils.AppUtils;

import java.lang.ref.WeakReference;
import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Aono on 2018/3/23.
 */

public class Http {

	private volatile static Http mInstance;

	/*弱引用. 防止内存泄漏*/
	private static WeakReference<Context> wrContext;
	/*判断是否加载loading对话框*/
	private boolean isShowLoadingDialog = false;
	/*被观察者*/
	private Observable mObservable;

	/**
	 * 私有化构造函数
	 * @param context
	 */
	private Http(Context context) {
		wrContext = new WeakReference<>(context);
	}

	/**
	 * 获得单例对象
	 * @return
	 */
	public static Http with(Context context){
		if (mInstance == null){
			synchronized (Http.class){
				if (mInstance == null){
					mInstance = new Http(context);
				}
			}
		}
		return mInstance;
	}

	/**
	 * 是否显示加载动画
	 * @param showLoadingDialog
	 * @return
	 */
	public Http setShowLoadingDialog(boolean showLoadingDialog){
		this.isShowLoadingDialog = showLoadingDialog;
		return mInstance;
	}

	/**
	 * 设置被观察者
	 * @param observable
	 * @return
	 */
	public Http setObservable(Observable observable){
		this.mObservable = observable;
		return mInstance;
	}


	/**
	 * 使用OKHTTP.builder和retrofit.builder创建NetWorkApi
	 */
	public static class NetWorkApiBuilder{

		private String baseUrl;											//跟地址
		private boolean isAddSessionId;									//是否添加sessionId
		private HashMap<String, String> dynamicParams;					//url动态参数
		private boolean isAddRegularParams;								//url固定参数是否添加
		private Retrofit.Builder rfBuilder;								//retrofit builder
		private OkHttpClient.Builder okBuilder;							//ok builder
		private Converter.Factory converterFactory;						//转换工场

		public NetWorkApiBuilder setBaseUrl(String baseUrl) {
			this.baseUrl = baseUrl;
			return this;
		}

		public NetWorkApiBuilder setAddSessionId(boolean addSessionId) {
			this.isAddSessionId = addSessionId;
			return this;
		}

		public NetWorkApiBuilder setDynamicParams(HashMap<String, String> dynamicParams) {
			this.dynamicParams = dynamicParams;
			return this;
		}

		public NetWorkApiBuilder setAddRegularParams(boolean addRegularParams) {
			this.isAddRegularParams = addRegularParams;
			return this;
		}

		public NetWorkApiBuilder setConverterFactory(Converter.Factory converterFactory) {
			this.converterFactory = converterFactory;
			return this;
		}

		public NetWorkApi build(){
			rfBuilder = new Retrofit.Builder();
			okBuilder = new OkHttpClient.Builder();

			if (!TextUtils.isEmpty(baseUrl)){
				rfBuilder.baseUrl(baseUrl);
			} else {
				rfBuilder.baseUrl(AppUtils.getBaseUrl());
			}

			if (isAddSessionId){// 添加sessionId
				okBuilder.addInterceptor(new HeadInterceptor(wrContext.get()));
			}

			if (dynamicParams != null){// url动态参数
				okBuilder.addInterceptor(new DynamicParamsInterceptor(dynamicParams));
			}

			if (isAddRegularParams){// url固定参数
				 okBuilder.addInterceptor(new ParamsInterceptor());
			}

			//log要放在最后面打印
			if (AppUtils.isDebugAble()){
				okBuilder.addInterceptor(new LogInterceptor());
			}

			if (converterFactory != null){
				rfBuilder.addConverterFactory(converterFactory);
			}else {
				rfBuilder.addConverterFactory(GsonConverterFactory.create());
			}

			return rfBuilder
					.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
					.client(okBuilder.build())
					.build()
					.create(NetWorkApi.class);
		}
	}

}
