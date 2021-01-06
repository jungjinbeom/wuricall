package com.wurigo.socialService.contoller;


import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wurigo.socialService.dao.TaxiDriverMapper;
import com.wurigo.socialService.security.WURI_Security;
import com.wurigo.socialService.service.CustomerService;
import com.wurigo.socialService.service.ReservationService;
import com.wurigo.socialService.service.TaxiGroupService;
import com.wurigo.socialService.service.UserService;


@RestController
@CrossOrigin(origins="*", maxAge=3600)
@RequestMapping("/api/reservation")
public class SocBookingController {
	
	@Autowired
//	SocBookingService	callTaxiService;
	ReservationService	socBookingService;
	@Autowired
	CustomerService userService;	 
	@Autowired
	TaxiGroupService taxiGroupService;
	@Autowired 
	TaxiDriverMapper taxiDriverMapper;
	@PostMapping("/register")
	public Map<String, Object> reserRegister(@RequestBody Map<String,Object> params) throws Exception {
		System.out.println(params); 
		Map<String,Object> map =  socBookingService.regist_socialBooking(params);
		return map;
	}
	@PostMapping("/taxiReserTodayCount")
	public Map<String,Object> taxiReserTodayCount(@RequestBody Map<String,Object> params)throws Exception{
		String customerNo = (String)params.get("adminNum");
		String email = userService.getAdmin(customerNo);
		List<Map<String,Object>> list = socBookingService.userCustomerNoList(email);
		Map<String,Object> map = new HashMap<String, Object>();
		
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		for(int i = 0;i<list.size();i++) {
			params.put("customerNo",list.get(i).get("customerNo"));
			data.addAll(socBookingService.taxiReserTodayCount(params));
		}
		for(int i =0;i<data.size();i++) {
			Map<String, Object> result = data.get(i);
			if(result==null) {
				data.remove(i);
			}
		}
		if(data.size()==1 && data.get(0)==null) {
			map.put("count",0);
		}else {
			map.put("count",data.size());
		}
		return map;
	}
	@PostMapping("/taxiReserTodayList")
	public Map<String,Object> taxiReserTodayList(@RequestBody Map<String,Object> params) throws Exception{
		Map<String,Object> reserList = socBookingService.get_socialBookingList(params);
		return reserList;
	}
	@PostMapping("/taxiBookingInfo/{reserveNo}/{num}")
	public Map<String,Object> taxiBookingInfo(@PathVariable String reserveNo,@PathVariable int num) throws Exception{
		
		Map<String,Object> map = socBookingService.getBookingInfo(reserveNo);
		String passenger = (String)map.get("passenger");
		String [] str = passenger.split("\\/");
		map.put("userName", str[0]);
		map.put("phone", str[1]);
		
		////////////택시그룹번호로 그룹 정보 가져오기////////////////////////
		String groupNo =(String)map.get("groupNo");
		Map<String,Object> data = taxiGroupService.taxiGroupDetail(groupNo);
		map.put("driverlist",data.get("driverlist"));
		String driverNo = (String)map.get("driverNo");
		String driverInfo = taxiDriverMapper.taxiDriverInfo(driverNo);
		map.put("driverInfo", driverInfo);
		map.put("groupName",data.get("groupName"));
		map.put("create_date",data.get("create_date"));
		////////////택시그룹번호로 그룹 정보 가져오기////////////////////////
		
		/////////////위도 경도 복호화////////////////////////
		String finishLocX=(String) map.get("finishLocX");
		String finishLocY=(String) map.get("finishLocY");
		Map<String,String>  finishLoc = WURI_Security.decodeLoc(finishLocX,finishLocY);
		String startLocX = (String) map.get("startLocX");
		String startLocY = (String) map.get("startLocY");
		map.put("finishLocX",finishLoc.get("locX"));
		map.put("finishLocY",finishLoc.get("locY"));
		Map<String,String> startLoc=  WURI_Security.decodeLoc(startLocX,startLocY);
		map.put("startLocX",startLoc.get("locX"));
		map.put("startLocY",startLoc.get("locY"));
		/////////////위도 경도 복호화////////////////////////
		int groupNoType = Integer.parseInt((String)map.get("groupNo"));
		map.put("groupNo",groupNoType);
		
		return map;
		
	}
	@PostMapping("/taxiBookingSearch")
	public Map<String,Object> taxiBookingSearch(@RequestBody Map<String,Object> params ) throws Exception {
			Map<String,Object> map = socBookingService.get_socialBookingList(params);
		return map;
	}
	@PutMapping("/reserUpdate")
	public Map<String,Object> reserUpdate(@RequestBody Map<String,Object> params ) throws Exception {
		int reserveNo = (int) params.get("reserveNo");
		Map<String,Object> data = userService.social_readOne(reserveNo);
		String customerNo = (String)data.get("customerNo");
		Map<String,Object> map = userService.social_userInfo(customerNo);
		params.put("user_token",map.get("user_token"));
		socBookingService.update_socialBooking(params);
		
		return map; 
	}
	@DeleteMapping("/taxiBookingDelete/{reserveNo}")
	public Map<String,Object> taxiBookingDelete(@PathVariable String reserveNo,@RequestBody Map<String,Object> param) throws Exception {
		param.put("reserveNo", reserveNo);
		param.put("customerType",9);
		Map<String,Object> map = socBookingService.cancel_socialBooking(param);
		return map;
	}
	
}
