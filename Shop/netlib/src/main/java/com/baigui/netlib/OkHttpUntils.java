package com.baigui.netlib;

import android.content.Context;
import com.google.gson.Gson;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
//import rx.Observable;
//import rx.Subscriber;
//import rx.android.schedulers.AndroidSchedulers;
//import rx.schedulers.Schedulers;

public class OkHttpUntils {
//
//    private Context context ;
//
//    private static OkHttpUntils okHttpUntils ;
//
//    private OkHttpUntils(Context context){
//        this.context = context ;
//    }
//
//    public static OkHttpUntils getOkHttpUntils(Context context){
//        if(okHttpUntils==null){
//            synchronized (OkHttpUntils.class){
//                if(okHttpUntils==null){
//                    okHttpUntils = new OkHttpUntils(context);
//                }
//            }
//        }
//        return okHttpUntils;
//    }
//
//    public void get(String url, final Class clazz, final NetClickListener netClickListener){
//
//        Request request = new Request.Builder()
//                .get()
//                .url(url)
//                .build();
//        OkHttpClient.Builder  okbuild = new OkHttpClient.Builder();
//        okbuild.addInterceptor(new LogInterceptor())
//                .build()
//                .newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                if(netClickListener!=null){
//                    netClickListener.Error(e.getMessage().toString());
//                }
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                final String string = response.body().string();
//                Observable.create(new Observable.OnSubscribe<BaseBean>() {
//                    @Override
//                    public void call(Subscriber<? super BaseBean> subscriber) {
//                        Gson gson = new Gson();
//                        BaseBean o = (BaseBean) gson.fromJson(string, clazz);
//                        if(o!=null){
//                            subscriber.onNext(o);
//                            subscriber.onCompleted();
//                        }else {
//                            subscriber.onError(new NullPointerException("请求数据为空"));
//                        }
//                    }
//                }).subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<BaseBean>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        if(netClickListener!=null){
//                            netClickListener.Error(e.getMessage().toString());
//                        }
//                    }
//
//                    @Override
//                    public void onNext(BaseBean baseBean) {
//                        if(netClickListener!=null){
//                            if(baseBean.getCode().equals("0")){
//                                netClickListener.Suesses(baseBean);
//                            }
//                        }
//                    }
//                });
//
//            }
//        });
//    }

}
