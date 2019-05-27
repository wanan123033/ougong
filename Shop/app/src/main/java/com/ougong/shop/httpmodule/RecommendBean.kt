package com.ougong.shop.httpmodule

import java.io.Serializable

/*
{"status":200,
"data":
    {
    "total":2,
    "rows":[
        {
            "id":8033,
            "productId":6027,
            "title":"30头/45头/63头/36头长",
            "ogCode":"WLMDC001-276-8033",
            "material":"铁艺",
            "color":"金色",
            "spec":"500*350",
            "price":693.00,
            "purchasePrice":0.00,
            "minCount":1,
            "stock":100,
            "cube":0.0,
            "marketPrice":1260.0,
            "product":{
                "id":6027,
                "title":"文联美灯城 吊灯 现代风格 铁艺 30头/45头/63头/36头长",
                "headImage":"http://og-image-admin-test.oss-cn-shenzhen.aliyuncs.com/product/c502af0a-bee3-400f-88d1-6c6f5b715029.png",
                "brandId":34,
                "categoryId":276,
                "unit":"个",
                "sellStatus":true,
                "isSingle":true
                }
         }
     ]}}


 */
class RecommendBean : Serializable{
    var rows:Array<DataBean>? = null
    var total:Int? = 0
    var selectedNum:Int = 0
    var selectedPrice:Double = 0.0
    class DataBean(var color:String,var cube:Double,var id:Int,var marketPrice:Double,var material:String,var minCount:Int,
                   var ogCode:String,var price:Double,var product:ProductBean,var productId:Int,var purcheasePrice:Double,
                   var spec:String,var stock:Int,var title:String)

    class ProductBean(var brandId:Int,var categoryId:Int,var headImage:String,var id:Int,var isSingle:Boolean,
                      var sellStatus:Boolean,var title:String,var unit:String,var enable:Boolean = false,var specId:Int,var count:Int)

}
