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
import com.flyone.mymarket.RecyclerAdapter;
import com.flyone.mymarket.bean.Good;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wl624 on 2018/4/25.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    public static final int TYPE_HEADER = 0;  //说明是带有Header的
    public static final int TYPE_FOOTER = 1;
    public static final int TYPE_NORMAL = 2;  //说明是不带有header和footer的

    private Context mContext;
    private View mHeaderView=null,mFooterView=null;
    private List<Good> mData=new ArrayList<>();
    //private NewsView newsView;
    private int flagTheme;


    public static interface OnItemClickListener {
        void onItemClick(View view , int position);
    }
    public SearchAdapter(Context context){
        this.mContext=context;


    }
    public void setLatestDate(List<Good> data) {
        mData.clear();
        for(int i=0;i<data.size();i++){
            Good good=new Good();
            good=data.get(i);
            mData.add(good);
        }

        Log.e("setData","sxsx");
        this.notifyDataSetChanged();
    }
    public void setMoreDate(List<Good> data) {
        for(int i=0;i<data.size();i++){
            Good good=new Good();
            good=data.get(i);
            mData.add(good);
        }

        Log.e("setData","sxsx");
        this.notifyDataSetChanged();
    }


    public View getHeaderView() {
        return mHeaderView;
    }

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }
    public View getFooterView(){
        return mFooterView;

    }
    public void setFooterView(View footerView){
        mFooterView = footerView;
        notifyItemChanged(getItemCount()-1);
    }



    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null && mFooterView == null){
            return TYPE_NORMAL;
        }else if(mHeaderView!=null) {
            if (position == 0){
                //第一个item应该加载Header
                return TYPE_HEADER;
            }
        }else if(mFooterView!=null){
            if (position == getItemCount()-1){
                //最后一个,应该加载Footer
                return TYPE_FOOTER;
            }
        }

        return TYPE_NORMAL;

    }


    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        if(mHeaderView != null && viewType == TYPE_HEADER) {
            return new SearchAdapter.ViewHolder(mHeaderView);
        }
        if(mFooterView != null && viewType == TYPE_FOOTER) {
            return new SearchAdapter.ViewHolder(mFooterView);
        }


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.goods_item,parent,false);
        SearchAdapter.ViewHolder vh = new SearchAdapter.ViewHolder(view);
        return vh;

    }

    @Override
    public void onBindViewHolder(SearchAdapter.ViewHolder holder, int position) {
        System.out.println(position+"aa");
        if(getItemViewType(position) == TYPE_NORMAL){
            System.out.println(position-1);
            //System.out.println(position+"  "+mData.get(position-1).imageStory[0]);


            //holder.textView.setText(mData.get(position-1).getStr());

            if(mData.size()==0){

                Glide.with(mContext).load(R.mipmap.ic_bailu).into(holder.imageView01);
                Glide.with(mContext).load(R.mipmap.ic_bailu).into(holder.imageView02);
                Glide.with(mContext).load(R.mipmap.ic_bailu).into(holder.imageView03);

            }else {

                if(mData.get(position).getGoodsImages().size()==1){
                    Glide.with(mContext).load(mData.get(position-1).getGoodsImages().get(0))
                            .error(R.mipmap.ic_launcher) //在图像加载失败时显示
                            .into(holder.imageView01);
                }else if(mData.get(position).getGoodsImages().size()==2){
                    Glide.with(mContext).load(mData.get(position).getGoodsImages().get(0))
                            .error(R.mipmap.ic_launcher) //在图像加载失败时显示
                            .into(holder.imageView01);
                    Glide.with(mContext).load(mData.get(position).getGoodsImages().get(1))
                            .error(R.mipmap.ic_launcher) //在图像加载失败时显示
                            .into(holder.imageView02);
                }else if(mData.get(position).getGoodsImages().size()==0){
                    Glide.with(mContext).load(R.mipmap.ic_bailu).into(holder.imageView01);
                }else {
                    Glide.with(mContext).load(mData.get(position).getGoodsImages().get(0))
                            .error(R.mipmap.ic_launcher) //在图像加载失败时显示
                            .into(holder.imageView01);
                    Glide.with(mContext).load(mData.get(position).getGoodsImages().get(1))
                            .error(R.mipmap.ic_launcher) //在图像加载失败时显示
                            .into(holder.imageView02);
                    Glide.with(mContext).load(mData.get(position).getGoodsImages().get(2))
                            .error(R.mipmap.ic_launcher) //在图像加载失败时显示
                            .into(holder.imageView03);
                }

                if(mData.get(position).getUserPhoto()!=null){
                    Glide.with(mContext).load(mData.get(position).getUserPhoto())
                            .error(R.mipmap.ic_bailu) //在图像加载失败时显示
                            .into(holder.userIcon);
                }else {
                    Glide.with(mContext).load(R.mipmap.ic_bailu)
                            .into(holder.userIcon);
                }
                holder.userName.setText(mData.get(position).getUserName());
                holder.goodCollege.setText(mData.get(position).getGoodSchool());
                holder.goodPrice.setText(mData.get(position).getGoodPrice()+"¥");
                holder.goodTitle.setText(mData.get(position).getGoodTitle());


            }



        }else if(getItemViewType(position) == TYPE_HEADER) {
            return;
        }else {
            return;
        }
    }


    @Override
    public int getItemCount() {
        if(mData==null){
            return 0;
        }else if(mHeaderView == null && mFooterView == null){
            //System.out.println(mData.size());
            return mData.size();
        }else if(mHeaderView == null && mFooterView != null){
            //System.out.println(mData.size()+1);
            return mData.size() + 1;
        }else if (mHeaderView != null && mFooterView == null){
            //System.out.println(mData.size()+1);
            return mData.size() + 1;
        }else {
            //System.out.println(mData.size()+2);
            return mData.size() + 2;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView01,imageView02,imageView03;
        private CircleImageView userIcon;
        private TextView userName,goodPrice,goodCollege,goodTitle;
        //private CardView cardView;
        public ViewHolder(View itemView) {
            super(itemView);
            if (itemView == mHeaderView){
                return;
            }
            if (itemView == mFooterView){
                return;
            }


            userIcon=(CircleImageView)itemView.findViewById(R.id.goods_userIcon);
            //cardView=(CardView)itemView.findViewById(R.id.recycler_card);
            imageView01=(ImageView)itemView.findViewById(R.id.goods_image_1);
            imageView02=(ImageView)itemView.findViewById(R.id.goods_image_2);
            imageView03=(ImageView)itemView.findViewById(R.id.goods_image_3);
            userName=(TextView)itemView.findViewById(R.id.goods_user);
            goodPrice=(TextView)itemView.findViewById(R.id.goods_price);
            goodCollege=(TextView)itemView.findViewById(R.id.goods_college);

            goodTitle=(TextView)itemView.findViewById(R.id.good_title);

        }
    }
}
