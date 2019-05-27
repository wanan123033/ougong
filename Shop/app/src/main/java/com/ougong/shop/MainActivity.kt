package com.ougong.shop

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Handler
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import com.baigui.commonlib.kotlinUtils.APKVersionCodeUtils
import com.baigui.commonlib.kotlinUtils.extensions.isPermissionGranted
import com.baigui.commonlib.kotlinUtils.extensions.requestPermission
import com.baigui.commonlib.kotlinUtils.extensions.shouldShowPermissionRationale
import com.baigui.commonlib.utils.LogUtils
import com.baigui.commonlib.utils.NetUtils
import com.baigui.commonlib.utils.ToastUtils
import com.baigui.commonview.CommonDialog
import com.ougong.shop.ActiivtyV2.B_clien.cheng_yun_shang.ChengYunShangInfoFragment
import com.ougong.shop.ActiivtyV2.B_clien.decoration_factory.decorationFactoryInfoFragment
import com.ougong.shop.ActiivtyV2.B_clien.designer.designerInfoFragment
import com.ougong.shop.ActiivtyV2.B_clien.vip_designer.vipDesignerInfoFragment
import com.ougong.shop.ActiivtyV2.brandtab.BrandFragment
import com.ougong.shop.Bean.Netbean
import com.ougong.shop.activity.MainLabeFrgment.LabelFragmentV2
import com.ougong.shop.activity.MainShopCarFragment.ShopCarFragment
import com.ougong.shop.activity.Maininfo.InfoFragment
import com.ougong.shop.activity.mainHomepage.homePageFragmentV2
import com.ougong.shop.base_mvp.base.BaseActivity
import com.ougong.shop.download.DownloadFile
import com.ougong.shop.httpmodule.CheckVersion
import com.ougong.shop.httpmodule.OrderHistoryJavaBean
import com.ougong.shop.httpmodule.ServiceFactory.Companion.OTHER_URL
import com.tencent.bugly.crashreport.CrashReport
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_upgrade.*
import kotlinx.android.synthetic.main.dialog_upgrade.view.*
import kotlinx.android.synthetic.main.tab_item.view.*


