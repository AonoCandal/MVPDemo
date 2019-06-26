package com.aono.networklib;

import android.content.Context;
import android.util.Log;

import com.aono.networklib.utils.AppUtils;
import com.google.gson.GsonBuilder;

import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Aono on 2018/3/23.
 */

public class HttpManager {

    private volatile static HttpManager mInstance;

    /*弱引用. 防止内存泄漏*/
    private WeakReference<Context> wrContext;
    private static final long DEFAULT_TIMEOUT = 3000;
    private String baseUrl;                                            //根地址
    private boolean isAddSessionId;                                    //是否添加sessionId
    private HashMap<String, String> dynamicParams;                     //url动态参数
    private boolean isAddRegularParams;                                //url固定参数是否添加
    private Converter.Factory converterFactory;                        //转换工场
    private Retrofit.Builder rfBuilder;                                //retrofit builder
    private OkHttpClient.Builder okBuilder;                            //ok builder

    /**
     * 私有化构造函数
     */
    private HttpManager() {}

    /**
     * 获得单例对象
     */
    public static HttpManager getInstance() {
        if (mInstance == null) {
            synchronized (HttpManager.class) {
                if (mInstance == null) {
                    mInstance = new HttpManager();
                }
            }
        }
        return mInstance;
    }

    public void init(Context context) {
        wrContext = new WeakReference<>(context);
        rfBuilder = new Retrofit.Builder()
                .addConverterFactory(converterFactory != null ? converterFactory
                        : GsonConverterFactory.create(new GsonBuilder().setDateFormat(
                                "yyyy-MM-dd HH:mm:ss").serializeNulls().create()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okBuilder.build());

        okBuilder = new OkHttpClient.Builder()
                .cache(new Cache(wrContext.get().getCacheDir(), 1024 * 1024 * 100))
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS);

        //log要放在最后面打印
        if (AppUtils.isDebugAble()) {
            okBuilder.addInterceptor(new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    try {
                        Log.i("OKHttp", "OKHttp-- " + URLEncoder.encode(message, "utf-8"));
                    } catch (UnsupportedEncodingException e) {
                        Log.i("OKHttp", "OKHttp-- " + message);
                    }
                }
            }).setLevel(HttpLoggingInterceptor.Level.BODY));
        }
    }

    public <T> T createRequest(Class<T> clazz) {
        return rfBuilder.build().create(clazz);
    }

}
