package com.ougong.shop.activity.Maininfo.EditInfo

import android.content.Intent
import android.net.Uri
import android.support.v4.app.DialogFragment
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.widget.Toast
import com.baigui.commonlib.kotlinUtils.StarusBarUtils
import com.baigui.commonlib.kotlinUtils.StringUtils
import com.baigui.commonlib.utils.LogUtils
import com.baigui.commonlib.utils.TimeUtils
import com.baigui.commonlib.utils.ToastUtils
import com.baigui.commonview.ProgressBarDialog
import com.bumptech.glide.Glide
import com.jzxiang.pickerview.TimePickerDialog
import com.jzxiang.pickerview.data.Type
import com.jzxiang.pickerview.listener.OnDateSetListener
import com.ougong.shop.AccountHelper
import com.ougong.shop.App
import com.ougong.shop.Bean.User
import com.ougong.shop.ConstString.REQUEST_CODE_LOCAL
import com.ougong.shop.R
import com.ougong.shop.activity.Maininfo.mangeAdress.ChooseAddress.ChoosePlace
import com.ougong.shop.base_mvp.base.BaseActivity
import com.ougong.shop.httpmodule.ServiceFactory
import io.armcha.ribble.presentation.utils.extensions.takeColor
import kotlinx.android.synthetic.main.activity_edit_info.*
import kotlinx.android.synthetic.main.include_setting_back_title.*
import java.io.File

