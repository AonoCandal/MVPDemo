package com.aono.baseui.base;

import java.lang.ref.WeakReference;

/**
 * Created by Aono on 2018/3/22.
 *
 * V : IView
 */
public abstract class BasePresenter<V> {

	/**
	 * 弱引用  用来保存View对象
	 */
	private WeakReference<V> weakReference;

	/**
	 * 连接View
	 * @param view
	 */
	protected void attachView(V view){
		weakReference = new WeakReference<>(view);
	}

	/**
	 * 注销View
	 */
	protected void detachView(){
		if (isAttatchView()){
			weakReference.clear();
			weakReference = null;
		}
	}

	/**
	 * 判断是否连接了View
	 * @return
	 */
	private boolean isAttatchView() {
		return weakReference!=null && weakReference.get()!=null;
	}

	/**
	 * 获取View
	 * @return
	 */
	public V obtainView(){
		return isAttatchView() ? weakReference.get() : null;
	}

}
