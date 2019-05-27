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
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gwm.android.ACache;
import com.gwm.android.ThreadManager;
import com.gwm.annotation.Layout;
import com.gwm.base.BaseActivity;
import com.gwm.base.BaseRunnable;
import com.gwm.util.ImageUtil;
import com.ogmall.faceread.bean.CodeBean;
import com.ogmall.faceread.bean.ImageBean;
import com.ogmall.faceread.bean.RegisterBean;
import com.ogmall.faceread.bean.WearcherBean;
import com.ogmall.faceread.datapresenter.GetCodeDataPresenter;
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
@Layout(R.layout.activity_regist)
public class RegistActivity extends BaseActivity implements TextWatcher {
    public static final String TYPE = "type";
    private static final int REQUEST_CAMERA = 2559;
    private static final int UPDATE_DATE = 3344;
    private Uri imageUri;
    private int type;
    private RegisterDataPresenter registerDataPresenter;
    private GetCodeDataPresenter getCodeDataPresenter;

    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.et_name)
    EditText et_name;
    @BindView(R.id.et_vode)
    EditText et_vode;
    @BindView(R.id.iv_scan)
    ImageView iv_scan;
    @BindView(R.id.tv_wendu)
    TextView tv_wendu;
    @BindView(R.id.tv_getCode)
    TextView tv_getCode;
    @BindView(R.id.tv_xingqi)
    TextView tv_xingqi;
    @BindView(R.id.tv_date)
    TextView tv_date;
    @BindView(R.id.tv_time)
    TextView tv_time;

    int i = 59;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 12695){
                tv_getCode.setText("获取验证码("+i+")");
                i--;
                tv_getCode.setEnabled(false);
                if (i >= 0) {
                    handler.sendEmptyMessageDelayed(12695, 1000);
                }else if (i < 0){
                    i = 59;
                    tv_getCode.setEnabled(true);
                    tv_getCode.setText("获取验证码");
                }
            }else if (msg.what == 3344){
                initDateView();
            }
        }
    };

    @OnClick(R.id.ll_back)
    public void onClick1(View view){
        Intent intent = new Intent(getApplicationContext(),Detect2Activity.class);
        startActivity(intent);
    }
    @Override
    public void onCreate(FragmentManager manager, Bundle savedInstanceState) {
        type = getIntent().getIntExtra(TYPE,0);
        registerDataPresenter = new RegisterDataPresenter(this,this);
        getCodeDataPresenter = new GetCodeDataPresenter(this,this);
        getWeacher();
        handler.sendEmptyMessage(UPDATE_DATE);

        et_phone.addTextChangedListener(this);
        et_name.addTextChangedListener(this);
        et_vode.addTextChangedListener(this);
    }
    private void initDateView() {
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

    private void getWeacher() {
        WearcherDataPresenter dataPresenter = new WearcherDataPresenter(this,this);
        dataPresenter.wearcher();
    }
    @OnClick({R.id.tv_picket,R.id.tv_register,R.id.tv_getCode})
    public void onClick(View view){
        handler.removeCallbacks(runable);
        switch (view.getId()){
            case R.id.tv_picket:
                toCamera();
                break;
            case R.id.tv_register:
                register();
                break;
            case R.id.tv_getCode:
                getCode();

                break;

        }
    }

    private void getCode() {
        String phone = et_phone.getText().toString().trim();
        if (!TextUtils.isEmpty(phone) && phone.length() == 11) {
            getCodeDataPresenter.getCode(phone);
        }else {
            showToast("请输入手机号");
        }
    }

    private void register() {

        String phone = et_phone.getText().toString().trim();
        String vode = et_vode.getText().toString().trim();
        String name = et_name.getText().toString().trim();
        String path = getRealPathFromURI(imageUri);
        if (TextUtils.isEmpty(phone) || phone.length() != 11) {
            showToast("请输入手机号");
            return;
        }else if (TextUtils.isEmpty(vode)){
            showToast("请输入验证");
            return;
        }else if (TextUtils.isEmpty(name)){
            showToast("请输入姓名");
            return;
        }else if (TextUtils.isEmpty(path)){
            showToast("请拍照");
            return;
        }
        registerDataPresenter.register(phone,vode,name,type);

    }
    private Bitmap bitmap;
    @Override
    public void onRequestSuccessData(Object data) {
        if (data instanceof RegisterBean){
            if (((RegisterBean) data).code == 500){
                showToast("该手机号已注册，请更换手机号！");
                return;
            }
            handler.postDelayed(runable,1000*60*2);
            //TODO 上传图片
            final String token = ((RegisterBean) data).data.token;
            ACache.get(this).put("token",token);
            ThreadManager.getInstance().run(new BaseRunnable() {
                @Override
                public void run() {
                    super.run();
                    File file = new File(getRealPathFromURI(imageUri));
                    handler.removeCallbacks(runable);
                    bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    bitmap = Bitmap.createScaledBitmap(bitmap, 217, 200, true);
                    String path = saveImage(bitmap);
                    registerDataPresenter.upload(token,new File(path));
                }
            });

        }else if (data instanceof ImageBean){
            handler.postDelayed(runable,1000*60*2);
            if (bitmap != null) {
                bitmap.recycle();
                bitmap = null;
            }
            if (((ImageBean) data).code == 500){
                showToast(((ImageBean) data).message);
            }else if (((ImageBean) data).code == 200){
                startActivity(SucuessActivity.class);
            }
        }else if (data instanceof WearcherBean){
            tv_wendu.setText(((WearcherBean) data).data.wendu+"℃  "+((WearcherBean) data).data.forecast.get(0).type);
        }else if (data instanceof CodeBean){
            showToast("验证码已发送");
            handler.sendEmptyMessage(12695);
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
    @Override
    public void onError() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CAMERA){
            ImageUtil.display(imageUri.toString(),iv_scan);
        }
    }

    /**
     * 选择相机
     */
    public void toCamera() {
        imageUri = createImageUri(this);
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra("camerasensortype", 2); // 调用前置摄像头
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

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        handler.removeCallbacks(runable);
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        handler.postDelayed(runable,1000*60*2);
    }
    Runnable runable = new Runnable() {
        @Override
        public void run() {
            handler.removeCallbacksAndMessages(null);
            startActivity(new Intent(getApplicationContext(),SplashActivity.class));
        }
    };
}
