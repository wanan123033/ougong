package com.ougong.shop.activity.MainShopCarFragment

import android.text.TextUtils
import com.baigui.commonlib.utils.LogUtils
import com.baigui.commonlib.utils.ToastUtils
import com.ougong.shop.App
import com.ougong.shop.base_mvp.api.ApiPresenter
import com.ougong.shop.httpmodule.ServiceFactory
import okhttp3.MediaType
import okhttp3.RequestBody
import com.google.gson.Gson


class ShopCarPresenter : ApiPresenter<ShopCarContract.View>(), ShopCarContract.Presenter {
    /**
     * 这个东西不知道如何处理，这里只能按照他都成功修改数据就得了，因为假如按照回调的话，有可能前一个动作比后一个慢，
     * 数据改变就会出问题，这里默认所有的操作都成功，
     */
    override fun ChangeShopCarNO(No: Int, Id: Int) {
        fetch(ServiceFactory.instance.productApiservice!!.ChangeShopCarNO(No, Id)) {
            if (it.status != 200) {
                if (!TextUtils.isEmpty(it.message)) {
                    ToastUtils.toast(App.app, it.message)
                }
            } else {
//                view.getShopCarList(it.data)
//                               ToastUtils.toast(App.app, "获取成功")
            }
            view?.hideLoading()

        }
    }

    override fun DelectShopCar(array: ArrayList<Int>) {

        val gson = Gson()
        val postInfoStr = gson.toJson(array)

        LogUtils.e("------" + postInfoStr + array.size, this)
        val firstBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), postInfoStr)
        //请求服务器

        fetch(ServiceFactory.instance.productApiservice!!.DelectShopCarList(firstBody)) {
            if (it.status != 200) {
                if (!TextUtils.isEmpty(it.message)) {
                    ToastUtils.toast(App.app, it.message)
                }
            } else {
                //直接更新列表吧.这个方法也可以
                view?.DelectSucess()
                getShopCarList()
                ToastUtils.toast(App.app, "删除成功")
            }
            view?.hideLoading()

        }

    }

    override fun getShopCarList() {
        fetch(ServiceFactory.instance.productApiservice!!.getShopCarList()) {
            if (it.status != 200) {
                if (it.status == 40005) {
                    return@fetch
//                    view.GotoLogin()
                }
                if (!TextUtils.isEmpty(it.message)) {
                    ToastUtils.toast(App.app, it.message)
                }
            } else {

                view?.getShopCarList(it.data)
                //               ToastUtils.toast(App.app, "获取成功")
            }
            view?.hideLoading()

        }

    }


    override fun onRequestError(errorMessage: String?) {
        super.onRequestError(errorMessage)
//        ToastUtils.toast(App.app, errorMessage)
    }
}