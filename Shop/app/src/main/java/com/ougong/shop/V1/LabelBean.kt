package com.ougong.shop.V1

import com.baigui.netlib.BeanBase

class LabelListBean : BeanBase(){
    open var list : List<ProdectBean>? = null

}

class LabelBean(){
    private var id: Int = 0
    private var title: String? = null
    private var imgUrl: String? = null
}
