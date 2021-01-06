package com.wurigo.socialService.service;

import java.util.List;
import java.util.Map;

public interface TaxiDriverService {

	public String readOne(String adminNum)throws Exception;
	public List<Map<String,Object>>TaxiDriverList(Map<String,Object> map)throws Exception;
	public List<Map<String,Object>> taxiDriverAllList()throws Exception;
	public List<Map<String,Object>> taxiDriverInfoList(Map<String,Object> params)throws Exception;
	
	
}
