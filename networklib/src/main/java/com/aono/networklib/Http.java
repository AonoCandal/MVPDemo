package com.aono.networklib;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.aono.networklib.interceptor.DynamicParamsInterceptor;
import com.aono.networklib.interceptor.HeadInterceptor;
import com.aono.networklib.interceptor.ParamsInterceptor;
import com.aono.networklib.utils.AppUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
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
     */
    private Http(Context context) {
        wrContext = new WeakReference<>(context);
    }

    /**
     * 获得单例对象
     */
    public static Http with(Context context) {
        if (mInstance == null) {
            synchronized (Http.class) {
                if (mInstance == null) {
                    mInstance = new Http(context);
                }
            }
        }
        return mInstance;
    }

    /**
     * 是否显示加载动画
     */
    public Http setShowLoadingDialog(boolean showLoadingDialog) {
        this.isShowLoadingDialog = showLoadingDialog;
        return mInstance;
    }

    /**
     * 设置被观察者
     */
    public Http setObservable(Observable observable) {
        this.mObservable = observable;
        return mInstance;
    }


    /**
     * 使用OKHTTP.builder和retrofit.builder创建builder
     */
    public static class Builder {

        private static final long DEFAULT_TIMEOUT = 3000;
        private String baseUrl;                                            //跟地址
        private boolean isAddSessionId;                                    //是否添加sessionId
        private HashMap<String, String> dynamicParams;                     //url动态参数
        private boolean isAddRegularParams;                                //url固定参数是否添加
        private Retrofit.Builder rfBuilder;                                //retrofit builder
        private OkHttpClient.Builder okBuilder;                            //ok builder
        private Converter.Factory converterFactory;                        //转换工场

        public Builder setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder addSessionId() {
            this.isAddSessionId = true;
            return this;
        }

        public Builder setDynamicParams(HashMap<String, String> dynamicParams) {
            this.dynamicParams = dynamicParams;
            return this;
        }

        public Builder addRegularParams() {
            this.isAddRegularParams = true;
            return this;
        }

        public Builder setConverterFactory(Converter.Factory converterFactory) {
            this.converterFactory = converterFactory;
            return this;
        }

        public <T> T build(Class<T> clazz) {
            rfBuilder = new Retrofit.Builder();
            okBuilder = new OkHttpClient.Builder()
                    .cache(new Cache(wrContext.get().getCacheDir(), 1024 * 1024 * 100))
                    .readTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                    .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS);

            if (!TextUtils.isEmpty(baseUrl)) {
                rfBuilder.baseUrl(baseUrl);
            } else {
                rfBuilder.baseUrl(AppUtils.getBaseUrl());
            }

            if (isAddSessionId) {// 添加sessionId
                okBuilder.addInterceptor(new HeadInterceptor(wrContext.get()));
            }

            if (dynamicParams != null) {// url动态参数
                okBuilder.addInterceptor(new DynamicParamsInterceptor(dynamicParams));
            }

            if (isAddRegularParams) {// url固定参数
                okBuilder.addInterceptor(new ParamsInterceptor());
            }

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

            /********************retrofit*********************/

            return rfBuilder
                    .addConverterFactory(converterFactory != null
                            ? converterFactory
                            : GsonConverterFactory.create(
                            new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
                                    .serializeNulls().create()))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(okBuilder.build())
                    .build()
                    .create(clazz);
        }
    }

}
