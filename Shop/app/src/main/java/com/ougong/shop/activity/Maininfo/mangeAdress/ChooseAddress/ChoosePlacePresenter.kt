package com.ougong.shop.activity.Maininfo.mangeAdress.ChooseAddress

import android.text.TextUtils
import com.baigui.commonlib.utils.ToastUtils
import com.ougong.shop.App
import com.ougong.shop.base_mvp.api.ApiPresenter
import com.ougong.shop.httpmodule.ServiceFactory

class ChoosePlacePresenter : ApiPresenter<ChoosePlaceContract.view>(), ChoosePlaceContract.presenter {
    override fun getData() {

        fetch(
            ServiceFactory.instance.userInfoApiService!!.getAddressData()) {
            if (it.status != 200) {
                if (!TextUtils.isEmpty(it.message)) {
                    ToastUtils.toast(App.app, it.message)
                }
            } else {
                view?.GetDataSucess(it)
            }
            view?.hideLoading()

        }
    }

}