package com.baigui.netlib.V2.http;

import com.baigui.netlib.Contributor;
import com.baigui.netlib.HttpService;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import org.reactivestreams.Subscriber;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by liukun on 16/3/9.
 */
public class HttpMethods {

    public static final String BASE_URL = "https://api.douban.com/v2/movie/";

    static String API_URL = "https://api.github.com";

    private static final int DEFAULT_TIMEOUT = 5;

    private Retrofit retrofit;
    private HttpService movieService;

    //构造方法私有
    private HttpMethods() {
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder()
                .client(builder.build())
                //modify by zqikai 20160317 for 对http请求结果进行统一的预处理 GosnResponseBodyConvert
                .addConverterFactory(GsonConverterFactory.create())
//                .addConverterFactory(ResponseConvertFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(API_URL)
                .build();

        movieService = retrofit.create(HttpService.class);
    }

    //在访问HttpMethods时创建单例
    private static class SingletonHolder{
        private static final HttpMethods INSTANCE = new HttpMethods();
    }

    //获取单例
    public static HttpMethods getInstance(){
        return SingletonHolder.INSTANCE;
    }

    /**
     * 用于获取豆瓣电影Top250的数据
     * @param subscriber  由调用者传过来的观察者对象
     * @param start 起始位置
     * @param count 获取长度
     */
    public void getTopMovie(Consumer<List<Contributor>> subscriber, String start, String count){

        Disposable disposable = movieService.contributors(start, count)
//                .map(new HttpResultFunc<List<Contributor>>())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Consumer<List<Contributor>>() {
                    @Override
                    public void accept(List<Contributor> contributors) throws Exception {
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });

//        toSubscribe(observable, subscriber);
    }

    private <T> void toSubscribe(Observable<T> o, Subscriber<T> s){
//         o.subscribeOn(Schedulers.io())
//                .unsubscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(Consumer);
    }

    /**
     * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
     *
     * @param <T>   Subscriber真正需要的数据类型，也就是Data部分的数据类型
     */
    private class HttpResultFunc<T> implements Function<List<Contributor>, T> {

        @Override
        public T apply(List<Contributor> tHttpResult) throws Exception {
//            if (tHttpResult.getCount() == 0) {
//                throw new ApiException(100);
//            }
            return (T) tHttpResult;
        }
    }

}
