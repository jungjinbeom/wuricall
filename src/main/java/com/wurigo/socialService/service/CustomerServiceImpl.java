package com.wurigo.socialService.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wurigo.socialService.commons.Utils;
import com.wurigo.socialService.dao.ConstantDAO;
import com.wurigo.socialService.dao.CustomerDAO;
import com.wurigo.socialService.domain.Customer;
import com.wurigo.socialService.security.WURI_Security;

//import lombok.NonNull;



@Service
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	private CustomerDAO dao;
	@Autowired
	private ConstantDAO constDao;

	@Override
	public List<Customer> getCustomer() throws Exception {

		return dao.getCustomer();
	}
	
	@Override
	public Customer read(String customerNo) throws Exception {

		return dao.read(customerNo);
	}
	@Override
	public Customer readOne(String email) throws Exception {

		return dao.readOne(email);
	}
	public Customer findByToken(String usertoken) throws Exception{
		
		return dao.findByToken(usertoken);
	}
	 
	@Override
	public Map<String, Object> registerUser(Map<String, Object> params) throws Exception {
		
		Map<String, Object>  retMap = new HashMap<String, Object>();
		
		String email = (String)params.get("email");
		Map<String, Object> checkMap = checkWithdrawal(email);  // 탈퇴후 7일이내 가입 불가  
		if(checkMap!= null) {
			retMap.put("code", "668");
			retMap.put("message", "탈퇴한 이메일 계정으로는 탈퇴시점에서 7일 이후에 재가입 할 수 있습니다.");
			retMap.put("accesstoken", "");
			return retMap;
		}
		Customer user = readOne(email);
		if(user!=null) {  
			retMap.put("code", "999");
			retMap.put("message", "동일한 이메일이  존재합니다.");
			retMap.put("accesstoken", "");
			return retMap;
		}
		
		String customerNo = make_customer_no();
		if(customerNo.isEmpty()) {
			retMap.put("code", "888");
			retMap.put("message", "고유 번호를 생성할 수 없습니다.");
			retMap.put("accesstoken", "");
			return retMap;
		}
		String dbPasswd = "";
		String pwdType = (String)params.get("pwdType");
		if(pwdType.contentEquals("plain")) {
			dbPasswd = make_plain2dbpassword((String)params.get("password"));
		}else {
			dbPasswd = make_dbpassword((String)params.get("password"));
		}
		if(dbPasswd.isEmpty()) {
			retMap.put("code", "878");
			retMap.put("message", "비밀번호 암호화를 실패했습니다.");
			retMap.put("accesstoken", "");
			return retMap;
		}
System.out.println("customerNo: " + customerNo + " dbPasswd: " + dbPasswd );

		String username = (String)params.get("username");
		String phone    = (String)params.get("phone");

//		Customer customer = new Customer(customerNo, 0, email,username,phone,dbPasswd);
//	dao.joinUser(customer);		
		Map<String, Object> r_map = new HashMap<String, Object>();
		int customerType = (int)params.get("customerType");
		if(email == null) {
			if(customerType==9) {
				r_map.put("customerNo", customerNo);
				r_map.put("customerType",9);
				r_map.put("approval",0);
				r_map.put("phone", params.get("phone"));
				r_map.put("email", params.get("phone"));//무료이용자는 이메일은 전화번호로 
				r_map.put("username",params.get("username"));
				r_map.put("real_name",params.get("username"));
				r_map.put("password2",dbPasswd);
				r_map.put("address",params.get("addr"));
				r_map.put("point",params.get("point"));
				r_map.put("uniqueness",params.get("uniqueness"));
				r_map.put("zip_code",params.get("zip_code"));
				r_map.put("guardian",params.get("guardian"));
				r_map.put("licenseNo",params.get("licenseNo"));
				r_map.put("adminNum",params.get("adminNum"));
				r_map.put("groupCode",params.get("groupCode"));
				r_map.put("social_type",params.get("social_type"));
				dao.social_userRegister(r_map);
				dao.social_userOtherRegister(r_map); 
			}else{
				r_map.put("customerNo", customerNo);
				r_map.put("customerType",0);
				r_map.put("approval",0);
				r_map.put("phone", params.get("phone"));
				r_map.put("email", params.get("phone"));//무료이용자는 이메일은 전화번호로 
				r_map.put("username",params.get("username"));
				r_map.put("real_name",params.get("username"));
				r_map.put("password2",dbPasswd);
				r_map.put("address",params.get("addr"));
				r_map.put("point",0);
				r_map.put("uniqueness",params.get("uniqueness"));
				r_map.put("zip_code",params.get("zip_code"));
				r_map.put("guardian",params.get("guardian"));
				r_map.put("licenseNo",params.get("licenseNo"));
				r_map.put("adminNum",params.get("adminNum"));
				r_map.put("groupCode",params.get("groupCode"));
				r_map.put("social_type",params.get("social_type"));
				dao.social_userRegister(r_map);
				dao.social_userOtherRegister(r_map); 
			}
		}else {
			r_map.put("customerNo", customerNo);
			r_map.put("customerType", 0);
			r_map.put("phone", phone);
			r_map.put("email", email);
			r_map.put("username", username);
			r_map.put("real_name", username);
			r_map.put("password2", dbPasswd);
			dao.registerUser(r_map);
		}
		Integer usernum = ((java.math.BigInteger)r_map.get("usernum")).intValue();
		if( usernum <= 0 ) {
			retMap.put("code", "666");
			retMap.put("message", "회원정보 등록 실패");
			retMap.put("accesstoken", "");
			return retMap;
		}
System.out.println("success dao.registerUser: "+r_map.get("usernum") )	;

		String user_token = WURI_Security.make_user_token();
  		String accesstoken = WURI_Security.make_access_token(user_token);
  		String newNo = customerNo; //customer.getCustomerNo();
System.out.println("newNo: " + newNo + " user_token: " + user_token + " accesstoken:" + accesstoken );
 		
  		String dev_token="";  // login할때 update된다.
  		updateToken(newNo, user_token, dev_token );
  		
  		retMap.put("code", "777");
		retMap.put("message", newNo);
  		retMap.put("accesstoken",  accesstoken);
  		return retMap;        
	}
	
	@Override
	public Map<String, Object>  updateUser(Map<String, Object> params) throws Exception {
		
		Map<String, Object>  retMap = new HashMap<String, Object>();
		
		String customerNo = (String)params.get("customerNo");
		Customer user = read( customerNo);
		if(user==null) {  
			retMap.put("code", "666");
			retMap.put("message", "존재하지 않는 회원입니다.");
			retMap.put("accesstoken", "");
			return retMap;
		}
		
		String dbPasswd = "";
		String pwdType = (String)params.get("pwdType");
		if(pwdType.contentEquals("plain")) {
			dbPasswd = make_plain2dbpassword((String)params.get("password"));
		}
		else {
			dbPasswd = make_dbpassword((String)params.get("password"));
		}
		if(dbPasswd.isEmpty()) {
			retMap.put("code", "878");
			retMap.put("message", "비밀번호 암호화를 실패했습니다.");
			retMap.put("accesstoken", "");
			return retMap;
		}
		String username = (String)params.get("username");
		String phone    = (String)params.get("phone");

System.out.println("customerNo: " + customerNo +"/"+username+"/"+ phone +"/dbPasswd: " + dbPasswd );

		Map<String, Object> r_map = new HashMap<String, Object>();
		r_map.put("customerNo", customerNo); 
		r_map.put("phone", phone);
		r_map.put("username", username);
		r_map.put("password2", dbPasswd);
		dao.updateUser(r_map);

		String accesstoken = WURI_Security.make_access_token(user.getUser_token()); 
System.out.println("updateUser   accesstoken:" + accesstoken );
 
		retMap.put("code", "778");
		retMap.put("message", "회원 정보가 수정되었습니다.");
		retMap.put("accesstoken", accesstoken);
		return retMap;
	}
	
	@Override
	public Map<String, Object> checkWithdrawal(@Param("_email")String email ) throws Exception {
		
		Map<String, Object> map = dao.checkWithdrawal(email);
		return map;
	}
