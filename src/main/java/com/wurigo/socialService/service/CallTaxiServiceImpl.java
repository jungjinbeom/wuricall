package com.wurigo.socialService.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wurigo.socialService.dao.CallTaxiMapper;
@Service
public class CallTaxiServiceImpl implements CallTaxiService {
	@Autowired
	CallTaxiMapper callTaxiMapper;  
	@Override
	public void reserRegist(Map<String, Object> params) throws Exception {
		callTaxiMapper.reserRegist(params);
	}
	@Override
	public void socialReserRegist(Map<String, Object> params) throws Exception {
		callTaxiMapper.socialReserRegist(params);
	}
	@Override
	public List<Map<String, Object>> reserList(Map<String, Object> params) throws Exception {
		return null;
	}

}
