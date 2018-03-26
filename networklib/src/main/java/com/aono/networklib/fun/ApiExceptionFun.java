package com.aono.networklib.fun;

import com.aono.networklib.bean.ApiException;
import com.aono.networklib.bean.ResponseInfo;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Created by Aono on 2018/3/26.
 *
 * 异常解析  返回200的函数
 */

public class ApiExceptionFun<T> implements Function<ResponseInfo<T>, Observable<T>> {

	@Override
	public Observable<T> apply(@NonNull ResponseInfo<T> info) throws Exception {

		if (info.getCode() != 200){//非正常返回
			return Observable.error(new ApiException(info.getMsg(), info.getCode(), info.getResponseStamp()));
		}
		return Observable.just(info.getData());
	}
}
