package com.wurigo.socialService.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wurigo.socialService.dao.TaxiDriverMapper;

@Service
public class TaxiDriverServiceImpl implements TaxiDriverService {

	@Autowired
	TaxiDriverMapper taxiDriverMapper;
	@Override
	public String readOne(String adminNum) throws Exception {
		return taxiDriverMapper.readOne(adminNum);
	}
	@Override
	public List<Map<String, Object>> TaxiDriverList(Map<String, Object> map) throws Exception {
		return taxiDriverMapper.TaxiDriverList(map);
	}
	@Override
	public List<Map<String, Object>> taxiDriverAllList() throws Exception {
		// TODO Auto-generated method stub
		return taxiDriverMapper.taxiDriverAllList();
	}
	@Override
	public List<Map<String, Object>> taxiDriverInfoList(Map<String, Object> params) throws Exception {
		// TODO Auto-generated method stub
		
		return taxiDriverMapper.taxiDriverInfoList(params);
	}
}
