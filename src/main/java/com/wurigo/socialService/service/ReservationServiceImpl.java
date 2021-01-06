package com.wurigo.socialService.service;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wurigo.socialService.commons.Common;
import com.wurigo.socialService.commons.Utils;
import com.wurigo.socialService.dao.BookingDAO;
import com.wurigo.socialService.dao.ConstantDAO;
import com.wurigo.socialService.dao.CustomerDAO;
import com.wurigo.socialService.dao.HistoryCustomerDAO;
import com.wurigo.socialService.dao.ReservationDAO;
import com.wurigo.socialService.dao.ServiceUtilsDAO;
import com.wurigo.socialService.dao.TaxiDriverMapper;
import com.wurigo.socialService.dao.UserMapper;
import com.wurigo.socialService.domain.Booking;
import com.wurigo.socialService.domain.Customer;
import com.wurigo.socialService.domain.Reservation; 

@Service
@Transactional
public class ReservationServiceImpl implements ReservationService {

	@Autowired
	private ReservationDAO reserveDao;
	@Autowired
	private CustomerDAO custDao;
	@Autowired
	private ConstantDAO constDao;
	@Autowired
	private BookingDAO bookingDao;
	@Autowired
	private HistoryCustomerDAO historyCustDao;
	@Autowired
	private ServiceUtilsDAO ServiceDao;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private TaxiDriverMapper taxiDriverMapper;
	//기사 history, 기사 status도 처리!!!!
	
	
	@Override
	public Map<String, Object> getReservationList(Map<String, Object> param) throws Exception {
		
		String accesstoken = (String)param.get("accesstoken");
System.out.println("getReservationList:" + accesstoken);	
				 
		Map<String, Object> ret = Common.check_accesstoken(accesstoken);
		int code = (int)ret.get("code");
		if(code<2) return ret;
		
		String customerNo = (String)ret.get("customerNo");
		int customerType = (int)ret.get("customerType");
		param.put("customerNo", customerNo);
		param.put("customerType", customerType);
			
		int reserveType = Integer.parseInt((String)param.get("reserveType"));
		String reserveNo   = (String)param.get("reserveNo");
		String startDay 	= (String)param.get("startDay");
		String endDay 	= (String)param.get("endDay");
System.out.println("getReservationList:" + param.toString());

		List<Reservation> list=null;
		List<Map<String, Object>>  retList = new ArrayList<Map<String, Object>>();
		if(reserveNo.equals("today")) {
			list = reserveDao.findReservationByToday(customerNo);
		}
		else if(reserveNo.contentEquals("0")) {
			if( startDay!=null && !startDay.isEmpty()) {
				if(endDay !=null && !endDay.isEmpty()) list = reserveDao.findReservationByInterval(customerNo, startDay, endDay);
				else list = reserveDao.findReservationByAfterDay(customerNo, startDay);
			}
		}
		if(list!=null) {
			for(int i=0; i<list.size(); i++ ) retList.add( list.get(i).convert2Map());
		}
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		String newtoken = Common.update_accesstoken(accesstoken);
		retMap.put("accesstoken", newtoken);	  		
		retMap.put("code","777");
		retMap.put("count",retList.size());
		retMap.put("feed", retList);
		return  retMap;
		
	}
	@Override
	public Reservation findReservationByNo(String reserveNo, String startDay) throws Exception {
		Reservation result = reserveDao.findReservationByNo(reserveNo,startDay);
		return result;
	}
	@Override
	public Reservation findReservationByName(String customerNo,String reserveName) throws Exception{
		
		Reservation result = reserveDao.findReservationByName(customerNo,reserveName);
		return result;
	}
	
	@Override
	public Map<String, Object> registReservation(Map<String, Object> param) throws Exception{
		
		String accesstoken = (String)param.get("accesstoken");
System.out.println("registReservation:" + accesstoken);	
		 
		Map<String, Object> ret = Common.check_accesstoken(accesstoken);
		int code = (int)ret.get("code");
		if(code<2) return ret;
		
		Map<String, Object>  retMap = new HashMap<String, Object>();
		
		String customerNo = (String)ret.get("customerNo");
		int customerType = (int)ret.get("customerType");
		param.put("customerNo", customerNo);
		param.put("customerType", customerType);
		
		String reserveName = (String)param.get("reserveName");
		Reservation reservation = findReservationByName(customerNo,reserveName);
		if(reservation!=null) {    
			retMap.put("code", "999");
			retMap.put("message", "동일한 예약이름이 존재합니다.");
			retMap.put("accesstoken", "");
			return retMap;
		}
		
		param = location_encryption(param);
		
		int reserveNo = getMaxReserveNo()+ 1;
		param.put("reserveNo",reserveNo);
System.out.println("reserveNo:" + reserveNo);		
// groupNo>0 일때 drivers 값 할당처리  		
		
		String reserveTime = (String)param.get("reserveTime");
		String startAddress = (String)param.get("startAddress");
		int groupNo = Integer.parseInt((String)param.get("groupNo"));

		String realdays = (String)param.get("realdays");
		String[] rDays = realdays.split("\\,");
		
		
		int noti_mode=0;  //insert
		int rcount=0; 
		for(int i=0; i< rDays.length; i++) {
			
			String startDay = rDays[i];
			param.put("startDay",startDay);
			int count = reserveDao.registReservation(param);
			Integer no = ((java.math.BigInteger)param.get("serialNo")).intValue();
			if(no>0) {
				rcount++;
				send_noti_bookingcall(customerNo,customerType,reserveNo, 0, startDay, noti_mode,reserveTime,startAddress,groupNo ) ;
			}
		}

		String newtoken = Common.update_accesstoken(accesstoken);
		String message = "정기예약이 " + rcount + "건 등록된었습니다."; 
		retMap.put("accesstoken", newtoken);	  		
		retMap.put("code","777");
		retMap.put("message", message);
		return  retMap;
	}
	
