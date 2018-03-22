package com.aono.mvpdemo;

/**
 * Created by Aono on 2018/3/22.
 */

class MainModel implements MainContract.Model{

	private final MainPresenter presenter;

	public MainModel(MainPresenter presenter) {
		this.presenter = presenter;
	}

}
