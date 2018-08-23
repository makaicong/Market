package com.flyone.mymarket.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allure.lbanners.LMBanners;
import com.allure.lbanners.transformer.TransitionEffect;
import com.allure.lbanners.utils.ScreenUtils;
import com.flyone.mymarket.activity.SearchActivity;
import com.flyone.mymarket.bean.Config;
import com.flyone.mymarket.bean.Good;
import com.flyone.mymarket.LocalImgAdapter;
import com.flyone.mymarket.R;
import com.flyone.mymarket.RecyclerAdapter;
import com.flyone.mymarket.RoundRect;
import com.flyone.mymarket.activity.LoginActivity;
import com.flyone.mymarket.presenter.MyPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wl624 on 2018/4/16.
 */

public class MainFragment extends Fragment {

    private RecyclerView recyclerView;
    private MyPresenter myPresenter=null;
    private LMBanners mLBanners;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<Integer> localImages = new ArrayList<Integer>();
    private Handler handler=new Handler();
    private RecyclerAdapter recyclerAdapter;
    RoundRect roundRect;

    private String latestTime=null;
    private LinearLayoutManager linearLayoutManager;

    public MainFragment() {
    }

    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_main,null);

        int statusBarHeight1 = -1;
        //获取status_bar_height资源的ID
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight1 = getResources().getDimensionPixelSize(resourceId);
        }
        LinearLayout linearLayout=(LinearLayout)root.findViewById(R.id.bar01);

        RelativeLayout.LayoutParams layoutParams1=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ScreenUtils.dip2px(getContext(),40));
        linearLayout.setLayoutParams(layoutParams1);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        swipeRefreshLayout=(SwipeRefreshLayout)root.findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(android.R.color.white);
        swipeRefreshLayout.setColorSchemeColors(Color.BLUE,
                Color.GREEN,
                Color.YELLOW,
                Color.RED);
        swipeRefreshLayout.setDistanceToTriggerSync(350);
        swipeRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT);
        swipeRefreshLayout.setRefreshing(true);
        myPresenter=new MyPresenter(getContext());
        myPresenter.setView(mvp_view);
        myPresenter.queryAndGetLatestGoods();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                myPresenter.setView(mvp_view);
                myPresenter.queryAndGetLatestGoods();
                //myPresenter.getGoods();
            }
        });

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        },3000);
        recyclerView=(RecyclerView)root.findViewById(R.id.recyclerView_Main);
        linearLayoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addOnScrollListener(mOnScrollListener);
        recyclerAdapter=new RecyclerAdapter(getContext());
        View bannerAndOther=inflater.inflate(R.layout.banner_and_other,null);
        RelativeLayout relativeLayoutbnnn=bannerAndOther.findViewById(R.id.bannnnn);
        LinearLayout.LayoutParams layoutParamqq=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ScreenUtils.dip2px(getContext(),40));
        relativeLayoutbnnn.setLayoutParams(layoutParamqq);
        mLBanners=(LMBanners)bannerAndOther.findViewById(R.id.lmBanner);

        mLBanners.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ScreenUtils.dip2px(getContext(), 250)));



        //参数设置
        mLBanners.isGuide(false);//是否为引导页
        mLBanners.setAutoPlay(true);//自动播放
        mLBanners.setVertical(false);//是否锤子播放
        mLBanners.setScrollDurtion(2000);//两页切换时间
        mLBanners.setCanLoop(true);//循环播放
        //mLBanners.setSelectIndicatorRes(R.drawable.guide_indicator_select);//选中的原点
        //mLBanners.setUnSelectUnIndicatorRes(R.drawable.guide_indicator_unselect);//未选中的原点
        //若自定义原点到底部的距离,默认20,必须在setIndicatorWidth之前调用
        mLBanners.setIndicatorBottomPadding(30);
        mLBanners.setIndicatorWidth(10);//原点默认为5dp
        mLBanners.setHoriZontalTransitionEffect(TransitionEffect.ZoomOut);//选中喜欢的样式
//        mLBanners.setHoriZontalCustomTransformer(new ParallaxTransformer(R.id.id_image));//自定义样式
        mLBanners.setDurtion(5000);//轮播切换时间
//        mLBanners.hideIndicatorLayout();//隐藏原点
//        mLBanners.showIndicatorLayout();//显示原点
        mLBanners.setIndicatorPosition(LMBanners.IndicaTorPosition.BOTTOM_MID);//设置原点显示位置





        //本地用法
        localImages.add(R.drawable.ic_banner1);
        localImages.add(R.drawable.ic_banner2);
        localImages.add(R.drawable.ic_banner3);
        mLBanners.setAdapter(new LocalImgAdapter(getContext()), localImages);
        //网络图片
