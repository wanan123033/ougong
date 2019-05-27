package com.ougong.shop.Bean


/**
 * Created by ruedy on 2016/11/30.
 */

class HomeCategory {

    var imageid: Int = 0
    var typename: String? = null

    constructor(imageid: Int, typename: String) {

        this.imageid = imageid
        this.typename = typename
    }

    constructor(imageid: Int, stringID: Int) {

        this.imageid = imageid
    }
}
