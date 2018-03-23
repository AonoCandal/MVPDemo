package com.aono.networklib.api;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Aono on 2018/3/23.
 */

public abstract class BaseApi {


	public static RequestBody toBody(Map map){
		Gson gson = new Gson();
		return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), gson.toJson(map));
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

		public ObservableBuilder addApiException(boolean apiException) {
			this.apiException = apiException;
			return this;
		}

		public ObservableBuilder addToJsonObject(boolean toJsonObject) {
			this.toJsonObject = toJsonObject;
			return this;
		}

		public ObservableBuilder setWeb(boolean web) {
			isWeb = web;
			return this;
		}

		public void setSubscribeScheduler(Scheduler subscribeScheduler) {
			this.subscribeScheduler = subscribeScheduler;
		}

		public void setObserverScheduler(Scheduler observerScheduler) {
			this.observerScheduler = observerScheduler;
		}

		public Observable build(){

//			if (isWeb){
//				observable = observable.map(new StringToJSONObjectFun<>());
//			}
			return observable;
		}
	}

}
