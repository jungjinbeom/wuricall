package com.wurigo.socialService.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.wurigo.socialService.domain.Booking;
import com.wurigo.socialService.domain.Reservation;


public interface ReservationService {

	public Reservation findReservationByNo(
			@Param("reserveNo")  String reserveNo,
			@Param("startDay")   String startDay) throws Exception;
	
	public Reservation findReservationByName(
			@Param("customerNo") String customerNo,
			@Param("reserveName")  String reserveName) throws Exception;

	/*
	public List<Reservation> findReservationByToday(
			@Param("customerNo") String customerNo) throws Exception;

	public List<Reservation> findReservationByAfterDay(
			@Param("customerNo") String customerNo,
			@Param("startDay")   String startDay) throws Exception; 
	
	public List<Reservation> findReservationByInterval(
			@Param("customerNo") String customerNo,
			@Param("startDay")   String startDay, 
			@Param("endDay") 	  String endDay) throws Exception;  */
	
	public Map<String, Object> registReservation(Map<String, Object> map) throws Exception; 
	
	public Map<String, Object> updateReservation(Map<String, Object> map) throws Exception; 
	
	public void deleteReservation(
			@Param("reserveNo") String reserveNo,
			@Param("startDay")   String startDay) throws Exception; 
	
	public int getMaxReserveNo() throws Exception;
	public Map<String, Object> cancelReservation(Map<String, Object> param) throws Exception;
	
///////////////////////////////////////////////////////////////////////////////////////////////////////
	public Map<String, Object> findBookingByNo(@Param("reserveNo")  String reserveNo) throws Exception;
//	public Booking findBookingByNo(@Param("reserveNo")  String reserveNo) throws Exception;
	public Map<String,Object> getBookingInfo(@Param("reserveNo") String reserveNo)throws Exception;

/*	
	public List<Booking> findBookingByToday(@Param("customerNo") String customerNo) throws Exception;
	public List<Booking> findBookingByAfterDay(
			@Param("customerNo") String customerNo,
			@Param("startDay")   String startDay) throws Exception; 
	
	public List<Booking> findBookingByInterval(
			@Param("customerNo") String customerNo,
			@Param("startDay")   String startDay, 
			@Param("endDay") 	  String endDay) throws Exception;  */
	
	public Map<String, Object> registBooking(Map<String, Object> map) throws Exception; 
	public Map<String, Object> regist_socialBooking(Map<String, Object> map) throws Exception;
	public Map<String, Object> updateBooking(Map<String, Object> map) throws Exception; 
	
//	public int deleteBooking(@Param("reserveNo") String reserveNo) throws Exception; 
	
	public int getMaxBookingNo() throws Exception;
	
//	public Map<String, Object> getBookingList(Map<String,Object> map) throws Exception;
	public Map<String, Object> get_socialBookingList(Map<String,Object> map) throws Exception;
	public Map<String, Object> cancel_socialBooking(Map<String, Object> param) throws Exception;
	public Map<String, Object> getReservationList(Map<String,Object> map ) throws Exception;
	public Map<String, Object> update_socialBooking(Map<String, Object> param) throws Exception;
	public Map<String, Object> cancelBooking(Map<String, Object> param) throws Exception;
	
//////////////////////////////////////////////////////////////////////////////////////////////////
	public  List<String> find_proper_drivers_No(@Param("customerNo") String customerNo,@Param("customerType")int customerType,
			@Param("startAddress") String startAddress, @Param("groupNo")int groupNo, 
			@Param("startDay")String startDay, @Param("reserveTime") String reserveTime) throws Exception;
	
	public int isReservedTime(@Param("driverNo")String driverNo,
			@Param("startDay") String startDay, @Param("reserveTime") String reserveTime) throws Exception;
	
//  social booking	
	public List<Map<String,Object>> taxiReserTodayCount(Map<String, Object> param)throws Exception;
	public List<Map<String,Object>> userCustomerNoList(String email)throws Exception;
	public List<Map<String,Object>> findBooking(String groupNo)throws Exception ;
	
}
