package com.lee.parttime.ui;


import com.lee.parttime.R;
import com.lidroid.xutils.util.LogUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

public class BaseActivity extends Activity{
	private static TipsToast tipsToast;
	private long exitTime = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		LogUtils.d(this.getClass().getSimpleName()
				+ " onCreate() invoked!!");
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

	}
	

	@Override
	protected void onStart() {
		LogUtils.d(this.getClass().getSimpleName() + " onStart() invoked!!");
		super.onStart();
	}

	@Override
	protected void onRestart() {
		LogUtils.d(this.getClass().getSimpleName()
				+ " onRestart() invoked!!");
		super.onRestart();
	}

	@Override
	protected void onResume() {
		LogUtils.d(this.getClass().getSimpleName()
				+ " onResume() invoked!!");
		super.onResume();
	}

	@Override
	protected void onPause() {
		LogUtils.d(this.getClass().getSimpleName() + " onPause() invoked!!");
		super.onPause();
		try {
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onStop() {
		LogUtils.d(this.getClass().getSimpleName() + " onStop() invoked!!");
		super.onStop();
	}

	@Override
	public void onDestroy() {
		LogUtils.d(this.getClass().getSimpleName()
				+ " onDestroy() invoked!!");
		super.onDestroy();
	}

	/*
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){   
	        if((System.currentTimeMillis()-exitTime) > 2000){  
	        	showShortToast(1, "再按一次退出程序");                         
	            exitTime = System.currentTimeMillis();   
	        } else {
	            finish();
	            Intent intent = new Intent(Intent.ACTION_MAIN);
	    	    intent.addCategory(Intent.CATEGORY_HOME);
	    	    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    	    startActivity(intent);
	        }
	        return true;   
	    }
	    return super.onKeyDown(keyCode, event);
	}*/

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

	super.onCreateOptionsMenu(menu);
	MenuItem item = menu.add(Menu.NONE, Menu.NONE, Menu.NONE, "退出");
	item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
	{
	public boolean onMenuItemClick(MenuItem item)
	{
		finish();
	    Intent intent = new Intent(Intent.ACTION_MAIN);
	    intent.addCategory(Intent.CATEGORY_HOME);
	    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    startActivity(intent);
	return true;
	}
	});
	return true;
	}
	
	//[start] 仿造微信TIPS
	
	/**
	 * 长时间显示提示框
	 * @param MsgType：消息类型。1：提醒；2：成功；3：失败；4：笑脸
	 * @param text ： 消息内容
	 */
	protected void showLongToast(int MsgType, CharSequence text) {
		int typeid = 0;
		switch (MsgType) {
		case 1:
			typeid = R.drawable.tips_warning;
			break;
		case 2:
			typeid = R.drawable.tips_success;
			break;
		case 3:
			typeid = R.drawable.tips_error;
			break;
		case 4:
			typeid = R.drawable.tips_smile;
			break;
		default:
			break;
		}
		showTips(typeid, text, 1);
	}
	
	/**
	 * 长时间显示提示框
	 * @param MsgType：消息类型。1：提醒；2：成功；3：失败；4：笑脸
	 * @param text ： 消息内容
	 */
	protected void showShortToast(int MsgType, CharSequence text) {
		int typeid = 0;
		switch (MsgType) {
		case 1:
			typeid = R.drawable.tips_warning;
			break;
		case 2:
			typeid = R.drawable.tips_success;
			break;
		case 3:
			typeid = R.drawable.tips_error;
			break;
		case 4:
			typeid = R.drawable.tips_smile;
			break;
		default:
			break;
		}
		showTips(typeid, text, 0);
	}
	
	private void showTips(int iconResId, CharSequence text, int duartion) {
		if (tipsToast != null) {
			
		}else {
			if (duartion == 0) {
				tipsToast = TipsToast.makeText(getApplication()
						.getBaseContext(), text, TipsToast.LENGTH_SHORT);
			} else {
				tipsToast = TipsToast.makeText(getApplication()
						.getBaseContext(), text, TipsToast.LENGTH_LONG);
			}
		}
		tipsToast.show();
		tipsToast.setIcon(iconResId);
		tipsToast.setText(text);
	}
	//[end]
	
}
