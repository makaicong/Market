package com.flyone.mymarket.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.flyone.mymarket.R;
import com.flyone.mymarket.RecyclerAdapter;
import com.flyone.mymarket.adapter.SearchAdapter;
import com.flyone.mymarket.bean.Good;
import com.flyone.mymarket.fragment.MVP_View;
import com.flyone.mymarket.presenter.MyPresenter;

import java.util.List;

import scut.carson_ho.searchview.ICallBack;
import scut.carson_ho.searchview.SearchView;
import scut.carson_ho.searchview.bCallBack;

/**
 * Created by wl624 on 2018/4/25.
 */

public class SearchActivity extends AppCompatActivity {
    private SearchView searchView;
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
        setContentView(R.layout.activity_search);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(0xFFFF4081);
        }
        myPresenter=new MyPresenter(getApplicationContext());
        myPresenter.setView(mvp_view);
        recyclerView=(RecyclerView)findViewById(R.id.recyclerview4);

        linearLayoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        //recyclerView.addOnScrollListener(mOnScrollListener);
        recyclerAdapter=new SearchAdapter(getApplicationContext());
        recyclerView.setAdapter(recyclerAdapter);
        searchView=(SearchView)findViewById(R.id.search_view);
        searchView.setOnClickSearch(new ICallBack() {
            @Override
            public void SearchAciton(String string) {
                //System.out.println("我收到了" + string);
                myPresenter.queryAndGetGoodByWhere("goodSecondkind",string);
            }
        });


        // 5. 设置点击返回按键后的操作（通过回调接口）
        searchView.setOnClickBack(new bCallBack() {
            @Override
            public void BackAciton() {
                finish();
            }
        });

    }
}
