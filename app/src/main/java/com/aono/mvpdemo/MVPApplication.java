package com.aono.mvpdemo;

import android.app.Application;

import com.aono.networklib.HttpManager;

/**
 * Created on 2019-06-26.
 * 包名:com.aono.mvpdemo
 *
 * @author 李舰舸 <jiange.li@56qq.com>
 */
public class MVPApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        HttpManager.getInstance().init(this);
    }
}
