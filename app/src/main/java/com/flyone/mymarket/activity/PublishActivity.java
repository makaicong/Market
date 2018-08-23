package com.flyone.mymarket.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;


import com.flyone.mymarket.R;
import com.flyone.mymarket.adapter.PublishGoodsGridAdapter;
import com.flyone.mymarket.bean.Good;
import com.flyone.mymarket.bean.ImageItem;
import com.flyone.mymarket.bean.User;
import com.flyone.mymarket.utils.Bimp;
import com.flyone.mymarket.view.MyDialog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadBatchListener;


public class PublishActivity extends AppCompatActivity {
    private EditText goodtitle;
    private EditText gooddescribe;
    private EditText goodprice;
    private EditText goodschool;
    private EditText goodprovince;
    private EditText goodfirst;
    private EditText goodsecond;
    private TextView publish;
    private GridView gridView;
    private View root;
    private PopupWindow pop = null;
    private PopupWindow popKindOfGood=null;
    private static final int TAKE_PHOTO=1;
    private static final int CHOOSE_PHOTO=2;
    private File img;
    private MyDialog dialog;
    private PublishGoodsGridAdapter adapter;
    private List<String> listImages=new ArrayList<>();

    private boolean isPubishing=false;

    private Button buttonKind;
    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        root=getLayoutInflater().inflate(R.layout.activity_publish,null);
        setContentView(root);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(0xFFFF4081);
        }

        goodtitle = (EditText) findViewById(R.id.title);
        gooddescribe = (EditText)findViewById(R.id.describe);
        goodprice = (EditText)findViewById(R.id.price);
        goodschool = (EditText)findViewById(R.id.school);
        goodprovince = (EditText)findViewById(R.id.province);
        goodfirst = (EditText)findViewById(R.id.first);
        goodsecond = (EditText)findViewById(R.id.second);
        publish = (TextView)findViewById(R.id.publish);
        gridView=(GridView)findViewById(R.id.noScrollgridview);
        adapter=new PublishGoodsGridAdapter(getApplicationContext());

        pop = new PopupWindow(PublishActivity.this);
        View view = getLayoutInflater().inflate(R.layout.item_popupwindows, null);
        //ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
        pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        pop.setContentView(view);



        Button bt1 = (Button) view.findViewById(R.id.item_popupwindows_camera);
        Button bt3 = (Button) view.findViewById(R.id.item_popupwindows_cancel);
        //拍照
        bt1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent ("android.media.action.IMAGE_CAPTURE");
                //intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                startActivityForResult(intent,TAKE_PHOTO);
                pop.dismiss();
                //ll_popup.clearAnimation();
            }
        });

        bt3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                pop.dismiss();
                //ll_popup.clearAnimation();
            }
        });

        popKindOfGood = new PopupWindow(PublishActivity.this);

        popKindOfGood.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popKindOfGood.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popKindOfGood.setBackgroundDrawable(new BitmapDrawable());
        popKindOfGood.setFocusable(true);
        popKindOfGood.setOutsideTouchable(true);
       //popKindOfGood.setContentView(view1);

       // buttonKind=(Button)view1.findViewById(R.id.btnGet);
       /* buttonKind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/


        adapter.setListener(new PublishGoodsGridAdapter.OnPublishGridDeleteItemListener() {
            @Override
            public void deleteOnClick(View v, int position) {
                Bimp.tempSelectBitmap.remove(position);
                adapter.notifyDataSetChanged();
            }
        });


        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {//查看某个照片

            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                if (arg2 == Bimp.tempSelectBitmap.size()) {
                    //ll_popup.startAnimation(AnimationUtils.loadAnimation(PublishGoodsActivity.this, R.anim.activity_translate_in));
                    pop.showAtLocation(root, Gravity.CENTER, 0, 0);
                } else {
                    //Toast.makeText(PublishGoodsActivity.this, Bimp.tempSelectBitmap.get(arg2).imagePath, Toast.LENGTH_LONG).show();
                }
            }
        });



        publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isPubishing){

                    isPubishing=true;
                    final String title =goodtitle.getText().toString() ;
                    final String describe= gooddescribe.getText().toString() ;
                    final String price =goodprice.getText().toString();
                    final String school =goodschool.getText().toString() ;
                    final String province =goodprovince.getText().toString();
                    final String first =goodfirst.getText().toString();
                    final String second=goodsecond.getText().toString() ;
                    publishnow(title,describe,price,school,province,first,second);
                }else {

                }
                //dialog = MyDialog.showDialog(getApplicationContext());
                //dialog.show();

            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case TAKE_PHOTO:
                if (data == null) {
                    return;
                } else {
                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        Bitmap bm = extras.getParcelable("data");
                        Uri uri = saveBitmap(bm);
                        ImageItem takePhoto = new ImageItem();
                        takePhoto.setBitmap(bm);
                        takePhoto.setImagePath(uri.getPath());

                        Bimp.tempSelectBitmap.add(takePhoto);
                        Log.e("ssss",Bimp.tempSelectBitmap.get(0).getImagePath());
                        adapter.notifyDataSetChanged();
                    }
                }
                break;
           /* case GlobalDefineValues.CHOOSE_KIND:
                if (data == null) {
                    return;
                } else {
                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        kind = extras.getString(GlobalDefineValues.GOODS_KIND);
                        secondkind = extras.getString(GlobalDefineValues.GOODS_SECOND_KIND);
                        Log.e("kind", kind + "  " + secondkind);
                        mGoodsKindText.setText(kind + "  " + secondkind);
                    }
                }
                break;*/
        }

    }

    private Uri saveBitmap(Bitmap bm) {
        File tmpDir;
        if (hasSD()) {
            tmpDir = new File(Environment.getExternalStorageDirectory() + "/tradein/");
        } else {
            tmpDir = new File(Environment.getDataDirectory() + "/tradein/");
        }

        if (!tmpDir.exists()) {
            tmpDir.mkdir();
        }
        img = new File(tmpDir.getAbsolutePath() + System.currentTimeMillis() + ".png");
        try {
            FileOutputStream fos = new FileOutputStream(img);
            bm.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            return Uri.fromFile(img);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean hasSD() {
        //如果有SD卡 则下载到SD卡中
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;

        } else {
            //如果没有SD卡
            return false;
        }
    }

    public void publishnow(final String title,final String describe,final String price,
                           final String school,final String province,
                           final String first,final String second){

        final int ss=Bimp.tempSelectBitmap.size();
        final String[] imagepath=new String[ss];
        for(int i=0;i<ss;i++){
            imagepath[i]=Bimp.tempSelectBitmap.get(i).getImagePath();
        }


        BmobFile.uploadBatch(imagepath, new UploadBatchListener() {
            @Override
            public void onSuccess(List<BmobFile> list, List<String> list1) {
                try {
                    Log.e("8888",list.get(0).getFileUrl()+"    "+list1.get(0));
                    Log.e("8888",list.get(1).getFileUrl()+"    "+list1.get(1));
                }catch (Exception e){
                    Log.e("error","ccc");
                }
                Log.e("8888",list.get(0).getFileUrl()+"    "+list1.get(0));
                if(list1.size()==imagepath.length){
                    //listImages=list1;
                    publishGoods(title,describe,price,school,province,first,second,list1);
                }

                //Toast.makeText(MainActivity.this, "图片成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onProgress(int i, int i1, int i2, int i3) {

            }

            @Override
            public void onError(int i, String s) {

            }
        });


    }

    public void publishGoods( String title,String describe,String price,
                              String school,String province, String first,
                              String second,List<String>images){
        final Good good = new Good();
        User user= BmobUser.getCurrentUser(User.class);
        String userID = user.getObjectId();
        String userPhoto= user.getUserPhoto();
        String userName= user.getUsername();
        good.setUserName(userName);
        good.setUserPhoto(userPhoto);
        good.setGoodTitle(title);
        good.setGoodDescription(describe);
        good.setGoodPrice(price);
        good.setGoodSchool(school);
        good.setGoodCity(province);
        good.setGoodFirstKind(first);
        good.setGoodSecondkind(second);
        good.setGoodsImages(images);
        good.setUserId(userID);



        good.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e==null){
                    Toast.makeText(PublishActivity.this, "发布成功", Toast.LENGTH_SHORT).show();
                    Bimp.tempSelectBitmap.clear();
                    adapter.notifyDataSetChanged();

                    finish();

                }else{
                    Toast.makeText(PublishActivity.this, "上传失败", Toast.LENGTH_SHORT).show();

                }
                isPubishing=false;
            }
        });

    }
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_BACK:
                Bimp.tempSelectBitmap.clear();
                adapter.notifyDataSetChanged();
                finish();
                break;
        }
        return super.onKeyUp(keyCode, event);
    }


}
