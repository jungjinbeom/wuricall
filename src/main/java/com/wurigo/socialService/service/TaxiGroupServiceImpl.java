package com.wurigo.socialService.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wurigo.socialService.dao.TaxiGroupMapper;

@Service
public class TaxiGroupServiceImpl implements TaxiGroupService {
	@Autowired
	TaxiGroupMapper taxiGroupMapper;
	@Override
	public String readOne(String groupNo) throws Exception {
		
		return taxiGroupMapper.readOne(groupNo);
	}
	@Override
	public int taxiGroupRegister(Map<String,Object> map) throws Exception {
		return taxiGroupMapper.taxiGroupRegister(map);
	}
	@Override
	public List<Map<String, Object>> taxiGroupList(String adminNum) throws Exception {
		return taxiGroupMapper.taxiGroupList(adminNum);
	}
	@Override
	public int taxiGroupDelete(String groupNo) throws Exception {
		return taxiGroupMapper.taxiGroupDelete(groupNo);
	}
	@Override
	public Map<String, Object> taxiGroupDetail(String groupNo) throws Exception {
		return taxiGroupMapper.taxiGroupDetail(groupNo);
	}
	@Override
	public Map<String,Object> taxiDriverInfo(String driverNo) throws Exception {
		// TODO Auto-generated method stub
		return taxiGroupMapper.taxiDriverInfo(driverNo);
	}
	@Override
	public int taxiGroupEdit(String driverList,String groupName,String groupNo) throws Exception {
		return taxiGroupMapper.taxiGroupEdit(driverList,groupName,groupNo);
	}
	@Override
	public int taxiGroupUpdate(String groupNo) throws Exception {
		return taxiGroupMapper.taxiGroupUpdate(groupNo);
	}
}
