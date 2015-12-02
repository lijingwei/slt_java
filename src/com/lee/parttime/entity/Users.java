package com.lee.parttime.entity;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@SuppressWarnings("serial")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Users implements Serializable {
	public int usersID; 
	public String usersName; 
	public String usersPwd; 
	public String usersInvalitCode; 
	public int usersIsForgot; 
	public String usersRegDate; 

 
	public Users(int usersID) { 
		super(); 
		this.usersID = usersID; 
	} 
 
	public Users() { 
		super(); 
	} 
 
	public int getUsersID() { 
		return usersID; 
	} 
 
	public void setUsersID(int usersID) { 
		this.usersID = usersID; 
	} 
 
	public String getUsersName() { 
		return usersName; 
	} 
 
	public void setUsersName(String usersName) { 
		this.usersName = usersName; 
	} 
 
	public String getUsersPwd() { 
		return usersPwd; 
	} 
 
	public void setUsersPwd(String usersPwd) { 
		this.usersPwd = usersPwd; 
	} 
 
	public String getUsersInvalitCode() { 
		return usersInvalitCode; 
	} 
 
	public void setUsersInvalitCode(String usersInvalitCode) { 
		this.usersInvalitCode = usersInvalitCode; 
	} 
 
	public int getUsersIsForgot() { 
		return usersIsForgot; 
	} 
 
	public void setUsersIsForgot(int usersIsForgot) { 
		this.usersIsForgot = usersIsForgot; 
	} 
 
	public String getUsersRegDate() { 
		return usersRegDate; 
	} 
 
	public void setUsersRegDate(String usersRegDate) { 
		this.usersRegDate = usersRegDate; 
	} 
 
}
