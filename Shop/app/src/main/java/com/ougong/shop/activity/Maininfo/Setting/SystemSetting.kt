package com.ougong.shop.activity.Maininfo.Setting

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.view.View
import com.baigui.commonlib.kotlinUtils.APKVersionCodeUtils
import com.baigui.commonlib.kotlinUtils.StarusBarUtils
import com.baigui.commonlib.utils.ToastUtils
import com.ougong.shop.AccountHelper
import com.ougong.shop.MainActivity
import com.ougong.shop.R
import com.ougong.shop.base_mvp.base.BaseActivity
import io.armcha.ribble.presentation.base_mvp.base.BaseContract
import io.armcha.ribble.presentation.base_mvp.base.BasePresenter
import kotlinx.android.synthetic.main.activity_system_setting.*
import kotlinx.android.synthetic.main.include_setting_back_title.*

class SystemSetting : BaseActivity<BaseContract.View, BasePresenter<BaseContract.View>>(){
    override fun setContentViewSource() = R.layout.activity_system_setting
    var presenter = object : BasePresenter<BaseContract.View>(){

    }
    override fun initPresenter()= object : BasePresenter<BaseContract.View>(){

    }

    override fun initView() {

        title_back.visibility = View.VISIBLE
        title_back.setOnClickListener { finish() }
        title_name.setText("系统设置")

        StarusBarUtils.setStatusBarColor(this, R.color.f5f5f5)
        StarusBarUtils.setAndroidNativeLightStatusBar(this, true)

        version.text = APKVersionCodeUtils.getVerName(this)

        quit_account.setOnClickListener {
            AccountHelper.logout()
            ToastUtils.toast(this,"退出成功")

            val intent = Intent(this, MainActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

    }


}
