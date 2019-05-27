package com.ougong.shop.activity.ProductList

import com.ougong.shop.Bean.Netbean
import com.ougong.shop.Bean.ProductListItem
import com.ougong.shop.Bean.fuckProcudtList
import io.armcha.ribble.presentation.base_mvp.api.ApiContract
import java.nio.ByteOrder

interface ProductBottomContract {

    interface View : ApiContract.View{

        fun getProductSucess(it: Netbean<fuckProcudtList<ProductListItem>>)
    }

    interface Presenter : ApiContract.Presenter<View>{
        fun getProduct(id: Int,page: Int = 0, order: String = "asc",sort : String = "view_count")
    }

}