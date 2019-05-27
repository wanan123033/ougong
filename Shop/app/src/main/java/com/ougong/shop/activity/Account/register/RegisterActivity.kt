package com.ougong.shop.activity.Account.register

import android.os.CountDownTimer
import android.os.Handler
import android.support.v4.app.DialogFragment
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import com.baigui.commonlib.kotlinUtils.StarusBarUtils
import com.baigui.commonlib.kotlinUtils.StringUtils
import com.baigui.commonlib.utils.LogUtils
import com.baigui.commonlib.utils.ToastUtils
import com.baigui.commonview.ProgressBarDialog
import com.ougong.shop.R
import com.ougong.shop.base_mvp.base.BaseActivity
import kotlinx.android.synthetic.main.activity_register.*
import java.net.PasswordAuthentication

class RegisterActivity : BaseActivity<RegisterContract.View, RegisterContract.Presenter>(), RegisterContract.View {
    override fun checkNOSucess() {

        if (!send_check_no.isEnabled)
            return

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

    override fun CheckPhoneSucess() {
        //这里已经验证成功了！
        presenter.sendCheckNo(phone_num.text.toString())
    }

    var mProgressDialog: DialogFragment? = null
    override fun initView() {

        back.setOnClickListener { finish() }
        send_check_no.setOnClickListener {
            if (TextUtils.isEmpty(phone_num.text) || phone_num.text.toString().length != 11) {
                ToastUtils.toast(this, getString(R.string.no_phone_no))
                return@setOnClickListener
            }

            presenter.CheckPhoneNo(phone_num.text.toString())
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

        register_btn.setOnClickListener {
            if (TextUtils.isEmpty(checek_no.text)) {
                ToastUtils.toast(this, getString(R.string.no_check_no))
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(password.text)||password.text.length < 6 || password.text.length > 18) {
                ToastUtils.toast(this, "请输入6-18位密码")
                return@setOnClickListener
            }


            if (TextUtils.isEmpty(phone_num.text) || phone_num.text.length != 11) {
                ToastUtils.toast(this, getString(R.string.no_phone_no))
                return@setOnClickListener
            }

            if (!agree_licece.isChecked) {
                ToastUtils.toast(this, getString(R.string.agree_liecee))
                return@setOnClickListener
            }

            presenter.register(
                PasswordAuthentication(
                    phone_num.text.toString().trim(),
                    password.text.toString().trim().toCharArray()
                ), checek_no.text.toString()
            )
        }

        gotoLogin.setOnClickListener { finish() }

        gotoLogin2.setOnClickListener { finish() }
    }

    override fun initPresenter() = RegisterPresenter()

    override fun RegisterSucess() {
        finish()
    }

    override fun setContentViewSource() = R.layout.activity_register


    override fun showLoading() {
        mProgressDialog = ProgressBarDialog()
        mProgressDialog?.show(supportFragmentManager, "waiting")

    }

    override fun hideLoading() {
        mProgressDialog?.dismiss()
    }
}
