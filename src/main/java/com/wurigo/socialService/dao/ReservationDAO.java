package com.wurigo.socialService.dao;

import java.util.List;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.wurigo.socialService.domain.Reservation;

 
@Repository
@Mapper
public interface ReservationDAO {
 
	
	public List<Reservation> findReservationByToday(
			@Param("customerNo") String customerNo) throws Exception;
	
	public Reservation findReservationByNo(
			@Param("reserveNo")  String reserveNo,
			@Param("startDay")   String startDay) throws Exception;
	
	public Reservation findReservationByName(
			@Param("customerNo") String customerNo,
			@Param("reserveName")  String reserveName) throws Exception;
	
	public List<Reservation> findReservationByAfterDay(
			@Param("customerNo") String customerNo,
			@Param("startDay")   String startDay) throws Exception; 
	
	public List<Reservation> findReservationByInterval(
			@Param("customerNo") String customerNo,
			@Param("startDay")   String startDay, 
			@Param("endDay") 	  String endDay) throws Exception; 
	
	public int registReservation(Map<String, Object> map) throws Exception; 
	
	public int updateReservation(Map<String, Object> map) throws Exception; 
	
	public int deleteReservation(
			@Param("reserveNo") String reserveNo,
			@Param("startDay")   String startDay) throws Exception; 
	
	public int getMaxReserveNo() throws Exception;
	public int cancelAcceptedReservation(Map<String, Object> map) throws Exception;
	  
}
