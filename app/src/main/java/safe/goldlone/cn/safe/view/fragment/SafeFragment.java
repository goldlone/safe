package safe.goldlone.cn.safe.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import safe.goldlone.cn.safe.R;
import safe.goldlone.cn.safe.view.activity.HeartActivity;

/**
 * 安全检测
 * Created by CN on 2018/4/2.
 */

public class SafeFragment extends Fragment implements View.OnClickListener {

    //
    private View rootView;
    // 心率检测
    private LinearLayout ll_safe_heart_rate;
    // 足迹异常监测
    private LinearLayout ll_safe_foot_monitor;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_safe, container, false);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        initView();
    }

    private void initView() {
        ll_safe_heart_rate = rootView.findViewById(R.id.ll_safe_heart_rate);
        ll_safe_foot_monitor = rootView.findViewById(R.id.ll_safe_foot_monitor);

        ll_safe_heart_rate.setOnClickListener(this);
        ll_safe_foot_monitor.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_safe_heart_rate:
//                Toast.makeText(getContext(), "心率检测", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), HeartActivity.class));
                break;
            case R.id.ll_safe_foot_monitor:
                Toast.makeText(getContext(), "足迹异常监测", Toast.LENGTH_SHORT).show();
                break;
        }
    }



}
