package com.ogmall.faceread.activity;

import android.Manifest;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.view.TextureView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.aip.ImageFrame;
import com.baidu.aip.face.CameraImageSource;
import com.baidu.aip.face.DetectRegionProcessor;
import com.baidu.aip.face.FaceDetectManager;
import com.baidu.aip.face.FaceFilter;
import com.baidu.aip.face.PreviewView;
import com.baidu.aip.face.TexturePreviewView;
import com.baidu.aip.face.camera.ICameraControl;
import com.baidu.aip.face.camera.PermissionCallback;
import com.baidu.idl.facesdk.FaceInfo;
import com.gwm.android.ACache;
import com.gwm.annotation.Layout;
import com.gwm.util.MyLogger;
import com.gwm.util.PermissionUtil;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.iflytek.sunflower.FlowerCollector;
import com.ogmall.faceread.AnimatorManager;
import com.ogmall.faceread.bean.FaceImgBean;
import com.ogmall.faceread.bean.WearcherBean;
import com.ogmall.faceread.datapresenter.FileDBDataPresenter;
import com.ogmall.faceread.datapresenter.WearcherDataPresenter;
import com.ogmall.faceread.widget.BrightnessTools;
import com.ogmalllarge.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;

/**
 * 实时检测人脸框并把检测到得人脸图片绘制在屏幕上，每10帧截图一张。
 * Intent intent = new Intent(MainActivity.this, DetectActivity.class);
 * startActivity(intent);
 */
@Layout(R.layout.activity_detect3)
public class Detect3Activity extends com.gwm.base.BaseActivity {

    private static final int MSG_INITVIEW = 1005;
    private static final int SSFG_ANIM = 2256;
    private static final int UPDATE_DATE = 5589;
    private static final int ANIM = 6666;
    private static final int YINCANGINFO = 55894;
    @BindView(R.id.preview_view)
    TexturePreviewView previewView;
    @BindView(R.id.texture_view)
    TextureView mTextureView;
    @BindView(R.id.tv_content)
    TextView tv_content;
    @BindView(R.id.tv_xingqi)
    TextView tv_xingqi;
    @BindView(R.id.tv_date)
    TextView tv_date;
    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.iv_scan)
    ImageView ivScan;
    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.rl_shenfen)
    RelativeLayout rl_shenfen;

    private FaceDetectManager faceDetectManager;
    private DetectRegionProcessor cropProcessor = new DetectRegionProcessor();
    private Paint paint = new Paint();
    private List<Bitmap> mList = new ArrayList<>();
    private int mScreenW;
    private int mScreenH;
    private int mRound;
    private boolean isCompressing = false;
    private boolean isUploading = false;
    private String path;
    private boolean mDetectStoped = false;

    private SpeechSynthesizer mTts;
    // 引擎类型
    private String mEngineType = SpeechConstant.TYPE_CLOUD;
    // 默认发音人
    private String voicer = "xiaoyan";
    private ACache aCache;

    private android.os.Handler handler = new android.os.Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MSG_INITVIEW:
                    start();
                    break;
                case UPDATE_DATE:
                    initDateView();
                    break;
                case ANIM:
                    startScanAmin();
                    break;
                case YINCANGINFO:
                    tv_content.setText("欢迎光临！");
