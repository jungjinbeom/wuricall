package com.wurigo.socialService.security;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.management.openmbean.InvalidKeyException;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

//import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
@Component
public class WURI_Security   {
	
	
	//키가 스트링으로 오기 때문에 바이트로 바꿔줘야함
    private static String RSA_PUBLIC_KEY = "-----BEGIN PUBLIC KEY-----\n" + 
    		"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAp4VJvSRX2jkyh4azV+MY\n" + 
    		"kDgXll+3DwRS7obpghrAh5iO4loWXPxBh0pUZL/jOYTRLTn496/jgoJIpKQPggfR\n" + 
    		"VGVb0jIj9Vg9molE4A0HHccqKmqlW6gYaV1Ga6C2PRAwIBSBbr9sjFF1K8bvtOtR\n" + 
    		"1fjjBl9e55aZko3S+YuaCyD/tMxySNTOYTaJqQrfq3hCn/M4hBCzAK38wt3MYOjp\n" + 
    		"zcQkFsoylrA4Gsl2t+OgpWwfqQ/ZMPJ9SRxhqOqPlMzKJrXHAioB+8kh503kPYhC\n" + 
    		"W7TPFIKdhlG8cz3+fquAnyWEcYegZoDalbbHLS1lJsW7MnB0MIkuVgdA3O8+DieT\n" + 
    		"7QIDAQAB\n" + 
    		"-----END PUBLIC KEY-----\n" + 
    		"";
    private static String RSA_PRIVATE_KEY = "-----BEGIN PRIVATE KEY-----\n" + 
			"MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCnhUm9JFfaOTKH\n" + 
			"hrNX4xiQOBeWX7cPBFLuhumCGsCHmI7iWhZc/EGHSlRkv+M5hNEtOfj3r+OCgkik\n" + 
			"pA+CB9FUZVvSMiP1WD2aiUTgDQcdxyoqaqVbqBhpXUZroLY9EDAgFIFuv2yMUXUr\n" + 
			"xu+061HV+OMGX17nlpmSjdL5i5oLIP+0zHJI1M5hNompCt+reEKf8ziEELMArfzC\n" + 
			"3cxg6OnNxCQWyjKWsDgayXa346ClbB+pD9kw8n1JHGGo6o+UzMomtccCKgH7ySHn\n" + 
			"TeQ9iEJbtM8Ugp2GUbxzPf5+q4CfJYRxh6BmgNqVtsctLWUmxbsycHQwiS5WB0Dc\n" + 
			"7z4OJ5PtAgMBAAECggEAME49Dy91rdWkfnQXLxarNJsYIiKDlO74QxPgLcYtEmyp\n" + 
			"dxfnLvUGqE6Eu3ChwypLbzEyW6n6ft22uNMeLa63bBC2Q4x11f+V9AP1Om5HSQzv\n" + 
			"Wb/a4u077NYawlpbcoxCdF21XQwR7vxmPzNkV9Urifn07Rd/NGS28A25coAQ8Xgh\n" + 
			"DJbq6xRra9F7ylYeG1IySR/+3jXm3ja51V2R6GZ/Bq6GHB+LhRA5ebMWO01POmTw\n" + 
			"3ZFY4s8NmVRuQ7Ua6pxNhSWZbz4fcaTFqFM68s7ZiE4/92ToEyegwf8QRh4/Uf0V\n" + 
			"s2WCdMxBRMu9pqOb0qEdhCi8zM3ONYzz+EqE04YSwQKBgQDUMALZlvoYWbyojtcx\n" + 
			"oLCPcojZdZBJwkidWWckZS77bWn7rYld0Y77ltcA4hgqJzgQw2pbwcIR0hxPEeqG\n" + 
			"f0a39C3zF2yvksuChJ2I1dSib5hr6hK6sEWEp5o4CdEXPp3/m94hhqQaSq7xTrq8\n" + 
			"S+geJKxsgKc6UKtIso3zxK1nVQKBgQDKHDyctFsY3E4TVbndU6DoiEfvur9OVsj3\n" + 
			"GngMOiSFpY2mi+jdmBP0H9vPxyu+WN3+jZ53f7dx4XKShS59v+TXCL0AzF/el2zE\n" + 
			"TFNGpsbGUwMpB4eZd8u8/SQHaLViI9zyXUW2+sF+r+kbKs4zMAhkHVBGytve5bL4\n" + 
			"jhohGvlKOQKBgE5tHHCOJAwiBuAKQ7GdIgUzIS+3C0in3a0C4WOLXo0hyL10S8R4\n" + 
			"cy+8deVmq+XQryFLCSG3AOMTVNFI3vUtT9coEl+6NbrDOCManxt3ZWtQcfbZAmks\n" + 
			"Q9WfGRpDTpwX0dArLTQZDsiE+BpKrixwpe+WQICuIKEmNdjd3ifOdwUtAoGANNrr\n" + 
			"kwOrqzW6CV5q7BG5y/YP8gbBjwgtKEo4yOHPuU/RWpPOQ11JIpfOUWlB7FarP63j\n" + 
			"mlbBL7wIzX6c6O/tNbsoSnQoQjsRi8h2IszswOLmua0pbh4hHvyIlpNwlAjS+GMi\n" + 
			"Hzc2sR3p9Vvdbw+JsjzkSI82rvUdN9lZxfya4ykCgYEAteIBfrCdGXo3kEd3S06R\n" + 
			"5CciPiXly5K1XM78EpDW986V3/JoCQcpjsYJLBhb33POY4F8DtoaTHASGjuqOPkV\n" + 
			"55W6Vb77Yt8wGjIbayz91iOYNTU8nc7DAnIqNKEHqBRZQHYE+pmeQ76jRPhYefpq\n" + 
			"vv0Y6ZrtI21gcg0lFEgQYH0=\n" + 
			"-----END PRIVATE KEY-----\n" + 
			"";   //

	
    private static byte pbUserKey[]	= { (byte)0x4A, (byte)0x65, (byte)0x73,(byte)0x75,(byte)0x73, (byte)0x20, (byte)0x6C,
			(byte)0x6F,(byte)0x76,(byte)0x65, (byte)0x73,(byte)0x20, (byte)0x6D,(byte)0x65, (byte)0x21,(byte)0x21}; 

