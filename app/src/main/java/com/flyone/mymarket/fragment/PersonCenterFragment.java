package com.flyone.mymarket.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flyone.mymarket.CircleImageView;
import com.flyone.mymarket.R;
import com.flyone.mymarket.activity.OtherActivity;
import com.flyone.mymarket.activity.SettingActivity;
import com.flyone.mymarket.activity.UserInformation;
import com.flyone.mymarket.bean.User;
import com.flyone.mymarket.activity.LoginActivity;
import com.flyone.mymarket.utils.SetName;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;

/**
 * Created by TuoZhaoBing on 2016/4/6 0006.
 */
public class PersonCenterFragment extends Fragment {

	public static final String TAG = "PersonCenterFragment";
	private CircleImageView userPhoto;


	private RelativeLayout personCenter,my_post,my_sell,my_buy,my_college,introduce_to_friend_rl,call_center,person_center_settings;

	private TextView userName;


	private Context mContext;

	private User user=null;
	private SetName setName;

	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			final String action = intent.getAction();

			if(action=="LOGIN_SUCCESS"){
				Log.e("LOGIN","SUCCESS");
				//mLoginRightNowRl.setVisibility(View.GONE);
				//mLoginedRl.setVisibility(View.VISIBLE);

				//mUserName.setText(user.getUsername());
				//textView09.setText("cccc");
			}else if(action=="LOGIN FAILURE"){
				Log.e("LOGIN","FAILURE");
				//textView09.setText("cccc");
			}

			//There are four basic operations for moving data in BLE: read, write, notify,
			// and indicate. The BLE protocol specification requires that the maximum data
			// payload size for these operations is 20 bytes, or in the case of read operations,
		}
	};

	public PersonCenterFragment(){
		mContext = getContext();

	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


		setName=SetName.getInstance(getContext());
		IntentFilter intentFilter = new IntentFilter("com.flyone.mymarket");
		//步骤3：设置接收广播的类型
		getContext().registerReceiver(mBroadcastReceiver, intentFilter);
		final View mRootView = inflater.inflate(R.layout.fragment_personcenter,null);
		ButterKnife.bind(this, mRootView);
		userPhoto=(CircleImageView)mRootView.findViewById(R.id.userPhoto);
		personCenter=(RelativeLayout)mRootView.findViewById(R.id.person_center);
		userName=(TextView)mRootView.findViewById(R.id.userName);
		my_post=(RelativeLayout)mRootView.findViewById(R.id.my_post);
		my_sell=(RelativeLayout)mRootView.findViewById(R.id.my_sell);
		my_buy=(RelativeLayout)mRootView.findViewById(R.id.my_buy);
		my_college=(RelativeLayout)mRootView.findViewById(R.id.my_college);
		introduce_to_friend_rl=(RelativeLayout)mRootView.findViewById(R.id.introduce_to_friend_rl);
		call_center=(RelativeLayout)mRootView.findViewById(R.id.call_center);
		my_post.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(getContext(), OtherActivity.class);
				Bundle bundle=new Bundle();
				bundle.putString("rr","我的发布");
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		person_center_settings=(RelativeLayout)mRootView.findViewById(R.id.person_center_settings);

		personCenter.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				user= BmobUser.getCurrentUser(User.class);
				if(user != null){
					//userName.setText(user.getUsername());
					Intent intent=new Intent(getContext(),UserInformation.class);
					startActivity(intent);

				}else{
					Intent intent=new Intent(getContext(),LoginActivity.class);
					startActivity(intent);
				}
			}
		});
		setName.setView(userName,userPhoto);
		initData();

		person_center_settings.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(getContext(), SettingActivity.class);
				startActivity(intent);
			}
		});
		return mRootView;
	}


	public void initData(){
		 user= BmobUser.getCurrentUser(User.class);
		if(user != null){
			//userName.setText(user.getUsername());

			setName.setUserName(user.getUsername());
			setName.setUserPhoto(user.getUserPhoto());
			//已登录
			//mLoginRightNowRl.setVisibility(View.GONE);
			//mLoginedRl.setVisibility(View.VISIBLE);
			/**
			if (user.getPhoto()!=null){
				personCenterFragmentPresenter.loadPhoto(mContext,user.getPhoto().getUrl());
			}else{Ph
				mPersonCenteroto.setImageResource(R.mipmap.icon_photo);
			}
			 **/
			//mUserName.setText(user.getUsername());
			/**
			if (user.getSignature() == null || user.getSignature().equals("")){
				mUserSpecified.setText("您的签名空空如也，立即设置？");
			}else {
				mUserSpecified.setText(user.getSignature());
			}
			 **/
		}else{
			setName.setUserName("请登录");
			//mLoginedRl.setVisibility(View.GONE);
			//mLoginRightNowRl.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void onPause() {
		super.onPause();

	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onStop() {
		super.onStop();

	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		ButterKnife.unbind(this);
	}

	/**
	@OnClick({R.id.person_center_photo, R.id.login_right_now_textview, R.id.login_right_now_rl, R.id.my_post_rl,  R.id.setting_rl, R.id.logined_rl})
	public void onClick(View view) {
		User user = BmobUser.getCurrentUser(User.class);
		switch (view.getId()) {
			case R.id.person_center_photo:

				break;
			case R.id.login_right_now_textview:

				Intent intent=new Intent(getContext(), LoginActivity.class);
				startActivity(intent);
				//UIUtils.nextPage(mContext, LoginActivity.class);
				//((MainActivity)mContext).finish();
				break;
			case R.id.my_post_rl:
				if (user == null){
					//UIUtils.nextPage(mContext,LoginActivity.class);
					//((MainActivity)mContext).finish();
				}else {
					//UIUtils.nextPage(mContext, MyPublishActivity.class);
				}
				break;
			/*case R.id.my_qiugou_rl:
				if (user == null){
					UIUtils.nextPage(mContext,LoginActivity.class);
					((MainActivity)mContext).finish();
				}else {
					UIUtils.nextPage(mContext, MyQiuGouActivity.class);
				}
				break;
			case R.id.my_favor_rl:
				if (user == null){
					UIUtils.nextPage(mContext,LoginActivity.class);
					((MainActivity)mContext).finish();
				}else {
					UIUtils.nextPage(mContext, MyFavorActivity.class);
				}
				break;
			case R.id.introduce_to_friend_rl:
				if (user == null){
					UIUtils.nextPage(mContext,LoginActivity.class);
					((MainActivity)mContext).finish();
				}else{
					processIntroductionTofriend();
				}
				break;
			case R.id.setting_rl:
				//UIUtils.nextPage(mContext, SettingsActivity.class);
				break;
		}
	}

	**/
	public void processIntroductionTofriend(){

	}

}
