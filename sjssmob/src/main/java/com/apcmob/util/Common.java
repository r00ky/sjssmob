package com.apcmob.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import org.springframework.web.multipart.MultipartFile;

import com.apcmob.common.CommPath;

public class Common {

	public static String nvl(String str, String str2) {
		return (str == null) ? str2 : str;
	}

	/**
	 * byte단위로 문자열을 자른다
	 * 
	 * 2바이트 문자열이 잘리는 부분은 제거한다.
	 * 
	 * @param endIndex
	 * @return 잘려진문자열
	 */
	public String cutStrInByte(String str, int endIndex) {
		StringBuffer sbStr = new StringBuffer(endIndex);
		int iTotal = 0;
		char[] chars = str.toCharArray();

		for (int i = 0; i < chars.length; i++) {
			iTotal += String.valueOf(chars[i]).getBytes().length;
			if (iTotal > endIndex) {
				sbStr.append("..."); // ... 붙일것인가 옵션
				break;
			}
			sbStr.append(chars[i]);
		}
		return sbStr.toString();
	}

	
	/**
	 * 문자의 길이를 계산한다 (한글2byte)
	 * 
	 * 
	 * @param str
	 * @return 글자 length
	 */
	public static int CharLen(String str) {
		int strlen = 0;

		for (int j = 0; j < str.length(); j++) {
			char c = str.charAt(j);
			if (c < 0xac00 || 0xd7a3 < c)
				strlen++;
			else
				strlen += 2; //
		}
		return strlen;
	}
	
	
	/* 서버 시간 가져오기 */
	public static String getDate(long addDate){
		String date = "";
		
		Date today = new Date();
		SimpleDateFormat dateprint = new SimpleDateFormat("yyyy-MM-dd");

		date = dateprint.format(today.getTime() + 1000 * 60 * 60 * 24 * addDate);

		return date;
	}

	
	/* 한글 자르기 */
	public static String strCut(String szText, String szKey, int nLength, int nPrev, boolean isNotag, boolean isAdddot) { // 문자열 자르기

		String r_val = szText;
		int oF = 0, oL = 0, rF = 0, rL = 0;
		int nLengthPrev = 0;
		Pattern p = Pattern.compile("<(/?)([^<>]*)?>", Pattern.CASE_INSENSITIVE); // 태그제거 패턴

		if (isNotag) {
			r_val = p.matcher(r_val).replaceAll("");
		} // 태그 제거
		r_val = r_val.replaceAll("&amp;", "&");
		r_val = r_val.replaceAll("(!/|\r|\n|&nbsp;)", ""); // 공백제거

		try {
			byte[] bytes = r_val.getBytes("UTF-8"); // 바이트로 보관 
			if (szKey != null && !szKey.equals("")) {
				nLengthPrev = (r_val.indexOf(szKey) == -1) ? 0 : r_val.indexOf(szKey); // 일단 위치찾고
				nLengthPrev = r_val.substring(0, nLengthPrev).getBytes("MS949").length; // 위치까지길이를 byte로 다시 구한다
				nLengthPrev = (nLengthPrev - nPrev >= 0) ? nLengthPrev - nPrev : 0; // 좀 앞부분부터 가져오도록한다.
			}

			// x부터 y길이만큼 잘라낸다. 한글안깨지게.
			int j = 0;

			if (nLengthPrev > 0)
				while (j < bytes.length) {
					if ((bytes[j] & 0x80) != 0) {
						oF += 2;
						rF += 3;
						if (oF + 2 > nLengthPrev) {
							break;
						}
						j += 3;
					} else {
						if (oF + 1 > nLengthPrev) {
							break;
						}
						++oF;
						++rF;
						++j;
					}
				}

			j = rF;

			while (j < bytes.length) {
				if ((bytes[j] & 0x80) != 0) {
					if (oL + 2 > nLength) {
						break;
					}
					oL += 2;
					rL += 3;
					j += 3;
				} else {
					if (oL + 1 > nLength) {
						break;
					}
					++oL;
					++rL;
					++j;
				}
			}

			r_val = new String(bytes, rF, rL, "UTF-8"); // charset 옵션

			if (isAdddot && rF + rL + 3 <= bytes.length) {
				r_val += "...";
			} // ...을 붙일지말지 옵션 
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return r_val;
	}
	
	
	/* 파일저장 */
	public static String saveFile(MultipartFile file){
		
		InputStream inputStream = null;  
		OutputStream outputStream = null;  
		
		String fileName = file.getOriginalFilename();  
		String sUploadPath = CommPath.FILE_UPLOAD_PATH;
		String sRootFolder = sUploadPath;
		int extIndex = fileName.lastIndexOf(".");
		String fileExt = fileName.substring(extIndex+1);
		String sUploadName = String.valueOf(System.currentTimeMillis())+"."+fileExt;
	
		File filePath = null;
		filePath = new File(sRootFolder);
		if(!filePath.exists()) filePath.mkdir();
		sUploadPath = sRootFolder;
		
		try {  
			inputStream = file.getInputStream();  
			File newFile = new File(sUploadPath, sUploadName);  
		
			if (!newFile.exists()) {  
				newFile.createNewFile();  
			}  
	
			outputStream = new FileOutputStream(newFile);  
			int read = 0;  
			byte[] bytes = new byte[1024];  
		  
			while ((read = inputStream.read(bytes)) != -1) {  
				outputStream.write(bytes, 0, read);  
			}  
		}catch (IOException e) {  
			e.printStackTrace();  
		}finally{
			try{if(inputStream!=null) inputStream.close();}catch(Exception ex){}
			try{if(outputStream!=null) outputStream.close();}catch(Exception ex){}
		}
		
		return sUploadName;
		
	}


	
}
