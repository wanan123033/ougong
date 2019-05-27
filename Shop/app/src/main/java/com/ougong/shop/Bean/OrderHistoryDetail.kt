package com.ougong.shop.Bean

import com.ougong.shop.httpmodule.OrderHistoryJavaBean

class OrderHistoryDetail {
    var addressSnap:AddressSnap? = null
    var brandInfoList:Array<OrderHistoryJavaBean.BrandInfo>? = null
    var clientTypeName:String? = null
    var clientType:Int? = null
    var createTime:String? = null
    var deleted:Boolean? = null
    var deliveryType:Int? = null
    var id:Int? = null
    var logisticsSnap:LogisticsSnap? = null
    var orderInfoList:Array<OrderHistoryJavaBean.OrderInfo>? = null
    var orderNo:String? = null
    var orderType:Int? = null
    var payType:Int? = null
    var payTypeName:String? = null
    var paymentNo:String? = null
    var productPrice:Double? = null
    var serviceFeeFactor:Double? = null
    var servicePrice:Double? = null
    var shortDistancePrice:Double? = null
    var status:Double? = null
    var statusName:String? = null
    var totalPrice:Double? = null
    var userId:Int? = null
    var userSnap:UserSnap? = null
    var userTypeName:String? = null
    var uuid:String? = null
    var woddenPrice:Double? = null
    class UserSnap(var id:Int,var infoData:UserInfo,var phone:String,var type:Int)
    class AddressSnap(var address:String,var addressInfo:String,var cityId:Int,var createTime:Long,var districtId:Int,var id:Int,var isDefault:Boolean,
                      var mobile:String,var name:String,var provinceId:Int,var updateTime:Long,var userId:Int)
    class LogisticsSnap(var companyName:String)
    class UserInfo(var designData:DesignData)
    class DesignData(var parent:Int)
}
