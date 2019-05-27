package com.ougong.shop.activity.checkin

import com.ougong.shop.ActiivtyV2.bean.BrandBeanCollection
import com.ougong.shop.Bean.Netbean
import com.ougong.shop.Bean.fuckNetbean
import com.ougong.shop.httpmodule.StyleBrandBean
import io.armcha.ribble.presentation.base_mvp.api.ApiContract

interface SxDialogContract {
    interface View:ApiContract.View{
        fun initStyleList(bean: fuckNetbean<BrandBeanCollection.BrandStyleBean>)
        fun initBrandList(bean: Netbean<StyleBrandBean>)

    }
}