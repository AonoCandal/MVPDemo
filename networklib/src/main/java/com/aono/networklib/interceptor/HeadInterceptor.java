package com.aono.networklib.interceptor;

import android.content.Context;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by Aono on 2018/3/23.
 */

public class HeadInterceptor implements Interceptor {

	private final Context mContext;

	public HeadInterceptor(Context context) {
		this.mContext = context;
	}

	@Override
	public Response intercept(Chain chain) throws IOException {
		return null;
	}
}