	@Override
	public Map<String, Object> updateReservation(Map<String, Object> param) throws Exception{
		
		String accesstoken = (String)param.get("accesstoken");
		Map<String, Object> ret = Common.check_accesstoken(accesstoken);
		int code = (int)ret.get("code");
		if(code<2) return ret;
		String customerNo = (String)ret.get("customerNo");
		int customerType = (int)ret.get("customerType");
		param.put("customerNo", customerNo);
		param.put("customerType", customerType);
		
		
		int reserveNo = Integer.parseInt((String)param.get("reserveNo")); 
		
		param = location_encryption(param);
		
		Map<String, Object>  retMap = new HashMap<String, Object>();
		String startDay = (String)param.get("startDay");
		int r_success = (int)reserveDao.updateReservation(param);
		if(r_success==0) {
			retMap.put("code","666");
			retMap.put("message", "예약 수정 실패");
			retMap.put("accesstoken", "");
		}
		else {
			String reserveTime = (String)param.get("reserveTime");
			String startAddress = (String)param.get("startAddress");
			int groupNo = Integer.parseInt((String)param.get("groupNo"));
			
			int noti_mode = 1; //update
			send_noti_bookingcall(customerNo, customerType,reserveNo, 0, startDay, noti_mode,reserveTime,startAddress,groupNo) ;
			
			String newtoken = Common.update_accesstoken(accesstoken);
			String message = startDay + "예약이 수정되었습니다."; 
			retMap.put("accesstoken", newtoken);	  		
			retMap.put("code","778");
			retMap.put("message", message);
		}
		return  retMap;
	}
	
	@Override
	public Map<String, Object> cancelReservation(Map<String, Object> param) throws Exception{
		
		String accesstoken = (String)param.get("accesstoken");						 
		Map<String, Object> ret = Common.check_accesstoken(accesstoken);
		int code = (int)ret.get("code");
		if(code<2) return ret;
		
		String customerNo = (String)ret.get("customerNo");
		int customerType = (int)ret.get("customerType");		
		String reserveNo = (String)param.get("reserveNo");
		String startDay  = (String)param.get("startDay");
		
		String strCode="",  message="";
		Map<String, Object> retMap = new HashMap<String,Object>();
//state =>  0: 예약대기  1 : 예약확정  2: 예약취소    3: 예약종료     5: 승차거부  6: 차량미배차		
		Reservation reservation = reserveDao.findReservationByNo(reserveNo,startDay);
		if(reservation==null) {
			retMap.put("code", "555");
			retMap.put("message", "not found reservation:" + reserveNo);
			return retMap;
		}
System.out.println("cancelReservation:" + reservation.toString());

		int noti_mode = 2;  //delete
		if( reservation.getState() ==0 )  {  // 기사 수락전예약은 삭제
			int rcount = reserveDao.deleteReservation(reserveNo,startDay);
			if( rcount==1 ) {
				strCode = "888"; message =  "delete reservation successfully";	
				
				String reserveTime = (String)param.get("reserveTime");
				String startAddress = (String)param.get("startAddress");
//				int groupNo = Integer.parseInt((String)param.get("groupNo"));
				int groupNo = -1;
				send_noti_bookingcall (customerNo, customerType,Integer.parseInt(reserveNo), 1, startDay, noti_mode,reserveTime,startAddress,groupNo ) ;	
			}
			else {
				strCode = "666"; message =  "delete reservation error!";	
			}
		}
		else if( reservation.getState() ==1 ) { //예약 확정 후 취소 
			String sX = (String)param.get("sX");
			String sY = (String)param.get("sY");
			String driverNo  = reservation.getDriverNo(); //(String)param.get("driverNo");
System.out.println("예약 확정 후 취소:" + param.toString());	

			int rcount = cancel_accepted_booking(Integer.parseInt(reserveNo), 1, startDay,sX,sY,driverNo,customerNo);
			if(rcount==1) {
				strCode = "788"; message = "cancel reservation successfully";  
			}
		}
		
		String newtoken = Common.update_accesstoken(accesstoken);
		retMap.put("accesstoken", newtoken);
		retMap.put("code",strCode);
		retMap.put("message", reserveNo);
System.out.println("cancelBooking:" + retMap.toString());		
		return  retMap;
	}
	
