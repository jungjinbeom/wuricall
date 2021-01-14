package com.wurigo.socialService.contoller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.wurigo.socialService.commons.FTPUtil;
import com.wurigo.socialService.commons.Utils;
import com.wurigo.socialService.domain.Customer;
import com.wurigo.socialService.domain.UserVO;
import com.wurigo.socialService.security.WURI_Security;
import com.wurigo.socialService.service.CustomerService;

@RestController
@CrossOrigin(origins="*", maxAge=3600)
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
	CustomerService customerService;
	@PostMapping("/register")
	public Map<String,Object> register(@RequestBody Map<String,Object> params) throws Exception{
		 Map<String,Object> map = new HashMap<String, Object>();
		 String adminNum = (String)params.get("adminNum");
		 String adminId = customerService.getAdmin(adminNum);//아이디값가져오기
		 params.put("pwdType","plain");
		 params.put("zip_code",adminId);
		 params.put("email",null);
		 customerService.registerUser(params);
		 map.put("message","회원등록이 완료되었습니다.");
		 return map;
	}
	@PostMapping("/userInfoList")
	public List<Map<String,Object>> userInfoList(@RequestBody String adminNum) throws Exception {
		int idx = adminNum.indexOf("=");
		String num = adminNum.substring(0,idx);
		String zip_code = customerService.getAdmin(num);
		List<Map<String,Object>> list = customerService.social_userInfoList(zip_code);
		return list;
	}
	@PostMapping("/userInfoPartList")
	public List<Map<String,Object>> userInfoPartList(@RequestBody Map<String,Object> params) throws Exception {
		List<Map<String,Object>> userInfo = new ArrayList<Map<String,Object>>();
		String groupCode =(String)params.get("groupCode");
		if(groupCode.equals("all")) {
			String adminNum=(String)params.get("adminNum");
			String zip_code = customerService.getAdmin(adminNum);
			userInfo = customerService.social_userInfoList(zip_code);
		}else {
			List<Map<String,Object>> list = customerService.userInfoPartList(params);
			Map<String,Object> map = new HashMap<String, Object>();
			for(int i =0;i<list.size();i++) {
				String customerNo = (String)list.get(i).get("customerNo");
				map = customerService.social_userInfo(customerNo);
				userInfo.add(map);
			}
		}	
		return userInfo;
	}
	@PostMapping("/groupCreate")
	public Map<String,Object> userGroupCreate(@RequestBody Map<String,Object> params) throws Exception{
		Map<String,Object> map = new HashMap<String, Object>();
		Map<String,Object> data = customerService.social_getGroupName(params);
		if(data != null) {
			map.put("message","이미 존재하는 그룹명입니다.\n다시 시도해주세요");
		}else {
			int result = customerService.social_groupCreate(params);
			if(result == 1) {
				map.put("message","그룹이 정상적으로 등록 되었습니다.");
				map.put("success","success");
			}else {
				map.put("message","그룹이 등록 중 에러가 발생했습니다.\n다시 시도해주세요.");
			}
		}
		return map;
	}
	@PostMapping("/groupList")
	public List<Map<String,Object>> userGroupList(@RequestBody Map<String,Object> param) throws Exception{
		List<Map<String,Object>> list = customerService.social_getGroupList(param);
		return list;
	}
	@PostMapping("/userInfo/{customerNo}")
	public Map<String,Object> userInfo(@PathVariable String customerNo) throws Exception{
		Map<String,Object> map  = customerService.social_userInfo(customerNo);
		
		String str =(String)map.get("address");
		String [] array = str.split("-");
		String useCount = customerService.getUseCount(customerNo);
		map.put("useCount",useCount);
		map.put("uniqueness", map.get("address_detail"));
		map.put("zipCode", array[0]);
		map.put("address", array[1]);
		map.put("addressDetail", array[2]);
		
		Map<String,Object> data = customerService.getGroupInfo(customerNo);
		map.put("social_type",data.get("social_type"));
		String groupCode= (String)data.get("groupCode");
		data = customerService.getGroupName(groupCode);
		map.put("groupName", data.get("groupName"));
		map.put("groupCode", groupCode);
		
		return map;
	}
	@PostMapping("/userInfoEdit")
	public Map<String,Object> userInfoEdit(@RequestBody Map<String,Object> params) throws Exception{
		Map<String,Object> map = new HashMap<String, Object>();
		int result = 0; 
		result = customerService.social_userGroupUpdate(params);
		if(result ==1){
			result = customerService.social_userInfoEdit(params);
			if(result==1) {
				map.put("message","회원정보가 정상적으로 변경되었습니다.");
				map.put("success", "success");
			}else { 
				map.put("message", "회원정보 변경 중 에러가 발생했습니다.\n다시 시도해주세요.");
				
			}
		}
		return map;
	}
	@PostMapping("/userGroupDetail/{groupCode}")
	public List<Map<String,Object>> userGroupDetail(@PathVariable int groupCode , @RequestBody Map<String,Object> params) throws Exception{
		Map<String,Object> map = new HashMap<String, Object>();
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> list = customerService.userInfoPartList(params);
		for(int i =0;i<list.size();i++) {
			String customerNo = (String)list.get(i).get("customerNo");
			map = customerService.social_userInfo(customerNo);
			if(map!=null) {
				data.add(map);
			}
		}
		return data;
	}
	@PostMapping("/userGroupName/{groupCode}")
	public Map<String,Object> userGroupName(@PathVariable String groupCode)throws Exception{
		Map<String,Object> map = new HashMap<String, Object>();
		map = customerService.getGroupName(groupCode);
		return map;
	}
	@PutMapping("/groupNameUpdate/{groupCode}")
	public Map<String,Object> groupNameUpdate(@PathVariable String groupCode,@RequestBody Map<String,Object> params)throws Exception{
		Map<String,Object> map = new HashMap<String, Object>();
		int result = customerService.getGroupNameUpdate(params);
		System.out.println(result);
		if(result==1) {
			map.put("message", "정상적으로 변경되었습니다.");
			map.put("success", "success");
		}else {
			map.put("message", "변경작업이 실패하였습니다.\n다시시도해주세요.");
		}
		return map;
	}
	@DeleteMapping("/groupDelete/{groupCode}")
	public Map<String,Object> groupDelete(@PathVariable String groupCode,@RequestBody Map<String,Object> param)throws Exception{
		Map<String,Object> map = customerService.groupDelete(param);
		return map;
	}
	
	////우리고 ///
	//회원가입 기능
			@PostMapping("/wurigo/register")
			public Map<String, Object> register(HttpServletRequest req, @RequestBody Map<String,Object> map) throws Exception {
				String email = (String) map.get("email");
				Customer result = customerService.readOne(email);
				if(result == null) {
					String user_token = WURI_Security.make_user_token();
					map.put("customerNo",make_customer_no());
					map.put("user_token", user_token);
					String dbPasswd = "";
					String pwdType ="plain";
					
					if(pwdType.equals("plain")) {
						dbPasswd = make_plain2dbpassword((String)map.get("password"));
					}
					map.put("password2", dbPasswd);
					customerService.insertUserRecord(map);
					map.put("message","회원가입이 완료되었습니다.");
				}else {
					map.put("message","이미 동일한 이메일 계정이 존재합니다.");
				}
				 return map;
			}
			//로그인 기능
			@PostMapping("/wurigo/login")
			public Map<String, Object> login(@RequestBody Map<String,Object> params) throws Exception {
				
				String ipAddress = Utils.getIPAddress(true);
				String email = (String)params.get("email");
				String plainPasswd = (String)params.get("password");
				params.put("email",email);
				params.put("ip",ipAddress);
				params.put("userType",0);
				params.put("devType",2);
				
				String code= "";
				int success = 0;
				
				Map<String, Object> data = new HashMap<String, Object>();
				UserVO user = customerService.userLogin(params);
				if(user==null) {
					code="999";//존재하지 않는 회원입니다.
					params.put("code",code);
					params.put("success",0);
					customerService.insertLoginHistory(params);
					data.put("code",code);
					data.put("message", "존재하지 않는 회원입니다.");
				}else{
				//로그인 창에 입력한 비밀번호랑 데이터베이스의 비밀번호를 가져와서 비교한다 
					String encryptedPWD = WURI_Security.create_encrypted_password(plainPasswd);
					String dbPasswd =user.getPassword2();
			  		boolean result = WURI_Security.verify_password(encryptedPWD, dbPasswd);
			  		
					if(result==false) {
						code="888";
						data.put("code",code);
						data.put("message","비밀번호가 틀리셨습니다.\n 다시시도해주세요.");
					}else {
						String user_token = WURI_Security.make_user_token();
				  		String accesstoken = WURI_Security.make_access_token(user_token);
				  		String customerNo = user.getCustomerNo();
				  		params.put("accesstoken",  accesstoken);
						String dev_token = "";
						customerService.updateToken(customerNo, user_token, dev_token);
				  		code="777";
						data.put("code",code);
						data.put("loginSuccess",true);
						data.put("message",user.getUsername()+"님 환영합니다.");
						data.put("userToken",user_token);
						data.put("customerNo",customerNo);
					}
				}
				params.put("code",code);  
				params.put("success",success);
				customerService.insertLoginHistory(params);
				return data;
			}
			
			//로그아웃 기능 
			@PostMapping("/wurigo/logout")
			public Map<String, Object> logout(HttpServletRequest req) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("message","로그아웃");
				map.put("success",true);
				return map;
			}
			
			//회원정보
			@PostMapping("/wurigo/userInfo")
			public UserVO userInfo(@RequestBody Map<String,Object> map) throws Exception{
				String customerNo = (String)map.get("customerNo");
				UserVO vo = customerService.userInfo(customerNo);
				String imageResult = vo.getLicenseImage();
				if(imageResult!=null) {
					String licenseImage="https://dev.wuricall.com/modugoClient/images/"+vo.getLicenseImage();
					vo.setLicenseImage(licenseImage);
				}
				return vo;
			}
			
			//회원정보 수정 (비밀번호,유저네임,핸드폰)
			@PutMapping("/wurigo/updateUserInfo")
			public Map<String, Object> userInfoUpdate(@RequestBody Map<String,Object> map) throws Exception {
				System.out.println(map);
				 Map<String, Object> data = new HashMap<String, Object>();
				 	String dbPasswd = "";
					String pwdType ="plain";
					if(pwdType.equals("plain")) {
						dbPasswd = make_plain2dbpassword((String)map.get("password"));
					}
					map.put("password2", dbPasswd);
					customerService.userInfoUpdate(map);
				 data.put("success",true);
				 data.put("message", "회원정보수정이 완료되었습니다.");
				return data;
			}
			
			//페이지 핸들러 
			@PostMapping("/auth")
			public Map<String, Object> auth(HttpServletRequest req,@RequestBody Map<String,Object> map) {
				Map<String, Object> data = new HashMap<String, Object>();
				String userToken=(String)map.get("userToken");
				String customerNo=(String)map.get("customerNo");
				if(userToken==null && customerNo==null) {
					data.put("success",true);
					data.put("isAuth",false);
				}else {
					data.put("isAuth",true);
				}
				return data;
			}
			
			//장애인 회원정보 등록 및 업데이트 
			@PostMapping("user/insertDisabledInfo")
			public Map<String, Object> uploadSingle(@RequestParam("licenseImage") MultipartFile file,
													@RequestParam("customerNo") String customerNo,
													@RequestParam("licenseNo") String licenseNo,
													@RequestParam("guardian") String guardian,
													HttpServletRequest req) throws Exception {
				
				String dir="/var/www/html/deriberi/saeammobile/modugoClient/images/";
				Map<String, Object> map = new HashMap<String, Object>();
				Map<String, Object> data = new HashMap<String, Object>();
				FTPUtil ftpUtil = new FTPUtil();
				
				map.put("licenseNo",licenseNo);
				map.put("guardian",guardian);
				map.put("customerNo",customerNo);
				
				String imageName = customerService.licenseImageRecord(customerNo);//이메일 + 사진명
				UserVO vo = customerService.userInfo(customerNo);//이메일 정보
				String email = vo.getEmail();
				String licenseName = email+"_L"+file.getOriginalFilename();//이메일 + 사진명
				if(imageName != null) {//이미지파일명이 있을경우 
					if(licenseName == imageName) {//이미지 파일명과 등록한 파일명이 같을 경우 정보만 업데이트
						customerService.updateDisabledInfo(map);//정보만 업데이트 
						data.put("message","회원정보가 변경되었습니다.");
					}else{//이미지 파일명과 등록하는 파일명이 틀릴 경우 이미지이름 업데이트 및 이미지 삭제 후 업로드
						//삭제후 다시 등록
						ftpUtil.connect();
						ftpUtil.fileDelete(dir,imageName);
						ftpUtil.upload(dir,email,convert(file));
						ftpUtil.disconnect();
						map.put("licenseImage",licenseName);
						customerService.updateDisabledInfo(map);//이미지 정보 같이 업데이트 
						data.put("message", "이미지파일 및 정보가 등록 되었습니다.");
					}
				}else {//이미지파일명이 없을 경우 새로 등록 
					ftpUtil.connect();
					ftpUtil.upload(dir,email,convert(file));
					ftpUtil.disconnect();
					map.put("licenseImage",licenseName);
					customerService.updateDisabledInfo(map);
					data.put("message","회원정보가 등록되었습니다.");
				}
			    return data;
			}
			
			
	//암호화 과정
	public String make_plain2dbpassword(String plainPasswd) {
		String encryptedPWD = WURI_Security.create_encrypted_password(plainPasswd);  //from homepage
		String dbPasswd = WURI_Security.make_encrypted_password(encryptedPWD);
		return dbPasswd;
	}
	//사진변환과정
	public File convert(MultipartFile file) throws IOException{    
	    File convFile = new File(file.getOriginalFilename());
	    convFile.createNewFile(); 
	    FileOutputStream fos = new FileOutputStream(convFile); 
	    fos.write(file.getBytes());
	    fos.close(); 
	    return convFile;
	}
	//customerNo 생성기 
	public String make_customer_no() throws Exception   {
		String customerNo = "";
		while(true)  {
			String header="";	
			for(int j=0; j<2; j++)  //알파벳 2개 대문자 추출
			{ 
				Random rnd = new Random();
				header += String.valueOf((char) ((int) (rnd.nextInt(26)) + 65));
			} 					 
			customerNo	= String.format( "%s%06d", header, Utils.randomRange(0,999999) );   // total 8자리
			
			Customer user = new Customer();
			user = customerService.read(customerNo);
			if(user==null) break;
		} 
		return customerNo;
	}
	
}
