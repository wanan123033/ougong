package com.ougong.shop.httpmodule

class HxBean {
    var id:Int? = 0
    var name:String? = null
    var isDelete:Boolean? = false
    var spaceId:Array<SpecDataBean>? = null

    class SpecDataBean(var count:Int? = 0,var id:Int? = 0)
}
