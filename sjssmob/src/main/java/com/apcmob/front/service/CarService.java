package com.apcmob.front.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface CarService {

	public Map<String, Object> selectTransSettingList(HttpServletRequest request, Map<String, Object> param, HttpSession session) throws Exception;
	
	public Map<String, Object> selectTransSettingDetail(HttpServletRequest request, Map<String, Object> param, HttpSession session) throws Exception;
	
	public Map<String, Object> updateCarInfo(HttpServletRequest request, Map<String, Object> param, HttpSession session) throws Exception;

}
