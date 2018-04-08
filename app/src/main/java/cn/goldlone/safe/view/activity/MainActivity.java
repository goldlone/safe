package cn.goldlone.safe.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.fe.library.TabContainerView;
import com.fe.library.adapter.DefaultAdapter;


import cn.goldlone.safe.R;
import cn.goldlone.safe.view.fragment.AntiTheftFragment;
import cn.goldlone.safe.view.fragment.CommunicationFragment;
import cn.goldlone.safe.view.fragment.HelpFragment;
import cn.goldlone.safe.view.fragment.MineFragment;
import cn.goldlone.safe.view.fragment.SafeFragment;


public class MainActivity extends AppCompatActivity {

    private TabContainerView tabContainerView;
    private int[] iconImageArray, selectedIconImageArray;
    private Fragment[] fragments;

    // 监听两次按键
    private long firstClickTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {

        iconImageArray = new int[]{
                R.mipmap.safe,
                R.mipmap.safe,
                R.mipmap.safe,
                R.mipmap.safe,
                R.mipmap.safe
        };
        selectedIconImageArray = new int[]{
                R.mipmap.safe_select,
                R.mipmap.safe_select,
                R.mipmap.safe_select,
                R.mipmap.safe_select,
                R.mipmap.safe_select
        };
        fragments = new Fragment[] {
                new CommunicationFragment(),
                new HelpFragment(),
                new AntiTheftFragment(),
                new SafeFragment(),
                new MineFragment()
        };

        tabContainerView = (TabContainerView) findViewById(R.id.tab_containerview_main);
        tabContainerView.setAdapter(
                new DefaultAdapter(this,
                        fragments,
                        getSupportFragmentManager(),
                        getResources().getStringArray(R.array.bottom_navication_titles),
                        getResources().getColor(R.color.colorPrimary),
                        iconImageArray,
                        selectedIconImageArray));

    }






    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_DOWN: // 发送求救短信
//                helpFragment.sendHelpMessage();
                return true;
            case KeyEvent.KEYCODE_VOLUME_UP: // 启动隐秘录像
                clickEvent();
            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 监听按两次下音量键
     */
    private void clickEvent() {
        Log.i("隐秘录像","点击下音量键： "+firstClickTime);
        long secondClickTime = System.currentTimeMillis();
        long dtime = secondClickTime - firstClickTime;
        if(dtime < 500){
            // 实现双击
//            Intent intent=new Intent(this, RecordActivity.class);
//            startActivity(intent);
            Toast.makeText(this, "开启隐秘录像", Toast.LENGTH_SHORT).show();
        } else{
            firstClickTime = System.currentTimeMillis();
        }
    }
}