class EditInfoActivity : BaseActivity<EditInfoContract.View, EditInfoContract.Presenter>(), EditInfoContract.View,
    OnDateSetListener {

    override fun uploadPicSicess(it: String) {

        Glide.with(this)
            .load(ServiceFactory.BASE_URL+it)
            .error(Glide.with(this).load(R.drawable.ice_default))
            .into(ice)

        mUser.imagePath = it
    }

    override fun onDateSet(timePickerView: TimePickerDialog?, millseconds: Long) {
        mUser.birthday = TimeUtils.DateTimeyyyy_mm_dd(millseconds)
        choose_birthdy.text = TimeUtils.DateTimeyyyy_mm_dd(millseconds)
    }

    var mUser = User()

    var mProgressDialog: DialogFragment? = null

    override fun showLoading() {
        mProgressDialog = ProgressBarDialog()
        mProgressDialog?.show(supportFragmentManager, "waiting")

    }

    override fun hideLoading() {
        mProgressDialog?.dismiss()
    }

    override fun setContentViewSource() = R.layout.activity_edit_info

//    override fun initPresenter() = EditInfoPresenter()

    override fun initPresenter() = EditInfoPresenter()

    override fun updataSucess() {
        ToastUtils.toast(this, "更新成功")
        finish()
    }

    fun selectPicFromLocal() {
        val intent: Intent
        intent = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_CODE_LOCAL)
    }

    override fun initView() {

        upload_pic.setOnClickListener {
            selectPicFromLocal()
        }
        StarusBarUtils.setStatusBarColor(this, R.color.f5f5f5)
        StarusBarUtils.setAndroidNativeLightStatusBar(this, true)

        title_name.setText("编辑个人信息")
        title_right.visibility = View.VISIBLE
        title_back.visibility = View.VISIBLE
        title_back.setOnClickListener { finish() }
        title_right.setText("保存")
        title_right.setOnClickListener {
            presenter.updataInfo(mUser)
        }

        if (AccountHelper.getUser().infoData == null) {
            secrect.isChecked = true
        } else if (AccountHelper.getUser().infoData?.normalData == null) {
            secrect.isChecked = true
        } else {
            when (AccountHelper.getUser().infoData!!.normalData!!.sex) {
                1 -> secrect.isChecked = true
                2 -> man.isChecked = true
                3 -> woman.isChecked = true
            }
        }
        name.text = AccountHelper.getUser().name ?: ""
        id.text = AccountHelper.getUser().id.toString()


        Glide.with(App.app!!)
            .load(AccountHelper.getUser().avatar?:"")
            .error(Glide.with(this).load(R.drawable.ice_default))
            .into(ice)

        name_edit.setText(AccountHelper.getUser().name ?: "")

        phone.text = AccountHelper.getUser().phone ?: ""

        if (AccountHelper.getUser().infoData != null) {

//            choose_birthdy.setText(AccountHelper.getUser().infoData!!.normalData!!.birthday)

        }
        choose_data_lin.setOnClickListener {

            val handerYears = 70L * 365 * 1000 * 60 * 60 * 24L
            val threYears = 30L * 365 * 1000 * 60 * 60 * 24L
            val mDialogYearMonthDay = TimePickerDialog.Builder()
                .setType(Type.YEAR_MONTH_DAY)
                .setCurrentMillseconds(System.currentTimeMillis() - threYears)
                .setCallBack(this)
                .setThemeColor(takeColor(R.color.white))
                .setDayText("日")
                .setTitleStringId("选择生日")
                .setMinMillseconds(System.currentTimeMillis() - handerYears)
                .setMaxMillseconds(System.currentTimeMillis())
                .setMonthText("月")
                .setYearText("年")
                .setWheelItemTextSize(15)
                .setWheelItemTextNormalColor(takeColor(R.color.j666))
                .setWheelItemTextSelectorColor(takeColor(R.color.j000))
                .build()
            mDialogYearMonthDay.show(supportFragmentManager, "------")
        }
        choose_citu_lin.setOnClickListener {
            //if (AccountHelper.getUser().infoData != null) {
            val inten = Intent(this, ChoosePlace::class.java)

            startActivityForResult(inten, 222)
            //}

        }
//
//        if (AccountHelper.getUser().birthday != null)
//            choose_birthdy.text = AccountHelper.getUser().birthday

        title_right.setOnClickListener {


            if (TextUtils.isEmpty(name_edit.text.toString())) {
                ToastUtils.toast(this, "请输入昵称")
                return@setOnClickListener
            }

            if (StringUtils.isSpecialChar(name_edit.text.toString())) {
                ToastUtils.toast(this, "昵称不能包含特殊字符")
                return@setOnClickListener
            }
            mUser.name = name_edit.text.toString()

            if (mUser.birthday == null) {
                ToastUtils.toast(this, "请选择生日")
                return@setOnClickListener
            }

            if (mUser.provinceId == -1) {
                ToastUtils.toast(this, "请选择城市")
                return@setOnClickListener
            }

//            if (mUser.imagePath == null) {
//                ToastUtils.toast(this, "请选择头像")
//                return@setOnClickListener
//            }

            LogUtils.e("----"+man.isChecked+"-------"+secrect.isChecked,this)
            when {
                man.isChecked -> mUser.sex = 2
                woman.isChecked -> mUser.sex = 3
                else -> mUser.sex = 1
            }

            mUser.detailAdress = note_content.text.toString()
            presenter.updataInfo(mUser)
        }
    }

    override fun initData() {
        if (AccountHelper.getUser() != null) {
//            presenter.updataInfo(AccountHelper.getUser()!!)
        }
//        presenter.uploadImage("")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            222 -> {
                if (resultCode == 223) {
                    if (data != null) {
                        mUser.provinceId = data!!.getIntExtra("data1", -1)
                        mUser.cityId = data!!.getIntExtra("data2", -1)
                        mUser.districtId = data!!.getIntExtra("data3", -1)

                        choose_city.text = data!!.getStringExtra("data4")

                    }
                }
            }
            REQUEST_CODE_LOCAL -> {

                if (data != null) {
                    val selectedImage = data.data
                    if (selectedImage != null) {
                        LogUtils.e(selectedImage.path, this)
                        if (getPath(selectedImage).equals(""))
                            return
                        presenter.uploadImage(getPath(selectedImage))
                    }
                }
            }
        }
    }

    private fun getPath(selectedImage: Uri): String {
        var picturePath: String
        var cursor = contentResolver.query(selectedImage, null, null, null, null)
        if (cursor != null) {
            cursor.moveToFirst()
            val columnIndex = cursor.getColumnIndex("_data")
            picturePath = cursor.getString(columnIndex)
            cursor.close()
            cursor = null

            if (picturePath == null || picturePath == "null") {
                val toast = Toast.makeText(this, "找不到图片", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
                return ""
            }
        } else {
            val file = File(selectedImage.path!!)
            if (!file.exists()) {
                val toast = Toast.makeText(this, "找不到图片", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
                return ""

            }
            picturePath = file.absolutePath
        }
        return picturePath
    }
}
