package com.wurigo.socialService.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.wurigo.socialService.domain.Customer;
import com.wurigo.socialService.domain.UserVO;


public interface CustomerService {

	public List<Customer> getCustomer() throws Exception;
	public Customer read(String customerNo) throws Exception;
	public Customer readOne(String email) throws Exception;
	public Customer findByToken(@Param("_usertoken") String usertoken) throws Exception;  
	
	public Map<String, Object>  registerUser(Map<String, Object> map) throws Exception;
	public Map<String, Object>  updateUser(Map<String, Object> map) throws Exception; 
	
	public Map<String, Object> loginUser(Map<String, String> params ) throws Exception;
	public Map<String, Object> loginUser2(String email,String plainPasswd ) throws Exception;
	
	public void updateToken(String customerNo, String user_token, String dev_token) throws Exception;
	public void insertLoginHistory(Map<String, Object> map) throws Exception;
	public Map<String, Object> checkWithdrawal(@Param("_email")String email ) throws Exception;
	public String make_customer_no() throws Exception ;
	
// social user	
	public Map<String,Object> social_readOne(int reserveNo) throws Exception;
	
	public void social_userRegister(Map<String,Object> params)throws Exception;
	
	public List<Map<String,Object>> social_userInfoList(String zip_code)throws Exception;
	
	public Map<String,Object> social_userInfo(String customerNo)throws Exception;
	
	public int social_userInfoEdit(Map<String,Object> params)throws Exception;
	
	public String getAdmin(String customerNo)throws Exception;
	
	public String getUseCount(String customerNo)throws Exception;
	
	public int social_groupCreate(Map<String,Object> param)throws Exception;
	
	public List<Map<String,Object>> social_getGroupList(Map<String,Object> param)throws Exception;
	
	public Map<String,Object> social_getGroupName(Map<String,Object> params)throws Exception;
	
	public List<Map<String,Object>> userInfoPartList(Map<String,Object> params)throws Exception;
	
	public Map<String,Object> getGroupInfo(String customerNo)throws Exception;
	
	public Map<String,Object> getGroupName(String groupCode)throws Exception;
	
	public int social_userGroupUpdate(Map<String, Object> params)throws Exception;
	
	public int getGroupNameUpdate(Map<String, Object> params)throws Exception;
	
	public Map<String,Object> groupDelete(Map<String, Object> param)throws Exception;
//////////////////////////////////////////////////////////////////////////////////////////
//wurigo user 
	public UserVO userInfo(String customerNo)throws Exception;
	//등록된 이메일로 회원정보 불러오기
	//		public String readOne(String email) throws Exception; 
	//회원 정보 불러오기 유저로그인용
	//	public UserVO read(String customerNo) throws Exception;
	//로그인 기록
	//로그인
	public UserVO userLogin(Map<String, Object> map) throws Exception;
	//로그인시 토큰 기록 업데이트 
	//회원가입
	public void insertUserRecord(Map<String, Object> map) throws Exception;
	//회원정보 수정
	public void userInfoUpdate(Map<String, Object> map)throws Exception;
	//등록된 이미지 파일명 가져오기 
	public String licenseImageRecord(String customerNo)throws Exception;
	//장애인 정보 업데이트 사진제외
	public void updateDisabledInfo(Map<String, Object> map);
}
