package com.ougong.shop.activity.Maininfo.mangeAdress

import android.text.TextUtils
import com.baigui.commonlib.utils.ToastUtils
import com.ougong.shop.App
import com.ougong.shop.base_mvp.api.ApiPresenter
import com.ougong.shop.httpmodule.ServiceFactory

class ManageAdressPresenter : ApiPresenter<ManageAdressContract.View>(),ManageAdressContract.Presenter {

    override fun deleteAdress(id : Int) {

        fetch(ServiceFactory.instance.userInfoApiService!!.delectAddressList(id)){
            if (it.status != 200){
                if(!TextUtils.isEmpty(it.message)) {
                    ToastUtils.toast(App.app, it.message)
                }
            }else{
                ToastUtils.toast(App.app,"删除成功")
                getAdressList()
            }
            view?.hideLoading()

        }
    }

    override fun updateAdress() {
        }
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

    override fun onRequestError(errorMessage: String?) {
        super.onRequestError(errorMessage)
        ToastUtils.toast(App.app,errorMessage)
    }
}