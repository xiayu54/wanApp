package com.yuan.fastec.latte.net.Rx;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * @author XYuan
 * Version  1.0
 * Description
 */
public interface RxRestService {

    @GET
    Observable<String> get(@Url String url, @QueryMap Map<String, Object> params);

    @FormUrlEncoded
    @POST
    Observable<String> post(@Url String url, @FieldMap Map<String, Object> body);

    @FormUrlEncoded
    @PUT
    Observable<String> put(@Url String url, @FieldMap Map<String, Object> body);

    @DELETE
    Observable<String> delete(@Url String url, @QueryMap Map<String, Object> params);

    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String url, @QueryMap Map<String, Object> params);

    @Multipart
    @POST
    Observable<ResponseBody> upload(@Url String url, @Part MultipartBody.Part file);

}