	@Override
	public void deleteReservation(String reserveNo,String startDay) throws Exception{
		reserveDao.deleteReservation(reserveNo, startDay);
	}
	
	@Override
	public int getMaxReserveNo() throws Exception {
		return reserveDao.getMaxReserveNo();
	}
	
	private Map<String, Object> location_encryption(Map<String, Object> param) throws IOException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {

		String startAddress = (String)param.get("startAddress");
		String finishAddress = (String)param.get("finishAddress");
		String s_sidocode = Utils.get_area_code(startAddress);		
		String e_sidocode = Utils.get_area_code(finishAddress);
		
		String startX  = (String)param.get("startLocX");
		String startY  = (String)param.get("startLocY");
		String finishX = (String)param.get("finishLocX");
		String finishY  = (String)param.get("finishLocY");
		
		Map<String, String> encLoc1 = Common.encodeLoc(startX, startY);
		String encSX = encLoc1.get("locX");
		String encSY = encLoc1.get("locY");
		Map<String, String> encLoc2 = Common.encodeLoc(finishX, finishY);
		String encEX = encLoc2.get("locX");
		String encEY = encLoc2.get("locY");
		
		param.put("startLocX",encSX);
		param.put("startLocY",encSY);
		param.put("finishLocX",encEX);
		param.put("finishLocY",encEY);
		
		param.put("s_sido_code", s_sidocode );
		param.put("e_sido_code", e_sidocode );
		
// PHP Server연동 앱을 위해서 데이터 저장 		
		Map<String, String> encLocPHP1 = Common.encodePHPLoc(startX, startY);
		String encPHPSX = encLocPHP1.get("locX");
		String encPHPSY = encLocPHP1.get("locY");
		Map<String, String> encLocPHP2 = Common.encodePHPLoc(finishX, finishY);
		String encPHPEX = encLocPHP2.get("locX");
		String encPHPEY = encLocPHP2.get("locY");
		param.put("startLocXenc",encPHPSX);
		param.put("startLocYenc",encPHPSY);
		param.put("finishLocXenc",encPHPEX);
		param.put("finishLocYenc",encPHPEY);
		
		return param;
	}
/**************************************************************************************************/
/*	@Override
	public Booking findBookingByToday(String customerNo) throws Exception{
		return bookingDao.findBookingByToday(customerNo);
	}
	
	@Override
	public List<Booking> findBookingByAfterDay(String customerNo,String startDay) throws Exception {
		return bookingDao.findBookingByAfterDay(customerNo, startDay);
	}
	@Override
	public List<Booking> findBookingByInterval(String customerNo,String startDay,String endDay) throws Exception {
		return bookingDao.findBookingByInterval(customerNo, startDay, endDay);
	} */
	@Override
	public Map<String, Object> findBookingByNo(String reserveNo) throws Exception{
		 
		return bookingDao.findBookingByNo(reserveNo);
	}
	public Map<String,Object> getBookingInfo(@Param("reserveNo") String reserveNo) throws Exception{
		 
		return bookingDao.findBookingByNo(reserveNo);
	}
	@Override
	public Map<String, Object> registBooking(Map<String, Object> param) throws Exception{
		
		String accesstoken = (String)param.get("accesstoken");
		System.out.println("registBooking:" + accesstoken);	
				 
		Map<String, Object> ret = Common.check_accesstoken(accesstoken);
		int code = (int)ret.get("code");
		if(code<2) return ret;
		
		Map<String, Object>  retMap = new HashMap<String, Object>();
		
		String customerNo = (String)ret.get("customerNo");
		int customerType = (int)ret.get("customerType");
		param.put("customerNo", customerNo);
		param.put("customerType", customerType);
		
		param = location_encryption(param);
		String startDay = (String)param.get("startDay");
		
		int noti_mode = 0;  //insert
		int reserveNo =0;
		String strCode = "";
		int rcount = bookingDao.registBooking(param);
		if( rcount==1 ) {
			strCode = "777";
			reserveNo = ((java.math.BigInteger)param.get("reserveNo")).intValue();
			
			if(customerType==9) { // 무료사용자 예약정보 등록 
				String adminNum = (String)param.get("adminNum");
				
				Map<String,Object> sparam = new HashMap<String, Object>(); 
				sparam.put("reserveType", 0);
				sparam.put("reserveNo", reserveNo);
				sparam.put("customerNo", customerNo);
				sparam.put("adminNum", adminNum);
				ServiceDao.insert_social_booking(sparam);
			}
			
			String reserveTime = (String)param.get("reserveTime");
			String startAddress = (String)param.get("startAddress");
			int groupNo = Integer.parseInt((String)param.get("groupNo"));
			
			send_noti_bookingcall (customerNo, customerType,reserveNo, 0, startDay, noti_mode,reserveTime,startAddress,groupNo ) ;		
		}
		else {
			strCode = "666";
			retMap.put("code",strCode);
			retMap.put("message", "Error!");
			return retMap;
		}
		
		String newtoken = Common.update_accesstoken(accesstoken);
		retMap.put("accesstoken", newtoken);
		retMap.put("code",strCode);
		retMap.put("message", reserveNo);
		
System.out.println("registBooking:" + retMap.toString());		
		return  retMap;
	}
	
