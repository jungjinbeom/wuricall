
package com.wurigo.socialService.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.wurigo.socialService.dao.AdminMapper;

@Service
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	AdminMapper adminMapper;
	
	@Override
	public String read(String adminNum) throws Exception 
	{
		return adminMapper.read(adminNum);
	}
	@Override
	public String readOne(String adminId) throws Exception {
		// TODO Auto-generated method stub
		return adminMapper.readOne(adminId);
	}
	@Override
	public void adminRegister(Map<String, Object> params) throws Exception {
		
		adminMapper.adminRegister(params);
	}
	@Override
	public Map<String,Object> adminInfo(String adminId) throws Exception {
		return adminMapper.adminInfo(adminId);
	}
	@Override
	public void adminInfoEdit(Map<String, Object> params) throws Exception {
		// TODO Auto-generated method stub
		 adminMapper.adminInfoEdit(params);
	}
	@Override
	public String adminFindEmail(Map<String, Object> params) throws Exception {
		// TODO Auto-generated method stub
		return adminMapper.adminFindEmail(params);
	}
	@Override
	public Map<String, Object> adminLogin(Map<String, Object> params) throws Exception {
		return adminMapper.adminLogin(params);
	}
	@Override
	public Map<String, Object> groupCodeRecord(Map<String, Object> params) throws Exception {
		// TODO Auto-generated method stub
		return adminMapper.groupCodeRecord(params);
	}
	@Override
	public void adminPasswordUpdate(String DbPassword,String email) throws Exception {
		adminMapper.adminPasswordUpdate(DbPassword,email);
	}
	@Override
	public void lastLoginDateInsert(String adminId) throws Exception {
		adminMapper.lastLoginDateInsert(adminId);
	}
	@Override
	public List<Map<String,Object>>  districtInfo(Map<String, Object> params) throws Exception {
		return adminMapper.districtInfo(params);
	}
	@Override
	public void adminUpdatePwd(Map<String, Object> params) throws Exception {
		// TODO Auto-generated method stub
		adminMapper.adminUpdatePwd(params);
	}
	
	
	

}

