package io.armcha.ribble.presentation.utils.extensions

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.support.v4.content.ContextCompat

/**
 * Created by Chatikyan on 19.09.2017.
 */
inline fun <reified T> Activity.start() {
    this.startActivity(Intent(this, T::class.java))
}

fun Activity.isPortrait() = this.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT

infix fun Activity.takeColor(colorId: Int) = ContextCompat.getColor(this, colorId)

inline fun Activity.start(intent:Intent?=null){
    this.startActivity(intent)
}
