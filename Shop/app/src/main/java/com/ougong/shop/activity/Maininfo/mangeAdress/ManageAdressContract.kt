package com.ougong.shop.activity.Maininfo.mangeAdress


import com.ougong.shop.Bean.fuckNetbean
import io.armcha.ribble.presentation.base_mvp.api.ApiContract

interface ManageAdressContract {
    interface View : ApiContract.View{

        fun getAdressSucess(it: fuckNetbean<AddressBean>)
    }

    interface Presenter : ApiContract.Presenter<View> {

        fun getAdressList()

        fun deleteAdress(id: Int)

        fun updateAdress()
    }
}