//        mLBanners.setAdapter(new UrlImgAdapter(MainActivity.this), networkImages);

        recyclerView.setAdapter(recyclerAdapter);


        List<Good> list=new ArrayList<>();
        for(int i=0;i<5;i++){
            Good goods=new Good();
            list.add(goods);
        }





        //recyclerAdapter.setDate(list);
        recyclerAdapter.setHeaderView(bannerAndOther);


        linearLayout.setBackgroundResource(R.color.colorAccent);
        TextView textView=new TextView(getContext());
        LinearLayout.LayoutParams layoutParams2=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,statusBarHeight1);
        textView.setLayoutParams(layoutParams2);
        //linearLayout.addView(textView);

        RelativeLayout relativeLayout=new RelativeLayout(getContext());
        LinearLayout.LayoutParams layoutParams3=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ScreenUtils.dip2px(getContext(),40));
        relativeLayout.setLayoutParams(layoutParams3);
        linearLayout.addView(relativeLayout);
        relativeLayout.setElevation(55);



        ImageView moreView=new ImageView(getContext());
        RelativeLayout.LayoutParams layoutParam5=new RelativeLayout.LayoutParams(ScreenUtils.dip2px(getContext(),30),ScreenUtils.dip2px(getContext(),30));
        layoutParam5.addRule(RelativeLayout.CENTER_VERTICAL);
        layoutParam5.setMarginEnd(ScreenUtils.dip2px(getContext(),5));
        layoutParam5.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        moreView.setLayoutParams(layoutParam5);
        moreView.setId(R.id.moreView);
        moreView.setBackgroundResource(R.color.colorPrimary);
        //relativeLayout.addView(moreView);


        roundRect=new RoundRect(getContext());
        RelativeLayout.LayoutParams layoutParams4=new RelativeLayout.LayoutParams(ScreenUtils.dip2px(getContext(),300),ScreenUtils.dip2px(getContext(),30));
        layoutParams4.setMarginEnd(ScreenUtils.dip2px(getContext(),10));
        layoutParams4.addRule(RelativeLayout.CENTER_VERTICAL);
        layoutParams4.addRule(RelativeLayout.CENTER_HORIZONTAL);
        //layoutParams4.addRule(RelativeLayout.LEFT_OF,moreView.getId());
        roundRect.setLayoutParams(layoutParams4);

        roundRect.setElevation(5);
        relativeLayout.addView(roundRect);
        roundRect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),SearchActivity.class);
                startActivity(intent);
            }
        });

        TextView textView3=new TextView(getContext());
        RelativeLayout.LayoutParams layoutParams33=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textView3.setTextSize(ScreenUtils.dip2px(getContext(),20));
        textView3.setTextColor(0xffeee);
        textView3.setText("华为");
        layoutParams33.addRule(Gravity.CENTER);
        layoutParams33.addRule(RelativeLayout.CENTER_HORIZONTAL);
        layoutParams33.addRule(RelativeLayout.CENTER_VERTICAL);
        textView.setLayoutParams(layoutParams33);
        relativeLayout.addView(textView3);
        return root;
    }
    public RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        private int lastVisibleItem;
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            lastVisibleItem=linearLayoutManager.findLastVisibleItemPosition();
        }
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE&&lastVisibleItem +1 ==recyclerAdapter.getItemCount()) {
                System.out.println("loading more data"+lastVisibleItem);

                if(!Config.isQuerying){
                    myPresenter.queryAndGetGoodsByTime(latestTime);
                }


                //myPresenter.attachView(newsView);

            }
        }
    };
    private MVP_View mvp_view=new MVP_View() {
        @Override
        public void onLatestSuccess(List<Good> list) {
            swipeRefreshLayout.setRefreshing(false);
            recyclerAdapter.setLatestDate(list);
            latestTime=list.get(list.size()-1).getCreatedAt();
            Config.isQuerying=false;

        }

        @Override
        public void onMoreSuccess(List<Good> list) {
            recyclerAdapter.setMoreDate(list);
            latestTime=list.get(list.size()-1).getCreatedAt();
            Config.isQuerying=false;
        }

        @Override
        public void onFailure() {

            Config.isQuerying=false;
        }

    };
}
