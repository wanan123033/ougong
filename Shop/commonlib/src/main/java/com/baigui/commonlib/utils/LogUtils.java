package com.baigui.commonlib.utils;

import android.util.Log;
import org.jetbrains.annotations.Nullable;

/**
 * 打印日志
 * Created by chenjunshan on 2016/7/5.
 */
public class LogUtils {
    public static boolean isLog = true;

    /**
     * info的日志
     *
     * @param info
     */
    public static void i(String info,Object object) {
        if (!isLog)
            return;

        if (object == null) {
            Log.d("static" + "--" + new Exception().getStackTrace()[1].getMethodName(), info);
        }else {
            Log.d(object.getClass().getSimpleName() + "--" + new Exception().getStackTrace()[1].getMethodName(), info);
        }
    }

    /**
     * debug的日志
     *
     * @param debug
     */
    public static void d(String debug,Object object) {
        if (!isLog)
            return;

        if (object == null) {
            Log.d("static" + "--" + new Exception().getStackTrace()[1].getMethodName(), debug);
        }else {
            Log.d(object.getClass().getSimpleName() + "--" + new Exception().getStackTrace()[1].getMethodName(), debug);
        }
    }
    /**
     * error的日志
     *
     * @param error
     */
    public static void e(String error,@Nullable Object object) {
        if (!isLog)
            return;

        if (object == null) {
            Log.e("static" + "--" + new Exception().getStackTrace()[1].getMethodName(), error);
        }else {
            Log.e(object.getClass().getSimpleName() + "--" + new Exception().getStackTrace()[1].getMethodName(), error);
        }
    }

    /**
     * warn的日志
     *
     * @param warn
     */
    public static void w(String warn,Object object) {
        if (!isLog)
            return;

        if (object == null) {
            Log.e("static" + "--" + new Exception().getStackTrace()[1].getMethodName(), warn);
        }else {
            Log.e(object.getClass().getSimpleName() + "--" + new Exception().getStackTrace()[1].getMethodName(), warn);
        }
    }

    /**
     * verbose模式,打印最详细的日志
     *
     * @param verbose
     */
    public static void v(String verbose,Object object) {
        if (!isLog)
            return;

        if (object == null) {
            Log.d("static" + "--" + new Exception().getStackTrace()[1].getMethodName(), verbose);
        }else {
            Log.d(object.getClass().getSimpleName() + "--" + new Exception().getStackTrace()[1].getMethodName(), verbose);
        }
    }
}
