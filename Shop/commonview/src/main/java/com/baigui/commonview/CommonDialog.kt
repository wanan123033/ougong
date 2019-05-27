package com.baigui.commonview


import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.icu.lang.UCharacter.DecompositionType.SQUARE
import android.os.Build
import android.support.annotation.LayoutRes
import android.view.Display
import android.view.Gravity
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.baigui.commonlib.utils.ScreenUtils


/**
 * 公共弹出框
 * Created by 陈俊山 on 2016/3/24.
 */
class CommonDialog private constructor(var builder: Builder) {
    lateinit var dialog: Dialog
    private val gravity: Int//弹出宽的位置，默认居中
    private val width: Float//弹出框的宽度,默认0.8，例如：1表示全屏，0.8表示为屏幕宽度的0.8倍
    private val height: Float//默认布局自动加载高度
//    private val shape: Int//弹出框形状，默认方角
    private val resId: Int//布局id
    private val animResId: Int = View.NO_ID//动画id
    private var isTouchOutside: Boolean = false//点击屏幕是否可消失
    lateinit var mView : View
    /**
     * 是否show
     *
     * @return
     */
    val isShowing: Boolean
        get() = if (dialog != null) {
            dialog!!.isShowing
        } else false

    private val listener = DialogInterface.OnKeyListener { dialogInterface, i, keyEvent ->
        if (i == KeyEvent.KEYCODE_BACK) {
            !isTouchOutside
        } else false
    }


    init {
        this.gravity = builder.gravity
        this.width = builder.width
        this.height = builder.height
//        this.shape = builder.shape
        this.resId = builder.resId
//        this.animResId = builder.animResId
        this.dialog = builder.dialog
        this.isTouchOutside = builder.isTouchOutside
        this.dialog!!.setOnKeyListener(listener)

        this.mView = builder.mView
    }

    class Builder(context: Context) {
        private val windowManager: WindowManager
        private val display: Display
        private val activity: Activity
        lateinit var dialog: Dialog
        var gravity = Gravity.CENTER
        var width = 0.9f
        var height = 0.0f
        var shape = SQUARE
        var resId: Int = 0
//        var animResId = DIALOG_IN_OUT
        var isTouchOutside = true

        init {
            this.activity = context as Activity
            windowManager = activity.windowManager
            display = windowManager.defaultDisplay
        }

        fun setGravity(GRAVITY: Int): Builder {
            this.gravity = GRAVITY
            return this
        }

        fun setWidth(width: Float): Builder {
            this.width = width
            return this
        }

        fun setHeight(height: Float): Builder {
            this.height = height
            return this
        }

        fun setShape(shape: Int): Builder {
            this.shape = shape
            return this
        }

        fun setResId(resId: Int): Builder {
            this.resId = resId
            return this
        }

        fun setAnimResId(animResId: Int): Builder {
//            this.animResId = animResId
            return this
        }

        fun setTouchOutside(touchOutside: Boolean): Builder {
            isTouchOutside = touchOutside
            return this
        }

        fun build(): CommonDialog {
            dialog = object : Dialog(activity) {
                override fun show() {
                    this.window!!.setFlags(
                        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    )
                    super.show()
                    fullScreenImmersive(window!!.decorView)
                    this.window!!.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
                }
            }
            dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)//取消标题
            dialog!!.setCanceledOnTouchOutside(isTouchOutside)//点击屏幕消失

            mView = dialog.layoutInflater.inflate(resId,null,false)
            dialog!!.setContentView(mView)
//            dialog!!.setContentView(binding!!.getRoot())
            val dialogwWindow = dialog!!.window
            val lp = dialogwWindow!!.attributes
//            if (width != 0f)
//                lp.width = (display.width * width).toInt()//设置Dialog宽度
//            if (height != 0f)
//                lp.height = (((display.height + ScreenUtils.getStatusHeight(activity) * 2) * height).toInt())//设置Dialog高度
//            dialogwWindow!!.attributes = lp
            dialogwWindow.setGravity(gravity)//设置dialog显示位置
//            dialogwWindow.setBackgroundDrawableResource(shape)//设置dialog背景风格
//            if (animResId != 0) {
//                dialogwWindow.setWindowAnimations(animResId)//设置动画效果
//            }
            dialog!!.setOnDismissListener {
                fullScreenImmersive(activity.window.decorView)
                diaLogDissmiss()
            }
            return CommonDialog(this)
        }

        private fun fullScreenImmersive(view: View) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                val uiOptions = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
                //| View.SYSTEM_UI_FLAG_FULLSCREEN;
                view.systemUiVisibility = uiOptions
            }
        }

        lateinit var mView : View
        fun getView(): View {
            return mView
        }
        fun diaLogDissmiss() {}
    }

    fun getView(): View {
        return mView
    }
    /**
     * 关闭dialog
     */
    fun dismiss() {
        if (dialog != null) {
            dialog.dismiss()
        }
    }

    /**
     * 显示dialog
     */
    fun show() {
        if (dialog != null && !dialog.isShowing) {
            dialog.show()
        }
    }

    companion object {
//        val CIRCLE = R.drawable.dialog_circle_shape//圆角
//        val SQUARE = R.drawable.dialog_square_shape//方角
//        val DIALOG_OnLine_IN_OUT = R.style.DarkAnimation//默认弹出
//        val DIALOG_IN_OUT = R.style.dialog_in_out//向上弹起向下滑落
//        val DIALOG_LEFT_RIGHT = R.style.dialog_left_right//从左弹出从右关闭
    }
}
