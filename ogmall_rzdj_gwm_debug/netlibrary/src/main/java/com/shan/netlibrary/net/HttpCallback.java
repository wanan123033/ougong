package com.shan.netlibrary.net;

import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;

import com.junshan.pub.App;
import com.junshan.pub.bean.MessageEvent;
import com.junshan.pub.config.BaseMsgConstant;
import com.junshan.pub.utils.LogUtils;
import com.junshan.pub.utils.NetUtils;
import com.junshan.pub.utils.PDUtils;
import com.junshan.pub.utils.ToastUtils;
import com.shan.netlibrary.R;

import org.greenrobot.eventbus.EventBus;

import rx.Subscriber;

/**
 * 处理请求回调
 * Created by chenjunshan on 2016/8/28.
 */

public abstract class HttpCallback<T extends BaseBean> extends Subscriber<T> {
    private PDUtils pdUtils = null;
    private boolean isCancel = true;//默认点击返回键是可以取消
    private CancelRequestListener cancelRequestListener;
    private Context mContext;

    public HttpCallback() {
    }

    //不启动ProgressDialog
    public HttpCallback(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * 启动ProgressDialog，并注册取消请求监听
     *
     * @param context
     * @param cancelRequestListener 取消请求监听
     */
    public HttpCallback(Context context, CancelRequestListener cancelRequestListener) {
        this.mContext = context;
        pdUtils = new PDUtils(context, isCancel);
        this.cancelRequestListener = cancelRequestListener;
        pdUtils.setOnKeyListener(new DialogOnKeyListener());
    }

    /**
     * 启动ProgressDialog，并注册取消请求监听
     *
     * @param context
     * @param cancelRequestListener 取消请求监听
     * @param isCancel              点击返回键是否可以取消
     */
    public HttpCallback(Context context, CancelRequestListener cancelRequestListener, boolean isCancel) {
        this.mContext = context;
        this.isCancel = isCancel;
        this.cancelRequestListener = cancelRequestListener;
        if (!isCancel) {
            pdUtils = new PDUtils(context, isCancel);
            pdUtils.setOnKeyListener(new DialogOnKeyListener());
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (pdUtils != null && !pdUtils.isShowing()) {
            pdUtils.show();
        }
    }

    @Override
    public void onCompleted() {
        if (pdUtils != null && pdUtils.isShowing()) {
            pdUtils.dismiss();
        }
    }

    @Override
    public void onError(Throwable e) {
        if (e.toString().contains("404")){
            EventBus.getDefault().post(new MessageEvent(BaseMsgConstant.SYSTEMERROR));
            return;
        }
        LogUtils.e(e.toString());
        if (!NetUtils.isNetworkConnected()) {
            ToastUtils.toast(App.getInstance().getResources().getString(R.string.str_not_net));
            EventBus.getDefault().post(new MessageEvent(BaseMsgConstant.NONET));
        }
        onFailure(e);
        if (pdUtils != null && pdUtils.isShowing()) {
            pdUtils.dismiss();
        }
    }

    @Override
    public void onNext(T t) {
        if (t.getCode() != 0) {
            ToastUtils.toast(t.getMessage());
            if (t.getCode() == -100) {
                //关闭所有页面
                EventBus.getDefault().post(new MessageEvent(BaseMsgConstant.FINISHACTIVITY));
                //调到登录页面
                EventBus.getDefault().post(new MessageEvent(BaseMsgConstant.LOGOUTTOLOGIN));
            }
            onError(new RuntimeException(t.getMessage()));
            return;
        }
        onSuccess(t);
        if (pdUtils != null && pdUtils.isShowing()) {
            pdUtils.dismiss();
        }
//        onError(new RuntimeException("404"));
    }

    protected abstract void onSuccess(T t);

    protected abstract void onFailure(Throwable e);

    protected void onFailure(T t) {
    }

    private class DialogOnKeyListener implements DialogInterface.OnKeyListener {
        @Override
        public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
            if (i == KeyEvent.KEYCODE_BACK) {
                if (!isCancel)
                    return true;
                onError(new RuntimeException(App.getInstance().getResources().getString(R.string.str_cancel_request)));
                cancelRequestListener.cancelRequest();
            }
            return false;
        }
    }

}
