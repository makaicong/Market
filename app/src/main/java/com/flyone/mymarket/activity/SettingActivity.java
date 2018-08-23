package com.flyone.mymarket.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.flyone.mymarket.R;
import com.flyone.mymarket.utils.SetName;

import cn.bmob.v3.BmobUser;

/**
 * Created by wl624 on 2018/4/25.
 */

public class SettingActivity extends AppCompatActivity {
    private Button button;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        button=(Button)findViewById(R.id.sign_out);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobUser.logOut();
                SetName setName=SetName.getInstance(getApplicationContext());
                setName.setUserPhoto(" ");
                setName.setUserName("请登录");

                finish();
            }
        });
    }
}
