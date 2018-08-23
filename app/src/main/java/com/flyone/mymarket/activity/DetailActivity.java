package com.flyone.mymarket.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.flyone.mymarket.CircleImageView;
import com.flyone.mymarket.R;
import com.flyone.mymarket.adapter.ImageAdapter;
import com.flyone.mymarket.bean.Good;
import com.flyone.mymarket.fragment.MVP_View;
import com.flyone.mymarket.presenter.MyPresenter;

import java.util.List;

/**
 * Created by wl624 on 2018/4/25.
 */

public class DetailActivity extends AppCompatActivity {
    private CollapsingToolbarLayout collapsingToolbarLayout01;
    private CoordinatorLayout coordinatorLayout;
    private TextView titleOfArticle;
    private Toolbar toolbar01;
    private AppBarLayout appBarLayout01;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private MyPresenter myPresenter;
    private ImageAdapter imageAdapter;
    private TextView name,college,price,dd;
    private MVP_View mvp_view=new MVP_View() {
        @Override
        public void onLatestSuccess(List<Good> list) {
            imageAdapter.setLatestDate(list);
            Glide.with(getApplicationContext()).load(list.get(0).getUserPhoto())
                    .error(R.mipmap.ic_bailu)
                    .into(circleImageView);
            name.setText(list.get(0).getUserName());
            college.setText(list.get(0).getGoodSchool());
            price.setText(list.get(0).getGoodPrice()+"¥");
            dd.setText(list.get(0).getGoodDescription());
        }

        @Override
        public void onMoreSuccess(List<Good> list) {

        }

        @Override
        public void onFailure() {

        }
    };
    private CircleImageView circleImageView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(0xFFFF4081);
        }
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        System.out.println(bundle.getString("oj"));
        coordinatorLayout=(CoordinatorLayout)findViewById(R.id.coordinatorLayout01);
        appBarLayout01=(AppBarLayout)findViewById(R.id.appBarLayout01);
        toolbar01=(Toolbar)findViewById(R.id.toolbarOfArticle);
        //toolbar01.setNavigationIcon(R.mipmap.ic_launcher);
        setSupportActionBar(toolbar01);
        toolbar01.setTitleTextColor(Color.WHITE);
        toolbar01.setTitle("dsxxsx");
        toolbar01.setNavigationIcon(R.mipmap.back);

        toolbar01.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        recyclerView=(RecyclerView) findViewById(R.id.recyclerview9);
        linearLayoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        imageAdapter=new ImageAdapter(getApplicationContext());
        recyclerView.setAdapter(imageAdapter);

        myPresenter=new MyPresenter(getApplicationContext());
        myPresenter.setView(mvp_view);
        myPresenter.queryAndGetGoodById(bundle.getString("oj"));
        circleImageView=(CircleImageView)findViewById(R.id.goods_userIcon);

        name=(TextView)findViewById(R.id.goods_user);

        college=(TextView)findViewById(R.id.goods_college);
        price=(TextView)findViewById(R.id.goods_price);
        dd=(TextView)findViewById(R.id.textView5);

    }


}
