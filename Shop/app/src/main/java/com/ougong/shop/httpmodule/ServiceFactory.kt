package com.ougong.shop.httpmodule

import com.baigui.netlib.HttpService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by chenjunshan on 2016/8/19.
 */

class ServiceFactory private constructor() {

    init {
        val builder = OkHttpClient.Builder()
            .connectTimeout(DEFAULT_TIMEOUT.toLong(), TimeUnit.SECONDS)
//            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(TokenInterceptor())
            .addInterceptor(LoggerInterceptor())
            .retryOnConnectionFailure(true)

        retrofit = Retrofit.Builder()
            .client(builder.build())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(BASE_URL)
            .build()
        httpService = retrofit.create(HttpService::class.java)
    }

    var accountAPIService: AccountAPIService? = null
        get() {
            if (null == field) {
                this.accountAPIService = retrofit.create(AccountAPIService::class.java)
            }
            return field
        }

    var mAPKVersionService: APKVersionService? = null
        get() {
            if (null == field) {

                val builder = OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .connectTimeout(DEFAULT_TIMEOUT.toLong(), TimeUnit.SECONDS)

                var otherretrofit = Retrofit.Builder()
                    .client(builder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(OTHER_URL)
                    .build()
                this.mAPKVersionService = otherretrofit.create(APKVersionService::class.java)
            }
            return field
        }

    var userInfoApiService: UserInfoApiService? = null
        get() {
            if (null == field) {
                this.userInfoApiService = retrofit.create(UserInfoApiService::class.java)
            }
            return field
        }

    var productApiservice: ProductApiservice? = null
        get() {
            if (null == field) {
                this.productApiservice = retrofit.create(ProductApiservice::class.java)
            }
            return field
        }

    companion object {
//                var BASE_URL = "http://192.168.1.149/"
//      var BASE_URL = "http://test.ougmall.com"
                var BASE_URL = "https://www.ogmall.com"
//        var BASE_URL = "http://admin.ogmall.com"
//        var BASE_URL = "http://www.ogmall.com/"

        var OTHER_URL = "http://192.168.1.115:9779"
        private val DEFAULT_TIMEOUT = 5//默认5秒超时

        val instance = ServiceFactory()

        lateinit var httpService: HttpService

        lateinit var retrofit: Retrofit
//        private//是否超时重连
//        val okHttpClient: OkHttpClient
//            get() {
//                val builder = OkHttpClient.Builder()
//                    .connectTimeout(DEFAULT_TIMEOUT.toLong(), TimeUnit.SECONDS)
//                    .retryOnConnectionFailure(true)
//                return builder.build()
//            }
    }
}
