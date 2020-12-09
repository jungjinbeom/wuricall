package com.wurigo.socialService.commons;


import java.io.IOException;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.wurigo.socialService.dao.UserMapper;
import com.wurigo.socialService.securty.WURI_Security;


@Component
//@Scope(scopeName = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class Common  {  //implements InitializingBean, DisposableBean
	
	@Autowired
	private static UserMapper userMapper;
	@Autowired
	private static WURI_Security m_security;
	
	public Common() {
		m_security = new WURI_Security();
		
System.out.println("Common Constructor!");		
	}
/*	
	@Override
	public void afterPropertiesSet() throws Exception {
		// 빈 초기화 시 코드 구현
//		String aa = (String)constDao.getConstantByName("call_fee");
System.out.println("afterPropertiesSet:" );	
	} 
	@Override
	public void destroy() throws Exception {
		// 빈 소멸 시 코드 구현
System.out.println("destroy");			
	} */
/*
	public static void generate_RSA_keypair() throws Exception {
		String RSA_PUBLIC_KEY = constDao.getConstantByName("Client_publicKey");
		 
		String RSA_PRIVATE_KEY = constDao.getConstantByName("Client_privateKey");

		WURI_Security.generate_keypair(RSA_PUBLIC_KEY,RSA_PRIVATE_KEY);  
	}*/
//	public static Map<String, Object> check_accesstoken(String accesstoken) throws Exception {
//		
///*		try {
//			WURI_Security.generate_keypair();
////			generate_keypair();
//		} catch (Exception e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		} */
//		Map<String, Object> r_map = m_security.check_accesstoken(accesstoken);
//		
//		int code = (int)r_map.get("code");
//		if(code <= 0) {
//			if(code==0) r_map.put("message", "로그인 사용시간을 초과했습니다.다시 로그인하세요.");
//			else r_map.put("message", "ERROR!");
//				
//			return r_map;   // code=-1: error  0: timeout
//		}
//		String usertoken = (String)r_map.get("usertoken");
//System.out.println("usertoken: " +usertoken);	
//
//	userMapper = (UserMapper)getBean("userMapper");
////		ApplicationContext context = new AnnotationConfigApplicationContext("com.wuricall.wurigo");
////		custDao = context.getBean(CustomerDAO.class);
//
//		UserVO user = userMapper.findByToken(usertoken);		
//		if(user==null) {
//			r_map.put("code", 1);  // not exist user
//			r_map.put("message", "존재하지 않는 사용자입니다.");
//		}
//		else {
//			r_map.put("code", 2); 
//			r_map.put("customerNo", user.getCustomerNo()); 
//			r_map.put("customerType", user.getCustomerType()); 
//		}
//		return r_map; 
//	}
//check_accesstoken와 pair로 사용해야함 because WURI_Security.generate_RSA_keypair()	
	public static String update_accesstoken( String accesstoken) {
		return m_security.update_accesstoken(accesstoken);
	}
	
	public static Map<String, String> encodeLoc(String coordX, String coordY) throws java.security.InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {		 
        
        Map<String, String> result =  m_security.encodeLoc(coordX, coordY);
        return result;
	}
	public static Map<String, String> decodeLoc(String encX, String encY) throws java.security.InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		Map<String, String> result =  m_security.decodeLoc(encX, encY);
        return result;
	}
	public static Map<String, String> decodePHPLoc(String encX, String encY) throws IOException {
		 
		Map<String, String> map = m_security.decodePHPLoc(encX,encY);
        return map;
	}
		
	public static Map<String, String> encodePHPLoc(String locX, String locY) throws IOException {
		Map<String, String> map = m_security.encodePHPLoc(locX,  locY);
        return map;
	}
	
	public static String make_access_token(String hash) {
		return m_security.make_access_token(hash);
	}
	public static String make_user_token() {
		return m_security.make_user_token();
	}
	
	public static Object getBean(String beanId) { 
		ApplicationContext applicationContext = AppContextProvider.getApplicationContext(); 
		if( applicationContext == null ) { 
			throw new NullPointerException("Spring의 ApplicationContext초기화 안됨"); 
		} 
/*		String[] names = applicationContext.getBeanDefinitionNames(); 
		for (int i=0; i<names.length; i++) { 
			System.out.println(names[i]); 
		} */
		 
		
		return applicationContext.getBean(beanId); 
	}

}
