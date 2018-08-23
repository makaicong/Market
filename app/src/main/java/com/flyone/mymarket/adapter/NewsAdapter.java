package com.flyone.mymarket.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyone.mymarket.R;
import com.flyone.mymarket.bean.News;

import java.util.List;

/**
 * Created by Jacky Fu on 2018/4/23.
 */

public class NewsAdapter extends ArrayAdapter<News> {

    private int resourceId;

    public NewsAdapter(Context context, int textViewResourceId,
                       List<News> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        News news = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.userIcon = (ImageView) view.findViewById(R.id.news_userIcon);
            viewHolder.userNews = (TextView) view.findViewById(R.id.news_news);
            viewHolder.goodsImage = (ImageView) view.findViewById(R.id.news_goodsimage);
            viewHolder.userName = (TextView) view.findViewById(R.id.news_username);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.userIcon.setImageResource(news.getImageId());
        viewHolder.userName.setText(news.getName());
        viewHolder.userNews.setText(news.getLastNews());
        viewHolder.goodsImage.setImageResource(news.getThingImageId());
        return view;
    }

    class ViewHolder {
        ImageView userIcon;
        ImageView goodsImage;
        TextView userName;
        TextView userNews;
    }


}
