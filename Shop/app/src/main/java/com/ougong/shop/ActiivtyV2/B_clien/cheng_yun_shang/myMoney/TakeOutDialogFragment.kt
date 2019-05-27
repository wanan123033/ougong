package com.ougong.shop.ActiivtyV2.B_clien.cheng_yun_shang.myMoney

import android.os.Bundle
import android.support.v4.app.DialogFragment
import com.ougong.shop.R
import android.R.attr.gravity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.DisplayMetrics
import android.view.*
import kotlinx.android.synthetic.main.take_out_fragmen.*
import kotlinx.android.synthetic.main.take_out_fragmen.view.*


class TakeOutDialogFragment : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        return inflater.inflate(R.layout.take_out_fragmen,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.yes.setOnClickListener {
            (activity as MyMoneyActivity).makesureTakeOutMoney(input_no.text.toString().toInt())
            dismiss()
        }
        view.no.setOnClickListener {

            dismiss()
        }
    }
    override fun onStart() {
        super.onStart()
        val win = dialog.window
        // 一定要设置Background，如果不设置，window属性设置无效
        win!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val dm = DisplayMetrics()
        activity!!.windowManager.defaultDisplay.getMetrics(dm)

        val params = win.attributes
        params.gravity = Gravity.CENTER
        // 使用ViewGroup.LayoutParams，以便Dialog 宽度充满整个屏幕
        params.width = ViewGroup.LayoutParams.WRAP_CONTENT
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT
        win.attributes = params
    }
}
