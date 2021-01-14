package com.wurigo.socialService.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TaxiGroupMapper {
	public String readOne(String groupNo)throws Exception;
	public int taxiGroupRegister(Map<String,Object> map)throws Exception;
	public List<Map<String,Object>> taxiGroupList(String adminNum)throws Exception;
	public int taxiGroupDelete(String groupNo)throws Exception;
	public int taxiGroupUpdate(String groupNo)throws Exception;
	public Map<String,Object> taxiGroupDetail(String groupNo)throws Exception;
	public Map<String,Object> taxiDriverInfo(String driverNo)throws Exception;
	public int taxiGroupEdit(String driverList,String groupName,String groupNo ) throws Exception;
}
