package com.ougong.shop.Bean


import android.text.TextUtils
import com.baigui.commonlib.utils.LogUtils
import com.ougong.shop.httpmodule.ServiceFactory
import java.io.Serializable
import java.lang.NullPointerException

/**
 * 用户信息类
 */
class User {
//
//    var contenToken: String? = null
//
//    var RefeshToken: String? = null

    var id: Int = 0
    var name: String? = null
    var phone: String? = null

    var type: Int? = -1

    var provinceId: Int = -1
    var cityId: Int = 0
    var districtId: Int = 0

    var avatar: String? = null

    var imagePath: String? = null
    var birthday: String? = null
    var infoData: infoData? = null

    var detailAdress: String? = null
    var mail_address: String? = null
    var fox_no: String? = null
    var home_phone: String? = null
    var factory_cateary: String? = null
    var vido_url: String? = null
    var factory_describe: String? = null
    fun getMap(): Map<String, String> {
        val map = hashMapOf<String, String>()
        map.put("name", name!!)
        birthday?.let {
            map.put("birthday", birthday!!)
        }
        map.put("cityID", cityId!!.toString())
        map.put("districtID", districtId!!.toString())

        if (imagePath != null) {
            map.put("headImage", imagePath!!)
        }
        map.put("provinceID", provinceId!!.toString())
        map.put("sex", "" + sex)

        //设计师加入详细地址
        if (!TextUtils.isEmpty(detailAdress))
            map.put("address", detailAdress!!)


        //设计师加入详细地址
        if (!TextUtils.isEmpty(mail_address))
            map.put("company_email", mail_address!!)

        //设计师加入详细地址
        if (!TextUtils.isEmpty(fox_no))
            map.put("faxNumber", fox_no!!)


        //设计师加入详细地址
        if (!TextUtils.isEmpty(home_phone))
            map.put("telephone", home_phone!!)

        //设计师加入详细地址
        if (!TextUtils.isEmpty(factory_cateary))
            map.put("trade", factory_cateary!!)

        //设计师加入详细地址
        if (!TextUtils.isEmpty(factory_describe))
            map.put("intro", factory_describe!!)
        return map
    }

    var sex: Int = 0

    companion object {
        val GENDER_MALE = 1 // 男
        val GENDER_FEMALE = 2 // 女
    }

    public fun isDesinger(): Boolean {
        return type == 4 && infoData?.designData?.parentType == 3
    }

    public fun isVipDesinger(): Boolean {
        return (type == 4 && infoData?.designData?.parentType == 4)

    }


    public fun isCustom(): Boolean {
        return type == 1
    }

    public fun isDecorationFactory(): Boolean {
        return type == 3
    }


    public fun isChengtYunShang(): Boolean {
        return type == 4
    }
}

class infoData(var province: String) {
    var normalData: normalData? = null
    var designData: DesignData? = null

}


class normalData(var birthday: String, var sex: Int)

class DesignData() {
    var parentType: Int? = 0
}