package com.ougong.shop.activity.MainLabeFrgment

import com.ougong.shop.Bean.CateryBean
import com.ougong.shop.Bean.fuckNetbean
import com.ougong.shop.activity.Maininfo.InfoContract
import io.armcha.ribble.presentation.base_mvp.api.ApiContract

interface LabeContract{
    interface View : ApiContract.View{
        fun getDataSucess(bean : fuckNetbean<CateryBean>)
    }

    interface Presenter : ApiContract.Presenter<View>{

        fun getData()
    }
}