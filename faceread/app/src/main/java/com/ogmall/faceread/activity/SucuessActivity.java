package com.ogmall.faceread.activity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.gwm.android.ACache;
import com.gwm.android.ThreadManager;
import com.gwm.annotation.Layout;
import com.gwm.base.BaseActivity;
import com.gwm.base.BaseRunnable;
import com.ogmall.faceread.bean.ImageBean;
import com.ogmall.faceread.bean.WearcherBean;
import com.ogmall.faceread.datapresenter.RegisterDataPresenter;
import com.ogmall.faceread.datapresenter.WearcherDataPresenter;
import com.ogmalllarge.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2019/3/18.
 */
@Layout(R.layout.activity_scuess)
public class SucuessActivity extends BaseActivity{

    private static final int REQUEST_CAMERA = 8899;
    private static final int UPDATE_DATE = 88789;
    @BindView(R.id.tv_info)
    TextView tv_info;
    @BindView(R.id.tv_wendu)
    TextView tv_wendu;
    @BindView(R.id.tv_xingqi)
    TextView tv_xingqi;
    @BindView(R.id.tv_date)
    TextView tv_date;
    @BindView(R.id.tv_time)
    TextView tv_time;

    private int time = 5;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1233){
                if (isReset){
                    tv_info.setText("5秒后跳转到主界面");
                    return;
                }
                String text = tv_info.getText().toString().trim();
                if (text.contains("5秒")){
                    handler.sendEmptyMessageDelayed(1233,1000);
                    tv_info.setText("4秒后跳转到主界面");
                }else if (text.contains("4秒")){
                    handler.sendEmptyMessageDelayed(1233,1000);
                    tv_info.setText("3秒后跳转到主界面");
                }else if (text.contains("3秒")){
                    handler.sendEmptyMessageDelayed(1233,1000);
                    tv_info.setText("2秒后跳转到主界面");
                }else if (text.contains("2秒")){
                    handler.sendEmptyMessageDelayed(1233,1000);
                    tv_info.setText("1秒后跳转到主界面");
                }else if (text.contains("1秒")){
                    handler.sendEmptyMessageDelayed(1233,1000);
                    tv_info.setText("0秒后跳转到主界面");
                }else {
                    startActivity(Detect2Activity.class);
                    finish();
                }
            }else if (msg.what == UPDATE_DATE){
                initDataView();
            }
        }
    };

    private void initDataView() {
        Calendar calendar = Calendar.getInstance();
        int xingqi = calendar.get(Calendar.DAY_OF_WEEK)-1;
        int monthy = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int month = calendar.get(Calendar.MINUTE);
        Log.e("----"+xingqi+"---"+monthy+"---"+day+"---"+hour+"----"+month);
        tv_xingqi.setText("星期"+XQzhuanhuan(xingqi));
        tv_date.setText(zhuanhan(monthy)+"/"+zhuanhan(day));
        tv_time.setText(zhuanhan(hour)+":"+zhuanhan(month));
        handler.sendEmptyMessageDelayed(UPDATE_DATE,1000*60);
    }
    private String zhuanhan(int day) {
        if (day<10){
            return "0"+day;
        }
        return String.valueOf(day);
    }

    private String XQzhuanhuan(int xingqi) {
        switch (xingqi){
            case 0:
                return "日";
            case 1:
                return "一";
            case 2:
                return "二";
            case 3:
                return "三";
            case 4:
                return "四";
            case 5:
                return "五";
            case 6:
                return "六";
        }
        return "";
    }

    private Uri imageUri;
    private boolean isReset;

    @Override
    public void onRequestSuccessData(Object data) {
        if (data instanceof WearcherBean){
            tv_wendu.setText(((WearcherBean) data).data.wendu+"℃  "+((WearcherBean) data).data.forecast.get(0).type);
        }else if (data instanceof ImageBean){
            isReset = false;
            handler.sendEmptyMessageDelayed(1233,1000);
            if (bitmap != null) {
                bitmap.recycle();
                bitmap = null;
            }
            if (((ImageBean) data).code == 500){
                showToast(((ImageBean) data).message);
            }else if (((ImageBean) data).code == 200){
                showToast("上传成功！");
            }
        }
    }

    @Override
    public void onError() {

    }

    @Override
    public void onCreate(FragmentManager manager, Bundle savedInstanceState) {
        handler.sendEmptyMessageDelayed(1233,1000);
        getWeacher();
        hideNavigationBar();
        handler.sendEmptyMessage(UPDATE_DATE);
    }

    private void getWeacher() {
        WearcherDataPresenter dataPresenter = new WearcherDataPresenter(this,this);
        dataPresenter.wearcher();
    }
    @OnClick(R.id.tv_reset)
    public void onClick(View view){
        isReset = true;
        toCamera();
    }
    private Bitmap bitmap;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CAMERA){
            ThreadManager.getInstance().run(new BaseRunnable() {
                @Override
                public void run() {
                    super.run();
                    File file = new File(getRealPathFromURI(imageUri));

                    bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    bitmap = Bitmap.createScaledBitmap(bitmap, 217, 200, true);
                    String path = saveImage(bitmap);
                    String token = ACache.get(getApplicationContext()).getAsString("token");
                    new RegisterDataPresenter(SucuessActivity.this,getApplicationContext()).upload(token,new File(path));
                }
            });
        }
    }
    private String saveImage(Bitmap face) {
        try {
            String path = getApplicationContext().getCacheDir()+"/"+System.currentTimeMillis()+".png";
            File file = new File(path);
            if (!file.exists()){
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(path);
            face.compress(Bitmap.CompressFormat.PNG,100,fos);
            fos.flush();
            fos.close();
            return path;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 选择相机
     */
    public void toCamera() {
        imageUri = createImageUri(this);
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra("android.intent.extras.CAMERA_FACING", 1); // 调用前置摄像头
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);//如果不设置EXTRA_OUTPUT getData()  获取的是bitmap数据  是压缩后的
        startActivityForResult(intent, REQUEST_CAMERA);
    }
    private static Uri createImageUri(Context context) {
        String name = "takePhoto" + System.currentTimeMillis();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.TITLE, name);
        contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, name + ".jpeg");
        contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        Uri uri = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        return uri;
    }
    public String getRealPathFromURI(Uri contentUri) {
        if (contentUri == null){
            return null;
        }
        String res = null;
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if(cursor.moveToFirst()){
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {

        }
    };
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                handler.removeCallbacks(runnable);
                break;
            case MotionEvent.ACTION_UP:
                startAD();
                break;
        }
        return super.onTouchEvent(event);
    }
    public void startAD() {
        handler.removeCallbacks(runnable);
        handler.postDelayed(runnable, time);
    }

}
