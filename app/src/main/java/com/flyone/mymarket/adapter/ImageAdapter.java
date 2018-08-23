package com.flyone.mymarket.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.flyone.mymarket.CircleImageView;
import com.flyone.mymarket.R;
import com.flyone.mymarket.bean.Good;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wl624 on 2018/4/25.
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
    private Context mContext;
    private View mHeaderView=null,mFooterView=null;
    private List<Good> mData=new ArrayList<>();
    //private NewsView newsView;
    private int flagTheme;



    public ImageAdapter(Context context){
        this.mContext=context;


    }
    public void setLatestDate(List<Good> data) {
        mData.clear();
        mData=data;

        Log.e("setData","sxsx");
        this.notifyDataSetChanged();
    }



    @Override
    public ImageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {





        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item,parent,false);
        ImageAdapter.ViewHolder vh = new ImageAdapter.ViewHolder(view);
        return vh;

    }

    @Override
    public void onBindViewHolder(ImageAdapter.ViewHolder holder, int position) {


        Glide.with(mContext).load(mData.get(0).getGoodsImages().get(position))
                .error(R.mipmap.ic_bailu)
                .into(holder.imageView);
    }


    @Override
    public int getItemCount() {
        if(mData.size()==0){
            return mData.size();
        }else {
            return mData.get(0).getGoodsImages().size();
        }


    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        //private CardView cardView;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView=(ImageView)itemView.findViewById(R.id.imageView4);



        }
    }
}
