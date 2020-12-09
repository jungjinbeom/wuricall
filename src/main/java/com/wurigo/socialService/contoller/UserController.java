package com.wurigo.socialService.contoller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wurigo.socialService.commons.Utils;
import com.wurigo.socialService.securty.WURI_Security;
import com.wurigo.socialService.service.UserService;

@RestController
@CrossOrigin(origins="*", maxAge=3600)
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@PostMapping("/register")
	public Map<String,Object> register(@RequestBody Map<String,Object> params) throws Exception{
		 Map<String,Object> map = new HashMap<String, Object>();
		 System.out.println("params="+params);
		 
		 String dbPasswd = "";
		 String pwdType ="plain";
		 if(pwdType.equals("plain")) {
				dbPasswd = make_plain2dbpassword((String)params.get("password"));
				
			 params.put("customerType",9);
			 params.put("customerNo", make_customer_no());
			 params.put("email",params.get("phone"));
			 params.put("password", dbPasswd);
			 String adminNum = (String)params.get("adminNum");
			 String adminId = userService.read(adminNum);//아이디값가져오기 
			 params.put("zip_code",adminId);
			 userService.userRegister(params);
			 
			 map.put("message","회원등록이 완료되었습니다.");
		}
		 
		return map;
	}
	@PostMapping("/userInfoList")
	public List<Map<String,Object>> userInfoList(@RequestBody String adminNum) throws Exception {
		
		int idx = adminNum.indexOf("=");
		String num = adminNum.substring(0,idx);
		String zip_code = userService.read(num);
		System.out.println(zip_code);
		List<Map<String,Object>> list = userService.userInfoList(zip_code);
		System.out.println(list);
	
		return list;
	}
	@PostMapping("/userInfo/{customerNo}")
	public Map<String,Object> userInfo(@PathVariable String customerNo) throws Exception{
		Map<String,Object> map  = userService.userInfo(customerNo);
		String str =(String)map.get("address");
		String [] array = str.split("-");
		map.put("uniqueness", map.get("address_detail"));
		map.put("address", array[0]);
		map.put("addressDetail", array[1]);
		
		return map;
	}
	@PostMapping("/userInfoEdit")
	public Map<String,Object> userInfoEdit(@RequestBody Map<String,Object> params) throws Exception{
		System.out.println(params);
		Map<String,Object> map = new HashMap<String, Object>();
		userService.userInfoEdit(params);
		return map;
	}
	
	//암호화 과정
	public String make_plain2dbpassword(String plainPasswd) {
		String encryptedPWD = WURI_Security.create_encrypted_password(plainPasswd);  //from homepage
		String dbPasswd = WURI_Security.make_encrypted_password(encryptedPWD);
		return dbPasswd;
	}
	
	//customerNo 생성기 
	public String make_customer_no() throws Exception{
		String customerNo = "";
		while(true)  {
			String header="";	
			for(int j=0; j<2; j++)  //알파벳 2개 대문자 추출
			{ 
				Random rnd = new Random();
				header += String.valueOf((char) ((int) (rnd.nextInt(26)) + 65));
			} 					 
			customerNo	= String.format( "%s%06d", header,  Utils.randomRange(0,999999) );   // total 8자리
			
			String user;
			user = userService.read(customerNo);
			if(user==null) break;
		} 
		return customerNo;
	}
	
}
