package com.ougong.shop.httpmodule

class ComboTitleBean {
    var total:Int = 0
    var rows:Array<ComboTitleBean.Result>? = null

    class Result{
        /*
        * {
                "id": 6,
                "name": "自选套餐",
                "type": 0,
                "sort": 3
            }*/
        var id:Int? = 0
        var name:String? = null
        var type:Int = 0
        var sort:Int = 0


        constructor(id: Int?, name: String?, type: Int, sort: Int) {
            this.id = id
            this.name = name
            this.type = type
            this.sort = sort
        }

        constructor()
    }
}
