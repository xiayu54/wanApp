package com.yuan.fastec.latte.net.Rx;

import android.content.Context;

import com.yuan.fastec.latte.net.RestClient;
import com.yuan.fastec.latte.net.RestCreator;
import com.yuan.fastec.latte.net.callback.IError;
import com.yuan.fastec.latte.net.callback.IFailure;
import com.yuan.fastec.latte.net.callback.IRequest;
import com.yuan.fastec.latte.net.callback.ISuccess;
import com.yuan.fastec.latte.ui.LoaderStyle;

import java.util.Map;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author XYuan
 * Version  1.0
 * Description
 *  Buidler 里面做一些传值的操作
 */
public class RxRestClientBuilder {

    private  String mUrl = null;
    private  static final Map<String, Object> PARAMS = RestCreator.getParams();

    private  RequestBody mBody = null;
    private LoaderStyle mLoaderStyle = null;
    private Context mContext = null;

    RxRestClientBuilder(){

    }

    public final RxRestClientBuilder url(String url){
        this.mUrl = url;
        return this;
    }

    public final RxRestClientBuilder params(WeakHashMap<String, Object> params){
        PARAMS.putAll(params);
        return this;
    }

    public final RxRestClientBuilder params(String key, Object value){
        PARAMS.put(key, value);
        return this;
    }

    public final RxRestClientBuilder raw(String raw){
        this.mBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), raw);
        return this;
    }

    public final RxRestClientBuilder loder(Context context, LoaderStyle loaderStyle){
        this.mContext = context;
        this.mLoaderStyle = loaderStyle;
        return this;
    }

    public final RxRestClientBuilder loder(Context context){
        this.mContext = context;
        this.mLoaderStyle =  LoaderStyle.BallClipRotatePulseIndicator;
        return this;
    }

    public final RxRestClient build(){
        return new RxRestClient(mUrl, PARAMS, mBody, mLoaderStyle, mContext);
    }
}
