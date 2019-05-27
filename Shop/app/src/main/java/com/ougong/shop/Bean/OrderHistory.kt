package com.ougong.shop.Bean

import com.ougong.shop.ActiivtyV2.bean.DesingerBean
import java.io.Serializable

class OrderHistoryBean {

    var total: Int = 0
    var rows: List<Rows>? = null

}

class Rows : OrderHistoryBase(),Cloneable{

    var id: Int = 0
    var orderBaseNo: String? = null
    var totalPrice: Double = 0.toDouble()
    var status: Int = 0
    var createTime: String? = null
    var serviceFeeFactor: Double = 0.toDouble()
    var servicePrice: Double = 0.toDouble()
    var shortFee: Double = 0.toDouble()

    var designer : Desinger? = null
    var subList: List<SubList>? = null

    /**
     * 这是浅复制。但是基本数据类型是可以相互独立的，这里已经满足要求了
     */
    public override fun clone(): Any {
        return super.clone()
    }
}

class Desinger{
    var name : String? = null
}

class SubList : OrderHistoryBase(){

    var id: Int = 0
    var orderBaseId: Int = 0
    var orderSubNo: String? = null
    var productPrice: Double = 0.toDouble()
    var woddenPrice: Double = 0.toDouble()
    var shortDistancePrice: Double = 0.toDouble()
    var servicePrice: Double = 0.toDouble()
    var brandSnap: BrandSnap? = null
    var createTime: String? = null
    var subInfoList: List<SubInfoList>? = null

}

class SubInfoList : OrderHistoryBase() {

    var id: Int = 0
    var orderSubId: Int = 0
    var createTime: String? = null
    var productSnap: ProductSnap? = null

}
class BrandSnap {

    var woodenPriceFactor: Double = 0.toDouble()
    var generalDesignerFactor: Double = 0.toDouble()
    var companyFactor: Double = 0.toDouble()
    var code: String? = null
    var supplierId: Int = 0
    var generalFactor: Double = 0.toDouble()
    var vipDesignerFactor: Double = 0.toDouble()
    var shortFreight: ShortFreight? = null
    var cityOperatorFactor: Double = 0.toDouble()
    var enable: Boolean = false
    var name: String? = null
    var logo: String? = null
    var id: Int = 0

}
class ProductSnap {

    var factoryCode: String? = null
    var images: List<String>? = null
    var product: Product? = null
    var marketPrice: Double = 0.toDouble()
    var color: String? = null
    var productId: Int = 0
    var count: Int = 0
    var title: String? = null
    var spec: String? = null
    var productCategory: ProductCategory? = null
    var material: String? = null
    var price: Double = 0.toDouble()
    var model: Boolean = false
    var id: Int = 0
    var cube: Double = 0.toDouble()
    var stock: Int = 0
    var lastUpdateTime: Long = 0

}

/**
 * 1 是title 2 是品牌title、
 * 3 item 4 foot
 */
open class OrderHistoryBase{
    var type : Int = 3
}