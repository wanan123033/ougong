package com.ougong.shop.httpmodule

import com.ougong.shop.ActiivtyV2.bean.BrandBeanCollection
import com.ougong.shop.ActiivtyV2.bean.BrandDetailBean
import com.ougong.shop.ActiivtyV2.bean.BrandDetailTagBean
import com.ougong.shop.Bean.*
import io.reactivex.Flowable
import okhttp3.RequestBody
import retrofit2.http.*

interface ProductApiservice {

    @GET("api/goods/front/common/navigation")
    fun getCateryBean(): Flowable<fuckNetbean<CateryBean>>


    @GET("api/goods/front/common/brandCategorys")
    fun getBrandList(): Flowable<fuckNetbean<BrandBeanCollection.BrandBean>>


    @GET("api/goods/front/common/brandStyles")
    fun getStyleList(@Query("brandCategoryId") brandCategoryId : Int?): Flowable<fuckNetbean<BrandBeanCollection.BrandStyleBean>>


    @GET("api/goods/front/mobile/app/brand/details/{id}")
    fun getBrandDetail(@Path("id") BrandId : Int?): Flowable<Netbean<BrandDetailBean>>


    @GET("api/goods/front/mobile/app/brand/navigation/{id}")
    fun getBrandTagList(@Path("id") BrandId : Int?): Flowable<fuckNetbean<BrandDetailTagBean>>


    @GET("api/goods/front/mobile/app/brand/list")
    fun getBrandItemList(@Query("brandCategoryId") brandCategoryId : Int?,@Query("limit") limt : Int,@Query("page") page : Int?
    ,@Query("styleId") styleId : Int?): Flowable<Netbean<fuckProcudtList<BrandBeanCollection.BrandItemBean>>>

    @GET("api/goods/front/mobile/app/product/list")
    fun getProduct(@Query("brandId") brandId : Int? = null,@Query("categoryId") category: Int? = null, @Query("limit")limit: Int = 10, @Query("order")order: String? = null,
                   @Query("page") page: Int = 0, @Query("searchWord") search:String = "", @Query("sort")sort: String? = null)
            : Flowable<Netbean<fuckProcudtList<ProductListItem>>>

    @GET("api/goods/front/mobile/app/product/details/{id}")
    fun getProductDetail(@Path("id") id : Int) :Flowable<Netbean<ProductDetails>>


    @GET("api/goods/front/mobile/app/product/specification")
    fun getProductSpec(@Query("productId") id : Int) :Flowable<Netbean<fuckProcudtList<productSpec>>>

    @GET("api/goods/front/mobile/app/product/properties")
    fun getProductDetail1(@Query("specId") id : Int) :Flowable<Netbean<fuckProcudtList<productDetailKeyValue>>>


    @POST("orderService/front/cart/addCart")
    fun addShopCar(@Query("quantity")count : Int,@Query("specificationId")id: Int) : Flowable<Netbean<String>>

    @GET("orderService/front/cart/cartList")
    fun getShopCarList() : Flowable<Netbean<FuckData>>

    @POST("orderService/front/order/selectOrderStatusCount")
    fun getOrderCount() : Flowable<Netbean<AccountBean>>


    @GET("orderService/front/cart/getCartCount")
    fun getShopCarCount() : Flowable<Netbean<Int>>

    @POST("orderService//front/cart/updateCartQuantity")
    fun ChangeShopCarNO(@Query("count") No : Int, @Query("spec_id") id: Int) : Flowable<Netbean<String>>


    //    @DELETE1("orderService/front/cart/deleteCartProduct")
    @HTTP(method = "DELETE", path = "orderService/front/cart/deleteCartProduct", hasBody = true)
    fun DelectShopCarList(@Body body: RequestBody) : Flowable<Netbean<String>>

    @GET("orderService/front/cart/checkout")
    fun SendOrder(@Query("cartInfo") order: String,@Query("quick") quick : Boolean) : Flowable<Netbean<OrderBean>>


    @FormUrlEncoded
    @POST("orderService/front/order/submitOrder")
    fun BuyIt(@Field("orderType")orderType:Int, @Field("addressId") addressId: Int, @Field("cartInfo") cartInfo: String?, @Field("comboData")comboData:String?, @Field("clientType") clientType: Int,
              @Field("deliveryType")deliveryType : Int, @Field("payType")payType : Int, @Field("quick")quick: Boolean?,
              @Field("remark") remark: String?,@Field("logisticsCompany")logisticsCompany:String?,@Field("ownLogisticsName")ownLogisticsName:String?,@Field("ownLogisticsPhone")ownLogisticsPhone:String?) : Flowable<Netbean<PayBean>>

    /**
     * status 0: 未支付   10   已支付    null   全部
     */
    @POST("orderService/front/order/selectOrderListFront")
    fun getOrderList(@Query("limit") limit: Int, @Query("page") page: Int, @Query("status") status: Int?) : Flowable<Netbean<OrderHistoryBean>>


