package com.ougong.shop.activity.Account

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.baigui.commonlib.kotlinUtils.StarusBarUtils
import com.baigui.commonlib.utils.ToastUtils
import com.ougong.shop.R
import com.ougong.shop.base_mvp.base.BaseActivity
import kotlinx.android.synthetic.main.activity_change_password.*
import kotlinx.android.synthetic.main.include_setting_back_title.*

class ChangePassword : BaseActivity<AccountPresentContract.View, AccountPresentContract.Presenter>() ,
    AccountPresentContract.View {
    override fun Sucess() {

    }

    override fun ChangePasswordSucess() {
        ToastUtils.toast(this,"修改成功")
        finish()
    }
    override fun setContentViewSource() = R.layout.activity_change_password

    override fun initPresenter() = AccountPresent()

    override fun initView() {


        title_back.visibility = View.VISIBLE
        title_back.setOnClickListener { finish() }
        StarusBarUtils.setStatusBarColor(this, R.color.f5f5f5)
        StarusBarUtils.setAndroidNativeLightStatusBar(this, true)

        title_name.setText("修改密码")

        change_Password.setOnClickListener {
            if(TextUtils.isEmpty(old_password.text.toString())){
                ToastUtils.toast(this,"请输入旧密码")
                return@setOnClickListener
            }
//
//            if (TextUtils.isEmpty(new_password.text.toString())){
//                ToastUtils.toast(this,"请输入新密码")
//                return@setOnClickListener
//            }

            if (TextUtils.isEmpty(new_password.text.toString()) || new_password.text.length < 6 || new_password.text.length > 18){
                ToastUtils.toast(this,"请输入6-18位数字或字母的新密码")
                return@setOnClickListener
            }

            if (!new_password.text.toString().equals(new_password_1.text.toString())){
                ToastUtils.toast(this,"请再次输入新密码")
                return@setOnClickListener
            }
            presenter.changePassword(old_password.text.toString(),new_password.text.toString())
        }


    }
}
