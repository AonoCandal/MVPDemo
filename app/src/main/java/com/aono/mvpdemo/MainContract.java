package com.aono.mvpdemo;

import com.aono.baseui.base.BasePresenter;
import com.aono.baseui.base.BaseView;

/**
 * Created by Aono on 2018/3/22.
 */

public interface MainContract {

	interface View extends BaseView<Presenter> {

	}

	abstract class Presenter extends BasePresenter<View> {

	}

	interface Model {

	}

}
