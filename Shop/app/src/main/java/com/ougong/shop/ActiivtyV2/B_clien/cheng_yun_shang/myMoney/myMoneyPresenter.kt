package com.ougong.shop.ActiivtyV2.B_clien.cheng_yun_shang.myMoney

import android.text.TextUtils
import com.baigui.commonlib.utils.LogUtils
import com.baigui.commonlib.utils.ToastUtils
import com.ougong.shop.App
import com.ougong.shop.base_mvp.api.ApiPresenter
import com.ougong.shop.httpmodule.ServiceFactory
import retrofit2.http.Query

class myMoneyPresenter : ApiPresenter<myMoneyContract.View>(), myMoneyContract.Presenter {

    override fun MoneyDeatail(endDate : String?, startDate : String?,
                             type : Int?) {

        fetch(ServiceFactory.instance.accountAPIService!!.MoneyDetail(endDate,startDate,type)) {

            if (it.status != 200) {
                if (!TextUtils.isEmpty(it.message)) {
                    ToastUtils.toast(App.app, it.message)
                }
            } else {
                view?.MoneyDeatailSucess(it.data)
            }
        }
    }

    override fun TakeOut(no : Int) {
        fetch(ServiceFactory.instance.accountAPIService!!.takeOut(no)) {
            if (it.status != 200) {
                if (!TextUtils.isEmpty(it.message)) {
                    ToastUtils.toast(App.app, "提现失败"+it.message)
                }
            } else {
                ToastUtils.toast(App.app, "提现成功")
                view?.TakeOutSucess()
            }
        }
    }

    override fun getMonyey() {
        fetch(ServiceFactory.instance.accountAPIService!!.getMoney()) {
            if (it.status != 200) {
                if (!TextUtils.isEmpty(it.message)) {
                    ToastUtils.toast(App.app, it.message)
                }
            } else {
                view?.getMonyeySucess(it.data)
            }
        }
    }


}