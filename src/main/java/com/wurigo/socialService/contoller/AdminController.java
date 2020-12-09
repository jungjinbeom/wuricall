package com.wurigo.socialService.contoller;

import java.util.HashMap;

import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wurigo.socialService.commons.CustomMailSender;
import com.wurigo.socialService.commons.Utils;
import com.wurigo.socialService.securty.WURI_Security;
import com.wurigo.socialService.service.AdminService;
import com.wurigo.socialService.service.EmailService;


@RestController
@CrossOrigin(origins="*", maxAge=3600)
@RequestMapping("/api/admin")
public class AdminController {
	@Autowired
	AdminService adminService;
	@Autowired
	EmailService emailService;
	@PostMapping("/register")
	public Map<String,Object> register(@RequestBody Map<String,Object> params) throws Exception{
		Map<String,Object> map = new HashMap<String, Object>();
		String dbPasswd = "";
		String pwdType ="plain";
		String adminId = (String)params.get("adminId");
		//먼저 중복되는 아이디가 있는지 읽어오고 있으면 뒤로가기 
		String adminIdChk= adminService.readOne(adminId);
		
		if(adminIdChk==null) {
			if(pwdType.equals("plain")) {
				dbPasswd = make_plain2dbpassword((String)params.get("password"));
			}
			String str =(String)params.get("groupName");
			String [] array = str.split("-");
			params.put("sido_2", array[0]);
			params.put("sigungu", array[1]);
			map = adminService.groupCodeRecord(params);
			
			params.put("groupCode",map.get("code_l")+"_"+map.get("code_m"));
			params.put("adminNum", make_customer_no());
			params.put("password", dbPasswd);
			adminService.adminRegister(params);	
			map.put("message","회원가입이 완료되었습니다!!");
		}else {
			map.put("message","이미 가입되어있는 아이디 입니다.\n 다시시도해주세요");
			
		}
		return map;
	}
	
	
	
	@PostMapping("/login")
	public Map<String, Object> login(@RequestBody Map<String, Object> params,HttpServletRequest req) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> user =adminService.adminLogin(params);
		String plainPasswd = (String)params.get("password");
		if(user==null) {
			map.put("message","존재하지 않는 회원입니다.");
		}else{
			//로그인 창에 입력한 비밀번호랑 데이터베이스의 비밀번호를 가져와서 비교한다 
			String encryptedPWD = WURI_Security.create_encrypted_password(plainPasswd);//입력한 비밀번호
			String dbPasswd =(String)user.get("password");//데이터베이스에서 가져온 비밀번호 
	  		boolean result = WURI_Security.verify_password(encryptedPWD, dbPasswd);
	  		if(result ==false) {
	  			map.put("message","비밀번호가 틀리셨습니다.\n다시시도해주세요.");
	  		}else {
	  			String adminId = (String)params.get("adminId");
	  			String adminNum = (String)user.get("adminNum");
	  			adminService.lastLoginDateInsert(adminId);
	  			map.put("adminNum",adminNum);
	  			map.put("loginSuccess",true);
	  			map.put("message",user.get("adminName")+"님 환영합니다.");
	  		}
		}
		return map;
	}
	@PostMapping("/adminInfo")
	public Map<String,Object> adminInfo(@RequestBody String adminNum)throws Exception {
		int idx = adminNum.indexOf("=");
		String num = adminNum.substring(0,idx);
		System.out.println(num);
		Map<String,Object> map=adminService.adminInfo(num);
		String str =(String)map.get("groupName");
		String [] array = str.split("-");
		map.put("sido", array[0]);
		map.put("sigungu", array[1]);
		return map;
	}
	@PostMapping("/adminInfoEdit")
	public Map<String,Object> adminInfoEdit(@RequestBody Map<String,Object> params)throws Exception {
		Map<String,Object> map = new HashMap<String, Object>();
		String str =(String)params.get("groupName");
		String [] array = str.split("-");
		params.put("sido_2", array[0]);
		params.put("sigungu", array[1]);
		map = adminService.groupCodeRecord(params);
		params.put("groupCode",map.get("code_l")+"_"+map.get("code_m"));
		adminService.adminInfoEdit(params);
		map.put("message","정보가 변경되었습니다.");
		
		return map;
	}
	@PostMapping("/adminFindPwd")
	public Map<String,Object> adminFindPwd(@RequestBody Map<String,Object> params)throws Exception {
		Map<String,Object> map = new HashMap<String, Object>();
		String email = adminService.adminFindEmail(params);
		
		
				
		if(email==null) {
			System.out.println("테스트"+null);
			map.put("message","존재하지않는 아이디입니다");
		}else {
			String subject = "테스트";
			String password = Utils.getRamdomPassword();
			String body = "우리고 회원 임시비밀번호를 발급하였습니다.\n"+"임시비밀번호는"+password+"입니다"+" 회원 정보에서 수정하세요.";
			String toAddress=email;
			emailService.sendEmail(toAddress, subject, body);
			String DbPassword = make_plain2dbpassword(password);
			System.out.println("DbPassword"+DbPassword);
			adminService.adminPasswordUpdate(DbPassword,email);
		}
		return map;
	}
	
	
	//adminNum 생성기 
			public String make_customer_no() throws Exception   {
				String adminNum = "";
				while(true)  {
					String header="";	
					for(int j=0; j<2; j++)  //알파벳 2개 대문자 추출
					{ 
						Random rnd = new Random();
						header += String.valueOf((char) ((int) (rnd.nextInt(26)) + 65));
					} 					 
					adminNum	= String.format( "%s%06d", header, Utils.randomRange(0,999999) );   // total 8자리
					String user;
					user = adminService.read(adminNum);
					if(user==null) break;
				} 
				return adminNum;
			}
	//암호화 과정
		public String make_plain2dbpassword(String plainPasswd) {
			String encryptedPWD = WURI_Security.create_encrypted_password(plainPasswd);  //from homepage
			String dbPasswd = WURI_Security.make_encrypted_password(encryptedPWD);
			return dbPasswd;
		}
}