    private static byte pbIV[]	= {(byte)0x6C,(byte)0x6F, (byte)0x76,(byte)0x65, (byte)0x20,(byte)0x6F, (byte)0x6E,(byte)0x65,
			(byte)0x20,(byte)0x61, (byte)0x6E,(byte)0x6F,(byte)0x74, (byte)0x68,(byte)0x65, (byte)0x72 };
	
    final static String secretKey =  "4920616d207769746820796f7520616c77617973";	
//    static AES256Util aes256 =null;  
    
	private static PublicKey publicKey=null;
	private static PrivateKey privateKey=null;
	
	public WURI_Security() {
		generate_keypair();
	}
	
	
// 	홈페이지에서 회원가입시 입력한 비밀번호로 암호화된 비밀번호 생성 	
	public static String create_encrypted_password(String plainPasswd) {  
		
		String encPasswd = encrypt_RSA(plainPasswd);
System.out.println("encPasswd : " + encPasswd );		
		return encPasswd;
	}
	
// 앱에서 전달된 암호화된 password나 홈피에 만들어진 암호화된 password를 가지고 테이블에 저장할 암호화된 비밀번호 생성 	
	public static String make_encrypted_password(String encPasswd) {
		
		String dbPasswd = generate_encrypted_dbpassword(encPasswd);
System.out.println("make_encrypted_password : " + dbPasswd );		
		return dbPasswd;
	}
	
//암호화된 비밀번호를 복호화해서 다시 암호화한후 디비에 저장된 값과 비교 	
	public static boolean verify_password(String encPasswd, String dbPasswd) {
		
		String resultPwd = generate_encrypted_dbpassword(encPasswd);
		boolean result = resultPwd.equals(dbPasswd);
		 
System.out.println("verify_password : " + result );			
		return result;
	}
		
