package com.ougong.shop.activity.Account.changePhone

import android.os.CountDownTimer
import android.text.TextUtils
import android.view.View
import com.baigui.commonlib.kotlinUtils.StarusBarUtils
import com.baigui.commonlib.utils.ToastUtils
import com.ougong.shop.App
import com.ougong.shop.R
import com.ougong.shop.activity.Account.AccountPresent
import com.ougong.shop.activity.Account.AccountPresentContract
import com.ougong.shop.base_mvp.base.BaseActivity
import kotlinx.android.synthetic.main.activity_change_phone.*
import kotlinx.android.synthetic.main.include_setting_back_title.*

class changePhone : BaseActivity<AccountPresentContract.View, AccountPresentContract.Presenter>(),
    AccountPresentContract.View {
    override fun Sucess() {
    }

    override fun CheckPhoneSucess(havePhoneRecord: Boolean) {

        if (havePhoneRecord) {
            ToastUtils.toast(App.app, "手机号已经注册")
        } else {
            presenter.sendCheckNo(new_phone_num.text.toString(),2)

        }

    }

    override fun sendCheckNoSucess() {

        send_check_no.isEnabled = false
        /** 倒计时60秒，一次1秒 */
        val timer = object : CountDownTimer(60 * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                // TODO Auto-generated method stub
                send_check_no.setText("" + millisUntilFinished / 1000 + "S")
            }

            override fun onFinish() {
                send_check_no.isEnabled = true
                send_check_no.setText("获取验证码")
            }
        }.start()

    }

    override fun ChangePhoneSucess() {
        ToastUtils.toast(this, "密码更新成功，请重新登录。")
    }

    override fun setContentViewSource() = R.layout.activity_change_phone
    override fun initPresenter() = AccountPresent()

    override fun initView() {
        super.initView()

        title_back.visibility = View.VISIBLE
        title_back.setOnClickListener { finish() }
        title_name.setText(R.string.changephone)

        StarusBarUtils.setStatusBarColor(this, R.color.f5f5f5)
        StarusBarUtils.setAndroidNativeLightStatusBar(this, true)

//        send_old_heck_no.setOnClickListener {
//
//            presenter.sendCheckNo(old_phone_num.text.toString())
//        }

        send_check_no.setOnClickListener {

            //            if (!StringUtils.isPhone(new_phone_num.text.toString())) {
//                ToastUtils.toast(this, "请输入正确的电话号码")
//            }

            if (TextUtils.isEmpty(new_phone_num.text.toString()) || new_phone_num.text.toString().length != 11) {
                ToastUtils.toast(this, "请输入正确的手机号码")
                return@setOnClickListener
            }

            presenter.CheckPhoneNo(new_phone_num.text.toString())
//            presenter.sendCheckNo(new_phone_num.text.toString(),2)

        }

        change_phone.setOnClickListener {
            if (TextUtils.isEmpty(password.text.toString())) {

                ToastUtils.toast(this, "请输入密码")
                return@setOnClickListener
            }

            if (TextUtils.isEmpty(new_phone_num.text.toString())) {

                ToastUtils.toast(this, "请输入手机号")
                return@setOnClickListener

            }

            if (TextUtils.isEmpty(new_phone_check_no.text.toString())) {

                ToastUtils.toast(this, "请输入验证码")
                return@setOnClickListener

            }
            presenter.changePhone(
                password.text.toString(),
                new_phone_num.text.toString(),
                new_phone_check_no.text.toString()
            )
        }

    }
}
