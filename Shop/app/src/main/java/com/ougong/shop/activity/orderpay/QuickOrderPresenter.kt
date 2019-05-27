package com.ougong.shop.activity.orderpay

import android.text.TextUtils
import com.baigui.commonlib.utils.ToastUtils
import com.ougong.shop.App
import com.ougong.shop.base_mvp.api.ApiPresenter
import com.ougong.shop.httpmodule.ServiceFactory

class QuickOrderPresenter : ApiPresenter<QuickOrderContract.View>() , QuickOrderContract.Presenter{
    override fun getAdressList() {
        fetch(ServiceFactory.instance.userInfoApiService!!.getAddressList()){
            if (it.status != 200){
                if(!TextUtils.isEmpty(it.message)) {
                    ToastUtils.toast(App.app, it.message)
                }
            }else{
                view?.getAdressSucess(it)
            }
            view?.hideLoading()

        }
    }
    override fun sendOrder(id: String, quick: Boolean) {
        fetch(ServiceFactory.instance.productApiservice!!.SendOrder(id, quick)) {
            if (it.status != 200) {
                if (!TextUtils.isEmpty(it.message)) {
                    ToastUtils.toast(App.app, it.message)
                }
            } else {
                view?.getOrderSucess(it.data)
                //            ToastUtils.toast(App.app, "获取成功")
            }
            view?.hideLoading()

        }
    }
    override fun buyIt(orderType:Int,addressId: Int, cardInfo: String?,comboData:String?, deliveryType: Int, payType: Int, remark: String?, quick: Boolean?,logisticsCompany:String?,ownLogisticsName:String?,ownLogisticsPhone:String?) {

        fetch(ServiceFactory.instance.productApiservice!!.BuyIt(orderType,addressId,cardInfo ,comboData,clientType = 2 , deliveryType = deliveryType,payType = payType,
            quick = quick,
            remark = remark,
            logisticsCompany = logisticsCompany,
            ownLogisticsName = ownLogisticsName,
            ownLogisticsPhone = ownLogisticsPhone
        )) {
            if (it.status != 200) {
                if (!TextUtils.isEmpty(it.message)) {
                    ToastUtils.toast(App.app, it.message)
                }
            } else {
                view?.payPreSucess(it.data)
                //              ToastUtils.toast(App.app, "获取成功")
            }
            view?.hideLoading()
        }
    }

}