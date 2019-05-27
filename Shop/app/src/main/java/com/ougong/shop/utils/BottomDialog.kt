package com.ougong.shop.utils

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.*
import com.ougong.shop.R

class BottomDialog : DialogFragment() {


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val dialog = Dialog(context, R.style.CustomDatePickerDialog)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE) // must be called before set content
        dialog.setContentView(LayoutInflater.from(context).inflate(R.layout.test
            , null))
        dialog.setCanceledOnTouchOutside(true)

        // 设置宽度为屏宽、靠近屏幕底部。
        val window = dialog.window
        val wlp = window!!.attributes
        wlp.gravity = Gravity.BOTTOM
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT
        window.attributes = wlp
        return dialog
    }
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        return inflater.inflate(R.layout.test,container,false)
//    }
}