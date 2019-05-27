package com.ougong.shop.ActiivtyV2.brandtab.BrandActivity

import com.ougong.shop.ActiivtyV2.bean.BrandDetailBean
import com.ougong.shop.ActiivtyV2.bean.BrandDetailTagBean
import com.ougong.shop.Bean.Netbean
import com.ougong.shop.Bean.ProductListItem
import com.ougong.shop.Bean.fuckNetbean
import com.ougong.shop.Bean.fuckProcudtList
import io.armcha.ribble.presentation.base_mvp.api.ApiContract

interface BrandContract {

    interface View : ApiContract.View{
        fun getBrandDetailSucess(bean : BrandDetailBean)

        fun getBrandTagSucess(bean: fuckNetbean<BrandDetailTagBean>)

        fun getProductListSucess(it: Netbean<fuckProcudtList<ProductListItem>>)
    }

    interface Presenter : ApiContract.Presenter<View>{

        fun getBrandDetail(id:Int)

        fun getBrandTagList(brandId : Int)

        fun getProductList(brandId: Int?, category: Int? = null, page: Int = 10, order: String? = null, sort: String? = null)
    }
}