	public Map<String, Object> regist_socialBooking(Map<String, Object> param) throws Exception{
		System.out.println(param.get("user_token"));
		
		param.put("accesstoken",Common.make_access_token((String)param.get("user_token")));
		String accesstoken = (String)param.get("accesstoken");
		
		System.out.println(accesstoken);
		
		Map<String, Object> ret = Common.check_accesstoken(accesstoken);
		int code = (int)ret.get("code");
		if(code<2) return ret;
		
		Map<String, Object>  retMap = new HashMap<String, Object>();
		
		String customerNo = (String)ret.get("customerNo");
		int customerType = Integer.parseInt((String)ret.get("customerType"));
		param.put("customerNo", customerNo);
		param.put("customerType", customerType);
		
		param = location_encryption(param);
		String startDay = (String)param.get("startDay");
		
		int noti_mode = 0;  //insert
		int reserveNo =0;
		String strCode = "";
		int rcount = bookingDao.registBooking(param);
		if( rcount==1 ) {
			strCode = "777";
			reserveNo = ((java.math.BigInteger)param.get("reserveNo")).intValue();
			
			if(customerType==9) { // 무료사용자 예약정보 등록 
				String adminNum = (String)param.get("adminNum");
				
				Map<String,Object> sparam = new HashMap<String, Object>(); 
				sparam.put("reserveType", 0);
				sparam.put("reserveNo", reserveNo);
				sparam.put("customerNo", customerNo);
				sparam.put("adminNum", adminNum);
				ServiceDao.insert_social_booking(sparam);
			}
			
			String reserveTime = (String)param.get("reserveTime");
			String startAddress = (String)param.get("startAddress");
			int groupNo = (int)param.get("groupNo");
			
			send_noti_bookingcall (customerNo,customerType,reserveNo, 0, startDay, noti_mode,reserveTime,startAddress,groupNo ) ;		
		}
		else {
			strCode = "666";
			retMap.put("code",strCode);
			retMap.put("message", "Error!");
			return retMap;
		}
		
		String newtoken = Common.update_accesstoken(accesstoken);
		retMap.put("accesstoken", newtoken);
		retMap.put("code",strCode);
		retMap.put("message", reserveNo);
		
System.out.println("registBooking:" + retMap.toString());		
		return  retMap;
	}
	
