package com.ougong.shop

import android.text.TextUtils
import com.baigui.commonlib.utils.LogUtils
import com.baigui.commonlib.utils.ToastUtils
import com.baigui.commonview.CommonDialog
import com.ougong.shop.base_mvp.api.ApiPresenter
import com.ougong.shop.httpmodule.ServiceFactory
import io.armcha.ribble.domain.fetcher.result_listener.RequestType

class MainActivityPresenter : ApiPresenter<MainActivityContract.View>(), MainActivityContract.Presenter {

    override fun CheckVersion() {
        fetch(ServiceFactory.instance.mAPKVersionService!!.getVersion()) {
            if ( it.code!= 200) {
                if (!TextUtils.isEmpty(it.message)) {
                    ToastUtils.toast(App.app, it.message)
                }
            } else {
//                ShowUpGradeDialog(it)
                view?.UpGradeSucess(it.data!!)
            }
            view?.hideLoading()
        }

    }

    private var upgradeDialog: CommonDialog? = null

    private fun ShowUpGradeDialog() {
//        if (upgradeDialog == null) {
//            //deleteApk();
//            upgradeDialog = CommonDialog.Builder(view.)
//                .setResId(R.layout.dialog_upgrade)
//                .setShape(R.drawable.dialog_tra_shape)
//                .setTouchOutside(false)
//                .setAnimResId(0)
//                .setWidth(0.33f)
//                .setHeight(0.75f)
//                .build()
//            upgradeBinding = upgradeDialog.getBinding() as DialogUpgradeBinding
//            val content = baseBean.getData().getIntroduce()
//            var versionName = baseBean.getData().getVersionName()
//            if (!TextUtils.isEmpty(versionName)) {
//                if (versionName.contains("_")) {
//                    versionName = versionName.split("_".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()[0]
//                }
//                upgradeBinding.tvVersion.setText("V $versionName")
//            }
//            if (!TextUtils.isEmpty(content)) {
//                upgradeBinding.tvContent.setText(content)
//            }
//            if (baseBean.getData().isIsForced()) {
//                upgradeBinding.btnCancel.setVisibility(View.GONE)
//            } else {
//                upgradeBinding.btnCancel.setVisibility(View.VISIBLE)
//            }
//
//            upgradeBinding.btnCancel.setOnClickListener(View.OnClickListener { upgradeDialog.dismiss() })
//            upgradeBinding.btnOk.setOnClickListener(View.OnClickListener {
//                if (!NetUtils.isNetworkConnected()) {
//                    ToastUtils.toast(mContext.getResources().getString(R.string.str_not_net))
//                    return@OnClickListener
//                }
//                if (baseBean.getData().isIsForced()) {
//                    upgradeBinding.llOkCancel.setVisibility(View.GONE)
//                    upgradeBinding.llProgress.setVisibility(View.VISIBLE)
//                }
//
//                val permisstions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                if (PermissionUtis.checkPermissions(mContext, permisstions)) {
//                    PermissionUtis.requestPermissions(
//                        mContext as Activity,
//                        PermissionUtis.REQUESTCODEDOWNLOAD, permisstions
//                    )
//                } else {
//                    startDownload()
//                }
//            })
//            upgradeBinding.tvInstall.setOnClickListener(View.OnClickListener { downloadFile.install() })
//        }
//        upgradeDialog.show()
//    }
    }
    override fun onRequestError(requestType: RequestType, errorMessage: String?) {
        super.onRequestError(requestType, errorMessage)
        ToastUtils.toast(App.app,errorMessage)
    }
}