package com.ougong.shop.httpmodule

data class OrderHistoryJavaBean(var rows:Array<Databean>?,var total:Int?) {

    data class Databean(var brandInfoList:Array<BrandInfo>?,var createTime:String?,var id:Int?,var orderInfoList:Array<OrderInfo>?,var orderNo:String?,
    var orderType:Int?,var payType:Int?,var status:Double?,var statusName:String?,var totalPrice:Double?)

    data class BrandInfo(var brandSnap:BrandSnap?,var createTime: String?,var id:Int?,var orderId:Int?)

    data class OrderInfo(var createTime: String?,var id:Int?,var orderId:Int?,var specSnap:SpecSnap?)
    data class BrandSnap(var cityOperatorFactor:Int?,var code:String?,var companyFactor:Int?,var enable:Boolean?,var generalDesignerFactor:Double?,var generalFactor:Double?,
                    var id:Int?,var logo:String?,var name:String?,var sort:Long?,var supplierId:Int?,var vipDesignerFactor:Double?,var woodenPriceFactor:Int?)

    data class SpecSnap(var color:String?,var count:Int?,var cube:Double?,var id:Long?,var image:Array<String>?,var lastUpdateTime:Long?,var marketPrice:Double?,var material:String?,
                   var model:Boolean?,var ogCode:String?,var orderType:Int?,var price:Double?,var product:Product?,var productCategory:ProductCategory?,var productId:Int?,
                   var remark:String?,var spec:String?,var stock:Int?,var supplierId:Int?,var title:String?)

    data class Product(var brandId:Int?,var categoryId:Int?,var createTime:String?,var headImage:String?,var id:Int?,var isSingle:Boolean?,var sellStatus:Boolean?,var seq:Long?,var styleIds:Array<Int>?,var title:String?,var unit:String?)

    data class ProductCategory(var appImage:String?,var enable:Boolean?,var id:Int?,var image:String?,var name:String?,var parentId:Int?,var xcxImage:String?)
}
