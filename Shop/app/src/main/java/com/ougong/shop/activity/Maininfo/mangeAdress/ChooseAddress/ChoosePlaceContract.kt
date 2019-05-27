package com.ougong.shop.activity.Maininfo.mangeAdress.ChooseAddress

import com.ougong.shop.Bean.fuckNetbean
import com.ougong.shop.activity.Maininfo.mangeAdress.addAddressdatabaseBean
import io.armcha.ribble.presentation.base_mvp.api.ApiContract


interface ChoosePlaceContract {

    interface view : ApiContract.View{
        fun GetDataSucess(it: fuckNetbean<addAddressdatabaseBean>)
    }

    interface presenter : ApiContract.Presenter<view>{
        fun getData()
 }
}