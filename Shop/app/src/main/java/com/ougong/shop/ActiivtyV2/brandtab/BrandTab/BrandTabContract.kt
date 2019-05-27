package com.ougong.shop.ActiivtyV2.brandtab.BrandTab

import com.ougong.shop.ActiivtyV2.bean.BrandBeanCollection
import com.ougong.shop.Bean.fuckNetbean
import com.ougong.shop.Bean.fuckProcudtList
import io.armcha.ribble.presentation.base_mvp.api.ApiContract

interface BrandTabContract {

    interface View : ApiContract.View{
        fun getStyleSucess(bean : fuckNetbean<BrandBeanCollection.BrandStyleBean>)

        fun getBrandItemSucess(bean : fuckProcudtList<BrandBeanCollection.BrandItemBean>)
    }

    interface Presenter : ApiContract.Presenter<View>{

        fun getStyleList(id:Int?)

        fun getBrandItemList(brandId : Int? ,currentIndex : Int, StyleId: Int? = null)
    }
}