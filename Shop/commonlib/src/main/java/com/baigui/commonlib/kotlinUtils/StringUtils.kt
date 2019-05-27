package com.baigui.commonlib.kotlinUtils

import java.util.regex.Matcher
import java.util.regex.Pattern


class StringUtils {
    companion object {
        fun isPhone(phone: String): Boolean {
            val regex =
                "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$"
            if (phone.length != 11) {
                return false
            } else {
                val p = Pattern.compile(regex)
                val m = p.matcher(phone)
                val isMatch = m.matches()
                return isMatch
            }
        }


        /**
         * 判断是否含有特殊字符
         *
         * @param str
         * @return true为包含，false为不包含
         */
        fun isSpecialChar(str: String): Boolean {
            var regEx = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t"
            var p = Pattern.compile(regEx);
            var m = p.matcher(str)
            return m.find()
        }

    }
}