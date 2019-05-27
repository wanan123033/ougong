package com.baigui.commonlib.utils;

import android.content.Context;
import android.content.Intent;

/**
 * 广播工具类
 * Created by chenjunshan on 17-4-12.
 */

public class ReceiverUtils {
    public static final String TAG = "tag";
    public static final String MSG = "msg";

    /**
     * 发送广播
     * @param action
     * @param type
     */
    public static void sendBroadcast(Context context, String action,int type){
        Intent intent = new Intent(action);
        intent.putExtra(TAG, type);
        context.sendBroadcast(intent);
    }

    /**
     * 发送广播
     * @param action
     * @param type
     */
    public static void sendBroadcast(Context context, String action, int type, String msg){
        Intent intent = new Intent(action);
        intent.putExtra(TAG, type);
        intent.putExtra(MSG, msg);
        context.sendBroadcast(intent);
    }
}
