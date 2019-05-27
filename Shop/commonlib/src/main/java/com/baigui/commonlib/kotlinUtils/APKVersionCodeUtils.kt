package com.baigui.commonlib.kotlinUtils

import android.content.Context
import android.content.pm.PackageManager

class APKVersionCodeUtils {
    /**
     * 获取当前本地apk的版本
     *
     * @param mContext
     * @return
     */

    companion object {
        fun getVersionCode(mContext: Context) : Int {

            var versionCode = 0
            try {
                //获取软件版本号，对应AndroidManifest.xml下android:versionCode
                versionCode =
                    mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionCode
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }
            return versionCode
        }

        fun getVerName(mContext: Context): String {

            var verName = ""
            try {
                verName = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionName;
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }
            return verName;
        }
    }

}