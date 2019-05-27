package com.ougong.shop.httpmodule

import java.io.Serializable

class ComboDatailBean : Serializable{
    var area:Int? = 0
    var details:String? = null
    var enable:Boolean? = false
    var headImage:String? = null
    var houseTypeId:Int? = 0
    var houseTypeName:String? = null
    var id:Int? = 0
    var images:Array<String>? = null
    var intro:String? = null
    var name:String? = null
    var packageCategory:Category? = null
    var packageCategoryId:Int? = 0
    var recommendData:Array<Recommend>? = null
    var rooms:Array<Room>? = null
    var styleId:Int? = 0
    var styleName:String? = null
    var updateTime:String? = null
    val minPrice: Double? = null

    class Category(var id:Int,var name:String,var type:Int) : Serializable
    class Recommend(var count:Int,var specId:Int) : Serializable
    class Room(var roomName:String,var products:Array<Array<Product>>,var roomNum:Int = 0,var roomPrice:Double = 0.0,var isShow:Boolean = false) : Serializable
    class Product(var color:String,var count:Int,var headImage:String,var ogCode:String,var price:Double,
                  var productCategoryId:Int,var productCategoryName:String,var productId:Int,var spec:String,var specId:Int,
                  var specTitle:String,var title:String,var enable:Boolean) : Serializable
}
