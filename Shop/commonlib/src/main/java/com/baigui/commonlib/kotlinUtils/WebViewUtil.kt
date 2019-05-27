package com.baigui.commonlib.kotlinUtils

import android.text.TextUtils
import org.jsoup.Jsoup
import java.lang.Exception

class WebViewUtil {
    companion object {

        fun getNewContent(htmltext : String?) : String?{
            if (TextUtils.isEmpty(htmltext))
                return ""

            try {
                var doc= Jsoup.parse(htmltext)
                var elements=doc.getElementsByTag("img")
                for (element in elements) {
                    element.attr("width","100%").attr("height","auto")
                }
                return doc.toString()
            } catch (e : Exception) {
                return htmltext
            }
        }
    }
}