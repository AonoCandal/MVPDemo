package com.aono.networklib.api;

import com.aono.networklib.Http;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * Created by Aono on 2018/3/26.
 *
 * API 获取
 */

public class MobileApi extends BaseApi {


	private static NetWorkApi netWorkApi;
	private static Observable observable;

	public static NetWorkApi getNetWorkApi(){
		if (netWorkApi == null){
			netWorkApi = new Http.NetWorkApiBuilder()
					.addSessionId()
					.addRegularParams()
					.build();
		}
		return netWorkApi;
	}

	public static Observable getObservable(Observable observable){
		if (observable == null){
			observable = new ObservableBuilder(observable)
					.addApiException()
					.build();
		}
		return observable;
	}

	public static Observable response(Map<String, String> map, int protocolId){
		RequestBody requestBody = toBody(map);
		return getObservable(getNetWorkApi().response(protocolId, requestBody));
	}
}
