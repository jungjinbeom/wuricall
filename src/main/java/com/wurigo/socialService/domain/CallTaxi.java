package com.wurigo.socialService.domain;

import java.time.LocalDateTime;



public class CallTaxi {
	 
	private int callNo;
	private int callType;
	private int customerType;
	private String customerNo;
	private LocalDateTime call_date;

	private int status;
	private int callCount;
	private int caseType;
	private int serviceType;
	private int callfee;
	private int callPayMethod;

	private int coupon_amt;    // coupon 금액
	private int serviceCharge;  // 부가서비스 수수료

	private String startLoc;
	private String endLoc;
	private String startAddress;
	private String endAddress;
	private String s_latitude;
	private String s_longitude;
	private String e_latitude;
	private String e_longitude;
	private String sLatenc;
	private String sLonenc;
	private String eLatenc;
	private String eLonenc;

	private String customerPhone;
	private String driverlist;
	private String etcService;
	private int taxi_type;
	private int taxi_size;

	private int toll;
	private int fare;
	private int totalpayment;
	private int anotherPayment;
	private LocalDateTime action_date;
	private String driverNo;
	private int penalty_don;

	private int isPay;		// '0 미결재, 1 결제완료 결재 호출 후 정상일때 결제완료',
	private int payMethod;	//'최종 결재한 결제수단,  등록카드:0, 현금:10, 다른카드:11',
	private String coupon_no;
	private LocalDateTime pay_date;
	private int serviceChargeFee;  // ' 부가서비스수수료',
	
	
	public int getCallNo() {
		return callNo;
	}
	public void setCallNo(int callNo) {
		this.callNo = callNo;
	}
	public int getCallType() {
		return callType;
	}
	public void setCallType(int callType) {
		this.callType = callType;
	}
	public int getCustomerType() {
		return customerType;
	}
	public void setCustomerType(int customerType) {
		this.customerType = customerType;
	}
	public String getCustomerNo() {
		return customerNo;
	}
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
	public LocalDateTime getCall_date() {
		return call_date;
	}
	public void setCall_date(LocalDateTime call_date) {
		this.call_date = call_date;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getCallCount() {
		return callCount;
	}
	public void setCallCount(int callCount) {
		this.callCount = callCount;
	}
	public int getCaseType() {
		return caseType;
	}
	public void setCaseType(int caseType) {
		this.caseType = caseType;
	}
	public int getServiceType() {
		return serviceType;
	}
	public void setServiceType(int serviceType) {
		this.serviceType = serviceType;
	}
	public int getCallfee() {
		return callfee;
	}
	public void setCallfee(int callfee) {
		this.callfee = callfee;
	}
	public int getCallPayMethod() {
		return callPayMethod;
	}
	public void setCallPayMethod(int callPayMethod) {
		this.callPayMethod = callPayMethod;
	}
	public int getCoupon_amt() {
		return coupon_amt;
	}
	public void setCoupon_amt(int coupon_amt) {
		this.coupon_amt = coupon_amt;
	}
	public int getServiceCharge() {
		return serviceCharge;
	}
	public void setServiceCharge(int serviceCharge) {
		this.serviceCharge = serviceCharge;
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
	public String getStartAddress() {
		return startAddress;
	}
	public void setStartAddress(String startAddress) {
		this.startAddress = startAddress;
	}
	public String getEndAddress() {
		return endAddress;
	}
	public void setEndAddress(String endAddress) {
		this.endAddress = endAddress;
	}
	public String getS_latitude() {
		return s_latitude;
	}
	public void setS_latitude(String s_latitude) {
		this.s_latitude = s_latitude;
	}
	public String getS_longitude() {
		return s_longitude;
	}
	public void setS_longitude(String s_longitude) {
		this.s_longitude = s_longitude;
	}
	public String getE_latitude() {
		return e_latitude;
	}
	public void setE_latitude(String e_latitude) {
		this.e_latitude = e_latitude;
	}
	public String getE_longitude() {
		return e_longitude;
	}
	public void setE_longitude(String e_longitude) {
		this.e_longitude = e_longitude;
	}
	public String getsLatenc() {
		return sLatenc;
	}
	public void setsLatenc(String sLatenc) {
		this.sLatenc = sLatenc;
	}
	public String getsLonenc() {
		return sLonenc;
	}
	public void setsLonenc(String sLonenc) {
		this.sLonenc = sLonenc;
	}
	public String geteLatenc() {
		return eLatenc;
	}
	public void seteLatenc(String eLatenc) {
		this.eLatenc = eLatenc;
	}
	public String geteLonenc() {
		return eLonenc;
	}
	public void seteLonenc(String eLonenc) {
		this.eLonenc = eLonenc;
	}
	public String getCustomerPhone() {
		return customerPhone;
	}
	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}
	public String getDriverlist() {
		return driverlist;
	}
	public void setDriverlist(String driverlist) {
		this.driverlist = driverlist;
	}
	public String getEtcService() {
		return etcService;
	}
	public void setEtcService(String etcService) {
		this.etcService = etcService;
	}
	public int getTaxi_type() {
		return taxi_type;
	}
	public void setTaxi_type(int taxi_type) {
		this.taxi_type = taxi_type;
	}
	public int getTaxi_size() {
		return taxi_size;
	}
	public void setTaxi_size(int taxi_size) {
		this.taxi_size = taxi_size;
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
	public int getTotalpayment() {
		return totalpayment;
	}
	public void setTotalpayment(int totalpayment) {
		this.totalpayment = totalpayment;
	}
	public int getAnotherPayment() {
		return anotherPayment;
	}
	public void setAnotherPayment(int anotherPayment) {
		this.anotherPayment = anotherPayment;
	}
	public LocalDateTime getAction_date() {
		return action_date;
	}
	public void setAction_date(LocalDateTime action_date) {
		this.action_date = action_date;
	}
	public String getDriverNo() {
		return driverNo;
	}
	public void setDriverNo(String driverNo) {
		this.driverNo = driverNo;
	}
	public int getPenalty_don() {
		return penalty_don;
	}
	public void setPenalty_don(int penalty_don) {
		this.penalty_don = penalty_don;
	}
	public int getIsPay() {
		return isPay;
	}
	public void setIsPay(int isPay) {
		this.isPay = isPay;
	}
	public int getPayMethod() {
		return payMethod;
	}
	public void setPayMethod(int payMethod) {
		this.payMethod = payMethod;
	}
	public String getCoupon_no() {
		return coupon_no;
	}
	public void setCoupon_no(String coupon_no) {
		this.coupon_no = coupon_no;
	}
	public LocalDateTime getPay_date() {
		return pay_date;
	}
	public void setPay_date(LocalDateTime pay_date) {
		this.pay_date = pay_date;
	}
	public int getServiceChargeFee() {
		return serviceChargeFee;
	}
	public void setServiceChargeFee(int serviceChargeFee) {
		this.serviceChargeFee = serviceChargeFee;
	}

}