	@Override
	public Map<String, Object> update_socialBooking(Map<String, Object> param) throws Exception{//예약 수정
		param.put("accesstoken",Common.make_access_token((String)param.get("user_token")));
		String accesstoken = (String)param.get("accesstoken");
		Map<String, Object> ret = Common.check_accesstoken(accesstoken);
		int code = (int)ret.get("code");
		if(code<2) return ret;
		String customerNo = (String)param.get("customerNo");
		int customerType = 9;
		
		Map<String, Object>  retMap = new HashMap<String, Object>();
		param.put("customerNo", customerNo);
		param.put("customerType", customerType);
		System.out.println(param);
		param = location_encryption(param);
		String startDay = (String)param.get("startDay");
		
		int noti_mode = 1;  //update
		int reserveNo =0;
		
		String strCode = "";
		int rcount = bookingDao.updateBooking(param);	
System.out.println("updateBooking result:" + rcount);	

		if( rcount==1 ) {
			strCode = "777";
			reserveNo = (int)param.get("reserveNo");
			String reserveTime = (String)param.get("reserveTime");
			String startAddress = (String)param.get("startAddress");
			int groupNo = (int)param.get("groupNo");
			send_noti_bookingcall (customerNo,customerType, reserveNo, 0, startDay, noti_mode,reserveTime, startAddress,groupNo) ;	
		}
		else {
			strCode = "666";
			retMap.put("code",strCode);
			retMap.put("message", "Error!");
			return retMap;
		}
		
		String newtoken = Common.update_accesstoken(accesstoken);
		retMap.put("accesstoken", newtoken);
		retMap.put("code",strCode);
		retMap.put("message", reserveNo);
System.out.println("update_accesstoken:" + retMap.toString());		
		return  retMap;
	}
	@Override
	public Map<String, Object> updateBooking(Map<String, Object> param) throws Exception{
		
		String accesstoken = (String)param.get("accesstoken");
System.out.println("updateBooking:" + accesstoken);	
						 
		Map<String, Object> ret = Common.check_accesstoken(accesstoken);
		int code = (int)ret.get("code");
		if(code<2) return ret;
		
		Map<String, Object>  retMap = new HashMap<String, Object>();
		
		String customerNo = (String)ret.get("customerNo");
		int customerType = (int)ret.get("customerType");
		param.put("customerNo", customerNo);
		param.put("customerType", customerType);
		
		param = location_encryption(param);
		String startDay = (String)param.get("startDay");
		
		int noti_mode = 1;  //update
		int reserveNo =0;
		String strCode = "";
		int rcount = bookingDao.updateBooking(param);	
System.out.println("updateBooking result:" + rcount);	

		if( rcount==1 ) {
			strCode = "777";
			reserveNo = (int)param.get("reserveNo");
			String reserveTime = (String)param.get("reserveTime");
			String startAddress = (String)param.get("startAddress");
			int groupNo = Integer.parseInt((String)param.get("groupNo"));			
			send_noti_bookingcall (customerNo,customerType,reserveNo, 0, startDay, noti_mode,reserveTime, startAddress,groupNo) ;	
		}
		else {
			strCode = "666";
			retMap.put("code",strCode);
			retMap.put("message", "Error!");
			return retMap;
		}
		
		String newtoken = Common.update_accesstoken(accesstoken);
		retMap.put("accesstoken", newtoken);
		retMap.put("code",strCode);
		retMap.put("message", reserveNo);
System.out.println("update_accesstoken:" + retMap.toString());		
		return  retMap;
	}
	
	
//	@Override
//	public Map<String, Object> getBookingList(Map<String, Object> param) throws Exception {
//		
//		String accesstoken = (String)param.get("accesstoken");
////System.out.println("getBookingList:" + accesstoken);	
//				 
//		Map<String, Object> ret = Common.check_accesstoken(accesstoken);
//		int code = (int)ret.get("code");
//		if(code<2) return ret;
//		
//		String customerNo = (String)ret.get("customerNo");
//		int customerType = (int)ret.get("customerType");
//		param.put("customerNo", customerNo);
//		param.put("customerType", customerType);
//			
//		int reserveType = Integer.parseInt((String)param.get("reserveType"));
//		String reserveNo    = (String)param.get("reserveNo");
//		String startDay 	= (String)param.get("startDay");
//		String endDay 		= (String)param.get("endDay");
//System.out.println("getBookingList:" + param.toString());	
//
//		List<Map<String, Object>> list=null;
//		List<Map<String, Object>> retList = new ArrayList<Map<String, Object>>();
//		if(reserveNo.equals("today")) {
//			list = bookingDao.findBookingByToday(customerNo,startDay);
//		}
//		else if(reserveNo.contentEquals("0")) {
//			if( startDay!=null && !startDay.isEmpty()) {
//				if(endDay !=null && !endDay.isEmpty()) list = bookingDao.findBookingByInterval(customerNo, startDay, endDay);
//				else list = bookingDao.findBookingByAfterDay(customerNo, startDay);
//			}
//		}
//		if(list!=null) {
//			for(int i=0; i<list.size(); i++ ) retList.add(((Booking) list.get(i)).convert2Map());
//		}
//		
//		Map<String, Object> retMap = new HashMap<String, Object>();
//		String newtoken = Common.update_accesstoken(accesstoken);
//		retMap.put("accesstoken", newtoken);	  		
//		retMap.put("code","777");
//		retMap.put("count",retList.size());
//		retMap.put("feed", retList);
//		return  retMap;
//		
//	}
	
