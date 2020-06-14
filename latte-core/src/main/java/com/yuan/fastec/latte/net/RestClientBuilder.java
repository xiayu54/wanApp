package com.yuan.fastec.latte.net;

import android.content.Context;

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
public class RestClientBuilder {

    private  String mUrl = null;
    private  static final Map<String, Object> PARAMS = RestCreator.getParams();
    private  IRequest mRequest = null;
    private  ISuccess mSuccess = null;
    private  IFailure mFailure = null;
    private  IError mError = null;
    private  RequestBody mBody = null;
    private LoaderStyle mLoaderStyle = null;
    private Context mContext = null;

    RestClientBuilder(){

    }

    public final RestClientBuilder url(String url){
        this.mUrl = url;
        return this;
    }

    public final RestClientBuilder params(WeakHashMap<String, Object> params){
        PARAMS.putAll(params);
        return this;
    }

    public final RestClientBuilder params(String key, Object value){
        PARAMS.put(key, value);
        return this;
    }

    public final RestClientBuilder onRequest(IRequest iRequest){
        this.mRequest = iRequest;
        return this;
    }

    public final RestClientBuilder raw(String raw){
        this.mBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), raw);
        return this;
    }

    public final RestClientBuilder success(ISuccess iSuccess){
        this.mSuccess = iSuccess;
        return this;
    }

    public final RestClientBuilder failure(IFailure iFailure){
        this.mFailure = iFailure;
        return this;
    }

    public final RestClientBuilder error(IError iError){
        this.mError = iError;
        return this;
    }

    public final RestClientBuilder loder(Context context, LoaderStyle loaderStyle){
        this.mContext = context;
        this.mLoaderStyle = loaderStyle;
        return this;
    }

    public final RestClientBuilder loder(Context context){
        this.mContext = context;
        this.mLoaderStyle =  LoaderStyle.BallClipRotatePulseIndicator;
        return this;
    }

    public final RestClient build(){
        return new RestClient(mUrl, PARAMS, mRequest, mSuccess, mFailure, mError, mBody, mLoaderStyle, mContext);
    }
}
