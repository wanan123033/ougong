package com.ougong.shop.ActiivtyV2.B_clien.decoration_factory.mydesinger

import android.text.TextUtils
import com.baigui.commonlib.utils.ToastUtils
import com.ougong.shop.App
import com.ougong.shop.base_mvp.api.ApiPresenter
import com.ougong.shop.httpmodule.ServiceFactory

class MyDesingerPresenter : ApiPresenter<MyDesingerContract.View>(), MyDesingerContract.Presenter {
    override fun getDesingerList(mShowRole: Int) {

        fetch(ServiceFactory.instance.accountAPIService!!.getSubAccountLidt(mShowRole)) {
            if (it.status != 200) {
                if (!TextUtils.isEmpty(it.message)) {
                    ToastUtils.toast(App.app, it.message)
                }
            } else {
                view?.getDesingerListSucess(it.data)
            }
        }

    }


}