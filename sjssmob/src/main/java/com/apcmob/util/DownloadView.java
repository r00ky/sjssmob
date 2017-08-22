package com.apcmob.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

public class DownloadView extends AbstractView{

//	public DownloadView(){
//		setContentType("application/download; utf-8");    
//	}

	
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {        
		
		File file = (File)model.get("downloadFile");
		String oriFilename = (String)model.get("fileOriName");
		
		System.out.println("DownloadView --> file.getPath() : " + file.getPath());
		System.out.println("DownloadView --> file.getName() : " + file.getName());
		System.out.println("DownloadView --> file.oriName() : " + oriFilename);
		
		response.setContentType(getContentType());
		response.setContentLength((int)file.length());
		
		String userAgent = request.getHeader("User-Agent");
		
		String fileName = null;
		fileName = URLEncoder.encode(oriFilename, "UTF-8");
		
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
		response.setHeader("Content-Transfer-Encoding", "binary");
		
		OutputStream out = response.getOutputStream();
		FileInputStream fis = null;
		
		try {
			fis = new FileInputStream(file);
			FileCopyUtils.copy(fis, out);
		} catch(Exception e){
			e.printStackTrace();
		}finally{
			if(fis != null){
				try{
					fis.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}      
	    out.flush();
	
	}

}