///////////////////////////////////////////////////////////////////	
	@Override
	public Map<String, Object> loginUser( Map<String, String> params) throws Exception{
		
//		String encPwd = new String(enc_password.getBytes(StandardCharsets.UTF_8));
//		encPwd = encPwd.replaceAll(" ", "" );
		String email = params.get("email");
		String enc_password = params.get("password");
		String dev_token = params.get("dev_token");
		String IP 		 = params.get("IP");
		String devType 	 = params.get("devType");
		
		Map<String,Object> history = new HashMap<String, Object>(); 
		history.put("loginID",  email);
  		history.put("IP",  IP);
  		history.put("userType",  0);
  		history.put("devType", devType);  // 0:android 1:ios
		
		Map<String, Object>  retMap = new HashMap<String, Object>();
		String code="";  int success=0;
		Customer user = readOne( email);
		if(user==null) {
			
			code = "999"; success=0;
	  		history.put("code",code );  
	  		history.put("success",  success);
	  		insertLoginHistory(history);
	  		
	  		retMap.put("code", code);
	  		retMap.put("message", "존재하지 않는 회원입니다."); 
	  		return retMap;
		}
		
		String dbPasswd = user.getPassword2();
		
		try {
			generate_keypair(); 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
  		boolean result = WURI_Security.verify_password(enc_password, dbPasswd );
	  	if( result==false ) {
	  		code = "888"; success=0;
	  		retMap.put("code", code );
	  		retMap.put("message", "비밀번호가 일치하지 않습니다."); 
	  	}
	  	else {
	  		code = "777"; success=1;
	  		retMap.put("code", code);
	  		retMap.put("message",  user.getUsername()+"님 환영합니다."); 
	  		retMap.put("email",  user.getEmail());      
	  		retMap.put("username",  user.getUsername()); 		 
	  		retMap.put("customerNo",  user.getCustomerNo()); 	   		 		
	  		retMap.put("phone",  user.getPhone()); 	 
	  		retMap.put("realname",  user.getReal_name()); 
/*	  		retMap.put("ishandicapped",  user.getIshandicapped()); 
	  		retMap.put("guardian",  user.getGuardian()); 
	  		retMap.put("licenseNo",  user.getLicenseNo()); ;	
	  		retMap.put("licensefile",  user.getLicenseImage()); 		  		
	  		retMap.put("ishandicapped",  user.getIshandicapped()); 
	  		retMap.put("guardian",  user.getGuardian()); 
	  		retMap.put("licenseNo",  user.getLicenseNo()); ;	
	  		retMap.put("licensefile",  user.getLicenseImage()); 	*/
	  		
	  		String user_token = WURI_Security.make_user_token();
	  		String accesstoken = WURI_Security.make_access_token(user_token);
	  		retMap.put("accesstoken",  accesstoken);
	  		
	  		String customerNo = user.getCustomerNo();
	  		updateToken(customerNo, user_token, dev_token);
	  	}
	  	history.put("code",code );  
  		history.put("success",  success);
  		insertLoginHistory(history);
  		 
  		
		return retMap;
	}
	
	@Override
	public Map<String, Object> loginUser2(String email,String plainPasswd ) throws Exception{
		
		String ipaddress = Utils.getIPAddress(true);
		Map<String,Object> history = new HashMap<String, Object>(); 
		history.put("loginID",  email);
  		history.put("IP",  ipaddress);
  		history.put("userType",  0);
  		history.put("devType",  2);  // devType=2 PC
  		
		int success=0;
  		String code = "";
		Map<String, Object>  retMap = new HashMap<String, Object>();
		
		Customer user = readOne( email);
		if(user==null) {
					
	  		history.put("code","999" );  
	  		history.put("success",  0);	  		
	  		insertLoginHistory(history);
	  		
	  		code = "999"; success=0;
	  		retMap.put("code", code);
	  		retMap.put("message", "존재하지 않는 회원입니다."); 
	  		return retMap;
		}
		
		try {
			generate_keypair(); 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		String encryptedPWD = WURI_Security.create_encrypted_password(plainPasswd);  //from homepage
//System.out.print("plainPasswd" + plainPasswd + "\n");	
//System.out.print("encryptedPWD" + encryptedPWD.length() + "/ " + encryptedPWD +   "\n");
		String dbPasswd = user.getPassword2();
//		String dbPasswd = WURI_Security.make_encrypted_password(encryptedPWD);
  		boolean result = WURI_Security.verify_password(encryptedPWD, dbPasswd);
  		
  		if( result==false ) {
  			code = "888"; success=0;
	  		retMap.put("code", code);
	  		retMap.put("message", "비밀번호가 일치하지 않습니다."); 
	  	}
	  	else {
	  		code = "777";
	  		retMap.put("code", code); success=1;
	  		retMap.put("message",  user.getUsername()+"님 환영합니다."); 
	  		retMap.put("email",  user.getEmail());      
	  		retMap.put("username",  user.getUsername()); 		 
	  		retMap.put("customerNo",  user.getCustomerNo()); 	   		 		
	  		retMap.put("phone",  user.getPhone()); 	 
	  		retMap.put("realname",  user.getReal_name()); 
/*	  		retMap.put("ishandicapped",  user.getIshandicapped()); 
	  		retMap.put("guardian",  user.getGuardian()); 
	  		retMap.put("licenseNo",  user.getLicenseNo()); ;	
	  		retMap.put("licensefile",  user.getLicenseImage()); 	*/
	  		
	  		
	  		String user_token = WURI_Security.make_user_token();
	  		String accesstoken = WURI_Security.make_access_token(user_token);
	  		retMap.put("accesstoken",  accesstoken);
System.out.print("user_token: " + user_token + "\n");	
			
			String dev_token = "";
	  		String customerNo = user.getCustomerNo();
	  		updateToken(customerNo, user_token, dev_token );
	  	}
  		 
  		history.put("code",code );  
  		history.put("success",  success);
  		insertLoginHistory(history);
  		 
		return retMap;
	}
	
	@Override
	public void updateToken(String customerNo, String user_token, String dev_token) throws Exception {
		dao.updateToken(customerNo, user_token, dev_token); 
	}
	
	@Override
	public void insertLoginHistory(Map<String, Object> map) throws Exception {
		dao.insertLoginHistory(map);
	}
	
	/////////////////////////////////
	public void generate_keypair() throws Exception {
//		String RSA_PUBLIC_KEY = constDao.getConstantByName("Client_publicKey");
//System.out.println("RSA_PUBLIC_KEY: " + RSA_PUBLIC_KEY)	;				 
//		String RSA_PRIVATE_KEY = constDao.getConstantByName("Client_privateKey");
//System.out.println("RSA_PRIVATE_KEY: " + RSA_PRIVATE_KEY)	;	

		WURI_Security.generate_keypair(); //RSA_PUBLIC_KEY,RSA_PRIVATE_KEY); 
	}
	public String make_dbpassword(String encryptedPWD) {
		
		try {
			generate_keypair();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
System.out.print("encryptedPWD" + encryptedPWD.length() + "/ " + encryptedPWD +   "\n");

		String dbPasswd = WURI_Security.make_encrypted_password(encryptedPWD);
		
		return dbPasswd;
	}
	public String make_plain2dbpassword(String plainPasswd) {
		try {
			generate_keypair();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
System.out.print("plainPasswd" + plainPasswd + "\n");		
		String encryptedPWD = WURI_Security.create_encrypted_password(plainPasswd);  //from homepage
//System.out.print("plainPasswd" + plainPasswd + "\n");	
//System.out.print("encryptedPWD" + encryptedPWD.length() + "/ " + encryptedPWD +   "\n");

		String dbPasswd = WURI_Security.make_encrypted_password(encryptedPWD);
		return dbPasswd;
	}
	
	public String make_customer_no() throws Exception   {
		 
		String custNo = "";
		while(true)  {
	 					
			String header="";	
			for(int j=0; j<2; j++)  //알파벳 2개 대문자 추출
			{ 
				Random rnd = new Random();
				header += String.valueOf((char) ((int) (rnd.nextInt(26)) + 65));
			} 					 
			custNo	= String.format( "%s%06d", header,  Utils.randomRange(0,999999) );   // total 8자리
			
			Customer user;
			user = dao.read(custNo);
			if(user==null) break;
		} 
		return custNo;
	}
////////////////////////////social user ////////////////////////////////
	
	public void social_userRegister(Map<String, Object> params) throws Exception {
		dao.social_userRegister(params);
	}

	public List<Map<String, Object>> social_uuserInfoList(String zip_code) throws Exception {
		return dao.social_userInfoList(zip_code);
	}

	public Map<String, Object> social_userInfo(String customerNo) throws Exception {
		// TODO Auto-generated method stub
		return dao.social_userInfo(customerNo);
	}
	
	public void social_userInfoEdit(Map<String, Object> params) throws Exception {
		
		dao.social_userInfoEdit(params);
	}
	public String getAdmin(String customerNo) throws Exception {
		return dao.getAdmin(customerNo);
	}

	@Override
	public List<Map<String, Object>> social_userInfoList(String zip_code) throws Exception {
		// TODO Auto-generated method stub
		return dao.social_userInfoList(zip_code);
	}

	@Override
	public Map<String, Object> social_readOne(int reserveNo) throws Exception {
		// TODO Auto-generated method stub
		return dao.social_readOne(reserveNo);
	}

	@Override
	public String getUseCount(String customerNo) throws Exception {
		// TODO Auto-generated method stub
		return dao.getUseCount(customerNo);
	}

	@Override
	public void social_groupCreate(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		 dao.social_groupCreate(param);
	}

	@Override
	public List<Map<String,Object>> social_getGroupList(Map<String,Object> param) throws Exception {
		return dao.social_getGroupList(param);
	}

	@Override
	public Map<String, Object> social_getGroupName(Map<String, Object> params) throws Exception {
		// TODO Auto-generated method stub
		return dao.social_getGroupName(params);
	}

	@Override
	public List<Map<String, Object>> userInfoPartList(Map<String, Object> params) throws Exception {
		// TODO Auto-generated method stub
		return dao.userInfoPartList(params);
	}

	@Override
	public Map<String, Object> getGroupInfo(String customerNo) throws Exception {
		return dao.getGroupInfo(customerNo);
	}

	@Override
	public Map<String, Object> getGroupName(String groupCode) throws Exception {
		return dao.getGroupName(groupCode);
	}

	@Override
	public void social_userGroupUpdate(Map<String, Object> params) throws Exception {
		dao.social_userGroupUpdate(params);
	}

	
	@Override
	public int getGroupNameUpdate(Map<String, Object> params) throws Exception {
		return dao.getGroupNameUpdate(params);
		
	}

	@Override
	public Map<String,Object> groupDelete(Map<String, Object> param) throws Exception {
		
		Map<String,Object> map = new HashMap<String, Object>();
		String adminNum = (String)param.get("adminNum");
		int groupCode = (int)param.get("groupCode");
		List<Map<String,Object>> list = dao.social_getCustomerNo(adminNum,groupCode);
		System.out.println("list="+list);
		
		int result = dao.social_groupUserUpdate(param);
		System.out.println(result);
		if(result!=0) {
			for(int i =0; i<list.size();i++){
				String customerNo = (String)list.get(i).get("customerNo");
				
				System.out.println("유저 데이터 변경 2"+customerNo);
				int SEQ2Result = dao.groupUserUpdate(customerNo);
			}
		}
		int SEQ3Result =dao.groupDelete(param);
		if(SEQ3Result==1) {
			map.put("message","그룹이 정상적으로 삭제되었습니다.");
		}else {
			map.put("message","그룹삭제 과정 중 에러가 발생하였습니다\n 다시 시도해주세요.");
		}
		return map;
	}
}


