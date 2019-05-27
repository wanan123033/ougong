package com.baigui.netlib

import com.baigui.commonlib.utils.ReflectToStringUtil

class Contributor(public var login: String, public var contributions: Int): BeanBase(){

    override fun toString(): String {

        return ReflectToStringUtil.toStringUtil(this,false)
    }
}
