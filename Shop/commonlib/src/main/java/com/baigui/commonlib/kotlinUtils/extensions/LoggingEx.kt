package io.armcha.ribble.presentation.utils.extensions



/**
 * Created by Chatikyan on 14.09.2017.
 */

//inline fun log(message: () -> Any?) {
//    LogUtils.e()
//}

inline fun <reified T> T.withLog(): T {
    log("${T::class.java.simpleName} $this")
    return this
}

fun log(vararg message: () -> Any?) {
    message.forEach {
        log(it())
    }
}

fun log(message: Any?) {

}