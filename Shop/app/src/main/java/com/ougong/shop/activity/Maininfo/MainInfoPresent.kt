package com.ougong.shop.activity.Maininfo

import android.text.TextUtils
import com.baigui.commonlib.utils.ToastUtils
import com.ougong.shop.AccountHelper
import com.ougong.shop.App
import com.ougong.shop.base_mvp.api.ApiPresenter
import com.ougong.shop.httpmodule.ServiceFactory
import io.armcha.ribble.presentation.base_mvp.base.BasePresenter

class MainInfoPresent : ApiPresenter<InfoContract.View>(), InfoContract.Presenter {
    override fun refeshOrder() {
        fetch(ServiceFactory.instance.productApiservice!!.getOrderCount()) {
            if (it.status != 200) {
                if (!TextUtils.isEmpty(it.message)) {
                    ToastUtils.toast(App.app, it.message)
                }
            } else {

                AccountHelper.refeshOrder(it.data)
                view?.refeshOrderSucess()
            }

        }
    }

    /**
     * 随便找个接口就可以测试了
     */
    override fun checkLogin() {
//        fetch(ServiceFactory.instance.productApiservice!!.getShopCarList()) {
//            if (it.status != 200) {
////                if (it.status == 40005){
////                    view.GotoLogin()
////                }
//                if (!TextUtils.isEmpty(it.message)) {
//                    ToastUtils.toast(App.app, it.message)
//                }
//            } else {
//
//            }
//
//        }
    }

}