package com.flyone.mymarket.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


import com.flyone.mymarket.R;
import com.flyone.mymarket.adapter.NewsAdapter;
import com.flyone.mymarket.bean.News;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jacky Fu on 2018/4/23.
 */

public class NewsFragment extends Fragment {
    private List<News> newsList = new ArrayList<>();
    public NewsFragment() {
        super();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_news,null);
        initNews();
        NewsAdapter adapter = new NewsAdapter(getContext(), R.layout.news_item, newsList);
        ListView listView = (ListView) root.findViewById(R.id.fragment_news_listview);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News news = newsList.get(position);
                Toast.makeText(getContext(), news.getName(),Toast.LENGTH_SHORT).show();
            }
        });
        return  root;
    }


    private void initNews() {
        News n1 = new News(R.drawable.news_user_1,"小马","你好！",R.drawable.news_goods_1);
        newsList.add(n1);
        News n2 = new News(R.drawable.news_user_2,"伏佳豪","在吗！我想问个问题",R.drawable.news_goods_2);
        newsList.add(n2);
        News n3 = new News(R.drawable.news_user_3,"王磊","要不350元吧",R.drawable.news_goods_3);
        newsList.add(n3);
        News n4 = new News(R.drawable.news_user_4,"张辉","现在几点了",R.drawable.news_goods_4);
        newsList.add(n4);
        News n5 = new News(R.drawable.news_user_5,"杨佳文","好看，可以考虑",R.drawable.news_goods_5);
        newsList.add(n5);

    }



}
