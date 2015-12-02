package com.lee.parttime.job;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import android.R.integer;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.lee.common.util.PreferencesUtils;
import com.lee.parttime.R;
import com.lee.parttime.config.URLs;
import com.lee.parttime.entity.Joblist;
import com.lee.parttime.entity.Message;
import com.lee.parttime.ui.BaseActivity;
import com.lee.parttime.ui.LoadingDialog;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;


public class PartTimeJobView extends BaseActivity {
	@ViewInject(R.id.job_theme)
	TextView job_theme;

	@ViewInject(R.id.job_pay)
	TextView job_pay;
	@ViewInject(R.id.job_distance)
	TextView job_distance;
	@ViewInject(R.id.publishTime)
	TextView publishTime;
	@ViewInject(R.id.job_company)
	TextView job_company;
	@ViewInject(R.id.paytime)
	TextView paytime;
	
	@ViewInject(R.id.job_requestCount)
	TextView job_requestCount;
	
	@ViewInject(R.id.job_time)
	TextView job_time;
	@ViewInject(R.id.job_address)
	TextView job_address;
	@ViewInject(R.id.inerview_time)
	TextView inerview_time;
	@ViewInject(R.id.inerview_address)
	TextView inerview_address;
	@ViewInject(R.id.job_detailshow)
	TextView job_detailshow;

	@ViewInject(R.id.job_contact)
	TextView job_contact;

	@ViewInject(R.id.job_phone)
	TextView job_phone;

	private LoadingDialog dialog;
	private int userID = 0;
	private String jobID = "0";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jobdetail_layout);
		ViewUtils.inject(this);

		userID = PreferencesUtils.getInt(this, "usersID", 0);
		dialog = new LoadingDialog(this);
		Intent intent = this.getIntent();
		jobID = intent.getStringExtra("jobid");
		LoadData();
	}

	private void LoadData() {
		HttpUtils http = new HttpUtils();
		String jobURL = String.format(URLs.JOB_VIEW, jobID);
		http.send(HttpRequest.HttpMethod.GET, jobURL,
				new RequestCallBack<String>() {
					@Override
					public void onLoading(long total, long current,
							boolean isUploading) {
						dialog.show();
					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						ObjectMapper mapper = new ObjectMapper();
						if (dialog != null && dialog.isShowing()) {
							dialog.dismiss();
						}
						Message message = null;
						try {
							message = mapper.readValue(responseInfo.result,
									Message.class);
							Log.e("--test---", responseInfo.result);
						} catch (IOException e) {
							e.printStackTrace();
						}
						try {
							Joblist job = mapper.readValue(message.data,
									Joblist.class);
							job_theme.setText(job.jobTitle);
							job_pay.setText(job.jobPayFee);
							
							job_distance.setText(job.jobPostAddress);
							
							publishTime.setText(job.jobPostDate);
							job_company.setText(job.jobPostCompany);
							paytime.setText(job.jobJSRQ);
							job_requestCount.setText(job.jobZPRS+"");
							job_time.setText(job.jobGZRQ);
							job_address.setText(job.jobGZDZ);
							inerview_time.setText(job.jobMSSJ);
							inerview_address.setText(job.jobMSDZ);
							job_detailshow.setText(job.jobZWMS);
							if (userID > 0) {
								job_phone.setText(job.jobPhone);
							} else {
								job_phone.setText("联系信息请登录后查看。");
							}

						} catch (IOException e) {
							e.printStackTrace();
						}

					}

					@Override
					public void onStart() {
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						if (dialog != null && dialog.isShowing()) {
							dialog.dismiss();
						}
						showLongToast(3, "信息获取出错");
					}
				});
	}

	@OnClick(R.id.callPhone)
	public void callPhone(View v) {
		if (!job_contact.getText().toString().trim().equals("联系信息请登录后查看。")) {
			Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
					+ job_contact.getText().toString().trim()));
			startActivity(intent);
		}
	}
}
