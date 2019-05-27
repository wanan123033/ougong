package com.ougong.shop.activity.checkin

import android.text.TextUtils
import com.baigui.commonlib.utils.ToastUtils
import com.ougong.shop.App
import com.ougong.shop.base_mvp.api.ApiPresenter
import com.ougong.shop.httpmodule.ServiceFactory

class CheckInFanganFragPresenter : ApiPresenter<CheckInFanganFragmentContract.View>(), CheckInFanganFragmentContract.Presenter{
    override fun getProducts(
        brandId: Int?,
        categoryId: Int?,
        limit: Int?,
        maxPrice: Int?,
        minPrice: Int?,
        order: String?,
        page: Int?,
        sort: String?,
        styleId: Int?
    ) {
        fetch(ServiceFactory.instance.productApiservice!!.getProductByCheckLin(brandId,categoryId,limit,maxPrice,minPrice,order,page,sort,styleId)) {

            if (it.status != 200) {
                if (!TextUtils.isEmpty(it.message)) {
                    ToastUtils.toast(App.app, it.message)
                }
            } else {
                view?.initProducts(it)
            }
            view?.hideLoading()

        }
    }
}