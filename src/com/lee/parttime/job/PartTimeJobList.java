package com.lee.parttime.job;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.lee.common.util.IntentUtil;
import com.lee.parttime.R;
import com.lee.parttime.config.URLs;
import com.lee.parttime.entity.Joblist;
import com.lee.parttime.entity.Message;
import com.lee.parttime.ui.BaseActivity;
import com.lee.parttime.ui.LoadingDialog;
import com.leelistview.view.LeeListView;
import com.leelistview.view.LeeListView.IXListViewListener;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;



public class PartTimeJobList extends BaseActivity implements IXListViewListener{
	
	@ViewInject(R.id.my_listview)
	LeeListView mXListView;
	
	private PartTimeJobAdapter mAdapter;
	private LoadingDialog dialog;
	
	private int start = 0;
	private int currentPage = 0;
	private static String refreshTime = "";
	private static String refreshCount = "0";
	private List<Joblist> mDatas = new ArrayList<Joblist>();
	private Handler mHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jobinvitation_layout);
		ViewUtils.inject(this);

		dialog = new LoadingDialog(this);
		mAdapter = new PartTimeJobAdapter(this, mDatas);

		mXListView.setAdapter(mAdapter);
		mXListView.setPullLoadEnable(true,"共"+refreshCount+"条数据");
		mXListView.setPullRefreshEnable(false);
		mXListView.setXListViewListener(this);
		mXListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Joblist item = (Joblist) mAdapter
						.getItem(position - 1);

				BasicNameValuePair pair = new BasicNameValuePair("jobid",
						item.getJobID()+"");
				Log.e("-------test------", item.getJobID()+"");

				IntentUtil.start_activity(PartTimeJobList.this,
						PartTimeJobView.class, pair);
			}
		});
		mHandler = new Handler();
	}

	@Override
	public void onRefresh() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {

				LoadData();
				mAdapter.addAll(mDatas);
				mAdapter.notifyDataSetChanged();
				onLoad();
			}
		}, 2000);
	}

	@Override
	public void onLoadMore() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				LoadData();
				mAdapter.notifyDataSetChanged();
				onLoad();
			}
		}, 2000);
	}
	
	private void LoadData(){
		HttpUtils http = new HttpUtils();
		http.send(HttpRequest.HttpMethod.GET,
		    URLs.JOB_LIST,
		    new RequestCallBack<String>(){
		        @Override
		        public void onLoading(long total, long current, boolean isUploading) {
		        	dialog.show();
		        }

		        @Override
		        public void onSuccess(ResponseInfo<String> responseInfo) {
		        	currentPage++;
		        	ObjectMapper mapper = new ObjectMapper();
					if (dialog != null && dialog.isShowing()) {
						dialog.dismiss();
					}
					Message message = null;
					try {
						message = mapper.readValue(responseInfo.result,
								Message.class);
					} catch (IOException e) {
						e.printStackTrace();
					}
					try {
						List<Joblist> joblists = mapper.readValue(message.data, new TypeReference<List<Joblist>>() {});
						mDatas = joblists;
						refreshTime = new Date().toString();
						refreshCount = joblists.size()+"";
						mAdapter.addAll(mDatas);
						mAdapter.notifyDataSetChanged();
						mXListView.stopRefresh();
						
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
		        	mXListView.stopRefresh();
		        }
		});
	}
	
	private void onLoad() {
		mXListView.stopRefresh();
		mXListView.stopLoadMore();
		mXListView.setRefreshTime(refreshTime);
	}
}
