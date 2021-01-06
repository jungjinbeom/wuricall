package com.wurigo.socialService.dao;

import java.util.List;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.wurigo.socialService.domain.Booking;

 
@Repository
@Mapper
public interface BookingDAO {
 
	
	public List<Map<String, Object>> findBookingByToday(
			@Param("customerNo") String customerNo,
			@Param("startDay") String startDay
			) throws Exception;
	
	public Map<String, Object> findBookingByNo(@Param("reserveNo")  String reserveNo) throws Exception;
	
	public Map<String,Object> bookingInfo(String customerNO, String reserveNo) throws Exception;
	
	public Map<String,Object> getBookingInfo(String reserveNo)throws Exception;
	
	public List<Map<String, Object>> findBookingByAfterDay(
			@Param("customerNo") String customerNo,
			@Param("startDay")   String startDay,
			@Param("state") 	  String state
			) throws Exception; 
	
	public List<Map<String, Object>> findBookingByInterval(
			@Param("customerNo") String customerNo,
			@Param("startDay")   String startDay, 
			@Param("endDay") 	  String endDay,
			@Param("state") 	  String state
			) throws Exception; 
	
	public int registBooking(Map<String, Object> map) throws Exception; 
	
	public int updateBooking(Map<String, Object> map) throws Exception; 
	
	public int deleteBooking(@Param("reserveNo") String reserveNo) throws Exception; 
	
	public int getMaxBookingNo() throws Exception;
	
	public int cancelAcceptedBooking(Map<String, Object> map) throws Exception; 
	  
}
