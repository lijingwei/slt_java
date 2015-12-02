package com.lee.parttime.config;

public class URLs {
	public static final String BASIC_URL = "http://10.0.2.2:8080/SLT_Andorid_Parttime";//http://10.0.2.2:8094
	public static final String USER_LOGIN = BASIC_URL
			+ "/users/users?action=validate";

	public static final String USER_REGISTER = BASIC_URL
			+ "/users/users?action=add";
	
	public static final String USER_RESETPWD = BASIC_URL
			+ "/users/users?action=forgotpwd";
	
	public static final String JOB_LIST = BASIC_URL
			+ "/joblist/joblist?action=list";
	
	public static final String JOB_VIEW = BASIC_URL
			+ "/joblist/joblist?action=view&jobID=%s";
	
}
