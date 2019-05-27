package com.baigui.commonlib.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast统一管理类
 */
public class ToastUtils {
    private static Toast toast;

    /**
     * 短时间Toast
     *
     * @param msg
     */
    public static void toast(Context context, String msg) {
        if (toast == null) {
            toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

    /**
     * 长时间Toast
     *
     * @param msg
     */
    public static void toastlong(Context context,String msg) {
        if (toast == null) {
            toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
        } else {
            toast.setText(msg);
        }
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

}
