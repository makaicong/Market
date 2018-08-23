package com.flyone.mymarket.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.flyone.mymarket.R;
import com.flyone.mymarket.adapter.SearchAdapter;
import com.flyone.mymarket.bean.Good;
import com.flyone.mymarket.presenter.MyPresenter;

import java.util.List;

/**
 * Created by Jacky Fu on 2018/4/21.
 */

public class CommunityFragment extends Fragment {
    private static final String TAG = "CommunityFragment";
    private ImageView myCollege,tongcheng,other;
    private RecyclerView recyclerView;
    public CommunityFragment() {

    }

    private SearchAdapter searchAdapter;
    private MyPresenter myPresenter;
    private MVP_View mvp_view=new MVP_View() {
        @Override
        public void onLatestSuccess(List<Good> list) {
            searchAdapter.setLatestDate(list);
        }

        @Override
        public void onMoreSuccess(List<Good> list) {

        }

        @Override
        public void onFailure() {

        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_community,null);
        myCollege=(ImageView) root.findViewById(R.id.community_my_college);
        tongcheng = (ImageView) root.findViewById(R.id.community_my_tongcheng);
        other = (ImageView) root.findViewById(R.id.community_my_other);
        //ImageView otherWay = (ImageView) root.findViewById(R.id.community_other_way);
        myCollege.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(getContext(), AdvertisingActivity.class);
                //startActivity(intent);
            }
        });
        tongcheng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ///Intent intent = new Intent(getContext(), MyCollegeActivity.class);
                //startActivity(intent);
            }
        });

        other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(getContext(), OtherWayActivity.class);
                //startActivity(intent);
            }
        });
        recyclerView=(RecyclerView)root.findViewById(R.id.communityRecyclerView);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        searchAdapter=new SearchAdapter(getContext());
        recyclerView.setAdapter(searchAdapter);
        myPresenter=new MyPresenter(getContext());
        myPresenter.setView(mvp_view);
        myPresenter.queryAndGetGoodByWhere("goodCity","合肥市");

        return root;
    }
}
