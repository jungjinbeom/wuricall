package com.wurigo.socialService.dao;

import java.util.List;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


@Repository
@Mapper
public interface ConstantDAO {
	
	public String  getConstantByName(@Param("_cName") String cname) throws Exception;  
	public List<Map<String, Object>> getConstantByType(@Param("_vType") String vType) throws Exception; 
}
