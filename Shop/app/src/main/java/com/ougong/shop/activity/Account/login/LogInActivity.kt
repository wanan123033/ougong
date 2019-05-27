package com.ougong.shop.activity.Account.login

import android.support.v4.app.DialogFragment
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import com.baigui.commonlib.utils.LogUtils
import com.baigui.commonlib.utils.ToastUtils
import com.baigui.commonview.ProgressBarDialog
import com.ougong.shop.AccountHelper
import com.ougong.shop.Bean.User
import com.ougong.shop.ConstString
import com.ougong.shop.R
import com.ougong.shop.activity.Account.ForgetPassword
import com.ougong.shop.activity.Account.register.RegisterActivity
import com.ougong.shop.base_mvp.base.BaseActivity
import io.armcha.ribble.presentation.utils.extensions.start
import kotlinx.android.synthetic.main.activity_log_in.*
import java.net.PasswordAuthentication

/**
 * 登录分为两步，1. 登录。2 更新用户信息，
 */

class LogInActivity : BaseActivity<LogInContract.View, LogInContract.Presenter>(), LogInContract.View {
    override fun refeshUserInfosucess(data: User) {
        AccountHelper.updateUserCache(data)
        ToastUtils.toast(this, "登录成功")
        setResult(ConstString.LOG_IN_RESULT_OK)
        /**
         * 这里更新所有内容，
         */
        AccountHelper.synchronizationUser()
        finish()

    }

    /**\
     * 登录根本什么也不用做，只是获取了一个token。
     */
    override fun loginSucess(data: User) {
//        AccountHelper.updateUserInfo(data)
        presenter.RefeshInfo()
    }


    override fun startAuth() {}

    var progressDialog: DialogFragment? = null
    override fun showLoading() {
        progressDialog = ProgressBarDialog()
        progressDialog?.show(supportFragmentManager, "progress")
    }

    override fun onBackPressed() {
        setResult(ConstString.LOG_IN_RESULT_NO)
        super.onBackPressed()
    }

    override fun hideLoading() {
        progressDialog?.dismiss()
    }

    override fun back() {

    }

    val mPresenter = LogInPresenter()

    override fun initPresenter() = mPresenter

    override fun setContentViewSource() = R.layout.activity_log_in

    override fun initView() {


        register.setOnClickListener {
            start<RegisterActivity>()
        }

        redister2.setOnClickListener {
            start<RegisterActivity>()
        }

        forgetPassword.setOnClickListener {
            start<ForgetPassword>()
        }

        back.setOnClickListener {
            setResult(ConstString.LOG_IN_RESULT_NO)
            finish()
        }

        phone_num.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
                LogUtils.e(charSequence.toString() + "---" + start + "-----" + before + "------" + count, this)
                LogUtils.e("" + charSequence?.length, this)
                charSequence?.let {
                    if (!charSequence.isNotEmpty())
                        return
                    if (!charSequence!!.startsWith('1', false)!!) {
                        phone_num.setText("")
                    }
                }
            }

        })

        login_btn.setOnClickListener {

            if (TextUtils.isEmpty(password.text)) {
                ToastUtils.toast(this, "请输入密码")
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(phone_num.text)) {
                ToastUtils.toast(this, getString(R.string.no_username))
                return@setOnClickListener
            }

//            if (!StringUtils.isPhone(phone_num.text.toString())) {
            if (phone_num.text.toString().length != 11) {
                ToastUtils.toast(this, "请输入正确的电话号码")
                return@setOnClickListener
            }

            mPresenter.makeLogin(
                PasswordAuthentication(
                    phone_num.text.toString().trim(),
                    password.text.toString().trim().toCharArray()
                )
            )
        }
    }

}
