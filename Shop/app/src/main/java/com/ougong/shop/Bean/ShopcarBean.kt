package com.ougong.shop.Bean

import java.util.*



data class FuckData(var serviceFeeFactor : Float, var brandList : Array<BrandList>?){
    var isAllSelect = false
}

class BrandList {

    companion object {
        val APP_TOCHECK_ED = 4 //状态发生变化，变为check状态

        var APP_TO_CHECK = 3 //变为非选取状态，

        var CHECK = 2 //非选取

        var CHEKED = 1 //选取
    }
    var appCheckState = 2

    var id: Int = 0
    var name: String? = null
    var supplierId: Int = 0
    var logo: String? = null
    var code: String? = null
    var enable: Boolean = false
    var cityOperatorFactor: Double = 0.toDouble()
    var companyFactor: Double = 0.toDouble()
    var vipDesignerFactor: Double = 0.toDouble()
    var generalDesignerFactor: Float = 0f
    var generalFactor: Double = 0.toDouble()
    var woodenPriceFactor: Float = 0f
    var shortFreight: ShortFreight? = null
    var specificationCartVos: Array<SpecificationCartVos>? = null

}

class ShortFreight {
    var unitPrice: Float = 0f
    var startPrice: Float = 0f
    var cubeThreshold: Int = 0

}

class SpecificationCartVos {

    var appCheckState = 2
    var id: Int = 0
    var productId: Int = 0
    var title: String? = null
    var ogCode: String? = null
    var material: String? = null
    var materialIntro: String? = null
    var color: String? = null
    var colorIntro: String? = null
    var spec: String? = null
    var specIntro: String? = null
    var model: Boolean = false
    var price = 0.toDouble()
    var stock: Int = 0
    var cube: Float = 0f
    var marketPrice = 0.toDouble()
    var images: Array<String>? = null
    var lastUpdateTime: String? = null
    var createTime: String? = null
    var product: Product? = null
    var count: Int = 0
    var minCount:Int = 0
    var productCategory: ProductCategory? = null
    var properties: Array<Properties>? = null

}
class Product {

    var id: Int = 0
    var title: String? = null
    var headImage: String? = null
    var brandId: Int = 0
    var categoryId: Int = 0
    var unit: String? = null
    var createTime: String? = null
    var styleIds: Array<Int>? = null
    var sellStatus: Boolean = false

}

class Properties {

    var id: Int = 0
    var specId: Int = 0
    var nameId: Int = 0
    var value: String? = null
    var name: String? = null

}

class ProductCategory {

    var id: Int = 0
    var name: String? = null
    var image: String? = null
    var appImage: String? = null
    var xcxImage: String? = null
    var parentId: Int = 0
    var enable: Boolean = false

}