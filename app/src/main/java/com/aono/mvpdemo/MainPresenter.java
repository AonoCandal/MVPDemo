package com.aono.mvpdemo;

/**
 * Created by Aono on 2018/3/22.
 */

public class MainPresenter extends MainContract.Presenter {

	private final MainModel model;

	public MainPresenter(){
		this.model = new MainModel(this);
	}

}
