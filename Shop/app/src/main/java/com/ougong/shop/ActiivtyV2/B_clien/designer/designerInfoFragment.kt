package com.ougong.shop.ActiivtyV2.B_clien.designer

import android.content.Intent
import android.view.View
import com.baigui.commonlib.kotlinUtils.StarusBarUtils
import com.ougong.shop.AccountHelper
//import com.ougong.shop.ActiivtyV2.B_clien.vip_designer.EditInfo.EditInfoActivity
import com.ougong.shop.R
import com.ougong.shop.activity.Account.ChangePassword
import com.ougong.shop.activity.Account.login.LogInActivity
import com.ougong.shop.activity.Account.changePhone.changePhone
import com.ougong.shop.activity.Maininfo.EditInfo.EditInfoActivity
import com.ougong.shop.activity.Maininfo.InfoContract
import com.ougong.shop.activity.Maininfo.MainInfoPresent
import com.ougong.shop.activity.Maininfo.Setting.SystemSetting
import com.ougong.shop.activity.Maininfo.mangeAdress.MangeAdress
import com.ougong.shop.activity.orderhistory.OrderMenuFragment
import com.ougong.shop.base_mvp.base.BaseFragment
import io.armcha.ribble.presentation.utils.extensions.start
import kotlinx.android.synthetic.main.designer_info_fragment.*

class designerInfoFragment : BaseFragment<InfoContract.View, InfoContract.Presenter>() ,InfoContract.View{

    override fun refeshOrderSucess() {
        initUserInfo()
    }

    override fun onPause() {
        super.onPause()
        presenter.refeshOrder()
    }

    override fun setContentViewSource() = R.layout.designer_info_fragment

    override fun initPresenter() = MainInfoPresent()

    override fun initView() {

        if (!AccountHelper.isLogin) {
            GotoLogin()
        }
        StarusBarUtils.setStatusBarColor(activity, R.color.black)
        StarusBarUtils.setAndroidNativeLightStatusBar(activity, false)
        presenter.checkLogin()
        useinfo_pic.setBackgroundResource(R.drawable.desiger_ice)

        enter.setOnClickListener {
            activity.start<EditInfoActivity>()
        }

        initUserInfo()

//        if (AccountHelper.getUser().imagePath)
        llSettingPlace.setOnClickListener {
            activity.start<MangeAdress>()

//            var p = ProgressBarDialog()
//            p.show(childFragmentManager,"fuck")
        }

        llSettingPhone.setOnClickListener {
            activity.start<changePhone>()
        }

        llSettingPassword.setOnClickListener {
            activity.start<ChangePassword>()
        }


        llSettingSetting.setOnClickListener {
            activity.start<SystemSetting>()
        }

        //加载订单菜单
        val fragmentManager = childFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.frame_order_menu, OrderMenuFragment())
        transaction.commit()

    }

    private fun initUserInfo() {
        username.text = AccountHelper.getUser().name
        if (AccountHelper.globalDataTemp.orderHistorynotpayCount != 0) {
            dot_item_1.visibility = View.VISIBLE
            dot_item_1.text = AccountHelper.globalDataTemp.orderHistorynotpayCount.toString()
        } else {
            dot_item_1.visibility = View.GONE
        }
        if (AccountHelper.globalDataTemp.orderhistoryPayedCount != 0) {
            dot_item_2.visibility = View.VISIBLE
            dot_item_2.text = AccountHelper.globalDataTemp.orderhistoryPayedCount.toString()
        } else {
            dot_item_2.visibility = View.GONE
        }

        if (AccountHelper.globalDataTemp.orderHistorycount != 0) {

            dot_item_3.visibility = View.VISIBLE
            dot_item_3.text = AccountHelper.globalDataTemp.orderHistorycount.toString()

        } else {
            dot_item_3.visibility = View.GONE
        }
    }


}