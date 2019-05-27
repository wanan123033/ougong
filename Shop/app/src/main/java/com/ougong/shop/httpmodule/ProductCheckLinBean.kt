package com.ougong.shop.httpmodule

import java.util.*

class ProductCheckLinBean {
    var total:Int? = 0
    var rows:Array<DataBean>? = null
    class DataBean(var brandId:Int,var categoryId:Int,var createTime:String,var headImage:String,var id:Int,var seq:Long,
                   var specifications:Array<SpecDataBean>,var title:String,var viewCount:Int)
    class SpecDataBean(var color:String,var cube:Double,var factoryCode:String,var id:Int,var images:Array<String>,var isDefault:Boolean,
                       var lastUpdateTime:String,var marketPrice:Double,var material:String,var minCount:Int,var model:Boolean,var ogCode:String,
                       var price:Double,var productId:Int,var spec:String,var stock:Int,var title:String){
        override fun toString(): String {
            return "SpecDataBean(color='$color', cube=$cube, factoryCode='$factoryCode', id=$id, images=${Arrays.toString(
                images
            )}, isDefault=$isDefault, lastUpdateTime='$lastUpdateTime', marketPrice=$marketPrice, material='$material', minCount=$minCount, model=$model, ogCode='$ogCode', price=$price, productId=$productId, spec='$spec', stock=$stock, title='$title')"
        }
    }
}
