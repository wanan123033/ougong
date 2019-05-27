package com.ougong.shop.activity.checkin

import com.ougong.shop.Bean.CateryBean
import com.ougong.shop.Bean.fuckNetbean
import io.armcha.ribble.presentation.base_mvp.api.ApiContract

interface EditFanganFenleiContract{
    interface View : ApiContract.View{
        fun initOneCategory(bean:fuckNetbean<CateryBean>)
    }
    interface Presenter : ApiContract.Presenter<EditFanganFenleiContract.View>{
        fun getAllOneCategory()
    }
}