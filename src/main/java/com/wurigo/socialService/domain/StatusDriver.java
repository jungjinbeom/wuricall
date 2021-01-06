package com.wurigo.socialService.domain;

import java.time.LocalDateTime;


import com.fasterxml.jackson.annotation.JsonFormat;



public class StatusDriver {
	
	private String driverNo;
	private int status;
	private int driverState;
	private int caseType;
	private LocalDateTime curdate;
	private int callType;
	private int callNo;
	private String customerNo;
	private String reserveDate;
	private int reserveNo;
	private int reserveType;
	private String noti_from;
	private String noti_type;
	private String noti_title;
	private String noti_message;
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime off_starttime;
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime off_endtime;
	
	private String encLat;
	private String encLon;
	private String encLatJ;   // java 모듈로 encryption
	private String encLonJ;	  // java 모듈로 encryption
	public String getDriverNo() {
		return driverNo;
	}
	public void setDriverNo(String driverNo) {
		this.driverNo = driverNo;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getDriverState() {
		return driverState;
	}
	public void setDriverState(int driverState) {
		this.driverState = driverState;
	}
	public int getCaseType() {
		return caseType;
	}
	public void setCaseType(int caseType) {
		this.caseType = caseType;
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
	public String getCustomerNo() {
		return customerNo;
	}
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
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
	public String getNoti_from() {
		return noti_from;
	}
	public void setNoti_from(String noti_from) {
		this.noti_from = noti_from;
	}
	public String getNoti_type() {
		return noti_type;
	}
	public void setNoti_type(String noti_type) {
		this.noti_type = noti_type;
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
	public LocalDateTime getOff_starttime() {
		return off_starttime;
	}
	public void setOff_starttime(LocalDateTime off_starttime) {
		this.off_starttime = off_starttime;
	}
	public LocalDateTime getOff_endtime() {
		return off_endtime;
	}
	public void setOff_endtime(LocalDateTime off_endtime) {
		this.off_endtime = off_endtime;
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
	
	

}