	@Override
	public Map<String,Object> get_socialBookingList(Map<String, Object> param) throws Exception {
		Map<String, Object> ret = new HashMap<String, Object>();
		
		ret.put("customerNo", (String)param.get("adminNum"));
		ret.put("customerType",(Integer)param.get("customerType"));
		String customerNo = (String)ret.get("customerNo");
		String email = custDao.getAdmin(customerNo);
		List<Map<String,Object>> customerNoList = ServiceDao.userCustomerNoList(email);
		
		String reserveNo    = (String)param.get("reserveNo");
		String startDay 	= (String)param.get("startDay");
		String endDay 		= (String)param.get("endDay");
		
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		
		if(reserveNo.equals("today")) {
			for(int i=0; i<customerNoList.size();i++) {
				customerNo = (String)customerNoList.get(i).get("customerNo");
				data.addAll(bookingDao.findBookingByToday(customerNo,startDay));
				System.out.println(data);
			}
		}
		else if(reserveNo.contentEquals("0")) {
			if( startDay!=null && !startDay.isEmpty()) {
				if(endDay !=null && !endDay.isEmpty()) {
					String state = (String)param.get("state");
					System.out.println(state);
					for(int i =0; i<customerNoList.size();i++) {
						customerNo = (String)customerNoList.get(i).get("customerNo");
						data.addAll(bookingDao.findBookingByInterval(customerNo,startDay,endDay,state));
					}
				}else{
					String state = (String)param.get("state");
					System.out.println(state);
					for(int i =0; i<customerNoList.size();i++) {
						customerNo = (String)customerNoList.get(i).get("customerNo");
						data.addAll(bookingDao.findBookingByAfterDay(customerNo, startDay,state));
					}
				}
			}
		}
		
		for(int i =0;i<data.size();i++) {
			Map<String, Object> result = data.get(i);
			if(result==null) {
				data.remove(i);
			}
		}
		Map<String, Object> retMap = new HashMap<String, Object>();
		if(data.size()==1 && data.get(0)==null) {
			retMap.put("count",0);
			data.remove(0);
		}else {
			retMap.put("count",data.size());
		}
		
		for(int idx=0;idx<data.size();idx++) {
			String driverNo = (String)data.get(idx).get("driverNo");
			data.get(idx).put("driverInfo",taxiDriverMapper.taxiDriverInfo(driverNo));
		}
		
		System.out.println(data);
		retMap.put("code","777");
		retMap.put("feed", data);
		return  retMap ; 
	}
	@Override
	public Map<String, Object> cancelBooking(Map<String, Object> param) throws Exception{
	
		String accesstoken = (String)param.get("accesstoken");						 
		Map<String, Object> ret = Common.check_accesstoken(accesstoken);
		int code = (int)ret.get("code");
		if(code<2) return ret;
		
		String customerNo = (String)ret.get("customerNo");
		int customerType = (int)ret.get("customerType");		
		String reserveNo = (String)param.get("reserveNo");
		String startDay  = (String)param.get("startDay");
		
		String strCode="",  message="";
		Map<String, Object> retMap = new HashMap<String,Object>();
//state =>  0: 예약대기  1 : 예약확정  2: 예약취소    3: 예약종료     5: 승차거부  6: 차량미배차		
//		Booking booking = bookingDao.findBookingByNo(reserveNo);
		Map<String, Object> booking = bookingDao.findBookingByNo(reserveNo);
		if(booking==null) {
			retMap.put("code", "555");
			retMap.put("message", "not found booking:" + reserveNo);
			return retMap;
		}
System.out.println("cancelBooking:" + booking.toString());

		int noti_mode = 2;  //delete
		int bookingState = Integer.parseInt((String)booking.get("state"));
//		if( booking.getState() ==0 )  {  // 기사 수락전예약은 삭제
		if( bookingState ==0 )  {  // 기사 수락전예약은 삭제
			int rcount = bookingDao.deleteBooking(reserveNo);
			if( rcount==1 ) {
				strCode = "888"; message =  "delete booking successfully";	
				
				String reserveTime = (String)param.get("reserveTime");
				String startAddress = (String)param.get("startAddress"); 
				int groupNo = -1;
				send_noti_bookingcall (customerNo,customerType, Integer.parseInt(reserveNo), 0, startDay, noti_mode,
										reserveTime, startAddress,groupNo ) ;
				if(customerType==9) { // 무료사용자 예약정보 삭제 
					Map<String,Object> sparam = new HashMap<String, Object>(); 
					sparam.put("reserveType", 0);
					sparam.put("reserveNo", Integer.parseInt(reserveNo));
					sparam.put("customerNo", customerNo);
					ServiceDao.delete_social_booking(sparam);
				}
			}
			else {
				strCode = "666"; message =  "delete booking error!";	
			}
		}
//		else if( booking.getState() ==1 ) { //예약 확정 후 취소
		else if( bookingState ==1 ) { //예약 확정 후 취소
			String sX = (String)param.get("sX");
			String sY = (String)param.get("sY");
			String driverNo  = (String)param.get("driverNo");
			int rcount = cancel_accepted_booking(Integer.parseInt(reserveNo), 0, startDay,sX,sY,driverNo,customerNo);
			if(rcount==1) {
				strCode = "788"; message = "cancel booking successfully";  
			}
		}
		
		String newtoken = Common.update_accesstoken(accesstoken);
		retMap.put("accesstoken", newtoken);
		retMap.put("code",strCode);
		retMap.put("message", reserveNo);
System.out.println("cancelBooking:" + retMap.toString());		
		return  retMap;
	}
	
