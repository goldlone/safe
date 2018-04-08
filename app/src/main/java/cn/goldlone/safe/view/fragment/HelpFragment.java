package cn.goldlone.safe.view.fragment;

import android.content.Intent;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import cn.goldlone.safe.R;

/**
 * 紧急求救
 * Created by CN on 2018/4/2.
 */

public class HelpFragment extends Fragment implements View.OnClickListener {

    private View rootView;

    private LinearLayout ll_help_location = null;
    private LinearLayout ll_help_knowledge = null;
    private LinearLayout ll_help_light = null;
    private LinearLayout ll_help_quiet = null;
    private LinearLayout ll_help_video = null;

    private ImageView iv_help_location = null;
    private ImageView iv_help_light = null;
    private ImageView iv_help_quiet = null;
    private ImageView iv_help_video = null;

    private boolean sw_light = false;
    private boolean sw_quiet = false;
    private boolean sw_video = false;

    // 闪光灯
    private Camera camera = null;
    boolean isPreview = false;
    private int cnum;
    private TimerTask mTimerTask = null;
    private Timer mTimer = null;
    private boolean isSOSOn = false;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView =  inflater.inflate(R.layout.fragment_help, container, false);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        initView();
    }

    private void initView() {
        ll_help_location = (LinearLayout) rootView.findViewById(R.id.ll_help_location);
        ll_help_knowledge = (LinearLayout) rootView.findViewById(R.id.ll_help_knowledge);
        ll_help_light = (LinearLayout) rootView.findViewById(R.id.ll_help_light);
        ll_help_quiet = (LinearLayout) rootView.findViewById(R.id.ll_help_quiet);
        ll_help_video = (LinearLayout) rootView.findViewById(R.id.ll_help_video);
        ll_help_location.setOnClickListener(this);
        ll_help_knowledge.setOnClickListener(this);
        ll_help_light.setOnClickListener(this);
        ll_help_quiet.setOnClickListener(this);
        ll_help_video.setOnClickListener(this);

//        iv_help_location = (ImageView) rootView.findViewById(R.id.iv_help_location);
        iv_help_light = (ImageView) rootView.findViewById(R.id.iv_help_light);
        iv_help_quiet = (ImageView) rootView.findViewById(R.id.iv_help_quiet);
        iv_help_video = (ImageView) rootView.findViewById(R.id.iv_help_video);


    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.ll_help_location:
                Toast.makeText(getActivity(), "定位求救", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_help_knowledge:
                Toast.makeText(getActivity(), "安全小知识", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_help_light:
                isC();
                if (isSOSOn) {
                    if (mTimer != null) {
                        mTimer.cancel();
                        mTimer = null;
                    }
                    if (mTimerTask != null) {
                        mTimerTask.cancel();
                        mTimerTask = null;
                    }
                    Camera.Parameters p = camera.getParameters();
                    p.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                    camera.setParameters(p);
                    camera.stopPreview();
                    isSOSOn = false;
                    cnum = 0;
                } else {
                    if (mTimer == null) {
                        mTimer = new Timer();
                    }
                    if (mTimerTask == null) {
                        SetTimerTask();
                    }
                    if (mTimer != null && mTimerTask != null) {
                        mTimer.schedule(mTimerTask, 0, 50);
                        isSOSOn = true;
                        cnum = 0;
                    }
                }
                if(sw_light) {
//                    Toast.makeText(getActivity(), "关闭闪光灯", Toast.LENGTH_SHORT).show();
                    sw_light = false;
                    iv_help_light.setImageResource(R.mipmap.light_none);
                } else {
                    sw_light = true;
//                    Toast.makeText(getActivity(), "打开闪光灯", Toast.LENGTH_SHORT).show();
                    iv_help_light.setImageResource(R.mipmap.light_act);
                }
                break;
            case R.id.ll_help_quiet:
                if(sw_quiet) {
                    sw_quiet = false;
                    Toast.makeText(getActivity(), "关闭静默模式", Toast.LENGTH_SHORT).show();
                    iv_help_quiet.setImageResource(R.mipmap.quiet_none);
                } else {
                    sw_quiet = true;
                    Toast.makeText(getActivity(), "启动静默模式", Toast.LENGTH_SHORT).show();
                    iv_help_quiet.setImageResource(R.mipmap.quiet_act);
                }
                break;
            case R.id.ll_help_video:
                if(sw_video) {
                    sw_video = false;
                    Toast.makeText(getActivity(), "关闭隐蔽录像", Toast.LENGTH_SHORT).show();
                    iv_help_video.setImageResource(R.mipmap.video_none);
                } else {
                    sw_video = true;
                    Toast.makeText(getActivity(), "启动隐蔽录像", Toast.LENGTH_SHORT).show();
                    iv_help_video.setImageResource(R.mipmap.video_act);
                }
                break;
        }
    }



    public void isC() {
        FeatureInfo[] feature = getActivity().getPackageManager()
                .getSystemAvailableFeatures();
        for (FeatureInfo featureInfo : feature) {
            if (PackageManager.FEATURE_CAMERA_FLASH.equals(featureInfo.name)) {
                break;
            }
        }
    }

    void SetTimerTask() {
        if (camera != null) {
            if (isPreview)
                camera.stopPreview();
            camera.release();
            camera = null;
        }
        camera = Camera.open();
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                Camera.Parameters p = camera.getParameters();
                switch (cnum) {
                    case 0:
                    case 4:
                    case 8:
                    case 14:
                    case 22:
                    case 30:
                        p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                        camera.setParameters(p);
                        camera.startPreview();
                        break;
                    case 2:
                    case 6:
                    case 10:
                    case 18:
                    case 26:
                    case 34:
                        p.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                        camera.setParameters(p);
                        camera.startPreview();
                        break;
                    default:
                        break;
                }
                if (cnum == 35)
                    cnum = 0;
                else
                    cnum++;
            }
        };
    }




}
