package com.ougong.shop.activity.Maininfo.mangeAdress.editAdress

import com.ougong.shop.activity.Maininfo.mangeAdress.AddressBean
import io.armcha.ribble.presentation.base_mvp.api.ApiContract

class EditAdressContract {
    interface view : ApiContract.View{

        fun addAddressSucess()

        fun editAdressSucess()
    }

    interface presenter : ApiContract.Presenter<view>{
        fun addAddress(address: AddressBean)

        fun editAddress(addressBean: AddressBean)
    }
}