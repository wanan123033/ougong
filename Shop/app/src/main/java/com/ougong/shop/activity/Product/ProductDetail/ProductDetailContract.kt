package com.ougong.shop.activity.Product.ProductDetail

import com.ougong.shop.Bean.User
import com.ougong.shop.Bean.fuckNetbean
import com.ougong.shop.Bean.fuckProcudtList
import com.ougong.shop.Bean.productDetailKeyValue
import io.armcha.ribble.presentation.base_mvp.api.ApiContract
import java.net.PasswordAuthentication

class ProductDetailContract {
    interface View : ApiContract.View{

        fun getDetailSucess(etbean: fuckProcudtList<productDetailKeyValue>)
    }

    interface Presenter : ApiContract.Presenter<View>{

        fun getDetails(id : Int)

    }
}