package com.apcmob.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.springframework.web.multipart.MultipartFile;

public class FileUtil {
	private static long SERIAL = -1;
	public static String IMAGE_PATH = "upimages";
	/**
	 * 파일명 가져오기
	 * @param fileName
	 * @return
	 */
	public static String getFileName(String fileName){
		System.out.println("fileName:"+fileName);
		int posExt = fileName.lastIndexOf(".");
		String ext = "";
		if(posExt>0){
			ext = fileName.substring(posExt+1);
		}
		if(SERIAL < 0){
			SERIAL = System.currentTimeMillis();
		}
		SERIAL++;
		String tmp = Long.toHexString(SERIAL) + "." +ext;
		return tmp;
	}
	/**
	 * 이미지 경로 생성
	 * @param rootPath
	 * @return
	 */
	public static String getImagePath(String rootPath){
		
    	String sYear = DateUtil.getStringByPattern("yyyy");
    	String sMonthDay = DateUtil.getStringByPattern("MMdd");
    	StringBuffer sbImagePath = new StringBuffer();

    	File filePath = null;
    	sbImagePath.append(IMAGE_PATH);
    	filePath = new File(rootPath + sbImagePath.toString());
		if(!filePath.exists()) filePath.mkdir();
		
		sbImagePath.append("/"+sYear);
    	filePath = new File(rootPath + sbImagePath.toString());
		if(!filePath.exists()) filePath.mkdir();
		
		sbImagePath.append("/"+sMonthDay);
    	filePath = new File(rootPath + sbImagePath.toString());
		if(!filePath.exists()) filePath.mkdir();

		return sbImagePath.toString();
	}
	/**
	 * 이미지 경로 생성
	 * @param rootPath
	 * @return
	 */
	public static String getFilePath(String rootPath){
		
    	String sYear = DateUtil.getStringByPattern("yyyy");
    	String sMonthDay = DateUtil.getStringByPattern("MMdd");
    	StringBuffer sbImagePath = new StringBuffer();

    	File filePath = null;
		
		sbImagePath.append("/"+sYear);
    	filePath = new File(rootPath + sbImagePath.toString());
		if(!filePath.exists()) filePath.mkdir();
		
		sbImagePath.append("/"+sMonthDay);
    	filePath = new File(rootPath + sbImagePath.toString());
		if(!filePath.exists()) filePath.mkdir();

		return sbImagePath.toString();
	}
	
	/**
	 * 파일 업로드
	 * @param formFile
	 * @param svrFile
	 * @return
	 */
    public static boolean uploadFormFile(MultipartFile formFile, File svrFile) throws FileNotFoundException, IOException{
    	boolean returnValue = false;
    	
        InputStream inStream = null;
        OutputStream outStream = null;
        try {
			if (!svrFile.exists()) {  
				svrFile.createNewFile();  
			}
        	
        	inStream = formFile.getInputStream();
            
        	outStream = new FileOutputStream(svrFile);
            int read = 0;
            byte[] bytes = new byte[1024];
			while ((read = inStream.read(bytes)) != -1) {  
				outStream.write(bytes, 0, read);  
			}  
            returnValue = true;
        } catch (FileNotFoundException ex) {
            throw ex;
        } catch (IOException ex) {
            throw ex;
        }finally{
			try{if(inStream!=null) inStream.close();}catch(Exception ex){}
			try{if(outStream!=null) outStream.close();}catch(Exception ex){}
        }
        return returnValue;
    }
    /**
     * 파일 삭제
     * @param path
     * @param fileName
     */
    public static boolean deleteFile(File file) throws Exception{
    	boolean returnValue = false;
        try {
        	if(file.exists()){
        		if(file.delete()){
        			returnValue = true;	
        		}else{
        			throw new Exception("File delete Error");
        		}
        		
        	}else{
        		throw new FileNotFoundException(file.getPath());
        	}
        } catch (Exception ex) {
            throw ex;
        }
        return returnValue;
    }
}