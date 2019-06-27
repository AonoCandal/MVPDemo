package com.aono.networklib.provider;

import okhttp3.CookieJar;
import okhttp3.Interceptor;

/**
 * Created on 2019-06-27.
 * 包名:com.aono.networklib.provider
 *
 * @author 李舰舸 <jiange.li@56qq.com>
 */
public interface NetWorkProvider {

    /**
     * 配置 OKHttp 拦截器
     * @return 拦截器
     */
    Interceptor[] configInterceptors();

    /**
     * 配置 Cookie
     * @return Cookie
     */
    CookieJar configCookie();

    /**
     * 配置连接超时时间
     * @return
     */
    long configConnectTimeoutSets();

    /**
     * 配置读取超时时间
     * @return
     */
    long configReadTimeoutSets();

    /**
     * 配置写入超时时间
     * @return
     */
    long configWriteTimeoutSets();

    /**
     * 配置 log是否显示
     * @return
     */
    boolean configLogEnable();




}
