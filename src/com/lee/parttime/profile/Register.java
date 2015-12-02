package com.lee.parttime.profile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.map.ObjectMapper;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.lee.common.util.IntentUtil;
import com.lee.common.util.LogUtil;
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

public class Register extends BaseActivity {

	@ViewInject(R.id.register_username)
	EditText register_username;

	@ViewInject(R.id.register_validate_code)
	EditText register_validate_code;
	@ViewInject(R.id.register_password)
	EditText register_password;
	@ViewInject(R.id.register_confirm_password)
	EditText register_confirm_password;
	@ViewInject(R.id.register_invite_code)
	EditText register_invite_code;

	private LoadingDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_userregist_layout);
		ViewUtils.inject(this);

		dialog = new LoadingDialog(this);
	}

	@OnClick(R.id.register_submit)
	public void register_submit(View v) {
		String username = register_username.getText().toString().trim();
		String password = register_password.getText().toString().trim();
		String confirm_password = register_confirm_password.getText()
				.toString().trim();
		String invite_code = register_invite_code.getText().toString().trim();
		if (TextUtils.isEmpty(username)) {
			showShortToast(1, "手机号码必须填写");
			return;
		}
		if (TextUtils.isEmpty(password)) {
			showShortToast(1, "密码必须填写");
			return;
		}
		if (TextUtils.isEmpty(confirm_password)) {
			showShortToast(1, "再次密码必须填写");
			return;
		}
		if (!confirm_password.equals(password)) {
			showShortToast(1, "两次密码输入一致");
			return;
		}

		RequestParams params = new RequestParams();

		params.addBodyParameter("usersName", username);
		params.addBodyParameter("usersPwd", password);
		params.addBodyParameter("usersInvalitCode", invite_code);
		params.addBodyParameter("usersIsForgot", "0");
		SimpleDateFormat sDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd hh:mm:ss");
		String date = sDateFormat.format(new java.util.Date());
		params.addBodyParameter("usersRegDate", date);

		Log.e("---test--", username);
		Log.e("---test--", new Date().toString());

		HttpUtils http = new HttpUtils();
		http.send(HttpRequest.HttpMethod.POST, URLs.USER_REGISTER, params,
				new RequestCallBack<String>() {

					@Override
					public void onStart() {
						LogUtil.e("---test--", URLs.USER_REGISTER);
					}

					@Override
					public void onLoading(long total, long current,
							boolean isUploading) {
						dialog.show();
					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						if (dialog != null && dialog.isShowing()) {
							dialog.dismiss();
						}
						ObjectMapper mapper = new ObjectMapper();
						Message message = null;
						try {
							message = mapper.readValue(responseInfo.result,
									Message.class);
						} catch (IOException e) {
							e.printStackTrace();
						}
						Log.e("---tt-----", responseInfo.result);
						int resultID = message.res;
						if (resultID > 0) {
							showLongToast(2, message.msg);
							try {
								Users users = mapper.readValue(message.data,
										Users.class);
								PreferencesUtils.putInt(Register.this,
										"usersID", users.usersID);
								PreferencesUtils.putString(Register.this,
										"usersName", users.usersName);
								IntentUtil.start_activity(Register.this,
										PartTimeJobList.class);
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
						Log.e("---test--", error.getExceptionCode() + msg);
					}
				});
	}

	@OnClick(R.id.back)
	public void back(View v) {
		IntentUtil.start_activity(this, HomeActivity.class);
	}

}
