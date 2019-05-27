package com.ougong.shop.activity.Maininfo.mangeAdress.addAddress

import com.ougong.shop.activity.Maininfo.mangeAdress.AddressBean
import com.ougong.shop.base_mvp.api.ApiPresenter
import io.armcha.ribble.presentation.base_mvp.api.ApiContract

interface AddAddressContract {

    interface view : ApiContract.View{

        fun addAddressSucess()

        fun editAdressSucess()
    }

    interface presenter : ApiContract.Presenter<view>{
        fun addAddress(address: AddressBean)

        fun editAddress(addressBean: AddressBean)
    }
}