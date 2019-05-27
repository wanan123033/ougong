package com.ougong.shop.Bean

import java.io.Serializable

class ProductListItem : Serializable{
    var id : Int = 0
    var title : String = ""
    var headImage : String? = null
    var viewCount: Int = 0
    var sales: Int = 0
    var showPrice : Double? = 0.toDouble()
    var showMarketPrice : Double = 0.toDouble()
}