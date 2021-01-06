package com.wurigo.socialService.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TaxiDriverMapper {
	public String readOne(String adminNum)throws Exception;
	public List<Map<String,Object>> TaxiDriverList(Map<String,Object> map)throws Exception;
	public List<Map<String,Object>> taxiDriverAllList()throws Exception;
	public List<Map<String,Object>> taxiDriverInfoList(Map<String,Object> params)throws Exception;
	public String taxiDriverInfo(String driverNo)throws Exception;
}
