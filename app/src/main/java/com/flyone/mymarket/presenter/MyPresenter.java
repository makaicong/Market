package com.flyone.mymarket.presenter;

import android.content.Context;
import android.util.Log;

import com.flyone.mymarket.bean.Config;
import com.flyone.mymarket.bean.Good;
import com.flyone.mymarket.fragment.MVP_View;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by wl624 on 2018/4/20.
 */

public class MyPresenter {
    private  Context mContext;
    private MVP_View mvp_view=null;
    public MyPresenter(Context context){
        this.mContext=context;
    }



    public void setInitBmob(){
        Bmob.initialize(mContext, "bab2a53e736fa27bc6c420234c2b8ace");
        insertAndSetGood();
    }

    public void insertAndSetGood(){
        Good good=new Good();
        List<Good> list=new ArrayList<>();
        //list.add()
        //good.setGoodsImages();
        good.setGoodTitle("宝宝宝宝");
        good.setGoodPrice("88");
        good.setGoodDescription("还得看v开始发航空产业库地区和大陆富豪");
        good.setGoodCity("合肥");
        good.setGoodSchool("建筑大学");
        good.setGoodFirstKind("手机");
        good.setGoodSecondkind("华为");
        good.setUserName("1815693eeeee");

        good.save(new SaveListener<String>() {

            @Override
            public void done(String objectId, BmobException e) {
                if(e==null){
                    Log.e("创建数据成功：" ," "+objectId);
                    queryAndGetGoodsByTime("2018-04-22 10:26:42");
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }


    public void queryAndGetGoodByWhere(String name,String value){
        BmobQuery<Good> query = new BmobQuery<Good>();
        query.addWhereEqualTo(name,value);
        query.setLimit(10);
        //执行查询方法
        query.findObjects(new FindListener<Good>() {
            @Override
            public void done(List<Good> object, BmobException e) {
                if(e==null){
                    mvp_view.onLatestSuccess(object);
                    //toast("查询成功：共"+object.size()+"条数据。");
                    for (Good good : object) {
                        //获得playerName的信息
                        //good.getPlayerName();
                        //获得数据的objectId信息
                        good.getObjectId();
                        //获得createdAt数据创建时间（注意是：createdAt，不是createAt）

                        Log.e("获取数据",good.getObjectId());
                        good.getCreatedAt();

                    }
                }else{
                    mvp_view.onFailure();
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }
    public void queryAndGetGoodById(String id){
        BmobQuery<Good> query = new BmobQuery<Good>();
        query.addWhereEqualTo("objectId",id);
        query.setLimit(10);
        //执行查询方法
        query.findObjects(new FindListener<Good>() {
            @Override
            public void done(List<Good> object, BmobException e) {
                if(e==null){
                    mvp_view.onLatestSuccess(object);
                    //toast("查询成功：共"+object.size()+"条数据。");
                    for (Good good : object) {
                        //获得playerName的信息
                        //good.getPlayerName();
                        //获得数据的objectId信息
                        good.getObjectId();
                        //获得createdAt数据创建时间（注意是：createdAt，不是createAt）

                        Log.e("获取数据",good.getObjectId());
                        good.getCreatedAt();

                    }
                }else{
                    mvp_view.onFailure();
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }

    public void queryAndGetLatestGoods(){
        Config.isQuerying=true;
        //小于23：59：59
        BmobQuery<Good> query = new BmobQuery<Good>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
        // 获取当前时间
        Date date = new Date(System.currentTimeMillis());

        String end = simpleDateFormat.format(date);

        query.addWhereLessThanOrEqualTo("createdAt",new BmobDate(date));
        query.setLimit(10);
        query.order("-createdAt");
        //执行查询方法
        query.findObjects(new FindListener<Good>() {
            @Override
            public void done(List<Good> object, BmobException e) {
                if(e==null){
                    //toast("查询成功：共"+object.size()+"条数据。");
                    mvp_view.onLatestSuccess(object);
                    for (Good good : object) {
                        //获得playerName的信息
                        //good.getPlayerName();
                        //获得数据的objectId信息
                        good.getObjectId();
                        //获得createdAt数据创建时间（注意是：createdAt，不是createAt）

                        Log.e("获取数据",good.getObjectId());
                        good.getCreatedAt();

                    }
                }else{
                    mvp_view.onFailure();
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }
    public void queryAndGetGoodsByTime(String end){
        Config.isQuerying=true;
        //小于23：59：59
        Log.e("Time","more");
        BmobQuery<Good> query = new BmobQuery<Good>();
        //小于23：59：59
        //String end = "2015-05-01 23:59:59";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date  = null;
        try {
            date = sdf.parse(end);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        query.addWhereLessThanOrEqualTo("createdAt",new BmobDate(date));
        query.setLimit(20);
        query.order("-createdAt");
        //执行查询方法
        query.findObjects(new FindListener<Good>() {
            @Override
            public void done(List<Good> object, BmobException e) {
                if(e==null){
                    mvp_view.onMoreSuccess(object);
                    //toast("查询成功：共"+object.size()+"条数据。");
                    for (Good good : object) {
                        //获得playerName的信息
                        //good.getPlayerName();
                        //获得数据的objectId信息
                        good.getObjectId();
                        //获得createdAt数据创建时间（注意是：createdAt，不是createAt）

                        Log.e("获取数据",good.getObjectId());
                        good.getCreatedAt();
                    }
                }else{
                    Log.e("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                    mvp_view.onFailure();
                }
            }
        });
    }


    public void setView(MVP_View mvp_view){
        this.mvp_view=mvp_view;
    }
}
