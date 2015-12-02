package com.lee.parttime.profile;

import java.io.IOException;
import java.util.Date;

import org.codehaus.jackson.map.ObjectMapper;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

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

public class ResetPwd extends BaseActivity {
	@ViewInject(R.id.register_telephone)
	EditText register_telephone;

	@ViewInject(R.id.register_password)
	EditText register_password;

	@ViewInject(R.id.register_confirm_password)
	EditText register_confirm_password;

	private LoadingDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reset_password);
		ViewUtils.inject(this);

		dialog = new LoadingDialog(this);
	}

	@OnClick(R.id.register_submit)
	public void register_submit(View v) {
		String username = register_telephone.getText().toString().trim();
		String password = register_password.getText().toString().trim();
		String confirm_password = register_confirm_password.getText()
				.toString().trim();

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
		params.addBodyParameter("usersIsForgot", "1");
		HttpUtils http = new HttpUtils();
		http.send(HttpRequest.HttpMethod.POST, URLs.USER_RESETPWD, params,
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
						int resultID = message.res;
						if (resultID > 0) {
							showLongToast(2, message.msg);

							IntentUtil.start_activity(ResetPwd.this,
									Login.class);

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
}