	@Override
	public Map<String, Object> cancel_socialBooking(Map<String, Object> param) throws Exception{
		
		String customerNo = (String)param.get("customerNo");
		int customerType = (int)param.get("customerType");		
		String reserveNo = (String)param.get("reserveNo");
		String startDay  = (String)param.get("startDay");
		String strCode="",  message="";
		
		Map<String, Object> retMap = new HashMap<String,Object>();
//state =>  0: 예약대기  1 : 예약확정  2: 예약취소    3: 예약종료     5: 승차거부  6: 차량미배차		
		Map<String, Object> booking = bookingDao.findBookingByNo(reserveNo);
		if(booking==null) {
			retMap.put("code", "555");
			retMap.put("message", "not found booking:" + reserveNo);
			return retMap;
		}
System.out.println("cancelBooking:" + booking.toString());

		int noti_mode = 2;  //delete
		int bookingState = Integer.parseInt((String)booking.get("state"));
		if( bookingState ==0 )  {  // 기사 수락전예약은 삭제
			int rcount = bookingDao.deleteBooking(reserveNo);
			if( rcount==1 ) {
				strCode = "888"; message =  "delete booking successfully";	
				
				String reserveTime = (String)param.get("reserveTime");
				String startAddress = (String)param.get("startAddress"); 
				int groupNo = -1;
				send_noti_bookingcall (customerNo, customerType,Integer.parseInt(reserveNo), 0, startDay, noti_mode,
										reserveTime, startAddress,groupNo ) ;
				
				if(customerType==9) { // 무료사용자 예약정보 삭제 
					Map<String,Object> sparam = new HashMap<String, Object>(); 
					sparam.put("reserveType", 0);
					sparam.put("reserveNo", Integer.parseInt(reserveNo));
					sparam.put("customerNo", customerNo);
					ServiceDao.delete_social_booking(sparam);
				}
			}
			else {
				strCode = "666"; message =  "delete booking error!";	
			}
		}
		else if( bookingState ==1 ) { //예약 확정 후 취소
			System.out.println("예약취소 테스트중");
			System.out.println(customerNo+","+reserveNo);
			Map<String,Object> sxData = bookingDao.bookingInfo(customerNo,reserveNo);
			System.out.println(sxData);
			String sX = (String)sxData.get("startLocXenc");
			String sY = (String)sxData.get("startLocYenc");
			String driverNo  = (String)param.get("driverNo");
			int rcount = cancel_accepted_booking(Integer.parseInt(reserveNo), 0, startDay,sX,sY,driverNo,customerNo);
			if(rcount==1) {
				strCode = "788"; message = "cancel booking successfully";  
			}
		}
		retMap.put("code",strCode);
		retMap.put("message", reserveNo);
System.out.println("cancelBooking:" + retMap.toString());		
		return  retMap;
	}
	@Override
	public int getMaxBookingNo() throws Exception{
		return bookingDao.getMaxBookingNo();
	}
	
	private int cancel_accepted_booking(int reserveNo, int reserveType,String startDay, 
										String sX,String sY,String driverNo, String customerNo) throws Exception {
		int caseType=52;   //비정기예약 취소
		if(reserveType==1) caseType = 54;
		int penalty_don = 2000;  //2000원
		
		int rcount=0;
		Map<String,Object> dataMap = new HashMap<String, Object>();
		dataMap.put("caseType", caseType);
		dataMap.put("reserveNo", reserveNo);
		dataMap.put("startDay", startDay);
		dataMap.put("customerNo", customerNo);
		dataMap.put("state", 2);
		if(reserveType==0) rcount = bookingDao.cancelAcceptedBooking(dataMap);
		else rcount = reserveDao.cancelAcceptedReservation(dataMap);
		
		// insert customer history
		Map<String, String> encLoc1 = Common.encodeLoc(sX, sY);
		String encSX = encLoc1.get("locX");
		String encSY = encLoc1.get("locY");
		
		Map<String,Object> statusMap = new HashMap<String, Object>(); 
		statusMap.put("caseType", caseType );
		statusMap.put("penalty_don", penalty_don );
		statusMap.put("driverNo", driverNo);
		statusMap.put("callNo", reserveNo );
		statusMap.put("reserveNo", reserveNo );
		statusMap.put("reserveType", "0" );
		statusMap.put("reserveDate", startDay);
		statusMap.put("customerNo", customerNo );
		statusMap.put("encLat", encSX);
		statusMap.put("encLon", encSY); 
		historyCustDao.insertCustomerHistory(statusMap);
		
		return rcount;
	}
/////////////////////////////////////////////////////////////////////////////////////////////////////
	private void send_noti_bookingcall (String customerNo,int customerType,int reserveNo,int reserveType,
										String startDay, int noti_mode,
										String reserveTime, String sAddr, int groupNo) throws Exception {
//  noti_mode => 0: insert 1:update  2:delete	
System.out.println("send_noti_bookingcall:" + noti_mode + "/" + customerNo + "/" + sAddr);	
		Map<String,Object> param = new HashMap<String, Object>(); 
		
		if(noti_mode>=1) {
			String sDay=null;
			if(reserveType==1) sDay = startDay;
			
			param.put("reserveType", reserveType );
			param.put("reserveNo", reserveNo );
			param.put("startDay", sDay); 
			ServiceDao.delete_booking_noti(param);
		}
		
		if(noti_mode==2) return;
		
		
		List<String> driverlist = find_proper_drivers_No(customerNo,customerType,sAddr,groupNo, startDay, reserveTime);
		for(int i=0; i < driverlist.size();  i++ ) {
			String driverNo = driverlist.get(i);
System.out.println("send_noti_bookingcall:" + driverNo);	

			param.clear();
			param.put("reserveType", reserveType );
			param.put("reserveNo", reserveNo );
			param.put("startDay", startDay); 
			param.put("customerNo", customerNo );
			param.put("driverNo", driverNo );
			ServiceDao.insert_booking_noti(param);
		}
	}
	
