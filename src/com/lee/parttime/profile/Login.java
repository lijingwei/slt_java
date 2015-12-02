package com.lee.parttime.profile;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.lee.common.util.IntentUtil;
import com.lee.common.util.PreferencesUtils;
import com.lee.parttime.HomeActivity;
import com.lee.parttime.R;
import com.lee.parttime.config.URLs;
import com.lee.parttime.entity.Message;
import com.lee.parttime.entity.Users;
import com.lee.parttime.job.PartTimeJobList;
import com.lee.parttime.ui.BaseActivity;
import com.lee.parttime.ui.LoadingDialog;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.R.integer;
import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class Login extends BaseActivity {

	@ViewInject(R.id.login_username)
	EditText login_username;

	@ViewInject(R.id.login_password)
	EditText login_password;

	private LoadingDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		ViewUtils.inject(this);

		dialog = new LoadingDialog(this);
	}

	@OnClick(R.id.login_submit)
	public void login_submit(View v) {
		String usersName = login_username.getText().toString().trim();
		String usersPwd = login_password.getText().toString().trim();
		if (TextUtils.isEmpty(usersName)) {
			showShortToast(1, "用户名必须填写");
			return;
		}
		if (TextUtils.isEmpty(usersPwd)) {
			showShortToast(1, "密码必须填写");
			return;
		}
		RequestParams params = new RequestParams();
		params.addBodyParameter("usersName", usersName);
		params.addBodyParameter("usersPwd", usersPwd);

		HttpUtils http = new HttpUtils();
		http.send(HttpRequest.HttpMethod.POST, URLs.USER_LOGIN, params,
				new RequestCallBack<String>() {

					@Override
					public void onStart() {

					}

					@Override
					public void onLoading(long total, long current,
							boolean isUploading) {
						dialog.show();
					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						ObjectMapper mapper = new ObjectMapper();
						mapper.enableDefaultTyping();
						if (dialog != null && dialog.isShowing()) {
							dialog.dismiss();
						}
						Log.e("-----tttt-----", responseInfo.result);
						Message message = null;
						try {
							message = mapper.readValue(responseInfo.result,
									Message.class);
						} catch (IOException e) {
							e.printStackTrace();
						}
		
						int resultID = message.res;
						if (resultID > 0) {
							showLongToast(2, message.msg);
							try {
								Users users = mapper.readValue(message.data,
										Users.class);
								PreferencesUtils.putInt(Login.this, "usersID",
										users.usersID);
								PreferencesUtils.putString(Login.this,
										"usersName", users.usersName);
								IntentUtil.start_activity(Login.this, PartTimeJobList.class);
							} catch (IOException e) {
								e.printStackTrace();
							}
						} else {
							showLongToast(3, message.msg);
						}
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						if (dialog != null && dialog.isShowing()) {
							dialog.dismiss();
						}
						showLongToast(3, msg);
					}
				});

	}
	
	@OnClick(R.id.back)
	public void back(View v) {
		IntentUtil.start_activity(this, HomeActivity.class);
	}
	@OnClick(R.id.login_forget_password)
	public void login_forget_password(View v) {
		IntentUtil.start_activity(this, ResetPwd.class);
	}
	@OnClick(R.id.login_register)
	public void login_register(View v) {
		IntentUtil.start_activity(this, Register.class);
	}
}
