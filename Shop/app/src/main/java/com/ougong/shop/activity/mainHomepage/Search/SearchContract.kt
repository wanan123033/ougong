package com.ougong.shop.activity.mainHomepage.Search

import com.ougong.shop.Bean.*
import io.armcha.ribble.presentation.base_mvp.api.ApiContract

//这里用的同一个接口不过懒得挑了，复制过来也挺好
class SearchContract {
    interface View : ApiContract.View{

        fun getProductSucess(it: Netbean<fuckProcudtList<ProductListItem>>)
    }

    interface Presenter : ApiContract.Presenter<View>{
        fun getProduct(searchWord : String,page: Int = 0, order: String = "asc",sort : String = "view_count")
    }
}