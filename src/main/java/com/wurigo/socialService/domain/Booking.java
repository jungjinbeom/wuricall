package com.wurigo.socialService.domain;

import java.time.LocalDate;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;


public class Booking {
	
	
	private int reserveNo; 
	private int customerType;  // default=0 
	private String customerNo;
	private String driverNo;
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime create_date; 
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime update_date; 
	
	private String startDay; 
	private String reserveTime;
	
	private String startLocPlace; 
	private String finishLocPlace; 
	private String startAddress;
	private String finishAddress;
	
	private int state;
	private int caseType; 
	private int serviceType;   // 서비스 유형 ( 비지정= -1)
	private int serviceCharge ;
	private int callfee;        // 호출 수수료 
	private int groupNo;
	private int passengerType;  // 본인:0 타인:1 
	
	private String passenger;  //  탑승자 정보 
	
	private String drivers;    // 지정택시 그룹 기사목록 
	private String etcService; // 기타 서비스내
//	private String serviceMemo; // 서비스 메모  (not used )
	 
	private String s_sido_code;	
	private String e_sido_code;
	private String startLocXenc;  // PHP함수로 암호화된 값 -> 복호화 후에 java 함수로 다시 암호화되어 startLocX에 저장 
	private String startLocYenc;
	private String finishLocXenc; 
	private String finishLocYenc;
	
	
	private String startLocX;   // 자바함수로 암호화되어 저장된 값 
	private String startLocY;
	private String finishLocX; 
	private String finishLocY;
	
	private int callPayMethod;    //  '호출시 선택한 결제수단, 등록카드:0, 현장결제:1',

	
	private int fare;
	private int toll;
	private int totalpayment;
	private int anotherPayment;	 
	private int coupon_amt;    // coupon 금액 
	
	private int isPay;		// '0 미결재, 1 결제완료 결재 호출 후 정상일때 결제완료',
	private int payMethod;	//'최종 결재한 결제수단,  등록카드:0, 현금:10, 다른카드:11',
	private String coupon_no;
	private LocalDateTime pay_date;
	private int serviceChargeFee;  // ' 부가서비스수수료',
	
	private int isSocial;

	
	
	public int getReserveNo() {
		return reserveNo;
	}
	public void setReserveNo(int reserveNo) {
		this.reserveNo = reserveNo;
	}
	
	public String getCustomerNo() {
		return customerNo;
	}
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
	
	public String getStartDay() {
		return startDay;
	}
	public void setStartDay(String startDay) {
		this.startDay = startDay;
	}
	
