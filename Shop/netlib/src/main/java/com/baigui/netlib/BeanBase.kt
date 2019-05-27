package com.baigui.netlib

import com.baigui.commonlib.utils.ReflectToStringUtil

open class BeanBase {

    override fun toString(): String {
        return ReflectToStringUtil.toStringUtil(this,false)

    }
}