package com.ougong.shop.activity.mainHomepage.Search

import android.text.TextUtils
import com.baigui.commonlib.utils.LogUtils
import com.baigui.commonlib.utils.ToastUtils
import com.ougong.shop.App
import com.ougong.shop.base_mvp.api.ApiPresenter
import com.ougong.shop.httpmodule.ServiceFactory

class SearchPresenter : ApiPresenter<SearchContract.View>(), SearchContract.Presenter {
    override fun getProduct(searchword : String, page: Int, order: String, sort: String) {

        LogUtils.e(page.toString() + "------------", this)
        fetch(
            ServiceFactory.instance.productApiservice!!.getProduct(
                page = page,
                order = order,
                search = searchword,
                sort = sort
            )
        ) {
            if (it.status != 200) {
                if (!TextUtils.isEmpty(it.message)) {
                    ToastUtils.toast(App.app, it.message)
                }
            } else {
                view?.getProductSucess(it)
   //             ToastUtils.toast(App.app, "获取成功")
            }
            view?.hideLoading()

        }


    }


    override fun onRequestError(errorMessage: String?) {
        super.onRequestError(errorMessage)
        ToastUtils.toast(App.app, errorMessage)
    }
}