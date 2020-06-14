package com.yuan.fastec.example;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.yuan.fastec.latte.delegates.LatteDelegate;
import com.yuan.fastec.latte.net.RestClient;
import com.yuan.fastec.latte.net.RestCreator;
import com.yuan.fastec.latte.net.Rx.RxRestClient;
import com.yuan.fastec.latte.net.callback.IError;
import com.yuan.fastec.latte.net.callback.IFailure;
import com.yuan.fastec.latte.net.callback.ISuccess;
import com.yuan.fastec.latte.ui.LoaderStyle;

import java.util.WeakHashMap;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * @author XYuan
 * Version  1.0
 * Description
 */
public class ExampleDelegate extends LatteDelegate {
    @Override
    public Object setLayout() {
        return R.layout.delegate_example;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
//        testRxRestClient2();
    }

    private void testRestClient(){
        RestClient.Builder()
                .url("/")
//                .loder(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {

                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {

                    }
                })
                .build().get();
    }

    private void testRxRestClient(){
        final  String url = "/";
        final WeakHashMap<String, Object> params = new WeakHashMap<>();
        Observable<String> observable = RestCreator.getRxRestService().get(url, params);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        Toast.makeText(getContext(), "RX--------" +s, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void testRxRestClient2(){
        final  String url = "/";
        RxRestClient.Builder()
                .url(url)
                .build()
                .get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        Toast.makeText(getContext(), "RX2--------" +s, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
