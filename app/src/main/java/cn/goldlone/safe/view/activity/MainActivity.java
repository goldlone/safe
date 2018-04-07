package cn.goldlone.safe.view.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

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
}
