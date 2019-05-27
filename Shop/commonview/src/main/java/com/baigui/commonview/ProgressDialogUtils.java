package com.baigui.commonview;

import android.app.Dialog;
import android.content.Context;
import android.view.*;

/**
 * Created by chenjunshan on 2016/9/7.
 */

public class ProgressDialogUtils extends Dialog {
    private CircleProgress progress = null;

    public ProgressDialogUtils(Context context, boolean isCancel) {
        super(context, R.style.progress_dialog);
        View view = LayoutInflater.from(context).inflate(R.layout.loading_layout, null);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//取消标题
        this.setCanceledOnTouchOutside(isCancel);//点击屏幕消失
        this.setContentView(view);
        Window dialogwWindow = this.getWindow();
        WindowManager.LayoutParams lp = dialogwWindow.getAttributes();
        dialogwWindow.setGravity(Gravity.CENTER);
        dialogwWindow.setAttributes(lp);
        dialogwWindow.setBackgroundDrawableResource(R.drawable.loding_dialog_square);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (progress != null) {
            progress.stopAnim();
        }
    }
}
