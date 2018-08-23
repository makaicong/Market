package com.flyone.mymarket.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.flyone.mymarket.R;
import com.flyone.mymarket.bean.User;
import com.flyone.mymarket.utils.SetName;
import com.flyone.mymarket.utils.ToolsUtils;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText mLoginUserName;
    private EditText mLoginUserPass;
    private TextView mLoginTextview;
    private TextView mForgetPass;
    private TextView mRegisterNow;
    private ImageView mQQLogin;
    private LoginListener loginListener=null;
    public final static String LOGIN_ACTION="LOGIN_ACTION";

    //private ImageView imageView;

    private static final String TAG = "MainActivity";
    private static final String APP_ID = "1106758439";//官方获取的APPID
    //private Tencent mTencent;
    //private BaseUiListener mIUiListener;
    //private UserInfo mUserInfo;
    private String nickname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(0xFFFF4081);
        }
        mLoginUserName = (EditText) findViewById(R.id.login_user_name);

        mLoginUserPass = (EditText) findViewById(R.id.login_user_pass);

        mLoginTextview = (TextView) findViewById(R.id.login_textview);

        mForgetPass = (TextView) findViewById(R.id.login_error);

        mRegisterNow = (TextView) findViewById(R.id.register_now);

        mQQLogin = (ImageView) findViewById(R.id.qq_login);

        //imageView = (ImageView) findViewById(R.id.qq_login);


        mLoginTextview.setOnClickListener(this);
        mForgetPass.setOnClickListener(this);
        mRegisterNow.setOnClickListener(this);
        mQQLogin.setOnClickListener(this);
        //imageView.setOnClickListener(this);


        //mTencent = Tencent.createInstance(APP_ID,LoginActivity.this.getApplicationContext());
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.login_textview:
                progressLogin(mLoginUserName.getText().toString(),mLoginUserPass.getText().toString());
                break;
            case R.id.login_error:
                //UIUtils.nextPage(LoginActivity.this,ForgetPassActivity.class);
                break;
            case R.id.register_now:
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                //UIUtils.nextPage(LoginActivity.this,RegisterActivity.class);
                break;
            case R.id.qq_login:
                //QQLogin();
                break;



        }
    }

    /**
    public void QQLogin(){
        mIUiListener = new BaseUiListener();
        //all表示获取所有权限
        //mTencent.login(LoginActivity.this,"all", mIUiListener);
    }


    private class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object response) {
            Toast.makeText(LoginActivity.this, "授权成功", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "response:" + response);
            JSONObject obj = (JSONObject) response;
            try {
                String openID = obj.getString("openid");
                String accessToken = obj.getString("access_token");
                String expires = obj.getString("expires_in");
                mTencent.setOpenId(openID);
                mTencent.setAccessToken(accessToken,expires);
                QQToken qqToken = mTencent.getQQToken();
                mUserInfo = new UserInfo(getApplicationContext(),qqToken);
                mUserInfo.getUserInfo(new IUiListener() {
                    @Override
                    public void onComplete(Object response) {
                        Log.e(TAG,"登录成功"+response.toString());
                        parseJSON(response.toString());
                        Toast.makeText(LoginActivity.this, nickname, Toast.LENGTH_SHORT).show();
                        //233
                    }

                    @Override
                    public void onError(UiError uiError) {
                        Log.e(TAG,"登录失败"+uiError.toString());
                    }

                    @Override
                    public void onCancel() {
                        Log.e(TAG,"登录取消");

                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        private void parseJSON(String jsonData){

            try{
                JSONObject jsonObject=new JSONObject(jsonData);
                //JSONArray jsonArray = new JSONArray(jsonData);
                Log.e("ccc",jsonData);
                //JSONObject jsonObject =jsonArray.getJSONObject(i);
                nickname = jsonObject.getString("nickname");


            }catch(Exception e){
                e.printStackTrace();
            }

        }

        @Override
        public void onError(UiError uiError) {
            Toast.makeText(LoginActivity.this, "授权失败", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onCancel() {
            Toast.makeText(LoginActivity.this, "授权取消", Toast.LENGTH_SHORT).show();

        }

    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Constants.REQUEST_LOGIN){
            Tencent.onActivityResultData(requestCode,resultCode,data,mIUiListener);
        }

    }

     **/
   public void progressLogin(final String username, String pass){
        User bu2 = new User();
        if (ToolsUtils.isMobileNO(username)){
            if (ToolsUtils.isCorrectUserPwd(pass)){
                bu2.setMobilePhoneNumber(username);
                bu2.setPassword(pass);
                bu2.login(new SaveListener<User>() {
                    public void done(User user, BmobException e) {
                        if(e==null){
                            Toast.makeText(LoginActivity.this, "成功", Toast.LENGTH_SHORT).show();
                            user = BmobUser.getCurrentUser(User.class);
                            loginSuccess(user);
                        }else{
                            Toast.makeText(LoginActivity.this, "账户名或密码不正确", Toast.LENGTH_SHORT).show();
                            loginFailed("登录失败");
                            //loge(e);
                        }
                    }

                });
            }else{
                loginFailed("密码中含有非法字符");
                Toast.makeText(LoginActivity.this, "账户名或密码不正确1", Toast.LENGTH_SHORT).show();
            }
        }else {
            if (ToolsUtils.isCorrectUserPwd(pass)){
                bu2.setUsername(username);
                bu2.setPassword(pass);
                bu2.login(new SaveListener<User>() {

                 public void done(User user, BmobException e) {
                     if(e==null){
                         Toast.makeText(LoginActivity.this, "成功", Toast.LENGTH_SHORT).show();
                         user = BmobUser.getCurrentUser(User.class);
                         loginSuccess(user);
                     }else{
                         Toast.makeText(LoginActivity.this, "账户名或密码不正确", Toast.LENGTH_SHORT).show();
                         loginFailed("登录失败");
                     }
                 }

                });
            }else{
                loginFailed("密码中含有非法字符");
            }
        }
    }


    public void loginSuccess(User user) {

        Toast.makeText(this,user.getUsername().toString(),Toast.LENGTH_SHORT).show();
        /**
        Bundle bundle = new Bundle();
        bundle.putString(GlobalDefineValues.MainActivityWorkMode,GlobalDefineValues.PersonCenterWorkMode);
        if(loginListener!=null){
            loginListener.onSuccess(GlobalDefineValues.PersonCenterWorkMode);
        }
        //UIUtils.nextPage(this,MainActivity.class,bundle);
         **/
        SetName setName=SetName.getInstance(getApplicationContext());
        setName.setUserName(user.getUsername().toString());
        setName.setUserPhoto(user.getUserPhoto());
        Intent intent = new Intent("LOGIN_SUCCESS");
        getApplicationContext().sendBroadcast(intent);
        finish();
    }


    public void loginFailed(String str) {
        Toast.makeText(this,str,Toast.LENGTH_SHORT).show();

        //UIUtils.nextPage(this,MainActivity.class);
        if(loginListener!=null){
            loginListener.onFailed("ffff");
        }
        Intent intent = new Intent("LOGIN FAILURE");
        getApplicationContext().sendBroadcast(intent);
        finish();
    }
    interface LoginListener{
        void onSuccess(String bbb);
        void onFailed(String bbb);
    }
    public void setLoginListener(LoginListener loginListener){
        this.loginListener=loginListener;
    }

}
