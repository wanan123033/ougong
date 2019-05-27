package com.ougong.shop.ActiivtyV2.bean

class BrandBeanCollection {

    data class BrandBean(var id : Int?,var name : String,var enable : Boolean,var sort : Int,var image:String?)


    data class BrandStyleBean(var id : Int?,var name : String,var images : String,var enable : Boolean)

    data class BrandItemBean(var id : Int,var name : String,var logo : String,var preview : String)

}

data class BrandDetailBean(var id : Int,var name : String, var introduct : String, var productCount : Int, var logo : String,
                           var preview : String)

data class BrandDetailTagBean(var id : Int,var name : String)