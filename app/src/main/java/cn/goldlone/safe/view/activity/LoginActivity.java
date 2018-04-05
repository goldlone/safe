package cn.goldlone.safe.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import cn.goldlone.safe.R;

/**
 * Created by CN on 2018/4/5.
 */

public class LoginActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
}
