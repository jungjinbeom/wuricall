package com.wurigo.socialService.contoller;

import java.util.ArrayList;
import java.util.Arrays;
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
import com.wurigo.socialService.service.TaxiGroupService;

@RestController
@CrossOrigin(origins="*", maxAge=3600)
@RequestMapping("/api/taxiDriver")
public class TaxiDriverController {
	
	@Autowired
	TaxiDriverService taxiDriverService;
	@Autowired
	TaxiGroupService taxiGroupService;
	@PostMapping("/taxiDriverList") 
	public List<Map<String,Object>> taxiDriverList(@RequestBody Map<String,Object> params) throws Exception {
		Map<String,Object> map = new HashMap<String, Object>();
		String adminNum = (String)params.get("adminNum");
		String groupCode = taxiDriverService.readOne(adminNum);
		String [] array = groupCode.split("_");
		map.put("codeL",array[0]);
		map.put("codeM",array[1]);
		List<Map<String,Object>>list = taxiDriverService.TaxiDriverList(map);
		return list;
	}
	@PostMapping("/taxiDriverAllList")
	public List<Map<String,Object>> taxiDriverAllList(@RequestBody Map<String,Object> param) throws Exception{
		 String groupnNo = (String)param.get("groupNo");
		 String state = (String)param.get("state");
		 List<Map<String,Object>> list = new ArrayList<Map<String, Object>>();
		 List<Map<String,Object>> partList = new ArrayList<Map<String, Object>>();
		 Map<String,Object> map = new HashMap<String, Object>();
		 Map<String,Object> data = new HashMap<String, Object>();
		 if(state=="allList") {
			 list = taxiDriverService.taxiDriverAllList();
		 }else {
			 list = taxiDriverService.taxiDriverAllList();
//			 map = taxiGroupService.taxiGroupDetail(groupnNo);
//			 String driverList = (String)map.get("driverlist");
//			 String[] driverNums = driverList.split("\\^");
//			 String driverNo;
//			 for(int idx =0; idx<driverNums.length;idx++) {
//				 driverNo = driverNums[idx];
//				 data = taxiGroupService.taxiDriverInfo(driverNo);
//				 partList.add(idx,data);
//				 System.out.println(list);
//				 System.out.println(partList);
//			 }
//			 for(int i =0;i<list.size();i++) {
//				 for(int k =0;k<partList.size();k++) {
//					 boolean result = Arrays.equals(list.toArray(),partList.toArray());
//				 if(result==false) {
//					 list.remove(i);
//					 partList.remove(i);
//				 }
//				 	System.out.println(list);
//				 }
//			 }
		 }
		return list;
	}
	@PostMapping("/taxiDriverInfoList")
	public List<Map<String,Object>> taxiDriverInfoList(@RequestBody Map<String,Object> params) throws Exception{
		List<Map<String,Object>> list = taxiDriverService.taxiDriverInfoList(params);
		return list;
	}
}
