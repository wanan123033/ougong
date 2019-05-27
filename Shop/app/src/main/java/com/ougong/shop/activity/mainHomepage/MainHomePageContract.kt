package com.ougong.shop.activity.mainHomepage

import com.ougong.shop.Bean.*
import io.armcha.ribble.presentation.base_mvp.api.ApiContract
import java.net.PasswordAuthentication

interface MainHomePageContract{

    interface View : ApiContract.View{

        fun getAllListSucess(bean: fuckNetbean<CateryBean>)

        fun getProduceSucess1(bean: Netbean<fuckProcudtList<ProductListItem>>)
        fun getProduceSucess2(bean: Netbean<fuckProcudtList<ProductListItem>>)
    }

    interface Presenter : ApiContract.Presenter<View>{

        fun getAllList()

        fun getProduct1(id : Int)

        fun getProduct2(id : Int)
    }
}