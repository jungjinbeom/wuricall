package com.wurigo.socialService.domain;

import java.time.LocalDateTime;



public class HistoryCustomer {
	
	private int serialNo;   
	private String customerNo;   
	private LocalDateTime action_date;  
	private int caseType;   
	private int callNo;   
	private String driverNo;   
	private int reserveNo;   
	private int reserveType;   
	private String reserveDate;   
	private int penalty_don;   
	private int totalpayment;   
	private int toll;   
	private int fare;   
	private String Cause;   
	private String encLat;   
	private String encLon;
	public int getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(int serialNo) {
		this.serialNo = serialNo;
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
	public int getCaseType() {
		return caseType;
	}
	public void setCaseType(int caseType) {
		this.caseType = caseType;
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