	public String getReserveTime() {
		return reserveTime;
	}
	public void setReserveTime(String reserveTime) {
		this.reserveTime = reserveTime;
	}
	public String getStartLocPlace() {
		return startLocPlace;
	}
	public void setStartLocPlace(String startLocPlace) {
		this.startLocPlace = startLocPlace;
	}
	public String getFinishLocPlace() {
		return finishLocPlace;
	}
	public void setFinishLocPlace(String finishLocPlace) {
		this.finishLocPlace = finishLocPlace;
	}
	public String getStartAddress() {
		return startAddress;
	}
	public void setStartAddress(String startAddress) {
		this.startAddress = startAddress;
	}
	public String getFinishAddress() {
		return finishAddress;
	}
	public void setFinishAddress(String finishAddress) {
		this.finishAddress = finishAddress;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
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
	public int getServiceCharge() {
		return serviceCharge;
	}
	public void setServiceCharge(int serviceCharge) {
		this.serviceCharge = serviceCharge;
	}
	public int getCallfee() {
		return callfee;
	}
	public void setCallfee(int callfee) {
		this.callfee = callfee;
	}
	public int getGroupNo() {
		return groupNo;
	}
	public void setGroupNo(int groupNo) {
		this.groupNo = groupNo;
	}
	public int getPassengerType() {
		return passengerType;
	}
	public void setPassengerType(int passengerType) {
		this.passengerType = passengerType;
	}
	public String getPassenger() {
		return passenger;
	}
	public void setPassenger(String passenger) {
		this.passenger = passenger;
	}
	public String getDrivers() {
		return drivers;
	}
	public void setDrivers(String drivers) {
		this.drivers = drivers;
	}
	public String getEtcService() {
		return etcService;
	}
	public void setEtcService(String etcService) {
		this.etcService = etcService;
	}
/*	public String getServiceMemo() {
		return serviceMemo;
	}
	public void setServiceMemo(String serviceMemo) {
		this.serviceMemo = serviceMemo;
	}*/
	public int getServiceChargeFee() {
		return serviceChargeFee;
	}
	public void setServiceChargeFee(int serviceChargeFee) {
		this.serviceChargeFee = serviceChargeFee;
	}
	public String getStartLocX() {
		return startLocX;
	}
	public void setStartLocX(String startLocX) {
		this.startLocX = startLocX;
	}
	public String getStartLocY() {
		return startLocY;
	}
	public void setStartLocY(String startLocY) {
		this.startLocY = startLocY;
	}
	public String getFinishLocX() {
		return finishLocX;
	}
	public void setFinishLocX(String finishLocX) {
		this.finishLocX = finishLocX;
	}
	public String getFinishLocY() {
		return finishLocY;
	}
	public void setFinishLocY(String finishLocY) {
		this.finishLocY = finishLocY;
	}
	public int getCallPayMethod() {
		return callPayMethod;
	}
	public void setCallPayMethod(int callPayMethod) {
		this.callPayMethod = callPayMethod;
	}
	public int getCustomerType() {
		return customerType;
	}
	public void setCustomerType(int customerType) {
		this.customerType = customerType;
	}
	public LocalDateTime getCreate_date() {
		return create_date;
	}
	public void setCreate_date(LocalDateTime create_date) {
		this.create_date = create_date;
	}
	public LocalDateTime getUpdate_date() {
		return update_date;
	}
	public void setUpdate_date(LocalDateTime update_date) {
		this.update_date = update_date;
	}
	public int getFare() {
		return fare;
	}
	public void setFare(int fare) {
		this.fare = fare;
	}
	public int getToll() {
		return toll;
	}
	public void setToll(int toll) {
		this.toll = toll;
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
	public int getCoupon_amt() {
		return coupon_amt;
	}
	public void setCoupon_amt(int coupon_amt) {
		this.coupon_amt = coupon_amt;
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
	public String getStartLocXenc() {
		return startLocXenc;
	}
	public void setStartLocXenc(String startLocXenc) {
		this.startLocXenc = startLocXenc;
	}
	public String getStartLocYenc() {
		return startLocYenc;
	}
	public void setStartLocYenc(String startLocYenc) {
		this.startLocYenc = startLocYenc;
	}
	public String getFinishLocXenc() {
		return finishLocXenc;
	}
	public void setFinishLocXenc(String finishLocXenc) {
		this.finishLocXenc = finishLocXenc;
	}
	public String getFinishLocYenc() {
		return finishLocYenc;
	}
	public void setFinishLocYenc(String finishLocYenc) {
		this.finishLocYenc = finishLocYenc;
	}
	public String getS_sido_code() {
		return s_sido_code;
	}
	public void setS_sido_code(String s_sido_code) {
		this.s_sido_code = s_sido_code;
	}
	public String getE_sido_code() {
		return e_sido_code;
	}
	public void setE_sido_code(String e_sido_code) {
		this.e_sido_code = e_sido_code;
	}
	@Override
	public String toString() {
		return "Booking [reserveNo=" + reserveNo + ", customerType=" + customerType + ", customerNo=" + customerNo
				+ ", create_date=" + create_date + ", update_date=" + update_date + ", startDay=" + startDay
				+ ", reserveTime=" + reserveTime + ", startLocPlace=" + startLocPlace + ", finishLocPlace="
				+ finishLocPlace + ", startAddress=" + startAddress + ", finishAddress=" + finishAddress + ", state="
				+ state + ", caseType=" + caseType + ", serviceType=" + serviceType + ", serviceCharge=" + serviceCharge
				+ ", callfee=" + callfee + ", groupNo=" + groupNo + ", passengerType=" + passengerType + ", passenger="
				+ passenger + ", drivers=" + drivers + ", etcService=" + etcService + ", s_sido_code=" + s_sido_code
				+ ", e_sido_code=" + e_sido_code + ", startLocXenc=" + startLocXenc + ", startLocYenc=" + startLocYenc
				+ ", finishLocXenc=" + finishLocXenc + ", finishLocYenc=" + finishLocYenc + ", startLocX=" + startLocX
				+ ", startLocY=" + startLocY + ", finishLocX=" + finishLocX + ", finishLocY=" + finishLocY
				+ ", callPayMethod=" + callPayMethod + ", fare=" + fare + ", toll=" + toll + ", totalpayment="
				+ totalpayment + ", anotherPayment=" + anotherPayment + ", coupon_amt=" + coupon_amt + ", isPay="
				+ isPay + ", payMethod=" + payMethod + ", coupon_no=" + coupon_no + ", pay_date=" + pay_date
				+ ", serviceChargeFee=" + serviceChargeFee + "]";
	}
	
	public Map<String, Object> convert2Map() {
		
		Map<String, Object> transMap = new HashMap<String, Object>();
		transMap.put( "reserveNo", reserveNo );  
		transMap.put( "customerType", customerType );   // default=0 
		transMap.put( "customerNo", customerNo ); 
		transMap.put( "create_date", create_date ); 
		transMap.put( "update_date", update_date ); 

		transMap.put( "startDay",startDay );  
		transMap.put( "reserveTime",reserveTime ); 

		transMap.put( "startLocPlace",startLocPlace );  
		transMap.put( "finishLocPlace",finishLocPlace );  
		transMap.put( "startAddress",startAddress ); 
		transMap.put( "finishAddress",finishAddress ); 

		transMap.put( "state",state  ); 
		transMap.put( "caseType", caseType );  
		transMap.put( "serviceType", serviceType );    // 서비스 유형 ( 비지정= -1)
		transMap.put( "serviceCharge", serviceCharge ); 
		transMap.put( "callfee", callfee );         // 호출 수수료 
		transMap.put( "groupNo", groupNo ); 
		transMap.put( "passengerType", passengerType );   // 본인:0 타인:1 

		transMap.put( "passenger", passenger );   //  탑승자 정보 

		transMap.put( "drivers", drivers );     // 지정택시 그룹 기사목록 
		transMap.put( "etcService", etcService );  // 기타 서비스내 
		    
		transMap.put( "s_sido_code", s_sido_code ); 	
		transMap.put( "e_sido_code", e_sido_code ); 
		transMap.put( "startLocXenc", startLocXenc);   // PHP함수로 암호화된 값 -> 복호화 후에 java 함수로 다시 암호화되어 startLocX에 저장 
		transMap.put( "startLocYenc", startLocYenc ); 
		transMap.put( "finishLocXenc", finishLocXenc );  
		transMap.put( "finishLocYenc", finishLocYenc ); 

		transMap.put( "startLocX", startLocX );    // 자바함수로 암호화되어 저장된 값 
		transMap.put( "startLocY", startLocY ); 
		transMap.put( "finishLocX", finishLocX );  
		transMap.put( "finishLocY", finishLocY ); 

		transMap.put( "callPayMethod", callPayMethod );     //  '호출시 선택한 결제수단, 등록카드:0, 현장결제:1',
		transMap.put( "fare", fare ); 
		transMap.put( "toll", toll ); 
		transMap.put( "totalpayment",totalpayment  ); 
		transMap.put( "anotherPayment",anotherPayment  ); 	 
		transMap.put( "coupon_amt", coupon_amt );     // coupon 금액 

		transMap.put( "isPay", isPay ); 		// '0 미결재, 1 결제완료 결재 호출 후 정상일때 결제완료',
		transMap.put( "payMethod", payMethod ); 	//'최종 결재한 결제수단,  등록카드:0, 현금:10, 다른카드:11',
		transMap.put( "coupon_no", coupon_no ); 
		transMap.put( "pay_date", pay_date ); 
		transMap.put( "serviceChargeFee", serviceChargeFee );   // ' 부가서비스수수료',	
		transMap.put( "isSocial", isSocial );   // 무료예약 여부 
		
		
		return transMap;
	}
	public String getDriverNo() {
		return driverNo;
	}
	public void setDriverNo(String driverNo) {
		this.driverNo = driverNo;
	}
	public int getIsSocial() {
		return isSocial;
	}
	public void setIsSocial(int isSocial) {
		this.isSocial = isSocial;
	}
}
