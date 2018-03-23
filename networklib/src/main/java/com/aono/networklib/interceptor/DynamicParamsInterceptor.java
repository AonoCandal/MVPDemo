package com.aono.networklib.interceptor;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by Aono on 2018/3/23.
 */

public class DynamicParamsInterceptor implements Interceptor {


	public DynamicParamsInterceptor(HashMap<String, String> dynamicParams) {

	}

	@Override
	public Response intercept(Chain chain) throws IOException {
		return null;
	}
}
