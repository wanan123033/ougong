package com.ougong.shop.ActiivtyV2.brandtab

import com.ougong.shop.ActiivtyV2.bean.BrandBeanCollection
import com.ougong.shop.Bean.*
import io.armcha.ribble.presentation.base_mvp.api.ApiContract

interface BrandContract {
    interface View : ApiContract.View{
        fun getDataSucess(bean : fuckNetbean<BrandBeanCollection.BrandBean>)
    }

    interface Presenter : ApiContract.Presenter<View>{

        fun getData()
    }
}