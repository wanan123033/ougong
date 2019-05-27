package com.ougong.shop.activity.Maininfo

import android.content.Intent
import android.text.TextUtils
import android.view.View
import com.baigui.commonlib.kotlinUtils.StarusBarUtils.setAndroidNativeLightStatusBar
import com.baigui.commonlib.kotlinUtils.StarusBarUtils.setStatusBarColor
import com.bumptech.glide.Glide
import com.ougong.shop.AccountHelper
import com.ougong.shop.base_mvp.base.BaseFragment
import com.ougong.shop.R
import com.ougong.shop.activity.Account.ChangePassword
import com.ougong.shop.activity.Account.login.LogInActivity
import com.ougong.shop.activity.Account.changePhone.changePhone
import com.ougong.shop.App
import com.ougong.shop.ConstString
import com.ougong.shop.activity.Maininfo.EditInfo.EditInfoActivity
import com.ougong.shop.activity.Maininfo.Setting.SystemSetting
import com.ougong.shop.activity.Maininfo.mangeAdress.MangeAdress
import com.ougong.shop.activity.orderhistory.OrderMenuFragment
import io.armcha.ribble.presentation.utils.extensions.start
import kotlinx.android.synthetic.main.fragment_my_info.*

class InfoFragment : BaseFragment<InfoContract.View,InfoContract.Presenter>(),InfoContract.View {
    override fun refeshOrderSucess() {
        initUserInfo()
    }

    override fun initPresenter() = MainInfoPresent()

    override fun setContentViewSource() = R.layout.fragment_my_info

    override fun initView() {

        if (!AccountHelper.isLogin){
            GotoLogin()
        }
        if (!TextUtils.isEmpty(AccountHelper.getUser().avatar)){
            Glide.with(App.app!!)
                .load(AccountHelper.getUser().avatar)
                .error(Glide.with(this).load(R.drawable.ice_default))
                .into(ice)
        }
        setStatusBarColor(activity,R.color.black)
        setAndroidNativeLightStatusBar(activity,false)

        presenter.checkLogin()
        enter.setOnClickListener {
            activity.start<EditInfoActivity>()
        }

        initUserInfo()

//        if (AccountHelper.getUser().imagePath)
        llSettingPlace.setOnClickListener{
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
        }else{
            dot_item_1.visibility = View.GONE
        }
        if (AccountHelper.globalDataTemp.orderhistoryPayedCount != 0) {
            dot_item_2.visibility = View.VISIBLE
            dot_item_2.text = AccountHelper.globalDataTemp.orderhistoryPayedCount.toString()
        }else{
            dot_item_2.visibility = View.GONE
        }

        if (AccountHelper.globalDataTemp.orderHistorycount != 0) {

            dot_item_3.visibility = View.VISIBLE
            dot_item_3.text = AccountHelper.globalDataTemp.orderHistorycount.toString()

        } else{
          dot_item_3.visibility = View.GONE
        }
    }

    override fun onPause() {
        super.onPause()
        presenter.checkLogin()
    }


}