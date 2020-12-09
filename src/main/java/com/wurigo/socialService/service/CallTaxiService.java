package com.wurigo.socialService.service;


import java.util.List;
import java.util.Map;

public interface CallTaxiService {
	public void reserRegist(Map<String,Object> params)throws Exception;
	public void socialReserRegist(Map<String,Object> params)throws Exception;
	public List<Map<String,Object>> reserList(Map<String,Object> params)throws Exception;
}

