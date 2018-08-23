package com.flyone.mymarket.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.OnApplyWindowInsetsListener;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.WindowInsetsCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flyone.mymarket.R;
import com.flyone.mymarket.bean.User;
import com.flyone.mymarket.fragment.CommunityFragment;
import com.flyone.mymarket.fragment.MainFragment;
import com.flyone.mymarket.fragment.NewsFragment;
import com.flyone.mymarket.fragment.PersonCenterFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

import cn.bmob.sms.BmobSMS;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private LinearLayout linearLayout;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    MainFragment mainFragment=new MainFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.content,mainFragment).commit();

                    return true;
                case R.id.navigation_kind:


                    return true;
                case R.id.navigation_dashboard:
                    User user= BmobUser.getCurrentUser(User.class);
                    if(user == null){
                        Intent intent=new Intent(MainActivity.this,LoginActivity .class);
                        startActivity(intent);
                    }else {
                        Intent intent=new Intent(MainActivity.this,PublishActivity.class);
                        startActivity(intent);
                    }
                    return false;
                case R.id.navigation_notifications:

                    PersonCenterFragment personCenterFragment=new PersonCenterFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.content,personCenterFragment).commit();
                    return true;
            }
            return false;
        }

    };

    private BottomBar bottomBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        BmobSMS.initialize(getApplicationContext(),"bab2a53e736fa27bc6c420234c2b8ace");
        Bmob.initialize(getApplicationContext(), "bab2a53e736fa27bc6c420234c2b8ace");
        setContentView(R.layout.activity_main);

        bottomBar=(BottomBar)findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId) {
                    case R.id.tab1:
                        //toast.setText("tab1");
                        //toast.show();
                        MainFragment mainFragment=new MainFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.content,mainFragment).commit();

                        break;
                    case R.id.tab2:

                        CommunityFragment communityFragment=new CommunityFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.content,communityFragment).commit();

                        //toast.setText("tab2");
                        //toast.show();
                        break;
                    case R.id.tab3:
                        User user= BmobUser.getCurrentUser(User.class);
                        if(user == null){
                            Intent intent=new Intent(MainActivity.this,LoginActivity .class);
                            startActivity(intent);
                        }else {
                            Intent intent=new Intent(MainActivity.this,PublishActivity.class);
                            startActivity(intent);
                        }
                        //toast.setText("tab2");
                        //toast.show();
                        break;
                    case R.id.tab4:
                        NewsFragment newsFragment=new NewsFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.content,newsFragment).commit();

                        //toast.setText("tab2");
                        //toast.show();
                        break;
                    case R.id.tab5:
                        PersonCenterFragment personCenterFragment=new PersonCenterFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.content,personCenterFragment).commit();

                        //toast.setText("tab2");
                        //toast.show();
                        break;
                }
            }
        });
        bottomBar.setOnTabReselectListener(new OnTabReselectListener() {
            @Override
            public void onTabReSelected(@IdRes int tabId) {
                switch (tabId) {
                    case R.id.tab3:
                        User user= BmobUser.getCurrentUser(User.class);
                        if(user == null){
                            Intent intent=new Intent(MainActivity.this,LoginActivity .class);
                            startActivity(intent);
                        }else {
                            Intent intent=new Intent(MainActivity.this,PublishActivity.class);
                            startActivity(intent);
                        }
                        //toast.setText("tab2");
                        //toast.show();
                        break;
                }
            }
        });
        linearLayout=(LinearLayout)findViewById(R.id.container);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(0xFFFF4081);
        }

        //Goods goods=new Goods();
        //goods.getObjectId();
        /**
        ViewCompat
                .setOnApplyWindowInsetsListener(linearLayout, new OnApplyWindowInsetsListener() {

                    @Override
                    public WindowInsetsCompat onApplyWindowInsets(View v,
                                                                  WindowInsetsCompat insets) {
                        return insets.consumeSystemWindowInsets();
                    }
                });

         **/

        //BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        //navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
