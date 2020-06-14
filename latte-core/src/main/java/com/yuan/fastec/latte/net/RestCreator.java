package com.yuan.fastec.latte.net;

import com.yuan.fastec.latte.app.ConfigType;
import com.yuan.fastec.latte.app.latte;
import com.yuan.fastec.latte.net.Rx.RxRestService;
import com.yuan.fastec.latte.net.interceptors.MyHttpLoggingInterceptor;

import java.util.ArrayList;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * @author XYuan
 * Version  1.0
 * Description
 *  创建客户端
 */
public class RestCreator {

    private static final class ParamsHodler{
        public static final WeakHashMap<String, Object> PARAMS = new WeakHashMap<>();

    }

    public static WeakHashMap<String, Object> getParams(){
        return ParamsHodler.PARAMS;
    }

    private static final class RetrofitHolder{
        private static final String BASE_URL = (String) latte.getConfigurations().get(ConfigType.API_HOST.name());
        /**
         * retrofit client
         */
        private static final Retrofit RETROFIT_CLIENT = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(OKHttpHolder.OK_HTTP_CLIENT)
                .addConverterFactory(ScalarsConverterFactory.create())  // 结果转化为 String 类型
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    private static final class OKHttpHolder{
        private static final int TIME_OUT = 30;
        private static final OkHttpClient.Builder BUILDER = new OkHttpClient.Builder();
        // 获取拦截器
        private static final ArrayList<Interceptor> INTERCEPTORS = (ArrayList<Interceptor>) latte.getConfigurations()
                .get(ConfigType.INTERCEPTOR.name());
        private static OkHttpClient.Builder addInterceptor(){
            if (INTERCEPTORS != null && !INTERCEPTORS.isEmpty()){
                for (Interceptor interceptor : INTERCEPTORS) {
                    BUILDER.addInterceptor(interceptor);
                }
            }
            return BUILDER;
        }

        public static OkHttpClient.Builder setInterceptorLogging(){
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new MyHttpLoggingInterceptor());
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
            return addInterceptor().addNetworkInterceptor(loggingInterceptor);
        }

        /**
         * OKHttp client
         */
        private static final OkHttpClient OK_HTTP_CLIENT = setInterceptorLogging()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .build();
    }

    private static final class RestServiceHolder{
        /**
         * RestService
         */
        private static final RestService REST_SERVICE =
                RetrofitHolder.RETROFIT_CLIENT.create(RestService.class);
    }

    /**
     * 获取 RestService
     * @return
     */
    public static RestService getRestService(){
        return RestServiceHolder.REST_SERVICE;
    }

    private static final class RxRestServiceHolder{
        /**
         * RestService
         */
        private static final RxRestService REST_SERVICE =
                RetrofitHolder.RETROFIT_CLIENT.create(RxRestService.class);
    }

    /**
     * 获取 RestService
     * @return
     */
    public static RxRestService getRxRestService(){
        return RxRestServiceHolder.REST_SERVICE;
    }



}
