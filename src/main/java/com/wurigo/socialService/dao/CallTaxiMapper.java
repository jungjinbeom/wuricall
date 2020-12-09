package com.wurigo.socialService.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
@Mapper
public interface CallTaxiMapper {
	
	public void reserRegist(Map<String,Object> params)throws Exception;
	public void socialReserRegist(Map<String,Object> params)throws Exception;
}
