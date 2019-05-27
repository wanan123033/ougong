package com.ougong.shop.activity.Product.ChoosePropertoes

import com.ougong.shop.Bean.fuckProcudtList
import com.ougong.shop.Bean.productSpec
import io.armcha.ribble.presentation.base_mvp.api.ApiContract

class ChoosePropertoesContract {
    interface View : ApiContract.View{

        fun addSucess()
        fun getSucess(data: fuckProcudtList<productSpec>)
    }

    interface Presenter : ApiContract.Presenter<View>{
        fun getProper(int: Int)

        fun addShopCar(id : Int, count : Int)
    }

}