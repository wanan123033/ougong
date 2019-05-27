package com.baigui.commonview.bean

class ItemStatus {

    var viewType: Int = 0 // item类型：group or sub
    var groupItemIndex: Int = 0 // 一级列表索引
    var subItemIndex = -1 // 二级列表索引

    companion object {
        val VIEW_TYPE_GROUP_ITEM = 0
        val VIEW_TYPE_SUB_ITEM = 1
    }
}