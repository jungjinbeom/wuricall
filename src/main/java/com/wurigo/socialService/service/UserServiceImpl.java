package com.wurigo.socialService.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wurigo.socialService.dao.UserMapper;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserMapper userMapper;
	
	@Override
	public String read(String customerNo) throws Exception {
		return userMapper.read(customerNo);
	}

	@Override
	public void userRegister(Map<String, Object> params) throws Exception {
		userMapper.userRegister(params);
	}

	@Override
	public List<Map<String, Object>> userInfoList(String zip_code) throws Exception {
		return userMapper.userInfoList(zip_code);
	}

	@Override
	public Map<String, Object> userInfo(String customerNo) throws Exception {
		// TODO Auto-generated method stub
		return userMapper.userInfo(customerNo);
	}

	@Override
	public void userInfoEdit(Map<String, Object> params) throws Exception {
		
		 userMapper.userInfoEdit(params);
	}
	
}
