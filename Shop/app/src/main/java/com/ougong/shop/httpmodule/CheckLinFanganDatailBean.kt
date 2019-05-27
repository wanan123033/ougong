package com.ougong.shop.httpmodule

class CheckLinFanganDatailBean {
    var id:Int? = 0
    var name:String? = null
    var area:Int? = null
    var data:Array<RoomBean>? = null
    var userId:Int? = 0
    var createTime:String? = null
    var houseTypeName:String? = null
    var totalMoney:Double? = 0.0
    var productCount:Int? = 0
    class RoomBean(var roomName:String,var products:Array<ProductBean>)
    class ProductBean(var specId:Int,var ogCode:String,var productCategoryId:Int,var color:String,
                      var productId:Int,var price:Double,var headImage:String,var productCategoryName:String,
                      var count:Int,var title:String,var spec:String)
}
