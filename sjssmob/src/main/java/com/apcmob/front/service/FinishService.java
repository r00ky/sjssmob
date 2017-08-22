package com.apcmob.front.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface FinishService {

	public Map<String, Object> selectFinishSheet(HttpServletRequest request, Map<String, Object> param, HttpSession session) throws Exception;
	
}
