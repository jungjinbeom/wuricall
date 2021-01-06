package com.wurigo.socialService.service;

import java.util.List;
import java.util.Map;

public interface TaxiGroupService {

	public String readOne(String groupNo)throws Exception;
	public void taxiGroupRegister(Map<String,Object> map)throws Exception;
	public List<Map<String,Object>> taxiGroupList(String adminNum)throws Exception;
	public void taxiGroupDelete(String adminNum)throws Exception;
	public Map<String,Object> taxiGroupDetail(String groupNo)throws Exception;
	public Map<String,Object> taxiDriverInfo(String driverNo)throws Exception;
	public void taxiGroupEdit(String driverList,String groupName,String groupNo) throws Exception;

}
