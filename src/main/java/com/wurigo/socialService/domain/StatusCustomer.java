package com.wurigo.socialService.domain;

import java.time.LocalDateTime;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class StatusCustomer {
	
	private String customerNo; 
	private int status;
	
	private int callType;
	private int callNo;
	private String driverNo;
	private int caseType;
	private String startDay;
	private String reserveDate;
	private int reserveNo;
	private int reserveType;
	private String reserveDriver;
	private String noti_from;
	private String noti_title;
	private String noti_message;
	private String notrace;
	private String encLat;
	private String encLon;
	private String encLatJ;   // java 모듈로 encryption
	private String encLonJ;	  // java 모듈로 encryption
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime curdate;
	
	
	public StatusCustomer() {
 //		super();
	}
 	
	public String getCustomerNo() {
		return customerNo;
	}
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public LocalDateTime getCurdate() {
		return curdate;
	}
	public void setCurdate(LocalDateTime curdate) {
		this.curdate = curdate;
	}
	public int getCallType() {
		return callType;
	}
	public void setCallType(int callType) {
		this.callType = callType;
	}
	public int getCallNo() {
		return callNo;
	}
	public void setCallNo(int callNo) {
		this.callNo = callNo;
	}
	public String getDriverNo() {
		return driverNo;
	}
	public void setDriverNo(String driverNo) {
		this.driverNo = driverNo;
	}
	public int getCaseType() {
		return caseType;
	}
	public void setCaseType(int caseType) {
		this.caseType = caseType;
	}
	public String getStartDay() {
		return startDay;
	}
	public void setStartDay(String startDay) {
		this.startDay = startDay;
	}
	public String getReserveDate() {
		return reserveDate;
	}
	public void setReserveDate(String reserveDate) {
		this.reserveDate = reserveDate;
	}
	public int getReserveNo() {
		return reserveNo;
	}
	public void setReserveNo(int reserveNo) {
		this.reserveNo = reserveNo;
	}
	public int getReserveType() {
		return reserveType;
	}
	public void setReserveType(int reserveType) {
		this.reserveType = reserveType;
	}
	public String getReserveDriver() {
		return reserveDriver;
	}
	public void setReserveDriver(String reserveDriver) {
		this.reserveDriver = reserveDriver;
	}
	public String getNoti_from() {
		return noti_from;
	}
	public void setNoti_from(String noti_from) {
		this.noti_from = noti_from;
	}
	public String getNoti_title() {
		return noti_title;
	}
	public void setNoti_title(String noti_title) {
		this.noti_title = noti_title;
	}
	public String getNoti_message() {
		return noti_message;
	}
	public void setNoti_message(String noti_message) {
		this.noti_message = noti_message;
	}
	public String getNotrace() {
		return notrace;
	}
	public void setNotrace(String notrace) {
		this.notrace = notrace;
	}
	public String getEncLat() {
		return encLat;
	}
	public void setEncLat(String encLat) {
		this.encLat = encLat;
	}
	public String getEncLon() {
		return encLon;
	}
	public void setEncLon(String encLon) {
		this.encLon = encLon;
	}
	public String getEncLatJ() {
		return encLatJ;
	}
	public void setEncLatJ(String encLatJ) {
		this.encLatJ = encLatJ;
	}
	public String getEncLonJ() {
		return encLonJ;
	}
	public void setEncLonJ(String encLonJ) {
		this.encLonJ = encLonJ;
	}
	@Override
	public String toString() {
		return "StatusCustomer [getCustomerNo()=" + getCustomerNo() + ", getStatus()=" + getStatus() + ", getCurdate()="
				+ getCurdate() + ", getCallType()=" + getCallType() + ", getCallNo()=" + getCallNo()
				+ ", getDriverNo()=" + getDriverNo() + ", getCaseType()=" + getCaseType() + ", getStartDay()="
				+ getStartDay() + ", getReserveDate()=" + getReserveDate() + ", getReserveNo()=" + getReserveNo()
				+ ", getReserveType()=" + getReserveType() + ", getReserveDriver()=" + getReserveDriver()
				+ ", getNoti_from()=" + getNoti_from() + ", getNoti_title()=" + getNoti_title() + ", getNoti_message()="
				+ getNoti_message() + ", getNotrace()=" + getNotrace() + ", getEncLat()=" + getEncLat()
				+ ", getEncLon()=" + getEncLon() + ", getEncLatJ()=" + getEncLatJ() + ", getEncLonJ()=" + getEncLonJ()
				+ "]";
	}
	
	
}
