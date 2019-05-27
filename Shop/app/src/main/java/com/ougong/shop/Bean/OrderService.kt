package com.ougong.shop.Bean

import com.ougong.shop.activity.Maininfo.mangeAdress.AddressBean

class OrderBean  {

    var serviceFeeFactor: Double = 0.toDouble()
    var addressList: List<AddressBean>? = null
    var products: List<Products>? = null

}

class AddressList {

    var id: Int = 0
    var userId: Int = 0
    var name: String? = null
    var mobile: String? = null
    var provinceId: Int = 0
    var cityId: Int = 0
    var districtId: Int = 0
    var address: String? = null
    var isDefault: Boolean = false
    var addressInfo: String? = null
    var createTime: String? = null
    var updateTime: String? = null

}

//class Product {
//
//    var id: Int = 0
//    var title: String? = null
//    var headImage: String? = null
//    var brandId: Int = 0
//    var categoryId: Int = 0
//    var unit: String? = null
//    var createTime: String? = null
//    private var styleIds: List<Int>? = null
//    var sellStatus: Boolean = false
//
//    fun setStyleIds(styleIds: List<Int>) {
//        this.styleIds = styleIds
//    }
//
//    fun getStyleIds(): List<Int>? {
//        return styleIds
//    }
//}

/**
 * Copyright 2018 bejson.com
 */


/**
 * Auto-generated: 2018-12-22 16:48:36
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
class Products {

    var id: Int = 0
    var name: String? = null
    var supplierId: Int = 0
    var logo: String? = null
    var code: String? = null
    var enable: Boolean = false
    var cityOperatorFactor = 0f
    var companyFactor = 0f
    var vipDesignerFactor = 0f
    var generalDesigner: Float = 0f
    var generalFactor: Double = 0.toDouble()
    var woodenPriceFactor: Float = 0f
    var shortFreight: ShortFreight? = null
    var specificationCartVos: List<SpecificationCartVos>? = null

    var product : Product? = null

}