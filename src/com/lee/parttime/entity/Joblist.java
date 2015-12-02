
package com.lee.parttime.entity;
import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
 
@SuppressWarnings("serial")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Joblist implements Serializable{ 
	public int jobID; 
	public String jobTitle; 
	public String jobLat; 
	public String jobLon; 
	public String jobPostAddress; 
	public String jobPayFee; 
	public String jobPostDate; 
	public String jobPostCompany; 
	public String jobJSRQ; 
	public int jobZPRS; 
	public String jobGZRQ; 
	public String jobGZDZ; 
	public String jobMSSJ; 
	public String jobMSDZ; 
	public String jobZWMS; 
	public String jobPhone; 
	public String jobContactUser; 
	public String jobCity; 
 
	public Joblist(int jobID) { 
		super(); 
		this.jobID = jobID; 
	} 
 
	public Joblist() { 
		super(); 
	} 
 
	public int getJobID() { 
		return jobID; 
	} 
 
	public void setJobID(int jobID) { 
		this.jobID = jobID; 
	} 
 
	public String getJobTitle() { 
		return jobTitle; 
	} 
 
	public void setJobTitle(String jobTitle) { 
		this.jobTitle = jobTitle; 
	} 
 
	public String getJobLat() { 
		return jobLat; 
	} 
 
	public void setJobLat(String jobLat) { 
		this.jobLat = jobLat; 
	} 
 
	public String getJobLon() { 
		return jobLon; 
	} 
 
	public void setJobLon(String jobLon) { 
		this.jobLon = jobLon; 
	} 
 
	public String getJobPostAddress() { 
		return jobPostAddress; 
	} 
 
	public void setJobPostAddress(String jobPostAddress) { 
		this.jobPostAddress = jobPostAddress; 
	} 
 
	public String getJobPayFee() { 
		return jobPayFee; 
	} 
 
	public void setJobPayFee(String jobPayFee) { 
		this.jobPayFee = jobPayFee; 
	} 
 
	public String getJobPostDate() { 
		return jobPostDate; 
	} 
 
	public void setJobPostDate(String jobPostDate) { 
		this.jobPostDate = jobPostDate; 
	} 
 
	public String getJobPostCompany() { 
		return jobPostCompany; 
	} 
 
	public void setJobPostCompany(String jobPostCompany) { 
		this.jobPostCompany = jobPostCompany; 
	} 
 
	public String getJobJSRQ() { 
		return jobJSRQ; 
	} 
 
	public void setJobJSRQ(String jobJSRQ) { 
		this.jobJSRQ = jobJSRQ; 
	} 
 
	public int getJobZPRS() { 
		return jobZPRS; 
	} 
 
	public void setJobZPRS(int jobZPRS) { 
		this.jobZPRS = jobZPRS; 
	} 
 
	public String getJobGZRQ() { 
		return jobGZRQ; 
	} 
 
	public void setJobGZRQ(String jobGZRQ) { 
		this.jobGZRQ = jobGZRQ; 
	} 
 
	public String getJobGZDZ() { 
		return jobGZDZ; 
	} 
 
	public void setJobGZDZ(String jobGZDZ) { 
		this.jobGZDZ = jobGZDZ; 
	} 
 
	public String getJobMSSJ() { 
		return jobMSSJ; 
	} 
 
	public void setJobMSSJ(String jobMSSJ) { 
		this.jobMSSJ = jobMSSJ; 
	} 
 
	public String getJobMSDZ() { 
		return jobMSDZ; 
	} 
 
	public void setJobMSDZ(String jobMSDZ) { 
		this.jobMSDZ = jobMSDZ; 
	} 
 
	public String getJobZWMS() { 
		return jobZWMS; 
	} 
 
	public void setJobZWMS(String jobZWMS) { 
		this.jobZWMS = jobZWMS; 
	} 
 
	public String getJobPhone() { 
		return jobPhone; 
	} 
 
	public void setJobPhone(String jobPhone) { 
		this.jobPhone = jobPhone; 
	} 
 
	public String getJobContactUser() { 
		return jobContactUser; 
	} 
 
	public void setJobContactUser(String jobContactUser) { 
		this.jobContactUser = jobContactUser; 
	} 
 
	public String getJobCity() { 
		return jobCity; 
	} 
 
	public void setJobCity(String jobCity) { 
		this.jobCity = jobCity; 
	} 
 
 
} 
