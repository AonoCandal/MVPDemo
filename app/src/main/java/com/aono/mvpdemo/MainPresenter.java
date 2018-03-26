package com.aono.mvpdemo;

/**
 * Created by Aono on 2018/3/22.
 */

public class MainPresenter extends MainContract.Presenter {

	private final MainModel model;

	public MainPresenter(){
		this.model = new MainModel(this);
	}

	public void testMethod(){
//		Http.with((Context) obtainView())
//				.setObservable(MobileApi.response(new HashMap<String, String>(), 1))
//				.setObservable();
	}

}
