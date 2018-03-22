package com.aono.mvpdemo;

import android.os.Bundle;

import com.aono.baseui.base.BaseActivity;
import com.aono.baseui.base.BasePresenter;

public class MainActivity extends BaseActivity implements MainContract.View{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	protected BasePresenter createPresenter() {
		return new MainPresenter();
	}
}
