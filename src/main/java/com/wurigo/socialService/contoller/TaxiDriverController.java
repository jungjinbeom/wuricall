package com.wurigo.socialService.contoller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wurigo.socialService.service.TaxiDriverService;

@RestController
@CrossOrigin(origins="*", maxAge=3600)
@RequestMapping("/api/taxiDriver")
public class TaxiDriverController {
	
	@Autowired
	TaxiDriverService taxiDriverService;
	
	@PostMapping("/taxiDriverList") 
	public List<Map<String,Object>>  taxiDriverList(@RequestBody Map<String,Object> params) throws Exception {
		Map<String,Object> map = new HashMap<String, Object>();
		String adminNum = (String)params.get("adminNum");
		String groupCode = taxiDriverService.readOne(adminNum);
		String [] array = groupCode.split("_");
		map.put("codeL",array[0]);
		map.put("codeM",array[1]);
		List<Map<String,Object>>list = taxiDriverService.TaxiDriverList(map);
		return list;
	}
}
