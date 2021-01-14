package com.wurigo.socialService.contoller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins="*", maxAge=3600)
@RequestMapping("/api/auth")
public class AuthController {

	
	//페이지 핸들러 
	@PostMapping("/auth")
	public Map<String, Object> auth(HttpServletRequest req,@RequestBody Map<String,Object> map) {
		Map<String, Object> data = new HashMap<String, Object>();
		String state=(String)map.get("state");
		if(state==null) {
			System.out.println("페이지 핸들러 실패");
			data.put("success",true);
			data.put("isAuth",false);
		}else {
			System.out.println("페이지 핸들러 성공");
			data.put("isAuth",true);
		}
		return data;
	}
}
