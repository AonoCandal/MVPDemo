package com.aono.networklib.subscriber;


import android.content.Context;
import android.support.v4.app.DialogFragment;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * Created by Aono on 2018/3/26.
 */

public abstract class ApiSubscriber<T> implements Subscriber<T> {

	private Context mContext;
	private DialogFragment loadingDialog;
	private boolean isShowLoadingDialog;

	public void setShowLoadingDialog(boolean showLoadingDialog) {
		isShowLoadingDialog = showLoadingDialog;
	}

	@Override
	public void onSubscribe(Subscription s) {

	}

	@Override
	public void onError(Throwable t) {

	}

	@Override
	public void onComplete() {
		if (isShowLoadingDialog){
			dismissDialog();
		}
	}

	private void dismissDialog() {

	}
}
