package com.ougong.shop.activity.checkin

import com.ougong.shop.Bean.CateryBean
import com.ougong.shop.Bean.Netbean
import com.ougong.shop.Bean.fuckNetbean
import com.ougong.shop.httpmodule.ProductCheckLinBean
import io.armcha.ribble.presentation.base_mvp.api.ApiContract

interface CheckInFanganFragmentContract {
    interface View : ApiContract.View{
        fun initProducts(bean: Netbean<ProductCheckLinBean>)
    }
    interface Presenter :ApiContract.Presenter<CheckInFanganFragmentContract.View>{
        fun getProducts(brandId:Int?,categoryId:Int?,limit:Int?,maxPrice:Int?,minPrice:Int?,
                        order:String?,page:Int?,sort:String?,styleId:Int?)
    }
}