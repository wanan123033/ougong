package com.ougong.shop.activity.Product

import com.ougong.shop.Bean.*
import io.armcha.ribble.presentation.base_mvp.api.ApiContract

interface ProductContract {

    interface View : ApiContract.View{

        fun getProductSucess(it: Netbean<ProductDetails>)
        fun addSucess() {}

        fun getProperSucess(data: fuckProcudtList<productSpec>)
        fun getDetailSucess(data: fuckProcudtList<productDetailKeyValue>)
    }

    interface Presenter : ApiContract.Presenter<View>{
        fun getProper(int: Int)
        fun getProductDetails(id: Int)
        fun addShopCar(id : Int, count : Int)
        fun getDetails(id : Int){}
    }

}