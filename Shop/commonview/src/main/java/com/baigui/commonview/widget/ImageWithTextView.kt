package com.baigui.commonview.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.baigui.commonview.R
import kotlinx.android.synthetic.main.common_image_with_text.view.*

/**
 * Created by ruedy on 2016/11/30.
 */

class ImageWithTextView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr), View.OnClickListener {

    var imagetextclic: Imagetextclick? = null

    init {
        View.inflate(context, R.layout.common_image_with_text, this)
        initattrs()
        //ivImagetext.setOnClickListener(this);
        //  tvImagetext.setOnClickListener(this);
    }

    private fun initattrs() {

        //如果需要静态设置，此处可拓展
    }

    fun setIvImagetext(imageid: Int) {
        val drawable = resources.getDrawable(imageid,null)

        if (drawable != null) {
            iv_imagetext.setImageDrawable(drawable)
        }
    }

    fun setTvImagetext(typename: String?) {

        if (typename != null) {
            tv_imagetext!!.text = typename + ""

        }

    }

    override fun onClick(view: View) {
        if (imagetextclic != null) {
            imagetextclic!!.setImagetextclick()

        }
    }

    interface Imagetextclick {
        fun setImagetextclick()

    }

    fun setImagetextclick(imagetextclick: Imagetextclick) {
        this.imagetextclic = imagetextclick
    }
}
