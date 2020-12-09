package com.wurigo.socialService.commons;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Utils {
		final static String[] area_names = { "서울", "부산", "대구", "인천", "광주", "대전","울산","세종", "경기","강원",
					   "충북", "충남", "전북", "전남", "경북", "경남", "제주" };
		final static String[] area_codes = { "11", "21", "22", "23", "24", "25","26","29", "31","32",
						"33", "34", "35", "36", "37", "38", "39" };
		
		public static String toCurrencyFormat(String value) {
			int num = Integer.parseInt(value);
	        NumberFormat formatter = new DecimalFormat("#,###");
	        String formattedNumber = formatter.format(num);
	        return formattedNumber;
	    }
		public static double calc_distance(double lat1,double lon1,double lat2,double lon2,String unit) {
			 
				
			double theta = lon1 - lon2;
			double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) +  
					Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
			dist = Math.acos(dist);
			dist = Math.toDegrees(dist);
			double miles = dist * 60 * 1.1515;

			if (unit.equals("K")) {
				return (miles * 1.609344);
			} else if (unit.equals("N")) {
				return (miles * 0.8684);
			} if (unit.equals("M")) { //meter
				return (miles * 1609.344);
			}
			else {
				return miles;
			}
			 
		}
		public static boolean isMidNight(String rTime) throws ParseException {
			
			long time = System.currentTimeMillis(); 

			SimpleDateFormat dayTime = new SimpleDateFormat("hh:mm:ss");
			Date curTime = new Date(time) ;
			
			Date midnight2 = dayTime.parse("04:00:00");
			Date midnight1 = dayTime.parse("00:00:00");
		    
			if(curTime.compareTo(midnight1)>=0  && curTime.compareTo(midnight2)<=0 ) return true;
			
			return false;

		}
		
	public static String get_area_code(String address) {
		String sido="", sido_code="";
		if(address.contains("충청북도"))		sido = "충북";
		else if(address.contains("충청남도")) sido = "충남";
		else if(address.contains("전라북도")) sido = "전북";
		else if(address.contains("전라남도")) sido = "전남";
		else if(address.contains("경상북도")) sido = "경북";
		else if(address.contains("경상남도")) sido = "경남"; 
		else sido = address.substring(0,2);
		
		for(int i=0; i<area_names.length; i++ ) {
			if(sido.equals(area_names[i])) {
				sido_code =  area_codes[i]; break;
			}
		}
		return sido_code;
	}
	
	// 지정된 범위의 정수 1개를 램덤하게 반환하는 메서드
	  // n1 은 "하한값", n2 는 상한값
	public static int randomRange(int n1, int n2) {
		return (int) (Math.random() * (n2 - n1 + 1)) + n1;
	}

	/**
     * Returns MAC address of the given interface name.
     * @param interfaceName eth0, wlan0 or NULL=use first interface 
     * @return  mac address or empty string
     */
    public static String getMACAddress(String interfaceName) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                if (interfaceName != null) {
                    if (!intf.getName().equalsIgnoreCase(interfaceName)) continue;
                }
                byte[] mac = intf.getHardwareAddress();
                if (mac==null) return "";
                StringBuilder buf = new StringBuilder();
                for (byte aMac : mac) buf.append(String.format("%02X:",aMac));  
                if (buf.length()>0) buf.deleteCharAt(buf.length()-1);
                return buf.toString();
            }
        } catch (Exception ignored) { } // for now eat exceptions
        return "";
        /*try {
            // this is so Linux hack
            return loadFileAsString("/sys/class/net/" +interfaceName + "/address").toUpperCase().trim();
        } catch (IOException ex) {
            return null;
        }*/
    }

    /**
     * Get IP address from first non-localhost interface
     * @param useIPv4   true=return ipv4, false=return ipv6
     * @return  address or empty string
     */
    public static String getIPAddress(boolean useIPv4) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress();
                        //boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
                        boolean isIPv4 = sAddr.indexOf(':')<0;

                        if (useIPv4) {
                            if (isIPv4) 
                                return sAddr;
                        } else {
                            if (!isIPv4) {
                                int delim = sAddr.indexOf('%'); // drop ip6 zone suffix
                                return delim<0 ? sAddr.toUpperCase() : sAddr.substring(0, delim).toUpperCase();
                            }
                        }
                    }
                }
            }
        } catch (Exception ignored) { } // for now eat exceptions
        return "";
    }
    //임시비밀번호 생성기 
    public static String getRamdomPassword() {
    	char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
    			'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 
    			'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' }; 
    	int idx = 0; StringBuffer sb = new StringBuffer(); 
    	System.out.println("charSet.length :::: "+charSet.length); 
    	for (int i = 0; i < 9; i++) { 
    		idx = (int)(charSet.length * Math.random()); // 36 * 생성된 난수를 Int로 추출 (소숫점제거) 
    		System.out.println("idx :::: "+idx); 
    		sb.append(charSet[idx]); 
    		} 
    	return sb.toString();
    	}
    

}
