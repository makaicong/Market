package com.flyone.mymarket.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.flyone.mymarket.CircleImageView;
import com.flyone.mymarket.R;
import com.flyone.mymarket.bean.ImageItem;
import com.flyone.mymarket.bean.User;
import com.flyone.mymarket.utils.Bimp;
import com.flyone.mymarket.utils.SetName;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadBatchListener;

/**
 * Created by wl624 on 2018/4/25.
 */

public class UserInformation extends AppCompatActivity {
    private CircleImageView circleImageView;
    private User user;
    private PopupWindow pop = null;
    private Button button01,button02,button03;
    private File img;
    private View root;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        root=getLayoutInflater().inflate(R.layout.activity_userinformation,null);
        setContentView(root);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(0xFFFF4081);
        }
        pop = new PopupWindow(getApplicationContext());
        final View view = getLayoutInflater().inflate(R.layout.item_pic, null);
        //ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
        pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        pop.setContentView(view);
        button01=(Button)view.findViewById(R.id.item_popupwindows_camera);
        button03=(Button)view.findViewById(R.id.item_popupwindows_cancel);
        button01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent ("android.media.action.IMAGE_CAPTURE");
                //intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                startActivityForResult(intent,1);
                pop.dismiss();
            }
        });
        button03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop.dismiss();
            }
        });
        circleImageView=(CircleImageView)findViewById(R.id.userPhotoInformation);
        user= BmobUser.getCurrentUser(User.class);
        if(user.getUserPhoto() != null){
            //circleImageView.setImageBitmap();

            Glide.with(getApplicationContext()).load(user.getUserPhoto()).error(R.mipmap.ic_bailu)
                    .into(circleImageView);
        }else{
            Glide.with(getApplicationContext()).load(R.mipmap.ic_hanlu)
                    .into(circleImageView);
        }
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop.showAtLocation(root, Gravity.CENTER, 0, 0);
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 1:
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
                        String[] photo=new String[]{Bimp.tempSelectBitmap.get(0).getImagePath()};
                        BmobFile.uploadBatch(photo, new UploadBatchListener() {
                            @Override
                            public void onSuccess(List<BmobFile> list, List<String> list1) {
                                try {
                                    Log.e("8888",list.get(0).getFileUrl()+"    "+list1.get(0));
                                    Log.e("8888",list.get(1).getFileUrl()+"    "+list1.get(1));
                                }catch (Exception e){
                                    Log.e("error","ccc");
                                }


                                publishUser(list1.get(0));

                                //Toast.makeText(MainActivity.this, "图片成功", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onProgress(int i, int i1, int i2, int i3) {

                            }

                            @Override
                            public void onError(int i, String s) {

                            }
                        });
                        //adapter.notifyDataSetChanged();
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

    private void publishUser(String s) {

        user.setUserPhoto(s);
        user.update(user.getObjectId(), new UpdateListener() {

            @Override
            public void done(BmobException e) {
                Bimp.tempSelectBitmap.clear();
                if(e==null){
                    Toast.makeText(UserInformation.this, "修改成功", Toast.LENGTH_SHORT).show();
                    Glide.with(getApplicationContext()).load(user.getUserPhoto()).error(R.mipmap.ic_bailu)
                            .into(circleImageView);
                    SetName s=SetName.getInstance(getApplicationContext());
                    s.setUserPhoto(user.getUserPhoto());
                }else{
                    Toast.makeText(UserInformation.this, "修改失败", Toast.LENGTH_SHORT).show();
                }
            }

        });
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

}
