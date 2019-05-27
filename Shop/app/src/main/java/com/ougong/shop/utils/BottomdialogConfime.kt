package com.ougong.shop.utils

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import com.ougong.shop.R
import kotlinx.android.synthetic.main.confime.view.*

class BottomdialogConfime(
    context: Context,
    var title : String
) : Dialog(context, R.style.CustomDatePickerDialog) {


    var clickListenerInterface: ClickListenerInterface? = null
    interface ClickListenerInterface {

        fun doConfirm()

        fun doCancel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }


    fun init() {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.confime, null)
        setContentView(view)
        setCancelable(true)

        view.title.text = title
        setCanceledOnTouchOutside(true)
        view.yes.setOnClickListener {
            clickListenerInterface?.doConfirm()
            cancel()
        }

        view.no.setOnClickListener {
            clickListenerInterface?.doCancel()

            cancel()
        }

        // 设置宽度为屏宽、靠近屏幕底部。
        val window = window
        val wlp = window!!.attributes
        wlp.gravity = Gravity.BOTTOM
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT
        window.attributes = wlp
    }

}