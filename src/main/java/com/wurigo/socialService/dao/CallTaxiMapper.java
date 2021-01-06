package com.wurigo.socialService.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
@Mapper
public interface CallTaxiMapper {
	
	public Map<String,Object> reserRegist(Map<String,Object> params)throws Exception;
	public void socialReserRegist(Map<String,Object> params)throws Exception;
	public List<Map<String,Object>> taxiReserTodayCount(Map<String,Object> params)throws Exception;
	public List<Map<String,Object>> userCustomerNoList(String email)throws Exception;
	
}