	public List<String> find_proper_drivers_No(String customerNo, int customerType,String startAddress, int groupNo, 
												String startDay, String reserveTime) throws Exception {
		List<String> drivers = new ArrayList<String>();
		
		String rDate = startDay + " " + reserveTime;
        SimpleDateFormat fDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        Date reserveDate =null;
        try {
			reserveDate = fDate.parse(rDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        Map<String,Object> param = new HashMap<String, Object>(); 
        
        List<Map<String,String>> proper_driverlist = new ArrayList<Map<String,String>>();
		if( groupNo > 0 ) {
			
			param.clear();
			param.put("customerNo", customerNo );
			param.put("groupNo", groupNo );			
			String driversinfo = ServiceDao.get_group_drivers(param);			
			String[] gr_drivers = driversinfo.split("\\^");
//			proper_driverlist = new ArrayList<String>(Arrays.asList(gr_drivers));

			for( int i=0; i<gr_drivers.length; i++ ) {
				String driverNo = gr_drivers[i];
				System.out.println("driverNo="+driverNo);
				if(!driverNo.isEmpty()) {
					System.out.println("driverNo2="+driverNo);
					String dType = ServiceDao.get_driver_type(driverNo);  // 4무료봉사자 체크
					System.out.println("dType="+dType);
					Map<String,String> dr_map = new HashMap<String,String>();
					dr_map.put("driverNo", driverNo);
					dr_map.put("taxi_type", dType);
					proper_driverlist.add(dr_map);
					System.out.println("proper_driverlist="+proper_driverlist);
				}
			}  
		}
		else {
			String area_code = Utils.get_area_code(startAddress);
			
			param.clear();
			param.put("customerNo", customerNo );
			param.put("areacode", area_code );		
			proper_driverlist =  ServiceDao.get_proper_drivers(param);
System.out.println("area_code:" + area_code + "proper_driverlist cnt" + proper_driverlist.size());			
		}
		
		for( int i=0; i<proper_driverlist.size(); i++ ) {
			Map<String,String> dr_map = proper_driverlist.get(i);
			String driverNo = dr_map.get("driverNo");
			System.out.println();
			int taxi_type = Integer.parseInt(dr_map.get("taxi_type"));
			if( driverNo.isEmpty()) continue;	
			
			param.clear();
			param.put("driverNo", driverNo );
			param.put("reserveDate", reserveDate );	
			
			if(customerType!=9) {
				if( ServiceDao.isOffday(param)>0 ) continue;
			}
			if( ServiceDao.isCanceledDriver(driverNo, startDay, reserveTime, customerNo)>0) continue;
			if( isReservedTime(driverNo, startDay, reserveTime)>0 ) continue;
			
			if(taxi_type !=2 || customerType==9) { // 자가용무료봉사자는 customerType=9 일때만 예약받음 
System.out.println("proper_driver sel:" + driverNo);				
				drivers.add(driverNo);
			}
		}
		
		return drivers;
	}
	//이미 수락된 예약의 예약시간 < reserveTime + (출발~도착거리)/20Km/h +30분 이면 예약시간중으로 처리 예약못받음  (=return 1)
	public int isReservedTime(String driverNo, String startDay, String reserveTime) throws Exception {
		
		Calendar c = Calendar.getInstance();
		
		String rDate = startDay + " " + reserveTime;
        SimpleDateFormat fDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        Date reserveDate =null;
        try {
			reserveDate = fDate.parse(rDate);
	        c.setTime(reserveDate);
	        
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<Map<String,String>> locations = ServiceDao.getReservedLocation( driverNo, startDay, reserveTime);
		
		for(int i=0; i<locations.size(); i++ ) {
			Map<String, String> loc = locations.get(i);
			Map<String, String> sXY = Common.decodeLoc(loc.get("startLocX"),loc.get("startLocY"));
			Map<String, String> eXY = Common.decodeLoc(loc.get("finishLocX"),loc.get("finishLocY"));
			
			double lat1 = Double.parseDouble((String)sXY.get("locY"));  // 출발지 위치 
			double lon1 = Double.parseDouble((String)sXY.get("locX"));
			double lat2 = Double.parseDouble((String)eXY.get("locY"));  // 도착지 위치 
			double lon2 = Double.parseDouble((String)eXY.get("locX"));
			double kilometer = (int)Utils.calc_distance( lat1, lon1, lat2, lon2, "K");
			int drivingSecond = (int)( (kilometer/20.0)*60*60 + 30*60 );
			
			c.add(Calendar.SECOND, drivingSecond);
			Date estimated = c.getTime();
			
			if( reserveDate.compareTo(estimated) <= 0 ) return 1;
		}
		return 0;
	}
///////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public List<Map<String,Object>>taxiReserTodayCount(Map<String, Object> param) throws Exception {
		
		return ServiceDao.taxiReserTodayCount(param);
	}
	@Override
	public List<Map<String, Object>> userCustomerNoList(String email) throws Exception {
		return  ServiceDao.userCustomerNoList(email);
	}
	
	

}
