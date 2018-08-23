package com.flyone.mymarket.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyone.mymarket.R;
import com.flyone.mymarket.adapter.SearchAdapter;
import com.flyone.mymarket.bean.Good;
import com.flyone.mymarket.bean.User;
import com.flyone.mymarket.fragment.MVP_View;
import com.flyone.mymarket.presenter.MyPresenter;

import java.util.List;

import cn.bmob.v3.BmobUser;

/**
 * Created by wl624 on 2018/4/25.
 */

public class OtherActivity extends AppCompatActivity {
    private TextView textView;
    private ImageView imageView;

    private MyPresenter myPresenter;
    private RecyclerView recyclerView;
    private SearchAdapter recyclerAdapter;
    private LinearLayoutManager linearLayoutManager;
    private MVP_View mvp_view=new MVP_View() {
        @Override
        public void onLatestSuccess(List<Good> list) {
            recyclerAdapter.setLatestDate(list);

        }

        @Override
        public void onMoreSuccess(List<Good> list) {

        }

        @Override
        public void onFailure() {

        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();

        textView=(TextView)findViewById(R.id.textViewA);
        imageView=(ImageView)findViewById(R.id.imageViewA);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        textView.setText(bundle.getString("rr"));
        myPresenter=new MyPresenter(getApplicationContext());
        myPresenter.setView(mvp_view);
        recyclerView=(RecyclerView)findViewById(R.id.recyclerview99);

        linearLayoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        //recyclerView.addOnScrollListener(mOnScrollListener);
        recyclerAdapter=new SearchAdapter(getApplicationContext());
        recyclerView.setAdapter(recyclerAdapter);
        User user= BmobUser.getCurrentUser(User.class);
        myPresenter.queryAndGetGoodByWhere("userName",user.getUsername());

    }
}
