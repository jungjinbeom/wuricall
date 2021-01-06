package com.wurigo.socialService.contoller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wurigo.socialService.commons.Utils;
import com.wurigo.socialService.domain.Customer;
import com.wurigo.socialService.security.WURI_Security;
import com.wurigo.socialService.service.CustomerService;

@RestController
@CrossOrigin(origins="*", maxAge=3600)
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
	CustomerService userService;
	
	@PostMapping("/register")
	public Map<String,Object> register(@RequestBody Map<String,Object> params) throws Exception{
		 Map<String,Object> map = new HashMap<String, Object>();
		 System.out.println(params);
			 String adminNum = (String)params.get("adminNum");
			 String adminId = userService.getAdmin(adminNum);//아이디값가져오기
			 params.put("pwdType","plain");
			 params.put("zip_code",adminId);
			 params.put("email",null);
			 userService.registerUser(params);
			 map.put("message","회원등록이 완료되었습니다.");
		return map;
	}
	@PostMapping("/userInfoList")
	public List<Map<String,Object>> userInfoList(@RequestBody String adminNum) throws Exception {
		int idx = adminNum.indexOf("=");
		String num = adminNum.substring(0,idx);
		String zip_code = userService.getAdmin(num);
		List<Map<String,Object>> list = userService.social_userInfoList(zip_code);
		return list;
	}
	@PostMapping("/userInfoPartList")
	public List<Map<String,Object>> userInfoPartList(@RequestBody Map<String,Object> params) throws Exception {
		System.out.println(params);
		List<Map<String,Object>> userInfo = new ArrayList<Map<String,Object>>();
		String groupCode =(String)params.get("groupCode");
		if(groupCode.equals("all")) {
			String adminNum=(String)params.get("adminNum");
			String zip_code = userService.getAdmin(adminNum);
			userInfo = userService.social_userInfoList(zip_code);
			
		}else {
			List<Map<String,Object>> list = userService.userInfoPartList(params);
			Map<String,Object> map = new HashMap<String, Object>();
			System.out.println("list"+list);
			for(int i =0;i<list.size();i++) {
				String customerNo = (String)list.get(i).get("customerNo");
				System.out.println(customerNo);
				map = userService.social_userInfo(customerNo);
				System.out.println(map);
				userInfo.add(map);
			}
		}	
		return userInfo;
	}
	@PostMapping("/groupCreate")
	public Map<String,Object> userGroupCreate(@RequestBody Map<String,Object> params) throws Exception{
		Map<String,Object> map = new HashMap<String, Object>();
		Map<String,Object> data = userService.social_getGroupName(params);
		if(data != null) {
			map.put("message","이미 존재하는 그룹명입니다.\n다시 시도해주세요");
		}else {
			userService.social_groupCreate(params);
			map.put("message","그룹이 정상적으로 등록 되었습니다.");
		}
		return map;
	}
	@PostMapping("/groupList")
	public List<Map<String,Object>> userGroupList(@RequestBody Map<String,Object> param) throws Exception{
		List<Map<String,Object>> list = userService.social_getGroupList(param);
		System.out.println(list);
		return list;
	}
	@PostMapping("/userInfo/{customerNo}")
	public Map<String,Object> userInfo(@PathVariable String customerNo) throws Exception{
		Map<String,Object> map  = userService.social_userInfo(customerNo);
		
		String str =(String)map.get("address");
		String [] array = str.split("-");
		String useCount = userService.getUseCount(customerNo);
		map.put("useCount",useCount);
		map.put("uniqueness", map.get("address_detail"));
		map.put("zipCode", array[0]);
		map.put("address", array[1]);
		map.put("addressDetail", array[2]);
		
		Map<String,Object> data = userService.getGroupInfo(customerNo);
		map.put("social_type",data.get("social_type"));
		String groupCode= (String)data.get("groupCode");
		data = userService.getGroupName(groupCode);
		map.put("groupName", data.get("groupName"));
		map.put("groupCode", groupCode);
		
		return map;
	}
	@PostMapping("/userInfoEdit")
	public Map<String,Object> userInfoEdit(@RequestBody Map<String,Object> params) throws Exception{
		Map<String,Object> map = new HashMap<String, Object>();
		String groupCode = (String)params.get("groupCode");
		userService.social_userGroupUpdate(params);
		userService.social_userInfoEdit(params);
		return map;
	}
	@PostMapping("/userGroupDetail/{groupCode}")
	public List<Map<String,Object>> userGroupDetail(@PathVariable int groupCode , @RequestBody Map<String,Object> params) throws Exception{
		Map<String,Object> map = new HashMap<String, Object>();
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> list = userService.userInfoPartList(params);
		for(int i =0;i<list.size();i++) {
			String customerNo = (String)list.get(i).get("customerNo");
			map = userService.social_userInfo(customerNo);
				if(map!=null) {
				data.add(map);
			}
		}
		System.out.println(data);
		return data;
	}
	@PostMapping("/userGroupName/{groupCode}")
	public Map<String,Object> userGroupName(@PathVariable String groupCode)throws Exception{
		Map<String,Object> map = new HashMap<String, Object>();
		map = userService.getGroupName(groupCode);
		return map;
	}
	@PutMapping("/groupNameUpdate/{groupCode}")
	public Map<String,Object> groupNameUpdate(@PathVariable String groupCode,@RequestBody Map<String,Object> params)throws Exception{
		Map<String,Object> map = new HashMap<String, Object>();
		int result = userService.getGroupNameUpdate(params);
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
		Map<String,Object> map= userService.groupDelete(param);
		return map;
	}
	//암호화 과정
	public String make_plain2dbpassword(String plainPasswd) {
		String encryptedPWD = WURI_Security.create_encrypted_password(plainPasswd);  //from homepage
		String dbPasswd = WURI_Security.make_encrypted_password(encryptedPWD);
		return dbPasswd;
	}
	
}
