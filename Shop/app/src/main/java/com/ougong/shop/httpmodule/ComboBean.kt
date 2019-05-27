package com.ougong.shop.httpmodule

class ComboBean {
    var rows: Array<DataBean>? = null
    var total:Int? = 0
    class DataBean{
        var id:Int? = 0
        var name:String? = null
        var headImage:String? = null
        var packageCategoryId:Int? = 0
        var updateTime:String? = null
        var minPrice:String? = null
    }
}
