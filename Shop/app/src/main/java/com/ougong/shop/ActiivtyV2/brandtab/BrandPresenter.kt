package com.ougong.shop.ActiivtyV2.brandtab

import android.text.TextUtils
import com.baigui.commonlib.utils.ToastUtils
import com.ougong.shop.App
import com.ougong.shop.base_mvp.api.ApiPresenter
import com.ougong.shop.httpmodule.ServiceFactory

class BrandPresenter : ApiPresenter<BrandContract.View>(), BrandContract.Presenter {
    override fun getData() {
        fetch(ServiceFactory.instance.productApiservice!!.getBrandList()){
            if (it.status != 200){
                if(!TextUtils.isEmpty(it.message)) {
                    ToastUtils.toast(App.app, it.message)
                }
            }else{
                view?.getDataSucess(it)
                //              ToastUtils.toast(App.app,"获取成功")
            }
            view?.hideLoading()

        }
    }

    override fun onRequestError(errorMessage: String?) {
        super.onRequestError(errorMessage)
//        ToastUtils.toast(App.app,errorMessage)
    }
}