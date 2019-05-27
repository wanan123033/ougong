package com.ougong.shop.httpmodule

class StyleBrandBean {
    var brands:Array<BrandBean>? = null
    var styles:Array<StyleBean>? = null

    class BrandBean(var adSubTitle:String,var adTitle:String,var commend:Boolean,var createTime:String,var id:Int,var logo:String,var name:String,var preview:String,var sort:Int)
    class StyleBean(var enable:Boolean,var id:Int,var image:String,var name:String)
}
