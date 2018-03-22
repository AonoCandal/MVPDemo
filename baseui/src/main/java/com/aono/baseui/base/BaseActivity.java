package com.aono.baseui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public abstract class BaseActivity<V, P extends BasePresenter<V>> extends AppCompatActivity {

	protected P mPresenter;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mPresenter = createPresenter();
		mPresenter.attachView((V)this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mPresenter.detachView();
	}

	/**
	 * 用于创建不同的Presenter  在View中直接用mPresenter 获取实例
	 * @return
	 */
	protected abstract P createPresenter();
}
