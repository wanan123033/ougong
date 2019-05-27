package com.ougong.shop.httpmodule

import com.ougong.shop.ActiivtyV2.bean.DesingerBean
import com.ougong.shop.ActiivtyV2.bean.MOneyBeanDetail
import com.ougong.shop.ActiivtyV2.bean.MyMOneyBean
import com.ougong.shop.Bean.Netbean
import com.ougong.shop.Bean.User
import com.ougong.shop.Bean.fuckNetbean
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.*

interface AccountAPIService {

//
//    @GET("user")
//    fun getUser(): Flowable<UserResponse>


    @POST("user/front/common/passwordModify")
    fun passwordReset(@Query("confirmPassword") confirmPassword: String,
                      @Query("newPassword") newPassword: String,@Query("phone") phone: String,
                      @Query("smsCode")smsCode : String) : Flowable<Netbean<String>>



    @POST("user/front/mobile/app/login/refreshToken")
    fun refreshToken(@Header("Cookie") refeshtoken:String): Call<Netbean<String>>



    @POST("user/front/mobile/common/sendSms")
    fun sendSms(@Query("phone") phone: String, @Query("type") type : Int): Flowable<Netbean<String>>


    @GET("user/front/common/verifyPhone")
    fun CheckPhoneNo(@Query("phone") phone: String): Flowable<Netbean<String>>


    @GET("user/front/common/passwordFind")
    fun passwordFind(@Query("phone") phone: String,@Query("smsCode") smsCode: String): Flowable<Netbean<String>>


    @POST("user/front/common/passwordReset")
    fun passwordReset(@Query("confirmPassword") phone: String,@Query("password") password: String,
                      @Query("uuid") uuid: String): Flowable<Netbean<String>>


    @GET("user/front/common/verifyPhone")
    fun verifyPhone(@Query("phone") phone: String): Flowable<Netbean<String>>


    @POST("user/front/mobile/common/general")
    fun registerGeneral(@Query("password") password : String, @Query("phone") phone: String,
                        @Query("smsCode") smsCode : String): Flowable<Netbean<String>>

    @GET("user/front/mobile/app/login/validation")
    fun LogIn(@Query("password") password : String, @Query("username") phone: String): Flowable<Netbean<User>>

    @GET("user/front/common/getUserInfo")
    fun RefeshUserInfo(): Flowable<Netbean<User>>


    @POST("user/front/common/passwordModify")
    fun PasswordModify(@Query("oldPassword") oldPassword : String, @Query("password") password: String): Flowable<Netbean<String>>



    @POST("user/front/common/changePhone")
    fun ChangePhone(@Query("password") password : String, @Query("phone") phone: String,@Query("smsCode") smsCode: String): Flowable<Netbean<String>>

    @GET("user/front/common/subAccount")
    fun getSubAccountLidt(@Query("queryType") queryType : Int): Flowable<fuckNetbean<DesingerBean>>


    @GET("user/front/common/myWallet")
    fun getMoney(): Flowable<Netbean<MyMOneyBean>>


    @POST("user/front/common/takeOut")
    fun takeOut(@Query("amount") amount : Int): Flowable<Netbean<String>>


    @GET("user/front/common/walletDetail")
    fun MoneyDetail(@Query("endDate") endDate : String? = null, @Query("startDate") startDate : String? = null,
                    @Query("type") type : Int? = null): Flowable<fuckNetbean<MOneyBeanDetail>>



}
