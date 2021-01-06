package com.wurigo.socialService.contoller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wurigo.socialService.service.TaxiGroupService;

@RestController
@CrossOrigin(origins="*", maxAge=3600)
@RequestMapping("/api/taxiGroup")
public class TaxiGroupController {
	@Autowired
	TaxiGroupService taxiGroupService;  
	@PostMapping("/taxiGroupList/{adminNum}")
	public List<Map<String,Object>> taxiGroupList(@PathVariable String adminNum)throws Exception {//그룹 리스트 가져오기 
		List<Map<String,Object>> list =taxiGroupService.taxiGroupList(adminNum);
		Map<String,Object> data = new HashMap<String, Object>();
		Map<String,Object> map = new HashMap<String, Object>();
		String [] names = null;
		for(int i =0;i<list.size();i++) {
			int count = 0 ;
			String driverNum = "";
			driverNum+=list.get(i).get("driverlist");
		    names = driverNum.split("\\^");
		    count = names.length;
		    map.put("count",count);
		    data = list.get(i);
		    data.put("count", map.get("count"));
		}
		System.out.println(list);
		return list;
	}
	@PostMapping("/taxiGroupDetail/{groupNo}")
	public List<Map<String, Object>>taxiGroupDetail(@PathVariable String groupNo)throws Exception {
		//그룹 상세목록 리스트 가져올시 그룹에 포함된 기사 없애고 리스트 가져오기 
		System.out.println(groupNo);
		Map<String,Object> map = taxiGroupService.taxiGroupDetail(groupNo);
		Map<String,Object> data = new HashMap<String, Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String driverlist = (String)map.get("driverlist");
		String[] driverNums = driverlist.split("\\^");
		for(int i =0; i<driverNums.length;i++) {
			String driverNo = driverNums[i];
			data = taxiGroupService.taxiDriverInfo(driverNo);
			System.out.println(data);
			if(data != null) {
				list.add(data);
			}
		}
		return list;
	}
	@PostMapping("/register/{groupName}/{adminNum}")
	public Map<String,Object> taxiDriverRegister(@RequestBody List<Map<String,Object>> params,
												 @PathVariable String groupName, 
												 @PathVariable String adminNum)throws Exception {//그룹 등록 
		Map<String,Object> map = new HashMap<String, Object>();
		Map<String,Object> data = new HashMap<String, Object>();		
		String driverList = "";
		for(int i =0;i<params.size();i++) {
			driverList+=params.get(i).get("driverno")+"^";
		}
		map.put("adminNum",adminNum);
		map.put("groupName",groupName);
		map.put("driverlist",driverList);
		taxiGroupService.taxiGroupRegister(map);
		data.put("success","success");
		data.put("message","그룹생성이 완료되었습니다");
		return data;
	}
	@PostMapping("/groupNameInfo/{groupNo}")
	public Map<String,Object> taxiGroupNameInfo(@PathVariable String groupNo) throws Exception{
		Map<String,Object> map = taxiGroupService.taxiGroupDetail(groupNo);
		return map;
	}
	@PutMapping("/edit/{groupNo}")
	public Map<String,Object> taxiGroupEdit(@RequestBody Map<String,Object> params,@PathVariable String groupNo) throws Exception{
		Map<String,Object> map = new HashMap<String, Object>();
		String groupName = (String)params.get("groupName");
		List<Map<String,Object>> list=(List<Map<String, Object>>) params.get("driverList");	
		String driverList="";
		for(int i = 0;i<list.size();i++) {
			String driverNo = (String) list.get(i).get("driverno");
			driverList += driverNo+"^";
		}
		taxiGroupService.taxiGroupEdit(driverList,groupName,groupNo);
		map.put("message","정상적으로 수정이 완료되었습니다.");
		return map;
	}
	
	@DeleteMapping("/delete/{groupNo}")
	public Map<String,Object> taxiGroupDelete(@PathVariable String groupNo) throws Exception{//그룹 삭제 
		Map<String,Object> map = new HashMap<String, Object>();
		//예약에가서 만약 예약자가 있다면 택시그룹을 삭제하기 위해서는 예약을 취소하던가 완료된 뒤에 삭제 해주세요 
		taxiGroupService.taxiGroupDelete(groupNo);
		map.put("message","선택하신 그룹이 삭제되었습니다.");
		return map;
	}
}
