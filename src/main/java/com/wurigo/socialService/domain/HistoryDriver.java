package com.wurigo.socialService.domain;

import java.time.LocalDateTime;



public class HistoryDriver {
	private int serialNo;  
	private String driverNo;  
	private String customerNo;  
	private LocalDateTime action_date;  
	private int driver_status;  
	private int caseType;  
	private String startLoc;  
	private String endLoc;  
	private int callNo;  
	private int reserveNo;  
	private int reserveType;  
	private String reserveDate;  
	private int penalty_don;  
	private int totalpayment;  
	private int toll;  
	private int fare;  
	private LocalDateTime off_starttime;  
	private LocalDateTime off_endtime;  
	private String Cause;  
	private String encLat;  
	private String encLon;
	public int getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(int serialNo) {
		this.serialNo = serialNo;
	}
	public String getDriverNo() {
		return driverNo;
	}
	public void setDriverNo(String driverNo) {
		this.driverNo = driverNo;
	}
	public String getCustomerNo() {
		return customerNo;
	}
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
	public LocalDateTime getAction_date() {
		return action_date;
	}
	public void setAction_date(LocalDateTime action_date) {
		this.action_date = action_date;
	}
	public int getDriver_status() {
		return driver_status;
	}
	public void setDriver_status(int driver_status) {
		this.driver_status = driver_status;
	}
	public int getCaseType() {
		return caseType;
	}
	public void setCaseType(int caseType) {
		this.caseType = caseType;
	}
	public String getStartLoc() {
		return startLoc;
	}
	public void setStartLoc(String startLoc) {
		this.startLoc = startLoc;
	}
	public String getEndLoc() {
		return endLoc;
	}
	public void setEndLoc(String endLoc) {
		this.endLoc = endLoc;
	}
	public int getCallNo() {
		return callNo;
	}
	public void setCallNo(int callNo) {
		this.callNo = callNo;
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
	public String getReserveDate() {
		return reserveDate;
	}
	public void setReserveDate(String reserveDate) {
		this.reserveDate = reserveDate;
	}
	public int getPenalty_don() {
		return penalty_don;
	}
	public void setPenalty_don(int penalty_don) {
		this.penalty_don = penalty_don;
	}
	public int getTotalpayment() {
		return totalpayment;
	}
	public void setTotalpayment(int totalpayment) {
		this.totalpayment = totalpayment;
	}
	public int getToll() {
		return toll;
	}
	public void setToll(int toll) {
		this.toll = toll;
	}
	public int getFare() {
		return fare;
	}
	public void setFare(int fare) {
		this.fare = fare;
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
	public String getCause() {
		return Cause;
	}
	public void setCause(String cause) {
		Cause = cause;
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
	

}
