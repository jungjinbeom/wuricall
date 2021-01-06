package com.wurigo.socialService.dao;

import java.util.List;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.wurigo.socialService.domain.HistoryCustomer;


@Repository
@Mapper
public interface HistoryCustomerDAO {

	public List<HistoryCustomer> getCustomerHistory(Map<String, Object> map) throws Exception;
	
	public int insertCustomerHistory(Map<String, Object> map) throws Exception;
	public int deleteCustomerHistory(@Param("_customerNo") String customerNo) throws Exception;
}
