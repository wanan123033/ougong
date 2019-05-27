package com.ougong.shop.activity.orderhistory

import com.ougong.shop.R

interface OrderOpeator {
    fun qrsh(orderId:Int)  //确认收货
    fun cannalOrder(orderId:Int) //取消订单
    fun gotoPay(orderId:Int) //再次购买
    fun delete(orderId:Int) //删除订单
    fun pay(orderId:Int) //立即购买


}