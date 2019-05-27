package com.baigui.commonlib.kotlinUtils.extensions

import android.app.Activity
import android.content.Intent
import android.text.SpannableString
import com.baigui.commonlib.utils.PublicUtils
import io.armcha.ribble.presentation.utils.extensions.start
import java.math.BigDecimal
import java.text.DecimalFormat

inline fun Float.Fromate(foramatString : String? = "###.00") : String{
//    val df2 = DecimalFormat(foramatString)
//    return df2.format(this)
    return BigDecimal(this as Double).setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
}


inline fun Double.Fromate(foramatString : String? = "#.00",operate : Int = 1) : SpannableString? {
//    val df2 = DecimalFormat(foramatString)
//    return df2.format(this)
    if (operate == 1) {
        return PublicUtils.changTVsize("¥" + BigDecimal(this).setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString())
    }else
        return PublicUtils.changTVsize(BigDecimal(this).setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString())
}

inline fun Double.Fromate1(foramatString : String? = "#.00") : SpannableString? {
//    val df2 = DecimalFormat(foramatString)
//    return df2.format(this)
    return PublicUtils.changTVsize("￥:"+BigDecimal(this).setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString())
}