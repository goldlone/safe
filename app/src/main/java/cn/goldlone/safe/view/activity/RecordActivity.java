package cn.goldlone.safe.view.activity;


import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.SaveCallback;

import java.io.FileNotFoundException;
import java.util.List;

import cn.goldlone.safe.R;
import cn.goldlone.safe.help.RecordThread;
import cn.goldlone.safe.help.ScreenObserver;
import cn.goldlone.safe.utils.FileSave;

/**
 * class name：RecordDemoActivity<BR>
 * class description：demo<BR>
 * <BR>
 * @author bcaiw
 * @version 1.0 2012/08/02
 */
public class RecordActivity extends Activity implements
        SurfaceHolder.Callback {

    private SurfaceView surfaceview;// 视频预览控件
    private LinearLayout lay; // 愿揽控件的
    private SurfaceHolder surfaceHolder; // 和surfaceView相关的
    RecordThread thread;
    private static AudioManager audioManager = null;
    private ScreenObserver mScreenObserver;

    /**
     * onCreate方法
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_vedio);

        // 初始化控件
        if (thread == null) {
            // init();
            mScreenObserver = new ScreenObserver(this);
            mScreenObserver.requestScreenStateUpdate(new ScreenObserver.ScreenStateListener() {

                public void onScreenOn() {
                    init();
                }

                public void onScreenOff() {
                    if (thread != null) {
                        thread.stopRecord();
                        finish();
                    }
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 停止监听screen状态
        mScreenObserver.stopScreenStateUpdate();
    }

    @Override
    protected void onPause() {
        if (thread != null)
            thread.stopRecord();
        else {
            thread = new RecordThread(60 * 1000, surfaceview, surfaceHolder);
            thread.start();
        }
        super.onPause();
    }

    /**
     * 初始化控件以及回调
     */
    private void init() {
        surfaceview = (SurfaceView) this.findViewById(R.id.surfaceView);
        // 不显示拍摄内容
        lay = (LinearLayout) this.findViewById(R.id.lay);
        lay.setVisibility(LinearLayout.INVISIBLE);
        SurfaceHolder holder = this.surfaceview.getHolder();// 取得holder
        holder.addCallback(this); // holder加入回调接口
        // 设置setType
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        // 将holder，这个holder为开始在oncreat里面取得的holder，将它赋给surfaceHolder
        surfaceHolder = holder;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // 将holder，这个holder为开始在oncreat里面取得的holder，将它赋给surfaceHolder
        surfaceHolder = holder;
        Log.i("process", Thread.currentThread().getName());
        // 录像线程，当然也可以在别的地方启动，但是一定要在onCreate方法执行完成以及surfaceHolder被赋值以后启动
        boolean sdCardExist = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
        if (!sdCardExist) {
            Toast.makeText(RecordActivity.this, "请检查是否插入SD卡..,",
                    Toast.LENGTH_SHORT).show();
        } else {
            thread = new RecordThread(60 * 1000, surfaceview, surfaceHolder);
            thread.start();
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // surfaceDestroyed的时候同时对象设置为null
        surfaceview = null;
        surfaceHolder = null;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_HOME:
                if (thread != null)
                    thread.stopRecord();
                else
                    finish();

                break;
            case KeyEvent.KEYCODE_BACK:
                if (thread != null) {
                    thread.stopRecord();
                    try {
                        final AVFile file = AVFile.withAbsoluteLocalPath("video.mp4", FileSave.getRootPath() + "video.mp4");
                        Log.e("文件", file.toString());
                        Log.e("文件URL：", file.getUrl());
//                        file.saveInBackground(new SaveCallback() {
//                            @Override
//                            public void done(AVException e) {
//                                sendMessage(file.getUrl());
//                            }
//                        });
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    finish();
                }
                Log.e("隐秘录像", "结束了。。。");
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 发送求救短信
     * @param help
     */
    private void sendMessage(final String help) {
        String con = FileSave.getContact();
        if(con==null && con.equals("")){
            Toast.makeText(this, "请设置紧急联系人", Toast.LENGTH_SHORT).show();
            return;
        }
        sendSMS(con, help);
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:"+FileSave.getContact()));

        intent.putExtra("sms_body", help);
        startActivity(intent);
    }

    public void sendSMS(String phoneNumber, String message){
        //获取短信管理器
        android.telephony.SmsManager smsManager = android.telephony.SmsManager.getDefault();
        //拆分短信内容（手机短信长度限制）
        List<String> divideContents = smsManager.divideMessage(message);
        for (String text : divideContents) {
            smsManager.sendTextMessage(phoneNumber, null, text, null, null);
        }
    }
}
