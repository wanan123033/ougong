package com.baigui.netlib;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by chenjunshan on 2016/8/19.
 */

public class HttpBuilder {
    public static String BASE_URL = "http://318.ogmall.com";//正式环境
    private static final int DEFAULT_TIMEOUT = 5;//默认5秒超时

    private static final HttpBuilder INSTANCE = new HttpBuilder();
    private Retrofit retrofit;
    public static HttpService httpService;

    private HttpBuilder() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true);//是否超时重连
        retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        httpService = retrofit.create(HttpService.class);
    }
//
//    private static OkHttpClient getOkHttpClient() {
//        OkHttpClient.Builder builder = new OkHttpClient.Builder()
//                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
//                .retryOnConnectionFailure(true);//是否超时重连
//        return builder.build();
//    }

    public static HttpBuilder getInstance() {
        return INSTANCE;
    }

    public <T> Disposable execute(Observable<T> observable, Consumer<T> subscriber) {
        return observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    public <T> Disposable execute(Observable<T> observable, Consumer<T> subscriber,Consumer<? super Throwable> onError) {
        return observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber,onError);
    }

    //貌似这个可以吧请求的数据转化，这里暂时不纠结这个接口
    public <T,R> Disposable execute(Observable<T> observable, Consumer<R> consumer, Function<T,R> func1) {
        return observable.map(func1)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer);
    }
}
