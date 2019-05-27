package com.ougong.shop.activity.MainShopCarFragment

import com.ougong.shop.Bean.FuckData
import io.armcha.ribble.presentation.base_mvp.api.ApiContract

class ShopCarContract {
    interface View : ApiContract.View{

        fun DelectSucess()
        fun getShopCarList(data: FuckData)
    }

    interface Presenter : ApiContract.Presenter<View>{
        fun getShopCarList()

        fun DelectShopCar(array: ArrayList<Int>)

        fun ChangeShopCarNO(No : Int, Id : Int)
    }
}