package com.apcmob.util;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SendSms {

	static Log log = LogFactory.getLog(com.apcmob.util.SendSms.class);
	
	public static void send(String toNumber, String msg, String msgtype) throws IOException {

		/* 좋은소식 ==> 기술지원 담당자와 통화하여 URL체크 제외 요청해야함. */
		//url
		String url = "http://asp.smsgo.co.kr/Remote/Regist/?";
            
		//인증코드
		url = url+"serviceAC=IDKYYSJFBF";

		//사용자ID
		url = url+"&sUserID=oil";

		//ReturnUrl
		url = url+"&ReturnUrl=XML";

		//receiverList
		url = url+"&receiverList=" + toNumber;

		//sms타입 L=LMS S=SMS
		if(Common.CharLen(msg)>90){
			url = url+"&MessageType=L";
		}else{
			url = url+"&MessageType=S";
		}

		//Subject
		url = url+"&Subject=";

		//SendMsg
		url = url+"&SendMsg=" + URLEncoder.encode(msg,"EUC-KR");

		//CallbackPhoneNo
		//주문
		if(msgtype=="1"){
			url = url+"&CallbackPhoneNo=042-523-5466";
		//가상계좌입금
		}else if(msgtype=="2"){
		    url = url+"&CallbackPhoneNo=042-523-5467";
		//차량배차등..
		}else{
		    url = url+"&CallbackPhoneNo=042-523-5466";
		}
		
		//ReservedChk
		url = url+"&ReservedChk=0";

		//ResDate
		long time = System.currentTimeMillis(); 
		SimpleDateFormat dayTime = new SimpleDateFormat("yyyyMMddHHmmss");
		String timeStr = dayTime.format(new Date(time));
		url = url+"&ResDate="+timeStr;

		//UserData1
		url = url+"&UserData1=";

		//UserData2
		url = url+"&UserData2=";
		
		//UserData3
		url = url+"&UserData3=";

		/* 비즈모아샷 SMS
		 * //url
		String url = "https://biz.moashot.com/EXT/URLASP/mssend.asp?commType=0&title=&nType=3&indexCode=1&returnType=1&returnUrl=";
		
		//id
		url = url+"&uid=sjss34216";

		//password
		url = url+"&pwd=sjss31481";
		
		//msg
		url = url+"&contents=" + URLEncoder.encode(msg,"EUC-KR");
		
		//sms타입 5=MMS 3=SMS
		if(Common.CharLen(msg)>80){
			//url = url+"&sendType=5"; //LMS	
			url = url+"&sendType=3"; //SMS
		}else{
			url = url+"&sendType=3";
		}
		
		//tonumber
		url = url+"&toNumber=" + toNumber;
		
		//from number
		url = url+"&fromNumber=042-523-5466";
		
		//start toime
		long time = System.currentTimeMillis(); 
		SimpleDateFormat dayTime = new SimpleDateFormat("yyyyMMddHHmmss");
		String timeStr = dayTime.format(new Date(time));
		url = url+"&startTime="+timeStr;
		*/
		
//		System.out.println(url);
		log.info("############################################################");
		log.info("##################### [ SMS SEND INFO ]#####################");
		log.info("TO NUMBER : " + toNumber);
		log.info("MSG       : " + msg);
		log.info("TIME      : " + timeStr);
		log.info("------------------------------------------------------------");
		log.info("URL       : " + url);
		
		//SEND SMS
		URL urls = new URL(url);
		URLConnection urlcon = urls.openConnection();
		
		log.info("RESULT    : " + urlcon.getHeaderField(0));
//		System.out.println(urlcon.getHeaderField(0));
		log.info("############################################################");
		
	}

}
