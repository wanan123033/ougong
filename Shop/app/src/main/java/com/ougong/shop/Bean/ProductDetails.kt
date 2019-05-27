package com.ougong.shop.Bean

import java.io.Serializable

data class ProductDetails(var id: Int, var title : String, var headImage: String, var specImages : Array<String>,var unit : String, var createTime : String,
                          var viewCount:Int, var sellStatus : Boolean,var showPrice: String, var showMarketPrice: String,
                          var brand : BrandBean, var categorys : Categorys)
{
    var details: String? = null
}

data class BrandBean(var id: Int,var name: String, var logo : String, var code: String)


data class Categorys(var id: Int,var name: String, var logo : String, var code: String)

class productSpec( var id : Int, var productId : Int, var color : String, var price : Double, var stock: Int, var marketPrice :Double, var images : Array<String>,
                        var spec : String, var isDefault : Boolean) : Serializable{
    var appCount: Int = 1
}

data class productDetailKeyValue(var name: String,var value: String)