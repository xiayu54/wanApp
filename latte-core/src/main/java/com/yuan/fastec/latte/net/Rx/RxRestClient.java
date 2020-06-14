package com.yuan.fastec.latte.net.Rx;

import android.content.Context;

import com.yuan.fastec.latte.net.HttpMethod;
import com.yuan.fastec.latte.net.RestClientBuilder;
import com.yuan.fastec.latte.net.RestCreator;
import com.yuan.fastec.latte.net.RestService;
import com.yuan.fastec.latte.net.callback.IError;
import com.yuan.fastec.latte.net.callback.IFailure;
import com.yuan.fastec.latte.net.callback.IRequest;
import com.yuan.fastec.latte.net.callback.ISuccess;
import com.yuan.fastec.latte.net.callback.RequestCallbacks;
import com.yuan.fastec.latte.ui.LatteLoader;
import com.yuan.fastec.latte.ui.LoaderStyle;

import java.util.Map;
import java.util.WeakHashMap;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * @author XYuan
 * Version  1.0
 * Description
 *  使用 Bilder 建造者模式
 */
public class RxRestClient {
    // 初始化变量
    private final String URL;
    private static final WeakHashMap<String, Object> PARAMS = RestCreator.getParams();

    private final RequestBody BODY;
    private final LoaderStyle LOADER_STYLE;
    private final Context CONTEXT;

    public RxRestClient(String url,
                        Map<String, Object> params,
                        RequestBody body,
                        LoaderStyle loaderStyle,
                        Context context) {
        this.URL = url;
        PARAMS.putAll(params);
        this.BODY = body;
        this.LOADER_STYLE = loaderStyle;
        this.CONTEXT = context;
    }

    // 构造者
    public static RxRestClientBuilder Builder(){
        return new RxRestClientBuilder();
    }

    private Observable<String> request(HttpMethod method){
        final RxRestService service = RestCreator.getRxRestService();

        Observable<String> observable = null;

        if (LOADER_STYLE != null){
            LatteLoader.showLoading(CONTEXT, LOADER_STYLE);
        }

        switch (method){
            case GET:
                observable = service.get(URL, PARAMS);
                break;
            case POST:
                observable = service.post(URL, PARAMS);
                break;
            case PUT:
                observable = service.put(URL, PARAMS);
                break;
            case DELETE:
                observable = service.delete(URL, PARAMS);
                break;
            default:
                break;
        }

        return observable;
    }

    public final Observable<String> get(){
       return request(HttpMethod.GET);
    }

    public final Observable<String> post(){
        return request(HttpMethod.POST);
    }

    public final Observable<String> put(){
        return request(HttpMethod.PUT);
    }

    public final Observable<String> delete(){
       return request(HttpMethod.DELETE);
    }
}
