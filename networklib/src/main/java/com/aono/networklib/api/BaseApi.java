package com.aono.networklib.api;

import com.aono.networklib.bean.ImiRequestBean;
import com.aono.networklib.fun.ApiExceptionFun;
import com.aono.networklib.fun.StringToJSONObjectFun;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Aono on 2018/3/23.
 */

public abstract class BaseApi {


	public static RequestBody toBody(Map<String, String> map){
		Gson gson = new Gson();
		ImiRequestBean bean = new ImiRequestBean();
		bean.setMap(map);
		bean.setTimeStamp(System.currentTimeMillis());
		return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), gson.toJson(bean));
	}

	public static RequestBody toBody(JSONObject jsonObject){
		return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
	}

	public static class ObservableBuilder{

		private Observable observable;
		private boolean apiException;
		private boolean toJsonObject;
		private boolean isWeb;
		private Scheduler subscribeScheduler;
		private Scheduler observerScheduler;

		public ObservableBuilder(Observable observable) {
			this.observable = observable;
		}

		public ObservableBuilder addApiException() {
			this.apiException = true;
			return this;
		}

		public ObservableBuilder addToJsonObject() {
			this.toJsonObject = true;
			return this;
		}

		public ObservableBuilder addWeb() {
			isWeb = true;
			return this;
		}

		public void setSubscribeScheduler(Scheduler subscribeScheduler) {
			this.subscribeScheduler = subscribeScheduler;
		}

		public void setObserverScheduler(Scheduler observerScheduler) {
			this.observerScheduler = observerScheduler;
		}

		public Observable build(){

			if (isWeb){
				observable = observable.map(new StringToJSONObjectFun());
			}

			if (apiException){
				observable = observable.flatMap(new ApiExceptionFun());
			}

			if (subscribeScheduler != null){
				observable = observable.subscribeOn(subscribeScheduler);
			}else {
				observable = observable.subscribeOn(Schedulers.io());
			}

			if (observerScheduler != null){
				observable = observable.observeOn(observerScheduler);
			}else {
				observable = observable.observeOn(AndroidSchedulers.mainThread());
			}

			return observable;
		}
	}

}
