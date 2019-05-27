package com.ougong.shop.Bean

import java.io.Serializable

data class CateryBean(var id : Int,var name : String, var appImage : String, var child : Array<CateryBean>,var enabless:Boolean = false): Serializable
