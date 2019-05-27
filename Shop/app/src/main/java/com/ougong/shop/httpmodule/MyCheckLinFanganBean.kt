package com.ougong.shop.httpmodule

class MyCheckLinFanganBean {
    var rows:Array<RowBean>? = null
    var total:Int? = null
    class RowBean{
        var area:Int? = 0
        var createTime:String? = null
        var data:Array<DataBean>? = null
        var headImage:String? = null
        var houseTypeName:String? = null
        var id:Int? = 0
        var name:String? = null
        var productCount:Int? = 0
        var userId:Int? = 0
    }
    class DataBean(var products:Array<ProductBean>,var roomName:String)
    class ProductBean(var count:Int,var specId:Int)
}
