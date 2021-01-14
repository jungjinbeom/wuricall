package com.wurigo.socialService.commons;
import java.io.File; 


import java.io.FileInputStream;
import java.io.FileNotFoundException; 
import java.io.IOException; 
import org.slf4j.Logger; 
import org.slf4j.LoggerFactory; 
import com.jcraft.jsch.Channel; 
import com.jcraft.jsch.ChannelSftp; 
import com.jcraft.jsch.JSch; 
import com.jcraft.jsch.Session; 
import com.jcraft.jsch.SftpException; 

public class FTPUtil{
	
	private static Session session = null; 
	private static Channel channel = null; 
	private static ChannelSftp channelSftp = null; 
	
	// SFTP 서버연결 
	 public static void connect() throws Exception{
		JSch jsch = new JSch();
		System.out.println("연결");
	 	String url = "211.251.239.237"; 
	 	int port = 10000;
	 	String userName = "wuricall"; 
	 	String password = "morejeon";
	 		
	 	session = jsch.getSession(userName, url, port);
        session.setPassword(password);
        
        java.util.Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);
        session.connect();  
        
        channel = session.openChannel("sftp");
        channel.connect();

        // 8. 채널을 FTP용 채널 객체로 캐스팅한다.
      
        channelSftp = (ChannelSftp) channel;
        System.out.println(session);
        System.out.println(channelSftp);
    }
 
 	// 단일 파일 업로드 
	public void upload(String dir,String email,File file){ 
		FileInputStream in = null;
		try{ //파일을 가져와서 inputStream에 넣고 저장경로를 찾아 put 
			in = new FileInputStream(file); 
			channelSftp.cd(dir); channelSftp.put(in,email+"_L"+file.getName());
		}catch(SftpException se){ 
			se.printStackTrace(); 
		}catch(FileNotFoundException fe){
				fe.printStackTrace(); 
		}finally{ 
			try{
			in.close();
			} catch(IOException ioe){ 
				ioe.printStackTrace();
			} 
		} 
	} 
	//파일삭제
	public void fileDelete(String dir,String fileName){
	    try {
	    	channelSftp.cd(dir);
	    	channelSftp.rm(fileName);
	    } catch (SftpException e) {
	    	e.printStackTrace();
	    }
	}


		// 파일서버와 세션 종료 
	public void disconnect(){ 
		channelSftp.quit(); 
		session.disconnect(); 
	}
}


