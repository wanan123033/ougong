package com.ougong.shop.activity.Maininfo.mangeAdress

import java.io.Serializable

data class AddressBean(var id: Int, var name : String, var mobile : String, var provinceId : Int, var cityId : Int,var districtId: Int, var address : String,
                       var isDefault : Boolean, var addressInfo : String) : Serializable

class builder(){
    fun builde() : AddressBean{
        return AddressBean(0,"","",-1,-1,-1,"",false,"")
    }
}

data class addAddressdatabaseBean(var id : Int, var name: String, var children : Array<addAddressdatabaseBean>)