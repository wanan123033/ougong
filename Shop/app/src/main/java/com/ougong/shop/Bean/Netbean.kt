package com.ougong.shop.Bean

import com.baigui.netlib.BeanBase

/**
 * gson解析时候，少一个参数是没问题的，这里不用纠结。也可以直接解析，这里可以用空指针判断
 */
data class Netbean<T>(val data : T, val message : String, val status : Int): BeanBase()


/**
*曹丹的设计，，，，，，
 */
data class fuckNetbean<T>(val data : Array<T>, val message : String, val status : Int): BeanBase()