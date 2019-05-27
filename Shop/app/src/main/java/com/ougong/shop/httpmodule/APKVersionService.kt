package com.ougong.shop.httpmodule

import com.ougong.shop.Bean.Netbean
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface APKVersionService {

    @GET("apk/getVersion")
    fun getVersion(@Query("type") type : Int = 10) : Flowable<apkbean<CheckVersion>>

    @POST("orderService/front/address/addAddress")
    fun addAddress(@Query("address") address: String, @Query("cityId") cityId : Int, @Query("districtId")districtId : Int,
                   @Query("isDefault") isDefault : Boolean, @Query("mobile")mobile : String, @Query("name")name: String,
                   @Query("provinceId") provinceId : Int) : Flowable<Netbean<String>>

}

class apkbean<T>{
    var code : Int = 0
    var data : T? = null
    var message : String? = null
}

class CheckVersion{
    var introduce : String? = null
    var versionName : String? = null
    var uri : String? =null
    var versionCode = 0
    var isForced = false
}