class MainActivity : BaseActivity<MainActivityContract.View, MainActivityContract.Presenter>(),
    MainActivityContract.View, TabLayout.OnTabSelectedListener {

    override fun UpGradeSucess(data: CheckVersion) {
        val version = APKVersionCodeUtils.getVersionCode(this)
        if (data.versionCode > version) {
            showUpgradeDialog(data)
        }
    }


    private var upgradeDialog: CommonDialog? = null

    var uri: String? = null
    private fun showUpgradeDialog(checkVersiondata: CheckVersion) {
        if (upgradeDialog == null) {
            //deleteApk();


            LogUtils.e("-------------", this)
            upgradeDialog = CommonDialog.Builder(this)
                .setResId(R.layout.dialog_upgrade)
                .setTouchOutside(false)
                .setWidth(0.8f)
                .setHeight(0.5f)
                .build()

            val dialogView = upgradeDialog!!.getView()
            if (!TextUtils.isEmpty(checkVersiondata.introduce)) {
                upgradeDialog!!.dialog.content.text = checkVersiondata.introduce
            }

            if (checkVersiondata.isForced) {
                dialogView.cancleBtn.setVisibility(View.GONE)
            } else {
                dialogView.cancleBtn.setVisibility(View.VISIBLE)
            }
            dialogView.versin_name.text = checkVersiondata.versionName
            dialogView.cancleBtn.setOnClickListener(View.OnClickListener { upgradeDialog!!.dismiss() })
            dialogView.confirmBtn.setOnClickListener(View.OnClickListener {
                if (!NetUtils.isNetworkConnected(this)) {
                    ToastUtils.toast(this, getString(R.string.str_not_net))
                    return@OnClickListener
                }

                if (!isPermissionGranted(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    //拒绝过，这次需要一个dialog来说明一下，给我们权限，但是这里就可以不要了、
                    if (shouldShowPermissionRationale(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        ToastUtils.toast(this, "版本进行更新,需要下载新的安装包")
                        Handler().postDelayed({
                            requestPermission(arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 3)
                        }, 1000)
                    } else {
                        requestPermission(arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 3)
                    }
                    uri = OTHER_URL + checkVersiondata.uri
                } else {
                    startDownload(OTHER_URL + checkVersiondata.uri)
                }
            })
//            upgradeDialog!!.getView().tv_install.setOnClickListener(View.OnClickListener { downloadFile?.install() })
        }
        upgradeDialog!!.show()
    }


    /**
     * 开始执行下载
     */
    fun startDownload(uir: String) {

        var dialogView = upgradeDialog!!.getView()

        dialogView.progressBar.setVisibility(View.VISIBLE)
        dialogView.download_text.visibility = View.VISIBLE
        dialogView.confirmBtn.visibility = View.GONE
        dialogView.cancleBtn.visibility = View.GONE
        downloadApk(uir)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 3) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //重新获取权限成功
                startDownload(uri!!)
            } else {
                ToastUtils.toast(this, "无法获取存储权限，请手动打开存储权限!")
            }
        }

    }

    /**
     * 下载APK
     *
     * @param baseBean
     */
    var downloadFile: DownloadFile? = null

    private fun downloadApk(uri: String) {
        downloadFile = object : DownloadFile(this) {
            override fun onProgress(fraction: Float) {

//                if (baseBean.getData().isIsForced()) {
                if (fraction <= 1) {
                    val progress = (fraction * 100).toInt()
                    upgradeDialog!!.getView().progressBar.setProgress(progress)
//                        upgradeDialog!!.getView().tv_progress.setText("$progress%")
                }
//                }
            }

            override fun onFinish() {
//                upgradeDialog!!.getView().tv_install.setVisibility(View.VISIBLE)
                upgradeDialog!!.getView().progressBar.setProgress(100)
//                upgradeDialog!!.getView().tv_progress.setText("100%")
//                upgradeDialog?.dismiss()
                downloadFile?.install()

            }
        }
        downloadFile?.start(uri)
        //downloadFile.start("http://192.168.1.109:8080/examples/test.apk");
    }

    override fun initPresenter() = MainActivityPresenter()

    override fun setContentViewSource() = R.layout.activity_main

    override fun initView() {
//        CrashReport.testJavaCrash()
        presenter.CheckVersion()
        bottom_tab_layout.addOnTabSelectedListener(this)

        for (i in 0..4) {
            bottom_tab_layout.addTab(bottom_tab_layout.newTab().setCustomView(getTabView(i)))
        }
    }


    fun getTabView(index: Int): View {
        val v = LayoutInflater.from(this).inflate(R.layout.tab_item, null)
        when (index) {
            0 -> {
                v.imgView.setImageResource(R.drawable.homepage_drawable)
                v.textView.setText(R.string.home_page)
            }
            1 -> {
                v.imgView.setImageResource(R.drawable.catery_drawable)
                v.textView.setText("分类")
            }
            2 -> {
                v.imgView.setImageResource(R.drawable.brand_drawable)
                v.textView.setText("品牌")
            }
            3 -> {
                v.imgView.setImageResource(R.drawable.shopcar_drawable)
                v.textView.setText("购物车")
            }
            4 -> {
                v.imgView.setImageResource(R.drawable.info_drawable)
                v.textView.setText("我的")
            }

        }
        return v
    }

    override fun onTabReselected(p0: TabLayout.Tab?) {
//        LogUtils.v("onTabReselected",this)
    }

    override fun onTabUnselected(p0: TabLayout.Tab?) {
        //       LogUtils.v("onTabUnselected",this)
    }

    private var mHomePageFragment: Fragment? = null
    private var mLabelFragment: Fragment? = null
    private var mBrandFragment: Fragment? = null
    private var mShopCarFragment: Fragment? = null
    private var mInfoFragment: Fragment? = null
    private var mCurrentFrgment: Fragment? = null
    override fun onTabSelected(p0: TabLayout.Tab?) {

        //LogUtils.e(""+bottom_tab_layout.selectedTabPosition,this)
        when (bottom_tab_layout.selectedTabPosition) {
            0 -> {
                mHomePageFragment = doSelect(mHomePageFragment, 0, { homePageFragmentV2() })
            }
            1 -> {
                mLabelFragment = doSelect(mLabelFragment, 1, { LabelFragmentV2() })
            }
            2 -> {
                mBrandFragment = doSelect(mBrandFragment, 2, { BrandFragment() })
            }
            3 -> {
                mShopCarFragment = doSelect(mShopCarFragment, 3, { ShopCarFragment() })
            }
            4 -> {
                mInfoFragment = doSelect(mInfoFragment, 4, { getInfoFrg() })
            }
//            else->{
//                LogUtils.e("-----------------",this)
//                if (mInfoFragment == null) mInfoFragment = InfoFragment()
//            }
        }
    }

    /**
     * 1 普通用户
     * 2.
     */
    fun getInfoFrg(): Fragment {
        LogUtils.e("-------" + AccountHelper.getUser().type, this)

        return when (AccountHelper.getUser().type) {
            2 -> {
                if (AccountHelper.getUser().infoData?.designData?.parentType == 4) {
                    vipDesignerInfoFragment()
                } else {
                    designerInfoFragment()
                }
            }
            3 -> decorationFactoryInfoFragment()
            4 -> ChengYunShangInfoFragment()
            else -> InfoFragment()
        }
    }

    fun doSelect(frg: Fragment?, position: Int, function: () -> Fragment): Fragment {
        val temFragment: Fragment
        val ft = supportFragmentManager.beginTransaction()
        mCurrentFrgment?.let {
            ft.detach(mCurrentFrgment!!)
        }
        if (frg == null) {
            temFragment = function()
            ft.replace(R.id.home_container, temFragment, "MainActivity" + position)
        } else {
            temFragment = frg
            ft.attach(frg)
        }
        ft.commit()
        mCurrentFrgment = temFragment
        return temFragment
    }

    override fun onTrimMemory(level: Int) {

        LogUtils.e("onTrimMemory(level: Int)", this)
        super.onTrimMemory(level)
    }

    private var exitTime: Long = 0
    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {

        if ((System.currentTimeMillis() - exitTime) > 2000) {
            ToastUtils.toast(this, "再按一次退出程序")
            exitTime = System.currentTimeMillis()
        } else {
            finish()
        }
        // super.onBackPressed()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        LogUtils.e("-----" + requestCode + "----" + resultCode, this)
        if (requestCode == ConstString.LOG_IN_REQUEST) {
            if (resultCode == ConstString.LOG_IN_RESULT_OK) {//最好是刷新一下Fragment。但是每次解锁屏幕都要请求数据，用最暴力的方法解决吧

                if (mCurrentFrgment is ShopCarFragment) {
                    mShopCarFragment = doSelect(mShopCarFragment, 3) { ShopCarFragment() }
                } else {
                    mInfoFragment = doSelect(null, 4) { getInfoFrg() }
                }
            } else if (resultCode == ConstString.LOG_IN_RESULT_NO) {

                bottom_tab_layout.getTabAt(0)?.select()
//                LogUtils.e("------------------------",this)
//                mHomePageFragment = doSelect(mHomePageFragment,0){homePageFragmentV2()} }
            }
        }
    }
}
