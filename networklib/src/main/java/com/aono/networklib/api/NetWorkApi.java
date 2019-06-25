package com.aono.networklib.api;

import com.aono.networklib.bean.ResponseInfo;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Aono on 2018/3/23.
 */

public interface NetWorkApi {

	@POST("open/open.do")
	Observable<ResponseInfo<Object>> response(@Query("ACID") int acid, @Body RequestBody entery);

	@GET("")
	Call<ResponseInfo<Object>> getCall();


}