    @GET("orderService/front/order/selectOrderDetail")
    fun getOrderDetails(@Query("id") id: Int) : Flowable<Netbean<OrderHistoryDetail>>


    @DELETE("orderService/front/order/deleteOrder/{id}")
    fun delectOrder(@Path("id")id: Int) : Flowable<Netbean<String>>


    @FormUrlEncoded
    @POST("orderService/front/order/gotoPay")
    fun payOrder(@Field("clientType")clientType: String = "app",@Field("orderId")id: Int,@Query("payImage")payImage : String? = null,
                 @Field("payType")payType: Int) : Flowable<Netbean<PayBean>>
//mobile/common/packages/categorys

    @DELETE("orderService/front/order/cancelOrder/{id}")
    fun cancelOrder(@Path("id")id: Int) : Flowable<Netbean<String>>

    @GET("api/goods/front/mobile/common/packages/categorys")
    fun comboOneTitle():Flowable<Netbean<ComboTitleBean>>

    @GET("api/goods/front/mobile/common/packages/styles")
    fun comboStyleData(@Query("packageCategoryId")id:Int?):Flowable<fuckNetbean<ComboStyleBean>>


    @GET("api/goods/front/mobile/common/packages/list")
    fun comboData(@Query("limit")limit: Int, @Query("styleId")  styleId: Int?, @Query("page") page: Int, @Query("packageCategoryId")pageCategoryId:Int?): Flowable<Netbean<ComboBean>>


    @GET("api/goods/front/mobile/common/packages/details/{id}")
    fun comboDatail(@Path("id")id: Int): Flowable<Netbean<ComboDatailBean>>

    @POST("api/goods/common/specification/ofIds")
    fun recommendData(@Body body: RequestBody): Flowable<Netbean<RecommendBean>>

    @GET("api/goods/front/mobile/common/onlineDesign/userDesignList")
    fun userDesignList(@Query("limit")limit:Int,@Query("page")page:Int)

    @GET("/api/baseData/front/pc/getHouseType")
    fun getHxData(): Flowable<fuckNetbean<HxBean>>

    @GET("api/baseData/front/pc/getHouseSpace/{id}")
    fun getHouseSpace(@Path("id")hxId: Int?): Flowable<fuckNetbean<HxSpaceBean>>

    @GET("/api/goods/front/mobile/common/onlineDesign/products")
    fun getProductByCheckLin(
        @Query("brandId") brandId: Int?,
        @Query("categoryId") categoryId: Int?,
        @Query("limit") limit: Int?,
        @Query("maxPrice") maxPrice: Int?,
        @Query("minPrice") minPrice: Int?,
        @Query("order") order: String?,
        @Query("page") page: Int?,
        @Query("sort") sort: String?,
        @Query("styleId") styleId: Int?
    ): Flowable<Netbean<ProductCheckLinBean>>

    @POST("/api/goods/front/mobile/common/onlineDesign/save")
    fun saveCheckLinFangan(@Body body: RequestBody): Flowable<Netbean<String>>

    @GET("/api/goods/front/mobile/common/onlineDesign/userDesignList")
    fun getMyFangan(@Query("limit")limit: Int, @Query("page")page: Int): Flowable<Netbean<MyCheckLinFanganBean>>
    @GET("/api/goods/front/mobile/common/onlineDesign/{id}")
    fun getFanganDatail(@Path("id")id: Int): Flowable<Netbean<CheckLinFanganDatailBean>>

    @DELETE("/api/goods/front/mobile/common/onlineDesign/{id}")
    fun deleteFangan(@Path("id") id: Int): Flowable<Netbean<String>>

    @GET("/api/goods/front/common/navigationBottom")
    fun getBrandCategorys(@Query("categoryId")categoryId:Int?,@Query("limit")limit:Int?,@Query("searchWord")searchWord:String?,@Query("styleId")styleId:Int?) : Flowable<Netbean<StyleBrandBean>>

    @POST("/orderService/front/order/selectOrderListFront")
    fun getHistoryList(@Query("page")page: Int?, @Query("limit")limit: Int?, @Query("status")status: Int?): Flowable<Netbean<OrderHistoryJavaBean>>


    @POST("/orderService/front/order/confirmReceipt/{id}")
    fun qrsh(@Path("id") orderId: Int): Flowable<Netbean<String>>

    @FormUrlEncoded
    @POST("/orderService/front/order/cancelOrder")
    fun cannalOrder(@Field("id")orderId: Int): Flowable<Netbean<String>>

    @FormUrlEncoded
    @POST("/orderService/front/order/gotoPay")
    fun gotoPay(@Field("clientType")clientType:Int,@Field("orderIds")orderIds:Int,@Field("payType")payType:Int): Flowable<Netbean<PayBean>>

    @DELETE("/orderService/front/order/deleteOrder/{id}")
    fun deleteOrder(@Path("id")id: Int): Flowable<Netbean<String>>
}