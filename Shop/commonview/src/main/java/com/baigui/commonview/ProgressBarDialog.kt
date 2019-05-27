package com.baigui.commonview

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

public class ProgressBarDialog: DialogFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        isCancelable = false
        return inflater.inflate(R.layout.progress_bar,container, true)
    }

    companion object {
        fun getProgressBarDialog(): ProgressBarDialog{
            return ProgressBarDialog()
        }
    }
}