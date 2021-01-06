package com.wurigo.socialService.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface UserMapper {
	public String read(String customerNo)throws Exception;
	public Map<String,Object> readOne(int reserveNo)throws Exception;
	public void userRegister(Map<String,Object> params)throws Exception;
	public List<Map<String,Object>> userInfoList(String zip_code)throws Exception;
	public Map<String,Object> userInfo(String customerNo)throws Exception;
	public void userInfoEdit(Map<String,Object> params)throws Exception;
}

