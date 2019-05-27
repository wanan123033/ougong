package com.ougong.shop.ActiivtyV2.bean

import java.math.BigDecimal

class ChengYunShangBean() {
}

class MyMOneyBean {

    var expendTotal: BigDecimal? = null
    var total: BigDecimal? = null
    var balance: BigDecimal? = null
    var designerTotal: BigDecimal? = null
    var companyTotal: BigDecimal? = null

    //这是UI用的
    var SearchType : Int = 0
    var startTime : String = ""
    var endTime : String = ""
}


class MOneyBeanDetail( var id: Int, var userId: Int) {

    var balance: BigDecimal? = null
    var amount: BigDecimal? = null
    var type : Int? = 0
    var dealTime : String? = null//"2018-12-21 15:52:58",
    var dealData: MoneyListdataBean? = null

 }
class MoneyListdataBean{

        var orderNo: String? = null
        var user_type: Int? = 0
        var user_id: Int? = null
        var user_name: String? =null
        var user_phone: String? = null

}