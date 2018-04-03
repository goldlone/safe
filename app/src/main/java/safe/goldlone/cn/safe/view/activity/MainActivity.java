package safe.goldlone.cn.safe.view.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.fe.library.TabContainerView;
import com.fe.library.adapter.DefaultAdapter;


import safe.goldlone.cn.safe.R;
import safe.goldlone.cn.safe.view.fragment.AntiTheftFragment;
import safe.goldlone.cn.safe.view.fragment.CommunicationFragment;
import safe.goldlone.cn.safe.view.fragment.HelpFragment;
import safe.goldlone.cn.safe.view.fragment.MineFragment;
import safe.goldlone.cn.safe.view.fragment.SafeFragment;


public class MainActivity extends AppCompatActivity {

    private TabContainerView tabContainerView;
    private int[] iconImageArray, selectedIconImageArray;
    private Fragment[] fragments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initToolBar();
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

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("标题");
        // 标题栏按钮
//        toolbar.inflateMenu(R.menu.base_toolbar_menu);
//        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                if(item.getItemId() == R.id.action_default) {
//                    tabContainerView.setAdapter(new DefaultAdapter(BarActivity.this, fragments, getSupportFragmentManager(), getResources().getStringArray(R.array.titleArray),
//                            getResources().getColor(R.color.colorPrimary), iconImageArray, selectedIconImageArray));
//                }
//                if(item.getItemId() == R.id.action_one) {
//                    tabContainerView.setAdapter(new ExampleOneAdapter(BarActivity.this, fragments, getSupportFragmentManager(),
//                            getResources().getStringArray(R.array.exampleOneArray)));
//                }
//                if(item.getItemId() == R.id.action_two) {
//                    tabContainerView.setAdapter(new ExampleTwoAdapter(BarActivity.this, fragments, getSupportFragmentManager(),
//                            mIconArray));
//                }
//                return true;
//            }
//        });
    }
}
