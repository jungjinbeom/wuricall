package com.wurigo.socialService.domain;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author USER
 *
 */
public class UserVO {
	
	
	private int usernum;
	private String id; 
	private String customerNo;
	private int customerType;
	private String email;
	private String username;
	private String phone; 
	private String password2;
	private String real_name;
	private String user_token;
	private String licenseImage;
	private String guardian;
	private String licenseNo;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime create_date ;	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime modify_date;
	
	public int getUsernum() {
		return usernum;
	}
	public void setUsernum(int usernum) {
		this.usernum = usernum;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCustomerNo() {
		return customerNo;
	}
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
	public int getCustomerType() {
		return customerType;
	}
	public void setCustomerType(int customerType) {
		this.customerType = customerType;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPassword2() {
		return password2;
	}
	public void setPassword2(String password2) {
		this.password2 = password2;
	}
	public String getReal_name() {
		return real_name;
	}
	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}
	public String getUser_token() {
		return user_token;
	}
	public void setUser_token(String user_token) {
		this.user_token = user_token;
	}
	public String getLicenseImage() {
		return licenseImage;
	}
	public void setLicenseImage(String licenseImage) {
		this.licenseImage = licenseImage;
	}
	public String getGuardian() {
		return guardian;
	}
	public void setGuardian(String guardian) {
		this.guardian = guardian;
	}
	public String getLicenseNo() {
		return licenseNo;
	}
	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}
		
}

