package com.flyone.mymarket.utils;

import android.content.Context;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.flyone.mymarket.CircleImageView;
import com.flyone.mymarket.R;

/**
 * Created by wl624 on 2018/4/25.
 */

public class SetName {
    private static SetName setName=null;
    private static Context mContext;
    private TextView userName;
    private CircleImageView userPhoto;

    public SetName() {
    }

    public static SetName getInstance(Context context){
        mContext=context;
        synchronized (SetName.class) {
            if (setName == null) {
                setName= new SetName();
            }
        }
        return setName;
    }
    public void setView(TextView userName, CircleImageView userPhoto){
        this.userName=(TextView)userName;
        this.userPhoto=(CircleImageView)userPhoto;

    }
    public void setUserName(String str){
        userName.setText(str);
    }

    public void setUserPhoto(String sss) {
        if(sss==" "){
            Glide.with(mContext).load(R.mipmap.ic_bailu)
                    .into(userPhoto);
        }else {
            Glide.with(mContext).load(sss).error(R.mipmap.ic_bailu)
                    .into(userPhoto);
        }

    }
}
