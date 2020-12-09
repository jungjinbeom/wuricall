package com.wurigo.socialService.contoller;

import java.io.IOException;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wurigo.socialService.commons.Common;
import com.wurigo.socialService.service.CallTaxiService;


@RestController
@CrossOrigin(origins="*", maxAge=3600)
@RequestMapping("/api/reservation")
public class CallTaxiController {
	
	@Autowired
	CallTaxiService	callTaxiService;
	
	
	@PostMapping("/register")
	public String reserRegister(@RequestBody Map<String,Object> params) throws Exception {
		System.out.println(params);
		params.get("sX");
		params.get("sY");
		params.get("eX");
		params.get("eY");
		params = coordXY_encryption(params);
		System.out.println(params);
		callTaxiService.reserRegist(params);
		callTaxiService.socialReserRegist(params);
		
		return"";
	}
	@PostMapping("/reservationList")
	public String reservationList(@RequestBody Map<String,Object> params)throws Exception {
		List<Map<String,Object>>list = callTaxiService.reserList(params);
		return"";	
	}
	
	
private Map<String, Object> coordXY_encryption(Map<String, Object> param) throws IOException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		
		String startX  = (String)param.get("sX");
		String startY  = (String)param.get("sY");
		String finishX = (String)param.get("eX");
		String finishY = (String)param.get("eY");
		
		Map<String, String> encLoc1 = Common.encodeLoc(startX, startY);
		String encSX = encLoc1.get("locX");
		String encSY = encLoc1.get("locY");
		Map<String, String> encLoc2 = Common.encodeLoc(finishX, finishY);
		String encEX = encLoc2.get("locX");
		String encEY = encLoc2.get("locY");
		
		param.put("s_longitude",encSX);  //for java 
		param.put("s_latitude", encSY);
		param.put("e_longitude",encEX);
		param.put("e_latitude", encEY); 
		
		
// PHP Server연동 앱을 위해서 데이터 저장 		
		Map<String, String> encLocPHP1 = Common.encodePHPLoc(startX, startY);
		String encPHPSX = encLocPHP1.get("locX");
		String encPHPSY = encLocPHP1.get("locY");
		Map<String, String> encLocPHP2 = Common.encodePHPLoc(finishX, finishY);
		String encPHPEX = encLocPHP2.get("locX");
		String encPHPEY = encLocPHP2.get("locY");
		param.put("sLonenc",encPHPSX);  // for PHP
		param.put("sLatenc",encPHPSY);
		param.put("eLonenc",encPHPEX);
		param.put("eLatenc",encPHPEY);
		
		return param;
	}

}
