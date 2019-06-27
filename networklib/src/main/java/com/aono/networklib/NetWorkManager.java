package com.aono.networklib;

import com.aono.networklib.provider.NetWorkProvider;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created on 2019-06-27.
 * 包名:com.aono.networklib
 *
 * @author 李舰舸 <jiange.li@56qq.com>
 */
public class NetWorkManager {

    private HashMap<String, Retrofit> mRetrofitMap = new HashMap<>();
    private HashMap<String, OkHttpClient> mClientMap = new HashMap<>();
    private HashMap<String, NetWorkProvider> mProviderMap = new HashMap<>();


    Retrofit getRetrofit(String baseUrl, NetWorkProvider provider) {

        if (mRetrofitMap.containsKey(baseUrl)) {
            return mRetrofitMap.get(baseUrl);
        }

        Gson gson = new GsonBuilder().setDateFormat(
                "yyyy-MM-dd HH:mm:ss").serializeNulls().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(getClient(baseUrl, provider))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        mRetrofitMap.put(baseUrl, retrofit);

        return retrofit;
    }

    OkHttpClient getClient(String baseUrl, NetWorkProvider provider) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        if (provider.configConnectTimeoutSets() != 0) {
            builder.connectTimeout(provider.configConnectTimeoutSets(), TimeUnit.MILLISECONDS);
        }

        if (provider.configReadTimeoutSets() != 0) {
            builder.connectTimeout(provider.configReadTimeoutSets(), TimeUnit.MILLISECONDS);
        }

        if (provider.configWriteTimeoutSets() != 0) {
            builder.connectTimeout(provider.configWriteTimeoutSets(), TimeUnit.MILLISECONDS);
        }

        return builder.build();
    }

}