//                    HwitManager.HwitSetIOValue(5, 0);
                    break;
            }
        }
    };
    @BindView(R.id.tv_wendu)
    TextView tv_wendu;


    @Override
    public void onCreate(FragmentManager manager,Bundle savedInstanceState) {
        hideNavigationBar();
        aCache = ACache.get(this);
        faceDetectManager = new FaceDetectManager(this);
        initScreen();
        initView();
        mTts = SpeechSynthesizer.createSynthesizer(this,mTtsInitListener);
        handler.sendEmptyMessage(UPDATE_DATE);
        handler.sendEmptyMessageDelayed(ANIM,500);
        handler.sendEmptyMessageDelayed(MSG_INITVIEW,1000);

        text.setText("人 脸 识 别");
        tv_content.setText("欢迎光临");
        rl_shenfen.setVisibility(View.INVISIBLE);


        checkPermission();

        getWeacher();
    }
    private void getWeacher() {
        WearcherDataPresenter dataPresenter = new WearcherDataPresenter(this,this);
        dataPresenter.wearcher();
    }
    private void checkPermission() {
        permission(new PermissionUtil.RequestPermissionCallback() {
                       @Override
                       public void onGranted() {

                       }

                       @Override
                       public void onDenied(Object... permission) {

                       }
                   },
                Manifest.permission.CAMERA,
                Manifest.permission.LOCATION_HARDWARE,Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_SETTINGS,Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO,Manifest.permission.READ_CONTACTS,
                Manifest.permission.WRITE_SETTINGS);
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

    /**
     * 初始化监听。
     */
    private InitListener mTtsInitListener = new InitListener() {
        @Override
        public void onInit(int code) {
            MyLogger.kLog().e("keda code="+code);
            if (code == ErrorCode.SUCCESS){
                showToast("语音模块初始化成功");
                setParam();
                FlowerCollector.onEvent(Detect3Activity.this, "tts_play");
//                startVoice("欢迎光临");
            }else{
                showToast("语音模块初始化失败");
            }
        }
    };
    @Override
    protected void onResume() {
        super.onResume();
        if (mDetectStoped) {
            faceDetectManager.start();
            mDetectStoped = false;
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        faceDetectManager.stop();
        mDetectStoped = true;
        int size = mList.size();
        for (int i = 0; i < size; i++) {
            Bitmap bmp = mList.get(i);
            if (bmp != null && !bmp.isRecycled()) {
                bmp.recycle();
            }
        }
        mList.clear();
    }
    /**
     * 启动人脸检测
     */
    private void start() {
        try {
            RectF newDetectedRect = new RectF(0, 0, mScreenW, mScreenH);
            cropProcessor.setDetectedRect(newDetectedRect);
            faceDetectManager.setOnFaceDetectListener(new FaceDetectManager.OnFaceDetectListener() {
                @Override
                public void onDetectFace(int status, FaceInfo[] infos, ImageFrame imageFrame) {

                }
            });
            faceDetectManager.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initScreen() {
        WindowManager manager = getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        mScreenW = outMetrics.widthPixels;
        mScreenH = outMetrics.heightPixels;
        mRound = getResources().getDimensionPixelSize(R.dimen.round);
    }
    private void initView() {
        mTextureView.setOpaque(false);

        // 不需要屏幕自动变黑。
        mTextureView.setKeepScreenOn(true);

        final CameraImageSource cameraImageSource = new CameraImageSource(this);
        cameraImageSource.setPreviewView(previewView);

        faceDetectManager.setImageSource(cameraImageSource);
        faceDetectManager.setOnTrackListener(new FaceFilter.OnTrackListener() {
            @Override
            public void onTrack(final FaceFilter.TrackedModel trackedModel) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showFrame(trackedModel);
                    }
                });

            }
        });

        cameraImageSource.getCameraControl().setPermissionCallback(new PermissionCallback() {
            @Override
            public boolean onRequestPermission() {
                ActivityCompat.requestPermissions(Detect3Activity.this,
                        new String[]{Manifest.permission.CAMERA}, 100);
                return true;
            }
        });


        ICameraControl control = cameraImageSource.getCameraControl();
        control.setPreviewView(previewView);
        // 设置检测裁剪处理器
        faceDetectManager.addPreProcessor(cropProcessor);

        int orientation = getResources().getConfiguration().orientation;
        boolean isPortrait = (orientation == Configuration.ORIENTATION_PORTRAIT);

        if (isPortrait) {
            previewView.setScaleType(PreviewView.ScaleType.FIT_WIDTH);
        } else {
            previewView.setScaleType(PreviewView.ScaleType.FIT_HEIGHT);
        }

        int rotation = getWindowManager().getDefaultDisplay().getRotation();
        cameraImageSource.getCameraControl().setDisplayOrientation(rotation);
        setCameraType(cameraImageSource);
        initBrightness();
        initPaint();
    }

    /**
     * 绘制人脸框。
     *
     * @param model 追踪到的人脸
     */
    private void showFrame(final FaceFilter.TrackedModel model) {
        Canvas canvas = mTextureView.lockCanvas();
        if (canvas == null) {
            return;
        }
        // 清空canvas
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

        if (model != null) {
            FaceInfo info = model.getInfo();
            model.getImageFrame().retain();
            RectF rectCenter = new RectF(info.mCenter_x - 2 - info.mWidth * 3 / 5,
                    info.mCenter_y - 2 - info.mWidth * 3 / 5,
                    info.mCenter_x + 2 + info.mWidth * 3 / 5,
                    info.mCenter_y + 2 + info.mWidth * 3 / 5);
            previewView.mapFromOriginalRect(rectCenter);
            // 绘制框
            //paint.setStrokeWidth(mRound);
            //paint.setAntiAlias(true);
            //canvas.drawRect(rectCenter, paint);

            if (model.meetCriteria()) {
                // 符合检测要求，绘制绿框
//                paint.setColor(Color.GREEN);
            }
            if (!isCompressing && !isUploading) {
                compressImage(model);
            }
        }
        mTextureView.unlockCanvasAndPost(canvas);
    }

    private void setCameraType(CameraImageSource cameraImageSource) {
        // TODO 选择使用前置摄像头
        cameraImageSource.getCameraControl().setCameraFacing(ICameraControl.CAMERA_FACING_FRONT);

        // TODO 选择使用usb摄像头
//        cameraImageSource.getCameraControl().setCameraFacing(ICameraControl.CAMERA_USB);
        // 如果不设置，人脸框会镜像，显示不准
        previewView.getTextureView().setScaleX(-1);

        // TODO 选择使用后置摄像头
        //cameraImageSource.getCameraControl().setCameraFacing(ICameraControl.CAMERA_FACING_BACK);
        //previewView.getTextureView().setScaleX(-1);
    }

    /**
     * 设置相机亮度，不够200自动调整亮度到200
     */
    private void initBrightness() {
        int brightness = BrightnessTools.getScreenBrightness(Detect3Activity.this);
        if (brightness < 200) {
            BrightnessTools.setBrightness(this, 200);
        }
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
    }
    /**
     * 获取头像并压缩
     *
     * @param model
     */
    private void compressImage(FaceFilter.TrackedModel model) {
        try {
            isCompressing = true;
            Bitmap face = model.cropFace();
            //  final Bitmap face =ImageUtil.bitmapFromArgb(model.getImageFrame());
            if (face != null) {
                int size = mList.size();
                // 释放一些，以防止太多
                if (size >= 4) {
                    Bitmap bmp = mList.get(size - 4);
                    if (bmp != null) {
                        bmp.recycle();
                        bmp = null;
                    }
                }
                //压缩图片
                face = Bitmap.createScaledBitmap(face, 217, 200, true);
                path = saveImage(face);
//                imageView.setImageBitmap(face);
//                if (s.toString().length() > 500) {
//                    s = new StringBuffer();
//                }
//                s.append("采集头像成功\n");
//                tv.setText(s.toString());
                if (!isUploading) {
                    //TODO 添加活体检测
                    faceimages(path);

                }
                mList.add(face);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            isCompressing = false;
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
     * 上传图片
     *
     * @param path 文件路劲
     */
    public void faceimages(String path) {

        isUploading = true;
        FileDBDataPresenter dataPresenter = new FileDBDataPresenter(this,this);
        dataPresenter.getInfoOut(path);
    }

    @Override
    public void onRequestSuccessData(Object data) {
        if (data instanceof FaceImgBean){
            FaceImgBean bean = (FaceImgBean) data;
            isUploading = false;
            if (bean.code == 200) {
                //TODO
                //TODO 保存人脸数据，跟上次的对比
                FaceImgBean bean1 = (FaceImgBean) aCache.getAsObject("renData");
                if (bean1 == null || bean1.data.name == null) {
                    aCache.put("renData", bean);
                    aCache.put("renDataTime",System.currentTimeMillis());
                }else {
                    long renDataTime = (long) aCache.getAsObject("renDataTime");
                    long currentTime = System.currentTimeMillis() - renDataTime;
                    if (bean1.data.name.equals(bean.data.name) && currentTime < 1000*60){  //判断3分钟内是同一人  不做欢迎处理  只是更新当地的保存人脸数据
                        aCache.put("renData", bean);
                        aCache.put("renDataTime",System.currentTimeMillis());
                        return;
                    }else {
                        aCache.put("renData", bean);
                        aCache.put("renDataTime",System.currentTimeMillis());
                    }
                }
                startVoice(bean.data.message);
//                HwitManager.HwitSetIOValue(5, 1);
                tv_content.setText(bean.data.name+",您好！\n再见！欢迎下次光临");
                handler.sendEmptyMessageDelayed(YINCANGINFO,5000);
//                startVoice(bean.data.name+"您好,再见！欢迎下次光临！");
//                if (bean.data.user_type == 1){  //员工
//                    tv_content.updateStyle(bean.data.name+",您好！\n欢迎入职欧工！");
//                    startVoice(bean.data.name+"您好,欢迎入职欧工");
//                }else if (bean.data.user_type == 2){  //面试
//                    tv_content.updateStyle(bean.data.name+",您好！\n请前往5楼找行政部，谢谢！");
//                    startVoice(bean.data.name+",您好！请前往5楼找行政部，谢谢！");
//                }else if (bean.data.user_type == 3){  //业务
//                    tv_content.updateStyle(bean.data.name+",您好！\n请前往5楼商务部进行接待，谢谢!");
//                    startVoice(bean.data.name+",您好!请前往5楼商务部进行接待,谢谢!");
//                }
            }
        }else if (data instanceof WearcherBean){
            tv_wendu.setText(((WearcherBean) data).data.wendu+"℃"+((WearcherBean) data).data.forecast.get(0).type);
        }
    }
    private void startVoice(String content){

        // 设置参数

        int code = mTts.startSpeaking(content, mTtsListener);
//			/**
//			 * 只保存音频不进行播放接口,调用此接口请注释startSpeaking接口
//			 * text:要合成的文本，uri:需要保存的音频全路径，listener:回调接口
//			*/
			/*String path = Environment.getExternalStorageDirectory()+"/tts.pcm";
			int code = mTts.synthesizeToUri(text, path, mTtsListener);*/

    }


    /**
     * 参数设置
     * @return
     */
    private void setParam(){
        // 清空参数
        mTts.setParameter(SpeechConstant.PARAMS, null);
        // 根据合成引擎设置相应参数
        if(mEngineType.equals(SpeechConstant.TYPE_CLOUD)) {
            mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
            mTts.setParameter(SpeechConstant.TTS_DATA_NOTIFY, "1");
            // 设置在线合成发音人
            mTts.setParameter(SpeechConstant.VOICE_NAME, voicer);
            //设置合成语速
            mTts.setParameter(SpeechConstant.SPEED, "50");
            //设置合成音调
            mTts.setParameter(SpeechConstant.PITCH, "50");
            //设置合成音量
            mTts.setParameter(SpeechConstant.VOLUME, "50");
        }else {
            mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_LOCAL);
            mTts.setParameter(SpeechConstant.VOICE_NAME, "");

        }
        //设置播放器音频流类型
        mTts.setParameter(SpeechConstant.STREAM_TYPE, "3");
        // 设置播放合成音频打断音乐播放，默认为true
        mTts.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "");

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        mTts.setParameter(SpeechConstant.AUDIO_FORMAT, "pcm");
        mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, Environment.getExternalStorageDirectory()+"/msc/tts.pcm");
    }
    /**
     * 合成回调监听。
     */
    private SynthesizerListener mTtsListener = new SynthesizerListener() {

        @Override
        public void onSpeakBegin() {

        }

        @Override
        public void onSpeakPaused() {

        }

        @Override
        public void onSpeakResumed() {

        }

        @Override
        public void onBufferProgress(int percent, int beginPos, int endPos,
                                     String info) {

        }

        @Override
        public void onSpeakProgress(int percent, int beginPos, int endPos) {

        }

        @Override
        public void onCompleted(SpeechError error) {

        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {


        }
    };

    @Override
    public void onError() {

    }

    private void startScanAmin() {
        int height1 = 0;
        int height2 = 0;
        if (height1 == 0) {
            height1 = view.getHeight();
            height2 = ivScan.getHeight();
        }
        AnimatorManager.startAnimotion(ivScan, -height2, height1, 2000, AnimatorManager.TRANSLATIONY);
        handler.sendEmptyMessageDelayed(ANIM,2000);
    }

}
