package com.wurigo.socialService.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminMapper {
	
	public String read(String adminNum) throws Exception;//관리자 회원가입
	public String readOne(String adminId) throws Exception;//관리자 회원가입
	public void adminRegister(Map<String, Object> params) throws Exception;//관리자 회원가입
	public Map<String,Object> adminInfo(String adminId) throws Exception;//관리자 정보
	public void adminInfoEdit(Map<String, Object> params) throws Exception;//관리자 정보 변경
	public Map<String, Object> adminLogin(Map<String, Object> params) throws Exception;	//관리자 로그인
	public Map<String, Object> groupCodeRecord(Map<String, Object> params) throws Exception;//관리자 로그인
	public List<Map<String,Object>> districtInfo(Map<String, Object> params) throws Exception;//관리자 로그인
	public void adminUpdatePwd(Map<String,Object> params) throws Exception ; // 비밀번호 변경
	public String adminFindEmail(Map<String, Object> params)throws Exception;
	public void adminPasswordUpdate(String DbPassword,String email) throws Exception;// 비밀번호변경
	public void lastLoginDateInsert(String adminId) throws Exception;// 마지막 로그인 날짜추가
}