	public static String generate_encrypted_dbpassword(String encPasswd) {
		// 복호화 합니다.
        String decrypted="";
		try {
			decrypted = decryptRSA(encPasswd, privateKey);
System.out.println("******* decrypted : " + decrypted);			
		} catch (InvalidKeyException | java.security.InvalidKeyException | NoSuchPaddingException
				| NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException
				| UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


        // 암호화해서 table password2와 비교 
        byte[] plainbyte = decrypted.getBytes(StandardCharsets.UTF_8);        
        byte[] pszDigest =null;
        pszDigest = new byte[100]; 
        Arrays.fill(pszDigest, (byte)'\u0000'); 
              
        KISA_SHA256.SHA256_Encrpyt( plainbyte, plainbyte.length, pszDigest );
System.out.println("******* pszDigest : " + pszDigest);	
		
		String dbPassword = bin2hex(pszDigest) ;
		dbPassword = dbPassword.replaceAll("\u0000", "");
System.out.print("@@@@@@[" + dbPassword + "]/" + pszDigest.length + "\n");
		return dbPassword;
	}
	
	public static String encrypt_RSA( String plainText)  {
        String encText = "";
        try {
        	
            Cipher cipher = Cipher.getInstance("RSA"); //("RSA/None/PKCS1Padding");            
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            //byte로 바꿔준다
            byte[] encodedStr = plainText.getBytes(StandardCharsets.UTF_8); //("utf-8");.getBytes(StandardCharsets.UTF_8)
            byte[] cipherStr = cipher.doFinal(encodedStr);
//            encText = Base64.encode(cipherStr); 
            encText = Base64.getEncoder().encodeToString(cipherStr);//, Base64.DEFAULT); 

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (java.security.InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return encText;
 //import org.apache.tomcat.util.codec.binary.Base64;       
    }
/*	
	public static void generate_RSA_keypair() throws Exception {
//		String RSA_PUBLIC_KEY = constDao.getConstantByName("Client_publicKey");		 
//		String RSA_PRIVATE_KEY = constDao.getConstantByName("Client_privateKey");
//System.out.println("RSA_PUBLIC_KEY:" + RSA_PUBLIC_KEY);

		generate_keypair(RSA_PUBLIC_KEY,RSA_PRIVATE_KEY);  
	}
*/	
	public static void generate_keypair() { //String RSA_PUBLIC_KEY,String RSA_PRIVATE_KEY) {

        String privateKeyContent = RSA_PRIVATE_KEY.replaceAll("\\n", "").replace("-----BEGIN PRIVATE KEY-----", "").replace("-----END PRIVATE KEY-----", "");
        String publicKeyContent = RSA_PUBLIC_KEY.replaceAll("\\n", "").replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", "");;

        KeyFactory kf=null;
		try {
			kf = KeyFactory.getInstance("RSA");
		} catch (NoSuchAlgorithmException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
//		byte[] prk_decoded = Base64.decode(privateKeyContent);
		byte[] prk_decoded = Base64.getDecoder().decode(privateKeyContent.getBytes(StandardCharsets.UTF_8));
        PKCS8EncodedKeySpec keySpecPKCS8 = new PKCS8EncodedKeySpec(prk_decoded);
        
		try {
			privateKey = kf.generatePrivate(keySpecPKCS8);
		} catch (InvalidKeySpecException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

//		byte[] puk_decoded = Base64.decode(publicKeyContent);
		byte[] puk_decoded = Base64.getDecoder().decode(publicKeyContent.getBytes(StandardCharsets.UTF_8)); //, Base64.DEFAULT
        X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(puk_decoded); 
         
        
		try {
			publicKey = kf.generatePublic(keySpecX509);
		} catch (InvalidKeySpecException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	/**
     * Private Key로 RAS 복호화를 수행합니다.
     * @param encrypted 암호화된 이진데이터를 base64 인코딩한 문자열 입니다.
     * @param privateKey 복호화를 위한 개인키 입니다.
     * @return
	 * @throws java.security.InvalidKeyException 
     * @throws Exception
     */
    public static String decryptRSA(String encrypted, PrivateKey privateKey)
    		throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
    		         BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException, java.security.InvalidKeyException {

    	byte[] byteEncrypted = Base64.getDecoder().decode(encrypted.getBytes(StandardCharsets.UTF_8));
    	 //       byte[] byteEncrypted = Base64.decode(encrypted);
    	
        Cipher cipher = Cipher.getInstance("RSA");
//        Cipher cipher = Cipher.getInstance("RSA/None/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        byte[] bytePlain = cipher.doFinal(byteEncrypted);

        String decrypted = new String(bytePlain, StandardCharsets.UTF_8); //"utf-8");
        return decrypted;

    } 
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////    
    public static String bin2hex(byte[] arr) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < arr.length; i++) {
        	
        	if( arr[i]== (byte)'\u0000' ) continue;  /// add sunny
        	
            String str = Integer.toHexString((int) arr[i]);
            if (str.length() == 2)
                sb.append(str);
            if (str.length() < 2) {
                sb.append("0");
                sb.append(str);
            }
            if (str.length() > 2)
                sb.append(str.substring(str.length() - 2));
        }
        return sb.toString();
    }
	
	public static byte[] parseHexBinary(String s) {
	    final int len = s.length();

	    // "111" is not a valid hex encoding.
	    if( len%2 != 0 )
	        throw new IllegalArgumentException("hexBinary needs to be even-length: "+s);

	    byte[] out = new byte[len/2];

	    for( int i=0; i<len; i+=2 ) {
	        int h = hexToBin(s.charAt(i  ));
	        int l = hexToBin(s.charAt(i+1));
	        if( h==-1 || l==-1 )
	            throw new IllegalArgumentException("contains illegal character for hexBinary: "+s);

	        out[i/2] = (byte)(h*16+l);
	    }

	    return out;
	}

	private static int hexToBin( char ch ) {
	    if( '0'<=ch && ch<='9' )    return ch-'0';
	    if( 'A'<=ch && ch<='F' )    return ch-'A'+10;
	    if( 'a'<=ch && ch<='f' )    return ch-'a'+10;
	    return -1;
	}

	private static String generateRandom(int len) {
		
		String seedChar= "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#%^&*-_+=";
		int seedlen = seedChar.length()-1;
		
		Random rand = new Random();

		String genStr = "";
		for(int i = 0; i <= len; i++) {

		    int iValue = rand.nextInt(seedlen);  // 0 <= iValue < seedlen
		    genStr += seedChar.charAt(iValue);
		}
		return genStr;
	}
//////////////////////////////////////////////////////////////////////////////
	
	
	public static String make_user_token() {
		
		String genStr = generateRandom(16);
System.out.println("make_user_token: " + genStr);
	    
	    byte[] inputBytes = genStr.getBytes(StandardCharsets.UTF_8);  
	    int genLen = inputBytes.length;
	    
	    byte[] CipherText = KISA_SEED_CBC.SEED_CBC_Encrypt(pbUserKey, pbIV, inputBytes, 0, genLen);
	    String token = bin2hex(CipherText);
	    
	    return token;
	}
	
	public static String make_access_token(String hash) {
		
//		Date from = new Date();
//	    SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//	    String curT = transFormat.format(from); 
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); 
		String curT = LocalDateTime.now().format(formatter);
		
	    String token = hash + " " + curT;
	    
	    byte[] tokenBytes  = token.getBytes();  
	    int tokenLen = tokenBytes.length;
	    
	    byte[] CipherText = KISA_SEED_CBC.SEED_CBC_Encrypt(pbUserKey, pbIV, tokenBytes, 0, tokenLen);
	    String accesstoken = bin2hex(CipherText);
	    
	    return accesstoken;
	}

	public static Map<String,Object> check_accesstoken(String accesstoken) {
		Map<String,Object> result = new HashMap<String, Object>();
		int code = -1;	
		String usertoken = "";
		
		
		byte[] accessBytes = parseHexBinary(accesstoken);
	    byte[] retBytes = KISA_SEED_CBC.SEED_CBC_Decrypt(pbUserKey, pbIV, accessBytes, 0, accessBytes.length);
	System.out.println("accessBytes="+accessBytes);
	System.out.println("retBytes="+retBytes);
	    String atoken = new String(retBytes, StandardCharsets.UTF_8);
	    
System.out.println("SEED_CBC_Decrypt: " + atoken);

		int pos = atoken.indexOf(' ');
		if( pos < 0 ) {
			result.put("code", code);
			result.put("usertoken", usertoken);
			return result ;
		};
		
		String lastDate = atoken.substring(pos+1); // 받아온 accesstoken을 만든 시간을 구한다 
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); 
		LocalDateTime timeoutT  = LocalDateTime.parse(lastDate, formatter).plusHours(12); 
		LocalDateTime curT = LocalDateTime.now();
//System.out.println("timeoutT: " + timeoutT.toString() + " curT: " + curT.toString() );
		if( curT.compareTo(timeoutT) >= 0 ) {
			code = 0; // timeout					
		}				
		else {
			usertoken = atoken.substring(0, pos);
			code = 1;						
		}
		result.put("code", code);
		result.put("usertoken", usertoken);
		return result ;
	}
	
	public static String update_accesstoken( String accesstoken) {
		byte[] accessBytes = parseHexBinary(accesstoken);
	    byte[] retBytes = KISA_SEED_CBC.SEED_CBC_Decrypt(pbUserKey, pbIV, accessBytes, 0, accessBytes.length);
	    String atoken = new String(retBytes, StandardCharsets.UTF_8);
	    
	    String newtoken="";
	    int pos = atoken.indexOf(' ');
	    if( pos>0 ) {
	    	String hash = atoken.substring(0, pos);
	    	
	    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); 
			String curT = LocalDateTime.now().format(formatter);
			
		    String token = hash + " " + curT;
		    
		    byte[] tokenBytes  = token.getBytes();  
		    int tokenLen = tokenBytes.length;
		    
		    byte[] CipherText = KISA_SEED_CBC.SEED_CBC_Encrypt(pbUserKey, pbIV, tokenBytes, 0, tokenLen);
		    newtoken = bin2hex(CipherText);
	    }	    
	    return newtoken;	    
	}
	/*	
	public static boolean verify_accesstoken(String accesstoken, String usertoken) {
System.out.println("verify_accesstoken");
	    byte[] accessBytes = parseHexBinary(accesstoken);
//	    byte[] userBytes = parseHexBinary(usertoken);
//	    String utoken = new String(userBytes, StandardCharsets.UTF_8);
	    
	    byte[] retBytes = KISA_SEED_CBC.SEED_CBC_Decrypt(pbUserKey, pbIV, accessBytes, 0, accessBytes.length);
//	    String atoken = bin2hex(retBytes);
	    
	    String atoken = new String(retBytes, StandardCharsets.UTF_8);
System.out.println("SEED_CBC_Decrypt: " + atoken);		
System.out.println("SEED_CBC_Decrypt: " + usertoken);

		int pos = atoken.indexOf(' ');
		if( pos < 0 ) return false;
		String hash = atoken.substring(0, pos);
		
		if(hash.equals(usertoken)) return true;
		
		return false;
	}
*/	
/////////////////////////////////////////////////////////////////////////////////////////////
	public static Map<String, String> encodeLoc(String coordX, String coordY) throws java.security.InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		if(coordX.isEmpty() && coordY.isEmpty()) return null;
        
        AES256Util aes256 = new AES256Util();
        String encX = aes256.aesEncode(coordX);
        String encY = aes256.aesEncode(coordY);
        
        Map<String, String> result = new HashMap<String,String>();
        result.put("locX", encX);
        result.put("locY", encY);
		
        return result;
	}
	public static Map<String, String> decodeLoc(String encX, String encY) throws java.security.InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		
		if(encX.isEmpty() && encY.isEmpty()) return null;
		
		AES256Util aes256 = new AES256Util();
        String coordX = aes256.aesDecode(encX);
        String coordY = aes256.aesDecode(encY);

        Map<String, String> result = new HashMap<String,String>();
        result.put("locX", coordX);
        result.put("locY", coordY);
		
        return result;
	}
///////////////////////////////////////////////////////////////////////////////////////////////
// startLocXenc,startLocYenc, finishLocXenc, finishLocYenc 값을 받아서 decodeLoc.php를 통해서 복호화한 값을 가져온다 	
	public static Map<String, String> decodePHPLoc(String encX, String encY) throws IOException {
		ObjectMapper mapper = new ObjectMapper(); 
		Map<String, String> map = null;
		 
		String addr = "https://dev.wuricall.com/modugoClient/decodeLoc.php?encLocX="+encX+ 
						"&encLocY="+encY + "&type=decode";
//System.out.println(addr);	

        URL url = new URL(addr);//your url i.e fetch data from .
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("Failed : HTTP Error code : "
                    + conn.getResponseCode());
        }
        InputStreamReader in = new InputStreamReader(conn.getInputStream());
        BufferedReader br = new BufferedReader(in);
        String output;
        while ((output = br.readLine()) != null) {
        //    System.out.println(output); 
            map = (Map<String,String>)mapper.readValue(output, Map.class); 
        }
        conn.disconnect();
        
//        String coordX = map.get("locX");  String coordY = map.get("locY");
//System.out.println("decodeLoc:" + map);         
        return map;
	}
//startLocXenc,startLocYenc, finishLocXenc, finishLocYenc에  decodeLoc.php를 통해서 암호화한 값을 저장한 		
	public static Map<String, String> encodePHPLoc(String locX, String locY) throws IOException {
		ObjectMapper mapper = new ObjectMapper(); 
		Map<String, String> map = null;
		 
		String addr = "https://dev.wuricall.com/modugoClient/decodeLoc.php?encLocX="+locX+ 
						"&encLocY="+locY + "&type=encode";
//System.out.println(addr);	

        URL url = new URL(addr);//your url i.e fetch data from .
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("Failed : HTTP Error code : "
                    + conn.getResponseCode());
        }
        InputStreamReader in = new InputStreamReader(conn.getInputStream());
        BufferedReader br = new BufferedReader(in);
        String output;
        while ((output = br.readLine()) != null) {
        //    System.out.println(output); 
            map = (Map<String,String>)mapper.readValue(output, Map.class); 
        }
        conn.disconnect();
        
//        String coordX = map.get("locX");  String coordY = map.get("locY");
//System.out.println("decodeLoc:" + map);         
        return map;
	}
}
