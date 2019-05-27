package com.ougong.shop.activity.Account

import android.os.CountDownTimer
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.TextureView
import android.view.View
import com.baigui.commonlib.kotlinUtils.StarusBarUtils
import com.baigui.commonlib.kotlinUtils.StringUtils
import com.baigui.commonlib.utils.LogUtils
import com.baigui.commonlib.utils.ToastUtils
import com.ougong.shop.App
import com.ougong.shop.R
import com.ougong.shop.base_mvp.base.BaseActivity
import com.ougong.shop.base_mvp.base.BaseFragment
import kotlinx.android.synthetic.main.activity_forgetpassword.*
import kotlinx.android.synthetic.main.include_setting_back_title.*

class ForgetPassword : BaseActivity<AccountPresentContract.View, AccountPresentContract.Presenter>(),
    AccountPresentContract.View {
    override fun Sucess() {
        ToastUtils.toast(this, "更新密码成功")
        finish()
    }

    override fun CheckPhoneSucess(havePhone : Boolean) {

        if (havePhone) {
            presenter.sendCheckNo(phone_num.text.toString(), 1)
        }else
            ToastUtils.toast(App.app, "手机号未注册")
        }
    override fun setContentViewSource() = R.layout.activity_forgetpassword

    override fun initPresenter() = AccountPresent()

    override fun sendCheckNoSucess() {
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

    override fun initView() {

        StarusBarUtils.setStatusBarColor(this, R.color.f5f5f5)
        StarusBarUtils.setAndroidNativeLightStatusBar(this, true)

        title_name.text = "忘记密码"
        title_back.visibility = View.VISIBLE
        title_back.setOnClickListener { finish() }
        change_Password.setOnClickListener {

            if (TextUtils.isEmpty(phone_num.text.toString()) || phone_num.text.toString().length != 11) {
                ToastUtils.toast(this, "请输入正确手机号码")
                return@setOnClickListener
            }

            if (TextUtils.isEmpty(phone_check_no.text.toString())) {
                ToastUtils.toast(this, "请输入验证码")
                return@setOnClickListener
            }

            if (TextUtils.isEmpty(new_password.text.toString()) || new_password.text.toString().length < 6 || new_password.text.toString().length > 20) {
                ToastUtils.toast(this, "请输入6-18位数字或字母的新密码")

                return@setOnClickListener
            }

            if (TextUtils.isEmpty(new_password_1.text.toString())) {
                ToastUtils.toast(this, "请再次输入新密码")
                return@setOnClickListener
            }

            if (!TextUtils.equals(new_password.text.toString(), new_password_1.text.toString())) {
                ToastUtils.toast(this, "输入的两次密码不一致")

                return@setOnClickListener
            }

            presenter.forgetPassword(
                phone_num.text.toString(),
                phone_check_no.text.toString(),
                new_password.text.toString()
            )
        }

        phone_num.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) { }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {

                charSequence?.let {
                    if (!charSequence.isNotEmpty())
                        return
                    if (!charSequence!!.startsWith('1', false)!!) {
                        phone_num.setText("")
                    }
                }
            }

        })

        send_check_no.setOnClickListener {
            if (TextUtils.isEmpty(phone_num.text.toString())|| phone_num.text.toString().length != 11) {
                ToastUtils.toast(this, "请输入正确的手机号码")
                return@setOnClickListener
            }
            presenter.CheckPhoneNo(phone_num.text.toString())
//            presenter.sendCheckNo(phone_num.text.toString(),1)
        }
    }
}