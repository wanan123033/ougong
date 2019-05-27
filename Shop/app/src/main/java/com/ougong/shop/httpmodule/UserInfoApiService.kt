package com.ougong.shop.httpmodule

import com.ougong.shop.Bean.Netbean
import com.ougong.shop.Bean.fuckNetbean
import com.ougong.shop.activity.Maininfo.EditInfo.EditInfoPresenter
import com.ougong.shop.activity.Maininfo.mangeAdress.AddressBean
import com.ougong.shop.activity.Maininfo.mangeAdress.addAddressdatabaseBean
import io.reactivex.Flowable
import io.reactivex.Observable
import okhttp3.MultipartBody
import retrofit2.http.*

interface UserInfoApiService {



    @GET("orderService/front/address/{id}")
    fun getAddress(@Path("id") id : Int) : Flowable<Netbean<String>>

    @FormUrlEncoded
    @POST("orderService/front/address/addAddress")
    fun addAddress(@Field("address") address: String, @Field("cityId") cityId : Int, @Field("districtId")districtId : Int,
                   @Field("isDefault") isDefault : Boolean, @Field("mobile")mobile : String, @Field("name")name: String,
                   @Field("provinceId") provinceId : Int) : Flowable<Netbean<String>>


    @GET("orderService/front/address/addressList")
    fun getAddressList() : Flowable<fuckNetbean<AddressBean>>

    @DELETE("orderService/front/address/deleteAddress")
    fun delectAddressList(@Query("ids")id: Int) : Flowable<Netbean<String>>



    @POST("orderService/front/address/updateAddress")
    fun updateAddress(@Query("address") address: String, @Query("cityId") cityId : Int, @Query("districtId")districtId : Int,
                      @Query("id") id : Int, @Query("isDefault") isDefault : Boolean, @Query("mobile")mobile : String,
                      @Query("name")name: String, @Query("provinceId") provinceId : Int) : Flowable<Netbean<String>>


    @GET("api/baseData/front/mobile/province/list")
    fun getAddressData() : Flowable<fuckNetbean<addAddressdatabaseBean>>


    @FormUrlEncoded
    @POST("user/front/common/general/updateUserInfo")
    fun updataUserInfo(@FieldMap params : Map<String, String>) : Flowable<Netbean<String>>


    @Multipart
    @POST("fileServer/fileService/imageUp")
    fun uploadImage(@Part file: MultipartBody.Part): Observable<EditInfoPresenter.upLoaddata>

}