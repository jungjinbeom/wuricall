package com.wurigo.socialService.dao;

import java.util.List;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.wurigo.socialService.domain.Customer;
import com.wurigo.socialService.domain.UserVO;

 
@Repository
@Mapper
public interface CustomerDAO {
/*	
	public void create(Customer customer) throws Exception;
	public void update(String customerNo) throws Exception;
	public void delete(String customerNo) throws Exception;*/
	
	public Customer read(@Param("_customerNo") String customerNo) throws Exception;  
	public Customer readOne(@Param("_email") String customerNo) throws Exception;  
	public Customer findByToken(@Param("_usertoken") String usertoken) throws Exception;  
	public void  registerUser(Map<String, Object> map) throws Exception;
	public void  updateUser(Map<String, Object> map) throws Exception;
	
//	public void  joinUser(Customer customer) throws Exception;
	
	public List<Customer> getCustomer() throws Exception; 
	public void updateToken(@Param("_customerNo")String customerNo, 
			@Param("_user_token")String user_token,
			@Param("_dev_token")String dev_token ) throws Exception;
	
	public void insertLoginHistory(Map<String, Object> map) throws Exception;
	public Map<String, Object> checkWithdrawal(@Param("_email")String email ) throws Exception;
	
/// social user
	
	public void social_userRegister(Map<String,Object> params)throws Exception;
	public List<Map<String,Object>> social_userInfoList(String zip_code)throws Exception;
	public Map<String,Object> social_userInfo(String customerNo)throws Exception;
	public int social_userInfoEdit(Map<String,Object> params)throws Exception;
	public Map<String,Object> social_readOne(int reserveNo) throws Exception;
	public String getAdmin(String customerNo)throws Exception;
	public String getUseCount(String customerNo)throws Exception;
	public int social_groupCreate(Map<String,Object> param)throws Exception;
	public List<Map<String,Object>> social_getGroupList(Map<String,Object> param)throws Exception;
	public void social_userOtherRegister(Map<String,Object> params)throws Exception;
	public Map<String,Object> social_getGroupName(Map<String,Object> params)throws Exception;
	public List<Map<String,Object>> userInfoPartList(Map<String,Object> params)throws Exception;
	public Map<String,Object> getGroupInfo(String customerNo)throws Exception;
	public Map<String,Object> getGroupName(String groupCode)throws Exception;
	public int social_userGroupUpdate(Map<String, Object> params)throws Exception;
	public List<Map<String,Object>> social_getCustomerNo(@Param("adminNum") String adminNum,@Param("groupCode") int groupCode)throws Exception;
	public int getGroupNameUpdate(Map<String, Object> params)throws Exception;
	
	public int social_groupUserUpdate(Map<String, Object> param)throws Exception;
	public int groupUserUpdate(String customerNo)throws Exception;
	public int groupDelete(Map<String, Object> param)throws Exception;
//wurigo user 
	public UserVO userInfo(String customerNo)throws Exception;
	//로그인
	public UserVO userLogin(Map<String, Object> map) throws Exception;
	//회원가입
	public void insertUserRecord(Map<String, Object> map) throws Exception;
	//회원정보 수정
	public void userInfoUpdate(Map<String, Object> map)throws Exception;
	//등록된 이미지 파일명 가져오기 
	public String licenseImageRecord(String customerNo)throws Exception;
	//장애인 정보 업데이트 사진제외
	public void updateDisabledInfo(Map<String, Object> map);
}
