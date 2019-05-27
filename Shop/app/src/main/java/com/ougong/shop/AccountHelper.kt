package com.ougong.shop


import android.annotation.SuppressLint
import android.app.Application
import android.icu.text.UFormat
import android.text.TextUtils
import android.view.View
import com.baigui.commonlib.utils.SPUtils
import com.baigui.commonlib.utils.SharedPreferencesHelper
import com.google.gson.Gson
import com.ougong.shop.Bean.AccountBean
import com.ougong.shop.Bean.Netbean
import com.ougong.shop.Bean.User
import com.ougong.shop.httpmodule.ServiceFactory
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * 账户辅助类，
 * 用于更新用户信息和保存当前账户等操作
 */
class AccountHelper private constructor(private val application: Application) {
    private var user: User? = null
    companion object {
        private val TAG = AccountHelper::class.java.simpleName
        @SuppressLint("StaticFieldLeak")
        private var instances: AccountHelper? = null

        var gson = Gson()
        fun init(application: Application) {
            if (instances == null) {
                instances = AccountHelper(application)
//                instances!!.user = SharedPreferencesHelper.loadFormSource(instances!!.application, User::class.java)
            } else {
                // reload from source
//                instances!!.user = SharedPreferencesHelper.loadFormSource(instances!!.application, User::class.java)
            }

            contentToken = SPUtils.get(App.app, "content_token", "").toString()

            RefeshToken = SPUtils.get(App.app, "refesh_token", "").toString()

            synchronizationUser()
        }

        val isLogin: Boolean
            get() = getUser().id > 0 && !TextUtils.isEmpty(contentToken)

        var contentToken: String? = " "

        var RefeshToken: String? = " "


        @Synchronized
        fun getUser(): User {
            if (instances == null) {
                //    TLog.error("AccountHelper instances is null, you need call init() method.")
                return User()
            }
            //需要的时候从数据库sp中恢复。没必要保存这个，但是这里暂时不想删除
            if (instances!!.user == null) {
                val str = SPUtils.get(App.app, "user_gson", "").toString()
                if (!TextUtils.isEmpty(str)) {
                    instances!!.user =
                        gson.fromJson(str, User::class.java)
                }
            }
            if (instances!!.user == null)
                instances!!.user = User()
            return instances!!.user!!
        }
//
//        fun updateUserInfo(user: User?): Boolean {
//            if (user == null)
//                return false
//            // 这里基本上永远不会相等，都是从服务器直接拉下来的数据，应该写一个检查机制
//            if (user.equals(getUser())) {
//                return true
//            }
//            instances!!.user = user
//            SPUtils.put(App.app, "user_gson", gson.toJson(user))
//            return true
////            return SharedPreferencesHelper.save(instances!!.application, user)
//        }

        /**
         *
         */
        fun updateUserCache(user: User?): Boolean {
            if (user == null)
                return false

            //这是不可能相等的，
            if (user.equals(getUser())) {
                return true
            } else {
                instances!!.user = user
            }
            SPUtils.put(App.app, "user_gson", gson.toJson(user))
            return true
//            return SharedPreferencesHelper.save(instances!!.application, user)
        }


        fun updateTokenCache() {
            if (!TextUtils.isEmpty(contentToken)) {
                SPUtils.put(App.app, "content_token", contentToken)
            }else{
                SPUtils.remove(App.app, "content_token")
            }
            if (!TextUtils.isEmpty(RefeshToken)) {
                SPUtils.put(App.app, "refesh_token", RefeshToken)
            }else {
                SPUtils.remove(App.app, "refesh_token")
            }
        }

        fun clearUserCache() {
            var user = getUser()
            user = User()
            RefeshToken = null
            contentToken = null
            globalDataTemp = GlobalData()
            SPUtils.put(App.app, "user_gson", gson.toJson(user))
//            SharedPreferencesHelper.remove(instances!!.application, User::class.java)
        }


        /**
         * 和服务数据同步，主要是用来更新购物车数量和定单数量,其实这个变得可由可无
         * user信息是不会被改变的，只用在编辑个人信息页面和登录页面同步即可
         * 购物车操作比较少（商品详情和购物车编辑），所以也不会需要这个
         * 订单就稍微复杂一些了，但是只用在进入个人中心更新页面更新即可。
         */
        fun synchronizationUser() {
            if (!isLogin) {
                return
            }
            /**
             * 更新用户信息
             */
            ServiceFactory.instance.accountAPIService!!.RefeshUserInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(io.reactivex.functions.Consumer<Netbean<User>> {
                    if (it.status == 200) {
                        updateUserCache(it.data)
                    }
                }
                    , io.reactivex.functions.Consumer<Throwable> { })

            synchronizationShopCar()
            synchronizationOrder()

        }


        fun synchronizationShopCar() {
            if (!isLogin) {
                return
            }
            ServiceFactory.instance.productApiservice!!.getShopCarCount()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe (io.reactivex.functions.Consumer<Netbean<Int>> { it ->
                    if (it?.status == 200) {
                        globalDataTemp.shopCarCount = it.data
                    }
                }, io.reactivex.functions.Consumer<Throwable> { it.printStackTrace() })

        }
        fun synchronizationOrder() {
            if (!isLogin) {
                return
            }

            ServiceFactory.instance.productApiservice!!.getOrderCount()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(io.reactivex.functions.Consumer<Netbean<AccountBean>> {
                    if (it.status == 200) {
                        globalDataTemp.orderHistorycount = it.data.totalCount
                        globalDataTemp.orderHistorynotpayCount = it.data.unPayCount
                        globalDataTemp.orderhistoryPayedCount = it.data.payedCount
                    }
                }, io.reactivex.functions.Consumer<Throwable> { it.printStackTrace() })
        }

        fun refeshOrder(accountBean: AccountBean){
            globalDataTemp.orderHistorycount = accountBean.totalCount
            globalDataTemp.orderHistorynotpayCount = accountBean.unPayCount
            globalDataTemp.orderhistoryPayedCount = accountBean.payedCount

        }
        var globalDataTemp : GlobalData = GlobalData()
        fun logout() {
            clearUserCache()
        }
    }
}

/**
 * 这里存储一些全局tem变量，
 */
data class GlobalData(var shopCarCount: Int = 0,var orderHistorycount: Int = 0,var orderhistoryPayedCount: Int = 0,
                      var orderHistorynotpayCount: Int = 0)