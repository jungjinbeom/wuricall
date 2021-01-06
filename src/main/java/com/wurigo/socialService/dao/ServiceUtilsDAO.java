package com.wurigo.socialService.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface ServiceUtilsDAO {
 
	
	public String get_group_drivers(Map<String, Object> map) throws Exception;
	
	public List<Map<String,String>> get_proper_drivers(Map<String, Object> map) throws Exception;

	public String get_driver_type(String driverNo) throws Exception;
	
	// 예약일이 기사 휴무일인가?
	public int isOffday(Map<String, Object> map) throws Exception;
	
	// 기사가 한번 취소한 시간대의 예약은 다시 받을 수 없음  
	public int isCanceledDriver(
			@Param("driverNo")  String driverNo,
			@Param("startDay")   String startDay,
			@Param("reserveTime")   String reserveTime,
			@Param("customerNo") String customerNo ) throws Exception;
	
	//예약일자가 동일한 날짜의 예약이 확정된 예약의 출발도착지 가져오기 
	public List<Map<String,String>> getReservedLocation(
			@Param("driverNo")  String driverNo,
			@Param("startDay")   String startDay,
			@Param("reserveTime")   String reserveTime ) throws Exception; 
	
	public int delete_booking_noti(Map<String, Object> map) throws Exception; 
	
	public int insert_booking_noti(Map<String, Object> map) throws Exception; 
	
	public int insert_social_booking(Map<String, Object> map ) throws Exception;
	
	public int delete_social_booking(Map<String, Object> map ) throws Exception;
	
	public int isSocialBooking(@Param("reserveType")int reserveType,@Param("reserveNo") int reserveNo) 
			throws Exception;
	
//  social booking	
	public List<Map<String,Object>> taxiReserTodayCount(Map<String, Object> param)throws Exception;
	public List<Map<String,Object>> userCustomerNoList(String email)throws Exception;
	  
}
