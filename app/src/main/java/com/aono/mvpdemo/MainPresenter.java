package com.aono.mvpdemo;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Aono on 2018/3/22.
 */

public class MainPresenter extends MainContract.Presenter {

    private final MainModel model;

    public MainPresenter() {
        this.model = new MainModel(this);
    }

    public void testMethod() {

        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                emitter.onNext(new Object());
            }
        }).map(new Function<Object, String>() {
            @Override
            public String apply(Object o) throws Exception {
                return o.toString();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {

                    }
                }).dispose();
    }

}
