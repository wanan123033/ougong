package com.ougong.shop.activity.combo

import android.text.TextUtils
import com.baigui.commonlib.utils.ToastUtils
import com.ougong.shop.App
import com.ougong.shop.base_mvp.api.ApiPresenter
import com.ougong.shop.httpmodule.ServiceFactory

class ComboStylePresenter : ApiPresenter<ComboStyleFragmentContract.View>(), ComboStyleFragmentContract.Presenter {
    override fun getComboData(styleId: Int?,page:Int,combotypeId:Int?) {
        fetch(ServiceFactory.instance.productApiservice!!.comboData(10,styleId,page,combotypeId)){
            if (it.status != 200){
                if(!TextUtils.isEmpty(it.message)) {
                    ToastUtils.toast(App.app, it.message)
                }
            }else{
                view?.initComboData(it)
            }
            view?.hideLoading()
        }
    }

    override fun getStyleData(id: Int?) {

            fetch(ServiceFactory.instance.productApiservice!!.comboStyleData(id)){
                if (it.status != 200){
                    if(!TextUtils.isEmpty(it.message)) {
                        ToastUtils.toast(App.app, it.message)
                    }
                }else{
                    view?.initStyleData(it)
                }
                view?.hideLoading()
            }

    }


}
