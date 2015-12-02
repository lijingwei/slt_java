package com.lee.parttime.ui;

import android.content.Context; 
import android.telephony.TelephonyManager; 
public class PhoneInfo {
	/**
     * 国际移动用户识别码
     */ 
	private TelephonyManager telephonyManager; 
    private Context cxt; 
    public PhoneInfo(Context context) { 
        cxt=context;        
        telephonyManager = (TelephonyManager) context 
                .getSystemService(Context.TELEPHONY_SERVICE); 
    } 
    
    public String getNativePhoneIMSI()
    {
    	TelephonyManager tm = (TelephonyManager)cxt.getSystemService(Context.TELEPHONY_SERVICE); 
    	return tm.getSubscriberId();
    }
}
